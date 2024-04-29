package mvc.jy.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	// static 매소드들 
	// 대신 드라이버, 주소, 아이디 패스워드 프로펄티즈로 만들기
	
	public static Connection getConnection() {
		Connection conn = null;
		String path = JDBCTemplate.class.getResource("/driver.properties").getPath();
		try (FileReader fr = new FileReader(path);) {
			Properties driver = new Properties();
			driver.load(fr);
			Class.forName(driver.getProperty("driver"));
			conn = DriverManager.getConnection(driver.getProperty("url"), driver.getProperty("user"), driver.getProperty("pw"));
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","WEB","WEB");
			
			conn.setAutoCommit(false);
		
		}catch(ClassNotFoundException |IOException| SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void commit(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.rollback();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
