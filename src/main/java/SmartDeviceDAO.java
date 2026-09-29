import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SmartDeviceDAO {
    // 데이터베이스 접속 정보
    private String url = "jdbc:mysql://localhost:3306/shop";
    private String user = "root";
    private String password = "*****";

    // Connection(파이프)를 만들어주는 전용 메서드
    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    // READ: 모든 기기 목록 조회(리턴 타입은 여러 개의 DTO를 담은 ArrayList)
    public ArrayList<SmartDevice> getAllDevices(){
        // 빈 ArrayList 준비
        ArrayList<SmartDevice> deviceList = new ArrayList<>();

        // SELECT 쿼리 준비
        String sql = "SELECT * FROM SmartDevice;";

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");

                // 가져온 데이터들을 하나의 DTO로 포장
                SmartDevice device = new SmartDevice(id, name, price);

                // DTO를 ArrayList에 하나씩 차곡차곡 쌓기
                deviceList.add(device);
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("ERROR: SELECT Failed!");
            System.out.println(e.getMessage());
        }

        // DTO 묶음들이 가득 담긴 ArrayList를 반환
        return deviceList;
    }

    // CREATE: 새로운 기기 추가(DTO 상자를 통째로 받음)
    public int insertDevice(SmartDevice device){
        int result = 0;
        String sql = "INSERT INTO SmartDevice (id, name, price) VALUES (?, ?, ?);";

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // DTO 안에 들어있는 데이터를 Getter 메서드로 꺼내서 매핑
            pstmt.setInt(1, device.getId());
            pstmt.setString(2, device.getName());
            pstmt.setInt(3, device.getPrice());

            result = pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (SQLException e){
            System.out.println("ERROR: INSERT Failed!");
            System.out.println(e.getMessage());
        }

        return result;
    }

    // UPDATE: 기기 가격 수정
    public int updateDevice(int id, int price){
        int result = 0;
        String sql = "UPDATE SmartDevice SET price = ? WHERE id = ?;";

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, price);
            pstmt.setInt(2, id);

            result = pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (SQLException e){
            System.out.println("ERROR: UPDATE Failed!");
            System.out.println(e.getMessage());
        }

        return result;
    }

    // DELETE: 기기 삭제
    public int deleteDevice(int id){
        int result = 0;
        String sql = "DELETE FROM SmartDevice WHERE id = ?;";

        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            result = pstmt.executeUpdate();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("ERROR: DELETE Failed!");
            System.out.println(e.getMessage());
        }

        return result;
    }
}
