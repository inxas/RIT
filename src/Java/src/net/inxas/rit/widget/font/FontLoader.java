package net.inxas.rit.widget.font;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import net.inxas.rit.widget.Window;
import net.inxas.rit.widget.drawing.Color;
import net.inxas.rit.widget.event.RITEvent;
import net.inxas.rit.widget.event.WindowEvent;
import net.inxas.rit.widget.event.WindowListener;
import net.inxas.rit.widget.geometry.GlyphPath;
import net.inxas.rit.widget.geometry.Rectangle;

/**
 * フォントファイルを読み込みます。
 * 
 * @author inxas
 * @since  2019/10/30
 */
public class FontLoader {
    private final Path path;
    private byte[] allBytes;

    public FontLoader(Path path) {
        if (path == null) {
            throw new NullPointerException("path must not be null!");
        }
        if (Files.notExists(path)) {
            throw new IncorrectFontFormatException("File does not exist.");
        }
        if (Files.isDirectory(path)) {
            throw new IncorrectFontFormatException("path must be a file.");
        }
        this.path = path;
        loadByFormat();
    }

    private void loadByFormat() {
        try {
            allBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new IncorrectFontFormatException("Font loading failed.", e);
        }
        byte[] format = Arrays.copyOfRange(allBytes, 0, 4);
        if (new String(format).equals("ttcf")) {

        } else {
            TrueTypeFontLoader ttfLoader = new TrueTypeFontLoader(allBytes);
            GlyphPath glyph = ttfLoader.getGlyph('A');
            Window window = new Window()
                    .setBounds(new Rectangle(500, 50, 800, 900))
                    .setTitle("TTF Test")
                    .setRepaintTrigger(RITEvent.WINDOW_EVENT_MASK)
                    .setRepaint(drawing -> {
                        drawing.drawGlyph(glyph, Color.RED);
                    })
                    .setVisible(true);
            window.addWindowListener(new WindowListener() {
                @Override
                public void on(RITEvent e) {}

                @Override
                public void on(WindowEvent e) {
                    if (e.getID() == WindowEvent.CLOSING) {
                        window.dispose();
                    }
                }
            });
        }
    }
}
