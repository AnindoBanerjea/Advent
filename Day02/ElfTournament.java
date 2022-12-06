import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class ElfTournament {
   
    
    private ArrayList<MovePair> rounds;

    public ElfTournament(String filename) {
        this.rounds = new ArrayList<MovePair>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                Move oppMove = new Move((int)(line.charAt(0)-'A'));
                int target = (int)(line.charAt(2)-'X'-1);
                Move myMove = oppMove.predict(target);
                MovePair currentRound = new MovePair(oppMove, myMove);    
                this.rounds.add(currentRound);

                // read next line
                line = reader.readLine();

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MovePair> getRounds() {
        return this.rounds;
    }

}
