import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        System.out.println("Введите выражение:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String expresion = reader.readLine();
        expresion = expresion.replaceAll(" ", "");
        Automat automat = new Automat(expresion);
        addSteps(automat);
        if (automat.manager()) {
            Tree tree = new Tree(expresion);
            System.out.println("Ответ: " + tree.answer());
        }
        else System.out.println("Это не выражение");
    }

    private static void addSteps(Automat automat) {
        for (char letter = '1'; letter <= '9'; letter++) {
            automat.addStep(0, letter, 1);
            automat.addStep(6, letter, 1);
            automat.addStep(2, letter, 1);
            automat.addStep(1, letter, 1);
        }
        automat.addStep(1, '0', 1);

        List<Character> actions = Arrays.asList('*', '/', '-', '+');
        for (int i = 0; i < actions.size(); i++) {
            automat.addStep(1, actions.get(i), 2);
            automat.addStep(5, actions.get(i), 2);
        }
        automat.addStep(0, '-', 6);

        automat.addStep(0, '(', 3);
        automat.addStep(2, '(', 3);
        automat.addStep(6, '(', 3);
        automat.addStep(4, ')', 5);
    }
}
