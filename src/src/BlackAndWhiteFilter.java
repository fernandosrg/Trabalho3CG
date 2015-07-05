package src;

import java.awt.image.BufferedImage;
import java.io.IOException;

import util.ArrayData;

public class BlackAndWhiteFilter {
	ArrayData[] arrayData;

	ImageToArrayData transformer;
	
	public BlackAndWhiteFilter(BufferedImage image) {
		this.transformer =   new ImageToArrayData(image);
		this.arrayData =  transformer.getArrayData();
	}
	  	
	public BufferedImage writeOutputImageSepia() throws IOException {
	    ArrayData reds = arrayData[0];
	    ArrayData greens =arrayData[1];
	    ArrayData blues = arrayData[2];
	    BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
	                                                  BufferedImage.TYPE_INT_ARGB);
	  
	    int totalValue = 0;
	    for (int y = 0; y < reds.height; y++) {
		      for (int x = 0; x < reds.width; x++) {
		    	totalValue += (reds.get(x, y) + greens.get(x, y) + blues.get(x, y)) / 3;
		      }
		}
		      
		int averageValue = totalValue / (reds.height * reds.width);
	    
	    for (int y = 0; y < reds.height; y++) {
	      for (int x = 0; x < reds.width; x++) {
	    	int value = (reds.get(x, y) + greens.get(x, y) + blues.get(x, y)) / 3;
	    	
	    	if (value < averageValue) {
	    		value = 0;
	    	} else {
	    		value = 255;
	    	}
	    	
	        outputImage.setRGB(x, y, (value << 16) | (value << 8) | value | -0x01000000);
	      }
	    }
	    
	    return outputImage;
	}
}
