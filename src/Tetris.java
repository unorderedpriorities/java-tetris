
//required import statements
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Tetris extends JPanel {

	//set the initial width and height of your image
	private static final int WIDTH = 800;
	private static final int HEIGHT = 1000;
	private static int size = 50;

	//required global variables (initialize these in the constructor) 
	private BufferedImage image;
	private Graphics g;
	public Timer timer;
	public static Block currentBlock;
	public static Color[][] matrix;
	public static int score;
	//change this to whatever object(s) you are animating
	public int deltaTime=10;
	public int currentTime=0;
	public int dropTime=100;
	//Constructor required by BufferedImage
	public Tetris() {
		//set up Buffered Image and Graphics objects
		image =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();
		g.setColor(Color.blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		matrix = new Color[10][20];
		score = 0;
		currentBlock = new SquareBlock();
	
		

		//set up and start the Timer
		timer = new Timer(deltaTime, new TimerListener());
		timer.start();
		addKeyListener(new Keyboard());
		setFocusable(true);


	}
	private class Keyboard implements KeyListener {
		public void keyPressed(KeyEvent e) { 
		  //INCOMPLETE
		}
		public void keyReleased(KeyEvent e) { }
		public void keyTyped(KeyEvent e) { }
		}

	//TimerListener class that is called repeatedly by the timer
	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(currentTime%dropTime==0) {
				dropLogic();
			}
			g.setColor(Color.blue);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			//draws grid
			//vertical lines
			g.setColor(Color.white);
			for(int i=1; i<12;i++) {
				g.drawLine(i*size, size, i*size, 1000);
			}
			//horizontial lines
			g.drawLine(size, size, 200, size);
			for(int n=size;n<HEIGHT;n=n+size) {
				g.drawLine(size, n, 11*size, n);
			}
			for(int i=0;i<matrix.length;i++) {
				for(int n=0;n<matrix[i].length;n++){
					if(!(matrix[i][n]==null)){
						drawBlock(g,(i+1)*size,(n+1)*size,matrix[i][n]);

					}
				}
			}
			currentBlock.draw(g,(currentBlock.getxLocation()+1)*size,(currentBlock.getyLocation()+1)*size,size);



			repaint();
			currentTime+=deltaTime;
		}
		
	}

	//do not modify this
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

	//main method with standard graphics code
	public static void main(String[] args) {
		JFrame frame = new JFrame("tetris!");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Tetris()); 
		frame.setVisible(true);
	}
	//to be called on a drop frame
	public static void dropLogic() {
		if(isTouching()) {
			onContact();
		} else {
			currentBlock.setyLocation(currentBlock.getyLocation()+1);

		}
	}
	//determines whether the block will hit something when it goes down
	public static boolean isTouching() {
		int[][] state = currentBlock.getCurrentStateMap();
        for(int i=0;i<state.length;i++) {
            for(int n=0;n<state[i].length;n++){
                if(state[i][n]==1){
					//if the spot in the matrix directly below the block is occupied, this returns a true for isTouching
					//if the block is touching the ground, it returns true
                    if(currentBlock.getyLocation()+i>state.length||(!(matrix[currentBlock.getxLocation()+n][currentBlock.getyLocation()+i+1]==null))) {
						return true;

					}
                }
            }
        }
		return false;
	}

	//to be called when a block collides with the ground
	public static void onContact() {
		//turn the current block into spots in the matrix
		int[][] state = currentBlock.getCurrentStateMap();
        for(int i=0;i<state.length;i++) {
            for(int n=0;n<state[i].length;n++){
                if(state[i][n]==1){
					//if the spot in the matrix directly below the block is occupied, this returns a true for isTouching
					//if the block is touching the ground, it returns true
                    matrix[currentBlock.getxLocation()+n][currentBlock.getyLocation()+i]=currentBlock.getColor();

					
                }
            }
        }



		//check to see if there's any cleared lines
		//linescleared is amount of cleared lines
		int linescleared=0;
		for(int i=0;i<20;i++) {
			//checks to see if the line is complete
			boolean nonull=true;
			for(int n=0;n<10;n++) {
				if(matrix[n][i]==null) {
					nonull=false;
				}
			}
			if(nonull) {
				linescleared++;
				moveDown(i);
			}
		}
		//move the queue into the current block
		currentBlock = new SquareBlock();
		//add something to the queue
		score=+linescleared;
	}
	public static void drawBlock(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		g.fillRect(x, y, size, size);
		g.setColor(Color.black);
		g.drawLine(x, y, x+size, y);
		g.drawLine(x+size, y, x+size, y+size);
		g.drawLine(x, y, x, y+size);
		g.drawLine(x, y+size, x+size, y+size);


	}
	//call to clear a line and move the ones above it down one
	public static void moveDown(int line) {
		//clears the current line
		for(int n=0;n<10;n++) {
			matrix[n][line]=null;
		}
		//moves each line above it down one
		for(int i=line;i>0;i--) {
			for(int n=0;n<10;n++){
				matrix[n][i]=matrix[n][i-1];
			}

		}
		//clears the top line
		for(int n=0;n<10;n++){
			matrix[n][0]=null;
		}

	}
	

}