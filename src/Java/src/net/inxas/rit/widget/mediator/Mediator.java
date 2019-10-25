package net.inxas.rit.widget.mediator;

import net.inxas.rit.RIT;
import net.inxas.rit.RITUnsupportedException;
import net.inxas.rit.widget.Window;
import net.inxas.rit.widget.mediator.windows.WinWindowMediator;

/**
 * 各環境に合わせてインスタンスを割り当てます。
 * もし対応していない環境で実行した場合、{@link RITUnsupportedException}をスローします。
 * 
 * @author inxas
 * @since  2019/10/22
 */
public final class Mediator {
    /**
     * 現在の環境に合わせて{@link WindowMediator}を作成します。
     * 
     * @return                         現在の環境用の{@link WindowMediator}
     * @throws RITUnsupportedException もし現在の環境がRITをサポートしていなければスローされます。
     */
    public static WindowMediator createWindowMediator(Window window) throws RITUnsupportedException {
        if (RIT.isWindows()) {
            return new WinWindowMediator(window);
        }
        throw new RITUnsupportedException();
    }
}
