import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class Position extends Move {
    private int steps;
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
        this.steps++;
    }
    

    public Position(int x, int y) {
        super(x, y);
        steps = 0;
    }

    static Position copyOf(Position other) {
        return new Position(other);
    }
    public Position(Position other) {
        super(other.x, other.y);
        this.steps = other.steps;
    }

    public int getX() {
        return super.x;
    }
    public int getY() {
        return super.y;
    }
    public int getSteps() { return steps; }


}
