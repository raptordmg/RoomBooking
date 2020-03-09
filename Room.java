package RaptorDMG;

public class Room {
    private int computers;
    private int breakout;
    private boolean hasSmartboard;
    private boolean hasPrinter;
    private String bookedBy;

    public Room(int computers, int breakout, boolean hasSmartboard, boolean hasPrinter) {
        this.computers = computers;
        this.breakout = breakout;
        this.hasSmartboard = hasSmartboard;
        this.hasPrinter = hasPrinter;
    }
}
