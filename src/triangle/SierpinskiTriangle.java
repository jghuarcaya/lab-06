package triangle;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SierpinskiTriangle {
    private int count = 0;
    private int countPaintComponent = 0;
    public static int SIZE = 1000;

    JFrame frame;
    JPanel panel;

    @SuppressWarnings("serial")
    public void display() {
        System.out.println("display "+ count++);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                System.out.println("paintComponent" + countPaintComponent++);
                super.paintComponent(g);
                paintSierpinskiTriangle(g, getSize());
            }
        };
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.repaint();
            }
        });
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(SIZE, SIZE);
        frame.setVisible(true);
        frame.addComponentListener(new FrameListen());
    }

    public static void main(String[] args) {
        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        SierpinskiTriangle triangle = new SierpinskiTriangle();
        triangle.display();
    }

    public void paintSierpinskiTriangle(Graphics g, Dimension size) {

        System.out.println("paintSierpinskiTriangle "+ count++);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(getImage(size), 0, 0, null);
           }

    BufferedImage cachedImage;
    Dimension cachedSize;
    int getImageCount = 0, drawCount = 0;
    public BufferedImage getImage(Dimension size){
        System.out.println("getImageCount "+getImageCount++);
        if (size.equals(cachedSize)){
            System.out.println("size matches");
            if (cachedImage != null) return cachedImage;
            System.out.println("cached image null");
        }
        System.out.println("getImage -- start drawing "+ ++drawCount);
        System.out.println("size "+size.width+"x"+size.height);
        cachedSize = new Dimension(size.width, size.height);
        cachedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)cachedImage.getGraphics();
        g2.setBackground(Color.white);
        g2.clearRect(0, 0, size.width, size.height);
        g2.draw3DRect(20, 20, size.width - 40, size.height - 40, true);
        System.out.println("getImage -- done drawing " + drawCount);
        return cachedImage;
    }
    private class FrameListen implements ComponentListener {
        public void componentHidden(ComponentEvent arg0) {
        }
        public void componentMoved(ComponentEvent arg0) {
        }
        public void componentResized(ComponentEvent arg0) {
            String message = "-------componentResized Width: " +
                    Integer.toString(frame.getWidth());
            System.out.println(message);

        }
        public void componentShown(ComponentEvent arg0) {

        }
    }
}
