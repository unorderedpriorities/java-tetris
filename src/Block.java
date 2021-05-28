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
		xLocation=5;
		yLocation=0;
	}

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
		for(int i=0;i<state.length;i++) {
			for(int n=0;n<state[i].length;n++){
				if(state[i][n]==1){
					if(!(Tetris.matrix[getyLocation()+i][getxLocation()+n]==null)) {
						return false;
					}
				}
			}
		}
		return true;
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
	
}
