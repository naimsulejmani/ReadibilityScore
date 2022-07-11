
class Problem {
    public static void main(String[] args) {
        if (args.length != 3) {
            return;
        }
        String operator = args[0];
        int number1 = Integer.parseInt(args[1]);
        int number2 = Integer.parseInt(args[2]);

        switch (operator) {
            case "+":
                System.out.println(number1 + number2);
                break;
            case "-":
                System.out.println(number1 - number2);
                break;
            case "*":
                System.out.println(number1 * number2);
                break;
            default:
                System.out.println("Unknown operator");
                break;
        }

    }
}