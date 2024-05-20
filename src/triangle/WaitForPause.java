package triangle;

/**
 * WaitForPause
 *
 */

public class WaitForPause implements Runnable {
    long resizeDonePause = 0;
    Thread thread;
    boolean resizeInProgress = false;
    long lastResize = 0;
    Runnable action;
    public WaitForPause(long pause, Runnable action){
        this.resizeDonePause = pause;
        this.action = action;
        thread = new Thread(this);
    }
    public void setInProgress() {
        resizeInProgress = true;
        lastResize = System.nanoTime();
        System.out.println("resizeInProgress: " + resizeInProgress);
    }
    public void setInProgress(boolean inProgress) { resizeInProgress = inProgress;}
    @Override
    public void run() {
        boolean panelClosed = false;
        while (!panelClosed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //throw new RuntimeException(e);
            }
            if (resizeInProgress && (System.nanoTime() - lastResize > resizeDonePause)) {
                resizeInProgress = false;
                lastResize = 0;
                System.out.println("resizeInProgress: " + resizeInProgress);
                panelClosed = false;
                action.run();
            }
        }
        System.out.println("Thread done: " + panelClosed);
    }

    public void start() {
        thread.start();
    }

    public boolean inProgress() {
        return resizeInProgress;
    }
}
