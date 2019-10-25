package net.inxas.rit;

/**
 * 
 * @author inxas
 * @since 2019/10/22
 */
public final class RIT {
    static {
        System.loadLibrary("RIT");
    }

    // 現在の実行OSの名前です。
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    /**
     * 現在の実行環境がRITをサポートしているかを返します。 現在はWindowsのみのサポートです。
     * 
     * @return もしRITをサポートしているならばtrue、そうでなければfalse
     */
    public static boolean isSupported() {
        if (isWindows()) {
            return true;
        }
        return false;
    }

    /**
     * 現在の実行環境がWindowsであるかを返します。
     * 
     * @return もし現在の実行環境がWindowsであればtrue、そうでなければfalse
     */
    public static boolean isWindows() {
        return OS_NAME.startsWith("windows");
    }
}
