

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;




public class SinglePlayerPanel extends JPanel
{
   //fields
   
   //Most of this is the same as AnimationPanel
   public static final int FRAME = 500;
   private static final Color BACKGROUND = Color.GREEN.darker();
   
   private BufferedImage myImage;  
   private Graphics myBuffer;
   
   private Timer t;
   private Timer shiftTimer;
   private Timer shiftTimer1;
   private Timer slowDownTimer;

   private ArrayList<Animatable> animationObjects;
   
   //But we do need a new one: this will be true when the user is holding down the left arrow key.
   //Explaining why we need this is complicated; see the assignment for details.
   private boolean left;
   private boolean right;
   private boolean up;
   private boolean down;
   private boolean arrowup;
   private boolean arrowdown;
   private boolean arrowleft;
   private boolean arrowright;
   private boolean leftshift;
   private boolean zero;
   
   //And we need to declare our square we can control with arrow keys as a field, separately from 
   //the arraylist, so we can give it specific commands outside the constructor.
   private ArrowkeySquare sq;
   private ArrowkeySquare aq;
   private BouncingSquare cr; 
   
   private int score1;
   private int score2;
   
   
      
   //constructors
   public SinglePlayerPanel()
   {
      ImageIcon soccer = new ImageIcon("field.jpg");
      myImage =  new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB); 
      myBuffer = myImage.getGraphics(); 
      myBuffer.drawImage(soccer.getImage(), 0, 0, 1920, 1080, null);
     /* myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,640,480);
      myBuffer.setColor(Color.WHITE);  
      myBuffer.fillRect(50, 50, 540, 380); */
      
      animationObjects = new ArrayList<Animatable>();  
      
      //Make our ArrowkeySquare
      sq = new ArrowkeySquare(); //DO NOT RE-DECLARE sq - it's a field, it's already declared.  Just assign.
      animationObjects.add(sq); //We want sq to be animated, and also want to give sq other commands, so we 
                                //add sq to the arraylist and also refer to it as sq separaetly.
      aq = new ArrowkeySquare(50 ,50 , 1820, 540, Color.WHITE);
      animationObjects.add(aq);
      
      cr = new BouncingSquare(); //Instantiate a bouncing square
      animationObjects.add(cr);
      
      score1 = 0;
      score2 = 0;
      t = new Timer(5, new AnimationListener());
      t.start();  //Animation starts, but square -won't move yet...
      
      slowDownTimer = new Timer(500, new SlowDownListener());
      slowDownTimer.start();
      
      //Here's how to enable keyboard input:
      addKeyListener(new Key());  //Key is a private class defined below
      setFocusable(true);  //Don't forget this!
      
