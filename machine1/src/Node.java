import java.util.Arrays;


public class Node {
	
	private Node parent = null;
	private String[][] board;
	private char direction;
	private int xposX;
	private int xposY;
	private int size;
	private int g = 0;
	private int h;
	
	public Node(String[][] board){
		size = board.length;
		this.board = board;
		calculateHeuristics();
	}
	
	private void calculateHeuristics(){
		int numDist = 0;
		int total = 0;
		for (int y = 0; y < this.board.length; y++) {
			for (int x = 0; x < this.board.length; x++) {
				String piece = this.board[y][x];
				if (piece.equals("X")) {
					this.xposX=x;
					this.xposY=y;
					
					numDist = Math.abs(x-(this.size-1))+Math.abs(y-(this.size-1));
				}
				else{
					int number = Integer.parseInt(piece);
					int goalX = (number-1) % this.size;
					int goalY = (int)Math.floor((number-1)/this.size);
					numDist = Math.abs(x-goalX)+Math.abs(y-goalY);
				}
				total+=numDist;
			}
		}
		this.h = total;
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}
	
	public Node getParent(){
		return this.parent;
	}
	
	public void setDirection(char direction){
		this.direction = direction;
	}
	
	public char getDirection(){
		return this.direction;
	}
	
	public int getF(){
		return this.g+this.h;
	}
	
	public int getH(){
		return this.h;
	}
	
	public int getG(){
		return this.g;
	}
	
	public void setG(int g){
		this.g = g;
	}
	
	public int getXposX(){
		return this.xposX;
	}
	
	public int getXposY(){
		return this.xposY;
	}
	
	public String[][] getBoard(){
		return this.board;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < this.getBoard().length; i++) {
			string.append(Arrays.toString(this.getBoard()[i]));
			string.append("\n");
		}
		string.append("X pos: ("+this.xposX+", "+this.xposY+")");
		string.append("\n");
		string.append("f function: " + this.getG() + "+" + this.getH() + "=" + this.getF());
		string.append("\n");
		return string.toString();
	}
	
}
