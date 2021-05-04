package appci;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
public class Decrypt {
	public static void main(String[] args) {
		String key = "appci";
		//バイナリファイルの読み込み(パディングあり)
		byte[]origin = ReadFile(args[0]);
		System.out.println("読み込み完了");
		//二つに分割
		byte[]front = Arrays.copyOfRange(origin, 0, origin.length/2);
		byte[]back = Arrays.copyOfRange(origin,front.length,origin.length);
		//暗号化
		for(int i =0;i<3;++i) {
			front = Encode(front,key);
			key = Caesar(key,1);
			back = Encode(back,key);
			key = Caesar(key,1);
		}
		//一つに統合
		System.arraycopy(front,0,origin,0,front.length);
		System.arraycopy(back,0,origin,front.length,back.length);
		System.out.println("復号化完了");
		//復号化後のファイル出力
		CreateFile(origin,args[1]);
		System.out.println("出力完了");
	}
	
	//バイナリファイルの読み込み（返り値byte[]）
	private static byte[] ReadFile(String filePath){
		byte[] readBuffer = new byte[0];
		try (FileChannel channel = new FileInputStream(filePath).getChannel()){
			ByteBuffer buffer = ByteBuffer.allocate((int)channel.size());
			readBuffer = new byte[buffer.capacity()];
			while(true) {
				buffer.clear();
				if(channel.read(buffer) < 0)break;
				buffer.flip();
				buffer.get(readBuffer);
			}
			channel.close();
		}catch(FileNotFoundException ex){
            ex.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return readBuffer;
	}
	//暗号化（シーザー暗号）
	private static String Caesar(String key,int n) {
		byte[] tmp = key.replaceAll("[^a-z]", "").getBytes();
		byte[] buf = new byte[tmp.length];
		 for (int i = 0; i < tmp.length; i++) {
			 int num = ((tmp[i] - 97) + n + 26) % 26;
	         buf[i] = (byte) (num + 97);
	     }
		return new String(buf);
	}
	
	//暗号化（XOR暗号化）
	private static byte[] Encode(byte[] src,String key) {
		byte[] keyByte = new byte[0];
		byte[] encodeByte = new byte[src.length];
		//キーの文字列を変換する配列がカバーするまで繰り返す
        while(keyByte.length < src.length) {
             keyByte = (new String(keyByte) + key).getBytes();
        }
        //XOR変換
        for (int i = 0; i < src.length; i++) {
             encodeByte[i] = (byte)(src[i]^keyByte[i]);
        }
		return encodeByte;
	}
	//暗号化後のファイル出力
	private static void CreateFile(byte[] cipher,String fileName) {
		try(FileChannel channel = new FileOutputStream(fileName).getChannel()){
			ByteBuffer buffer = ByteBuffer.wrap(cipher);
			channel.write(buffer);
		}
		catch(FileNotFoundException ex){
            ex.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