      left = false; //at the moment, the user is not pushing down the left arrow key, so "false"
      right = false;
      up = false;
      down = false; 
      arrowup = false;
      arrowdown = false; 
      arrowright = false;
      arrowleft = false;
      leftshift = false;
      zero = false;
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
      //myBuffer.setColor(BACKGROUND);
      //myBuffer.fillRect(0,0,640,480);
      ImageIcon soccer = new ImageIcon("field.jpg");
      myBuffer.drawImage(soccer.getImage(), 0, 0, 1920, 1080, null);
      myBuffer.setColor(Color.BLACK);
      myBuffer.fillRect(700, 0, 520, 50);
      int x[] = {650, 700, 700};
      int y[] = {0, 0, 50};
      myBuffer.fillPolygon(x, y, 3);
      int x1[] = {1220, 1220, 1270};
      int y1[] = {0, 50, 0};
      myBuffer.fillPolygon(x1, y1, 3);
      myBuffer.setColor(Color.GRAY);
      myBuffer.fillRect(725, 10, 60, 30);
      myBuffer.fillRect(1135, 10, 60, 30);
      myBuffer.setFont(new Font("Retro Gaming", Font.BOLD, 30));  //We'll use size 30 serif font, bold AND italic.
      myBuffer.setColor(Color.BLUE);
      myBuffer.drawString("BLUE", 830, 35); 
      myBuffer.setFont(new Font("Retro Gaming", Font.BOLD, 30));  //We'll use size 30 serif font, bold AND italic.
      myBuffer.setColor(Color.RED);
      myBuffer.drawString("RED", 1020, 35); 
      ImageIcon versus = new ImageIcon("image-removebg-preview.png");
      myBuffer.drawImage(versus.getImage(), 950, 10, 30, 30, null);

      
      //Loop through the ArrayList of Animatable objects; do an animation step on each one & draw it
      for(Animatable animationObject : animationObjects)
      {
         if(cr.collide(sq))
         {
            double threefourths = 3/4;
            double twothirds = 2/3;
            if((int)(cr.getX() + cr.getXSide()/2) > (int)(sq.getX() + sq.getXSide()/2))
            {
               cr.setDX(7);
               if((int)(cr.getY() + cr.getYSide()/2) < (int)(sq.getY() + sq.getXSide()/4))
               {
                  cr.setDY(-5);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(sq.getY() + sq.getXSide()/4) && (int)(cr.getY() + cr.getYSide()/2) < (int)(sq.getY() +sq.getXSide()/2))
               {
                  cr.setDY(-2);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(sq.getY() + sq.getXSide()/2) && (int)(cr.getY() + cr.getYSide()/2) < (int)(sq.getY() + 3*(int)(sq.getXSide()/4)))
               {
                  cr.setDY(2);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(sq.getY() + 3*(int)(sq.getXSide()/4)))
               {
                  cr.setDY(5);
               }
            }
            if((int)(cr.getX() + cr.getXSide()/2) < (int)(sq.getX() + sq.getXSide()/2))
            {
               cr.setDX(-7);
               if((int)(cr.getY() + cr.getYSide()/2) < (int)(sq.getY() + sq.getXSide()/4))
               {
                  cr.setDY(-5);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(sq.getY() + sq.getXSide()/4) && (int)(cr.getY() + cr.getYSide()/2) < (int)(sq.getY() +sq.getXSide()/2))
               {
                  cr.setDY(-2);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(sq.getY() + sq.getXSide()/2) && (int)(cr.getY() + cr.getYSide()/2) < (int)(sq.getY() + 3*(int)(sq.getXSide()/4)))
               {
                  cr.setDY(2);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(sq.getY() + 3*(int)(sq.getXSide()/4)))
               {
                  cr.setDY(5);
               }
            }
         }
         if(cr.collide(aq))
         {
            double threefourths = 3/4;
            if((int)(cr.getX() + cr.getXSide()/2) > (int)(aq.getX() + aq.getXSide()/2))
            {
               cr.setDX(7);
               if((int)(cr.getY() + cr.getYSide()/2) < (int)(aq.getY() + aq.getXSide()/4))
               {
                  cr.setDY(-5);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(aq.getY() + aq.getXSide()/4) && (int)(cr.getY() + cr.getYSide()/2) < (int)(aq.getY() + aq.getXSide()/2))
               {
                  cr.setDY(-2);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(aq.getY() + aq.getXSide()/2) && (int)(cr.getY() + cr.getYSide()/2) < (int)(aq.getY() + 3*(int)(aq.getXSide()/4)))
               {
                  cr.setDY(2);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(aq.getY() + 3*(int)(aq.getXSide()/4)))
               {
                  cr.setDY(5);
               }
            }
            if((int)(cr.getX() + cr.getXSide()/2) < (int)(aq.getX() + aq.getXSide()/2))
            {
               cr.setDX(-7);
               if((int)(cr.getY() + cr.getYSide()/2) < (int)(aq.getY() + aq.getXSide()/4))
               {
                  cr.setDY(-5);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(aq.getY() + aq.getXSide()/4) && (int)(cr.getY() + cr.getYSide()/2) < (int)(aq.getY() + aq.getXSide()/2))
               {
                  cr.setDY(-2);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(aq.getY() + aq.getXSide()/2) && (int)(cr.getY() + cr.getYSide()/2) < (int)(aq.getY() + 3*(int)(aq.getXSide()/4)))
               {
                  cr.setDY(2);
               }
               if((int)(cr.getY() + cr.getYSide()/2) > (int)(aq.getY() + 3*(int)(aq.getXSide()/4)))
               {
                  cr.setDY(5);
               }
            }
         }
         if(cr.getX() >= (1920 - cr.getXSide()) || cr.getX() <= 0)
         {
            if(cr.getX() >= (1920 - cr.getXSide()) && cr.getY() > 440 && (cr.getY() + cr.getYSide()) < 640)
            {
               score2++;
               cr.setX(960);
               cr.setY(540);
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
            if(cr.getX() <= 0 && cr.getY() > 440 && (cr.getY() + cr.getYSide()) < 640)
            {
               score1++;
               cr.setX(960);
               cr.setY(540);
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
            else
            {
               cr.setDX(cr.getDX());
            }
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
      myBuffer.drawString(Integer.toString(score1), 1145, 35); 
      
      myBuffer.setFont(new Font("Serif", Font.BOLD, 30));  //We'll use size 30 serif font, bold AND italic.
      myBuffer.setColor(Color.WHITE);
      myBuffer.drawString(Integer.toString(score2), 735, 35);
      
      //Call built-in JFrame method repaint(), which calls paintComponent, which puts the next frame on screen
      repaint();
   }
   
   
   
   //private classes
   
   public class AnimationListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)  //Gets called over and over by the Timer
      {
         animate();
      }
   }
   public class SlowDownListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if (cr.getDX() > 0) {
            cr.setDX(cr.getDX() - 1);
         } else if (cr.getDX() < 0) {
            cr.setDX(cr.getDX() + 1);
         }

         if (cr.getDY() > 0) {
            cr.setDY(cr.getDY() - 1);
         } else if (cr.getDY() < 0) {
            cr.setDY(cr.getDY() + 1);
         }
      }
   }
   
   public class Key extends KeyAdapter //Make ONE class that EXTENDS KeyAdapter, and tell it what to do when keys are pressed or released
   {
      private void startShiftTimer(ArrowkeySquare c) 
      {
            shiftTimer = new Timer(100, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                        if(right)
                        {
                           c.setDX(10);
                        }
                        if(down)
                        {
                           c.setDY(10);
                        }
                        if(left)
                        {
                           c.setDX(-10);
                        }
                        if(up)
                        {
                           c.setDY(-10);
                        }
                        if(right && left)
                        {
                           c.setDX(0);
                        }
                        if(up && down)
                        {
                           c.setDY(0);
                        }
                        if(!up && !down)
                        {
                           c.setDY(0);
                        }
                        if(!left && !right)
                        {
                           c.setDX(0);
                        }
                       
                    
                }
            });
            shiftTimer.start();
        }
        private void startShiftTimer1(ArrowkeySquare c) 
        {
            shiftTimer1 = new Timer(100, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                        if(arrowright)
                        {
                           c.setDX(10);
                        }
                        if(arrowdown)
                        {
                           c.setDY(10);
                        }
                        if(arrowleft)
                        {
                           c.setDX(-10);
                        }
                        if(arrowup)
                        {
                           c.setDY(-10);
                        }
                        if(arrowright && arrowleft)
                        {
                           c.setDX(0);
                        }
                        if(arrowup && arrowdown)
                        {
                           c.setDY(0);
                        }
                        if(!arrowup && !arrowdown)
                        {
                           c.setDY(0);
                        }
                        if(!arrowleft && !arrowright)
                        {
                           c.setDX(0);
                        }
                       
                    
                }
            });
            shiftTimer1.start();
        }

        private void stopShiftTimer(ArrowkeySquare c) {
            if (shiftTimer != null) {
                shiftTimer.stop();
                if(c.getDX() > 0)
                {
                     c.setDX(c.getDX() - 5);
                }
                if(c.getDY() > 0)
                {
                    c.setDY(c.getDY() - 5);
                }
                if(c.getDX() < 0)
                {
                    c.setDX(c.getDX() + 5);
                }
                if(c.getDY() < 0)
                {
                    c.setDY(c.getDY() + 5);
                }
            }
        }
        private void stopShiftTimer1(ArrowkeySquare c) {
            if (shiftTimer1 != null) {
                shiftTimer1.stop();
                if(c.getDX() > 0)
                {
                     c.setDX(c.getDX() - 5);
                }
                if(c.getDY() > 0)
                {
                    c.setDY(c.getDY() - 5);
                }
                if(c.getDX() < 0)
                {
                    c.setDX(c.getDX() + 5);
                }
                if(c.getDY() < 0)
                {
                    c.setDY(c.getDY() + 5);
                }
            }
        }
      public void keyPressed(KeyEvent e) //Make ONE method for key presses; this is overridden, and will be called whenever a key is pressed
      {
         if(e.getKeyCode() == KeyEvent.VK_UP && !arrowup)
         {   
            aq.setDY(aq.getDY() - 5);
            arrowup = true;
         }  
         if(e.getKeyCode() == KeyEvent.VK_DOWN && !arrowdown)
         {   
            aq.setDY(aq.getDY() + 5);
            arrowdown = true;
         }  
         if(e.getKeyCode() == KeyEvent.VK_W && !up)
         {   
            sq.setDY(sq.getDY() - 5);
            up = true;
         }  
         if(e.getKeyCode() == KeyEvent.VK_S && !down)
         {   
            sq.setDY(sq.getDY() + 5);
            down = true;
         }  
         if(e.getKeyCode() == KeyEvent.VK_RIGHT && !arrowright)
         {
            aq.setDX(aq.getDX() + 5);
            arrowright = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_LEFT && !arrowleft)
         {
            aq.setDX(aq.getDX() -5);
            arrowleft = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_D && !right)
         {
            sq.setDX(sq.getDX() + 5);
            right = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_A && !left)
         {
            sq.setDX(sq.getDX() -5);
            left = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_SHIFT && !leftshift)
         {  
            startShiftTimer(sq);
            leftshift = true;
         } 
         if(e.getKeyCode() == KeyEvent.VK_INSERT && !zero)
         {  
            startShiftTimer1(aq);
            zero = true;
         } 

         
         // write code for the other keys here
         
         
      }
      
      public void keyReleased(KeyEvent e) //Also overridden; ONE method that will be called any time a key is released
      {
         if(e.getKeyCode() == KeyEvent.VK_UP)
         {   
            aq.setDY(aq.getDY() + 5);
            arrowup = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_DOWN)
         {   
            aq.setDY(aq.getDY() - 5);
            arrowdown = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_RIGHT)
         {   
            aq.setDX(aq.getDX() -5);
            arrowright = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_LEFT)
         {   
            aq.setDX(aq.getDX() +5);
            arrowleft = false;
         }   
         if(e.getKeyCode() == KeyEvent.VK_W)
         {   
            sq.setDY(sq.getDY() + 5);
            up = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_S)
         {   
            sq.setDY(sq.getDY() - 5);
            down = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_D)
         {   
            sq.setDX(sq.getDX() - 5);
            right = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_A)
         {   
            sq.setDX(sq.getDX() + 5);
            left = false;
         }  
         if(e.getKeyCode() == KeyEvent.VK_SHIFT)
         {   
            stopShiftTimer(sq);
            leftshift = false;

         } 
         if(e.getKeyCode() == KeyEvent.VK_INSERT)
         {   
            stopShiftTimer1(aq);
            zero = false;

         } 

         
         //write code for the other keys here
         
         
      }
   }
}
