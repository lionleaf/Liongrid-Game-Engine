package mapeditor;

public class IsometricProjection {
	
	private static float[][] matrix = {{1 , 0},
									   {0 , 1}};
	
	private static float[][] inverseMatrix = {{1 , 0},
											  {0 , 1}};
	
	public static boolean verify(){
		int result;
		boolean verified = false;
		
		result = getX(inverseMatrix[0][0], inverseMatrix[1][0]);
		verified |= result == 1;
		assert result == 1;
		
		result = getY(inverseMatrix[0][0], inverseMatrix[1][0]);
		verified |= result == 0;
		assert  result == 0;
		
		result = getX(inverseMatrix[0][1], inverseMatrix[1][1]);
		verified |= result == 0;
		assert  result == 0;
		
		result = getY(inverseMatrix[0][1], inverseMatrix[1][1]);
		verified |= result == 1;
		assert  result == 1;
		
		return verified;
	}

	public static int getX(float x, float y){
		float result;
		result = x * matrix[0][0];
		result += y * matrix[0][1];
		return (int) result;
	}
	
	public static int getInversX(float x, float y){
		float result;
		result = x * inverseMatrix[0][0];
		result += y * inverseMatrix[0][1];
		return (int) result;
	}
	
	public static int getY(float x, float y){
		float result;
		result = x * matrix[1][0];
		result += y * matrix[1][1];
		return (int) result;
	}
	
	public static int getInversY(float x, float y){
		float result;
		result = x * inverseMatrix[1][0];
		result += y * inverseMatrix[1][1];
		return (int) result;
	}
	
	public static void setMatrix(float a, float b){
		setMatrixX(a, b);
		setMatrixY(a, b);
	}
	
	public static void setMatrixX(float a, float b){
		matrix[0][0] = a;
		matrix[1][0] = b;
		setInverseMatrix();
	}
	
	public static void setMatrixY(float a, float b){
		matrix[0][1] = -a;
		matrix[1][1] = b;
		setInverseMatrix();
	}
	
	private static void setInverseMatrix() {
		float axby = matrix[0][0]*matrix[1][1];
		float aybx = matrix[0][1]*matrix[1][0];
		inverseMatrix[0][0] = matrix[1][1]/ (axby - aybx);
		inverseMatrix[0][1] = - matrix[0][1] / (axby - aybx);
		inverseMatrix[1][0] = - matrix[1][0] / (axby - aybx);
		inverseMatrix[1][1] = matrix[0][0] / (axby - aybx);
	}
}
