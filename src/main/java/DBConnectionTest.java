import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnectionTest {
    public static void main(String[] args){
        // 데이터베이스 주소, 아이디, 비밀번호 설정 (파이프 목적지)
        String url = "jdbc:mysql://localhost:3306/shop";
        String user = "root";
        String password = "*****";

        System.out.println("DB Connecting...");

        // 파이프 연결 시도 및 예외 처리
        try {
            // DriveManager가 통역기를 통해 파이프를 뚫어주는 역할
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Success: DB Connecting!");

            // SELECT 쿼리 쏘고 결과 읽기
            String sql = "SELECT * FROM SmartDevice;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("--- SmartStore Device List ---");

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");

                System.out.println("ID: " + id + " | model: " + name + " | price: " + price);
            }

            // INSERT 쿼리
            String insertSql = "INSERT INTO SmartDevice (id, name, price) VALUES (?, ?, ?);";
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, 8);
            pstmt.setString(2, "MacBook Air");
            pstmt.setInt(3, 1400000);

            int result = pstmt.executeUpdate();
            System.out.println("Insert Success: " + result);

            // UPDATE 쿼리
            String updateSql = "UPDATE SmartDevice SET price = ? WHERE id = ?;";
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, 1350000);
            pstmt.setInt(2, 8);

            int updateResult = pstmt.executeUpdate();
            System.out.println("Update Success: " + updateResult + " row(s) affected");

            // DELETE 쿼리
            String deleteSql = "DELETE FROM SmartDevice WHERE id = ?;";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, 8);

            int deleteResult = pstmt.executeUpdate();
            System.out.println("Delete Success: " + deleteResult + " row(s) removed");

            // 사용한 파이프 자원 반납하기
            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
