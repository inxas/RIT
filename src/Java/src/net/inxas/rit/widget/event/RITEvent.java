package net.inxas.rit.widget.event;

/**
 * RITに関係するすべてのイベントのルートクラスです。
 * 
 * @author inxas
 * @since  2019/10/24
 */
public class RITEvent {
    public static final int MOUSE_EVENT_MASK = 1;
    public static final int MOUSE_MOTION_EVENT_MASK = 1 << 1;
    public static final int MOUSE_WHEEL_EVENT_MASK = 1 << 2;

    private Object source;

    /**
     * RITイベントを構築します。
     * 
     * @param source イベントの発生元
     */
    public RITEvent(Object source) {
        this.source = source;
    }

    /**
     * このイベントの発生元を取得します。
     * 
     * @return イベントの発生元
     */
    public Object getSource() {
        return source;
    }
}
