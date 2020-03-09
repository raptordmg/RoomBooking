package RaptorDMG;

public class Room {
    private int roomNum;
    private int computers;
    private int breakout;
    private boolean hasSmartboard;
    private boolean hasPrinter;
    private String bookedBy;

    public Room(int roomNum,int computers, int breakout, boolean hasSmartboard, boolean hasPrinter) {
        this.roomNum = roomNum;
        this.computers = computers;
        this.breakout = breakout;
        this.hasSmartboard = hasSmartboard;
        this.hasPrinter = hasPrinter;
    }
}
