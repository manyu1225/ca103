package com.forPos.model;
/*
 * Base64是一種能將任意Binary資料用64種字元組合成字串的方法，而這個Binary資料和字串資料彼此之間是可以互相轉換的，十分方便。
 * 在實際應用上，Base64除了能將Binary資料可視化之外，也常用來表示字串加密過後的內容
 * 
 * Java 8的java.util套件中，新增了Base64的類別，可以用來處理Base64的編碼與解碼
 * Java 8提供的Base64擁有更好的效能，要比sun.misc套件提供的還要快至少11倍， * 比Apache Commons Codec提供的還要快至少3倍。
 * 因此在Java上若要使用Base64，建議使用這個Java 8底下的java.util套件所提供的Base64類別。
*/

import java.io.*;
import java.util.Base64;

public class BASE64_Encoder$Decoder_ByJava8 {

	public static void main(String[] args) throws IOException {
		
		//＠來源 (圖片tomcat.gif)
//		InputStream in = new FileInputStream("/b64.txt");
//		byte[] buffer1 = new byte[in.available()];
//		in.read(buffer1);
//		in.close();
////
//		//Base64編碼
//		Base64.Encoder encoder = Base64.getEncoder();
//		String encodedText = encoder.encodeToString(buffer1);
//		System.out.println(encodedText);
		
		//＠來源 Base64解碼
		Base64.Decoder decoder = Base64.getDecoder();
String str;
        FileReader fr = new FileReader("b65.txt");
        
        BufferedReader  br  =   new  BufferedReader(fr) ;
        while ((str = br.readLine()) != null) {

//		str.substring(str.indexOf("base64")+7);
//        System.out.println(str);

		byte[] buffer2 = decoder.decode("str");
		
//    終點 (圖片tomcat2.gif)
		OutputStream out = new FileOutputStream("65.jpg");
		out.write(buffer2);
		out.close();
        }
//        
	}
}