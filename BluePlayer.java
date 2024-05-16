import javax.swing.*;
import java.awt.*;

public class BluePlayer extends Square
{
      public BluePlayer(int xsideValue, int ysideValue, int x,int y,Color c)
   {
      super(xsideValue, ysideValue,x,y,c);
   }
   public void drawMe(Graphics g)
   {
     ImageIcon ball = new ImageIcon("blueplayer.png");
     g.drawImage(ball.getImage(), getX(), getY(), getXSide(), getYSide(), null);
   }
}