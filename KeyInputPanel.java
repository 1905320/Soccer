

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

public class KeyInputPanel extends JPanel
{
   //fields
   
   //Most of this is the same as AnimationPanel
   public static final int FRAME = 500;
   private static final Color BACKGROUND = Color.BLACK;
   
   private BufferedImage myImage;  
   private Graphics myBuffer;
   
   private Timer t;
   private Timer s;

   private ArrayList<Animatable> animationObjects;
   
   //But we do need a new one: this will be true when the user is holding down the left arrow key.
   //Explaining why we need this is complicated; see the assignment for details.
   private boolean left;
   private boolean right;
   private boolean up;
   private boolean down;
   private boolean arrowup;
   private boolean arrowdown;
   
   //And we need to declare our square we can control with arrow keys as a field, separately from 
   //the arraylist, so we can give it specific commands outside the constructor.
   private ArrowkeySquare sq;
   private ArrowkeySquare aq;
   private BouncingSquare cr; 
   
   private int score1;
   private int score2;
   
   
      
   //constructors
   public KeyInputPanel()
   {
      myImage =  new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB); 
      myBuffer = myImage.getGraphics(); 
      myBuffer.setColor(BACKGROUND);    
      myBuffer.fillRect(0,0,640,480);
      
      animationObjects = new ArrayList<Animatable>();  
      
      //Make our ArrowkeySquare
      sq = new ArrowkeySquare(); //DO NOT RE-DECLARE sq - it's a field, it's already declared.  Just assign.
      animationObjects.add(sq); //We want sq to be animated, and also want to give sq other commands, so we 
                                //add sq to the arraylist and also refer to it as sq separaetly.
      aq = new ArrowkeySquare(3, 80, 612, 200, Color.WHITE);
      animationObjects.add(aq);
      
      cr = new BouncingSquare(); //Instantiate an inflating circle...
      animationObjects.add(cr);
      
      score1 = 0;
      score2 = 0;
      t = new Timer(5, new AnimationListener());
      s = new Timer(10000, new AnimationListener());
      t.start();  //Animation starts, but square -won't move yet...
      
      //Here's how to enable keyboard input:
      addKeyListener(new Key());  //Key is a private class defined below
      setFocusable(true);  //Don't forget this!
      
