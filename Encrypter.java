package appci;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypter {

	//暗号鍵
    public static final String CRYPT_KEY = "1234567890123456";
    //初期ベクトル
    public static final String CRYPT_IV = "abcdefghijklmnop";
    
    public static void main(String[] args) {
		System.out.println(encrypter("test"));
	}
	
	public static String encrypter(String text) {
		//初期化
		String result = null;
		try {
			//バイト配列へ変換（対象、暗号鍵、初期ベクトル）
			byte[] byteText = text.getBytes("UTF-8");
			byte[] byteKey = CRYPT_KEY.getBytes("UTF-8");
			byte[] byteIv = CRYPT_IV.getBytes("UTF-8");
			
			System.out.println(byteText);
			System.out.println(byteKey);
			System.out.println(byteIv);
			
			//暗号鍵と初期ベクトルのオブジェクト生成
			SecretKeySpec key = new SecretKeySpec(byteKey,"AES");
			IvParameterSpec iv = new IvParameterSpec(byteIv);
			
			//Cipherオブジェクトの生成・初期化
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			
			// 暗号化の結果格納
			byte[] byteResult = cipher.doFinal(byteText);

			// Base64へエンコード
			result = Base64.getEncoder().encodeToString(byteResult);

		} catch (UnsupportedEncodingException e) { //文字のエンコードができない
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		//暗号を返す
		return result;
	}
	
	public static String decrypt(String text) {
		// 変数初期化
		String strResult = null;

		try {
			// Base64をデコード
			byte[] byteText = Base64.getDecoder().decode(text);

			// 暗号化キーと初期化ベクトルをバイト配列へ変換
			byte[] byteKey = CRYPT_KEY.getBytes("UTF-8");
			byte[] byteIv = CRYPT_IV.getBytes("UTF-8");

			// 復号化キーと初期化ベクトルのオブジェクト生成
			SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(byteIv);

			// Cipherオブジェクト生成
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// Cipherオブジェクトの初期化
			cipher.init(Cipher.DECRYPT_MODE, key, iv);

			// 復号化の結果格納
			byte[] byteResult = cipher.doFinal(byteText);

			// バイト配列を文字列へ変換
			strResult = new String(byteResult, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}

		// 復号化文字列を返却
		return strResult;
	}
}
