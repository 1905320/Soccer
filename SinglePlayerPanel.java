

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
   private Timer countdown;

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
   
   private boolean collisionWithSq;
   private boolean collisionWithAq;
   private boolean countdownActive;
   
   //And we need to declare our square we can control with arrow keys as a field, separately from 
   //the arraylist, so we can give it specific commands outside the constructor.
   private ArrowkeySquare sq;
   private ArrowkeySquare aq;
   private BouncingSquare cr; 
   
   private int score1;
   private int score2;
   private int number;
   private Key keyListener;
   
   private SoundPlayer soundPlayer;
   
   
      
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
       //Animation starts, but square -won't move yet...
      
      slowDownTimer = new Timer(500, new SlowDownListener());
      slowDownTimer.start();
      
      soundPlayer = new SoundPlayer();
      
      //Here's how to enable keyboard input:
      keyListener = new Key();
      addKeyListener(keyListener); //Key is a private class defined below
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
      collisionWithSq = false;
      collisionWithAq = false;
      countdownActive = false;
   }
   
   public void addNotify() {
        super.addNotify();
        t.start();
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
      myBuffer.drawString("S-BOT", 1020, 35); 
      ImageIcon versus = new ImageIcon("image-removebg-preview.png");
      myBuffer.drawImage(versus.getImage(), 950, 10, 30, 30, null);

      
      //Loop through the ArrayList of Animatable objects; do an animation step on each one & draw it
      for(Animatable animationObject : animationObjects)
      {
         boolean currentCollisionWithSq = cr.collide(sq);
         boolean currentCollisionWithAq = cr.collide(aq);

         if (currentCollisionWithSq && !collisionWithSq) {
            soundPlayer.playSound("kick-183936.wav");
         }
         collisionWithSq = currentCollisionWithSq;

         if (currentCollisionWithAq && !collisionWithAq) {
            soundPlayer.playSound("kick-183936.wav");
         }
         collisionWithAq = currentCollisionWithAq;
         
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
               soundPlayer.playSound("bet-365-goal-sound-[AudioTrimmer.com].wav");
               cr.setX(940);
               cr.setY(520);
               sq.setX(50);
               sq.setY(540);
               aq.setX(1820);
               aq.setY(540);
               if(score2 < 15)
               {
                  startCountdown();
               }
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
               soundPlayer.playSound("bet-365-goal-sound-[AudioTrimmer.com].wav");
               cr.setX(940);
               cr.setY(520);
               sq.setX(50);
               sq.setY(540);
               aq.setX(1820);
               aq.setY(540);
               if(score1 < 15)
               {
                  startCountdown();
               }
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
            soundPlayer.playSound("victory-1-90174.wav");
            if(score1 >= 15)
            {
               myBuffer.setFont(new Font("Retro Gaming", Font.BOLD, 100));  //We'll use size 30 serif font, bold AND italic.
               myBuffer.setColor(Color.RED);
               myBuffer.drawString("S-BOT Wins", 725, 500);     
            }
            if(score2 >= 15)
            {
               myBuffer.setFont(new Font("Retro Gaming", Font.BOLD, 100));  //We'll use size 30 serif font, bold AND italic.
               myBuffer.setColor(Color.BLUE);
               myBuffer.drawString("Blue Wins", 715, 500);     
            }  
         }
         int xcordofball = (int)(cr.getX() + cr.getXSide()/2);
         int ycordofball = (int)(cr.getY() + cr.getXSide()/2);
         int xcordofbot = (int)(aq.getX() + aq.getXSide()/2);
         int ycordofbot = (int)(aq.getY() + aq.getXSide()/2);
         int slope = 0;
         if((xcordofball - xcordofbot) != 0)
         {
            slope = (int)(ycordofball - ycordofbot)/(xcordofball - xcordofbot);
            if(xcordofbot > xcordofball)
            {
               aq.setDX(-5);
            }
            if(xcordofbot < xcordofball)
            {
               aq.setDX(5);
            }
            if(ycordofbot < ycordofball)
            {
               aq.setDY(Math.abs(slope));
            }
            if(ycordofbot > ycordofball)
            {
               aq.setDY(-1 * Math.abs(slope));
            }
         }
         if((xcordofball - xcordofbot) == 0)
         {
            if(ycordofbot > ycordofball)
            {
               aq.setDY(-5);
            }
            if(ycordofbot < ycordofball)
            {
               aq.setDY(5);
            }
            else
            {
               aq.setDY(0);
            }
         }
         if(countdownActive)
         {
            aq.setDX(0);
            aq.setDY(0);
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
      
      if (countdownActive) {
                     myBuffer.setFont(new Font("Retro Gaming", Font.BOLD, 100));
                     myBuffer.setColor(Color.BLACK);
                     if(number == 0)
                     {
                        myBuffer.drawString("GO", 890, 500);   
                        soundPlayer.playSound("referee-whistle-blow-gymnasium-6320.wav");                     
                     }
                     else
                     {
                        myBuffer.drawString(Integer.toString(number), 930, 500);
                     }
                     cr.setDX(0);
                     cr.setDY(0);
                     sq.setDX(0);
                     sq.setDY(0);
                     removeKeyListener(keyListener);
                     if(number == 0)
                     {
                        addKeyListener(keyListener);
                     }
                 }
      
      //Call built-in JFrame method repaint(), which calls paintComponent, which puts the next frame on screen
      repaint();
   }
   private void startCountdown() {
        countdownActive = true;
        number = 3;
        countdown = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (number == 0) {
                    countdown.stop();
                    countdownActive = false;
                    t.start();
                } else {
                    number-=1;
                }
            }
        });
        countdown.start();
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
      public void keyPressed(KeyEvent e) //Make ONE method for key presses; this is overridden, and will be called whenever a key is pressed
      {
         
       
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


         
         // write code for the other keys here
         
         
      }
      
      public void keyReleased(KeyEvent e) //Also overridden; ONE method that will be called any time a key is released
      {
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
         //write code for the other keys here   
         
      }
   }
}
