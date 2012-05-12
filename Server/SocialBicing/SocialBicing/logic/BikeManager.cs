using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using com.mobilenik.socialbicing.model;
using com.mobilenik.socialbicing.dao;
using com.mobilenik.socialbicing.util;
using com.mobilenik.socialbicing.biz;

namespace com.mobilenik.socialbicing.logic
{
    public class BikeManager
    {
        //TODO Agregarle las bicicletas reservadas y en uso por el usuarios
        public GetFreeBikesResult GetFreeBikes(int idUser, float latitude, float longitude)
        {
            GetFreeBikesResult res = new GetFreeBikesResult();

            BikeDao dao = new BikeDao();
            List<Bike> bikes = dao.getBikesByState(Constants.STATE_BIKE_FREE);
            res.bikes = new List<Bike>();
            res.resCod = 0;
            foreach (Bike bike in bikes)
            {
                double distance = Functions.GetDistance(latitude, longitude, bike.latitude, bike.longitude);
                if (distance <= Constants.MAX_DISTANCE)
                {
                    res.bikes.Add(bike);
                }
            }

            res.userStatus = new UserManager().getUserStatus(idUser);

            return res;
        }

        public List<Bike> GetBikesByUser(int idUser)
        {
            BikeDao dao = new BikeDao();

            return dao.getBikesByUser(idUser);
        }

        public List<Bike> GetBikesByState(int idState)
        {
            BikeDao dao = new BikeDao();

            return dao.getBikesByState(idState);
        }
        internal Bike GetBike(int idBike)
        {
            return new BikeDao().getBike(idBike);
        }

        internal GetBikeResult GetBike(int idUser, int idBike)
        {
            GetBikeResult res = new GetBikeResult();
            BikeDao dao = new BikeDao();

            res.resCod = 0;
            res.bike = dao.getBike(idBike);
            res.userStatus = new UserManager().getUserStatus(idUser);

            return res;
        }

        internal UseBikeResult UseBike(int idUser, int idBike)
        {
            UseBikeResult res = new UseBikeResult();
            BikeDao dao = new BikeDao();

            res.resCod = 0;
            res.bike = dao.getBike(idBike);
            if (res.bike.idState != Constants.STATE_BIKE_FREE)
            {
                throw new Exception("Bicicleta no está libre");
            }
            dao.UpdateStatus(idBike, Constants.STATE_BIKE_IN_USE);
            res.userStatus = new UserManager().getUserStatus(idUser);

            return res;
        }

        internal FreeBikeResult FreeBike(int idUser, int idBike)
        {
            FreeBikeResult res = new FreeBikeResult();
            BikeDao dao = new BikeDao();
            UserDao uDao = new UserDao();

            res.resCod = 0;
            res.bike = dao.getBike(idBike);
            if (res.bike.idState != Constants.STATE_BIKE_IN_USE)
            {
                throw new Exception("Bicicleta no está en uso");
            }
            dao.UpdateStatus(idBike, Constants.STATE_BIKE_FREE);
            res.userStatus = new UserManager().getUserStatus(idUser);

            return res;
        }

        internal ReserveBikeResult Reserve(int idUser, int idBike)
        {
            UserManager usrManager = new UserManager();
            ReserveBikeResult res = new ReserveBikeResult();
            BikeDao dao = new BikeDao();
            ReserveDao reDao = new ReserveDao();
            Bike bike = dao.getBike(idBike);
            if (bike.idState != Constants.STATE_BIKE_FREE)
            {
                throw new Exception("Bicicleta no disponible para reserva");
            }

            bool userOk = usrManager.isOkForReserve(idUser);
            if (userOk == false)
            {
                throw new Exception("Usuario no puede tomar la reserva");
            }


            usrManager.userMakeReservation(idUser, idBike);
            dao.UpdateStatus(idBike, Constants.STATE_BIKE_RESERVED);

            Reserve reserve = new Reserve();
            reserve.randomCode = Functions.CreateReserveCode();
            reserve.idBike = idBike;
            reserve.idUser = idUser;
            reserve.idState = Constants.STATE_RESERVE_ACTIVE;
            reserve.stateTime = DateTime.Now;
            reserve.creationTime = DateTime.Now;
            reDao.Insert(reserve);

            res.resCod = 0;
            res.userStatus = new UserManager().getUserStatus(idUser);
            res.reserveCode = Functions.CreateReserveCode();

            return res;
        }

