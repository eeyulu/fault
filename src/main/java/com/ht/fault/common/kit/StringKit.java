package com.ht.fault.common.kit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class StringKit {

	/*
	 * 返回长度为【strLength】的随机数，在前面补0
	 */
	public static String getFixLenthString(int strLength) {

		Random rm = new Random();

		// 获得随机数
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);
		System.out.println("a:" + fixLenthString);
		// 返回固定的长度的随机数
		return fixLenthString.substring(2, strLength + 1);
	}

	public static String genRandomNum() {
		int maxNum = 36;
		int i;
		int count = 0;
		char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < 8) {
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	@SuppressWarnings("resource")
	public static String readFile(File file, String charset) {
		// 设置默认编码
		if (charset == null) {
			charset = "UTF-8";
		}

		if (file.isFile() && file.exists()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				StringBuffer sb = new StringBuffer();
				String text = null;
				while ((text = bufferedReader.readLine()) != null) {
					sb.append(System.lineSeparator()+text);
				}
				return sb.toString();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
}