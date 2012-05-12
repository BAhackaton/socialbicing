using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using com.mobilenik.socialbicing.biz;
using com.mobilenik.socialbicing.dao;
using com.mobilenik.socialbicing.model;
using com.mobilenik.socialbicing.util;

namespace com.mobilenik.socialbicing.logic
{
    public class UserManager
    {
        public UserStatus getUserStatus(int idUser)
        {
            UserDao usrDao = new UserDao();
            BikeManager bikeMng = new BikeManager();
            ReserveDao rDao = new ReserveDao();

            User usr = usrDao.getUser(idUser);

            UserStatus status = new UserStatus();
            status.idStatus = usr.idState;
            status.bikesAssigned = bikeMng.GetBikesByUser(idUser);
            status.reserve = rDao.getReserveFromUser(idUser);
            if (status.reserve != null)
                status.reserve.Bike = bikeMng.GetBike(status.reserve.idBike);

            return status;
        }

        public LoginResult ValidateLogin(string userName, string password)
        {
            LoginResult res = new LoginResult();
            UserDao dao = new UserDao();
            User u = dao.getUser(userName, password);

            if (u == null)
            {
                res.resCod = 1;
                res.resDesc = "Usuario o clave inexistente";
            }
            else
            {
                res.resCod = 0;
                res.id = u.id;
                res.profile = u.profile;
                res.name = u.name;
                res.userStatus = this.getUserStatus(u.id);
            }

            return res;
        }

        internal bool isOkForReserve(int idUser)
        {
            UserDao dao = new UserDao();

            User user = dao.getUser(idUser);

            return user.idState == Constants.STATE_USER_HAS_NO_BIKE;
        }

        internal void userMakeReservation(int idUser, int idBike)
        {
            UserDao dao = new UserDao();

            dao.UpdateStatus(idUser, Constants.STATE_USER_HAS_BIKE_RESERVED);
        }

        internal bool isOkForPass(int idUser)
        {
            UserDao dao = new UserDao();

            User user = dao.getUser(idUser);

            return user.idState == Constants.STATE_USER_HAS_BIKE_ASSIGNED;
        }

        internal User getUser(int idUser)
        {
            UserDao dao = new UserDao();

            User user = dao.getUser(idUser);
            return user;
        }

        internal void cancelReservation(int idUser)
        {
            UserDao dao = new UserDao();
            dao.UpdateStatus(idUser, Constants.STATE_USER_HAS_NO_BIKE);
        }

        internal void changeState(int idUser, int newState)
        {
            UserDao dao = new UserDao();
            dao.UpdateStatus(idUser, newState);
        }
    }
}