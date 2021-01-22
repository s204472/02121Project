
/*
 * Made by:
 * 		GameObjects and classes that inherits (eg. Number, Mine, Flag and Zero): Anders Reher, 194587 and Magnus Siegumfeldt, s204472
 * 	
 * */

// This class is used mainly for other classes like Mine and Flag to extend from. 
// Because of this it is possible to make an 2-d array of different GameObjects which represents the board.

public class GameObjects {	
	private boolean visited = false;
	
	public void setVisited() {
		visited = true;
	}
	public boolean getVisited() {
		return visited;
	}
}
