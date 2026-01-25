import java.util.Scanner;


public class Trax {
    public static void main(String[] args) {

        //String[] tasks =  new String[100];
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Trax");
        System.out.println("What can I do for you?\n");

        String input = "";
        while (true) {
            input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            System.out.println(input);
        }

    }
}
