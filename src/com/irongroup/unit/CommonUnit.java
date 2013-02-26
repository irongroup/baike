package com.irongroup.unit;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUnit {
	public static String getSHAString(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(str.getBytes("UTF-8"));
		byte[] result = md.digest();

		StringBuffer sb = new StringBuffer();

		for (byte b : result) {
			int i = b & 0xff;
			if (i < 0xf) {
				sb.append(0);
			}
			sb.append(Integer.toHexString(i));
		}
		return sb.toString().toUpperCase();
	}
}
