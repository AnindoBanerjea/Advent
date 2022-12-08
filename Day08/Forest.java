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
        for (int i = 0; i < trees.length; i++){
            int tallest = -1;
            for (int j = 0; j < trees[i].length; j++) {
                if (trees[i][j] > tallest) {
                    visibleFromLeft[i][j] = true;
                    tallest = trees[i][j];
                } else {
                    visibleFromLeft[i][j] = false;
                }
            }
        }        
        visibleFromRight = new boolean[trees.length][trees[0].length];
        for (int i = 0; i < trees.length; i++){
            int tallest = -1;
            for (int j = trees[i].length-1; j >= 0; j--) {
                if (trees[i][j] > tallest) {
                    visibleFromRight[i][j] = true;
                    tallest = trees[i][j];
                } else {
                    visibleFromRight[i][j] = false;
                }
            }
        }        
        visibleFromTop = new boolean[trees.length][trees[0].length];
        for (int j = 0; j < trees[0].length; j++){
            int tallest = -1;
            for (int i = 0; i < trees.length; i++) {
                if (trees[i][j] > tallest) {
                    visibleFromTop[i][j] = true;
                    tallest = trees[i][j];
                } else {
                    visibleFromTop[i][j] = false;
                }
            }
        }        
        visibleFromBottom = new boolean[trees.length][trees[0].length];
        for (int j = 0; j < trees[0].length; j++){
            int tallest = -1;
            for (int i = trees.length-1; i >= 0; i--) {
                if (trees[i][j] > tallest) {
                    visibleFromBottom[i][j] = true;
                    tallest = trees[i][j];
                } else {
                    visibleFromBottom[i][j] = false;
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

    public int viewingScore(int x, int y){
        // looking right
        int rightScore=0;
        for (int j = y + 1; j < trees[x].length; j++){
            if (trees[x][j] >= trees[x][y] || j == trees[x].length-1) {
                rightScore = j-y;
                break;
            }
        }
        // looking left
        int leftScore=0;
        for (int j = y - 1; j >= 0; j--){
            if (trees[x][j] >= trees[x][y] || j == 0) {
                leftScore = y-j;
                break;
            }
        }
        // looking down
        int downScore=0;
        for (int i = x + 1; i < trees.length; i++){
            if (trees[i][y] >= trees[x][y] || i == trees.length-1) {
                downScore = i-x; 
                break;
            }
        }
        // looking up
        int upScore=0;
        for (int i = x - 1; i >= 0; i--){
            if (trees[i][y] >= trees[x][y] || i == 0) {
                upScore = x-i;
                break;
            }
        }
        return rightScore * leftScore * downScore * upScore;
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
