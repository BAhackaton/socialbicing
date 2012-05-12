using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

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
    }
}