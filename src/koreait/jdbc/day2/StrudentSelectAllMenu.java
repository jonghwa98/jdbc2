package koreait.jdbc.day2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StrudentSelectAllMenu {
	public static void main(String[] args) {
		
		Connection conn = OracleUtility.getConnection();
		System.out.println("::::::::: 학생을 학번으로 조회하는 메뉴 :::::::::");
		SelectAllStudent(conn);
		
		OracleUtility.close(conn);
	}

	private static void SelectAllStudent(Connection conn) {
		
		String sql = "SELECT * FROM TBL_STUDENT";
		
		try(PreparedStatement ps = conn.prepareStatement(sql);){
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			while(true) {
				System.out.println(String.format("학번: %s\t이름: %s\t나이: %d\t주소: %s"
						, rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
				if(rs.next() == false) {
					break;
				}
			}
		} catch(SQLException e) {
			System.out.println("데이터 조회에 문제가 생겼습니다. 상세내용 - " + e.getMessage());
		}
	}
}
