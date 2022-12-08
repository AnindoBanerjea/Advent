public class Elves {
    public static void main(String[] args) {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            System.out.println("Give 1 or 2 as the argument, based on which part you are solving.");
        } else {
            int num = Integer.parseInt(args[0]);
            DirectoryTree s = new DirectoryTree("input.txt");
            s.getRoot().computeSize();
            if (num == 1) {
                System.out.println(s.getRoot().sumOfDirectoriesBelow(100000));
            } else {
                int requiredSpace = 30000000;
                int availableSpace = 70000000 - s.getRoot().getSize();
                int need = requiredSpace - availableSpace;
                System.out.println(s.getRoot().smallestDirectoryAbove(need));
            }
        }
    }
}
