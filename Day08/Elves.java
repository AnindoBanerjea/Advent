public class Elves {
    public static void main(String[] args) {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            System.out.println("Give 1 or 2 as the argument, based on which part you are solving.");
        } else {
            int part = Integer.parseInt(args[0]);
            System.out.println("Executing part " + part + ".");
            Forest f = new Forest("input.txt");
            if (part == 1) {
                System.out.println(f.countVisible());
            } else {
                System.out.println(f.topViewingScore());
            }
        }
    }
}
