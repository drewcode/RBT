import java.awt.Graphics;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class tree extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here
	g.setFont(new Font("", Font.BOLD, 15)); 
	g.drawString("Height",10,30);
	g.drawString("=",65,30);
	g.drawString("'height variable here'",75,30);	

    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.add(new tree());
        jFrame.setSize(1000, 1000);
        jFrame.setVisible(true);
    }

}