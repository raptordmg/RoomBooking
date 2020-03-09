package RaptorDMG;

public class Booking {
    private int seatCapacity;
    private int requiredComputers = 0;
    private boolean breakoutAcceptable;
    private boolean hasSmartboard;
    private boolean hasPrinter;
    private String clientName;

    public Booking(int seatCapacity, int requiredComputers, boolean breakoutAcceptable, boolean hasSmartboard, boolean hasPrinter, String clientName) {
        this.seatCapacity = seatCapacity;
        this.requiredComputers = requiredComputers;
        this.breakoutAcceptable = breakoutAcceptable;
        this.hasSmartboard = hasSmartboard;
        this.hasPrinter = hasPrinter;
        this.clientName = clientName;
    }
}
