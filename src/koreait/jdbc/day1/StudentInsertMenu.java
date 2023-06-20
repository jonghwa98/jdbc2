package koreait.jdbc.day1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class StudentInsertMenu {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("::::::::: 학생 등록 메뉴 입니다. :::::::::");
		System.out.println("학생번호 입력시 0000 입력은 종료입니다.");
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "iclass";
		String password = "0419";
		
		try (
			Connection conn = DriverManager.getConnection(url, user, password);
			){
			String sql = "INSERT INTO TBL_STUDENT VALUES(?, ?, ?, ?)";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			String str;
			int num;
			
			while(true) {
				System.out.print("학번 : ");
				str = sc.nextLine();
				if(str.equals("0000")) {
					break;
				}
				pstmt.setString(1, str);	
				
				System.out.print("이름 : ");
				str = sc.nextLine();
				pstmt.setString(2, str);
				
				System.out.print("나이 : ");
				num = Integer.parseInt(sc.nextLine());
				pstmt.setInt(3, num);
				
				System.out.print("사는 지역 : ");
				str = sc.nextLine();
				pstmt.setString(4, str);
				
				pstmt.execute();
				System.out.println("정상적으로 새로운 학생이 입력되었습니다!!!");
				
			}
			System.out.println("종료되었습니다.");
			pstmt.close();
			sc.close();
		} catch(Exception e) {
			System.out.println("오류메시지 = " + e.getMessage());
		}
	}
}
















