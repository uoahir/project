package mvc.jy.admin.model.dao;

import static mvc.jy.common.JDBCTemplate.close;

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
	
	public List<Member> selectMemberAll(Connection conn, int cPage, int numPerpage){
		List<Member> members = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			System.out.println(cPage);
			pstmt = conn.prepareStatement(sql.getProperty("selectMemberAll"));
			pstmt.setInt(1, (cPage-1)*numPerpage+1);
			pstmt.setInt(2, cPage*numPerpage);
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
		System.out.println(members+" 맞아?");
		return members;
	}
	
	public int selectMemberAllCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0; 
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectMemberAllCount"));
			rs= pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public List<Member> searchMember(Connection conn, String type, String keyword, int cPage, int numPerPage){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> result = new ArrayList<>();
		try {
			String sql = this.sql.getProperty("selectSearchMember");
			sql = sql.replace("#COL", type);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type.equals("userName")? "%" + keyword + "%" : keyword);
			pstmt.setInt(2, (cPage-1)*numPerPage+1);
			pstmt.setInt(3, cPage*numPerPage);
		
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(MemberDao.makeMember(rs));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}
	
	public int searchMemberCount(Connection conn, String type, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = this.sql.getProperty("searchMemberCount");
		sql = sql.replace("#COL", type);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type.equals("userName")?"%"+keyword+"%":keyword);
			
			rs = pstmt.executeQuery();
			if(rs.next()) result = rs.getInt(1);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return result;
	}
	
	
}
