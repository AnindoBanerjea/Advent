import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class Position extends Move {
    public static final Map<Character, Move> lookup 
        = ImmutableMap.of(
            'L', new Move(-1, 0),
            'R', new Move( 1, 0),
            'U', new Move( 0, 1),
            'D', new Move( 0, -1)
        );

    public void apply(Move m){
        this.x += m.x;
        this.y += m.y;
    }
    
    public void follow(Position h){
        // this is the tail or body of the node following head, h is the head

        // How far off are we
        int deltax = h.x - this.x;
        int deltay = h.y - this.y;

        // We move if any of the dimensions is off by more than 1
        if (Math.abs(deltax) > 1 ||
            Math.abs(deltay) > 1) {

            // if we move at all, we move on both axes by zero or one step, even if we are off by only 1
            this.x += Integer.signum(deltax);
            this.y += Integer.signum(deltay);
        }
    }

    public Position(int x, int y) {
        super(x, y);
    }
    public Position(Position other) {
        super(other.x, other.y);
    }

    public int getX() {
        return super.x;
    }
    public int getY() {
        return super.y;
    }



}
