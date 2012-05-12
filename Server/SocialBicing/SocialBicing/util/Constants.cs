using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace com.mobilenik.socialbicing.util
{
    public class Constants
    {
        public static int STATE_ACTIVO = 1;
        public static int STATE_BIKE_FREE = 2;
        public static int STATE_BIKE_RESERVED = 3;
        public static int STATE_BIKE_IN_USE = 4;
        public static int STATE_BIKE_IN_STATION = 5;
        public static int STATE_BIKE_OUT_OF_SERVICE = 6;
        public static int STATE_USER_HAS_NO_BIKE = 7;
        public static int STATE_USER_HAS_BIKE_ASSIGNED = 8;
        public static int STATE_USER_HAS_BIKE_RESERVED = 9;

        public static int STATE_RESERVE_ACTIVE = 10;
        public static int STATE_RESERVE_USED = 11;
        public static int STATE_RESERVE_EXPIRED = 12;

        public static float MAX_DISTANCE = 5;

        public static int RESERVE_TIMEOUT = 10;

        public static int PROFILE_STATION = 1;
        public static int PROFILE_COMMON = 2;

        public static string RESERVE_CANCEL_REASON_TIMEOUT = "Tiempo expirado";
    }
}