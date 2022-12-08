import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class Forest {
   
    
    private final int[][] trees;
    private final boolean[][] visibleFromLeft;
    private final boolean[][] visibleFromRight;
    private final boolean[][] visibleFromTop;
    private final boolean[][] visibleFromBottom;

    public Forest(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lines != null && lines.size() > 0) {
            int m = lines.size();
            int n = lines.get(0).length();
            trees = new int[m][n];
            int i = 0;
            for (String line : lines) {
                int j = 0;
                for (char c : line.toCharArray()) {
                    trees[i][j++] = c - '0';
                }
                i++;
            }
        } else {
            trees = new int[0][0];
        }
        visibleFromLeft = new boolean[trees.length][trees[0].length];
        setVisible(visibleFromLeft, 0, 1); 
        visibleFromRight = new boolean[trees.length][trees[0].length];
        setVisible(visibleFromRight, 0, -1);
        visibleFromTop = new boolean[trees.length][trees[0].length];
        setVisible(visibleFromTop, 1, 0);
        visibleFromBottom = new boolean[trees.length][trees[0].length];
        setVisible(visibleFromBottom, -1, 0);
    }

    private void setVisible(boolean[][] visible, int dirx, int diry){
        // dirx = -1, diry = 0 means looking up
        // dirx =  1, diry = 0 means looking down
        // dirx =  0, diry = -1 means looking left
        // dirx =  0, diry =  1 means looking right
        // rest combinations (like 00, or 11) are not supported
        for (int i = 0; i < ((dirx == 0) ? trees[0].length : trees.length); i++){
            int tallest = -1;
            for (int j = ((dirx == -1) ? trees.length -1 : ((diry == -1) ? trees[0].length -1 : 0));
                 ((j >= 0) && (j < ((dirx == 0) ? trees[0].length : trees.length)));
                 j += (dirx + diry)) {
                int pointerx = ((dirx == 0) ? i : j );
                int pointery = ((dirx == 0) ? j : i );
                if (trees[pointerx][pointery] > tallest) {
                    visible[pointerx][pointery] = true;
                    tallest = trees[pointerx][pointery];
                } else {
                    visible[pointerx][pointery] = false;
                }
            }
        }            
    }


    public int[][] getTrees() {
        return trees;
    }

    @Override public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<trees.length; i++) {
            for (int j=0; j<trees[i].length; j++) {
                sb.append(trees[i][j]);
            }
            sb.append(" ");
            for (int j=0; j<trees[i].length; j++) {
                sb.append(visibleFromRight[i][j] ? 1 : 0);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int countVisible(){
        int result = 0;
        for (int i=0; i<trees.length; i++) {
            for (int j=0; j<trees[i].length; j++) {
                if (visibleFromLeft[i][j] || visibleFromRight[i][j] || visibleFromTop[i][j] || visibleFromBottom[i][j]) {
                    result++;                    
                }
            }
        }
        return result;
    }

    public int viewingScore(int x, int y, int dirx, int diry){
        // dirx = -1, diry = 0 means looking up
        // dirx =  1, diry = 0 means looking down
        // dirx =  0, diry = -1 means looking left
        // dirx =  0, diry =  1 means looking right
        // rest combinations (like 00, or 11) are not supported
        int score = 0;
        for (int ij = ((dirx == 0) ? y : x) + dirx + diry;
            ((ij >= 0) && (ij < ((dirx == 0) ? trees[0].length : trees.length)));
              ij += (dirx + diry)){
            int pointerx = (dirx == 00) ? x : ij;
            int pointery = (dirx == 0) ? ij : y;
            if (trees[pointerx][pointery] >= trees[x][y] ||
                ij == 0 ||
                ij == ((dirx == 0) ? trees[0].length-1 : trees.length -1)) {
                score = diry*(ij-y) + dirx*(ij-x);
                break;
            }
        }
        return score;
    }

    public int viewingScore(int x, int y){
        int[][] dirs = new int[][]{{0,1}, {0,-1}, {1,0}, {-1,0}};
        int score = 1;
        for (int[] dir : dirs) {
            score = score * viewingScore(x, y, dir[0], dir[1]);
        }
        return score;
    }

    public int topViewingScore(){
        int topscore = 0;
        for (int i=0; i<trees.length; i++){
            for (int j=0; j<trees[i].length; j++) {
                topscore = Math.max(topscore, viewingScore(i, j));
            }
        }
        return topscore;
    }

}
