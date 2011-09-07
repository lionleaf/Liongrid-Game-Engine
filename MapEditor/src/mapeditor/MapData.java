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
	private static float offsetCarthY;
	private static float offsetCarthX;
	private static int mapIndices[][]; //mapIndices[xIndex][TOP or BOT] = yIndex; 
	
	public static float x1;
	public static float y1;
	public static float x2;
	public static float y2;
	public static float x3;
	public static float y3;
	public static float x4;
	public static float y4;
	
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
		offsetCarthX = left; 
		offsetCarthY = - bot;
		
		arrayHeight = (int) top - (int) bot + 1;
		arrayWidth = (int) right - (int) left + 1;
		
		calculateMapIndices();
	}
	
	private static void calculateMapIndices(){
		// these variables are in the Cartesian system
		x1 = MapData.fromIsoToCartX(0, 0);
		y1 = MapData.fromIsoToCartY(0, 0);
		x2 = MapData.fromIsoToCartX(mapWidth, 0);
		y2 = MapData.fromIsoToCartY(mapWidth, 0);
		x3 = MapData.fromIsoToCartX(mapWidth, mapHeight);
		y3 = MapData.fromIsoToCartY(mapWidth, mapHeight);
		x4 = MapData.fromIsoToCartX(0, mapHeight);
		y4 = MapData.fromIsoToCartY(0, mapHeight);
		
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
			y2 = tmp;
		}
		
		// TODO Error at 512 x 100
		
//		System.out.println("x1 = " + x1);
//		System.out.println("y1 = " + y1);
//		System.out.println("x2 = " + x2);
//		System.out.println("y2 = " + y2);
		
		float dx = x2 - x1;
		float dy = y2 - y1;
		float a = dy/dx;
		// Set indices for the start and end positions
		mapIndices[(int) x1][limit] = (int) y1;
		mapIndices[(int) x2][limit] = (int) y2;
		
		int xStart = (int) Math.ceil(x1);
		
		for(int x = xStart; x < x2; x++){
			mapIndices[x][limit] = (int) (a*(x - x1) + y1);
		}
		
		if(a >= 0){ // this implies that y1 is less or equal to y2 
			int yStart = (int) Math.ceil(y1);
			for(int y = yStart; y < y2; y++){
				int x =  (int) ((y - y1)/a + x1);
				if(limit == TOP && mapIndices[x][limit] > y) continue;
				if(limit == BOTTOM && mapIndices[x][limit] < y) continue;
				mapIndices[x][limit] = y;
			}
		}
		else{ // a is less than zero, implies that y1 is greater than y2
			int yStart = (int) Math.floor(y1);
			for(int y = yStart; y > y2; y--){
				int x =  (int) ((y - y1)/a + x1);
				if(limit == TOP && mapIndices[x][limit] > y) continue;
				if(limit == BOTTOM && mapIndices[x][limit] < y) continue;
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
	public static float fromCartToIsoX(float x, float y) {
		float result = IsometricTransformation.getX(x - offsetCarthX, y - offsetCarthY);
		return result;
	}

	/**
	 * Transforms x and y Cartesian coordinates first into an isometric positive rotated
	 * view, and then changes the offset so it can be viewed in the top left oriented 
	 * coordinate system of the window. 
	 * @param x
	 * @param y
	 * @return the y coordinate in the top left oriented coordinate system.
	 */
	public static float fromCartToIsoY(float x, float y) {
		float result = IsometricTransformation.getY(x - offsetCarthX, y - offsetCarthY);
		return result;
	}

	/**
	 * Transforms the x and y isometric coordinates by transforming 
	 * the coordinates into a Cartesian coordinate system by multiplying 
	 * with the inverse projection.
	 * @param x
	 * @param y
	 * @return the x coordinate in the Cartesian coordinate system
	 */
	public static float fromIsoToCartX(int x, int y) {
		return IsometricTransformation.getInversX(x, y) + offsetCarthX;
	}
	
	/**
	 * Transforms the x and y isometric coordinates by transforming 
	 * the coordinates into a Cartesian coordinate system by multiplying 
	 * with the inverse projection.
	 * @param x
	 * @param y
	 * @return the y coordinate in the Cartesian coordinate system
	 */
	public static float fromIsoToCartY(int x, int y) {
		return IsometricTransformation.getInversY(x, y) + offsetCarthY;
	}
}
