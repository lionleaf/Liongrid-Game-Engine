package mapeditor;

public class IsometricTransformation {
	
	private static float[][] matrix = {{1 , 0},
									   {0 , 1}};
	
	private static float[][] inverseMatrix = {{1 , 0},
											  {0 , 1}};
	
	public static void verify(){
		int result;
		boolean verified = false;
		
		result = Math.round(getX(inverseMatrix[0][0], inverseMatrix[1][0]));
		verified |= result == 1;
		assert result == 1;
		
		result = Math.round(getY(inverseMatrix[0][0], inverseMatrix[1][0]));
		verified |= result == 0;
		assert  result == 0;
		
		result = Math.round(getX(inverseMatrix[0][1], inverseMatrix[1][1]));
		verified |= result == 0;
		assert  result == 0;
		
		result = Math.round(getY(inverseMatrix[0][1], inverseMatrix[1][1]));
		verified |= result == 1;
		assert  result == 1;
		
		if(!verified){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Matrix varifaction was false!");
			}
		}
		
	}

	public static float getX(float x, float y){
		float result;
		result = x * matrix[0][0];
		result += y * matrix[0][1];
		return result;
	}
	
	public static float getInversX(float x, float y){
		float result;
		result = x * inverseMatrix[0][0];
		result += y * inverseMatrix[0][1];
		return result;
	}
	
	public static float getY(float x, float y){
		float result;
		result = x * matrix[1][0];
		result += y * matrix[1][1];
		return result;
	}
	
	public static float getInversY(float x, float y){
		float result;
		result = x * inverseMatrix[1][0];
		result += y * inverseMatrix[1][1];
		return result;
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
