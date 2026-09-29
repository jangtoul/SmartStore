import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SmartStoreApp {
    public static void main(String[] args){
        // 사용자 입력을 받기 위한 스캐너 객체 생성
        Scanner scanner = new Scanner(System.in);

        // 프로그램 실행 상태를 제어하는 변수
        boolean isRunning = true;

        // 홀 직원은 이제 매니저(Service)하고 소통해야 한다 (Main - Service - DAO - DB)
        SmartDeviceService service = new SmartDeviceService();

        System.out.println("===================================");
        System.out.println("   SmartStore Management System   ");
        System.out.println("===================================");

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

                    // Service에게 전제 기기 목록(DTO 묶음) 가져오라고 시키기
                    ArrayList<SmartDevice> list = service.getAllDevices();

                    System.out.println("--------------------------------------------");
                    System.out.println(" ID |         Model         |    Price    |");
                    System.out.println("--------------------------------------------");

                    // 리스트에 있는 DTO를 하나씩 꺼내오기
                    for(SmartDevice device : list){
                        // DTO 안에 숨겨진 데이터는 Getter 메서드로 꺼내온다
                        System.out.printf(" %-2d | %-21s | %d \n",
                                device.getId(),
                                device.getName(),
                                device.getPrice());
                    }

                    System.out.println("--------------------------------------------");

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

                    // DTO 하나에 데이터들을 예쁘게 포장
                    SmartDevice device = new SmartDevice(newId, newName, newPrice);

                    // 포장된 DTO를 Service에게 전달하여 데이터베이스에 삽입
                    service.addDevice(device);
                    break;

                case 3:  // UPDATE 구현
                    System.out.println("\n### Updating device price...");

                    // 수정할 기기의 ID와 새로운 가격 입력 받기
                    System.out.print("Enter Target ID: ");
                    int targetId = scanner.nextInt();

                    System.out.print("Enter New Price: ");
                    int updatePrice = scanner.nextInt();

                    // Service에게 ID와 새로운 가격을 넘겨주며 수정을 지시
                    service.updateDevice(targetId, updatePrice);
                    break;

                case 4:  // DELETE 구현
                    System.out.println("\n### Deleting device...");

                    System.out.print("Enter Target ID to Delete: ");
                    int deleteId = scanner.nextInt();

                    // Service에게 삭제할 ID를 넘겨주며 삭제를 지시
                    service.deleteDevice(deleteId);
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
        scanner.close();
    }
}
