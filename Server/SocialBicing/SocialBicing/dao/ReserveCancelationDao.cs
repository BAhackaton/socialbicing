using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using com.mobilenik.socialbicing.model;
using System.Data;

namespace com.mobilenik.socialbicing.dao
{
    public class ReserveCancelationDao : BaseDao
    {
        protected override string SQL_SELECT { get { return "ID_RESERVA, MOTIVO"; } }

        protected override void ReadDataRow(DataRow r, object obj)
        {
            ReserveCancel model = (ReserveCancel)obj;
            model.idReserve = DaoFunciones.GetIntFromDataBase(r["ID_RESERVA"]);
            model.comment = DaoFunciones.GetStringFromDataBase(r["MOTIVO"]);
        }

        protected override string TABLE_NAME
        {
            get { return "RESERVA_CANCELACION"; }
        }

        protected override object GetNewObjectModel()
        {
            return new ReserveCancel();
        }

        internal void Insert(int id, string reason)
        {
            string sql = "Insert Into " + TABLE_NAME
                + " ( ID_RESERVA, MOTIVO)"
                + " Values (" + DaoFunciones.GetValueToDB(id)
                + ", " + DaoFunciones.GetValueToDB(reason)
                + ")";
            ConexionProvider.GetInstance().EjecutarSql(sql);
        }
    }
}