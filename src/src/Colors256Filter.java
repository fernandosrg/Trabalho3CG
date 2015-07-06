package src;

import java.awt.image.BufferedImage;
import java.io.IOException;

import util.ArrayData;

public class Colors256Filter {
	ArrayData[] arrayData;

	ImageToArrayData transformer;
	
	public Colors256Filter(BufferedImage image) {
		this.transformer =   new ImageToArrayData(image);
		this.arrayData =  transformer.getArrayData();
	}
	  	
	public BufferedImage writeOutputImageSepia() throws IOException {
	    ArrayData reds = arrayData[0];
	    ArrayData greens = arrayData[1];
	    ArrayData blues = arrayData[2];
	    BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
	                                                  BufferedImage.TYPE_INT_ARGB);
	  
	    for (int y = 0; y < reds.height; y++) {
	      for (int x = 0; x < reds.width; x++) {
	    	int red = reds.get(x, y);
	    	red = (red >> 5) << 5;
	    	
	    	int green = greens.get(x, y);
	    	green = (green >> 5) << 5;
	    	
	    	int blue = blues.get(x, y);
	    	blue = (blue >> 6) << 6;
	    	
	        outputImage.setRGB(x, y, (red << 16) | (green << 8) | blue | -0x01000000);
	      }
	    }
	    
	    return outputImage;
	}
}
