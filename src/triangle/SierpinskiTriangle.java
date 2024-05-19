package triangle;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SierpinskiTriangle implements Runnable {
    private int count = 0;
    private int countPaintComponent = 0;
    public static int SIZE = 1000;
    long resizeDonePause = 500 * 1000000;
    long lastResize = 0;
    boolean resizeInProgress = false;

    JFrame frame;
    JPanel panel;
    Thread panelThread;

    public SierpinskiTriangle() {
        createFrame();
    }

    public void createFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addPanel();

    }

    private void resetPanel() {
        //removePanel();
        //addPanel();
        panel.paint(panel.getGraphics());
    }

    private void removePanel() {
        panel.removeAll();
        frame.remove(panel);
        if (panelThread != null) {
            panelThread.interrupt();
            try {
                panelThread.join();
            } catch (InterruptedException e) {
                //throw new RuntimeException(e);
            }
        }
    }

    private void addPanel() {

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintSierpinskiTriangle(g, panel.getSize());
            }
        };

        panel.addComponentListener(new ComponentAdapter() {
            int panelResized = 0, frameResized = 0;

            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("panelResized" + ++panelResized);
                resizeInProgress = true;
                lastResize = System.nanoTime();
                System.out.println("resizeInProgress: " + resizeInProgress);
                //panel.repaint();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(SIZE, SIZE);
        frame.setVisible(true);

        panelThread = new Thread(this);
        panelThread.start();
    }

    public static void main(String[] args) {
        SierpinskiTriangle triangle = new SierpinskiTriangle();
    }

    public void paintSierpinskiTriangle(Graphics g, Dimension size) {
        System.out.println("paintSierpinskiTriangle " + count++);
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.white);
        g2.setColor(Color.gray);
        g2.clearRect(0, 0, size.width, size.height);
        int offset = 25;
        g2.draw3DRect(offset, offset, size.width - (2 * offset), size.height - (2 * offset), true);
        offset += 6;
        if (!resizeInProgress)
            g2.draw3DRect(offset, offset, size.width - (2 * offset), size.height - (2 * offset), true);

    }


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
                resetPanel();
            }
        }

    }
}
