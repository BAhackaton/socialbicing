using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using com.mobilenik.socialbicing.util;

namespace com.mobilenik.socialbicing.model
{
    public class Reserve
    {
        public int id;
        public string randomCode;
        public int idBike;
        public int idUser;
        public DateTime creationTime;
        public int idState;
        public DateTime stateTime;
        private Bike bike;

        public Bike Bike
        {
            get { return bike; }
            set { bike = value; }
        }

        public DateTime expirationTime
        {
            get {
                DateTime t;
                t = creationTime.AddMinutes(Constants.RESERVE_TIMEOUT);
                return t;
            }

            set { expirationTime = value; }
        }


    }
}