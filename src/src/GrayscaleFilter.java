package src;

import java.awt.image.BufferedImage;
import java.io.IOException;

import util.ArrayData;

public class GrayscaleFilter {
	ArrayData[] arrayData;

	ImageToArrayData transformer;
	
	public GrayscaleFilter(BufferedImage image) {
		this.transformer =   new ImageToArrayData(image);
		this.arrayData =  transformer.getArrayData();
	}
	  	
	public BufferedImage writeOutputImageSepia() throws IOException {
	    ArrayData reds = arrayData[0];
	    ArrayData greens =arrayData[1];
	    ArrayData blues = arrayData[2];
	    BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
	                                                  BufferedImage.TYPE_INT_ARGB);
	  
	    for (int y = 0; y < reds.height; y++) {
	      for (int x = 0; x < reds.width; x++) {
	    	int grayValue = (reds.get(x, y) + greens.get(x, y) + blues.get(x, y)) / 3;
	        outputImage.setRGB(x, y, (grayValue << 16) | (grayValue << 8) | grayValue | -0x01000000);
	      }
	    }
	    
	    return outputImage;
	}
}
