import java.util.*;


public class Trax {
    public static void main(String[] args) {

        ArrayList<String> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Trax");
        System.out.println("What can I do for you?\n");

        String input = "";
        while (true) {
            input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (input.equals("list")) {
                for(int i = 1; i<= tasks.size(); i++) {
                    System.out.println(String.format("%d. %s", i, tasks.get(i-1)));
                }
            } else {
                tasks.add(input);
                System.out.println("added: " + input);
            }

        }

    }
}
