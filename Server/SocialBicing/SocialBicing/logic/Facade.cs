using com.mobilenik.socialbicing.model;
using System.Collections.Generic;
using com.mobilenik.socialbicing.dao;
using System;
using com.mobilenik.socialbicing.util;
using com.mobilenik.socialbicing.biz;
namespace com.mobilenik.socialbicing.logic
{
    public class Facade
    {
        public static void AbrirConexion()
        {
            try
            {
                string connStr = System.Configuration.ConfigurationManager.ConnectionStrings["SBConnectionString"].ConnectionString;

                ConexionProvider.AbrirConexion(TiposConexiones.SqlServerConexcion, connStr);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public static void CerrarConexion()
        {
            try
            {
                ConexionProvider.CerrarConexion();
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public static LoginResult ValidateLogin(string userName, string password)
        {
            LoginResult res;
            try
            {
                Facade.AbrirConexion();
                res = new UserManager().ValidateLogin(userName, password);
            }
            catch (Exception ex)
            {
                res = new LoginResult();
                res.resCod = 99;
                res.resDesc = "Error inesperado: " + ex.Message;
            }
            finally
            {
                Facade.CerrarConexion();
            }
            return res;
        }

        public static GetFreeBikesResult GetFreeBikes(int idUser, float latitude, float longitude)
        {
            GetFreeBikesResult res;
            try
            {
                Facade.AbrirConexion();
                res = new BikeManager().GetFreeBikes(idUser, latitude, longitude);            
            }
            catch (Exception ex)
            {
                res = new GetFreeBikesResult();
                res.resCod = 99;
                res.resDesc = "Error inesperado: " + ex.Message;
            }
            finally
            {
                Facade.CerrarConexion();
            }
            return res;
        }




        internal static GetBikeResult GetBike(int idUser, int idBike)
        {
            GetBikeResult res;
            try
            {
                Facade.AbrirConexion();
                res = new BikeManager().GetBike(idUser, idBike);
            }
            catch (Exception ex)
            {
                res = new GetBikeResult();
                res.resCod = 99;
                res.resDesc = "Error inesperado: " + ex.Message;
            }
            finally
            {
                Facade.CerrarConexion();
            }
            return res;
        }

        internal static UseBikeResult UseBike(int idUser, int idBike)
        {
            UseBikeResult res;
            try
            {
                Facade.AbrirConexion();
                res = new BikeManager().UseBike(idUser, idBike);
            }
            catch (Exception ex)
            {
                res = new UseBikeResult();
                res.resCod = 99;
                res.resDesc = "Error inesperado: " + ex.Message;
            }
            finally
            {
                Facade.CerrarConexion();
            }
            return res;
        }

        internal static FreeBikeResult FreeBike(int idUser, int idBike)
        {
            FreeBikeResult res;
            try
            {
                Facade.AbrirConexion();
                res = new BikeManager().FreeBike(idUser, idBike);
            }
            catch (Exception ex)
            {
                res = new FreeBikeResult();
                res.resCod = 99;
                res.resDesc = "Error inesperado: " + ex.Message;
            }
            finally
            {
                Facade.CerrarConexion();
            }
            return res;
        }

        internal static ReserveBikeResult ReserveBike(int idUser, int idBike)
        {
            ReserveBikeResult res;
            try
            {
                Facade.AbrirConexion();
                ConexionProvider.GetInstance().BeginTran();
                res = new BikeManager().Reserve(idUser, idBike);
                ConexionProvider.GetInstance().CommitTran();
            }
            catch (Exception ex)
            {
                ConexionProvider.GetInstance().RollbackTran();

                res = new ReserveBikeResult();
                res.resCod = 99;
                res.resDesc = "Error inesperado: " + ex.Message;
            }
            finally
            {
                Facade.CerrarConexion();
            }
            return res;
        }

        internal static PassBikeResult PassBike(int idUser, int idBike, string reserveCode)
        {
            PassBikeResult res;
            try
            {
                Facade.AbrirConexion();
                ConexionProvider.GetInstance().BeginTran();
                res = new BikeManager().Pass(idUser, idBike, reserveCode);
                ConexionProvider.GetInstance().CommitTran();
            }
            catch (Exception ex)
            {
                ConexionProvider.GetInstance().RollbackTran();

                res = new PassBikeResult();
                res.resCod = 99;
                res.resDesc = "Error inesperado: " + ex.Message;
            }
            finally
            {
                Facade.CerrarConexion();
            }
            return res;
        }

        internal static CancelBikeReservationResult CancelBikeReservation(int idUser, int idBike, string reserveCode)
        {
            CancelBikeReservationResult res;
            try
            {
                Facade.AbrirConexion();
                ConexionProvider.GetInstance().BeginTran();
                res = new BikeManager().CancelReservation(idUser, idBike, reserveCode);
                ConexionProvider.GetInstance().CommitTran();
            }
            catch (Exception ex)
            {
                ConexionProvider.GetInstance().RollbackTran();

                res = new CancelBikeReservationResult();
                res.resCod = 99;
                res.resDesc = "Error inesperado: " + ex.Message;
            }
            finally
            {
                Facade.CerrarConexion();
            }
            return res;
        }

    }
}