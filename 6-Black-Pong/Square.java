import javax.swing.*;
import java.awt.*;

public class Square
{
   private int Xside;
   private int Yside;
   private int x;  //x coordinate of top left corner
   private int y;  //y coordinate of top left corner
   private Color c;
   
   //constructors
   public Square()
   {
      Xside = 3;
      Yside = 80;
      x = 25;
      y = 200;
      c = Color.WHITE;
   }
   public Square(int sideValue, int sideValuey, int xValue, int yValue, Color cValue)
   {
      Xside = sideValue;
      Yside = sideValuey;
      x = xValue;
      y = yValue;
      c = cValue;
   }
   
   //accessors
   public int getXSide()
   {
      return Xside;
   }
   public int getYSide()
   {
      return Yside;
   }
   public int getX()
   {
      return x;
   }
   public int getY()
   {
      return y;
   }
   public Color getColor()
   {
      return c;
   }
   
   //modifiers
   public void setSide(int sideValue)
   {
      Xside = sideValue;
   }
   public void setX(int xValue)
   {
      x = xValue;
   }
   public void setY(int yValue)
   {
      y = yValue;
   }
   public void setColor(Color cValue)
   {
      c = cValue;
   }
   
   //instance methods
   public void drawMe(Graphics g)
   {
      g.setColor(c);
      g.fillRect(x, y, Xside, Yside);
   }
   
   //other useful Java methods
   
   //(leave this commented out until the assignment tells you to use it)
   
   public String toString()
   {
      return "Square at " + x + ", " + y + " with side length " + Xside + " and color set to " + c;
   }
   
   
}