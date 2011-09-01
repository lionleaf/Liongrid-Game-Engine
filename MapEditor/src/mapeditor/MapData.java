package mapeditor;

/**
 * @author Lastis
 *	Transforms from a left top oriented system, to an isometric bottom left oriented 
 *	coordinate system. And vice versa.
 */
public class MapData {
	/**
	 * Be careful only read
	 */
	public static int arrayHeight;
	/**
	 * Be careful only read
	 */
	public static int arrayWidth;
	/**
	 * Be careful only read
	 */
	public static int mapHeight;
	/**
	 * Be careful only read
	 */
	public static int mapWidth;
	
	private static final int BOTTOM = 0;
	private static final int TOP = 1;
	private static int offsetX;
	private static int offsetY;
	private static float offsetCarthY;
	private static float offsetCarthX;
	private static int mapIndices[][]; //mapIndices[xIndex][TOP or BOT] = yIndex; 
	
	public static void setUp(int mapWidth, int mapHeight, int a, int b){
		IsometricTransformation.setMatrix(a, b);
		IsometricTransformation.verify();
		changeMap(mapWidth, mapHeight);
	}
	
	public static int[][] getMapIndices(){
		return mapIndices;
	}
	
	public static void changeMap(int mapWidth, int mapHeight){
		MapData.mapHeight = mapHeight;
		MapData.mapWidth = mapWidth;
		float top = IsometricTransformation.getInversY(0, mapHeight);
		float bot = IsometricTransformation.getInversY(mapWidth, 0);
		float left = IsometricTransformation.getInversX(0, 0);
		float right = IsometricTransformation.getInversX(mapWidth, mapHeight);
		offsetX = 0;
		offsetY = mapHeight;
		offsetCarthX = left; 
		offsetCarthY = - bot;
		
		arrayHeight = (int) (Math.ceil(top) - Math.ceil(bot)) + 1;
		arrayWidth = (int) (Math.ceil(right) - Math.ceil(left)) + 1;
		
		calculateMapIndices();
	}
	
	private static void calculateMapIndices(){
		// these variables are in the Cartesian system
		float x1 = MapData.transformFromWindowX(0, 0);
		float y1 = MapData.transformFromWindowY(0, 0);
		float x2 = MapData.transformFromWindowX(mapWidth, 0);
		float y2 = MapData.transformFromWindowY(mapWidth, 0);
		float x3 = MapData.transformFromWindowX(mapWidth, mapHeight);
		float y3 = MapData.transformFromWindowY(mapWidth, mapHeight);
		float x4 = MapData.transformFromWindowX(0, mapHeight);
		float y4 = MapData.transformFromWindowY(0, mapHeight);
		
		mapIndices = new int[arrayWidth][2];
		// Gather indices from the 4 lines that limit the square view.
		squaresInLine(x1, y1, x2, y2, BOTTOM);
		squaresInLine(x2, y2, x3, y3, BOTTOM);
		squaresInLine(x3, y3, x4, y4, TOP);
		squaresInLine(x4, y4, x1, y1, TOP);
	}
	
	private static void squaresInLine(float x1, float y1, float x2, float y2, int limit){
		if(x1 > x2){
			float tmp = x1;
			x1 = x2;
			x2 = tmp;
			
			tmp = y1;
			y1 = y2;
			y2 = y1;
		}
		float dx = x2 - x1;
		float dy = y2 - y1;
		float a = dy/dx;
		// Set indices for the start and end positions
		mapIndices[(int) x1][limit] = (int) y1;
		mapIndices[(int) x2][limit] = (int) y2;
		
		int xStart = (int) Math.ceil(x1) + 1;
		
		for(int x = xStart; x < x2; x++){
			mapIndices[x][limit] = (int) (a*(x - x1) + y1);
		}
		
		if(a >= 0){ // this implies that y1 is less or equal to y2 
			int yStart = (int) Math.ceil(y1);
			for(int y = yStart; y < y2; y++){
				int x =  (int) ((y - y1)/a + x1);
				mapIndices[x][limit] = y;
			}
		}
		else{ // a is less than zero, implies that y1 is greater than y2
			int yStart = (int) Math.ceil(y1);
			for(int y = yStart; y > y2; y--){
				int x =  (int) ((y - y1)/a + x1);
				mapIndices[x][limit] = y - 1;
			} 
		}
	}
	
	/**
	 * Transforms x and y Cartesian coordinates first into an isometric positive rotated
	 * view, and then changes the offset so it can be viewed in the top left oriented 
	 * coordinate system of the window.
	 * @param x
	 * @param y
	 * @return the x coordinate in the top left oriented coordinate system.
	 */
	public static int transformToWindowX(float x, float y) {
		return (int) (IsometricTransformation.getX(x - offsetCarthX, y - offsetCarthY) 
				+ offsetX);
	}

	/**
	 * Transforms x and y Cartesian coordinates first into an isometric positive rotated
	 * view, and then changes the offset so it can be viewed in the top left oriented 
	 * coordinate system of the window. 
	 * @param x
	 * @param y
	 * @return the y coordinate in the top left oriented coordinate system.
	 */
	public static int transformToWindowY(float x, float y) {
		return (int) (- IsometricTransformation.getY(x - offsetCarthX, y - offsetCarthY) 
				+ offsetY);
	}

	/**
	 * Transforms the x and y window coordinates by first negating the offset of the 
	 * window, changing the coordinates into a bottom left oriented isometric 
	 * coordinate system. Then transforming these coordinates into a Cartesian 
	 * coordinate system by multiplying with the inverse projection.
	 * @param x
	 * @param y
	 * @return the x coordinate in the Cartesian coordinate system
	 */
	public static float transformFromWindowX(int x, int y) {
		return IsometricTransformation.getInversX(x - offsetX, -y + offsetY) + offsetCarthX;
	}
	
	/**
	 * Transforms the x and y window coordinates by first negating the offset of the 
	 * window, changing the coordinates into a bottom left oriented isometric 
	 * coordinate system. Then transforming these coordinates into a Cartesian 
	 * coordinate system by multiplying with the inverse projection.
	 * @param x
	 * @param y
	 * @return the y coordinate in the Cartesian coordinate system
	 */
	public static float transformFromWindowY(int x, int y) {
		return IsometricTransformation.getInversY(x - offsetX, - y + offsetY) + offsetCarthY;
	}
}
