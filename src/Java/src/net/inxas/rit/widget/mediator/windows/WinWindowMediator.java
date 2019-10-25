package net.inxas.rit.widget.mediator.windows;

import net.inxas.rit.widget.WidgetManager;
import net.inxas.rit.widget.Window;
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

    private native void toBack(long wndPtr);

    private native void toFront(long wndPtr);

    private native void nextMessage(long wndPtr);

    private native void dispose(long wndPtr);

    public WinWindowMediator(Window window) {
        super(window);
        WidgetManager.getInstance().dispatchTask(() -> wndPtr = create());
        while (wndPtr == 0);
    }

    @Override
    public void setAlwaysOnTop(boolean alwaysOnTop) {
        setAlwaysOnTop(wndPtr, alwaysOnTop);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        setBounds(wndPtr, x, y, width, height);
    }

    @Override
    public void setClientBounds(int x, int y, int width, int height) {
        setClientBounds(wndPtr, x, y, width, height);
    }

    @Override
    public void setTitle(String newTitle) {
        setTitle(wndPtr, newTitle);
    }

    @Override
    public void setVisible(boolean visible) {
        setVisible(wndPtr, visible);
    }

    @Override
    public void toBack() {
        toBack(wndPtr);
    }

    @Override
    public void toFront() {
        toFront(wndPtr);
    }

    @Override
    public boolean isAlwaysOnTop() {
        return false;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public Rectangle getClientBounds() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public void nativeCallback() {
        nextMessage(wndPtr);
    }

    @Override
    public void dispose() {
        dispose(wndPtr);
    }
}
