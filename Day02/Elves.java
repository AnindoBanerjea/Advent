public class Elves {
    public static void main(String[] args) {
        ElfTournament t = new ElfTournament("input.txt");
        int totalscore = 0;
        for (MovePair round : t.getRounds()) {
            totalscore += round.getMyMove().score(round.getOppMove());
        }
        System.out.println("Total score = " + totalscore);
    }  
}
