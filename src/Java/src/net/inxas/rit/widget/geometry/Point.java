package net.inxas.rit.widget.geometry;

/**
 * 座標一つを表します。 座標はdoubleで表します。
 * 
 * @author inxas
 * @since  2019/10/23
 */
public class Point {
    private double x;
    private double y;

    /**
     * 座標をdouble指定で構築します。
     * 
     * @param x x座標
     * @param y y座標
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 座標をint指定で構築します。
     * 
     * @param x x座標
     * @param y y座標
     */
    public Point(int x, int y) {
        this((double) x, (double) y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
