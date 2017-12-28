
package sample;

public class BBox {
    private int radius, id;
    private double x, y;

    public BBox(double x, double y, int radius, int id) {
        this.id = id;
        this.radius = 20;
        this.x = x;
        this.y = y;
    }

    public boolean isColliding(BBox other){
        return Math.sqrt(Math.pow(x - other.x, 2) +
                Math.pow(y - other.y, 2)) < radius;
    }
}