        internal PassBikeResult Pass(int idUser, int idBike, string reserveCode)
        {
            PassBikeResult res = new PassBikeResult();

            BikeDao dao = new BikeDao();
            ReserveDao reDao = new ReserveDao();
            ReserveCancelationDao reCnsDao = new ReserveCancelationDao();

            UserManager usrManager = new UserManager();
            
            bool userOk = usrManager.isOkForPass(idUser);
            if (userOk == false)
            {
                throw new Exception("Usuario no tiene bicicleta");
            }

            Bike bike = dao.getBike(idBike);
            if (bike.idState != Constants.STATE_BIKE_RESERVED 
                && bike.idState != Constants.STATE_BIKE_FREE)
            {
                throw new Exception("Bicicleta no disponible para entrega");
            }

            Reserve reserve = reDao.getReserve(idBike, reserveCode);
            if (reserve == null)
            {
                throw new Exception("Código de reserva inválido");
            }
            else if (reserve.idState != Constants.STATE_RESERVE_ACTIVE)
            {
                throw new Exception("La reserva no está en un estado válido");
            }
            else if ((DateTime.Now - reserve.creationTime).Minutes > Constants.RESERVE_TIMEOUT)
            {
                res.resCod = 1;
                res.resDesc = "La reserva ha expirado";

                reDao.UpdateStatus(reserve.id, Constants.STATE_RESERVE_EXPIRED);
                reCnsDao.Insert(reserve.id, Constants.RESERVE_CANCEL_REASON_TIMEOUT);
                User currentUsr = usrManager.getUser(idUser);
                if (currentUsr.profile == Constants.PROFILE_COMMON)
                {
                    dao.UpdateStatus(idBike, Constants.STATE_BIKE_FREE);
                }
                else if (currentUsr.profile == Constants.PROFILE_STATION)
                {
                    dao.UpdateStatus(idBike, Constants.STATE_BIKE_IN_STATION);
                }

                usrManager.cancelReservation(reserve.idUser);
            }
            else
            {
                dao.UpdateUser(idBike, reserve.idUser);
                dao.UpdateStatus(idBike, Constants.STATE_BIKE_IN_USE);
                reDao.UpdateStatus(reserve.id, Constants.STATE_RESERVE_USED);
                usrManager.changeState(reserve.idUser, Constants.STATE_USER_HAS_BIKE_ASSIGNED);
                usrManager.changeState(idUser, Constants.STATE_USER_HAS_NO_BIKE);
                res.resCod = 0;
            }
            return res;
        }

        //TODO
        internal CancelBikeReservationResult CancelReservation(int idUser, int idBike, string reserveCode, string comments)
        {
            UserManager usrManager = new UserManager();
            CancelBikeReservationResult res = new CancelBikeReservationResult();
            BikeDao dao = new BikeDao();
            ReserveDao reDao = new ReserveDao();
            ReserveCancelationDao reCnsDao = new ReserveCancelationDao();

            Reserve reserve = reDao.getReserve(idBike, reserveCode);
            if (reserve == null)
            {
                throw new Exception("Código de reserva inválido");
            }
            else if (reserve.idUser != idUser)
            {
                throw new Exception("La reserva no pertenece a ese usuario.");
            }
            else if (reserve.idState != Constants.STATE_RESERVE_ACTIVE)
            {
                throw new Exception("La reserva no está en un estado válido");
            }
            else if ((DateTime.Now - reserve.creationTime).Minutes > Constants.RESERVE_TIMEOUT)
            {
                res.resCod = 1;
                res.resDesc = "La reserva ha expirado";

                reDao.UpdateStatus(reserve.id, Constants.STATE_RESERVE_EXPIRED);
                reCnsDao.Insert(reserve.id, comments);
                User currentUsr = usrManager.getUser(idUser);
                if (currentUsr.profile == Constants.PROFILE_COMMON)
                {
                    dao.UpdateStatus(idBike, Constants.STATE_BIKE_FREE);
                }
                else if (currentUsr.profile == Constants.PROFILE_STATION)
                {
                    dao.UpdateStatus(idBike, Constants.STATE_BIKE_IN_STATION);
                }

                usrManager.cancelReservation(reserve.idUser);
            }
            else
            {
                dao.UpdateStatus(idBike, Constants.STATE_BIKE_FREE);
                usrManager.cancelReservation(reserve.idUser);
                res.resCod = 0;
            }

            return res;
        }

    }
}