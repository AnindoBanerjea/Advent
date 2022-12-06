public class RangePair {
    private Range r1;
    private Range r2;
    public RangePair(int lo1, int hi1, int lo2, int hi2){
        r1 = new Range(lo1, hi1);
        r2 = new Range(lo2, hi2);
    }
    public Range getRange1(){
        return r1;
    }
    public Range getRange2(){
        return r2;
    }
}
