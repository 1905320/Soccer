import javax.swing.*;
import java.awt.*;

public class Ball extends Square
{
      public Ball(int xsideValue, int ysideValue, int x,int y,Color c)
   {
      super(xsideValue, ysideValue,x,y,c);
   }
   public void drawMe(Graphics g)
   {
     ImageIcon ball = new ImageIcon("ball-removebg-preview.png");
     g.drawImage(ball.getImage(), getX(), getY(), getXSide(), getYSide(), null);
   }
}