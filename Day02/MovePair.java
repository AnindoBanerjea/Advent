public class MovePair {
    private final Move oppMove;
    private final Move myMove;
    
    public MovePair(int n, int m){
        this.oppMove = new Move(n);
        this.myMove = new Move(m);
    }

    public MovePair(Move n, Move m){
        this.oppMove = n;
        this.myMove = m;
    }

    public Move getOppMove(){
        return this.oppMove;
    }

    public Move getMyMove(){
        return this.myMove;
    }
} 

