package mvc.jy.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PasswordEncoder extends HttpServletRequestWrapper{
	
	public PasswordEncoder(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getParameter(String name) {
		String oriVal = super.getParameter(name);
		if(name.equals("password")) {
			// 가저온 키 값이 password 와 일치하면 ~ ,,
			// 암호화 처리 !!! 하는 로직
			System.out.println("암호화 전 값 : "+oriVal);
			String encode = getSHA512(oriVal);
			System.out.println("암호화 후 값 : "+encode);
			return encode;
		} else {
			return super.getParameter(name);
			// 일치하지 않으면 그냥, 원래 겟파라미터 메소드 사용 ~ ! 
		}
	}
	
	private String getSHA512(String oriVal) {
		// java.security.MessageDigest 클래스를 이용해서 암호화함 !
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512"); // 이렇게 하면 ~ SHA-512 알고리즘 이용해서 처리하겠다 이 뜻임 . . .  자바에서 제공하는 알고리즘임 ~		
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// bit 연산처리 -> String -> byte 로 변환
		byte[] oriValByte = oriVal.getBytes(); // 겟바이츠 쓰면 바이트 배열로 반환 되고 ~ 
		md.update(oriValByte); // 매개변수로 들어오는 바이트 배열을 암호호ㅏ 해준당 ! // 내부 변경할 값을 설정
		byte[] encryptVal = md.digest(); // 변경된 값을 바이트배열로 가저옴.
		// 바이트배열 -> String 변환 : Base64 라는 인코더를 이용해서 변환 ! ! !
		String encryptStr =  Base64.getEncoder().encodeToString(encryptVal);
		return encryptStr;
	}
}
