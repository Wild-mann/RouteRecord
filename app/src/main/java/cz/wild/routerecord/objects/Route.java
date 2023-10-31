package cz.wild.routerecord.objects;

/**
 * Datový model databáze trasy
 */
public class Route {
    private int id;                 // id trasy
    private String dateTimeBegin;   // datum a čas
    private double lenght;          // délka trasy
    private double duration;        // celkový čas
    private double averageSpeed;    // průměrná rychlost
    private double maxSpeed;        // maximální rychlost

    public Route(){}

    public Route(int id, String dateTimeBegin, double lenght, double duration, double averageSpeed, double maxSpeed) {
        this.id = id;
        this.dateTimeBegin = dateTimeBegin;
        this.lenght = lenght;
        this.duration = duration;
        this.averageSpeed = averageSpeed;
        this.maxSpeed = maxSpeed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTimeBegin() {
        return dateTimeBegin;
    }

    public void setDateTimeBegin(String dateTimeBegin) {
        this.dateTimeBegin = dateTimeBegin;
    }

    public double getLenght() {
        return lenght;
    }

    public void setLenght(long lenght) {
        this.lenght = lenght;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
