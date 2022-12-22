import java.util.List;

public class Rock {
    private final List<char[]> picture;
    private int top;
    private int bottom;
    private int left;
    private int right;

    public Rock(List<char[]> picture, int top, int bottom, int left, int right) {
        this.picture = picture;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public Rock copyOf(){
        return new Rock(this.picture, this.top, this.bottom, this.left, this.right);
    }

    public List<char[]> getPicture() {
        return picture;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
