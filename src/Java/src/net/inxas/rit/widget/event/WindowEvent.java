package net.inxas.rit.widget.event;

import net.inxas.rit.widget.AbstractWindow;

/**
 * ウィンドウイベントです。
 * 
 * @author inxas
 * @since  2019/10/25
 */
public class WindowEvent extends RITEvent {
    public static final int ACTIVATED = 0;
    public static final int DEACTIVATED = 1;
    public static final int CLOSING = 2;
    public static final int CLOSED = 3;
    public static final int ICONIFIED = 4;
    public static final int MAXIMIZED = 5;
    public static final int GAINED_FOCUS = 6;
    public static final int LOST_FOCUS = 7;
    public static final int RESIZED = 8;
    public static final int RESTORED = 9;

    private int id;

    public WindowEvent(AbstractWindow source, int id) {
        super(source, WINDOW_EVENT_MASK);
        this.id = id;
    }

    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        String IDName = "unknown";
        switch (id) {
        case ACTIVATED:
            IDName = "activated";
            break;
        case DEACTIVATED:
            IDName = "deactivated";
            break;
        case CLOSING:
            IDName = "closing";
            break;
        case CLOSED:
            IDName = "closed";
            break;
        case ICONIFIED:
            IDName = "iconified";
            break;
        case MAXIMIZED:
            IDName = "maximized";
            break;
        case GAINED_FOCUS:
            IDName = "gainedFocus";
            break;
        case LOST_FOCUS:
            IDName = "lostFocus";
            break;
        case RESIZED:
            IDName = "resized";
            break;
        case RESTORED:
            IDName = "restored";
            break;
        }
        return "net.inxas.rit.widget.event.WindowEvent[ID=" + id + ",IDName=" + IDName + "]";
    }
}
