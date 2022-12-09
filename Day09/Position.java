import java.util.*;

import com.google.common.collect.ImmutableMap;

public class Position extends Move {
    static final Map<Character, Move> lookup 
        = ImmutableMap.of(
            'L', new Move(-1, 0),
            'R', new Move( 1, 0),
            'U', new Move( 0, 1),
            'D', new Move( 0, -1)
        );

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

    public void apply(Move m){
        this.x += m.x;
        this.y += m.y;
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
  
    public void follow(Position h){
        // this is the tail, h is the head

        int dirx = h.x > this.x ? 1 : h.x < this.x ? -1 : 0;
        int diry = h.y > this.y ? 1 : h.y < this.y ? -1 : 0;
        if (Math.abs(h.x - this.x) > 1 ||
            Math.abs(h.y - this.y) > 1) {
            // move one step closer to head along both axes
            this.x += dirx;
            this.y += diry;
        }
    }
}
