using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using com.mobilenik.socialbicing.model;
using System.Data;

namespace com.mobilenik.socialbicing.dao
{
    public class UserDao : BaseDao
    {
        protected override string SQL_SELECT { get { return "ID_USUARIO, CODIGO, CLAVE, NOMBRE, ID_ESTADO, FECHA_ESTADO, FECHA_ALTA, PERFIL"; } }

        protected override void ReadDataRow(DataRow r, object obj)
        {
            User model = (User)obj;
            model.id = DaoFunciones.GetIntFromDataBase(r["ID_USUARIO"]);
            model.userName = DaoFunciones.GetStringFromDataBase(r["CODIGO"]);
            model.name = DaoFunciones.GetStringFromDataBase(r["NOMBRE"]);
            model.idState = DaoFunciones.GetIntFromDataBase(r["ID_ESTADO"]);
            model.stateTime = DaoFunciones.GetDateTimeFromDataBase(r["FECHA_ESTADO"]);
            model.creationTime = DaoFunciones.GetDateTimeFromDataBase(r["FECHA_ALTA"]);
            model.profile = DaoFunciones.GetIntFromDataBase(r["PERFIL"]);
        }

        protected override string TABLE_NAME
        {
            get { return "USUARIO"; }
        }

        protected override object GetNewObjectModel()
        {
            return new User();
        }

        public User getUser(string userName, string password)
        {
            string where = "CODIGO = " + DaoFunciones.GetValueToDB(userName)
                            + " and CLAVE = " + DaoFunciones.GetValueToDB(password);
            return this.GetModelObject<User>(where);

            //User user = new User();
            //user.id = 1;
            //user.userName = userName;
            //user.password = password;
            //user.name = "Ignacio Orona";
            //user.idState = 0;
            //user.creationTime = DateTime.Now;
            //user.stateTime = DateTime.Now;
            //return user;
        }

        internal User getUser(int idUser)
        {
            string where = "ID_USUARIO = " + DaoFunciones.GetValueToDB(idUser);
            return this.GetModelObject<User>(where);
        }

        internal void UpdateStatus(int idUsr, int idState)
        {
            string sql = "Update " + TABLE_NAME
                + " Set ID_ESTADO = " + DaoFunciones.GetValueToDB(idState)
                + ", FECHA_ESTADO = " + DaoFunciones.GetValueToDB(DateTime.Now)
                + " Where ID_USUARIO = " + DaoFunciones.GetValueToDB(idUsr);
            ConexionProvider.GetInstance().EjecutarSql(sql);
        }
    }
}