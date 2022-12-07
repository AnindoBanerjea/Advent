public class Elves {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].matches("[12]")) {
            System.out.println("Give 1 or 2 as the argument, based on which part you are solving.");
        } else {
            int part = Integer.parseInt(args[0]);
            ElfTournament t = new ElfTournament("input.txt", part);
            int totalscore = 0;
            for (MovePair round : t.getRounds()) {
                totalscore += round.getMyMove().score(round.getOppMove());
            }
            System.out.println("Total score = " + totalscore);
        }
    }  
}
