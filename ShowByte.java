package appci;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShowByte {
	public static void main(String[] args){
		//バイナリファイルの読み込み
		byte[]origin = ReadFile(args[0]);
		CreateFile(origin,args[1]);
		System.out.println(origin);
	}

//バイナリファイルの読み込み（返り値byte[]）
private static byte[] ReadFile(String filePath) {
	byte[] readBuffer = new byte[32768];
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	try {
		FileInputStream fis = new FileInputStream(filePath);
		BufferedInputStream bis = new BufferedInputStream(fis);
		int size;
		while((size = bis.read(readBuffer)) != -1) {
			baos.write(readBuffer, 0, size);
		}
		bis.close();
		fis.close();
	}catch (IOException e){
		e.printStackTrace();
        return new byte[0];
	}
	return baos.toByteArray();
}

//暗号化後のファイル出力
private static void CreateFile(byte[] cipher,String fileName) {
	try {
		FileOutputStream fos = new FileOutputStream(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(cipher);
		bos.close();
		fos.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}
