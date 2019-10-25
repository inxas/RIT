package net.inxas.rit.widget.event;

import net.inxas.rit.widget.Widget;

/**
 * マウスクリックイベントです。
 * 
 * @author inxas
 * @since  2019/10/24
 */
public class MouseClickEvent extends RITEvent {
    public static final int LEFT_BUTTON = 0;
    public static final int RIGHT_BUTTON = 1;
    public static final int CENTER_BUTTON = 2;
    public static final int X_BUTTON_1 = 3;
    public static final int X_BUTTON_2 = 4;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int CLICK = 2;
    public static final int DOUBLE_CLICK = 3;

    public static final int VK_LEFT_BUTTON = 1;
    public static final int VK_RIGHT_BUTTON = 1 << 1;
    public static final int VK_CENTER_BUTTON = 1 << 2;
    public static final int VK_X_BUTTON_1 = 1 << 3;
    public static final int VK_X_BUTTON_2 = 1 << 4;
    public static final int VK_SHIFT_KEY = 1 << 5;
    public static final int VK_CONTROL_KEY = 1 << 6;

    private int button;

    private int mode;

    private boolean pressLB, pressRB, pressCB, pressXB1, pressXB2, pressSK, pressCK;

    private int x, y;

    /**
     * マウスイベントを構築します。
     * 
     * @param source     イベントの発生元
     * @param button     ボタンの種類
     * @param mode       ボタンの状態
     * @param virtualKey 同時に押されている仮想キーの論理和
     * @param x          x座標
     * @param y          y座標
     */
    public MouseClickEvent(Widget source, int button, int mode, int virtualKey, int x, int y) {
        super(source);
        this.button = button;
        this.mode = mode;
        pressLB = (virtualKey & VK_LEFT_BUTTON) != 0;
        pressRB = (virtualKey & VK_RIGHT_BUTTON) != 0;
        pressCB = (virtualKey & VK_CENTER_BUTTON) != 0;
        pressXB1 = (virtualKey & VK_X_BUTTON_1) != 0;
        pressXB2 = (virtualKey & VK_X_BUTTON_2) != 0;
        pressSK = (virtualKey & VK_SHIFT_KEY) != 0;
        pressCK = (virtualKey & VK_CONTROL_KEY) != 0;
        this.x = x;
        this.y = y;
    }

    /**
     * どのマウスボタンによるイベントなのかを取得します。
     * もし左ボタンなら{@link #LEFT_BUTTON}、
     * もし右ボタンなら{@link #RIGHT_BUTTON}、
     * もし中央ボタンなら{@link #CENTER_BUTTON}、
     * それ以外のボタンなら{@link #X_BUTTON_1}もしくは{@link #X_BUTTON_2}
     * を返します。
     * 
     * @return ボタンの種類
     */
    public int getButton() {
        return button;
    }

    /**
     * マウスの状態を取得します。
     * もしマウスを離す動作であれば{@link #UP}、
     * もしマウスを押す動作であれば{@link #DOWN}、
     * もしマウスをクリックする動作であれば{@link #CLICK}、
     * もしマウスをダブルクリックする動作であれば{@link #DOUBLE_CLICK}
     * を返します。
     * 
     * @return ボタンの状態
     */
    public int getMode() {
        return mode;
    }

    /**
     * マウスの左ボタンを同時に押しているかを取得します。
     * 
     * @return もし押していたらtrue、そうでなければfalse
     */
    public boolean withLeftButton() {
        return pressLB;
    }

    /**
     * マウスの右ボタンを同時に押しているかを取得します。
     * 
     * @return もし押していたらtrue、そうでなければfalse
     */
    public boolean withRightButton() {
        return pressRB;
    }

    /**
     * マウスの中央ボタンを同時に押しているかを取得します。
     * 
     * @return もし押していたらtrue、そうでなければfalse
     */
    public boolean withCenterButton() {
        return pressCB;
    }

    /**
     * マウスのXボタン1を同時に押しているかを取得します。
     * 
     * @return もし押していたらtrue、そうでなければfalse
     */
    public boolean withXButton1() {
        return pressXB1;
    }

    /**
     * マウスのXボタン2を同時に押しているかを取得します。
     * 
     * @return もし押していたらtrue、そうでなければfalse
     */
    public boolean withXButton2() {
        return pressXB2;
    }

    /**
     * シフトキーを同時に押しているかを取得します。
     * 
     * @return もし押していたらtrue、そうでなければfalse
     */
    public boolean withShiftKey() {
        return pressSK;
    }

    /**
     * コントロールキーを同時に押しているかを取得します。
     * 
     * @return もし押していたらtrue、そうでなければfalse
     */
    public boolean withControlKey() {
        return pressCK;
    }

    /**
     * イベントが発生したときのx座標を取得します。
     * 
     * @return x座標
     */
    public int getX() {
        return x;
    }

    /**
     * イベントが発生したときのy座標を取得します。
     * 
     * @return y座標
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        String buttonName = "unknown";
        if (button == LEFT_BUTTON)
            buttonName = "left";
        else if (button == RIGHT_BUTTON)
            buttonName = "right";
        else if (button == CENTER_BUTTON)
            buttonName = "center";
        else if (button == X_BUTTON_1)
            buttonName = "x1";
        else if (button == X_BUTTON_2)
            buttonName = "x2";
        String buttonMode = "unknown";
        if (mode == UP)
            buttonMode = "up";
        else if (mode == DOWN)
            buttonMode = "down";
        else if (mode == CLICK)
            buttonMode = "click";
        else if (mode == DOUBLE_CLICK)
            buttonMode = "doubleClick";
        return "net.inxas.rit.widget.event.MouseClickEvent[x=" + x + ",y=" + y + ",button=" + buttonName + ",mode="
                + buttonMode + ",withLeftButton=" + pressLB + ",withRightButton=" + pressRB + ",withCenterButton="
                + pressCB + ",withXButton1=" + pressXB1 + ",withXButton2=" + pressXB2 + ",withShiftKey=" + pressSK
                + ",withControlKey=" + pressCK + "]";
    }
}
