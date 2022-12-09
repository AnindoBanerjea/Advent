import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class Forest {
   
    
    private final int[][] trees;
    // {-1, 0} means looking up
    // { 1, 0} means looking down
    // { 0,-1} means looking left
    // { 0, 1} means looking right
    // rest combinations (like 00, or 11) are not supported
    private static final int[][] directions = new int[][]{{0,1}, {0,-1}, {1,0}, {-1,0}};

    // Visible tells you if a given tree is visible from a specific direction
    // e.g. visible.get({0,1})[i,j] is true if tree[i][j] is visible from the left (looking right)
    // visible.get({1,0})[i,j] is true if tree[i][j] is visible from the top (looking down)    
    private final Map<int[], boolean[][]> visible;

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
        visible = new HashMap<>();
        for (int[] dir : directions) {
            boolean[][] v = new boolean[trees.length][trees[0].length];
            setVisible(v, dir);
            visible.put(dir, v);
        }
    }

    private void setVisible(boolean[][] visible, int[] dir){
        for (int i = 0; i < ((dir[0] == 0) ? trees[0].length : trees.length); i++){
            int tallest = -1;
            for (int j = ((dir[0] == -1) ? trees.length -1 : ((dir[1] == -1) ? trees[0].length -1 : 0));
                 ((j >= 0) && (j < ((dir[0] == 0) ? trees[0].length : trees.length)));
                 j += (dir[0] + dir[1])) {
                int pointerx = ((dir[0] == 0) ? i : j );
                int pointery = ((dir[0] == 0) ? j : i );
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
            sb.append("\n");
        }
        return sb.toString();
    }

    public int countVisible(){
        int result = 0;
        for (int i=0; i<trees.length; i++) {
            for (int j=0; j<trees[i].length; j++) {
                boolean v = false;
                for (int[] dir : directions) {
                    v = v || visible.get(dir)[i][j];
                }
                if (v) {
                    result++;
                }
            }
        }
        return result;
    }

    public int viewingScore(int x, int y, int[] dir){
        int score = 0;
        for (int ij = ((dir[0] == 0) ? y : x) + dir[0] + dir[1];
            ((ij >= 0) && (ij < ((dir[0] == 0) ? trees[0].length : trees.length)));
              ij += (dir[0] + dir[1])){
            int pointerx = (dir[0] == 00) ? x : ij;
            int pointery = (dir[0] == 0) ? ij : y;
            if (trees[pointerx][pointery] >= trees[x][y] ||
                ij == 0 ||
                ij == ((dir[0] == 0) ? trees[0].length-1 : trees.length -1)) {
                score = dir[1]*(ij-y) + dir[0]*(ij-x);
                break;
            }
        }
        return score;
    }

    public int viewingScore(int x, int y){
        int score = 1;
        for (int[] dir : directions) {
            score = score * viewingScore(x, y, dir);
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
