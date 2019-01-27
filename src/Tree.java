import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tree {
    private List<String> listOfExpressions;
    private String expression;
    private int actionCount;
    private OperatorPriority operatorPriorityMass[];
    private TreeNode root;

    public Tree(String expression) {
        this.expression = expression;
        listOfExpressions = new ArrayList<>();
        actionCount = 0;
    }

    public double answer() {
        convertingToList();
        deleteStupidBrackets();
        createPriorityMass();
        if (operatorPriorityMass.length > 0) {
            root = new TreeNode(listOfExpressions, operatorPriorityMass);

            createTree(root);
            countTree(root);
            return root.getValue();
        }
        else return Double.parseDouble(listOfExpressions.get(0));
    }


    private void convertingToList() {           //Переводим строку в список
        if (expression.charAt(0) == '-')
            listOfExpressions.add("0");         //Добавляем ноль перед минусом
        for (int i = 0; i < expression.length(); i++) {
            Character symbol = expression.charAt(i);
            if (isAction(symbol)) {
                listOfExpressions.add(symbol.toString());
                actionCount++;
            } else if (isBracket(symbol)) {
                if (symbol == '(' && expression.charAt(i+1) == '-') {
                        listOfExpressions.add(symbol.toString());
                        listOfExpressions.add("0");         //Добавляем ноль перед минусом
                    }
                else listOfExpressions.add(symbol.toString());
            }
            else {
                int lustNumber = isNumber(i);
                listOfExpressions.add(expression.substring(i, lustNumber));
                i = lustNumber - 1;             // переводим итератор на следующий символ после числа
            }
        }
    }

    private void createPriorityMass() {          //Создаем массив операций с их приоритетом
        int priority = 0;                       //Приоретет высчитывается (10 - за скобку; 2 - умножение/деление; 1 - сложение/вычитаение)
        operatorPriorityMass = new OperatorPriority[actionCount];
        int j = 0;
        for (int i = 0; i < listOfExpressions.size(); i++) {
            char symbol = listOfExpressions.get(i).charAt(0);
            if (symbol == '(')
                priority += 10;
            else if (symbol == ')')
                priority -= 10;
            else if (symbol == '+' || symbol == '-') {
                operatorPriorityMass[j] = new OperatorPriority(priority + 1, i, listOfExpressions.get(i).charAt(0));
                j++;
            } else if (symbol == '*' || symbol == '/') {
                operatorPriorityMass[j] = new OperatorPriority(priority + 2, i, listOfExpressions.get(i).charAt(0));
                j++;
            }
        }
    }


    private void createTree(TreeNode treeNode) {

        System.out.println();
        System.out.println(treeNode.getExpression());
        if (isContainsExpression(treeNode)) {
            int splitPriorityMas = findMinPriority(treeNode.getOperatorPriorities());
            int splitExpression = treeNode.getOperatorPriorities()[splitPriorityMas].getStartPosition();
            treeNode.setOperator(treeNode.getOperatorPriorities()[splitPriorityMas].getOperator());
            treeNode.setLeftChild(new TreeNode(treeNode.getExpression().subList(0, splitExpression)));
            treeNode.setRightChild(new TreeNode(treeNode.getExpression().subList(splitExpression + 1, treeNode.getExpression().size())));

            if (splitPriorityMas > 0) {
                OperatorPriority[] leftChildMas = createLeftChildOperatorPrioritiesMas(splitPriorityMas, treeNode.getOperatorPriorities());
                treeNode.getLeftChild().setOperatorPriorities(leftChildMas);
            }
            if (splitPriorityMas < treeNode.getOperatorPriorities().length - 1) {
                OperatorPriority[] rightChildMas = createRightChildOperatorPrioritiesMas(splitPriorityMas + 1, treeNode.getOperatorPriorities().length, treeNode.getOperatorPriorities());
                treeNode.getRightChild().setOperatorPriorities(rightChildMas);
            }

                createTree(treeNode.getLeftChild());
                createTree(treeNode.getRightChild());

        }

        else if (treeNode.getExpression().get(0).toString().charAt(0) == '(')
            treeNode.setValue(Double.parseDouble(treeNode.getExpression().get(treeNode.getExpression().size() - 1).toString()));
        else
            treeNode.setValue(Double.parseDouble(treeNode.getExpression().get(0).toString()));

    }

    private OperatorPriority[] createRightChildOperatorPrioritiesMas(int start, int end, OperatorPriority[] operatorPriorities) {
        OperatorPriority[] childMas = new OperatorPriority[end - start];
        for (int i = 0; i < childMas.length; i++) {
            childMas[i] = operatorPriorities[i + start];
            childMas[i].setStartPosition(childMas[i].getStartPosition() - operatorPriorities[start - 1].getStartPosition() - 1);
        }
        return childMas;
    }

    private OperatorPriority[] createLeftChildOperatorPrioritiesMas(int end, OperatorPriority[] operatorPriorities) {
        OperatorPriority[] childMas = new OperatorPriority[end];
        System.arraycopy(operatorPriorities, 0, childMas, 0, end);
        return childMas;
    }

    private int findMinPriority(OperatorPriority[] operatorPriorities) {
        OperatorPriority temp = operatorPriorities[0];
        int tempNumber = 0;
        if (operatorPriorities.length > 1)
            for (int i = 1; i < operatorPriorities.length; i++)
                if (operatorPriorities[i].getPriority() <= temp.getPriority()) {
                    temp = operatorPriorities[i];
                    tempNumber = i;
            }
            return tempNumber;
    }

    private void deleteStupidBrackets() {
        for (int i = 0; i <= listOfExpressions.size() - 3; i++) {
            if (listOfExpressions.get(i).charAt(0) == '(' && listOfExpressions.get(i + 1).charAt(0) != '(' &&
                    listOfExpressions.get(i + 2).charAt(0) == ')') {
                listOfExpressions.remove(i + 2);
                listOfExpressions.remove(i);
            }
        }
        
    }

    private void countTree(TreeNode treeNode) {
        if (treeNode.getLeftChild().getValue() == null)
            countTree(treeNode.getLeftChild());
        if (treeNode.getRightChild().getValue() == null)
            countTree(treeNode.getRightChild());
         countValue(treeNode);

    }

    private void countValue(TreeNode treeNode) {
        switch (treeNode.getOperator()) {
            case '+':
                treeNode.setValue(treeNode.getLeftChild().getValue() + treeNode.getRightChild().getValue());
                break;
            case '-':
                treeNode.setValue(treeNode.getLeftChild().getValue() - treeNode.getRightChild().getValue());
                break;
            case '*':
                treeNode.setValue(treeNode.getLeftChild().getValue() * treeNode.getRightChild().getValue());
                break;
            case '/':
                treeNode.setValue(treeNode.getLeftChild().getValue() / treeNode.getRightChild().getValue());
                break;
            default:
                System.out.println("Ошибка");
                break;
        }
    }

    private boolean isContainsExpression(TreeNode treeNode) {
        return (treeNode.getExpression().toString().contains("+") || treeNode.getExpression().toString().contains("-") ||
                treeNode.getExpression().toString().contains("*") || treeNode.getExpression().toString().contains("/"));
    }
    private boolean isAction(char symbol) {
        return (symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/');
    }

    private boolean isBracket(char symbol) {
        return (symbol == '(' || symbol == ')');
    }

    private int isNumber(int i) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(expression.substring(i));
        if (m.lookingAt())
            return m.end() + i;

        else
            return -1;
    }


}

