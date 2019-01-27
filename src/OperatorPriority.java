public class OperatorPriority {
    private char operator;
    private int priority;
    private int startPosition;

    public OperatorPriority( int priority, int position, char operator) {
        this.priority = priority;
        this.startPosition = position;
        this.operator = operator;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getPriority() {
        return priority;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public char getOperator() {
        return operator;
    }
}
