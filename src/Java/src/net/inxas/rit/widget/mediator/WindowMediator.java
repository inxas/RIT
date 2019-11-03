package net.inxas.rit.widget.mediator;

import net.inxas.rit.widget.Window;
import net.inxas.rit.widget.event.MouseClickEvent;
import net.inxas.rit.widget.event.WindowEvent;
import net.inxas.rit.widget.geometry.Area;
import net.inxas.rit.widget.geometry.Rectangle;

/**
 * ウィンドウ用メディエータです。
 * 
 * @author inxas
 * @since  2019/10/22
 */
public abstract class WindowMediator {
    protected final Window window;

    public WindowMediator(Window window) {
        this.window = window;
    }

    /**
     * ウィンドウを常に最前面に表示するかを設定します。
     * 
     * @param alwaysOnTop もし常に最前面に表示するならtrue、そうでなければfalse
     */
    public abstract void setAlwaysOnTop(boolean alwaysOnTop);

    /**
     * ウィンドウの大きさを設定します。
     * 
     * @param x      x座標
     * @param y      y座標
     * @param width  横幅
     * @param height 縦幅
     */
    public abstract void setBounds(int x, int y, int width, int height);

    /**
     * クライアント領域を基にウィンドウの大きさを設定します。
     * 
     * @param x      x座標
     * @param y      y座標
     * @param width  横幅
     * @param height 縦幅
     */
    public abstract void setClientBounds(int x, int y, int width, int height);

    /**
     * ウィンドウのタイトルを設定します。
     * 
     * @param newTitle タイトル
     */
    public abstract void setTitle(String newTitle);

    /**
     * ウィンドウの可視状態を設定します。
     * 
     * @param visible もし表示するならtrue、そうでなければfalse
     */
    public abstract void setVisible(boolean visible);

    /**
     * ウィンドウを背後に送り、フォーカスを外します。 もし最前面表示であれば解除されます。
     */
    public abstract void toBack();

    /**
     * ウィンドウを最前面に送ります。
     */
    public abstract void toFront();

    /**
     * このウィンドウが最前面表示であるかを返します。
     * 
     * @return もし最前面表示であればtrue、そうでなければfalse
     */
    public abstract boolean isAlwaysOnTop();

    /**
     * このウィンドウの大きさを返します。
     * 
     * @return このウィンドウの大きさ
     */
    public abstract Rectangle getBounds();

    /**
     * このウィンドウのクライアント領域の大きさを返します。
     * 
     * @return このウィンドウのクライアント領域の大きさ
     */
    public abstract Area getClientArea();

    /**
     * このウィンドウのタイトルを返します。
     * 
     * @return このウィンドウのタイトル
     */
    public abstract String getTitle();

    /**
     * このウィンドウの表示状態を返します。
     * 
     * @return もし表示状態であればtrue、そうでなければfalse
     */
    public abstract boolean isVisible();

    /**
     * ウィンドウを削除します。 プログラム自体は終了しません。
     */
    public abstract void dispose();

    /**
     * ウィンドウの表示内容を変更します。
     */
    public abstract void paint();

    /**
     * 描画更新を行います。
     */
    public abstract void reflect();

    /**
     * 各環境に合わせて{@link net.inxas.rit.widget.WidgetManager}内で呼び出されます。
     */
    public abstract void nativeCallback();

    /**
     * マウスクリックイベントが発生したことをウィンドウに通知するため、OSなどから呼び出されます。
     * 
     * @param mouseButton マウスボタン
     * @param mode        状態
     * @param virtualKey  仮想キー
     * @param x           x座標
     * @param y           y座標
     */
    public void processMouseClick(int mouseButton, int mode, int virtualKey, int x, int y) {
        window.processMouseClickEvent(new MouseClickEvent(window, mouseButton, mode, virtualKey, x, y));
    }

    /**
     * ウィンドウイベントが発生したことをウィンドウに通知するため、OSなどから呼び出されます。
     * 
     * @param id ID
     */
    public void processWindow(int id) {
        window.processWindowEvent(new WindowEvent(window, id));
    }
}
