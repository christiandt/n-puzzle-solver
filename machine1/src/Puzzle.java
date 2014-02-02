import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;



public class Puzzle {
	
	String[][] board;
	int size;
	int emptyX;
	int emptyY;
	
	public static void main (String args[]){
		Puzzle puzzle = new Puzzle();
		try {
			puzzle.board = puzzle.getBoard(args[0]);
		} catch (IOException e) {
			System.out.println(e);
		}
		puzzle.printBoard();
		System.out.println(puzzle.calculateHeuristics());
	}
	
		
	private String[][] getBoard(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine();
		size = line.split(" ").length;
		int j = 0;
		String[][] board = new String[size][size];
		while (line != null) {
			String row[] = line.split(" ");
			for (int i = 0; i < row.length; i++) {
				board[j][i] = row[i];
				if(row[i].equals("X")){
					emptyX = i;
					emptyY = j;
				}
			}
			line = br.readLine();
			j++;
		}
		br.close();
		return board;
	}
	
	
	private void printBoard(){
		for (int i = 0; i < board.length; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
		System.out.println("X: "+emptyX+", Y: "+emptyY);
	}
	
	
	
	private int calculateHeuristics(){
		int numDist = 0;
		int total = 0;
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board.length; x++) {
				String piece = board[y][x];
				if (piece.equals("X")) {
					numDist = 0;
				}
				else{
					int number = Integer.parseInt(piece);
					int goalX = (number-1) % size;
					int goalY = (int)Math.floor(number/size);
					numDist = Math.abs(x-goalX)+Math.abs(y-goalY);
				}
				total+=numDist;
			}
		}
		return total;
	}
		
}