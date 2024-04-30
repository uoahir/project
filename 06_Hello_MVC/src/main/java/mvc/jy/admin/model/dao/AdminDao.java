package mvc.jy.admin.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import mvc.jy.model.dao.MemberDao;
import mvc.jy.model.dto.Member;
import static mvc.jy.common.JDBCTemplate.*;

public class AdminDao {
	private Properties sql = new Properties();
	private MemberDao MemberDao = new MemberDao();
	
	{
		String path= AdminDao.class.getResource("/sql/admin/sql_admin.properties").getPath();
		try(FileReader fr = new FileReader(path)) {
			sql.load(fr);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Member> selectMemberAll(Connection conn){
		List<Member> members = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectMemberAll"));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				members.add(MemberDao.makeMember(rs));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}	
		return members;
	}
}
