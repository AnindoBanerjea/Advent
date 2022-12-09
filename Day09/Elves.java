public class Elves {
    public static void main(String[] args) {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            System.out.println("Give the length of the snake as the argument.");
        } else {
            int length = Integer.parseInt(args[0]);
            System.out.println("Solving for snake length " + length + ".");
            Moves f = new Moves("input.txt", length);
            System.out.println(f.getVisited().size());
        }
    }
}
