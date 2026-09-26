import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class SmartStoreApp {
    public static void main(String[] args){
        // 사용자 입력을 받기 위한 스캐너 객체 생성
        Scanner scanner = new Scanner(System.in);

        // 프로그램 실행 상태를 제어하는 변수
        boolean isRunning = true;

        System.out.println("===================================");
        System.out.println("   SmartStore Management System   ");
        System.out.println("===================================");

        // 데이터베이스 접속 정보 설정
        String url = "jdbc:mysql://localhost:3306/shop";
        String user = "root";
        String password = "*****";

        try {
            // 데이터베이스 연결 (파이프 생성)
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("\n### DB Connected Successfully!");

            // isRunning이 true인 동안 무한 반복
            while(isRunning){
                // 메인 메뉴 출력
                System.out.println("\n[1] View Device List");
                System.out.println("[2] Add New Device");
                System.out.println("[3] Update Device Price");
                System.out.println("[4] Delete Device");
                System.out.println("[0] Exit System");
                System.out.printf("Select Menu: ");

                // 사용자가 입력한 숫자 받기
                int choice = scanner.nextInt();

                // 메뉴 선택에 따른 분기 처리
                switch (choice){
                    case 1:  // SELECT 구현
                        System.out.println("\n### Viewing device list...");

                        // SELECT 쿼리 실행 후 결과 받아오기
                        String selectSql = "SELECT * FROM SmartDevice ORDER BY id;";
                        PreparedStatement selectPstmt = conn.prepareStatement(selectSql);
                        ResultSet rs = selectPstmt.executeQuery();

                        System.out.println("--------------------------------------------");
                        System.out.println(" ID |         Model         |    Price    |");
                        System.out.println("--------------------------------------------");

                        // ResultSet에서 레코드 하나씩 순회하며 결과 출력하기
                        while(rs.next()){
                            int id = rs.getInt("id");
                            String name = rs.getString("name");
                            int price = rs.getInt("price");

                            System.out.printf(" %-2d | %-21s | %d \n", id, name, price);
                        }
                        System.out.println("--------------------------------------------");

                        // 사용이 끝난 자원 반납
                        rs.close();
                        selectPstmt.close();
                        break;

                    case 2:  // INSERT 구현
                        System.out.println("\n### Adding new device...");

                        // 사용자로부터 기기 정보 입력 받기
                        System.out.print("Enter New ID: ");
                        int newId = scanner.nextInt();
                        scanner.nextLine();  // 엔터키 찌꺼기 비워주기

                        System.out.print("Enter Model Name: ");
                        String newName = scanner.nextLine();

                        System.out.print("Enter Price: ");
                        int newPrice = scanner.nextInt();

                        // INSERT 쿼리
                        String insertSql = "INSERT INTO SmartDevice (id, name, price) VALUES (?, ?, ?);";
                        PreparedStatement insertPstmt = conn.prepareStatement(insertSql);

                        insertPstmt.setInt(1, newId);
                        insertPstmt.setString(2, newName);
                        insertPstmt.setInt(3, newPrice);

                        int insertResult = insertPstmt.executeUpdate();
                        System.out.println("Success: " + insertResult + " device added!");

                        // 사용한 자원 반납
                        insertPstmt.close();
                        break;

                    case 3:  // UPDATE 구현
                        System.out.println("\n### Updating device price...");

                        // 수정할 기기의 ID와 새로운 가격 입력 받기
                        System.out.print("Enter Target ID: ");
                        int targetId = scanner.nextInt();

                        System.out.print("Enter New Price: ");
                        int updatePrice = scanner.nextInt();

                        // UPDATE 쿼리
                        String updateSql = "UPDATE SmartDevice SET price = ? WHERE id = ?;";
                        PreparedStatement updatePstmt = conn.prepareStatement(updateSql);

                        updatePstmt.setInt(1, updatePrice);
                        updatePstmt.setInt(2, targetId);

                        int updateResult = updatePstmt.executeUpdate();
                        if(updateResult > 0) {
                            System.out.println("Success: Price Updated!");
                        } else {
                            System.out.println("Fail: ID not found");
                        }

                        // 사용한 자원 반납
                        updatePstmt.close();
                        break;

                    case 4:  // DELETE 구현
                        System.out.println("\n### Deleting device...");

                        System.out.print("Enter Target ID to Delete: ");
                        int deleteId = scanner.nextInt();

                        // DELETE 쿼리
                        String deleteSql = "DELETE FROM SmartDevice WHERE id = ?;";
                        PreparedStatement deletePstmt = conn.prepareStatement(deleteSql);
                        deletePstmt.setInt(1, deleteId);

                        int deleteResult = deletePstmt.executeUpdate();
                        if(deleteResult > 0){
                            System.out.println("Success: Device Deleted!");
                        } else {
                            System.out.println("Fail: ID not found");
                        }

                        // 사용한 자원 반납
                        deletePstmt.close();
                        break;

                    case 0:
                        System.out.println("\n### Exiting system... Good bye!");
                        isRunning = false;  // 루프 종료 조건
                        break;
                    default:
                        System.out.println("\nInvalid input! Please try again!");
                }
            }

            // 프로그램 종료시 자원 반납
            conn.close();
            scanner.close();

        } catch (SQLException e) {
            // 데이터베이스 연결 실패시
            System.out.println("\nERROR: DB Connection Failed!");
            System.out.println("Details: " + e.getMessage());
        }
    }
}
