import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class Puzzle {
	private Node root;
	private int size;
	private BufferedWriter out;
	
	public static void main (String args[]){
		Puzzle puzzle = new Puzzle();
		try {
			puzzle.root = puzzle.getRoot(args[0]);
			puzzle.solve(args[1]);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
		
	private Node getRoot(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		size = line.split(" ").length;
		int j = 0;
		String[][] board = new String[size][size];
		while (line != null) {
			String row[] = line.split(" ");
			for (int i = 0; i < row.length; i++) {
				board[j][i] = row[i];
			}
			line = br.readLine();
			j++;
		}
		br.close();
		Node root = new Node(board);
		return root;
	}
	
	
	public String[][] cloneBoard(String[][] board) {
	    String[][] result = new String[this.size][this.size];
	    for (int i = 0; i < this.size; i++) {
	        System.arraycopy(board[i], 0, result[i], 0, this.size);
	    }
	    return result;
	}
	
	private Node moveLeft(Node node){
		if(node.getXposX()<=0){
			return null;			
		}
		else{
			String[][] board = cloneBoard(node.getBoard());
			board[node.getXposY()][node.getXposX()-1] = node.getBoard()[node.getXposY()][node.getXposX()];
			board[node.getXposY()][node.getXposX()] = node.getBoard()[node.getXposY()][node.getXposX()-1];
			Node left = new Node(board);
			left.setDirection('l');
			return left;
		}
	}
	
	private Node moveRight(Node node){
		if(node.getXposX()>=this.size-1){
			return null;			
		}
		else{
			String[][] board = cloneBoard(node.getBoard());
			board[node.getXposY()][node.getXposX()+1] = node.getBoard()[node.getXposY()][node.getXposX()];
			board[node.getXposY()][node.getXposX()] = node.getBoard()[node.getXposY()][node.getXposX()+1];
			Node right = new Node(board);
			right.setDirection('r');
			return right;
		}
	}
	
	private Node moveUp(Node node){
		if(node.getXposY()<=0){
			return null;			
		}
		else{
			String[][] board = cloneBoard(node.getBoard());
			board[node.getXposY()-1][node.getXposX()] = node.getBoard()[node.getXposY()][node.getXposX()];
			board[node.getXposY()][node.getXposX()] = node.getBoard()[node.getXposY()-1][node.getXposX()];
			Node up = new Node(board);
			up.setDirection('u');
			return up;
		}
	}
	
	private Node moveDown(Node node){
		if(node.getXposY()>=this.size-1){
			return null;			
		}
		else{
			String[][] board = cloneBoard(node.getBoard());
			board[node.getXposY()+1][node.getXposX()] = node.getBoard()[node.getXposY()][node.getXposX()];
			board[node.getXposY()][node.getXposX()] = node.getBoard()[node.getXposY()+1][node.getXposX()];
			Node down = new Node(board);
			down.setDirection('d');
			return down;
		}
	}
	
	private ArrayList<Node> generateChilderen(Node node){
		ArrayList<Node> childeren = new ArrayList<Node>();
		Node right = this.moveRight(node);
		if(right!=null){
			childeren.add(right);
			right.setG(node.getG()+1);
		};
		Node left = this.moveLeft(node);
		if(left!=null){
			childeren.add(left);
			left.setG(node.getG()+1);
		};
		Node up = this.moveUp(node);
		if(up!=null){
			childeren.add(up);
			up.setG(node.getG()+1);
		};
		Node down = this.moveDown(node);
		if(down!=null){
			childeren.add(down);
			down.setG(node.getG()+1);
		};
		return childeren;
	}

	
	private void solve(String output) throws IOException{
		this.out = new BufferedWriter(new FileWriter(output));
		final long startTimer = System.nanoTime();
		ArrayList<Node> open = new ArrayList<Node>();
		ArrayList<Node> closed = new ArrayList<Node>();
		open.add(root);
		
		while(!open.isEmpty()){
			int minF = Integer.MAX_VALUE;
			Node node_current = null;
			
			// Make sure the program ends if 30 minutes has passed
			if((System.nanoTime() - startTimer)/1000000000>=1800){
				this.out.write("Program ended due to 30 minute time limit");
				this.out.close();
				System.exit(0);
			}
			
			// Find Node with lowest F value in Open-list
			for (Node node : open) {
				if(node.getF()<=minF){
					minF = node.getF();
					node_current = node;
				}
			}
			
			// If Heuristic is equal to zero, we have fount the solution
			// Go through in reverse order, and print solution
			if(node_current.getH()<=0){
				final long duration = System.nanoTime() - startTimer;
			    final double timer = ((double)duration / 1000000000);
				StringBuilder solution = new StringBuilder();
				while(node_current.getParent()!=null){
					solution.append(node_current.getDirection());
					node_current = node_current.getParent();
				}
				solution = solution.reverse();
				this.out.write(solution.toString());
				this.out.newLine();
				//System.out.println("Moves: " + (solution.length()));
				this.out.write((int)timer + " seconds");
				this.out.close();
				break;
			}
			
			// If this is not the solution, check all the possible children
			open.remove(node_current);
			closed.add(node_current);
			for (Node child : generateChilderen(node_current)) {
				// If node is allready visited, do not visit again
				if (closed.contains(child)) {
					break;
				}
				// If node is not in the queue or the current node 
				if(!open.contains(child) || (node_current.getG()+1)<child.getG()){
					child.setParent(node_current);        //set this node as parent
					child.setG(node_current.getG()+1);    //set G-value to current+1
					if(!open.contains(child)){            //if this is a child, not in the queue, add it
						open.add(child);
					}
				}
				
			}
		}
	}
	
	
}