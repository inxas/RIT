package net.inxas.rit;

/**
 * RITにまつわる例外のルートクラスです。
 * 
 * @author inxas
 * @since 2019/10/22
 */
public class RITException extends Exception {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6474499060257221388L;

    /**
     * 詳細メッセージがnullである新規RIT例外を構築します。
     */
    public RITException() {
        super();
    }

    /**
     * 指定された詳細メッセージを持つ、新規RIT例外を構築します。
     * 
     * @param message 詳細メッセージ
     */
    public RITException(String message) {
        super(message);
    }

    /**
     * 指定された詳細メッセージおよび原因を使用して新規RIT例外を構築します。
     * 
     * @param message 詳細メッセージ
     * @param cause   原因
     */
    public RITException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 指定された原因を使用して新規RIT例外を構築します。
     * 
     * @param cause 原因
     */
    public RITException(Throwable cause) {
        super(cause);
    }
}
