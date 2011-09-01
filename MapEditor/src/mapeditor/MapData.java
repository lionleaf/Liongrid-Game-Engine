package mapeditor;

/**
 * @author Lastis
 *	Transforms from a left top oriented system, to an isometric bottom left oriented 
 *	coordinate system. And vice versa.
 */
public class MapData {
	
	private static int offsetX;
	private static int offsetY;
	private static int offsetCarthY;
	private static int offsetCarthX;
	
	private static int arrayHeight;
	private static int arrayWidth;
	private static int mapHeight;
	private static int mapWidth;
	
	public static void setUp(int mapWidth, int mapHeight, int a, int b){
		IsometricTransformation.setMatrix(a, b);
		IsometricTransformation.verify();
		changeMap(mapWidth, mapHeight);
	}
	
	public static void changeMap(int mapWidth, int mapHeight){
		int left = IsometricTransformation.getInversX(0, 0);
		int bot = IsometricTransformation.getInversY(mapWidth, 0);
		int top = IsometricTransformation.getInversY(0, mapHeight);
		int right = IsometricTransformation.getInversX(mapWidth, mapHeight);
		
		arrayHeight = top - bot;
		arrayWidth = right - left;
		MapData.mapHeight = mapHeight;
		MapData.mapWidth = mapWidth;

		offsetX = 0; 
		offsetY = mapHeight;
		offsetCarthX = 0 - left; 
		offsetCarthY = 0 - bot; 
	}
	
	public static void calculateMapIndices(){
		// Gather indices from the 4 lines that limit the square view.
		
		for(int y = 0; y < arrayHeight; y++){
			int x = (y-offsetCarthY)/a;
		}
	}
	
	private static int[] squaresInLine(float a, float x1, float y1, float x2, float y2){
		if(x1 > x2){
			float tmpX = x1;
			x1 = x2;
			x2 = tmpX;
		}
		float b = offsetY;
		
		
		// Check in what squares the end points are
		float y = a*x1 + b;
		
		int[] result = new int[arrayWidth];
		
		int xStart = (int) Math.ceil(x1);
		int xEnd   = (int) Math.floor(x2);
		int yStart = (int) (y1 < y2 ? Math.ceil(y1) : Math.floor(y1));
		int yEnd   = (int) (y1 < y2 ? Math.floor(y2) : Math.ceil(y2));
		
		for(int x = (int) Math.ceil(xStart); x < (int) Math.floor(xEnd); x++){
			float y = a*(x - xStart) + b;
			int yIndex = (int) Math.floor(y);
			result[x] = yIndex;
		}
		
		return null;
	}
	
	public static int getTilesX(){
		return arrayWidth;
	}
	
	public static int getTilesY(){
		return arrayHeight;
	}
	
	/**
	 * Transforms x and y Cartesian coordinates first into an isometric positive rotated
	 * view, and then changes the offset so it can be viewed in the top left oriented 
	 * coordinate system of the window.
	 * @param x
	 * @param y
	 * @return the x coordinate in the top left oriented coordinate system.
	 */
	public static int transformX(int x, int y) {
		return IsometricTransformation.getX(x- offsetCarthX, y- offsetCarthY) + offsetX;
	}




	/**
	 * Transforms x and y Cartesian coordinates first into an isometric positive rotated
	 * view, and then changes the offset so it can be viewed in the top left oriented 
	 * coordinate system of the window. 
	 * @param x
	 * @param y
	 * @return the y coordinate in the top left oriented coordinate system.
	 */
	public static int transformY(int x, int y) {
		return - IsometricTransformation.getY(x- offsetCarthX, y- offsetCarthY) + offsetY;
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
	public static int inverseTransformX(int x, int y) {
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
	public static int inverseTransformY(int x, int y) {
		return IsometricTransformation.getInversY(x - offsetX, - y + offsetY) + offsetCarthY;
	}
}
