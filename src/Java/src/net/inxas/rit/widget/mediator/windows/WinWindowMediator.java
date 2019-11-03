package net.inxas.rit.widget.mediator.windows;

import net.inxas.rit.widget.Widget;
import net.inxas.rit.widget.WidgetManager;
import net.inxas.rit.widget.Window;
import net.inxas.rit.widget.drawing.Color;
import net.inxas.rit.widget.geometry.Area;
import net.inxas.rit.widget.geometry.Point;
import net.inxas.rit.widget.geometry.Rectangle;
import net.inxas.rit.widget.mediator.WindowMediator;

/**
 * {@link WindowMediator}のWindows用実装です。
 * 内部でWin32APIを使用しています。
 * 
 * @author inxas
 * @since  2019/10/22
 */
public class WinWindowMediator extends WindowMediator {
    private volatile long wndPtr;

    private native long create();

    private native void setAlwaysOnTop(long wndPtr, boolean alwaysOnTop);

    private native void setBounds(long wndPtr, int x, int y, int width, int height);

    private native void setClientBounds(long wndPtr, int x, int y, int width, int height);

    private native void setTitle(long wndPtr, String title);

    private native void setVisible(long wndPtr, boolean visible);

    private native boolean isAlwaysOnTop(long wndPtr);

    private native Rectangle getBounds(long wndPtr);

    private native Area getClientArea(long wndPtr);

    private native String getTitle(long wndPtr);

    private native boolean isVisible(long wndPtr);

    private native void toBack(long wndPtr);

    private native void toFront(long wndPtr);

    private native void nextMessage(long wndPtr);

    private native void dispose(long wndPtr);

    private native void paint(long wndPtr, int[] bytes, int height);

    private native void reflect(long wndPtr);

    private boolean disposed;

    public WinWindowMediator(Window window) {
        super(window);
        WidgetManager.getInstance().dispatchTask(() -> wndPtr = create());
        while (wndPtr == 0) {
            Thread.yield();
        }
    }

    @Override
    public void setAlwaysOnTop(boolean alwaysOnTop) {
        if (!disposed)
            setAlwaysOnTop(wndPtr, alwaysOnTop);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        if (!disposed)
            setBounds(wndPtr, x, y, width, height);
    }

    @Override
    public void setClientBounds(int x, int y, int width, int height) {
        if (!disposed)
            setClientBounds(wndPtr, x, y, width, height);
    }

    @Override
    public void setTitle(String newTitle) {
        if (!disposed)
            setTitle(wndPtr, newTitle);
    }

    @Override
    public void setVisible(boolean visible) {
        if (!disposed)
            setVisible(wndPtr, visible);
    }

    @Override
    public void toBack() {
        if (!disposed)
            toBack(wndPtr);
    }

    @Override
    public void toFront() {
        if (!disposed)
            toFront(wndPtr);
    }

    @Override
    public boolean isAlwaysOnTop() {
        if (disposed)
            return false;
        else
            return isAlwaysOnTop(wndPtr);
    }

    @Override
    public Rectangle getBounds() {
        if (disposed)
            return null;
        else
            return getBounds(wndPtr);
    }

    @Override
    public Area getClientArea() {
        if (disposed)
            return null;
        else
            return getClientArea(wndPtr);
    }

    @Override
    public String getTitle() {
        if (disposed)
            return null;
        else
            return getTitle(wndPtr);
    }

    @Override
    public boolean isVisible() {
        if (disposed)
            return false;
        else
            return isVisible(wndPtr);
    }

    @Override
    public void nativeCallback() {
        if (!disposed)
            nextMessage(wndPtr);
    }

    @Override
    public void dispose() {
        if (!disposed)
            dispose(wndPtr);
        disposed = true;
    }

    @Override
    public void paint() {
        if (disposed)
            return;
        Color[][] cache = window.getDrawing().getCache();
        if (cache == null)
            return;
        Color[][] deta = new Color[cache.length][cache[0].length];
        paint(window, deta);
        int width = deta.length;
        int height = deta[0].length;
        int[] paint = new int[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = deta[x][y];
                paint[x * height + y] = color.getBlue() | color.getGreen() << 8 | color.getRed() << 16;
            }
        }
        paint(wndPtr, paint, height);
    }

    private void paint(Widget parent, Color[][] deta) {
        Color[][] cache = parent.getDrawing().getCache();
        int width = cache.length;
        int height = cache[0].length;

        Point topLocation = parent.getLocationOnWindow();
        if (topLocation == null)
            topLocation = new Point(0, 0);
        int tx = (int) topLocation.getX();
        int ty = (int) topLocation.getY();

        int startX, startY, endX, endY;
        startX = tx < 0 ? -tx : 0;
        startY = ty < 0 ? -ty : 0;
        endX = (tx + width) >= deta.length ? deta.length + startX : width;
        endY = (ty + height) >= deta[0].length ? deta[0].length + startY : height;

        for (int i = startX; i < endX; i++) {
            int x = i + tx;
            for (int j = startY; j < endY; j++) {
                int y = j + ty;
                Color under = deta[x][y];
                Color top = cache[i][j];
                deta[x][y] = Color.coating(under, top);
            }
        }
        for (Widget child : parent.getChildren()) {
            paint(child, deta);
        }
    }

    @Override
    public void reflect() {
        if (!disposed)
            reflect(wndPtr);
    }
}
