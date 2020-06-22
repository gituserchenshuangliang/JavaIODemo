package com.io.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;

/**
 * IO学习
 * 字符流
 * @author Cherry
 * 2020年4月8日
 */
public class CharStream {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		//数据来源：网络，数据目的地：文件
//		pipe(new FileReader(new File(args[0])),new FileWriter(new File(args[1])));
//		String path = System.getProperties().get("user.dir").toString();
//		pipe(new FileReader(new File("E:\\WKS2019\\JavaIODemo\\src\\com\\io\\demo\\ByteStream.java")),new FileWriter(new File("a.txt")));
		
		//指定文件编码
//		streamChar(new URL("https://openhome.cc/Gossip/").openStream(),new FileOutputStream(new File("url.txt")), "GBK");
		
		//通过缓存区处理字符
//		buffereStream(new FileReader(new File("E:\\WKS2019\\JavaIODemo\\src\\com\\io\\demo\\ByteStream.java")),new FileWriter(new File("a.txt")));
	}
	
	//多态
	public static void pipe(Reader src,Writer dest) throws IOException {
		try(Reader r = src;Writer w = dest;){
			int length;
			char[] c = new char[1024];
			while((length = src.read(c)) != -1) {
				w.write(c, 0, length);
			}
		}
	}
	
	public static void streamChar(InputStream src ,OutputStream dest,String charset) throws UnsupportedEncodingException, IOException {
		pipe(new InputStreamReader(src,charset), new OutputStreamWriter(dest,charset));
	}
	
	public static void buffereStream(Reader src,Writer dest) throws IOException {
		try(BufferedReader br = new BufferedReader(src);BufferedWriter bw = new BufferedWriter(dest)){
			int length;
			char[] c = new char[1024];
			while((length = br.read(c)) != -1) {
				bw.write(c, 0, length);
			}
		}
	}
}
