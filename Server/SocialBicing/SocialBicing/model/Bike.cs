using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace com.mobilenik.socialbicing.model
{
    public class Bike
    {
        public int id;
        public string code;
        public DateTime creationTime;
        public int idState;
        public DateTime stateTime;
        public float latitude;
        public float longitude;
        public string address;
        public int idUser;
    }
}