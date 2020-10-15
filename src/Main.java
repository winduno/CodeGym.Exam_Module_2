import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static ArrayList<Contact> contacts = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static String input = "";
    static int index = -1;
    static int anchorIndex = 0;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
    private static final String PHONE_REGEX = "^0\\d{3}\\s\\d{3}\\s\\d{3}";

    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu(){
        System.out.println("\n---CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ---");
        System.out.println("Chọn chức năng theo số (để tiếp tục):");
        System.out.println("1. Xem danh sách");
        System.out.println("2. Thêm mới");
        System.out.println("3. Cập nhật");
        System.out.println("4. Xóa");
        System.out.println("5. Tìm kiếm");
        System.out.println("6. Đọc từ file");
        System.out.println("7. Ghi vào file");
        System.out.println("8. Thoát");
        System.out.print("Chọn chức năng: ");
        input = scanner.nextLine();
        try {
            int choice = Integer.parseInt(input);
            chosenMenu(choice);

        } catch (Exception e){
            System.err.println("Nhập số thôi nhé các bạn!!");
            mainMenu();
        }
    }

    public static void chosenMenu(int choice){
        switch (choice){
            case 1:
                showContact();
                break;
            case 2:
                addNewContact();
                break;
            case 3:
                updateContact();
                break;
            case 4:
                deleteContact();
                break;
            case 5:
                searchContact();
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                System.exit(-1);
                break;
            default:
                System.exit(0);
                break;
        }
    }

    public static void showContact(){
        int currentIndex;
        do {
            currentIndex = 0;
            if (anchorIndex == contacts.size()) break;
            for (int i = anchorIndex; i < contacts.size(); i++) {
                System.out.println(contacts.get(i).toString());
                currentIndex++;
                anchorIndex = i;
            }
        } while (currentIndex < 6);
    }

    public static void addNewContact(){
        Contact contact = new Contact();
        System.out.println("\n---THÊM MỚI DANH BẠ---");
        do{
            System.out.println("--Số điện thoại: (theo dạng 0xxx xxx xxx)");
            contact.setPhoneNumber(scanner.nextLine());
        } while (validPhoneNumber(contact.getPhoneNumber()) == false);

        do {
            System.out.println("--Nhóm danh bạ: ");
            contact.setGroup(scanner.nextLine());
        } while (contact.getGroup().equals(""));

        do{
            System.out.println("--Họ tên: ");
            contact.setName(scanner.nextLine());
        } while (contact.getName().equals(""));

        do {
            System.out.println("--Giới tính: ");
            contact.setGender(scanner.nextLine());
        } while (contact.getGender().equals(""));

        do {
            System.out.println("--Địa chỉ: ");
            contact.setAddress(scanner.nextLine());
        } while (contact.getAddress().equals(""));

        do {
            System.out.println("--Ngày sinh: ");
            contact.setDateOfBirth(scanner.nextLine());
        } while (contact.getDateOfBirth().equals(""));

        do{
            System.out.println("--Email: ");
            contact.setEmail(scanner.nextLine());
        }while (validEmail(contact.getEmail()) == false);

        contacts.add(contact);
        System.out.println("Thêm mới danh bạ thành công!!");
        mainMenu();
    }

    public static void updateContact(){
        System.out.println("\n---CẬP NHẬT DANH BẠ---");
        findContact();

        if (index > -1){
            if (contacts.get(index).getPhoneNumber().equals(input)){
                do {
                    System.out.println("--Nhóm danh bạ: ");
                    contacts.get(index).setGroup(scanner.nextLine());
                } while (contacts.get(index).getGroup().equals(""));

                do{
                    System.out.println("--Họ tên: ");
                    contacts.get(index).setName(scanner.nextLine());
                } while (contacts.get(index).getName().equals(""));

                do {
                    System.out.println("--Giới tính: ");
                    contacts.get(index).setGender(scanner.nextLine());
                } while (contacts.get(index).getGender().equals(""));

                do {
                    System.out.println("--Địa chỉ: ");
                    contacts.get(index).setAddress(scanner.nextLine());
                } while (contacts.get(index).getAddress().equals(""));

                do {
                    System.out.println("--Ngày sinh: ");
                    contacts.get(index).setDateOfBirth(scanner.nextLine());
                } while (contacts.get(index).getDateOfBirth().equals(""));

                do{
                    System.out.println("--Email: ");
                    contacts.get(index).setEmail(scanner.nextLine());
                }while (validEmail(contacts.get(index).getEmail()) == false);
                System.out.println("Cập nhật thành công!!");
            }
        }
        mainMenu();
    }

    public static void deleteContact(){
        System.out.println("\n---XÓA DANH BẠ---");
        findContact();
        System.out.println("Nhập Y để xóa");
        if (scanner.nextLine().toLowerCase().equals("y")){
            contacts.remove(index);
        }
        mainMenu();
    }

    public static void findContact(){
        boolean found = false;
        do {
            System.out.println("--Nhập số điện thoại: ");
            input = scanner.nextLine();
            if (input.equals("")){
                mainMenu();
            }

            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getPhoneNumber().equals(input)){
                    index = i;
                    found = true;
                }
                else {
                    index = -1;
                }
            }
            if (found == false) System.out.println("Không tìm được số điện thoại đã nhập!!");
        } while (found == false);
    }

    public static void searchContact(){
        System.out.println("\n---TÌM DANH BẠ---");
        System.out.println("Nhập số điện thoại hoặc tên cần tìm: ");
        input = scanner.nextLine();
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().contains(input) || contact.getName().toLowerCase().contains(input.toLowerCase()))
                System.out.println(contact.toString());
        }
        mainMenu();
    }

    public static boolean validEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean validPhoneNumber(String phoneNumber){
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()){
            return true;
        }
        else{
            return false;
        }
    }
}
