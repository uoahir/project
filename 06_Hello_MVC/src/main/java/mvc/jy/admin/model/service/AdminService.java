package mvc.jy.admin.model.service;

import java.sql.Connection;
import java.util.List;

import mvc.jy.admin.model.dao.AdminDao;
import mvc.jy.model.dto.Member;
import static mvc.jy.common.JDBCTemplate.*;

public class AdminService {
	
	private AdminDao dao = new AdminDao();
	public List<Member> selectMemberAll(){
		Connection conn = getConnection();
		List<Member> members = dao.selectMemberAll(conn);
		close(conn);
		return members;
	}
}
