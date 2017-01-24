package finalprojectdhana;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author fabed2976
 */


public class FinalGame extends JComponent implements KeyListener{

    // Height and Width of our game
    static final int WIDTH = 400;
    static final int HEIGHT = 600;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    // player position variables
    int x = 500;
    int y = 100; 
    int moveX = 0;
    int moveY = 0;
    
    // if the player is hit with the side
    boolean start = false;
    boolean dead = false;
    
    int gameFinished = 0;
    //game avariables
    boolean rightkey = false;
    boolean leftkey = false;
    
    
    // making a variable for the gravity
    int gravity = 0;
    
    //creating the squid
    Rectangle squid = new Rectangle (175, 550, 30, 30);
    
 
    int frameCount = 0;
    
    // create diamonds
    ArrayList <Rectangle> diamonds = new ArrayList<>();
    
    // create bricks
    ArrayList<Rectangle> rightRectangle = new ArrayList<>();
    
    // import images 
    BufferedImage sideRectangle = loadImage("rectangle2.png");
    BufferedImage Background = loadImage("background.png");
    BufferedImage diamond = loadImage ("diamond.png");
    BufferedImage gameOver = loadImage("gameover.png");
    
    
    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Error loading " + filename);
        }
        return img;
    }

    public FinalGame (){
        rightRectangle = new ArrayList<>();
        rightRectangle.add(new Rectangle(75, 50, 40, 20));
        rightRectangle.add(new Rectangle(0, 150, 40, 20));
        rightRectangle.add(new Rectangle(177, 440, 40, 20));
        rightRectangle.add(new Rectangle(250, 0, 40, 20));
        rightRectangle.add(new Rectangle(150, 400, 40, 20));
        rightRectangle.add(new Rectangle(280, 480, 40, 20));
        rightRectangle.add(new Rectangle(350, 250, 40, 20));
        rightRectangle.add(new Rectangle(275, 75, 40, 20));
        rightRectangle.add(new Rectangle(175, 345, 40, 20));
        rightRectangle.add(new Rectangle(350, 300, 40, 20));
        rightRectangle.add(new Rectangle(200, 200, 40, 20));
        rightRectangle.add(new Rectangle(500, 375, 40, 20));
        rightRectangle.add(new Rectangle(50, 500, 40, 20));
        rightRectangle.add(new Rectangle(120, 550, 40, 20));
        rightRectangle.add(new Rectangle(480, 260, 40, 20));
        
        diamonds = new ArrayList<>();
        diamonds.add (new Rectangle (30, 0, 30, 15));
        diamonds.add (new Rectangle (100, 10, 30, 15));
        diamonds.add (new Rectangle (250, 100, 30, 15));
        diamonds.add (new Rectangle (200, 250, 30, 15));
        diamonds.add (new Rectangle (450, 300, 30, 15));
        diamonds.add (new Rectangle (350, 89, 30, 15));
        diamonds.add (new Rectangle (300, 50, 30, 15));
        diamonds.add (new Rectangle (390, 175, 30, 15));
      
    }
    
    
    
    // creating a score time
    int score = 0;
    Font scoreFont = new Font ("Arial" , Font.BOLD, 42);
    
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
       
        // GAME DRAWING GOES HERE 

             
           // drawing the side rectangle on the side of the screen
            g.drawImage(sideRectangle,0, 0, 100, 80, null);
        

            // Creating the background of the game
            g.drawImage(Background, 0, 0, null);
            
            // drawing the squid or player of the game
            g.setColor(Color.DARK_GRAY);
            g.fillOval (squid.x, squid.y, squid.width, squid.height);
            
            //drawing the diamonds
            g.setColor (Color.CYAN);
            for(Rectangle diamond : diamonds ){
                g.drawImage(this.diamond, diamond.x, diamond.y, diamond.width, diamond.height,null);
            }

            // drawing the rectangles or bricks of the game
            g.setColor(Color.GRAY);
            for(Rectangle sideRectangle : rightRectangle){
                g.drawImage(this.sideRectangle, sideRectangle.x, sideRectangle.y, sideRectangle.width, sideRectangle.height, null);
            }
            
           //if (gameFinished == 0){//game over screen
            
           //g.drawImage(gameOver, 0, 0, WIDTH, HEIGHT, null);
            
      

        //draw the font on the screen
        g.setColor (Color.WHITE);
        g.setFont(scoreFont);
        g.drawString("" + score, WIDTH/2, 50);
            }
    
    
       
     
        // GAME DRAWING ENDS HERE
 
     
     
 
   
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
         
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE
            if (start){
                // make the player wait to play the game
   
                //moving the player from left to right
                if (leftkey) {
                    squid.x = squid.x - 3;
                } 
                if (rightkey) {
                    squid.x = squid.x + 3;
                }
                    // go through all of rectangles
                    for (Rectangle sideRectangle : rightRectangle) {
                        // making the rectangles to go down
                        sideRectangle.y = sideRectangle.y + 3;
                        if (sideRectangle.y > 600){
                            sideRectangle.y = - 100;
                        }
                    }
                    // go through all of diamonds
                    for (Rectangle diamond : diamonds){
                        //making the diamonds to go down
                        diamond.y = diamond.y + 2;
                        if (diamond.y > 600){
                            diamond.y = -100;
                            
                        }
                    }
      
                    //go through all of rectangles
                    for (Rectangle sideRectangle : rightRectangle){
                         // when the squid will collide a rectangle
                        if(squid.intersects(sideRectangle)){
                            // if yes the squid is hit
                            done = true;
                            
                        }
                    }                 
                    // go through all of diamonds
                    for (Rectangle diamond : diamonds ){
                        // when the squid will collide with a diamond it will ad a score
                        if (squid.intersects (diamond)){
                            score = score + 1 ;
                        }
                    }
                                         

                    
                    

                    
                        
                            
                    
   
            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try
            {
               if(deltaTime > desiredTime)
               {
                   //took too much time, don't wait
                   Thread.sleep(1);
               }else{
                  // sleep to make up the extra time
                 Thread.sleep(desiredTime - deltaTime);
               }
            }catch(Exception e){};
                }
             }
    }
    
        
    
    
    
 
            
  
     
        
          
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        FinalGame game = new FinalGame();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }  
    
    @Override
    public void keyPressed(KeyEvent e){
        //moving the squid to the left using this left key
        int key = e.getExtendedKeyCode();
        if (key == KeyEvent.VK_LEFT){
            leftkey = true;
        // moving the squid to the rigth using the rigth key
        } 
        if (key == KeyEvent.VK_RIGHT){
            rightkey = true;

        }
    }
    @Override
    public void keyReleased (KeyEvent e){
        int key = e.getExtendedKeyCode();
        if (key == KeyEvent.VK_LEFT){
            leftkey = false;
        } 
        if (key == KeyEvent.VK_RIGHT){
            rightkey = false;
        }
    }

    
    }


    
    
    
    
    
        
    
        
    

