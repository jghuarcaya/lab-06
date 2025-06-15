package triangle;

import resizable.ResizableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.util.GregorianCalendar.BC;
import static resizable.Debug.print;

/**
 * Implement your Sierpinski Triangle here.
 *
 *
 * You only need to change the drawTriangle
 * method!
 *
 *
 * If you want to, you can also adapt the
 * getResizeImage() method to draw a fast
 * preview.
 *
 */
public class Triangle implements ResizableImage {
    int drawTriangle = 0;
    /**
     * change this method to implement the triangle!
     * @param size the outer bounds of the triangle
     * @return an Image containing the Triangle
     */
    private BufferedImage drawTriangle(Dimension size) {
        print("drawTriangle: " + ++drawTriangle + " size: " + size);
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();

        int padding = 20;
        int maxWidth = size.width - 2 * padding;
        int maxHeight = size.height - 2 * padding;

        double side = Math.min(maxWidth, maxHeight / (Math.sqrt(3) / 2));
        double height = (Math.sqrt(3) / 2) * side;

        double centerX = size.width / 2.0;
        double topY = (size.height - height) / 2.0;

        int[] A = {(int) centerX, (int) topY};
        int[] B = {(int) (centerX - side / 2), (int) (topY + height)};
        int[] C = {(int) (centerX + side / 2), (int) (topY + height)};

        final int maxDepth = 6;

        java.util.Stack<Object[]> stack = new java.util.Stack<>();
        stack.push(new Object[]{A, B, C, 0});  // Ebene 0

        while (!stack.isEmpty()) {
            Object[] item = stack.pop();
            int[] top = (int[]) item[0];
            int[] left = (int[]) item[1];
            int[] right = (int[]) item[2];
            int level = (int) item[3];

            // Farbe je nach Ebene
            float hue = (float) level / maxDepth;
            gBuffer.setColor(Color.getHSBColor(hue, 0.6f, 0.9f));

            // Dreieck zeichnen
            int[] xPoints = {top[0], left[0], right[0]};
            int[] yPoints = {top[1], left[1], right[1]};
            gBuffer.fillPolygon(xPoints, yPoints, 3);

            if (level < maxDepth) {
                int[] tl = {(top[0] + left[0]) / 2, (top[1] + left[1]) / 2};
                int[] lr = {(left[0] + right[0]) / 2, (left[1] + right[1]) / 2};
                int[] rt = {(right[0] + top[0]) / 2, (right[1] + top[1]) / 2};

                int nextLevel = level + 1;

                stack.push(new Object[]{top, tl, rt, nextLevel});
                stack.push(new Object[]{tl, left, lr, nextLevel});
                stack.push(new Object[]{rt, lr, right, nextLevel});
            }
        }

        return bufferedImage;
    }

    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }
    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}
