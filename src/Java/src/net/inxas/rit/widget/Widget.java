package net.inxas.rit.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.inxas.rit.widget.event.MouseClickEvent;
import net.inxas.rit.widget.event.MouseClickListener;
import net.inxas.rit.widget.geometry.Rectangle;

/**
 * RITのGUIを構成する部品クラス群のルートクラスです。
 * 
 * @author inxas
 * @since  2019/10/22
 */
public abstract class Widget {
    private Rectangle bounds;

    private List<Widget> children;
    private List<MouseClickListener> mouseClickListeners;

    public Widget() {
        children = Collections.synchronizedList(new ArrayList<>());
        mouseClickListeners = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * 部品を追加します。
     * 
     * @param widget 部品
     */
    public void add(Widget widget) {
        if (widget instanceof AbstractWindow) {

        } else {
            children.add(widget);
        }
    }

    /**
     * 大きさを設定します。
     * 
     * @param bounds 大きさ
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    /**
     * 大きさを取得します。
     * 
     * @return 大きさ
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * マウスクリックリスナを追加します。
     * 
     * @param e リスナ
     */
    public void addMouseClickListener(MouseClickListener listener) {
        if (listener != null) {
            mouseClickListeners.add(listener);
        }
    }

    /**
     * マウスクリックリスナを外します。
     * 
     * @param e リスナ
     */
    public void removeMouseClickListener(MouseClickListener listener) {
        if (listener != null) {
            mouseClickListeners.remove(listener);
        }
    }

    /**
     * マウスイベントが発生したことを自分自身と子に通知します。
     * ユーザーはこのメソッドを使用して、疑似的にマウスクリックを行うことができます。
     */
    public void processMouseClickEvent(MouseClickEvent e) {
        for (MouseClickListener listener : mouseClickListeners) {
            listener.on(e);
        }
    }
}
