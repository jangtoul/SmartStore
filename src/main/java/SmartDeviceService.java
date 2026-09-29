import java.util.ArrayList;

public class SmartDeviceService {
    // 매니저가 주방장(DAO)를 사용할 수 있어야 한다
    private SmartDeviceDAO dao = new SmartDeviceDAO();

    // 1. READ(조회) 검사 후 DAO에게 지시: 굳이 검사할 게 없으면 패스
    public ArrayList<SmartDevice> getAllDevices(){
        return dao.getAllDevices();
    }

    // 2. CREATE(삽입) 검사 후 DAO에게 지시
    public void addDevice(SmartDevice device){
        // 마이너스 가격 방지 로직
        if(device.getPrice() < 0) {
            System.out.println("ERROR: Price must be zero or higher");

            // 주방장에게 넘어가지 않고 여기서 메서드 종료
            return;
        }

        int result = dao.insertDevice(device);
        if(result > 0){
            System.out.println("Success: Device added!");
        }
    }

    // 3. UPDATE(수정) 검사 후 DAO에게 지시
    public void updateDevice(int id, int price){
        if(price < 0){
            System.out.println("ERROR: Price must be zero or higher");
            return;
        }

        int result = dao.updateDevice(id, price);

        if(result > 0){
            System.out.println("Success: Price Updated!");
        } else {
            System.out.println("Fail: ID not found");
        }
    }

    // 4. DELETE(삭제) 검사 후 DAO에게 지시
    public void deleteDevice(int id){
        int result = dao.deleteDevice(id);

        if(result > 0){
            System.out.println("Success: Device deleted!");
        } else {
            System.out.println("Fail: ID not found");
        }
    }
}
