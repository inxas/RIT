package net.inxas.rit;

/**
 * 現在の環境がRITに対応していないにもかかわらず、RITを使用しようとした場合にスローされます。
 * 
 * @author inxas
 * @since 2019/10/22
 */
public class RITUnsupportedException extends RITRuntimeException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4201651832026883690L;

    /**
     * コンストラクタ
     */
    public RITUnsupportedException() {
        super("This environment does not support RIT.");
    }
}
