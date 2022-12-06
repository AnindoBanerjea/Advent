public class Podium {
    private int[] top;
    public Podium() {
        top = new int[]{0, 0, 0};
    }
    public void compare(int n) {
        if (n > top[0]) {
            top[2] = top[1];
            top[1] = top[0];
            top[0] = n;
        } else if (n > this.top[1]) {
            top[2] = top[1];
            top[1] = n;
        } else if (n > this.top[2]) {
            top[2] = n;
        }
    }

    public int total() {
        return top[0] + top[1] + top[2];
    }

    public int champ() {
        return top[0];
    }
}
