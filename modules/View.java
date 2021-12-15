package modules;

import java.util.Scanner;

import utils.Input;

public abstract class View {
    public Input input;
    public Scanner scan;

    public View() {
        input = new Input();
        scan = new Scanner(System.in);
    }
}
