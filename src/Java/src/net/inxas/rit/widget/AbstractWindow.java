package net.inxas.rit.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.inxas.rit.widget.event.RITEvent;
import net.inxas.rit.widget.event.WindowEvent;
import net.inxas.rit.widget.event.WindowListener;
import net.inxas.rit.widget.geometry.Area;

/**
 * {@link Widget}を描画するトップレベルなウィンドウです。
 * {@link #dispose()}を呼び出した後は、ウィンドウへの操作を受け付けません。
 * 
 * @author inxas
 * @since  2019/10/22
 */
public abstract class AbstractWindow extends Widget {
    private List<WindowListener> windowListeners;
    private boolean disposed;

    /**
     * マネージャにインスタンスを登録します。
     */
    public AbstractWindow() {
        super();
        disposed = false;
        windowListeners = Collections.synchronizedList(new ArrayList<>());
        WidgetManager.getInstance().register(this);
    }

    /**
     * ウィンドウのタイトルを設定します。
     * 
     * @param  title タイトル
     * @return       this
     */
    public abstract AbstractWindow setTitle(String title);

    /**
     * ウィンドウの表示状態を変更します。
     * 
     * @param  visible 表示させるならtrue、隠すならfalse
     * @return         this
     */
    public abstract AbstractWindow setVisible(boolean visible);

    /**
     * ウィンドウのクライアント領域の大きさを取得します。
     * 
     * @return クライアント領域の大きさ
     */
    public abstract Area getClientArea();

    /**
     * これ以降の描画内容を確定します。
     * 
     * @return this
     */
    public abstract AbstractWindow paint();

    /**
     * 強制的に再描画させます。
     * そもそも、ウィジェットの大きさが変化したときなどに描画をするので、
     * 通常のアプリケーションの場合は使用する必要はありません。
     * ゲームなどの常に描画を更新する必要のあるアプリケーションの場合に使用します。
     * 
     * @return this
     */
    public abstract AbstractWindow reflect();

    /**
     * ウィンドウを削除します。
     * {@link AbstractWindow}での実装ではマネージャの管理を解除するだけです。
     */
    public void dispose() {
        disposed = true;
        WidgetManager.getInstance().unregister(this);
    }

    /**
     * ウィンドウが消去済みかを返します。
     * 
     * @return もしウィンドウが消去済みならtrue、そうでなければfalse
     */
    public boolean isDisposed() {
        return disposed;
    }

    /**
     * ウィンドウを管理する上で内部的に呼び出されます。 ユーザーはオーバーライドしないでください。
     */
    protected abstract void nativeCallback();

    /**
     * ウィンドウリスナを追加します。
     * 
     * @param  listener リスナ
     * @return          this
     */
    public AbstractWindow addWindowListener(WindowListener listener) {
        if (listener != null) {
            windowListeners.add(listener);
        }
        return this;
    }

    /**
     * ウィンドウリスナを外します。
     * 
     * @param  listener リスナ
     * @return          this
     */
    public AbstractWindow removeWindowListener(WindowListener listener) {
        if (listener != null) {
            windowListeners.remove(listener);
        }
        return this;
    }

    /**
     * ウィンドウイベントが発生したことを通知します。
     * 
     * @param e ウィンドウイベント
     */
    public void processWindowEvent(WindowEvent e) {
        System.out.println(e);
        for (WindowListener listener : windowListeners) {
            listener.on(e);
        }
        if ((getRepaintTrigger() & RITEvent.WINDOW_EVENT_MASK) != 0 && getRepaint() != null) {
            repaint();
        }
    }
}
