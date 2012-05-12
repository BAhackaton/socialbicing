using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using com.mobilenik.socialbicing.model;
using System.Data;

namespace com.mobilenik.socialbicing.dao
{
    public class ReserveDao : BaseDao
    {
        protected override string SQL_SELECT { get { return "ID_RESERVA, ID_BICI, ID_USUARIO, FECHA_ALTA, ID_ESTADO, FECHA_ESTADO, CODIGO_CONFIRMACION"; } }

        protected override void ReadDataRow(DataRow r, object obj)
        {
            Reserve model = (Reserve)obj;
            model.id = DaoFunciones.GetIntFromDataBase(r["ID_RESERVA"]);
            model.idBike = DaoFunciones.GetIntFromDataBase(r["ID_BICI"]);
            model.idUser = DaoFunciones.GetIntFromDataBase(r["ID_USUARIO"]);
            model.creationTime = DaoFunciones.GetDateTimeFromDataBase(r["FECHA_ALTA"]);
            model.idState = DaoFunciones.GetIntFromDataBase(r["ID_ESTADO"]);
            model.stateTime = DaoFunciones.GetDateTimeFromDataBase(r["FECHA_ESTADO"]);
            model.randomCode = DaoFunciones.GetStringFromDataBase(r["CODIGO_CONFIRMACION"]);
        }

        protected override string TABLE_NAME
        {
            get { return "RESERVA"; }
        }

        protected override object GetNewObjectModel()
        {
            return new Reserve();
        }

        internal void Insert(Reserve model)
        {
            string sql = "Insert Into " + TABLE_NAME
                + " ( ID_BICI, ID_USUARIO, FECHA_ALTA, ID_ESTADO, FECHA_ESTADO, CODIGO_CONFIRMACION)"
                + " Values (" + DaoFunciones.GetValueToDB(model.idBike)
                + ", " + DaoFunciones.GetValueToDB(model.idUser)
                + ", " + DaoFunciones.GetValueToDB(model.creationTime)
                + ", " + DaoFunciones.GetValueToDB(model.idState)
                + ", " + DaoFunciones.GetValueToDB(model.stateTime)
                + ", " + DaoFunciones.GetValueToDB(model.randomCode)
                + ")";
            model.id = ConexionProvider.GetInstance().EjecutarSql(sql);
        }

        internal Reserve getReserve(int idBike, string reserveCode)
        {
            string where = "ID_BICI = " + DaoFunciones.GetValueToDB(idBike)
                + " and CODIGO_CONFIRMACION = " + DaoFunciones.GetValueToDB(reserveCode);
            return this.GetModelObject<Reserve>(where);
        }

        internal void UpdateStatus(int idReserve, int idState)
        {
            string sql = "Update " + TABLE_NAME
                + " Set ID_ESTADO = " + DaoFunciones.GetValueToDB(idState)
                + ", FECHA_ESTADO = " + DaoFunciones.GetValueToDB(DateTime.Now)
                + " Where ID_RESERVA = " + DaoFunciones.GetValueToDB(idReserve);
            ConexionProvider.GetInstance().EjecutarSql(sql);
        }
    }
}