import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class BouncingSquare extends Square implements Animatable
{
   private int dX;
   private int dY;
   
   public BouncingSquare()
   {
      super(20, 20, 310, 230, Color.WHITE);
      double randomOfTwoInts = (Math.random());
      if(randomOfTwoInts < 0.5)
      {
         dX = -4;
      }
      if(randomOfTwoInts >= 0.5)
      {
         dX = 4;
      }
      dY = -3 + (int)(Math.random() * 6);
   }
   public BouncingSquare(int sideValue, int sideValuey, int xVal, int yVal, Color cVal, int dXVal, int dYVal)
   {
      super(sideValue, sideValuey, xVal, yVal, cVal);
      dX = dXVal;
      dY = dYVal;
   }
   public int getDX()
   {
      return dX;
   }
   public int getDY()
   {
      return dY;
   }
   public void setDX(int dRValue)
   {
      dX = dRValue;
   }
   public void setDY(int dYVal)
   {
      dY = dYVal;
   }
   public void step()
   {
      if(getY() >= (480 - getYSide()) || getY() <= 0)
      {
         dY *= -1;
      }
      setX(getX() + dX);  
      setY(getY() + dY);
   }
   public boolean collide(ArrowkeySquare sq)
   {
      boolean condition1 = (getX() + getXSide()) > sq.getX();
      boolean condition2 = (getY() + getYSide()) > sq.getY();
      boolean condition3 = getX() < (sq.getX() + sq.getXSide());
      boolean condition4 = getY() < (sq.getY() + sq.getYSide());
      
      return condition1 && condition2 && condition3 && condition4;
     
   }
}

   