      left = false; //at the moment, the user is not pushing down the left arrow key, so "false"
      right = false;
      up = false;
      down = false; 
      arrowup = false;
      arrowdown = false; 
   }
   
   
   //overridden methods
   
   public void paintComponent(Graphics g)  
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);  
   }
   
   
   
   //instance methods
   
   public void animate()
   {      
      //Clear the current state of myImage by writing over it with a new blank background
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,640,480);
      
      //Loop through the ArrayList of Animatable objects; do an animation step on each one & draw it
      for(Animatable animationObject : animationObjects)
      {
         if(cr.collide(sq))
         {
            if(cr.getDX() > 0)
            {
               cr.setDX(-7);
            }
            if(cr.getDX() < 0)
            {
               cr.setDX(7);
            }
            int j = (cr.getY()+10);
            if( j < (sq.getY() + 13))
            {
               cr.setDY(-5);
            }
            if( j > (sq.getY() + 13) && j < (sq.getY() + 26))
            {
               cr.setDY(-3);
            }
            if( j > (sq.getY() + 26) && j < (sq.getY() + 40))
            {
               cr.setDY(-1);
            }
            if( j > (sq.getY() + 40) && j < (sq.getY() + 53))
            {
               cr.setDY(1);
            }
            if( j > (sq.getY() + 53) && j < (sq.getY() + 67))
            {
               cr.setDY(3);
            }
            if( j >= (sq.getY() + 67))
            {
               cr.setDY(5);
            } 
         }
         if(cr.collide(aq))
         {
            if(cr.getDX() < 0)
            {
               cr.setDX(7);
            }
            if(cr.getDX() > 0)
            {
               cr.setDX(-7);
            }
            int j = (cr.getY()+10);
            if( j < (aq.getY() + 13))
            {
               cr.setDY(-5);
            }
            if( j > (aq.getY() + 13) && j < (aq.getY() + 26))
            {
               cr.setDY(-3);
            }
            if( j > (aq.getY() + 26) && j < (aq.getY() + 40))
            {
               cr.setDY(-1);
            }
            if( j > (aq.getY() + 40) && j < (aq.getY() + 53))
            {
               cr.setDY(1);
            }
            if( j > (aq.getY() + 53) && j < (aq.getY() + 67))
            {
               cr.setDY(3);
            }
            if( j >= (aq.getY() + 67))
            {
               cr.setDY(5);
            } 
         }
         if(cr.getX() >= (640 - cr.getXSide()) || cr.getX() <= 0)
         {
            if(cr.getX() >= (640 - cr.getXSide()))
            {
               score2++;
            }
            if(cr.getX() <= 0)
            {
               score1++;
            }
            cr.setX(310);
            cr.setY(230);
            
            double randomOfTwoInts = Math.random();
            if(randomOfTwoInts < 0.5)
            {
               cr.setDX(-4);
            }
            else if(randomOfTwoInts >= 0.5)
            {
               cr.setDX(4);
            }
            cr.setDY(-3 + (int)(Math.random() * 6));
         }
         if(score1 >= 15 || score2 >=15)
         {
            t.stop();
         }
         animationObject.step();  
         animationObject.drawMe(myBuffer); 
        
      }
      myBuffer.setFont(new Font("Serif", Font.BOLD, 30));  //We'll use size 30 serif font, bold AND italic.
      myBuffer.setColor(Color.WHITE);
      myBuffer.drawString(Integer.toString(score2), 605, 30); 
      
      myBuffer.setFont(new Font("Serif", Font.BOLD, 30));  //We'll use size 30 serif font, bold AND italic.
      myBuffer.setColor(Color.WHITE);
      myBuffer.drawString(Integer.toString(score1), 10, 30);
      
      //Call built-in JFrame method repaint(), which calls paintComponent, which puts the next frame on screen
      repaint();
   }
   
   
   
   //private classes
   
   private class AnimationListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)  //Gets called over and over by the Timer
      {
         animate();
      }
   }
   
   private class Key extends KeyAdapter //Make ONE class that EXTENDS KeyAdapter, and tell it what to do when keys are pressed or released
   {
      public void keyPressed(KeyEvent e) //Make ONE method for key presses; this is overridden, and will be called whenever a key is pressed
      {
         if(e.getKeyCode() == KeyEvent.VK_UP && !up)
         {   
            aq.setDY(aq.getDY() - 5);
            up = true;
         }  
         if(e.getKeyCode() == KeyEvent.VK_DOWN && !down)
         {   
            aq.setDY(aq.getDY() + 5);
            down = true;
         }  
         if(e.getKeyCode() == KeyEvent.VK_W && !arrowup)
         {   
            sq.setDY(sq.getDY() - 5);
            arrowup = true;
         }  
         if(e.getKeyCode() == KeyEvent.VK_S && !arrowdown)
         {   
            sq.setDY(sq.getDY() + 5);
            arrowdown = true;
         }  
         
         
         
         // write code for the other keys here
         
         
      }
      
      public void keyReleased(KeyEvent e) //Also overridden; ONE method that will be called any time a key is released
      {
         if(e.getKeyCode() == KeyEvent.VK_UP)
         {   
            aq.setDY(aq.getDY() + 5);
            up = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_DOWN)
         {   
            aq.setDY(aq.getDY() - 5);
            down = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_W)
         {   
            sq.setDY(sq.getDY() + 5);
            arrowup = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_S)
         {   
            sq.setDY(sq.getDY() - 5);
            arrowdown = false;
         }  
         
         //write code for the other keys here
         
         
      }
   }
}
