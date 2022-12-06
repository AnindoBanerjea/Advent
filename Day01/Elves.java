import java.util.*;
public class Elves {
    public static void main(String[] args) {
        Expedition e = new Expedition("input.txt");
        Podium max = new Podium();
        for (ArrayList<Integer> elf : e.getElves()) {
            int elfTotal = 0;
            for (Integer food : elf) {
                elfTotal += food;
            }
            max.compare(elfTotal);
        }
        System.out.println(max.champ() + " " + max.total());
    }
}


