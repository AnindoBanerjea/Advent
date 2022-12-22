import java.util.Objects;

public record Cycle (long iteration, long time, long height, String shape){

    public Cycle{
        Objects.requireNonNull(shape);
    }
}
