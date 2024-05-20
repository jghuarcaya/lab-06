package triangle;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static triangle.Debug.print;

public class SierpinskiTriangle {
    public static int SIZE = 1000;
    int resizeDonePause = 2000;
    Triangle triangle = new Triangle();
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
        frame.addWindowListener(new WindowListener() {
            int windowCounter = 0;
            @Override
            public void windowOpened(WindowEvent e) {
                print("---- Frame ----- windowOpened = " + ++windowCounter);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                print("---- Frame ----- windowClosing = " + ++windowCounter);

            }

            @Override
            public void windowClosed(WindowEvent e) {
                print("---- Frame ----- windowClosed = " + ++windowCounter);

            }

            @Override
            public void windowIconified(WindowEvent e) {
                print("---- Frame ----- windowIconified = " + ++windowCounter);

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                print("---- Frame ----- windowDeiconified = " + ++windowCounter);

            }

            @Override
            public void windowActivated(WindowEvent e) {
                print("---- Frame ----- windowActivated = " + ++windowCounter);

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                print("---- Frame ----- windowDeactivated = " + ++windowCounter);

            }
        });
        frame.addComponentListener(new ComponentListener(){
            int componentListener = 0;
            @Override
            public void componentResized(ComponentEvent e) {
                if (waitForPause != null) waitForPause.setInProgress();
                print("---- Frame ----- componentResized = " + ++componentListener);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                print("---- Frame ----- componentMoved = " + ++componentListener);
            }

            @Override
            public void componentShown(ComponentEvent e) {
                print("---- Frame ----- componentShown = " + ++componentListener);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                print("---- Frame ----- componentHidden = " + ++componentListener);
            }



        });
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
        Dimension rectSize = new Dimension(size.width - 2*offset, size.height - 2*offset);
        g2.drawRect(offset, offset, rectSize.width, rectSize.height);

        if (!waitForPause.inProgress()) {
            Dimension triangleSize = rectSize;
            Dimension triangleOffset = new Dimension(offset, offset);
            g.drawImage(triangle.getImage(triangleSize), triangleOffset.width, triangleOffset.height, null);
            //print("countFullPaint " + ++countFullPaint);
            //printStackTrace("countFullPaint " + countFullPaint);
        }

    }

}
