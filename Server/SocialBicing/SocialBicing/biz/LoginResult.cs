using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using com.mobilenik.socialbicing.biz;

namespace com.mobilenik.socialbicing.biz
{
    public class LoginResult : Result
    {
        public int id;
        public string name;
        public int profile;
        public UserStatus userStatus;
    }
}
