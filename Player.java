import javax.swing.*;
import java.awt.*;

public class Player extends Square
{
   public String C;
   
   public Player(int xsideValue, int ysideValue, int x,int y, Color c, String Cs)
   {
      super(xsideValue, ysideValue,x,y,c);
      C = Cs;
   }
   public void drawMe(Graphics g)
   {
     ImageIcon player = new ImageIcon(C);
     g.drawImage(player.getImage(), getX(), getY(), getXSide(), getYSide(), null);
   }
}