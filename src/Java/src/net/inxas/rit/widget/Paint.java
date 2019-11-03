package net.inxas.rit.widget;

import net.inxas.rit.widget.drawing.Drawing;

/**
 * {@link Widget#setRepaint(Paint)}で使用します。
 * 
 * @author inxas
 * @since  2019/10/27
 */
public interface Paint {
    /**
     * 描画を行います。
     * 描画準備のできた{@link Drawing}が渡されます。
     * {@link Drawing#endDraw()}は呼び出さないでください。
     * 
     * @param drawing Drawing
     */
    void paint(Drawing drawing);
}
