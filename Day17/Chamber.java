import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Chamber {
    private static final List<Rock> rocks = List.of(

            // rock 1
            new Rock(List.of("  @@@@ ".toCharArray()), 0, 0, 2, 5),
            // rock 2
            new Rock(List.of("   @   ".toCharArray(),
                             "  @@@  ".toCharArray(),
                             "   @   ".toCharArray()), 0, 2, 2, 4),
            // rock 3
            new Rock(List.of("    @  ".toCharArray(),
                             "    @  ".toCharArray(),
                             "  @@@  ".toCharArray()), 0, 2, 2, 4),
            // rock 4
            new Rock(List.of("  @    ".toCharArray(),
                             "  @    ".toCharArray(),
                             "  @    ".toCharArray(),
                             "  @    ".toCharArray()), 0, 3, 2, 2),
            // rock 5
            new Rock(List.of("  @@   ".toCharArray(),
                             "  @@   ".toCharArray()), 0, 1, 2, 3)
    );
    private static final char[] blankLine = "       ".toCharArray();
    private static final char space = ' ';
    private static final char movingRock = '@';
    private static final char fixedRock = '#';
    private static final char pushLeft = '<';

    private long removedHeight = 0;
    private final boolean verbose;
    private final char[] input;
    private final CircularList chamber = new CircularList();
    private final List<Cycle> cycles = new ArrayList<>();

    public Chamber(String filename, boolean verbose) throws IOException {
        this.verbose = verbose;
        List<String> lines = Files.readAllLines(Paths.get(filename));
        // There is only one line, so no looping needed
        input = lines.get(0).toCharArray();

        // add a floor to avoid having to check for the floor which only is needed for the first couple of
        // iterations and after that only slows the loop down
        chamber.add("#######".toCharArray());
    }

    private long cycleTruncate(long iterations, long i, long cycleLength, long deltaHeight) {
        long remainingIterations = iterations - i;
        long removedCycles = remainingIterations / cycleLength;
        removedHeight = removedCycles * deltaHeight;
        remainingIterations = remainingIterations % cycleLength;
        long oldIterations = iterations;
        iterations = i + remainingIterations;
        log("Shortening iterations from %d to %d and compensating by saving removedHeight of %d\n", oldIterations, iterations, removedHeight);
        return iterations;
    }
    private long cycleDetection(long iterations, long i, long time) {
        // Once you have detected a cycle and shorted the number of iterations, stop cycle detection
        // to avoid doing this twice and confusing things
        long cycleLength = 0;
        if (removedHeight == 0) {
            if (i % input.length == 0 && time % rocks.size() == 0) {
                Cycle c = new Cycle(i, time, chamber.size(), convertShapeToString(10));
                cycles.add(c);
                for (int j = 0; j < cycles.size() - 1; j++) {
                    if (c.shape().equals(cycles.get(j).shape())) {
                        if (cycleLength == 0) {
                            cycleLength = i - cycles.get(j).iteration();
                        } else {
                            long secondCycleLength = i - cycles.get(j).iteration();
                            long deltaHeight = c.height() - cycles.get(j).height();
                            log("\n");
                            log("Found second matching cycle of length=%d\n", secondCycleLength);
                            log("First cycle was of length=%d\n", cycleLength);
                            log("Current cycle iteration=%d time=%d height=%d top=%s\n",
                                    i, time, chamber.size(), c.shape());
                            log("Previous cycle iteration=%d time=%d height=%d top=%s\n",
                                    cycles.get(j).iteration(), cycles.get(j).time(), cycles.get(j).height(), cycles.get(j).shape());
                            if (secondCycleLength * 2 == cycleLength) {
                                log("First cycle length is double of second, so confirmed\n");
                                iterations = cycleTruncate(iterations, i, secondCycleLength, deltaHeight);
                            } else {
                                log("First cycle length is not double of second, so keep looking\n");
                            }
                        }
                    }
                }
            }
        }
        return iterations;
    }
    public void simulate(long iterations){
        long time = 0;
        for (long i = 0; i < iterations; i++) {
            iterations = cycleDetection(iterations, i, time);

            // get a new rock of next rock type
            Rock rock = rocks.get((int)(i % rocks.size())).copyOf();
            // make sure there are exactly three blank lines
            make3BlankLines();
            addRock(rock);
            time = landRock(rock, time);
        }
    }

    private boolean move(@NotNull Rock rock, int dirX, int dirY, boolean test)  throws UnsupportedOperationException {
        boolean can = true;
        for (int i=rock.getBottom(); i<=rock.getTop(); i++) {
            int jStart = (dirX == 1) ? rock.getRight() : rock.getLeft();
            int jEnd   = (dirX == 1) ? rock.getLeft() : rock.getRight();
            int jIncrement = (dirX == 1) ? -1 : 1;
            for (int j=jStart; j != jEnd + jIncrement; j += jIncrement) {
                if (chamber.get(i)[j] == movingRock) {
                    if (test) {
                        // we don't actually move anything, just compute and return if we can
                        // We need to check if hit the chamber walls
                        // we don't need to check the floor, since we put a floor at zero
                        if (    j + dirX >= chamber.get(i).length ||
                                j + dirX < 0 ||
                                chamber.get(i + dirY)[j + dirX] == fixedRock) {
                            can = false;
                        }
                        /*
                         If we are moving left or right, once we have found the leading edge of the rock,
                         regardless of whether we can move or not we can break out of the j loop, we don't
                         need to check the rest
                        */
                        if (dirX != 0) break;
                    } else {
                        // if test is false, we actually move stuff
                        chamber.get(i)[j] = space;
                        chamber.get(i+dirY)[j+dirX] = movingRock;
                    }
                }
            }
            // If you can't move anymore, don't need to check the other rows
            if (!can) break;
        }
        if (!test) {
            if (dirX == 0) {
                // we are moving vertically
                rock.setTop(rock.getTop() + dirY);
                rock.setBottom(rock.getBottom() + dirY);
                for (int i=chamber.size()-1; i>rock.getTop(); i--) {
                    if (Arrays.equals(chamber.get(i), blankLine)) {
                        chamber.remove(i);
                    } else {
                        break;
                    }
                }
            } else {
                // we are moving horizontally
                rock.setLeft(rock.getLeft() + dirX);
                rock.setRight(rock.getRight() + dirX);
            }
        }
        return can;
    }
    private boolean moveRock(@NotNull Rock rock, int dirX, int dirY){
        // with test == true, we don't move anything, we just compute can or can't move
        boolean can = move(rock, dirX, dirY, true);
        if (can) {
            // for real this time
            move(rock, dirX, dirY, false);
        }
        return can;
    }

    private void freezeRock(@NotNull Rock rock) {
        for (int i=rock.getBottom(); i<=rock.getTop(); i++) {
            for (int j=rock.getLeft(); j<=rock.getRight(); j++) {
                if (chamber.get(i)[j] == movingRock) {
                    chamber.get(i)[j] = fixedRock;
                }
            }
        }
    }
    private long landRock(@NotNull Rock rock, long time){

        while (true) {
            // dirX is determined by input < means -1, > means +1
            int dirX = (input[(int)(time % input.length)] == pushLeft) ? -1 : 1;
            time++;
            if (time < 0) System.out.println("Time overflow.");
            // First we move the rock dirX horizontally
            moveRock(rock, dirX, 0);
            // then we move the rock -1 vertically
            if (!moveRock(rock, 0, -1)) {
                freezeRock(rock);
                break;
            }
        }
        return time;
    }
    public void make3BlankLines() {
        // needs to be recoded since in the second iteration
        // there will already by 3 or 4 blank lines
        chamber.add(Arrays.copyOf(blankLine, blankLine.length));
        chamber.add(Arrays.copyOf(blankLine, blankLine.length));
        chamber.add(Arrays.copyOf(blankLine, blankLine.length));
    }
    private void addRock(@NotNull Rock rock) {
        for (int j = rock.getPicture().size()-1; j >=0; j--) {
            chamber.add(Arrays.copyOf(rock.getPicture().get(j), rock.getPicture().get(j).length));
        }
        rock.setTop(chamber.size() - rock.getTop() -1);
        rock.setBottom(chamber.size() - rock.getBottom() -1);
        // left and right are correct already

    }

    public void printTop(int n) {
        for (int i=0; i<n && i < chamber.size(); i++) {
            System.out.printf("|%s| %d\n", String.valueOf(chamber.get(chamber.size()-i-1)), chamber.size()-i-1);
        }
        System.out.println();
    }

    public String convertShapeToString(int n) {
        StringBuilder output = new StringBuilder();
        for (int i=0; i<n && i < chamber.size(); i++) {
            output.append(chamber.get(chamber.size()-i-1));
        }
        return output.toString();
    }
    private void log(String format, Object... args) {
        if (verbose) System.out.printf(format, args);
    }

    public long getChamberHeight(){
        // -1 because we put a floor of rock
        return chamber.size() -1 + removedHeight;
    }
}
