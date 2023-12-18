package cz.wild.routerecord.objects;

/**
 * Datový model databáze trasy
 */
public class Route {
    private int id;                 // id trasy
    private long dateTimeBegin;     // datum a čas začátku
    private double lenght;          // délka trasy
    private long dateTimeEnd;     // celkový čas
    private double averageSpeed;    // průměrná rychlost
    private double maxSpeed;        // maximální rychlost

    public Route(){}

    public Route(int id, long dateTimeBegin, double lenght, long dateTimeEnd, double averageSpeed, double maxSpeed) {
        this.id = id;
        this.dateTimeBegin = dateTimeBegin;
        this.lenght = lenght;
        this.dateTimeEnd = dateTimeEnd;
        this.averageSpeed = averageSpeed;
        this.maxSpeed = maxSpeed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDateTimeBegin() {
        return dateTimeBegin;
    }

    public void setDateTimeBegin(long dateTimeBegin) {
        this.dateTimeBegin = dateTimeBegin;
    }

    public double getLenght() {
        return lenght;
    }

    public void setLenght(long lenght) {
        this.lenght = lenght;
    }

    public long getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(long dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(long averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(long maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
