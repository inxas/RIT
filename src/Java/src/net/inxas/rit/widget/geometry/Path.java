package net.inxas.rit.widget.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * 線を表します。
 * 各点は、一つ前の点を基準とした相対的な座標です。
 * 座標(0,0)を起点とし、そこからの差分を記録していきます。
 * {@link #moveTo(Point)}による線の変更も例外なく、
 * 一つ前の点を基準にします。
 * 
 * @author inxas
 * @since  2019/11/01
 */
public class Path {
    protected final List<List<Point>> lines;

    public Path(Point p) {
        this();
        lineTo(p);
    }

    public Path(double x, double y) {
        this(new Point(x, y));
    }

    public Path(int x, int y) {
        this(new Point(x, y));
    }

    public Path() {
        lines = new ArrayList<>();
    }

    public void lineTo(Point p) {
        if (p == null)
            throw new NullPointerException("p must not be null!");
        if (lines.isEmpty()) {
            lines.add(new ArrayList<>());
        }
        lines.get(lines.size() - 1).add(p);
    }

    public void lineTo(double x, double y) {
        this.lineTo(new Point(x, y));
    }

    public void lineTo(int x, int y) {
        this.lineTo(new Point(x, y));
    }

    public void moveTo(Point p) {
        if (p == null)
            throw new NullPointerException("p must not be null!");
        lines.add(new ArrayList<>());
        lineTo(p);
    }

    public void moveTo(double x, double y) {
        moveTo(new Point(x, y));
    }

    public void moveTo(int x, int y) {
        moveTo(new Point(x, y));
    }

    public List<List<Point>> getLines() {
        return lines;
    }

    public List<Point> getCurrentLine() {
        return lines.get(lines.size() - 1);
    }
}
