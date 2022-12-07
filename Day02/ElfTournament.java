import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class ElfTournament {
   
    
    private ArrayList<MovePair> rounds;

    public ElfTournament(String filename) {
        this.rounds = new ArrayList<MovePair>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));

            for (String line : lines) {
                Move oppMove = new Move((int)(line.charAt(0)-'A'));
                int target = (int)(line.charAt(2)-'X'-1);
                Move myMove = oppMove.predict(target);
                MovePair currentRound = new MovePair(oppMove, myMove);    
                this.rounds.add(currentRound);
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MovePair> getRounds() {
        return this.rounds;
    }

}
