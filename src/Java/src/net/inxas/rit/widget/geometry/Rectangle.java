package net.inxas.rit.widget.geometry;

/**
 * 矩形領域を表します。 座標はdoubleで表します。
 * 
 * @author inxas
 * @since 2019/10/23
 */
public class Rectangle {
    private double x;
    private double y;
    private double width;
    private double height;

    /**
     * 座標をdouble指定で構築します。
     * 
     * @param x      x座標
     * @param y      y座標
     * @param width  横幅
     * @param height 縦幅
     */
    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * 座標をint指定で構築します。
     * 
     * @param x      x座標
     * @param y      y座標
     * @param width  横幅
     * @param height 縦幅
     */
    public Rectangle(int x, int y, int width, int height) {
        this((double) x, (double) y, (double) width, (double) height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getCeilX() {
        return (int) Math.ceil(x);
    }

    public int getCeilY() {
        return (int) Math.ceil(y);
    }

    public int getCeilWidth() {
        return (int) Math.ceil(width);
    }

    public int getCeilHeight() {
        return (int) Math.ceil(height);
    }

    public int getFloorX() {
        return (int) Math.floor(x);
    }

    public int getFloorY() {
        return (int) Math.floor(y);
    }

    public int getFloorWidth() {
        return (int) Math.floor(width);
    }

    public int getFloorHeight() {
        return (int) Math.floor(height);
    }

    public int getRoundX() {
        return (int) Math.round(x);
    }

    public int getRoundY() {
        return (int) Math.round(y);
    }

    public int getRoundWidth() {
        return (int) Math.round(width);
    }

    public int getRoundHeight() {
        return (int) Math.round(height);
    }
}
