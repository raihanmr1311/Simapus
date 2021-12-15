import modules.Auth;
import utils.Console;
import utils.Input;
import views.AdminView;
import views.AuthView;
import views.StudentView;

public class Main {

    private static Input input;
    private static AuthView authView = new AuthView();
    private static AdminView adminView = new AdminView();
    private static StudentView studentView = new StudentView();

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        input = new Input();

        System.out.println("SIMAPUS");
        authView.login();

        Console.clear();
        if (Auth.userId == -1) {
            adminMenu();
        } else {
            siswaMenu();
        }
    }

    private static void adminMenu() {
        System.out.println("Menu admin");
        System.out.println("1. Lihat booking");
        System.out.println("2. Konfirmasi booking");
        System.out.println("3. Lihat peminjaman berdasarkan NIS");
        System.out.println("4. Tambah buku baru");
        System.out.println("5. Logout");
        System.out.print("=> ");

        int option = input.getNumber(1, 5);
        Console.clear();
        switch (option) {
            case 1:
                adminView.showBooking();
                break;
            case 2:
                adminView.acceptBooking();
                break;
            case 3:
                adminView.listPeminjamanByNIS();
                break;
            case 4:
                adminView.inpBook();
                break;
            case 5:
                init();
                break;
        }
        adminMenu();
    }

    private static void siswaMenu() {
        System.out.println("Menu siswa");
        System.out.println("1. Booking buku");
        System.out.println("2. List peminjaman");
        System.out.println("3. Logout");
        System.out.print("=> ");

        int option = input.getNumber(1, 3);
        Console.clear();

        switch (option) {
            case 1:
                studentView.book();
                break;
            case 2:
                studentView.listPeminjaman();
                break;
            case 3:
                init();
        }
        siswaMenu();
    }
}
