package koreait.jdbc.day2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ScoreSelctWithSubject {
	public static void main(String[] args) {
		Connection conn = OracleUtility.getConnection();
		System.out.println("::::::::: 학생을 학번으로 조회하는 메뉴 :::::::::");
		selectManyScore(conn);
		
		OracleUtility.close(conn);
	}

	private static void selectCount(Connection conn, String subject) {
		String sql = "SELECT count(*) FROM TBL_SCORE WHERE SUBJECT = ?";
		// *2. 조건절에 사용하는 컬럼이 기본키와 유니크일 때는 0 또는 1개 행이 조회되고	-> rs.next() 를 if에 사용
		//					 기본키와 유니크가 아닐 때는 0~n 개 행이 조회됩니다.	-> 			while 에 사용

		try(PreparedStatement ps = conn.prepareStatement(sql);){
			ps.setString(1, subject);
			ResultSet rs = ps.executeQuery();
			
			int count = 0;
			if(rs.next()) {	// *1.다른 조회문과 다르게 if 문 안써도 됩니다. rs.next() 만 단독으로.
				count = rs.getInt(1);
			}
			System.out.println("과목 << " + subject + " >> " + count + " 건이 조회되었습니다.");
			
		} catch(SQLException e) {
			System.out.println("데이터 조회에 문제가 생겼습니다. 상세내용 - " + e.getMessage());
		}
	}

	private static void selectManyScore(Connection conn) {
		Scanner sc = new Scanner(System.in);
		
		String sql = "SELECT stuno, jumsu, term, teacher FROM TBL_SCORE WHERE SUBJECT = ?";
		// *1. count 와 같은 함수 결과는 항상 행 1개, 컬럼 1개
		System.out.print("조회할 과목 입력 >>> ");
		String subject = sc.nextLine();
		
		try(PreparedStatement ps = conn.prepareStatement(sql);){
			ps.setString(1, subject);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {	
				System.out.println(String.format("학번: %s 점수: %d 학기: %s 선생님: %s"
						, rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
//				count++;
			}
			
//			sql = "select count(*) from tbl_score where subject = ?";
			// 참고 : 입력한 과목의 건(행) 수를 조회할 수 있습니다.
			
			selectCount(conn, subject);
		} catch(SQLException e) {
			System.out.println("데이터 조회에 문제가 생겼습니다. 상세내용 - " + e.getMessage());
		}
		sc.close();
	}

}
