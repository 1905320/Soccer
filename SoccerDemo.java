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

