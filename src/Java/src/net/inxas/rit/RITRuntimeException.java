package net.inxas.rit;

/**
 * RITにまつわるランタイム例外のルートクラスです。
 * @author inxas
 * @since 2019/10/22
 */
public class RITRuntimeException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5331408826675799414L;

    /**
     * 詳細メッセージがnullである新規RIT例外を構築します。
     */
    public RITRuntimeException() {
        super();
    }

    /**
     * 指定された詳細メッセージを持つ、新規RIT例外を構築します。
     * 
     * @param message 詳細メッセージ
     */
    public RITRuntimeException(String message) {
        super(message);
    }

    /**
     * 指定された詳細メッセージおよび原因を使用して新規RIT例外を構築します。
     * 
     * @param message 詳細メッセージ
     * @param cause   原因
     */
    public RITRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 指定された原因を使用して新規RIT例外を構築します。
     * 
     * @param cause 原因
     */
    public RITRuntimeException(Throwable cause) {
        super(cause);
    }
}
