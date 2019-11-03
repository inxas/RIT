package net.inxas.rit.widget.geometry;

/**
 * 面積を表します。
 * 
 * @author inxas
 * @since  2019/10/27
 */
public class Area {
    private double width, height;

    public Area(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public Area(int width, int height) {
        this((double) width, (double) height);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
