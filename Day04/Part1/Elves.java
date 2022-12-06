public class Elves {
    public static void main(String[] args) {
        AssignmentPairList apl = new AssignmentPairList("input.txt");
        int num = 0;
        for (RangePair rp : apl.getRangePairs()) {
            System.out.println(rp.getRange1().getLo() + "-" + rp.getRange1().getHi() + "," + rp.getRange2().getLo() + "-" + rp.getRange2().getHi());
            if (((rp.getRange1().getLo() >= rp.getRange2().getLo()) &&
                 (rp.getRange1().getHi() <= rp.getRange2().getHi())) ||
                 ((rp.getRange2().getLo() >= rp.getRange1().getLo()) &&
                 (rp.getRange2().getHi() <= rp.getRange1().getHi()))) {
                    num++;
                    System.out.println("Found one");
                 }
        }
        System.out.println(num);
    }
}
