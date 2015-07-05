package src;

import java.awt.image.BufferedImage;

import util.ArrayData;

public class ColorFilters {

	private ImageToArrayData transformer;
	private ArrayData[] arrayData;

	public ColorFilters(BufferedImage image){
		this.transformer =   new ImageToArrayData(image);
		this.arrayData =  transformer.getArrayData();
	}
	private static int bound(int value, int endIndex){
	    if (value < 0)
	      return 0;
	    if (value < endIndex)
	      return value;
	    return endIndex - 1;
	}

	public BufferedImage filterRed() {
	    ArrayData reds = arrayData[0];
	    ArrayData greens =arrayData[1];
	    ArrayData blues = arrayData[2];
	    BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
	                                                  BufferedImage.TYPE_INT_ARGB);
	  
	    for (int y = 0; y < reds.height; y++)
	    {
	      for (int x = 0; x < reds.width; x++)
	      {	    	
		        int red = 0;
		        int green = bound(greens.get(x, y), 256);
		        int blue = bound(blues.get(x, y), 256);
	        outputImage.setRGB(x, y, (red << 16) | (green << 8) | blue | -0x01000000);
	      }
	    }
	    
	    return outputImage;
	}

	public BufferedImage filterGreen() {
	    ArrayData reds = arrayData[0];
	    ArrayData greens =arrayData[1];
	    ArrayData blues = arrayData[2];
	    BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
	                                                  BufferedImage.TYPE_INT_ARGB);
	  
	    for (int y = 0; y < reds.height; y++)
	    {
	      for (int x = 0; x < reds.width; x++)
	      {	    	
		        int red = bound(reds.get(x, y), 256);
		        int green = 0;
		        int blue = bound(blues.get(x, y), 256);
	        outputImage.setRGB(x, y, (red << 16) | (green << 8) | blue | -0x01000000);
	      }
	    }
	    
	    return outputImage;	}

	public BufferedImage filterBlue() {
	    ArrayData reds = arrayData[0];
	    ArrayData greens =arrayData[1];
	    ArrayData blues = arrayData[2];
	    BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
	                                                  BufferedImage.TYPE_INT_ARGB);
	  
	    for (int y = 0; y < reds.height; y++)
	    {
	      for (int x = 0; x < reds.width; x++)
	      {	    	
		        int red = bound(reds.get(x, y), 256);
		        int green = bound(greens.get(x, y), 256);
		        int blue = 0;
	        outputImage.setRGB(x, y, (red << 16) | (green << 8) | blue | -0x01000000);
	      }
	    }
	    
	    return outputImage;
	}
	

}
