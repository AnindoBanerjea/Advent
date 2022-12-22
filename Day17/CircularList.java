import java.lang.UnsupportedOperationException;
public class CircularList {
    private final char[][] data;
    private int head;
    private int size;

    public CircularList() {
        data = new char[1000][0];
        head = 0;
        size = 0;
    }

    public void remove(int i)  throws UnsupportedOperationException {
        if (i == size -1) {
            head = (head - 1) % 1000;
            if (head < 0) head += 1000;
            size -= 1;

        } else throw new UnsupportedOperationException();
    }
    public void add(char[] row) {
        data[head] = row;
        head = (head + 1) % 1000;
        size = size + 1;
    }

    public int size() {
        return size;
    }

    public char[] get(int i) throws ArrayIndexOutOfBoundsException {
        if (i >= size || i < 0 || i < size - 1000) throw new ArrayIndexOutOfBoundsException();
        return data[i % 1000];
    }

}
