class Problem {
    public static void main(String[] args) {
        final String paramName = "mode";
        String paramValue = "default";
        for (int i = 0; i < args.length - 1; i += 2) {
            if (args[i].equalsIgnoreCase("mode")) {
                paramValue = args[i + 1];
                break;
            }
        }
        System.out.println(paramValue);
    }
}