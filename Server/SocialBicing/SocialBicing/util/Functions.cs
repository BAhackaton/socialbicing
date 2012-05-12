using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace com.mobilenik.socialbicing.util
{
    public class Functions
    {
        static Random _r = new Random();

        public static double GetDistance(double Lat1, double Long1, double Lat2, double Long2)
        {

            double dDistance = Double.MinValue;
            double dLat1InRad = Lat1 * (Math.PI / 180.0);
            double dLong1InRad = Long1 * (Math.PI / 180.0);
            double dLat2InRad = Lat2 * (Math.PI / 180.0);
            double dLong2InRad = Long2 * (Math.PI / 180.0);

            double dLongitude = dLong2InRad - dLong1InRad;
            double dLatitude = dLat2InRad - dLat1InRad;

            // Intermediate result a.
            double a = Math.Pow(Math.Sin(dLatitude / 2.0), 2.0) +
                       Math.Cos(dLat1InRad) * Math.Cos(dLat2InRad) *
                       Math.Pow(Math.Sin(dLongitude / 2.0), 2.0);

            // Intermediate result c (great circle distance in Radians).
            double c = 2.0 * Math.Asin(Math.Sqrt(a));

            // Distance.
            // const Double kEarthRadiusMiles = 3956.0;
            const Double kEarthRadiusKms = 6376.5;
            dDistance = kEarthRadiusKms * c;

            return dDistance;
        }

        public static string CreateReserveCode()
        {
            int number;
            string rv = null;
            for (int i = 0; i < 5; i++)
            {
                number = _r.Next(10);
                rv = string.Concat(rv, number.ToString());
            }

            return rv;
        }
    }
}