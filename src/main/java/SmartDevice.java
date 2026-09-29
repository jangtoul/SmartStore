// DTO 클래스
public class SmartDevice {

    // 데이터 보호를 위한 private 필드
    private int id;
    private String name;
    private int price;

    // 생성자
    public SmartDevice(){

    }

    public SmartDevice(int id, String name, int price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getter와 Setter
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int price){
        this.price = price;
    }
}
