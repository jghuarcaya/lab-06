package triangle;

import java.awt.*;
import java.awt.image.BufferedImage;

import static triangle.Debug.print;

public class Triangle {

    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    public synchronized Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }
    int drawTriangle = 0;
    private BufferedImage drawTriangle(Dimension size) {
        print("drawTriangle: "+ ++drawTriangle + "size: "+size);
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.black);
        int border = 2;
        gBuffer.drawRect(border, border, size.width-2*border  , size.height-2*border );
        gBuffer.setColor(Color.darkGray);
        border = 8;
        gBuffer.drawRect(border, border, size.width-2*border  , size.height-2*border );
        gBuffer.drawString("Triangle goes here", border*2,border*4);
        return bufferedImage;
    }

    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width-2*border  , size.height-2*border );
        return bufferedImage;
        }
}
