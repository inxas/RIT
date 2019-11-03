package net.inxas.rit.debug;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Random;

import net.inxas.rit.RITEntry;
import net.inxas.rit.RITProjectException;
import net.inxas.rit.widget.Window;
import net.inxas.rit.widget.drawing.Color;
import net.inxas.rit.widget.event.RITEvent;
import net.inxas.rit.widget.event.WindowEvent;
import net.inxas.rit.widget.event.WindowListener;
import net.inxas.rit.widget.font.FontLoader;
import net.inxas.rit.widget.geometry.Point;
import net.inxas.rit.widget.geometry.Rectangle;

/**
 * RITをテストします。
 * 
 * @author inxas
 * @since  2019/10/22
 */
public final class Test {
    // VM -Xcheck:jni
    /**
     * メインメソッド
     * 
     * @param args 使用しません
     */
    public static void main(String[] args) {
        System.out.println("Start the RIT test.");
        System.out.println("Start time:" + Calendar.getInstance().getTime());
        // windowTest();
        fontTest();
    }

    private static void windowTest() {
        final Random random = new Random();
        new Window()
                .setTitle("RIT WINDOW TEST")
                .setBounds(new Rectangle(500, 50, 800, 500))
                .addWindowListener(new WindowListener() {
                    @Override
                    public void on(RITEvent e) {}

                    @Override
                    public void on(WindowEvent e) {
                        if (e.getID() == WindowEvent.CLOSING) {
                            ((Window) e.getSource()).dispose();
                        }
                    }
                })
                .setRepaintTrigger(RITEvent.WINDOW_EVENT_MASK | RITEvent.MOUSE_EVENT_MASK)
                .setRepaint(drawing -> {
                    drawing.drawBezierCurve(new Point(50, 50), new Point(300, 400), new Point(500, 140), Color.RED);
                })
                .setVisible(true);
    }

    private static void RITEntryTest() {
        RITEntry entry = new RITEntry("RIT_Entry_Test", Paths.get("", "..", ".."));
        try {
            entry.build();
        } catch (RITProjectException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void fontTest() {
//        try {
//            Files.list(Paths.get("C:", "Windows", "Fonts")).forEach(path -> {
//                TrueTypeAnalyzer tta = new TrueTypeAnalyzer(path);
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        TrueTypeAnalyzer mincho = new TrueTypeAnalyzer(Paths.get("", "..", "..",
//                "msmincho.ttc"));
        // TrueTypeAnalyzer kyokasho = new TrueTypeAnalyzer(Paths.get("", "..", "..",
        // "UDDigiKyokashoN-R.ttc"));
        // arial.ttf
        FontLoader loader = new FontLoader(Paths.get("", "..", "..",
                "RictyDiminishedDiscord-Regular.ttf"));
    }
}
