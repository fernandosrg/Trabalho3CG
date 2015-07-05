package src;

import java.awt.image.BufferedImage;

import util.ArrayData;

public class SobelFilter extends Convolucao {
	private ArrayData gx;
	private ArrayData gy;

	public SobelFilter(BufferedImage image) {
		super(image, 3, 1);
		filterSize = 3;
		
		int[] gxMatrix = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
		gx = new ArrayData(gxMatrix, filterSize, filterSize);
		
		int[] gyMatrix = {-1, -2, -1, 0, 0, 0, 1, 2, 1};
		gy = new ArrayData(gyMatrix, filterSize, filterSize);
	}
	
	@Override
	public ArrayData[] rodaFiltro() {
		for (int n = 0; n < filterIterations; n++)
	        for (int i = 0; i < arrayData.length; i++) {
	            ArrayData arrayDataX = convolute(arrayData[i], gx);
	            ArrayData arrayDataY = convolute(arrayData[i], gy);
	            
	            int[] newData = new int[arrayData[i].dataArray.length];
	            
	            for (int j = 0 ; j < newData.length ; j++) {
	            	newData[j] = (int) Math.sqrt((arrayDataX.dataArray[j]*arrayDataX.dataArray[j])+(arrayDataY.dataArray[j]*arrayDataY.dataArray[j]));
	            	
	            	if (newData[j] > 255)
	            		newData[j] = 255;
	            }
	            
	          arrayData[i] = new ArrayData(newData, arrayData[i].width, arrayData[i].height);
	        }
		
	    return arrayData;
	}

}
