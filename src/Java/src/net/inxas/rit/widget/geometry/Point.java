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

    public Point() {
        this(0, 0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getCeilX() {
        return (int) Math.ceil(x);
    }

    public int getCeilY() {
        return (int) Math.ceil(y);
    }

    public int getFloorX() {
        return (int) Math.floor(x);
    }

    public int getFloorY() {
        return (int) Math.floor(y);
    }

    public int getRoundX() {
        return (int) Math.round(x);
    }

    public int getRoundY() {
        return (int) Math.round(y);
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(int x, int y) {
        setLocation((double) x, (double) y);
    }

    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    /**
     * 現在の位置に対する相対位置に移動します。
     * 
     * @param x x方向の移動量
     * @param y y方向の移動量
     */
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void move(int x, int y) {
        move((double) x, (double) y);
    }

    public void move(Point p) {
        move(p.x, p.y);
    }

    public Point moveTo(double x, double y) {
        return new Point(this.x + x, this.y + y);
    }

    public Point moveTo(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }

    public Point moveTo(Point p) {
        return new Point(this.x + p.x, this.y + p.y);
    }

    public Point calcMidpoint(double x, double y) {
        return this.calcMidpoint(new Point(x, y));
    }

    public Point calcMidpoint(int x, int y) {
        return this.calcMidpoint(new Point(x, y));
    }

    public Point calcMidpoint(Point p) {
        return new Point((this.x + p.x) * 0.5, (this.y + p.y) * 0.5);
    }

    @Override
    public String toString() {
        return "net.inxas.rit.widget.geometry.Point[x=" + x + ",y=" + y + "]";
    }
}
