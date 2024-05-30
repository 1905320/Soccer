import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PanelSwapPanel extends JPanel
{
   private JFrame myOwner;  //The JFrame that contains this panel (!)
   
   private SwapperOne subOne;  //A subpanel that will get swapped out for...
   private SinglePlayerPanel subTwo;  //...a different subpanel
   private JLabel title;
   private MultiPlayerPanel subThree;
   
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
      
      subTwo = new SinglePlayerPanel();
      
      subThree = new MultiPlayerPanel();
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
   public void switchSubpanels1()
   {
      //All of these commands are necessary, in this order, to remove a subpanel,
      //add another one, then cause the JFrame to figure itself out again, including
      //resizing if necessary.  If you leave something out, you'll get weird behavior.
      remove(subOne);
      add(subThree);
      repaint();
      revalidate();
      subThree.requestFocusInWindow();
      myOwner.setSize(1920,1080);  //Again, note - I'm giving the JFrame that contains this panel a command! (!)
   }
}

class SwapperOne extends JPanel
{
   private PanelSwapPanel myOwner;  //The PanelSwapPanel that contains this panel (!)
   
   public SwapperOne(PanelSwapPanel p)
   {
      myOwner = p;  //Store a reference to the PanelSwapPanel that I belong to (!)
      
      //setPreferredSize(new Dimension(300, 300)); //Set size here.
            
      setLayout(new GridLayout(2, 2));
      
      //"<html>Welcome to ARCADE SOCCER! A quality freestyle soccer game<br/>where you can play with your friends or just by yourself against<br/>our built-in bot<html>"
      JButton soccer = new JButton();
      soccer.setBackground(Color.BLACK);
      soccer.setIcon(new javax.swing.ImageIcon(getClass().getResource("jlabelsoccer.jpg")));
      //soccer.setFont(new Font("Retro Gaming", Font.BOLD, 30));
      //soccer.setBorder(BorderFactory.createMatteBorder(100, 100, 100, 100, Color.BLACK));
      soccer.setHorizontalAlignment(SwingConstants.CENTER);
      soccer.setEnabled(true);
      add(soccer);
      JButton switcheroo1 = new JButton("Single-Player");
      switcheroo1.setFont(new Font("Retro Gaming", Font.BOLD, 30));
      switcheroo1.setBackground(Color.RED);
      switcheroo1.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.RED.darker()));
      switcheroo1.addActionListener(new SwitchListener1());
      add(switcheroo1);
      JButton switcheroo2 = new JButton("Multi-Player");
      switcheroo2.setFont(new Font("Retro Gaming", Font.BOLD, 30));
      switcheroo2.setBackground(Color.BLUE);
      switcheroo2.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.BLUE.darker()));
      switcheroo2.addActionListener(new SwitchListener());
      add(switcheroo2);
      JLabel switcheroo3 = new JLabel("<html>Welcome to ARCADE SOCCER! A quality freestyle soccer game<br/>where you can play with your friends or just by yourself against<br/>our built-in bot<html>");
      add(switcheroo3);
      switcheroo3.setFont(new Font("Retro Gaming", Font.BOLD, 30));
      switcheroo3.setHorizontalAlignment(SwingConstants.CENTER);
   }
   
   private class SwitchListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         myOwner.switchSubpanels();  //Send a command to the PanelSwapPanel that contains this one (!)
      }
   }
   private class SwitchListener1 implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         myOwner.switchSubpanels1();  //Send a command to the PanelSwapPanel that contains this one (!)
      }
   }
}