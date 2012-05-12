using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using com.mobilenik.socialbicing.logic;
using com.mobilenik.socialbicing;
using com.mobilenik.socialbicing.biz;

namespace com.mobilenik.socialbicing
{
    /// <summary>
    /// Summary description for SocialBicing
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class SocialBicing : System.Web.Services.WebService
    {

        [WebMethod]
        public LoginResult Login(string usr, string pwd)
        {
            LoginResult ret = Facade.ValidateLogin(usr, pwd);
            return ret;
        }

        [WebMethod]
        public GetFreeBikesResult GetFreeBikes(int idUser, float latitude, float longitude)
        {
            GetFreeBikesResult ret = Facade.GetFreeBikes(idUser, latitude, longitude);
            return ret;
        }

        [WebMethod]
        public GetBikeResult GetBike(int idUser, int idBike)
        {
            GetBikeResult ret = Facade.GetBike(idUser, idBike);
            return ret;
        }

        [WebMethod]
        public UseBikeResult UseBike(int idUser, int idBike)
        {
            UseBikeResult ret = Facade.UseBike(idUser, idBike);
            return ret;
        }

        [WebMethod]
        public FreeBikeResult FreeBike(int idUser, int idBike)
        {
            FreeBikeResult ret = Facade.FreeBike(idUser, idBike);
            return ret;
        }

        [WebMethod]
        public ReserveBikeResult ReserveBike(int idUser, int idBike)
        {
            ReserveBikeResult ret = Facade.ReserveBike(idUser, idBike);
            return ret;
        }

        [WebMethod]
        public PassBikeResult PassBike(int idUser, int idBike, string reserveCode)
        {
            PassBikeResult ret = Facade.PassBike(idUser, idBike, reserveCode);
            return ret;
        }

        [WebMethod]
        public CancelBikeReservationResult CancelBikeReservation(int idUser, int idBike, string reserveCode, string comments)
        {
            CancelBikeReservationResult ret = Facade.CancelBikeReservation(idUser, idBike, reserveCode,  comments);
            return ret;
        }
    }
}
