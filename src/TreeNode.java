import java.util.List;

public class TreeNode {
    private List expression;
    private Double value;
    private char operator;
    private int splitPosition;
    private  TreeNode leftChild;
    private TreeNode rightChild;
    private OperatorPriority[] operatorPriorities;

    public TreeNode(List expression, OperatorPriority[] operatorPriorities) {
        this.expression = expression;
        this.operatorPriorities = operatorPriorities;

    }

    public TreeNode(List expression) {
        this.expression = expression;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setOperatorPriorities(OperatorPriority[] operatorPriorities) {
        this.operatorPriorities = operatorPriorities;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }


    public void setOperator(char operator) {
        this.operator = operator;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public List getExpression() {
        return expression;
    }

    public Double getValue() {
        return value;
    }

    public char getOperator() {
        return operator;
    }

    public OperatorPriority[] getOperatorPriorities() {
        return operatorPriorities;
    }
}
