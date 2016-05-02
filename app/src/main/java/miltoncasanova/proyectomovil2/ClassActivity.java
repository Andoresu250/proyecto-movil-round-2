package miltoncasanova.proyectomovil2;


public class ClassActivity {
    private String name;
    private double radius;
    private double lat;
    private double lon;

    public ClassActivity(String name, double radius, double lon, double lat) {
        this.name = name;
        this.radius = radius;
        this.lon = lon;
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
