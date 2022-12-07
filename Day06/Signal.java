import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Signal {
   
    
    private char[] signal;
    private int[] count;

    public Signal(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            // there is only one line, so don't look
            signal = line.toCharArray();

            reader.close();
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
