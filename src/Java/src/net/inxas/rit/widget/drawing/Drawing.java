package net.inxas.rit.widget.drawing;

import net.inxas.rit.widget.AbstractWindow;
import net.inxas.rit.widget.Widget;
import net.inxas.rit.widget.geometry.GlyphPath;
import net.inxas.rit.widget.geometry.Path;
import net.inxas.rit.widget.geometry.Point;

/**
 * {@link net.inxas.rit.widget.Widget Widget}に対して描画を行います。
 * 各描画メソッドを使用するには事前に{@link #beginDraw()}を実行しておく必要があります。
 * また、描画を確定するために{@link #endDraw()}を実行する必要があります。
 * 
 * @author inxas
 *
 */
public class Drawing {
    private final Widget widget;

    // pixels[height][width]
    private Color[][] pixels;
    private Color[][] cache;

    private int width, height;

    /**
     * 指定の部品に対するインスタンスを構築します。
     * ユーザーは基本的に使用しません。
     * 
     * @param widget 部品
     */
    public Drawing(Widget widget) {
        if (widget == null)
            throw new NullPointerException("widget is null!");
        this.widget = widget;
    }

    /**
     * このインスタンスが描画するウィジェットを返します。
     * 
     * @return 描画するウィジェット
     */
    public Widget getWidget() {
        return widget;
    }

