package net.inxas.rit;

/**
 * RITプロジェクトの作成中に発生する例外です。
 * 
 * @author inxas
 * @since  2019/10/27
 */
public class RITProjectException extends RITException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3482671968168529391L;

    /**
     * 詳細メッセージがnullである新規RITProject例外を構築します。
     */
    public RITProjectException() {
        super();
    }

    /**
     * 指定された詳細メッセージを持つ、新規RITProject例外を構築します。
     * 
     * @param message 詳細メッセージ
     */
    public RITProjectException(String message) {
        super(message);
    }

    /**
     * 指定された詳細メッセージおよび原因を使用して新規RITProject例外を構築します。
     * 
     * @param message 詳細メッセージ
     * @param cause   原因
     */
    public RITProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 指定された原因を使用して新規RITProject例外を構築します。
     * 
     * @param cause 原因
     */
    public RITProjectException(Throwable cause) {
        super(cause);
    }
}
