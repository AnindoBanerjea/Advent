public class Elves {
    public static void main(String[] args) {
        StacksAndPlan sap = new StacksAndPlan("input.txt");
        for (Move m : sap.getPlan().getPlan()) {
            sap.getStacks().move(m);
        }
        System.out.println(sap.getStacks().topOfStacks());
    }
}
