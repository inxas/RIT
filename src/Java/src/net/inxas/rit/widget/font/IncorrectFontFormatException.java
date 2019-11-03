package net.inxas.rit.widget.font;

import net.inxas.rit.RITRuntimeException;

/**
 * 解析しようとしたフォントファイルのフォーマットが間違っている場合にスローされます。
 * 
 * @author inxas
 * @since  2019/10/28
 */
public class IncorrectFontFormatException extends RITRuntimeException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5346955767722740488L;

    /**
     * 詳細メッセージがnullである新規IncorrectFontFormat例外を構築します。
     */
    public IncorrectFontFormatException() {
        super();
    }

    /**
     * 指定された詳細メッセージを持つ、新規IncorrectFontFormat例外を構築します。
     * 
     * @param message 詳細メッセージ
     */
    public IncorrectFontFormatException(String message) {
        super(message);
    }

    /**
     * 指定された詳細メッセージおよび原因を使用して新規IncorrectFontFormat例外を構築します。
     * 
     * @param message 詳細メッセージ
     * @param cause   原因
     */
    public IncorrectFontFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 指定された原因を使用して新規IncorrectFontFormat例外を構築します。
     * 
     * @param cause 原因
     */
    public IncorrectFontFormatException(Throwable cause) {
        super(cause);
    }
}
