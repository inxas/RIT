package net.inxas.rit.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.inxas.rit.widget.event.WindowEvent;
import net.inxas.rit.widget.event.WindowListener;

/**
 * {@link Widget}を描画するトップレベルなウィンドウです。
 * 
 * @author inxas
 * @since  2019/10/22
 */
public abstract class AbstractWindow extends Widget {
    private List<WindowListener> windowListeners;

    /**
     * マネージャにインスタンスを登録します。
     */
    public AbstractWindow() {
        super();
        windowListeners = Collections.synchronizedList(new ArrayList<>());
        WidgetManager.getInstance().register(this);
    }

    /**
     * ウィンドウのタイトルを設定します。
     * 
     * @param title タイトル
     */
    public abstract void setTitle(String title);

    /**
     * ウィンドウの表示状態を変更します。
     * 
     * @param visible 表示させるならtrue、隠すならfalse
     */
    public abstract void setVisible(boolean visible);

    /**
     * ウィンドウを削除します。
     * {@link AbstractWindow}での実装ではマネージャの管理を解除するだけです。
     */
    public void dispose() {
        WidgetManager.getInstance().unregister(this);
    }

    /**
     * ウィンドウを管理する上で内部的に呼び出されます。 ユーザーはオーバーライドしないでください。
     */
    protected abstract void nativeCallback();

    /**
     * ウィンドウリスナを追加します。
     * 
     * @param listener リスナ
     */
    public void addWindowListener(WindowListener listener) {
        if (listener != null) {
            windowListeners.add(listener);
        }
    }

    /**
     * ウィンドウリスナを外します。
     * 
     * @param listener リスナ
     */
    public void removeWindowListener(WindowListener listener) {
        if (listener != null) {
            windowListeners.remove(listener);
        }
    }

    /**
     * ウィンドウイベントが発生したことを通知します。
     * 
     * @param e ウィンドウイベント
     */
    public void processWindowEvent(WindowEvent e) {
        for (WindowListener listener : windowListeners) {
            listener.on(e);
        }
    }
}
