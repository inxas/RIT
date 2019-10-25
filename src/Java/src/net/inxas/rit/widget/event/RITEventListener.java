package net.inxas.rit.widget.event;

/**
 * RITに関係するすべてのイベントリスナのルートクラスです。
 * 
 * @author inxas
 * @since  2019/10/24
 */
public interface RITEventListener {
    /**
     * イベントが発生したことを通知します。
     * 
     * @param e RITイベント
     */
    void on(RITEvent e);
}
