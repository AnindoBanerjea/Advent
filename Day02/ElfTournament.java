import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class ElfTournament {
   
    
    private final List<MovePair> rounds;

    public ElfTournament(String filename, int part) {
        this.rounds = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));

            for (String line : lines) {
                Move oppMove = new Move(line.charAt(0)-'A');
                Move myMove;
                if (part == 1) {
                    myMove = new Move(line.charAt(2)-'X');
                } else {
                    int target = line.charAt(2)-'X'-1;
                    myMove = oppMove.predict(target);
                }
                MovePair currentRound = new MovePair(oppMove, myMove);    
                this.rounds.add(currentRound);
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MovePair> getRounds() {
        return this.rounds;
    }

}
