import java.util.*;

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

        // if we move, how much should we move x. -1 means one step left. 1 means one step right. 0 means don't move
        int stepx = h.x > this.x ? 1 : h.x < this.x ? -1 : 0;
        
        // if we move, how much should we move y. -1 means one step down. 1 means one step up. 0 means don't move
        int stepy = h.y > this.y ? 1 : h.y < this.y ? -1 : 0;

        // Now do we move at all? We move if any of the dimensions is off by more than 1
        if (Math.abs(h.x - this.x) > 1 ||
            Math.abs(h.y - this.y) > 1) {

            // if we move at all, we move on both axes, even if we are off by only 1
            this.x += stepx;
            this.y += stepy;
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


    @Override public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
 
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
 
        Position p = (Position) other;        
        return this.x == p.x && this.y == p.y;       
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
