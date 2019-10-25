package net.inxas.rit.widget;

import net.inxas.rit.RITUnsupportedException;
import net.inxas.rit.widget.geometry.Rectangle;
import net.inxas.rit.widget.mediator.Mediator;
import net.inxas.rit.widget.mediator.WindowMediator;

/**
 * 通常のウィンドウです。
 * 
 * @author inxas
 * @since  2019/10/22
 */
public class Window extends AbstractWindow {
    private final WindowMediator mediator;

    public Window() throws RITUnsupportedException {
        mediator = Mediator.createWindowMediator(this);
    }

    @Override
    public void setTitle(String title) {
        mediator.setTitle(title);
    }

    @Override
    public void setVisible(boolean visible) {
        mediator.setVisible(visible);
    }

    /**
     * ウィンドウを常に最前面に表示するかを設定します。
     * 
     * @param alwaysOnTop もし常に最前面に表示するならばtrue、そうでなければfalse
     */
    public void setAlwaysOnTop(boolean alwaysOnTop) {
        mediator.setAlwaysOnTop(alwaysOnTop);
    }

    /**
     * 非クライアント領域を含むウィンドウ全体の大きさを設定します。
     * 
     * @param ウィンドウを大きさ
     */
    @Override
    public void setBounds(Rectangle bounds) {
        super.setBounds(bounds);
        mediator.setBounds(bounds.getRoundX(), bounds.getRoundY(), bounds.getRoundWidth(), bounds.getRoundHeight());
    }

    @Override
    public void dispose() {
        mediator.dispose();
        super.dispose();
    }

    @Override
    protected void nativeCallback() {
        if (mediator != null) {
            mediator.nativeCallback();
        }
    }
}
