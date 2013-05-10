package pathfinding;

import java.util.AbstractMap.SimpleEntry;

public class Node {
	
	public int costFromStartToNode;
	public int costFromNodeToEnd;
	public int costSum;
	
	public SimpleEntry<Integer, Integer> parent;
}
