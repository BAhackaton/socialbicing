using System;
using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;
using System.Data.Common;
using System.Data;

namespace com.mobilenik.socialbicing.dao{

    public class ConexionSql : Conexion
    {

        public ConexionSql(string connString)
            : base(connString)
        {
        }

        public override void OpenConnection()
        {
            this.dbConnection = new SqlConnection();

            this.dbConnection.ConnectionString = this.connString;
                //"Server=" + this.server +
                //";Database=" + this.db +
                //";User ID=" + this.user +
                //";Password=" + this.pwd +
                //";Trusted_Connection=false" +
                //";Connect Timeout=100";

            try
            {
                this.dbConnection.Open();
                this.dbCommand = new SqlCommand();
                this.dbCommand.Connection = this.dbConnection;
            }
            catch (Exception e)
            {
                throw e;
            }
        }

        public override int EjecutarSql(string sql)
        {
            try
            {
                this.dbCommand.CommandText = sql;
                this.dbCommand.CommandType = CommandType.Text;

                this.dbCommand.ExecuteNonQuery();

                this.dbCommand.CommandText = "SELECT @@IDENTITY";

                object identity = this.dbCommand.ExecuteScalar();

                if (!(identity is System.DBNull))
                    return (int)(decimal)identity; // no se puede convertir directamente a int;
                return 0;
            }
            catch (Exception e)
            {
                throw (e);
            }
        }

        protected override DbDataAdapter GetDataAdapter()
        {
            return new SqlDataAdapter((SqlCommand)this.dbCommand);
        }


    }
}
