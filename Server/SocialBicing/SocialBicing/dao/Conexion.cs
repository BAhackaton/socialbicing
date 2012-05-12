using System;
using System.Collections.Generic;
using System.Text;
using System.Data;

namespace com.mobilenik.socialbicing.dao
{
    public abstract class Conexion
    {
        protected int id;
        protected string connString;
        //protected string server, db, user, pwd;
        protected System.Data.Common.DbConnection dbConnection;
        protected System.Data.Common.DbTransaction dbTransaction;
        protected System.Data.Common.DbCommand dbCommand;

        //public Conexion(string server, string db, string user, string pwd)
        //{
        //    this.server = server;
        //    this.db = db;
        //    this.user = user;
        //    this.pwd = pwd;
        //}

        public Conexion(string connString)
        {
            this.connString = connString;
        }

        #region Metodos Abstractos

        public abstract void OpenConnection();

        public abstract int EjecutarSql(string sql);

        protected abstract System.Data.Common.DbDataAdapter GetDataAdapter();

        #endregion Metodos Abstractos

        #region Common

        public void BeginTran()
        {
            this.dbTransaction = this.dbConnection.BeginTransaction();
            this.dbCommand.Transaction = this.dbTransaction;
        }

        public void RollbackTran()
        {
            this.dbTransaction.Rollback();
            this.dbTransaction.Dispose();
        }

        public void CommitTran()
        {
            this.dbTransaction.Commit();
            this.dbTransaction.Dispose();
        }

        internal void CloseConection()
        {
            this.dbConnection.Close();
            this.dbConnection = null;
            this.dbCommand = null;
            this.dbTransaction = null;
        }

        public ConnectionState Estado
        {
            get { return this.dbConnection.State; }
        }

        public DataSet EjecutarQuery(string sql)
        {
            System.Data.Common.DbDataAdapter dbDataAdapter = null;
            DataSet dataSet;

            try
            {
                this.dbCommand.CommandText = sql;
                this.dbCommand.CommandType = CommandType.Text;

                dbDataAdapter = this.GetDataAdapter();
                dataSet = new DataSet();
                dbDataAdapter.Fill(dataSet);
            }
            catch (Exception e)
            {
                throw (e);
            }
            finally
            {
                dbDataAdapter.Dispose();
            }

            return dataSet;
        }

        private void TestConnection()
        {
            try
            {
                System.Data.Common.DbDataAdapter dbDataAdapter = null;
                DataSet dataSet;

                this.dbCommand.CommandText = "Select 1";
                this.dbCommand.CommandType = CommandType.Text;

                dbDataAdapter = this.GetDataAdapter();
                dataSet = new DataSet();
                dbDataAdapter.Fill(dataSet);
            }
            catch (Exception ex)
            {
                try
                {
                    this.OpenConnection();
                }
                catch
                {
                    throw ex;
                }
            }
        }

        public void FillDataSet(DataSet ds, string storedProcedure, ParameterMap[] parameters)
        {
            System.Data.Common.DbParameter para;

            dbCommand.Parameters.Clear();
            ds.EnforceConstraints = false;
            this.dbCommand.CommandText = storedProcedure;
            this.dbCommand.CommandType = CommandType.StoredProcedure;

            for (int i = 0; i < parameters.Length; i++)
            {
                para = dbCommand.CreateParameter();
                para.Direction = ParameterDirection.Input;
                para.ParameterName = parameters[i].Name;
                para.Value = parameters[i].Value;
                dbCommand.Parameters.Add(para);
            }

            System.Data.Common.DbDataAdapter dbDataAdapter = GetDataAdapter();
            dbDataAdapter.Fill(ds, storedProcedure);
        }

        public void FillDataSet(DataSet ds, string tableName, string sql)
        {
            ds.EnforceConstraints = false;
            this.dbCommand.CommandText = sql;
            this.dbCommand.CommandType = CommandType.Text;
            System.Data.Common.DbDataAdapter dbDataAdapter = GetDataAdapter();
            dbDataAdapter.Fill(ds, tableName);
        }

        public bool IsAvailable()
        {
            return this.dbConnection.State == ConnectionState.Executing
                        || this.dbConnection.State == ConnectionState.Open
                        || this.dbConnection.State == ConnectionState.Fetching;
        }

        #endregion Common

    }

    public class ParameterMap
    {
        private string name;
        private object value;

        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public object Value
        {
            get { return this.value; }
            set { this.value = value; }
        }


    }
}
