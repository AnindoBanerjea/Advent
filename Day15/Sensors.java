import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;


public class Sensors {

    private final Position min;
    private final Position max;
    private final List<Position> sensors;
    private final List<Position> beacons;

    private final boolean verbose;
    private final int part;

    public Sensors(String filename, int part, boolean verbose) throws IOException {
        sensors = new ArrayList<>();
        beacons = new ArrayList<>();
        this.verbose = verbose;
        this.part = part;
        min = new Position(MAX_VALUE, MAX_VALUE);
        max = new Position(MIN_VALUE, MIN_VALUE);
        int maxdistance = 0;
        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines) {
            Pattern pattern = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
            Matcher matcher = pattern.matcher(line);
            // throw away the first match which matches the whole thing
            matcher.find();
            int sensorx = Integer.parseInt(matcher.group(1));
            int sensory = Integer.parseInt(matcher.group(2));
            int beaconx = Integer.parseInt(matcher.group(3));
            int beacony = Integer.parseInt(matcher.group(4));
            Position sensor = new Position(sensorx, sensory);
            Position beacon = new Position(beaconx, beacony);
            sensors.add(sensor);
            beacons.add(beacon);
            maxdistance = Math.max(maxdistance, sensor.distance(beacon));
            min.setX(Math.min(Math.min(sensorx, beaconx), min.getX()));
            max.setX(Math.max(Math.max(sensorx, beaconx), max.getX()));
            min.setY(Math.min(Math.min(sensory, beacony), min.getY()));
            max.setY(Math.max(Math.max(sensory, beacony), max.getY()));
        }
        min.setX(min.getX()-maxdistance);
        max.setX(max.getX()+maxdistance);
    }


    public SparseRange coverage (int row) {
        return coverage(row, min.getX(), max.getX());
    }

    public SparseRange coverage (int row, int minx, int maxx) {
        SparseRange range = new SparseRange(minx, maxx);
        for (int j = 0; j < sensors.size(); j++) {
            Position sensor = sensors.get(j);
            Position beacon = beacons.get(j);
            int distance = sensor.distance(beacon);
            // if the row is closer than distance
            if (distance > Math.abs(sensor.y-row)) {
                int remaining = distance - Math.abs(sensor.y - row);
                range.removeFromRange(sensor.x - remaining,sensor.x + remaining);
            }
        }
        if (part == 1) {
            for (Position beacon : beacons) {
                if (beacon.y == row) {
                    // Add back in the B, so we are not off by one
                    range.addIntoRange(beacon.x);
                }
            }
        }
        return range;
    }

    public int coverageSize(SparseRange coverage) {
        int size = coverage.size();
        return max.x - min.x + 1- size;
    }

    public Position findSensor(Position max) {
        for (int row = 0; row <= max.getY(); row++) {
            SparseRange coverage = coverage(row, 0, max.getX());
            if (verbose && !coverage.toString().isEmpty()) System.out.printf("Coverage for row %d is %s\n", row, coverage);
            int result = coverage.first();
            if (result != MIN_VALUE) {
                return new Position(coverage.first(), row);
            }
        }
        return null;
    }
}
