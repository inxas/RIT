package net.inxas.rit.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Widget全体を管理します。
 * 
 * @author inxas
 * @since  2019/10/23
 */
public class WidgetManager {
    private boolean exitIfEmpty;
    private final ConcurrentLinkedDeque<Runnable> taskQueue;
    private final WidgetManageThread manager;

    private WidgetManager() {
        exitIfEmpty = true;
        taskQueue = new ConcurrentLinkedDeque<>();

        manager = new WidgetManageThread();
    }

    public void register(AbstractWindow window) {
        if (window != null) {
            dispatchTask(() -> manager.windows.add(window));
        }
    }

    public void unregister(AbstractWindow window) {
        if (window != null) {
            dispatchTask(() -> {
                manager.windows.remove(window);
                if (manager.windows.isEmpty() && exitIfEmpty) {
                    manager.loop = false;
                }
            });
        }
    }

    public void dispatchTask(Runnable task) {
        taskQueue.push(task);
    }

    public void setExitIfEmpty(boolean exit) {
        exitIfEmpty = exit;
    }

    private class WidgetManageThread extends Thread {
        volatile boolean loop;
        final List<AbstractWindow> windows;

        WidgetManageThread() {
            super("RIT Widget Manager");

            loop = true;
            windows = Collections.synchronizedList(new ArrayList<>());

            start();
        }

        @Override
        public void run() {
            while (loop) {
                synchronized (windows) {
                    windows.forEach(window -> window.nativeCallback());
                }
                if (!taskQueue.isEmpty()) {
                    taskQueue.poll().run();
                }
                Thread.yield();
            }
        }
    }

    private static class InstanceHolder {
        static final WidgetManager INSTANCE = new WidgetManager();
    }

    public static synchronized WidgetManager getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
