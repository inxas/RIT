package net.inxas.rit.widget;

import net.inxas.rit.RITUnsupportedException;
import net.inxas.rit.widget.event.WindowListener;
import net.inxas.rit.widget.geometry.Area;
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
    public Window setTitle(String title) {
        mediator.setTitle(title);
        return this;
    }

    @Override
    public Window setVisible(boolean visible) {
        mediator.setVisible(visible);
        return this;
    }

    /**
     * ウィンドウを常に最前面に表示するかを設定します。
     * 
     * @param  alwaysOnTop もし常に最前面に表示するならばtrue、そうでなければfalse
     * @return             this
     */
    public Window setAlwaysOnTop(boolean alwaysOnTop) {
        mediator.setAlwaysOnTop(alwaysOnTop);
        return this;
    }

    /**
     * 非クライアント領域を含むウィンドウ全体の大きさを設定します。
     * 
     * @param  ウィンドウを大きさ
     * @return           this
     */
    @Override
    public Window setBounds(Rectangle bounds) {
        mediator.setBounds(bounds.getRoundX(), bounds.getRoundY(), bounds.getRoundWidth(), bounds.getRoundHeight());
        return this;
    }

    @Override
    public Rectangle getBounds() {
        return mediator.getBounds();
    }

    @Override
    public Area getClientArea() {
        return mediator.getClientArea();
    }

    @Override
    public Window paint() {
        mediator.paint();
        return this;
    }

    @Override
    public Window reflect() {
        mediator.reflect();
        return this;
    }

    @Override
    public Window addWindowListener(WindowListener listener) {
        super.addWindowListener(listener);
        return this;
    }

    @Override
    public Window setRepaintTrigger(long repaintTrigger) {
        super.setRepaintTrigger(repaintTrigger);
        return this;
    }

    @Override
    public Window setRepaint(Paint paint) {
        super.setRepaint(paint);
        return this;
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
