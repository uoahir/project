package mvc.jy.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

// 대칭키 암호화하기
// key 한 개로 암호화 복호화를 모두 처리하는 것
// 기본적으로 키가 있어야 함 !
// 키를 분실하면... 암호화된 값을 절대 찾을 수 업슴 . . .

public class AESEncryptor {
	// 1. key 생성 및 가져오기 -> 한 번 생성된 key 를 이용해서 처리 -> key 를 파일로 저장해서 관리 ! ! ! 
	//  1) 생성된 key 가 있으면 그 key 를 이용
	//	2) 생성된 key 가 없으면 새로 만듦
	// 로직 구성하기
	
	// SecretKey 클래스를 이용해서 대칭키를 관리
	private static SecretKey key;
	private String path; // key 저장하는 파일의 위치를 저장하는 변수
	
	public AESEncryptor() {
		this.path = AESEncryptor.class.getResource("/").getPath();
		this.path = this.path.substring(0,this.path.indexOf("classes"));
		File keyFile = new File(this.path+"bslove.bs");
		if(keyFile.exists()) {
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(keyFile));){
				this.key= (SecretKey)ois.readObject();
			} catch(IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			generateKey();
		}
	}
	
	private void generateKey() {
		SecureRandom rnd = new SecureRandom();
		KeyGenerator keygen = null;
		
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.path+"bslove.bs"))) {
			keygen = KeyGenerator.getInstance("AES");
			keygen.init(128,rnd);
			AESEncryptor.key = keygen.generateKey();
			oos.writeObject(AESEncryptor.key);
			
		} catch ( IOException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Cipher 클래스를 이용해서 암호화 복호화 처리를 함
	// 암호화 메소드 설정하기
//	NoSuchPaddingException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException
	public static String encryptData(String oriVal) throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, AESEncryptor.key);
		
		byte[] oriByte = oriVal.getBytes(Charset.forName("utf-8")); // 다시원위치시켯을때 글자깨지먼 안돼서 ?
		byte[] encByte = cipher.doFinal(oriByte);
		
		return Base64.getEncoder().encodeToString(encByte);
		
	}
	
	// 복호화 메소드 설정하기
	public static String decryptData(String encVal) throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, AESEncryptor.key);
		
		byte[] encryptByte = Base64.getDecoder().decode(encVal.getBytes(Charset.forName("utf-8")));
		byte[] decryptByte = cipher.doFinal(encryptByte);
		
		return new String(decryptByte);
	}
	
}