    /**
     * 描画を開始します。
     * 以前描画した結果は保持されません。
     */
    public void beginDraw() {
        if (widget instanceof AbstractWindow) {
            if (((AbstractWindow) widget).isDisposed())
                return;
            width = (int) ((AbstractWindow) widget).getClientArea().getWidth();
            height = (int) ((AbstractWindow) widget).getClientArea().getHeight();
        } else {
            width = widget.getWidth();
            height = widget.getHeight();
        }

        if (width < 1)
            width = 1;
        if (height < 1)
            height = 1;
        pixels = new Color[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = Color.WHITE;
            }
        }
    }

    /**
     * 描画を確定します。
     * これ以降は各描画メソッドを実行しても変化はありません。
     * 再び描画するときは{@link #beginDraw()}を実行します。
     */
    public void endDraw() {
        cache = pixels;
        pixels = null;
    }

    /**
     * 最後に行った{@link #endDraw()}の結果を取得します。
     * 
     * @return 最後に行った{@link #endDraw()}の結果
     */
    public Color[][] getCache() {
        return cache;
    }

    /**
     * 現在描画可能かを返します。
     * 
     * @return もし描画可能であればtrue、そうでなければfalse
     */
    public boolean isDrawable() {
        return pixels != null;
    }

    /**
     * 指定の位置に点を描きます。
     * 
     * @param x     x座標
     * @param y     y座標
     * @param color 色
     */
    public void drawDot(int x, int y, Color color) {
        if (pixels == null || color == null)
            return;
        if (x < 0 || x >= width || y < 0 || y >= height)
            return;
        Color under = pixels[y][x];
        pixels[y][x] = Color.coating(under, color);
    }

    public void drawDot(Point p, Color color) {
        drawDot(p.getRoundX(), p.getRoundY(), color);
    }

    /**
     * 指定のデータを配置します。
     * 
     * @param bytes データ配列
     * @param x     左上のx座標
     * @param y     左上のy座標
     */
    public void transfer(Color[][] bytes, int x, int y) {
        if (pixels == null)
            return;
        for (int i = 0; i < bytes.length && y + i <= height; i++) {
            if (y + i < 0)
                continue;
            for (int j = 0; j < bytes[i].length && x + j <= width; j++) {
                if (x + j < 0)
                    continue;
                drawDot(x + i, y + j, bytes[i][j]);
            }
        }
    }

    /**
     * 指定の点の線分を引きます。
     * 
     * @param x1    x座標
     * @param y1    y座標
     * @param x2    x座標
     * @param y2    y座標
     * @param color 線の色
     */
    public void drawSimpleLine(int x1, int y1, int x2, int y2, Color color) {
        if (x2 < x1) {
            int temp = x2;
            x2 = x1;
            x1 = temp;
            temp = y2;
            y2 = y1;
            y1 = temp;
        }
        if (x1 > width || x2 < 0 || Math.min(y1, y2) > height || Math.max(y1, y2) < 0)
            return;

        int dx = (x2 - x1) * 2;
        int dy = Math.abs(y2 - y1) * 2;

        int sy = y2 > y1 ? 1 : -1;

        drawDot(x1, y1, color);

        if (dx > dy) {
            int f = dy - dx / 2;
            int x = x1;
            int y = y1;
            while (x != x2) {
                if (f >= 0) {
                    f -= dx;
                    y += sy;
                }
                x++;
                f += dy;
                drawDot(x, y, color);
            }
        } else {
            int f = dx - dy / 2;
            int x = x1;
            int y = y1;
            while (y != y2) {
                if (f >= 0) {
                    f -= dy;
                    x++;
                }
                y += sy;
                f += dx;
                drawDot(x, y, color);
            }
        }
    }

    public void drawSimpleLine(Point p1, Point p2, Color color) {
        this.drawSimpleLine(p1.getRoundX(), p1.getRoundY(), p2.getRoundX(), p2.getRoundY(), color);
    }

    public void drawBezierCurve(Point start, Point end, Point control, Color color) {
        Point last = start;
        for (double t = 0d; t <= 1d; t += 0.01) {
            Point p0 = calcPoint(start, control, t);
            Point p1 = calcPoint(control, end, t);
            Point L = calcPoint(p0, p1, t);
            drawSimpleLine(last, L, color);
            last = L;
        }
        drawSimpleLine(last, end, color);
    }

    @SuppressWarnings("static-method")
    private Point calcPoint(Point p0, Point p1, double t) {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must be between 0 and 1.");
        }
        return new Point(p0.getX() * (1d - t) + p1.getX() * t, p0.getY() * (1d - t) + p1.getY() * t);
    }

    public void drawGlyph(GlyphPath glyph, Color color) {
        final Point old = new Point(10, 700);
        var lines = glyph.getLines();
        var onTheLines = glyph.isOnTheLines();
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var onTheLine = onTheLines.get(i);

            if (onTheLine.get(0)) {
                Point start = old.moveTo(line.get(0));
                old.setLocation(start);
                for (int j = 1; j < line.size(); j++) {
                    if (onTheLine.get(j)) {
                        Point nextPoint = old.moveTo(line.get(j));
                        drawSimpleLine(old, nextPoint, j == 1 ? Color.RED : Color.BLUE);
                        old.setLocation(nextPoint);
                        if (j == line.size() - 1) {
                            drawSimpleLine(old, start, color);
                        }
                    } else {
                        Point control = old.moveTo(line.get(j));
                        Point end = j < line.size() - 1 ? control.moveTo(line.get(++j)) : start;
                        drawBezierCurve(old, end, control, Color.GREEN);
                        old.setLocation(end == start ? control : end);
                        if (end != start && j == line.size() - 1) {
                            drawSimpleLine(old, start, color);
                        }
                    }
                }
            } else {
                Point startControl = old.moveTo(line.get(0));
                Point start = old.moveTo(line.get(0)).moveTo(line.get(1));
                old.setLocation(start);
                for (int j = 2; j < line.size(); j++) {
                    if (onTheLine.get(j)) {
                        Point nextPoint = old.moveTo(line.get(j));
                        drawSimpleLine(old, nextPoint, color);
                        old.setLocation(nextPoint);
                        if (j == line.size() - 1) {
                            drawSimpleLine(old, start, color);
                        }
                    } else {
                        Point control = old.moveTo(line.get(j));
                        Point end = j < line.size() - 1 ? control.moveTo(line.get(++j)) : start;
                        drawBezierCurve(old, end, control, color);
                        old.setLocation(j < line.size() ? end : control);
                        if (end != start && j == line.size() - 1) {
                            drawBezierCurve(old, start, startControl, color);
                        }
                    }
                }
            }
        }
    }

    public void drawPath(Path path, Color color) {
        for (int x = 0; x <= 1000; x += 5) {
            for (int y = 0; y <= 1000; y += 5) {
                drawDot(x, y, Color.BLUE);
            }
        }

        Point old = new Point(10, 700);
        drawSimpleLine(old.moveTo(-5, -5), old.moveTo(-5, 5), Color.BLACK);
        drawSimpleLine(old.moveTo(-5, -5), old.moveTo(5, -5), Color.BLACK);
        drawSimpleLine(old.moveTo(5, 5), old.moveTo(-5, 5), Color.BLACK);
        drawSimpleLine(old.moveTo(5, 5), old.moveTo(5, -5), Color.BLACK);

        var lines = path.getLines();
        for (var line : lines) {
            if (!line.isEmpty()) {
                Point start = old.moveTo(line.get(0));
                old.setLocation(start);
                drawSimpleLine(old.moveTo(-5, -5), old.moveTo(-5, 5), Color.GREEN);
                drawSimpleLine(old.moveTo(-5, -5), old.moveTo(5, -5), Color.GREEN);
                drawSimpleLine(old.moveTo(5, 5), old.moveTo(-5, 5), Color.GREEN);
                drawSimpleLine(old.moveTo(5, 5), old.moveTo(5, -5), Color.GREEN);
                for (int i = 1; i < line.size(); i++) {
                    Point p1 = old.moveTo(line.get(i));
                    drawSimpleLine(old, p1, color);
                    old.setLocation(p1);
                    drawSimpleLine(old.moveTo(-5, -5), old.moveTo(-5, 5), Color.BLUE);
                    drawSimpleLine(old.moveTo(-5, -5), old.moveTo(5, -5), Color.BLUE);
                    drawSimpleLine(old.moveTo(5, 5), old.moveTo(-5, 5), Color.BLUE);
                    drawSimpleLine(old.moveTo(5, 5), old.moveTo(5, -5), Color.BLUE);
                }
                drawSimpleLine(old, start, color);
            }
        }
    }
}
