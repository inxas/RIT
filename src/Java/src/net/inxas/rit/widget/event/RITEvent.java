package net.inxas.rit.widget.event;

/**
 * RITに関係するすべてのイベントのルートクラスです。
 * 
 * @author inxas
 * @since  2019/10/24
 */
public class RITEvent {
    public static final long MOUSE_EVENT_MASK = 1;
    public static final long MOUSE_MOTION_EVENT_MASK = 1 << 1;
    public static final long MOUSE_WHEEL_EVENT_MASK = 1 << 2;
    public static final long WINDOW_EVENT_MASK = 1 << 3;

    private Object source;
    private long mask;

    /**
     * RITイベントを構築します。
     * 
     * @param source イベントの発生元
     * @param mask   このイベントのマスク
     */
    public RITEvent(Object source, long mask) {
        this.source = source;
        this.mask = mask;
    }

    /**
     * このイベントの発生元を取得します。
     * 
     * @return イベントの発生元
     */
    public Object getSource() {
        return source;
    }

    /**
     * マスクを返します。
     * 
     * @return このイベントのマスク
     */
    public long getMask() {
        return mask;
    }
}
