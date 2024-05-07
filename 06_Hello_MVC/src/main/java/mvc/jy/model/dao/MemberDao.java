package mvc.jy.model.dao;

import static mvc.jy.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import mvc.jy.model.dto.Member;
import mvc.jy.model.dto.Notice;
public class MemberDao {
	
	private Properties sql = new Properties();
	
	{
		String path=MemberDao.class.getResource("/sql/member/sql_member.properties").getPath();
		try(FileReader fr = new FileReader(path)){
			sql.load(fr);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Member login(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member m = null;
//		String sql = "SELECT * FROM MEMBER WHERE USERID=? AND PASSWORD=?";
		try {
//			pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql.getProperty("login"));
			pstmt.setString(1, userId);
//			pstmt.setString(2, password);
			
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
				m = makeMember(rs);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return m;
	}
	
	public Member makeMember(ResultSet rs) throws SQLException{
		String hobby = rs.getString("hobby");
		Date enrollDate = rs.getDate("enrolldate");
		Member m = Member.builder().userId(rs.getString("USERID"))
				.password(rs.getString("PASSWORD"))
				.userName(rs.getString("USERNAME"))
				.gender(rs.getString("GENDER"))
				.age(rs.getInt("AGE"))
				.email(rs.getString("EMAIL"))
				.phone(rs.getString("PHONE"))
				.address(rs.getString("ADDRESS"))
				.hobby(
						hobby!=null?hobby.split(","):null)
				.enrollDate(
						enrollDate!=null?enrollDate:null)
				.build();
		return m;
	}
	
	public int signup(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("signup"));
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getPassword());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9,m.getHobby()!=null? String.join(",",m.getHobby()):null);
			
			result= pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public List<Notice> searchNotice(Connection conn, int cPage, int numPerpage){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Notice> notice= new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(this.sql.getProperty("searchNotice"));
			pstmt.setInt(1, (cPage-1)*numPerpage+1);
			pstmt.setInt(2, cPage*numPerpage);
			
			rs =  pstmt.executeQuery();
			
			while(rs.next()) {
				notice.add(getNotice(rs));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return notice;
	}
	
	public int selectNoticeCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectNoticeCount"));
			rs = pstmt.executeQuery();
			
			rs.next();
			result=rs.getInt(1);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
		
	}
	
	public Notice selectNoticeByNo(Connection conn, int no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice notice = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectNoticeByNo"));
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				notice = getNotice(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return notice;
	}
	
	public int insertNotice(Connection conn, Notice n) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertNotice"));
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeWriter());
			pstmt.setString(3, n.getNoticeContent());
			pstmt.setString(4, n.getFilePath());
			
			result = pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public static Notice getNotice(ResultSet rs) throws SQLException {
		return Notice.builder().noticeNo(rs.getInt("notice_no")).noticeTitle(rs.getString("notice_title"))
		.noticeWriter(rs.getString("notice_writer")).noticeContent(rs.getString("notice_content"))
		.noticeDate(rs.getDate("notice_date")).filePath(rs.getString("filepath")!=null?rs.getString("filepath"):"")
		.status(rs.getString("status")).build();
	}
}
