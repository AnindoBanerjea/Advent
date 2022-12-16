import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Position extends Move {
    public static final Map<Character, Move> lookup
        = ImmutableMap.of(
            '<', new Move(-1, 0),
            '>', new Move( 1, 0),
            '^', new Move( 0, 1),
            'V', new Move( 0, -1)
        );

    public void apply(Move m){
        this.x += m.x;
        this.y += m.y;
    }
    

    public Position(int x, int y) {
        super(x, y);
    }

    static Position copyOf(Position other) {
        return new Position(other);
    }
    public Position(Position other) {
        super(other.x, other.y);
    }

    public void setX(int x) {
        super.setX(x);
    }
    public void setY(int y) {
        super.setY(y);
    }
    public int getX() {
        return super.x;
    }
    public int getY() {
        return super.y;
    }


}
