package net.inxas.rit.widget.event;

/**
 * {@link MouseClickEvent}用のリスナです。
 * 
 * @author inxas
 * @since  2019/10/24
 */
public interface MouseClickListener extends RITEventListener {
    /**
     * マウスクリックイベントを通知します。
     * 
     * @param e マウスクリックイベント
     */
    void on(MouseClickEvent e);
}
