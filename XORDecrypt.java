package appci;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
public class XORDecrypt {
	public static void main(String[] args) {
		String key = "appci";
		//バイナリファイルの読み込み
		byte[] origin = ReadFile(args[0]);
		//二つに分割
		byte[]front = Arrays.copyOfRange(origin, 0, origin.length/2);
		byte[]back = Arrays.copyOfRange(origin,front.length,origin.length);
		//暗号化
		for(int i =0;i<4;++i) {
			front = Decode(front,key);
			key += 1;
			back = Decode(back,key);
			key += 1;
		}
		//一つに統合
		System.arraycopy(front,0,origin,0         ,front.length);
		System.arraycopy(back,0,origin,front.length,back.length);
		//暗号化後のファイル出力
		CreateFile(origin,args[1]);
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
//暗号化（XOR暗号化）
private static byte[] Decode(byte[] src,String key) {
	byte[] keyByte = new byte[0];
	byte[] encodeByte = new byte[src.length];
	
	//キーの文字列を変換する文字列をカバーするまで繰り返す
    while(keyByte.length < src.length) {
         keyByte = (new String(keyByte) + key).getBytes();
    }
    
    //変換
    for (int i = 0; i < src.length; i++) {
        encodeByte[i] = (byte)(src[i]^keyByte[i]);
    }
    
	return encodeByte;
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
