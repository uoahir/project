package mvc.jy.admin.model.service;

import java.sql.Connection;
import java.util.List;

import mvc.jy.admin.model.dao.AdminDao;
import mvc.jy.model.dto.Member;
import static mvc.jy.common.JDBCTemplate.*;

public class AdminService {
	
	private AdminDao dao = new AdminDao();
	
	public List<Member> selectMemberAll(int cPage, int numPerpage){
		Connection conn = getConnection();
		List<Member> members = dao.selectMemberAll(conn, cPage, numPerpage);
		close(conn);
		return members;
	}
	public int selectMemberAllCount(){
		Connection conn = getConnection();
		int result = dao.selectMemberAllCount(conn);
		close(conn);
		return result;
	}
	

}
