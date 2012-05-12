using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using com.mobilenik.socialbicing.model;

namespace com.mobilenik.socialbicing.biz
{
    public class OperationResult
    {
        public int result; //0 Ok 1 Error
        public string comments;
        public int bikeConditions; //0 ok, 1 pinchada
    }
}
