package com.cywj.file;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;


public class AppTest{
	public static void main(String args[]) {  
		   try {  
		       System.out.println(getMD5Checksum("D:\\BaiduNetdiskDownload\\房子\\装修参照\\客餐厅\\测试11.docx"));  
		   }  
		   catch (Exception e) {  
		       e.printStackTrace();  
		   }  
		}  
		  
		public static byte[] createChecksum(String filename) throws Exception {  
		   InputStream fis =  new FileInputStream(filename);          //<span style="color: rgb(51, 51, 51); font-family: arial; font-size: 13px; line-height: 20px;">将流类型字符串转换为String类型字符串</span>  
		  
		   byte[] buffer = new byte[1024];  
		   MessageDigest complete = MessageDigest.getInstance("MD5"); //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256  
		   int numRead;  
		  
		   do {  
		       numRead = fis.read(buffer);    //从文件读到buffer，最多装满buffer  
		       if (numRead > 0) {  
		       complete.update(buffer, 0, numRead);  //用读到的字节进行MD5的计算，第二个参数是偏移量  
		       }  
		   } while (numRead != -1);  
		  
		   fis.close();  
		   return complete.digest();  
		}  
		  
		public static String getMD5Checksum(String filename) throws Exception {  
			InputStream fis =  new FileInputStream(filename);          //<span style="color: rgb(51, 51, 51); font-family: arial; font-size: 13px; line-height: 20px;">将流类型字符串转换为String类型字符串</span>  
			  
			   byte[] buffer = new byte[1024];  
			   MessageDigest complete = MessageDigest.getInstance("MD5"); //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256  
			   int numRead;  
			  
			   do {  
			       numRead = fis.read(buffer);    //从文件读到buffer，最多装满buffer  
			       if (numRead > 0) {  
			       complete.update(buffer, 0, numRead);  //用读到的字节进行MD5的计算，第二个参数是偏移量  
			       }  
			   } while (numRead != -1);  
			  
			   fis.close();  
		   return byteToHexString(complete.digest());  
		}  
		
		private static String byteToHexString(byte[] b) {
			char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			char[] ob = new char[2];
		
			StringBuffer sb = new StringBuffer();
			for (int i=0; i < b.length; i++) {  
				ob[0] = Digit[( b[i] >>> 4) & 0X0f];
				ob[1] = Digit[ b[i] & 0X0F];
				sb.append(ob);
		    }  
			return sb.toString();
		} 
}
