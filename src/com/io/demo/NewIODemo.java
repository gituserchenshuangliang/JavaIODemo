package com.io.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/**
 * New IO
 * Channel and Buffer
 * @author Cherry
 * 2020年4月16日
 */
public class NewIODemo {

	public static void main(String[] args) throws IOException {
//		ioShow();
		fileSHow();
	}
	
	//Channels.newXXX()获取Channel实例
	public static void ioShow() throws IOException {
		InputStream is = new FileInputStream("a.txt");
		ReadableByteChannel rbc = Channels.newChannel(is);
		WritableByteChannel wbc = new FileOutputStream("b.java").getChannel();
		pipe(rbc,wbc);
	}
	
	//FileSystems,Files,Paths的静态方法获取相应操作对象或文件
	public static void fileSHow() throws IOException {
		FileSystem fs = FileSystems.getDefault();
		
		//第一个参数之后接受不定长度变量
		Path pathd = Paths.get("E:", "MyStudy");
		Path pathf = Paths.get("E:", "MyStudy","BAT","A.bat");
//		System.out.println(pathf);
//		System.out.println(pathf.getFileName());
		
		//Files的静态方法可获得文件的很多属性
		
		Files.lines(pathf);//返回Stream<String>对象实现管道操作
		Files.isHidden(pathf);
		Files.readAllBytes(pathf);
		Files.newBufferedWriter(pathf);//获得BufferWriterr对象
		Files.newInputStream(pathf);
		//Files.setAttribute(pathf, "dos:hidden",true);
		Files.getAttribute(pathf, "basic:lastModifiedTime");
		BasicFileAttributes basics = Files.readAttributes(pathf, BasicFileAttributes.class);

		//DirectoryStream Glob语法过滤，搜索文档	{$*,*.java,Pro*}
		try(DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get("D:"),"Pro*");){
			ds.forEach(System.out::println);
		}
		
		Files.copy(Paths.get("E:/MyStudy/BAT/A.bat"), new FileOutputStream("b.txt"));
		
		for(File f : pathd.toFile().listFiles()){
			//BasicFileAttributes  获取和设置文件属性
			BasicFileAttributes basic = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
//			System.out.println(basic.creationTime());
//			System.out.println(basic.lastAccessTime());
		}
		
		//list()和walk()返回Stream<Path>
		try(Stream<Path> s = Files.list(pathd)){
			//只遍历目录
//			s.forEach(System.out::println);
		}
		try(Stream<Path> s = Files.walk(pathd)){
			//遍历目录及目录之下的所有文件
//			s.forEach(System.out::println);
		}
		
		
	}
	
	public static void pipe(ReadableByteChannel rb , WritableByteChannel wb) throws IOException {
		//使用JVM内存
		ByteBuffer bb = ByteBuffer.allocate(1024);
		//使用系统内存
//		ByteBuffer bb = ByteBuffer.allocateDirect(1024);
		try(ReadableByteChannel src = rb;WritableByteChannel dest = wb;){
			while(src.read(bb) != -1) {
				//将limit设置为position,position设置为0
				bb.flip();
				dest.write(bb);
				System.out.println(bb.toString());
				bb.clear();
			}
		}
	}
	
}
