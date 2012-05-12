using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using System.Collections;


namespace com.mobilenik.socialbicing.dao
{
    public abstract class BaseDao
    {
        protected abstract string SQL_SELECT { get;}
        protected abstract string TABLE_NAME { get;}
        protected abstract void ReadDataRow(DataRow r, object obj);
        protected abstract object GetNewObjectModel();

        private List<T> ConvertToGenericList<T>(System.Collections.IList originalList)
        {
            List<T> list = new List<T>();

            foreach (T item in originalList)
            {
                list.Add(item);
            }

            return list;
        }

        protected T GetModelObject<T>(string where)
        {
            List<T> aux = this.GetModelList<T>(where, null);

            if (aux.Count == 0)
                return default(T);
            else
                return aux[0];
        }

        protected List<T> GetModelList<T>(string where, string orderBy)
        {
            string fromWhere = "From " + this.TABLE_NAME;

            if (string.IsNullOrEmpty(where) == false)
            {
                fromWhere += " Where (" + where + ")";
            }
            if (string.IsNullOrEmpty(orderBy) == false)
            {
                fromWhere += " Order By " + orderBy;
            }

            return GetModelListVariasTablas<T>(fromWhere, null);
        }


        protected List<T> GetModelListVariasTablas<T>(string fromWhere, string alias)
        {
            ArrayList lista = new ArrayList();

            string sql = "Select ";
            if (string.IsNullOrEmpty(alias) == true)
            {
                sql += this.SQL_SELECT;
            }
            else
            {
                foreach (string campo in this.SQL_SELECT.Split(','))
                {
                    sql += alias + "." + campo + ",";
                }
                sql = sql.Substring(0, sql.Length - 1);
            }

            sql += " " + fromWhere;

            DataSet dataSet = ConexionProvider.GetInstance().EjecutarQuery(sql);

            foreach (DataRow row in dataSet.Tables[0].Rows)
            {
                object obj = this.GetNewObjectModel();

                ReadDataRow(row, obj);
                lista.Add(obj);
            }

            return this.ConvertToGenericList<T>(lista);
        }
    }
}
