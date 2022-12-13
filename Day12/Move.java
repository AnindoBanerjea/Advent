import java.util.Objects;

public class Move {
    protected int x;
    protected int y;
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
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
