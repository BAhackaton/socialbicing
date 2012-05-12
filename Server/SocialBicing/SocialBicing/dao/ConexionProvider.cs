using System;
using System.Collections.Generic;
using System.Text;

namespace com.mobilenik.socialbicing.dao
{
    public class ConexionProvider
    {
        private static int ID_MAIN_CONN = -1;
        private static IList<Conexion> conexiones = new List<Conexion>();

        public static Conexion GetInstance(int idConn)
        {
            try
            {
                return conexiones[idConn];
            }
            catch (ArgumentOutOfRangeException)
            {
                throw new ArgumentOutOfRangeException("La conexion se encuentra disponible (inx: " + idConn + ")");
            }
        }

        public static Conexion GetInstance()
        {
            return GetInstance(ID_MAIN_CONN);
        }

        public static bool IsConexionOpen()
        {
            return ID_MAIN_CONN >= 0
                && conexiones.Count >= 0
                && ID_MAIN_CONN < conexiones.Count
                && GetInstance() != null 
                && GetInstance().IsAvailable();
        }

        public static int AbrirConexion(TiposConexiones tipoConn, string connString)
        {
            //string servidor = PropiedadManager.GetValor(Constantes.CONFIG_SERVIDOR_BD);
            //string bd = PropiedadManager.GetValor(Constantes.CONFIG_NOMBRE_BD);

            Conexion conexion = new ConexionSql(connString);

            conexiones.Add(conexion);
            conexion.OpenConnection();

            if (ID_MAIN_CONN == -1)
            {
                ID_MAIN_CONN = conexiones.Count - 1;
            }

            return conexiones.Count - 1;
        }

        public static void Reconnect(int idConn)
        {
            GetInstance(idConn).OpenConnection();
        }

        public static void CerrarConexion()
        {
            //if (GetInstance(ID_MAIN_CONN) != null)
            //{
            //    GetInstance(ID_MAIN_CONN).CloseConection();
            //    GetInstance(ID_MAIN_CONN) = null;
            //}
            //conexiones.RemoveAt(ID_MAIN_CONN);

            CerrarConexion(ID_MAIN_CONN);
            ID_MAIN_CONN = -1;
        }

        public static void CerrarConexion(int idConn)
        {
            if (GetInstance(idConn) != null)
            {
                GetInstance(idConn).CloseConection();
                conexiones[idConn] = null;
            }
            //conexiones.RemoveAt(idConn);
        }
    }

    public enum TiposConexiones
    {
        SqlServerConexcion,
        OdbcConexcion
    }
}
