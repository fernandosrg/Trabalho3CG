package src;

import java.awt.image.BufferedImage;
import java.util.Calendar;

import util.ArrayData;

public class GaussianFilter extends Convolucao {

	public GaussianFilter(BufferedImage image, int filterSize,
			int filterIterations) {
		super(image, filterSize, filterIterations);
		
		int[] filterMatrix = buildFilterMatrix(filterSize);
		filter = new ArrayData(filterMatrix, filterSize, filterSize);
		calculateFilterNormalizer();
	}

	private int[] buildFilterMatrix(int size) {
		int[] values = new int[size];
		int[] matrix = new int[size*size];
		
		for (int i = 0 ; i < size ; i++) {
			values[i] = factorial(size-1) / (factorial(i) * factorial(size-1-i));
		}
		
		for (int i = 0 ; i < size ; i++) {
			for (int j = 0 ; j < size ; j++) {
				matrix[i*size + j] = values[i] * values[j];
			}
		}
		
		return matrix;
	}
	
	public static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
	
}
