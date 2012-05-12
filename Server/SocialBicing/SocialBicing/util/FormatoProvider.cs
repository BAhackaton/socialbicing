using System;
using System.Collections.Generic;
using System.Text;
using System.Globalization;

namespace com.mobilenik.socialbicing.Util
{
    class FormatoProvider : IFormatProvider
    {
        private static CultureInfo cultura;
        private static FormatoProvider instance;

        public static FormatoProvider GetInstance()
        {
            if (instance == null)
            {
                cultura = (CultureInfo)CultureInfo.CurrentCulture.Clone();
                cultura.NumberFormat.NumberDecimalSeparator = ".";
                cultura.NumberFormat.NumberGroupSeparator = ",";
                cultura.DateTimeFormat.ShortDatePattern = "dd/MM/yyyy";
                instance = new FormatoProvider();
            }
            return instance;
        }

        public void setShortDatePattern(string format)
        {
            cultura.DateTimeFormat.ShortDatePattern = format;
        }

        #region IFormatProvider Members

        public object GetFormat(Type formatType)
        {
            if (formatType.Name == "NumberFormatInfo")
            {
                return FormatoProvider.cultura.NumberFormat;
            }
            else if (formatType.Name == "DateTimeFormatInfo")
            {
                return FormatoProvider.cultura.DateTimeFormat;
            }
            return formatType;
        }

        #endregion
    }
}
