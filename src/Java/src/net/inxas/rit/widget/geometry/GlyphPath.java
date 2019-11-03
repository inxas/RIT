package net.inxas.rit.widget.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * 文字アウトライン用のパスです。
 * 
 * @author inxas
 * @since  2019/11/02
 */
public class GlyphPath extends Path {
    private final short xMin, yMin, xMax, yMax;
    private final List<List<Boolean>> isOnTheLines;

    public GlyphPath(short xMin, short yMin, short xMax, short yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        isOnTheLines = new ArrayList<>();
    }

    public Rectangle getBlackBox() {
        return new Rectangle(xMin, yMin, xMax, yMax);
    }

    @Override
    public void lineTo(Point p) {
        this.lineTo(p, true);
    }

    @Override
    public void lineTo(double x, double y) {
        this.lineTo(x, y, true);
    }

    @Override
    public void lineTo(int x, int y) {
        this.lineTo(x, y, true);
    }

    @Override
    public void moveTo(Point p) {
        moveTo(p, true);
    }

    private boolean startIsOnTheLine = true;

    public void moveTo(Point p, boolean isOnTheLine) {
        startIsOnTheLine = isOnTheLine;
        isOnTheLines.add(new ArrayList<>());
        lines.add(new ArrayList<>());
        lineTo(p, isOnTheLine);
    }

    public void lineTo(Point p, boolean isOnTheLine) {
        if (isOnTheLines.isEmpty()) {
            isOnTheLines.add(new ArrayList<>());
        }
        List<Boolean> curenntOnTheLines = isOnTheLines.get(isOnTheLines.size() - 1);
        if ((!startIsOnTheLine && curenntOnTheLines.size() == 1 && !isOnTheLine)
                || (curenntOnTheLines.size() > 1 && !curenntOnTheLines.get(curenntOnTheLines.size() - 1)
                        && !isOnTheLine)) {
            Point point = p.calcMidpoint(new Point());
            super.lineTo(point);
            curenntOnTheLines.add(true);
            super.lineTo(point);
        } else {
            super.lineTo(p);
        }
        curenntOnTheLines.add(isOnTheLine);
    }

    public void normalizeTo() {
        if (getCurrentLine().isEmpty())
            return;
        List<Boolean> curenntOnTheLines = isOnTheLines.get(isOnTheLines.size() - 1);
        if (!curenntOnTheLines.get(0) && !curenntOnTheLines.get(curenntOnTheLines.size() - 1)) {
            List<Point> currentLine = getCurrentLine();
            Point end = new Point();
            for (int i = 1, len = currentLine.size(); i < len; i++) {
                end.move(currentLine.get(i));
            }
            lineTo(new Point(-end.getX(), -end.getY()).calcMidpoint(new Point()));
        }
    }

    public void lineTo(double x, double y, boolean isOnTheLine) {
        this.lineTo(new Point(x, y), isOnTheLine);
    }

    public void lineTo(int x, int y, boolean isOnTheLine) {
        this.lineTo(new Point(x, y), isOnTheLine);
    }

    public List<List<Boolean>> isOnTheLines() {
        return isOnTheLines;
    }
}
