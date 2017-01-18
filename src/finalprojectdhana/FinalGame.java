package finalprojectdhana;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author fabed2976
 */


public class FinalGame extends JComponent{

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
    
    // make the colour of the background
    // adding the lines the the squid needs to avoid
    Rectangle [] sidetopbrick = new Rectangle [5];
    Rectangle [] sidebottombrick= new Rectangle [5];
    boolean [] passedbrick = new boolean [5];
    
    //make a boolean for the player to wait the start to play the game
    boolean start = false;
    boolean end = false;
    
    // creating an integer for the speed of the game
    int speed = 3;
    
    // the gap between sidetopbrick and sidebottombrick
    int brickGap = 200;
    
    // distance between the brick
    int brickSpacing = 100;
    
    // the width of a single brick
    int brickWidth = 50;
    
    // the height of a brick
    int brickHeight = HEIGHT - 50;
    
    // minimum distance from edge
    int minDistance = 200;
    
    
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
        g.fillOval(175, 105, 40, 40);
        
        // adding the tails of the squid
        g.setColor (Color.WHITE);
        g.fillRect(185, 50, 5, 90);
        
        //adding another tail
        g.setColor (Color.WHITE);
        g.fillRect(200, 50, 5, 90);
        
        // drawing the bricks that the squid needs to avoid
        g.setColor (Color.DARK_GRAY);
        // drawing the bricks
        for (int i = 0; i < sidetopbrick.length; i++){
            g.fillRect(sidetopbrick[i].x,sidetopbrick [i].y,sidetopbrick [i].width, sidetopbrick[i].height);
            g.fillRect(sidebottombrick [i].x, sidebottombrick[i].y, sidebottombrick[i].width , sidebottombrick[i]. height);
        }
        
        
        
        
        // GAME DRAWING ENDS HERE
    }
    
    // set up the bricks
    public void setupthebricks (int brickPosition){
        // create a random generator
        Random randGen = new Random ();
        //generate the x position
        int brickY = randGen.nextInt(HEIGHT - 2 * minDistance) + minDistance;
        //generate the new pipe y position
        int brickX = sidetopbrick [brickPosition].y;
        brickX = brickX + (brickWidth + brickSpacing) * sidetopbrick.length;
        
        sidebottombrick[brickPosition].setBounds (brickX, brickY, brickWidth, brickHeight);
        sidetopbrick[brickPosition].setBounds(brickX, brickY - brickGap - brickHeight, brickWidth, brickHeight);
        
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
        int pipeX = 600;
        Random randGen = new Random();
        for (int i = 0; i < sidetopbrick.length; i++) {
            // generating a random x  position
            int pipeY = randGen.nextInt(HEIGHT - 2 * minDistance) + minDistance;
            sidebottombrick[i] = new Rectangle(pipeX, pipeY, brickWidth, brickHeight);
            sidetopbrick[i] = new Rectangle(pipeX, pipeY - brickGap - brickHeight, brickWidth, brickHeight);
            
            // move the pipeX value over
            pipeY = pipeY + brickWidth + brickSpacing;
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
                    for (int i = 0; i < sidetopbrick.length; i++){
                        sidetopbrick[i].x = sidetopbrick[i].x - speed;
                        sidebottombrick[i].y = sidebottombrick [i].y - speed;
                        // check if the brick is off the screen
                        if (sidetopbrick [i].x + brickWidth < 0){
                            // move the pipe
                            setupthebricks(i);
                            
                        }
                        
                    }
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
}