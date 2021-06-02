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
		xLocation=3;
		yLocation=0;
	}
	public Block(Block mainBlock) {
		xLocation=mainBlock.getxLocation();
		yLocation=mainBlock.getyLocation();
		blockStates=mainBlock.getBlockStates();
		color=Color.white;
		currentState=mainBlock.getCurrentState();

	}



	//Attempts to rotate the block to the right direction, so all the checks happen in canRotate and reverts back to previous state if it cant
	public void rotateRight(){
		this.currentState++;
		if(this.currentState > 3){
			this.currentState = 0;
			if(!canRotate()){
				this.currentState = 3;
				return;
			}
		}
		if(!canRotate()){
			this.currentState--;
			return;
		}
	}

	public void rotateLeft()
	{
		this.currentState--;
		if(!canRotate()){
			this.currentState++;
			return;
		}
		if(this.currentState < 0){
			this.currentState = 3;
			if(!canRotate()){
				this.currentState = 0;
				return;
			}
		}
	}
	public void draw(Graphics g, int x, int y, int size) {
        g.setColor(getColor());
        //draws the four blocks
        int[][] state = getCurrentStateMap();
        for(int i=0;i<state.length;i++) {
            for(int n=0;n<state[i].length;n++){
                if(state[i][n]==1){
                    drawBlock(g,x+size*n,y+size*i,size);
                }
            }
        }
    }

	
	//draws an individual block at the spot in question
	public void drawBlock(Graphics g, int x, int y, int size) {
		g.setColor(color);
		g.fillRect(x, y, size, size);
		g.setColor(Color.black);
		g.drawLine(x, y, x+size, y);
		g.drawLine(x+size, y, x+size, y+size);
		g.drawLine(x, y, x, y+size);
		g.drawLine(x, y+size, x+size, y+size);


	}
	public int getCurrentState(){
		return currentState;
	}
	public int[][] getCurrentStateMap(){
		return blockStates[getCurrentState()];
	}

	public boolean canRotate(){
		int[][] state = this.getCurrentStateMap();
		int yLoc = getyLocation();
		int xLoc = getxLocation();
		int[] list = {0, 1, 2, -1, -2};
		int[] list2 = {0, -1, 1};
		Boolean returnval = true;
		for(int m = 0; m < 3; m++) {
			yLoc = getyLocation() + list2[m];
			//for loop for checking the possible wall kicks
			for (int k = 0; k < 5; k++) {
				xLoc = getxLocation() + list[k];
				for (int i = 0; i < state.length; i++) {
					for (int n = 0; n < state[i].length; n++) {
						//This checks if its out of bounds of the grid or if theres a piece at the location, basically checking if the rotation is legal
						if ((yLoc + i > 19 || xLoc + n > 9) || (yLoc + i < 0 || xLoc + n < 0) || !(Tetris.matrix[yLoc + i][xLoc + n] == null)) {
							returnval = false;
							break;
						} else {
							returnval = true;
						}
					}
					if (!returnval) {
						break;
					}
				}
				if (returnval) {
					//change hte xLocation if we've shifted it over for the wall-kick
					setxLocation(xLoc);
					break;
				}
			}
			if (returnval) {
				//change hte xLocation if we've shifted it over for the wall-kick
				setyLocation(yLoc);
				break;
			}
		}
		return returnval;
	}

	public Color getColor(){
		return color;
	}

	/**
	 * @return the xLocation
	 */
	public int getxLocation() {
		return xLocation;
	}

	/**
	 * @param xLocation the xLocation to set
	 */
	public void setxLocation(int xLocation) {
		this.xLocation = xLocation;
	}

	/**
	 * @return the yLocation
	 */
	public int getyLocation() {
		return yLocation;
	}

	/**
	 * @param yLocation the yLocation to set
	 */
	public void setyLocation(int yLocation) {
		this.yLocation = yLocation;
	}

	public int[][][] getBlockStates() {
		return blockStates;
	}
	
}
