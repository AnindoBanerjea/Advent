import java.lang.UnsupportedOperationException;
public class CircularList {

    public static final int ring = 49;
    private final char[][] data;
    private int head;
    private int size;

    public CircularList() {
        data = new char[ring][0];
        head = 0;
        size = 0;
    }

    public void remove(int i)  throws UnsupportedOperationException {
        if (i == size -1) {
            head = (head - 1) % ring;
            if (head < 0) head += ring;
            size -= 1;

        } else throw new UnsupportedOperationException();
    }
    public void add(char[] row) {
        data[head] = row;
        head = (head + 1) % ring;
        size = size + 1;
    }

    public int size() {
        return size;
    }

    public char[] get(int i) throws ArrayIndexOutOfBoundsException {
        if (i >= size || i < 0 || i < size - ring) throw new ArrayIndexOutOfBoundsException();
        return data[i % ring];
    }

}
