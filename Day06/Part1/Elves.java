public class Elves {
    public static void main(String[] args) {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            System.out.println("Give the length of the signal window to look for as the argument as a positive integer.");
        } else {
            int num = Integer.parseInt(args[0]);
            Signal s = new Signal("input.txt");
            System.out.println(s.findStartOfPacket(num));
        }
    }
}
