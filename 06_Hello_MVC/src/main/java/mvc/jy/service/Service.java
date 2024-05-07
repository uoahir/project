package mvc.jy.service;

import static mvc.jy.common.JDBCTemplate.close;
import static mvc.jy.common.JDBCTemplate.commit;
import static mvc.jy.common.JDBCTemplate.getConnection;
import static mvc.jy.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import mvc.jy.model.dao.MemberDao;
import mvc.jy.model.dto.Member;
import mvc.jy.model.dto.Notice;

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
	
//	여기부터 노티스 ~ 
	
	public List<Notice> searchNotice(int cPage, int numPerpage){
		Connection conn = getConnection();
		List<Notice> notice = dao.searchNotice(conn,cPage,numPerpage);
		close(conn);
		return notice;
	}
	
	public int selectNoticeCount() {
		Connection conn = getConnection();
		int result = dao.selectNoticeCount(conn);
		close(conn);
		return result;
	}
	
	public Notice selectNoticeByNo(int no) {
		Connection conn = getConnection();
		Notice n = dao.selectNoticeByNo(conn, no);
		close(conn);
		return n;
	}
	
	public int insertNotice(Notice n) {
		Connection conn = getConnection();
		int result = dao.insertNotice(conn, n);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}


}
