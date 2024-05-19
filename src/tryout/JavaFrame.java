package tryout;

import javax.swing.JFrame;
import java.awt.*;

public class JavaFrame {
    public void frame01() {
        // from https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/frame.html
        //
        // 1. Create the frame.
        JFrame frame = new JFrame("FrameDemo");

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        //...create emptyLabel...
        String emptyLabel = "Text for Label in Frame 01";
        Label label = new Label(emptyLabel);
        frame.getContentPane().add(label, BorderLayout.CENTER);

        //4. Size the frame.
        frame.pack();

        //5. Show it.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new JavaFrame().frame01();
    }

}
