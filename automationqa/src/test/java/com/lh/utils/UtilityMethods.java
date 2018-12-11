package com.lh.utils;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * <h1>This class has all the utility methods which are used by the Page Classes
 * </h1>
 * <p>
 * 
 * @author shishir.das
 * @version 1.0
 */
public class UtilityMethods {

	public static String extractPartialClientURL(String baseURL) {

		String clientURL = baseURL.substring(0, baseURL.length() - 13);
		return clientURL;
	}

	public static ArrayList<String> splitPipeSepratedString(String pipeSepratedSting) {

		ArrayList<String> alist = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(pipeSepratedSting, "|");
		while (st.hasMoreTokens()) {
			alist.add(st.nextToken());
		}
		return alist;

	}

}
