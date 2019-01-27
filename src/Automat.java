import java.util.HashMap;
import java.util.Map;

public class Automat {
    private Map<Integer, Map<Character, Integer>> steps;
    private String string;
    private int numberOfLetter;
    private boolean isOnFinish;
    private int numberOfBrace;
    private int nextStep;
    private boolean isExpresion;

    public Automat (String string) {
        this.string = string;
        numberOfLetter = 0;
        steps = new HashMap<>();
        numberOfLetter = 0;
        isOnFinish = false;
        nextStep = 0;
        isExpresion = false;
    }

    public void addStep(int fromStep, char symbol, int toStep) {
        if (steps.containsKey(fromStep))
            steps.get(fromStep).put(symbol, toStep);
        else {
            steps.put(fromStep, new HashMap<>());
            steps.get(fromStep).put(symbol, toStep);
        }
    }

    public boolean manager(){
        Start();
        return isExpresion;
    }

    private void Start() {
        nextStep = nextStep(0);
        if (nextStep == 1)
            Finish();
        else if (nextStep == 6)
            Sixth();
        else if (nextStep == 3)
            Third();
    }

    private void Finish() {
        isOnFinish = true;
        nextStep = nextStep(1);
        if (nextStep == 1)
            Finish();
        else if (nextStep == 2)
            Second();
        else if (nextStep == 4)
            Fourth();
    }

    private void Second() {
        nextStep = nextStep(2);
        isOnFinish = false;
        if (nextStep == 1)
            Finish();
        else if (nextStep == 3)
            Third();
    }

    private void Third() {
        numberOfBrace++;
        Start();
    }

    private void Fourth() {
        isOnFinish = false;
        nextStep = nextStep(4);
        if (nextStep == 5)
            Fifth();
    }

    private void Fifth() {
        numberOfBrace--;
        isOnFinish = true;
        nextStep = nextStep(5);
        if (nextStep == 2)
            Second();
    }

    private void Sixth() {
        nextStep = nextStep(6);
        if (nextStep == 1)
            Finish();
        else if (nextStep == 3)
            Third();
    }

    private int nextStep(int currentStep) {
        if (numberOfLetter < string.length()) {
            if (steps.get(currentStep).containsKey(currentLetter())) {
                int nextStep = steps.get(currentStep).get(currentLetter());
                numberOfLetter++;
                return nextStep;
            }
            else if (currentStep == 1 || currentStep == 5)
                return 4;
            else {
                isExpresion = false;
                return -1;
            }
        }
        else {
            isExpresion = (isOnFinish && numberOfBrace == 0);
            return -1;
        }
    }

    private char currentLetter() {
        return string.charAt(numberOfLetter);
    }
}


