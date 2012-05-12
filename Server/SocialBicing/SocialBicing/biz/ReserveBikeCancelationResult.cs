using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace com.mobilenik.socialbicing.biz
{
    public class CancelBikeReservationResult : Result
    {
        public UserStatus userStatus;
        public string reserveCode;
    }
}