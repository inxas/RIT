package net.inxas.rit.debug;

import java.util.Calendar;

import net.inxas.rit.widget.Window;
import net.inxas.rit.widget.event.MouseClickEvent;
import net.inxas.rit.widget.event.MouseClickListener;
import net.inxas.rit.widget.event.RITEvent;
import net.inxas.rit.widget.event.WindowEvent;
import net.inxas.rit.widget.event.WindowListener;
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

        Window window = new Window();
        window.setTitle("RIT WINDOW TEST");
        window.setBounds(new Rectangle(50, 50, 500, 500));
        window.addMouseClickListener(new MouseClickListener() {
            @Override
            public void on(RITEvent e) {

            }

            @Override
            public void on(MouseClickEvent e) {
                System.out.println(e);
            }
        });
        window.addWindowListener(new WindowListener() {
            @Override
            public void on(RITEvent e) {

            }

            @Override
            public void on(WindowEvent e) {
                System.out.println(e);
                if (e.getID() == WindowEvent.CLOSING) {
                    window.dispose();
                }
            }
        });
        window.setVisible(true);
    }
}
