package finalprojectdhana;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
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
    
    //game avariables
    // creating the colour of the background
    Color background = new Color (207, 136, 185);
    
   
    // adding the bricks the the squid needs to avoid
    Rectangle [] topbrick = new Rectangle [5];
    Rectangle [] bottombrick= new Rectangle [5];
    boolean [] passedbrick = new boolean [5];
    
    //make a boolean for the player to wait the start to play the game
    boolean start = false;
    boolean end = false;
    
    // jump key variable
    boolean jump = false;
    boolean lastJump = false;
    
    // creating the gravity of the squid
    int gravity = 1;
    
    //difference in zero
    int dy = 0;
    
    //this will be the velocity when you click the key
    int movevelocity = -12;
            
    // creating an integer for the speed of the game
    int speed = 3;
    
    // the gap between sidetopbrick and sidebottombrick
    int brickGap = 200;
    
    // distance between the brick
    int brickSpacing = 100;
    
    // the width of a single brick
    int brickWidth = HEIGHT - 50;
    
    // the height of a brick
    int brickHeight = 30;
    
    // minimum distance from edge
    int maxDistance = 200;
    
    // creating the left key and right key
    boolean rightkey = false;
    boolean leftkey = false;
    
    // creating a score time
    int score = 0;
    Font scoreFont = new Font ("Arial" , Font.BOLD, 42);
    
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
       
        // setting up the colour background
        g.setColor (background);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        // adding the circle squid in the game
        g.setColor(Color.WHITE);
        g.fillOval(175, 90, 40, 40);
       
        // drawing the bricks that the squid needs to avoid
        g.setColor (Color.DARK_GRAY);
        // drawing the bricks
        for (int i = 150; i < 700; i+=150){
            int randomnumber = (int)(Math.random()*100);
            g.fillRect(0, i, randomnumber, brickHeight);
            g.fillRect(randomnumber + 125, i, HEIGHT, brickHeight);
            
            //g.fillRect(topbrick[i].x,topbrick[i].y, topbrick[i].width, topbrick[i].height);
            //g.fillRect(bottombrick[i].x,bottombrick[i].y, bottombrick[i].width, bottombrick[i].height);
        }
        
        //draw the font on the screen
        g.setColor (Color.WHITE);
        g.setFont(scoreFont);
        g.drawString("" + score, WIDTH/2, 50);
    }
        
        
        
        
        // GAME DRAWING ENDS HERE
        
    
    // set up the bricks
    public void setupthebricks (int brickPosition){
        // create a random generator
        Random randGen = new Random ();
        //generate the X position
        int brickX = randGen.nextInt (HEIGHT - 2 * maxDistance) + maxDistance;
        //generate the new pipe y position
        int brickY = topbrick [brickPosition].y;
        brickY = brickY + (brickHeight+ brickSpacing) * topbrick.length;
        
        topbrick[brickPosition].setBounds (brickX, brickY, brickWidth, brickHeight);
        bottombrick[brickPosition].setBounds (brickX, brickX - brickGap - brickHeight, brickWidth, brickHeight);
        
        passedbrick[brickPosition] = false;
    }
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
        // set up the bricks
        int brickY = 400;
        Random randGen = new Random();
        for (int i = 0; i < topbrick.length; i++) {
            // generating a random y position
            int brickX = randGen.nextInt(HEIGHT - 2 * maxDistance) + maxDistance;
            
            bottombrick[i] = new Rectangle(brickX, brickY, brickWidth, brickHeight);
            topbrick[i] = new Rectangle(brickX, brickY - brickGap - brickHeight, brickWidth, brickHeight);
            
            // move the pipeY value over
            brickY = brickY + brickHeight+ brickSpacing;
            passedbrick[i] = false;

        }
         
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
            // when the player needs to wait for the game to start
            if (start){
                //get the bricks moving
                if (!end){
                    for (int i = 0; i < topbrick.length; i++){
                        topbrick[i].y = topbrick[i].y - speed;
                        bottombrick[i].y = bottombrick [i].y -  speed;
                        // check if the brick is off the screen
                        if (topbrick [i].y + brickWidth < 0){
                            // move the pipe
                            setupthebricks(i);
                            
                        }
                 // get the bird to fall
                // apply gravity
                dy = dy + gravity;
                
                // make the bird fly
                if (jump && !lastJump && !end){
                dy = movevelocity;
                }
                lastJump = jump;
                        
                    }
                    // check if bird hits top or bottom of the screen
            
                
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
        
        // starts my game loop
        game.run();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }  
    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getExtendedKeyCode();
        if (key == KeyEvent.VK_LEFT){
            leftkey = true;
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
    
    
    
        
    
        
    

