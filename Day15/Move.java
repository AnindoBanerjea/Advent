import java.util.Objects;

public class Move {
    protected int x;
    protected int y;
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
 
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
 
        Move m = (Move) other;
        return this.x == m.x && this.y == m.y;
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }


    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    public int distance(Move other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }
}
