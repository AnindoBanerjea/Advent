import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class Signal {
   
    
    private char[] signal;
    private int[] count;

    public Signal(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            // there is only one line, so don't look
            signal = lines.get(0).toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public char[] getSignal() {
        return signal;
    }

    public boolean allUnique(int pos, int num) {
        for (int i=1; i<=num; i++) {
            if (count[index(signal[pos-i])] != 1) return false;
        }
        return true;
    }

    public int findStartOfPacket(int num){
        count = new int[26];

        for (int i=0; i<num; i++) {
            count[index(signal[i])]++;
        }


        for (int i = num; i < signal.length; i++) {
            if (allUnique(i, num)) return i;
            // move the window of size num by one character
            count[index(signal[i-num])]--; // remove the character that falls off the window of size 4
            count[index(signal[i])]++;   // add the next character that to the window of size 4
        }
        return -1;
    }

    public int index(char c) {
        return (int)(c - 'a'); // Only lower case characters
    }

}
