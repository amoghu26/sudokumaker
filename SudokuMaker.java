/**
 *	SudokuMaker - Creates a Sudoku puzzle using recursion and backtracking
 * 
 *  Sudoku is a game where a board must be filled without a repetition of a number
 * 	1-9 in a certain row, column, or 3x3 box
 *
 *	@author Amogh Upadhyaya
 *	@since  20 February 2021
 *
 */
public class SudokuMaker {

	// fields
	private int[][] puzzle;
	private final int PUZZLE_SIZE = 9;

	/** Constructor */ 
	public SudokuMaker()
	{
		puzzle = new int [PUZZLE_SIZE][PUZZLE_SIZE];
	}
	
	/** main method */
	public static void main(String [] args)
	{
		SudokuMaker sm = new SudokuMaker();
		sm.emptyPuzzle();
		sm.createPuzzle();
		sm.printPuzzle();
	}

	/**
	 * emptyPuzzle - sets all values on the grid to zero
	 */
	public void emptyPuzzle()
	{
		for(int i = 0; i<puzzle.length; i++)
		{
			for(int j = 0; j<puzzle[i].length; j++)
			{
				puzzle[i][j] = 0;
			}
		}
	}

	/**
	 * isBlank - checks if a certain grid slot is blank
	 * @param row the row number
	 * @param col the column number
	 * @return whether the slot is blank or not
	 */
	public boolean isBlank(int row, int col)
	{
		if(puzzle[row][col] == 0) return true;
		return false;
	}

	/**
	 * isValid - checks if a given value can be entered into the grid
	 * 			 without violating the game rules
	 *  @param row The row number
	 *  @param col The column number
	 *  @param val The value being checked (1-9)
	 *  @return whether the value can be entered into the board or not
	 */
	public boolean isValid(int row, int col, int val) 
	{
		// checks if the value being checked is already in the row
		for(int i = 0; i<PUZZLE_SIZE; i++)
		{
			if(puzzle[row][i] == val) return false;
		}

		// checks if the value being checked is already in the column
		for (int i = 0; i < PUZZLE_SIZE; i++) 
		{
			if (puzzle[i][col] == val) return false;
		}

		// checks if the value being checked is already in the respective 3x3 box
		for(int i = row - row%3; i<row - row%3 + 3; i++)
		{
			for(int j = col - col%3; j<col - col%3 + 3; j++)
			{
				if(puzzle[i][j] == val) return false;
			}
		}

		return true; // returns true otherwise
	}

	/**
	 * createPuzzle - solves the puzzle recursively
	 * @return whether the program can continue or whether it has to roll back to the 	*		  previous slot and attempt to fill it again
	 */
	public boolean createPuzzle()
	{
		int [] list = new int [9]; // holds the numbers 1-9 in random order
		int check = 0; // checks if the math.random value returned is already in the list array
		boolean works = true; // returns true if the value being checked can be placed in the list array

		//fills the list array in random order with values 1-9
		for(int i = 0; i<list.length; i++)
		{
			check = (int)(Math.random()*9 + 1);
			for(int j = 0; j<list.length; j++)
			{
				if(check == list[j]) works = false;
			}
			if(works == true)
			{
				list[i] = check;
			}
			else i--;
			works = true;
		}

		//solves the puzzle recursively
		for(int i = 0; i<PUZZLE_SIZE; i++)
		{
			for(int j = 0; j<PUZZLE_SIZE; j++)
			{
				if(isBlank(i, j))
				{
					for(int k = 0; k<list.length; k++)
					{
						if(isValid(i, j, list[k]))
						{
							puzzle[i][j] = list[k];
							if(createPuzzle()) return true; 
							else puzzle[i][j] = 0;
						}
					}
					return false;
				}
			}
		}
		return true;
	}
		
	/**
	 *	printPuzzle - prints the Sudoku puzzle with borders
	 *	If the value is 0, then print an empty space; otherwise, print the number.
	 */
	public void printPuzzle() {
		System.out.println("\nSudoku Puzzle\n");
		System.out.print("  +-----------+-----------+-----------+\n");
		String value = "";
		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[0].length; col++) {
				// if number is 0, print a blank
				if (puzzle[row][col] == 0) value = " ";
				else value = "" + puzzle[row][col];
				if (col % 3 == 0)
					System.out.print("  |  " + value);
				else
					System.out.print("  " + value);
			}
			if ((row + 1) % 3 == 0)
				System.out.print("  |\n  +-----------+-----------+-----------+\n");
			else
				System.out.print("  |\n");
		}
		System.out.println();
	}
}