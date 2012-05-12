package com.mobilenik.socialBicing.common.geocoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Utility class for  form encoding.this class is modified form java.net.URLEncoder so that it can work well in cldc env.
 * This class contains static methods
 * for converting a String to the <CODE>application/x-www-form-urlencoded</CODE> MIME
 * format. For more information about HTML form encoding, consult the HTML 
 * <A HREF="http://www.w3.org/TR/html4/">specification</A>. 
 *
 * <p>
 * When encoding a String, the following rules apply:
 *
 * <p>
 * <ul>
 * <li>The alphanumeric characters "<code>a</code>" through
 *     "<code>z</code>", "<code>A</code>" through
 *     "<code>Z</code>" and "<code>0</code>" 
 *     through "<code>9</code>" remain the same.
 * <li>The special characters "<code>.</code>",
 *     "<code>-</code>", "<code>*</code>", and
 *     "<code>_</code>" remain the same. 
 * <li>The space character "<code> </code>" is
 *     converted into a plus sign "<code>+</code>".
 * <li>All other characters are unsafe and are first converted into
 *     one or more bytes using some encoding scheme. Then each byte is
 *     represented by the 3-character string
 *     "<code>%<i>xy</i></code>", where <i>xy</i> is the
 *     two-digit hexadecimal representation of the byte. 
 *     The recommended encoding scheme to use is UTF-8. However, 
 *     for compatibility reasons, if an encoding is not specified, 
 *     then the default encoding of the platform is used.
 * </ul>
 *
 * <p>
 * For example using UTF-8 as the encoding scheme the string "The
 * string ü@foo-bar" would get converted to
 * "The+string+%C3%BC%40foo-bar" because in UTF-8 the character
 * ü is encoded as two bytes C3 (hex) and BC (hex), and the
 * character @ is encoded as one byte 40 (hex).
 *
 */
public class URLEncoder {

	// private static final int caseDiff = ('a' - 'A');
	private static String _dontNeedEncoding = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ -_.*";
	
	/**
     * Translates a string into <code>application/x-www-form-urlencoded</code>
     * format using a specific encoding scheme. This method uses the
     * supplied encoding scheme to obtain the bytes for unsafe
     * characters.
     * <p>
     * <em><strong>Note:</strong> The <a href=
     * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that
     * UTF-8 should be used. Not doing so may introduce
     * incompatibilites.</em>
     *
     * @param   s   <code>String</code> to be translated.
     * @param   enc   The name of a supported character encoding such as UTF-8
     * @return  the translated <code>String</code>.
     */
	public static String encode(String s, String enc)
			throws UnsupportedEncodingException {
		boolean needToChange = false;
		boolean wroteUnencodedChar = false;
		int maxBytesPerChar = 10; // rather arbitrary limit, but safe for now
		StringBuffer out = new StringBuffer(s.length());
		ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);

		OutputStreamWriter writer = new OutputStreamWriter(buf, enc);

		for (int i = 0; i < s.length(); i++) {
			int c = (int) s.charAt(i);
			if (dontNeedEncoding(c)) {
				if (c == ' ') {
					c = '+';
					needToChange = true;
				}
				out.append((char) c);
				wroteUnencodedChar = true;
			} else {
				// convert to external encoding before hex conversion
				try {
					if (wroteUnencodedChar) { // Fix for 4407610
						writer = new OutputStreamWriter(buf, enc);
						wroteUnencodedChar = false;
					}
					writer.write(c);
					/*
					 * If this character represents the start of a Unicode
					 * surrogate pair, then pass in two characters. It's not
					 * clear what should be done if a bytes reserved in the
					 * surrogate pairs range occurs outside of a legal surrogate
					 * pair. For now, just treat it as if it were any other
					 * character.
					 */
					if (c >= 0xD800 && c <= 0xDBFF) {
						if ((i + 1) < s.length()) {
							int d = (int) s.charAt(i + 1);
							if (d >= 0xDC00 && d <= 0xDFFF) {
								writer.write(d);
								i++;
							}
						}
					}
					writer.flush();
				} catch (IOException e) {
					buf.reset();
					continue;
				}
				byte[] ba = buf.toByteArray();
				for (int j = 0; j < ba.length; j++) {
					out.append('%');
					char ch = CCharacter.forDigit((ba[j] >> 4) & 0xF, 16);
					// converting to use uppercase letter as part of
					// the hex value if ch is a letter.
					// if (Character.isLetter(ch)) {
					// ch -= caseDiff;
					// }
					out.append(Character.toUpperCase(ch));
					ch = CCharacter.forDigit(ba[j] & 0xF, 16);
					out.append(Character.toUpperCase(ch));
				}
				buf.reset();
				needToChange = true;
			}
		}

		return (needToChange ? out.toString() : s);
	}

	/**
	 * Encoding simple, solamente los caracteres "<", ">", "/", " ", ":", "-" son procesados.
	 * @param sUrl
	 * @return Url Encodeada
	 */
	static public String encodeSimple(String sUrl){
		StringBuffer urlOK = new StringBuffer();
		for(int i=0; i<sUrl.length(); i++) 
		{
			char ch=sUrl.charAt(i);
			switch(ch)
			{
			case '<': urlOK.append("%3C"); break;
			case '>': urlOK.append("%3E"); break;
			case '/': urlOK.append("%2F"); break;
			case ' ': urlOK.append("%20"); break;
			case ':': urlOK.append("%3A"); break;
			case '-': urlOK.append("%2D"); break;
			default: urlOK.append(ch); break;
			} 
		}
		return urlOK.toString();
	}

	public static boolean dontNeedEncoding(int ch) {
		int len = _dontNeedEncoding.length();
		boolean en = false;
		for (int i = 0; i < len; i++) {
			if (_dontNeedEncoding.charAt(i) == ch) {
				en = true;
				break;
			}
		}

		return en;
	}

	static class CCharacter {
		public static char forDigit(int digit, int radix) {
			if ((digit >= radix) || (digit < 0)) {
				return '\0';
			}
			if ((radix < Character.MIN_RADIX) || (radix > Character.MAX_RADIX)) {
				return '\0';
			}
			if (digit < 10) {
				return (char) ('0' + digit);
			}
			return (char) ('a' - 10 + digit);
		}
	}
}
