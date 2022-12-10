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
    private final boolean[][] visible;

    public Forest(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));

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
        visible = new boolean[trees.length][trees[0].length];
        for (int[] dir : directions) {
            setVisible(dir);
        }
    }

    private void setVisible(int[] dir){
        // If dir[0] is zero, that means we are looking vertically, so we will have to swap our i and j
        // as well as use trees[0].length instead of trees.length to check for the end of the column
        boolean swap = (dir[0] == 0);
        // Generic loop that can go right to left, left to right, bottom to top or top to bottom through the array
        // Outer loop can be for rows or colums (we will swap using ii and jj later), but it is always forward
        for (int i = 0; i < (swap ? trees[0].length : trees.length); i++){
            int tallest = -1;
            for (// inner loop can be backwards if direction is negative, if it is backwards, start from 
                 // tree.length - 1 or tree[0].length - 1 depending on direction. Else start from 0.
                 int j = ((dir[0] == -1) ? trees.length -1 : ((dir[1] == -1) ? trees[0].length -1 : 0));
                 // termination condition is written to be able to handle either direction of travel
                 ((j >= 0) && (j < ((dir[0] == 0) ? trees[0].length : trees.length)));
                 // since exactly one of dir[0] or dir[1] is non-zero, we can just add them to get the
                 // direction of travel
                 j += (dir[0] + dir[1])) {
                // if we are moving horizontally, keep the order. If we are moving vertically, swap i and j 
                tallest = setVisible(swap ? i : j, swap ? j : i, tallest);
            }
        }            
    }

    private int setVisible(int ii, int jj, int tallest) {
        if (trees[ii][jj] > tallest) {
            visible[ii][jj] = true;
            return trees[ii][jj];
        }
        return tallest;
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
                if (visible[i][j]) {
                    result++;
                }
            }
        }
        return result;
    }

    public int viewingScore(int x, int y, int[] dir){
        int score = 0;
        // Generic loop that can go right to left, left to right, bottom to top or top to bottom through the array
        // if looking up or down, intialize ij (because it can represent either i or j) to x or y (depending on
        // whethere we are doing horizontally or vertitally) + or - 1 depending on the direction. Since only one
        // of dir[0] or dir[1] can be non-zero, we just add both to get the +-1
        for (int ij = (dir[0] == 0 ? y : x) + dir[0] + dir[1];
             // The termination condition handles both zero and tree length
             ((ij >= 0) && (ij < ((dir[0] == 0) ? trees[0].length : trees.length)));
             // for the increment, since only one of dir[0] or dir[1] can be non-zero, we just add both
             ij += (dir[0] + dir[1])){
            // Now, based on whether we are going horizontally (dir[0] == 0), or vertitally (otherwise)
            // we use ij to move the appropriate index and keep the other one constant at x or y
            int ii = (dir[0] == 00) ? x : ij;
            int jj = (dir[0] == 0) ? ij : y;
            if (trees[ii][jj] >= trees[x][y] ||
                ij == 0 ||
                ij == ((dir[0] == 0) ? trees[0].length-1 : trees.length -1)) {
                    // multiplying by dir[0] and dir[1] eliminates the score for the direction we are not looking
                    // as well as flips the sign in the case direction is -1
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
