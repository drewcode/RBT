import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
 
// An AWT program inherits from the top-level container java.awt.Frame
public class buttons extends Frame implements ActionListener {
   private Label lbl;    // Declare component Label
   private TextField tf; // Declare component TextField
   private Button btn1;   // Declare component Button
   private Button btn2;
   private int x = 0;     // Text field value (in future)
 
   /** Constructor to setup GUI components and event handling */
   public buttons () {
      setLayout(new FlowLayout());
         // "super" Frame sets its layout to FlowLayout, which arranges the components
         //  from left-to-right, and flow to next row from top-to-bottom.
      
      lbl = new Label("New");  // construct Label
      add(lbl);                    // "super" Frame adds Label
 
      tf = new TextField("", 10); // construct TextField
      tf.setEditable(true);       // set to read-only
      add(tf);                     // "super" Frame adds tfCount
 
      btn1 = new Button("Insert");   // construct Button
      add(btn1);                    // "super" Frame adds Button
 
      btn1.addActionListener(this);
	 // Clicking Button source fires ActionEvent
         // btn1 registers this instance as ActionEvent listener

      btn2 = new Button("Delete");
      add(btn2);
      
      btn2.addActionListener(this);
         // Clicking Button source fires ActionEvent
         // btn2 registers this instance as ActionEvent listener
 
      setTitle("AWT insertion");  // "super" Frame sets title
      setSize(350, 250);        // "super" Frame sets initial window size
 
      setVisible(true);         // "super" Frame shows
   }
 
   /** The entry main() method */
   public static void main(String[] args) {
      // Invoke the constructor to setup the GUI, by allocating an instance
      buttons app = new buttons();
   }
 
   /** ActionEvent handler - Called back upon button-click. */
   @Override
   public void actionPerformed(ActionEvent evt) {
	if(evt.getSource() == btn1)
	{
		x = Integer.parseInt(tf.getText());
		System.out.println("button 1: " + x);
        }
	else if(evt.getSource() == btn2)
	{
		x = Integer.parseInt(tf.getText());
		System.out.println("button 2: " + x);
	}
}
}