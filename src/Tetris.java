
//required import statements
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Tetris extends JPanel {

	//set the initial width and height of your image
	private static final int WIDTH = 800;
	private static final int HEIGHT = 1000;
	//how many pixels per side of a tetronimo block
	private static int size = 40;

	//required global variables (initialize these in the constructor) 
	private BufferedImage image;
	private Graphics g;
	public Timer timer;
	public static Block currentBlock;
	public static Block heldBlock;
	public static Block ghostBlock;
	//the matrix that represents the dropped pieces
	public static Color[][] matrix;
	public static int score;
	public static int linescleared;
	public static int levels=1;;
	//whether the down key is pressed
	public static boolean fastFalling;
	//whether the block has already been swapped for the held block
	public static boolean hasHeld;
	//the blocks waiting to be dropped
	public static ArrayList<Block> queue;
	public int deltaTime=10;
	public int currentTime=0;
	//ms until dropping down one
	public static int dropTime=200;
	//ms until drop locking
	public int dropLock=500;
	//ms spent touching ground
	public static int groundTime=0;

	public static boolean gameEnded = false;

	public static boolean hasWrittenToFile = false;

	private static Image endscreenBackground;

	

	//sound hell
	public AudioFormat audioFormat;
    public AudioInputStream audioInputStream;
    public Clip clip;

	public Tetris() {
		//set up Buffered Image and Graphics objects
		image =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();
		//background color of black
		g.setColor(Color.black.brighter());
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//creates matrix
		matrix = new Color[21][10];
		//score starts at zero
		score = 0;
		//the current block has not been swapped
		hasHeld=false;
		currentBlock = randomBlock();
		ghostBlock = new Block(currentBlock);


		queue = new ArrayList<Block>();
		for(int i=0;i<5;i++) {
			queue.add(randomBlock());
		}
		Clip line;
		File soundFile = new File("Tetris.wav");
		try {
		audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch(IOException e) {
			//shit.
		} catch(UnsupportedAudioFileException e) {
			//whoops.
		}
		audioFormat = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, audioFormat); // format is an AudioFormat object
		if (!AudioSystem.isLineSupported(info)) {
		// Handling the error.
		}
		// Obtain and open the line.
		try {
			line = (Clip) AudioSystem.getLine(info);
			line.open(audioInputStream);
			line.start();
		} catch (LineUnavailableException ex) {
			
		//... 
		} catch (IOException e) {
			//i have no clue how to do this.
		}
		


		//set up and start the Timer
		timer = new Timer(deltaTime, new TimerListener());
		timer.start();
		addKeyListener(new Keyboard());
		setFocusable(true);


	}
	private class Keyboard implements KeyListener {
		public void keyPressed(KeyEvent e) { 
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if(canMove(true)) {
					currentBlock.setxLocation(currentBlock.getxLocation()+1);
				}
			} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				if(canMove(false)) {
					currentBlock.setxLocation(currentBlock.getxLocation()-1);
				}
			} else if(e.getKeyCode() == KeyEvent.VK_UP) {
				currentBlock.rotateRight();
			} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				hardDrop();
			} else if (e.getKeyCode() == KeyEvent.VK_Z) {
				currentBlock.rotateLeft();
			} else if(e.getKeyCode() == KeyEvent.VK_C) {
				//checks to see if the block has already been swapped
				if(!hasHeld) {
					if(heldBlock==null) {
						heldBlock=currentBlock;
						heldBlock.setxLocation(5);
						heldBlock.setyLocation(0);
						currentBlock=queue.remove(0);
						randomBlock();
					} else {
						Block temp = heldBlock;
						heldBlock=currentBlock;
						heldBlock.setyLocation(0);
						heldBlock.setxLocation(5);
						currentBlock=temp;
					}
					hasHeld=true;
				}
			}  
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				fastFalling=true;
			}
			if(e.getKeyCode()== KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		  //INCOMPLETE
		}
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				fastFalling=false;
			}
		 }
		public void keyTyped(KeyEvent e) { }
		}

	//TimerListener class that is called repeatedly by the timer
	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(gameEnded){
				displayEndScreen(g);
				repaint();
				return;
			}
			if(isTouching(currentBlock)) {
				if(groundTime<dropLock) {
					groundTime+=deltaTime;
				} else {
					onContact();
					groundTime=0;
				}
			} else {
				groundTime=0;
				if(currentTime%dropTime==0||(fastFalling&&currentTime%30==0)) {
					currentBlock.setyLocation(currentBlock.getyLocation()+1);		
				}
			}
			ghostBlock = new Block(currentBlock);
			while(!isTouching(ghostBlock)) {
				ghostBlock.setyLocation(ghostBlock.getyLocation()+1);
			}


			g.setColor(Color.black.brighter());
			g.fillRect(0, 0, WIDTH, HEIGHT);
			//draws grid
			//vertical lines
			g.setColor(Color.white);
			for(int i=1; i<12;i++) {
				g.drawLine(i*size, size, i*size, size*21);
			}
			//horizontial lines
			g.drawLine(size, size, 200, size);
			for(int n=size;n<size*22;n=n+size) {
				g.drawLine(size, n, 11*size, n);
			}
			for(int i=0;i<matrix.length;i++) {
				for(int n=0;n<matrix[i].length;n++){
					if(!(matrix[i][n]==null)){
						drawBlock(g,(n+1)*size,(i)*size,matrix[i][n]);

					}
				}
			}
			ghostBlock.draw(g,(ghostBlock.getxLocation()+1)*size,(ghostBlock.getyLocation())*size,size);

			currentBlock.draw(g,(currentBlock.getxLocation()+1)*size,(currentBlock.getyLocation())*size,size);
			g.setColor(Color.white);
			g.setFont(new Font("Impact",Font.PLAIN,40));
			g.drawString(score+"", 0, size);


			if(!(heldBlock==null)) {
				heldBlock.draw(g, 650, 100, size);
			}
			for(int i=0;i<queue.size();i++) {
				queue.get(i).draw(g, 500, 100+150*i, size);
			}



			repaint();
			currentTime+=deltaTime;
		}
		
	}
	//moves the block down until it hits the ground
	public static void hardDrop() {
		while(!isTouching(currentBlock)) {
			currentBlock.setyLocation(currentBlock.getyLocation()+1);
		}	
		onContact();
		


	}
	//chooses a new block randomly
	public static Block randomBlock() {
		int rando=(int)(Math.random()*7);
		if(rando==0) {
			return new SquareBlock();
		} else if(rando==1) {
			return new TBlock();
		} else if(rando==2) {
			return new ZBlock();
		} else if(rando==3) {
			return new BLBlock();
		} else if(rando==4) {
			return new LBlock();
		} else if(rando==5) {
			return new LongBlock();
		} else {
			return new SBlock();
		}
	}
	//checks each element of where the block state map would be against the matrix of dropped blocks, if there's no overlap it's all good
	public static boolean canMove(boolean isRight) {
		//checks to see if the block will be moved to the right or the left
		if(isRight){
			int[][] state = currentBlock.getCurrentStateMap();
			for(int i=0;i<state.length;i++) {
				for(int n=0;n<state[i].length;n++){
					if(state[i][n]==1){
						if((currentBlock.getxLocation()+n+1)==matrix[0].length||!(matrix[currentBlock.getyLocation()+i][currentBlock.getxLocation()+n+1]==null)) {
							return false;
							
						} 
					}
				}
			}
			return true;
		}
		else {
			int[][] state = currentBlock.getCurrentStateMap();
			for(int i=0;i<state.length;i++) {
				for(int n=0;n<state[i].length;n++){
					if(state[i][n]==1){
						if((currentBlock.getxLocation()+n)==0||!(matrix[currentBlock.getyLocation()+i][currentBlock.getxLocation()+n-1]==null)) {
							return false;
							
						} 
					}
				}
			}
		}
		return true;
		
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

	//determines whether the block will hit something when it goes down
	public static boolean isTouching(Block currentBlock) {
		int[][] state = currentBlock.getCurrentStateMap();
        for(int i=0;i<state.length;i++) {
            for(int n=0;n<state[i].length;n++){
                if(state[i][n]==1){
					//if the spot in the matrix directly below the block is occupied, this returns a true for isTouching
					//if the block is touching the ground, it returns true
                    if((currentBlock.getyLocation()+i+1)==matrix.length) {
						return true;
						
					} else if(!(matrix[currentBlock.getyLocation()+i+1][currentBlock.getxLocation()+n]==null)) {
						return true;
					}
                }
            }
        }
		return false;
	}

	//to be called when a block collides with the ground
	static void onContact() {
		//turn the current block into spots in the matrix
		int[][] state = currentBlock.getCurrentStateMap();
        for(int i=0;i<state.length;i++) {
            for(int n=0;n<state[i].length;n++){
                if(state[i][n]==1){
					//if the spot in the matrix directly below the block is occupied, this returns a true for isTouching
					//if the block is touching the ground, it returns true
                    matrix[currentBlock.getyLocation()+i][currentBlock.getxLocation()+n]=currentBlock.getColor();


					
                }
            }
        }
		fastFalling=false;
		//check to see if there's any cleared lines
		//linescleared is amount of cleared lines
		int currentlinescleared=0;
		for(int i=0;i<matrix.length;i++) {
			//checks to see if the line is complete
			boolean nonull=true;
			for(int n=0;n<matrix[i].length;n++) {
				if(matrix[i][n]==null) {
					nonull=false;
				}
			}
			if(nonull) {
				currentlinescleared++;
				moveDown(i);
			}
		}
		//move the top of the queue into the current block
		currentBlock = queue.remove(0);
		//int[] list = {3, 4, 5, 6};
		for(int i = 0; i < 4; i++){
			if(matrix[0][i+3] != null){
				 gameEnded = true;
			}
		}
		
		//allows the held block to be taken again
		hasHeld=false;
		//add something to the queue
		queue.add(randomBlock());
		linescleared+=currentlinescleared;
		score+=Math.pow(currentlinescleared,2)*(levels+1);
		levels=linescleared/10;
		if(levels>17) {
			dropTime=20;
		} else {
			dropTime=(200-(levels*10));
		}

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

	public static void displayEndScreen(Graphics g){
		if(hasWrittenToFile == false){
			hasWrittenToFile = true;
		}else{
			return;
		}
		ArrayList<Integer> list = new ArrayList<Integer>();
		try{
			File file = new File("scoreboard.txt");
	//		endscreenBackground = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + "\\tetrisBackground.jpg");
			try {
				BufferedImage img = ImageIO.read(new File("tetrisBackground.jpg"));
				g.drawImage(img, 0, 0 , null);
				g.setColor(Color.CYAN);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Scanner fileScanner = new Scanner(file);
			while(fileScanner.hasNextLine()){
				list.add(fileScanner.nextInt());
			}
			if(score > 0){
				list.add(score);
			}
			Collections.sort(list);
			Collections.reverse(list);
			try{
				FileWriter writer = new FileWriter("scoreboard.txt", false);
				String txt = "";
				for(int i = 0; i < list.size(); i++){
					if(i == list.size()-1){
						txt += list.get(i);
						break;
					}
					txt += list.get(i) + "\n";
				}
				writer.write(txt);
				writer.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}catch (FileNotFoundException exception){
			return;
		}
		g.drawString("Round Score: " + score, WIDTH/2 - 80, 100);
		int place = 0;
		String placementType = "";
		for(int i = 0; i < list.size(); i++){
			if(list.get(i) == score){
				place = i+1;
				break;
			}
		}
		String txt = String.valueOf(place);
		switch(txt.charAt(txt.length()-1)){
			case '1':
				placementType = "st";
				break;
			case '2':
				placementType = "nd";
				break;
			case '3':
				placementType = "rd";
				break;
			default:
				placementType = "th";
				break;
		}
		if(score == 0){
			g.drawString("Round Score: " + score + ", Failed Attempt", WIDTH / 2 - 80, 100);

		}else {
			g.drawString("Round Score: " + score + ",  " + place + placementType, WIDTH / 2 - 80, 100);
		}
		g.drawString("--Top Rounds--", WIDTH / 2 - 60, 200);
		for(int i = 0; i < 5; i++){
			if(i+1 > list.size()){
				break;
			}
			g.drawString(String.valueOf(list.get(i)), WIDTH / 2 - 300, 420 + (i*60));
		}

	}
	//call to clear a line and move the ones above it down one
	public static void moveDown(int line) {
		//clears the current line
		for(int n=0;n<matrix[line].length;n++) {
			matrix[line][n]=null;
		}
		//moves each line above it down one
		for(int i=line;i>0;i--) {
			for(int n=0;n<matrix[i].length;n++){
				matrix[i][n]=matrix[i-1][n];
			}

		}
		//clears the top line
		for(int n=0;n<matrix[0].length;n++){
			matrix[0][n]=null;
		}
	}
	
}