import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SoccerDemo
{
   public static void main(String[] args)
   { 
      JFrame frame = new JFrame("GUI with BorderLayout");
      frame.setSize(1920, 1080);
      frame.setLocation(20, 20);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new PanelSwapPanel(frame));
      frame.setVisible(true);
   }
}

class PanelSwapPanel extends JPanel
{
   private JFrame myOwner;  //The JFrame that contains this panel (!)
   
   private SwapperOne subOne;  //A subpanel that will get swapped out for...
   private KeyInputPanel subTwo;  //...a different subpanel
   private JLabel title;
   
   public PanelSwapPanel(JFrame f)
   {
      myOwner = f;  //Store a reference to the JFrame that I belong to (!)
      
      //Add some text in the north
      setLayout(new BorderLayout());
      JLabel title = new JLabel("Arcade Soccer!");
      add(title, BorderLayout.NORTH);
      
      //Make and add first subpanel
      subOne = new SwapperOne(this);  //Pass **MYSELF** to SwapperOne (!)
      add(subOne);
      
      subTwo = new KeyInputPanel();
      //We **don't** add subTwo!  Just make sure it's ready.
   }
   public void switchSubpanels()
   {
      //All of these commands are necessary, in this order, to remove a subpanel,
      //add another one, then cause the JFrame to figure itself out again, including
      //resizing if necessary.  If you leave something out, you'll get weird behavior.
      remove(subOne);
      add(subTwo);
      repaint();
      revalidate();
      subTwo.requestFocusInWindow();
      myOwner.setSize(1920,1080);  //Again, note - I'm giving the JFrame that contains this panel a command! (!)
   }
}

class SwapperOne extends JPanel
{
   private PanelSwapPanel myOwner;  //The PanelSwapPanel that contains this panel (!)
   
   public SwapperOne(PanelSwapPanel p)
   {
      myOwner = p;  //Store a reference to the PanelSwapPanel that I belong to (!)
      
      setPreferredSize(new Dimension(300, 300)); //Set size here.
            
      setLayout(new GridLayout(1, 2));
      
      JButton switcheroo1 = new JButton("Single Player");
      switcheroo1.addActionListener(new SwitchListener());
      add(switcheroo1);
      JButton switcheroo2 = new JButton("Multi Player");
      switcheroo2.addActionListener(new SwitchListener());
      add(switcheroo2);
   }
   
   private class SwitchListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         myOwner.switchSubpanels();  //Send a command to the PanelSwapPanel that contains this one (!)
      }
   }
}