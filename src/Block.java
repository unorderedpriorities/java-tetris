import java.awt.*;

public class Block {

	private int[][][] blockStates;
	private Color color;

	private int currentState;

	private int xLocation;
	private int yLocation;

	public Block(Color color, int[][][] blockStates){
		this.blockStates = blockStates;
		this.color = color;
		currentState = 0;
	}

	public void rotateRight(){
		this.currentState++;
		if(this.currentState > 3){
			this.currentState = 0;
		}
	}

	public void rotateLeft()
	{
		this.currentState--;
		if(this.currentState < 0){
			this.currentState = 3;
		}
	}
	public int getCurrentState(){
		return currentState;
	}
	public int[][] getCurrentStateMap(){
		return blockStates[getCurrentState()];
	}

	public boolean canRotate(){
		return true;
	}
}
