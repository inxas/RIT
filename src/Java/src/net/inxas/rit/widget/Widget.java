package net.inxas.rit.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.inxas.rit.widget.drawing.Drawing;
import net.inxas.rit.widget.event.MouseClickEvent;
import net.inxas.rit.widget.event.MouseClickListener;
import net.inxas.rit.widget.event.RITEvent;
import net.inxas.rit.widget.geometry.Point;
import net.inxas.rit.widget.geometry.Rectangle;

/**
 * RITのGUIを構成する部品クラス群のルートクラスです。
 * set系、add系、remove系メソッドは原則thisを返します。これによってメソッドチェーンを実現できます。
 * 
 * @author inxas
 * @since  2019/10/22
 */
public abstract class Widget {
    private Rectangle bounds;

    private List<Widget> children;
    private List<MouseClickListener> mouseClickListeners;

    private Widget parent;

    private final Drawing drawing;
    private Paint paint;
    private long repaintTrigger;

    public Widget() {
        children = Collections.synchronizedList(new ArrayList<>());
        mouseClickListeners = Collections.synchronizedList(new ArrayList<>());
        drawing = new Drawing(this);
        repaintTrigger = 0;
    }

    /**
     * 現在追加されている子のリストを返します。
     * 返されるリストは内部で使用されているリストのシャローコピーです。
     * 
     * @return 子のリスト
     */
    public List<Widget> getChildren() {
        return new ArrayList<Widget>(children);
    }

    /**
     * 自分の親を返します。
     * もし親がなければnullを返します。
     * 
     * @return 自分の親
     */
    public Widget getParent() {
        return parent;
    }

    /**
     * 部品を追加します。
     * もし追加する部品がトップレベルなウィンドウであれば何もしません。
     * 
     * @param  widget 部品
     * @return        this
     */
    public Widget add(Widget widget) {
        if (widget == null || widget instanceof AbstractWindow)
            return this;

        if (widget.getParent() != null) {
            widget.getParent().remove(widget);
        }

        children.add(widget);
        widget.parent = this;
        return this;
    }

    /**
     * 部品を削除します。
     * 
     * @param  widget 部品
     * @return        this
     */
    public Widget remove(Widget widget) {
        if (widget == null)
            return this;
        children.remove(widget);
        widget.parent = null;
        return this;
    }

    /**
     * 大きさを設定します。
     * 
     * @param  bounds 大きさ
     * @return        this
     */
    public Widget setBounds(Rectangle bounds) {
        this.bounds = bounds;
        return this;
    }

    /**
     * 大きさを取得します。
     * 
     * @return 大きさ
     */
    public Rectangle getBounds() {
        return bounds;
    }

    public int getX() {
        return getBounds().getRoundX();
    }

    public int getY() {
        return getBounds().getRoundY();
    }

    public int getWidth() {
        return getBounds().getRoundWidth();
    }

    public int getHeight() {
        return getBounds().getRoundHeight();
    }

    /**
     * 自分の親に対する自分の位置を返します。
     * 
     * @return 親に対する自分の位置
     */
    public Point getLocation() {
        Rectangle bounds = getBounds();
        return new Point(bounds.getX(), bounds.getY());
    }

    /**
     * トップレベルなウィンドウ内での自分の位置を返します。
     * もし、先祖にトップレベルなウィンドウがなければnullを返します。
     * 
     * @return トップレベルなウィンドウ内での自分の位置
     */
    public Point getLocationOnWindow() {
        Point result = getLocation();
        Widget widget = this;
        while (true) {
            result.move(widget.getX(), widget.getY());
            if ((widget = widget.getParent()) == null)
                break;
        }
        return widget instanceof AbstractWindow ? result : null;
    }

    /**
     * 自分の属するウィンドウを調べます。
     * 
     * @return 自分の属するウィンドウ もしなければnull
     */
    public AbstractWindow getWindow() {
        Widget widget = this;
        while (!(widget instanceof AbstractWindow)) {
            widget = widget.getParent();
            if (widget == null)
                return null;
        }
        return (AbstractWindow) widget;
    }

    public Drawing getDrawing() {
        return drawing;
    }

    /**
     * リペイントを呼び出すトリガーを設定します。
     * トリガーは{@link net.inxas.rit.widget.event.RITEvent}のマスクです。
     * 各マスクを|でつなげて設定します。
     * 
     * @param  repaintTrigger 設定するトリガー
     * @return                this
     */
    public Widget setRepaintTrigger(long repaintTrigger) {
        this.repaintTrigger = repaintTrigger;
        return this;
    }

    /**
     * リペイントを呼び出すトリガーを追加します。
     * 
     * @param  repaintTrigger 追加するトリガー
     * @return                this
     */
    public Widget addRepaintTrigger(long repaintTrigger) {
        this.repaintTrigger |= repaintTrigger;
        return this;
    }

    /**
     * リペイントを呼び出すトリガーを外します。
     * 
     * @param  repaintTrigger 外すトリガー
     * @return                this
     */
    public Widget removeRepaintTrigger(long repaintTrigger) {
        this.repaintTrigger &= repaintTrigger;
        this.repaintTrigger ^= repaintTrigger;
        return this;
    }

    /**
     * 設定しているリペイントのトリガーを返します。
     * 
     * @return リペイントのトリガー
     */
    public long getRepaintTrigger() {
        return repaintTrigger;
    }

    /**
     * 描画メソッドを設定します。
     * 
     * @param  paint 描画
     * @return       this
     */
    public Widget setRepaint(Paint paint) {
        this.paint = paint;
        return this;
    }

    /**
     * 設定されている描画メソッドを返します。
     * 
     * @return Paint
     */
    public Paint getRepaint() {
        return paint;
    }

    /**
     * {@link #setRepaint(Paint)}で設定したメソッドを呼び出します。
     * 普通はトリガーに掛かったときに呼び出されるため、ユーザーは使用しません。
     */
    public final void repaint() {
        AbstractWindow window = getWindow();
        if ((this instanceof AbstractWindow && ((AbstractWindow) this).isDisposed())
                || (window != null && window.isDisposed())) {
            return;
        }
        Drawing drawing = getDrawing();
        drawing.beginDraw();
        getRepaint().paint(drawing);
        drawing.endDraw();
        if (window != null) {
            window.paint();
            window.reflect();
        }
    }

    /**
     * マウスクリックリスナを追加します。
     * 
     * @param  e リスナ
     * @return   this
     */
    public Widget addMouseClickListener(MouseClickListener listener) {
        if (listener != null) {
            mouseClickListeners.add(listener);
        }
        return this;
    }

    /**
     * マウスクリックリスナを外します。
     * 
     * @param  e リスナ
     * @return   this
     */
    public Widget removeMouseClickListener(MouseClickListener listener) {
        if (listener != null) {
            mouseClickListeners.remove(listener);
        }
        return this;
    }

    /**
     * マウスイベントが発生したことを自分自身と子に通知します。
     * ユーザーはこのメソッドを使用して、疑似的にマウスクリックを行うことができます。
     */
    public void processMouseClickEvent(MouseClickEvent e) {
        for (MouseClickListener listener : mouseClickListeners) {
            listener.on(e);
        }
        if ((getRepaintTrigger() & RITEvent.MOUSE_EVENT_MASK) != 0 && getRepaint() != null) {
            repaint();
        }
    }
}
