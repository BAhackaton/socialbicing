using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using com.mobilenik.socialbicing.biz;
using com.mobilenik.socialbicing.model;

namespace com.mobilenik.socialbicing.biz
{
    public class GetFreeBikesResult : Result
    {
        public UserStatus userStatus;
        public List<Bike> bikes; 
    }
}
