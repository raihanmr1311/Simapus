package views;


import modules.Auth;
import modules.View;
import utils.Console;

public class AuthView extends View {
    public void login() {

        String username = null;
        String password = null;

        System.out.println("Silahkan Login");

        System.out.print("Username    : ");
        username = this.scan.next();

        System.out.print("Password    : ");
        password = this.scan.next();

        if (username.equals("admin") && password.equals("admin")) {
            Auth.userId = -1;
        } else if (!username.equals(password)) {
            Console.clear();
            login();
        } else {
            Auth.userId = Auth.login(username);
        }

        if (Auth.userId == 0) {
            Console.clear();
            login();
        }
    }
}
