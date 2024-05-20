package triangle;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static triangle.Debug.print;
import static triangle.Debug.printStackTrace;

/**
 * Main Class for the Sierpinski Triangle.
 * Creates the Frame and the Panel that holds
 * The Triangle.
 *
 * Calls different getImage methods depending on
 * whether the repaint comes from within a resize or
 * after the resize.
 *
 * YOU DON'T NEED to CHANGE ANYTHING HERE!!!
 * IMPLEMENT the Sierpinski Triange In TRIANGLE.
 *
 */
public class SierpinskiTriangle {
    public static int SIZE = 1000;
    int resizeDonePause = 500;
    Triangle triangle = new Triangle();
    JFrame frame;
    JPanel panel;
    WaitForPause waitForPause;

    public SierpinskiTriangle() {
        createFrame();
        waitForPause = new WaitForPause(resizeDonePause, () -> resizeDone());
        waitForPause.start();
    }

    public static void main(String[] args) {
        SierpinskiTriangle triangle = new SierpinskiTriangle();
    }

    public void createFrame() {
        frame = new JFrame();

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintContentImage(g, panel.getSize());
            }
        };

        ComponentAdapter delayResize = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (waitForPause != null) waitForPause.setInProgress();
            }
        };
        panel.addComponentListener(delayResize);
        frame.addComponentListener(delayResize);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(SIZE, SIZE);
        frame.setVisible(true);

    }

    int resizeDone = 0;

    private void resizeDone() {
        frame.repaint();
        print("resizeDone " + ++resizeDone);
    }


    int countPaint = 0, countFullPaint = 0;

    public void paintContentImage(Graphics g, Dimension size) {
        print("countPaint " + ++countPaint);
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.white);
        g2.setColor(Color.gray);
        g2.clearRect(0, 0, size.width, size.height);
        int border = 25;
        Dimension triangleSize = new Dimension(size.width - 2 * border, size.height - 2 * border);
        Dimension triangleOffset = new Dimension(border, border);

        if (waitForPause.inProgress()) {
            g.drawImage(triangle.getResizeImage(triangleSize), triangleOffset.width, triangleOffset.height, null);
        } else {
            g.drawImage(triangle.getImage(triangleSize), triangleOffset.width, triangleOffset.height, null);
            print("countFullPaint " + ++countFullPaint);
            printStackTrace("countFullPaint " + countFullPaint);
        }

    }

}
