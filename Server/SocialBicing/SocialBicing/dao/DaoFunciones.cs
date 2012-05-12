using System;
using System.Collections.Generic;
using System.Text;
using com.mobilenik.socialbicing.Util;


namespace com.mobilenik.socialbicing.dao
{
    public static class DaoFunciones
    {
        public static string GetValueToDB(bool valor)
        {
            return (valor == true) ? 1.ToString() : 0.ToString();
        }

        public static string GetValueToDB(string valor)
        {
            return (valor != null && valor != string.Empty) ? "'" + valor.Replace("'", "") + "'" : "NULL";
        }

        public static string GetValueToDB(char? valor)
        {
            return (valor != null && valor.HasValue) ? "'" + valor.ToString() + "'" : "NULL";
        }

        public static string GetValueToDB(char valor)
        {
            return "'" + valor.ToString() + "'";
        }

        public static string GetValueToDB(DateTime? valor)
        {
            if (valor == null)
                return "null";
            else
                return "'" + valor.Value.Year.ToString("0000")
                    + valor.Value.Month.ToString("00")
                    + valor.Value.Day.ToString("00")
                    + " "
                    + valor.Value.Hour.ToString("00")
                    + ":"
                    + valor.Value.Minute.ToString("00")
                    + ":"
                    + valor.Value.Second.ToString("00")
                    + "'";
        }

        public static string GetValueToDB(int? valor)
        {
            return (valor.HasValue) ? valor.ToString() : "NULL";
        }

        public static string GetValueToDB(float? valor)
        {
            return (valor != null && valor.HasValue) ? "'" + valor.Value.ToString(FormatoProvider.GetInstance()) + "'" : "NULL";
        }

        public static string GetLikeValueToDB(string valor)
        {
            return (valor != null && valor != string.Empty) ? "'%" + valor + "%'" : "'%%'";
        }

        public static bool GetBoolFromDataBase(object valor)
        {
            if (valor is bool)
                return (bool)valor;
            if (valor is short)
                return ((short)valor == 1 ? true : false);
            throw new Exception(
                "El parámetro pasado como argumento no es de tipo 'booleano'." + Environment.NewLine +
                "Su tipo es '" + valor.GetType().ToString() + "'.");

        }

        public static int? GetNullableIntFromDataBase(object valor)
        {
            if (valor is System.DBNull)
                return null;
            if (valor is int)
                return (int?)valor;
            if (valor is long)
                return (int?)(long)valor;
            throw new Exception(
                "El parámetro pasado como argumento no es de tipo 'entero'." + Environment.NewLine +
                "Su tipo es '" + valor.GetType().ToString() + "'.");
        }

        public static int GetIntFromDataBase(object valor)
        {
            if (valor is System.DBNull)
                return -1;
            if (valor is byte)
                return Convert.ToInt32(valor);
            if (valor is long)
                return (int)(long)valor;
            if (valor is byte)
                return (int)valor;
            if (valor is int)
                return (int)valor;
            if (valor is short)
                return (short)valor;
            if (valor is string)
                return int.Parse((string)valor);
            if (valor is decimal)
                return (int)(decimal)valor;
            throw new Exception(
                "El parámetro pasado como argumento no es de tipo 'entero'." + Environment.NewLine +
                "Su tipo es '" + valor.GetType().ToString() + "'.");
        }

        public static DateTime GetDateTimeFromDataBase(object valor)
        {
            if (valor is DateTime)
                return (DateTime)valor;
            throw new Exception(
                "El parámetro pasado como argumento no es de tipo 'DateTime'." + Environment.NewLine +
                "Su tipo es '" + valor.GetType().ToString() + "'.");
        }

        public static DateTime? GetNullableDateTimeFromDataBase(object valor)
        {
            if (valor is System.DBNull)
                return null;
            if (valor is DateTime)
                return (DateTime?)valor;
            throw new Exception(
                "El parámetro pasado como argumento no es de tipo 'DateTime'." + Environment.NewLine +
                "Su tipo es '" + valor.GetType().ToString() + "'.");
        }

        public static float GetFloatFromDataBase(object valor)
        {
            if (valor is System.DBNull)
                return -1f;
            else
                return System.Convert.ToSingle(valor);
        }

        public static string GetStringFromDataBase(object valor)
        {
            if (valor is System.DBNull)
                return null;
            else
                return valor.ToString();
        }

        public static char GetCharFromDataBase(object valor)
        {
            if (valor is System.DBNull)
                return '_';
            else
                return System.Convert.ToChar(valor);
        }
    }
}
