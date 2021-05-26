
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
	private static final int HEIGHT = 600;

	//required global variables (initialize these in the constructor) 
	private BufferedImage image;
	private Graphics g;
	public Timer timer;
	public Block currentBlock;
	public Color[][] matrix;
	//change this to whatever object(s) you are animating
	public int deltaTime=10;
	//Constructor required by BufferedImage
	public Tetris() {
		//set up Buffered Image and Graphics objects
		image =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();
		g.setColor(Color.blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
	
		

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
			g.setColor(Color.blue);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
				
			
			
		
			repaint();
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

}