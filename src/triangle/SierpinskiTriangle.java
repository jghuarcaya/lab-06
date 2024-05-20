package triangle;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static triangle.Debug.print;

public class SierpinskiTriangle {
    public static int SIZE = 1000;
    int resizeDonePause = 2000;

    JFrame frame;
    JPanel panel;
    WaitForPause waitForPause;

    public SierpinskiTriangle() {
        createFrame();
        addPanel();
        waitForPause = new WaitForPause(resizeDonePause, () -> resizeDone());
        waitForPause.start();
    }

    public void createFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    int resizeDone = 0;

    private void resizeDone() {
        //panel.paint(panel.getGraphics());
        frame.repaint();
        print("resizeDone " + ++resizeDone);
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
            int panelResized = 0;

            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("panelResized" + ++panelResized);
                if (waitForPause != null) waitForPause.setInProgress();
            }

        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(SIZE, SIZE);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SierpinskiTriangle triangle = new SierpinskiTriangle();
    }

    int countPaint = 0, countFullPaint = 0;

    public void paintSierpinskiTriangle(Graphics g, Dimension size) {
        print("countPaint " + ++countPaint);
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.white);
        g2.setColor(Color.gray);
        g2.clearRect(0, 0, size.width, size.height);
        int offset = 25;
        g2.draw3DRect(offset, offset, size.width - (2 * offset), size.height - (2 * offset), true);
        offset += 6;
        if (!waitForPause.inProgress()) {
            g2.draw3DRect(offset, offset, size.width - (2 * offset), size.height - (2 * offset), true);
            print("countFullPaint " + ++countFullPaint);
            try {
                throw new Exception("countFullPaint " + countFullPaint);
            } catch (Exception e) {
                System.out.println("----------------------");
                System.out.println(Thread.currentThread().getName());
                System.out.println("----------- start  " + e.getMessage());
                String stackTrace = Arrays.stream(e.getStackTrace()).map(ste -> ste.toString()).collect(Collectors.joining("\n"));
                System.out.println(stackTrace);
                System.out.println("----------- end    " + e.getMessage());
                System.out.println(Thread.currentThread().getName());
                System.out.println("----------------------");
            }
        }

    }


}
