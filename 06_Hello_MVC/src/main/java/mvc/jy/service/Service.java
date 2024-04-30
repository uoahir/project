package mvc.jy.service;

import static mvc.jy.common.JDBCTemplate.*;
import static mvc.jy.common.JDBCTemplate.getConnection;

import java.sql.Connection;

import mvc.jy.model.dao.MemberDao;
import mvc.jy.model.dto.Member;

public class Service {
	private MemberDao dao = new MemberDao();
	
	public Member login(String userId, String password) {
		Connection conn = getConnection();
		
		Member m = dao.login(conn,userId);
		
		if(m==null || !m.getPassword().equals(password)) m=null;
		
		close(conn);
		return m;
	}
	
	public int signup(Member m) {
		Connection conn = getConnection();
		
		int result = dao.signup(conn,m);
		if(result > 0) {
			// 한개의 단일값이므로 커밋 롤백 처리할 필요는 없다 사실 !!!!!!
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	public Member selectMemberById(String userId) {
		Connection conn = getConnection();
		Member m = dao.login(conn,userId);
		close(conn);
		return m;
	}


}
