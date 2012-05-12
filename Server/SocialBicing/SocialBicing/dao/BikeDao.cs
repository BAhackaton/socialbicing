using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using com.mobilenik.socialbicing.model;
using System.Data;

namespace com.mobilenik.socialbicing.dao
{
    public class BikeDao : BaseDao
    {
        protected override string SQL_SELECT { get { return "ID_BICI, CODIGO, FECHA_ALTA, ID_ESTADO, FECHA_ESTADO, ID_USUARIO_RESPONSABLE, LATITUD, LONGITUD, DIRECCION"; } }

        protected override void ReadDataRow(DataRow r, object obj)
        {
            Bike model = (Bike)obj;
            model.id = DaoFunciones.GetIntFromDataBase(r["ID_BICI"]);
            model.code = DaoFunciones.GetStringFromDataBase(r["CODIGO"]);
            model.creationTime = DaoFunciones.GetDateTimeFromDataBase(r["FECHA_ALTA"]);
            model.idState = DaoFunciones.GetIntFromDataBase(r["ID_ESTADO"]);
            model.stateTime = DaoFunciones.GetDateTimeFromDataBase(r["FECHA_ESTADO"]);
            model.idUser = DaoFunciones.GetIntFromDataBase(r["ID_USUARIO_RESPONSABLE"]);
            model.latitude = DaoFunciones.GetFloatFromDataBase(r["LATITUD"]);
            model.longitude = DaoFunciones.GetFloatFromDataBase(r["LONGITUD"]);
            model.address = DaoFunciones.GetStringFromDataBase(r["DIRECCION"]);
        }

        protected override string TABLE_NAME
        {
            get { return "BICI"; }
        }

        protected override object GetNewObjectModel()
        {
            return new Bike();
        }

        public List<Bike> getBikesByState(int idState)
        {
            string where = "ID_ESTADO = " + DaoFunciones.GetValueToDB(idState);
            return this.GetModelList<Bike>(where, null);
        }

        public Bike getBike(int id)
        {
            string where = "ID_BICI = " + DaoFunciones.GetValueToDB(id);
            return this.GetModelObject<Bike>(where);
        }

        internal List<Bike> getBikesByUser(int idUser)
        {
            string where = "ID_USUARIO_RESPONSABLE = " + DaoFunciones.GetValueToDB(idUser);
            return this.GetModelList<Bike>(where, null);
        }

        internal void UpdateStatus(int idBike, int idState)
        {
            string sql = "Update " + TABLE_NAME
                + " Set ID_ESTADO = " + DaoFunciones.GetValueToDB(idState)
                + ", FECHA_ESTADO = " + DaoFunciones.GetValueToDB(DateTime.Now)
                + " Where id_bici = " + DaoFunciones.GetValueToDB(idBike);
            ConexionProvider.GetInstance().EjecutarSql(sql);
        }

        internal void UpdateUser(int idBike, int idUser)
        {
            string sql = "Update " + TABLE_NAME
                + " Set ID_USUARIO_RESPONSABLE = " + DaoFunciones.GetValueToDB(idUser)
                + " Where id_bici = " + DaoFunciones.GetValueToDB(idBike);
            ConexionProvider.GetInstance().EjecutarSql(sql);
        }
    }
}