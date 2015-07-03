

import java.awt.image.BufferedImage;
import java.io.IOException;

import util.ArrayData;

public class SepiaFilter {
	ArrayData[] arrayData;

	ImageToArrayData transformer;
	
	public SepiaFilter(	BufferedImage image){
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
	  	
	public BufferedImage writeOutputImageSepia(int sepiaIntensity, 	int sepiaDepth) throws IOException{
	    ArrayData reds = arrayData[0];
	    ArrayData greens =arrayData[1];
	    ArrayData blues = arrayData[2];
	    BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
	                                                  BufferedImage.TYPE_INT_ARGB);
	  
	    for (int y = 0; y < reds.height; y++)
	    {
	      for (int x = 0; x < reds.width; x++)
	      {
	    	int sepiaAvg = (reds.get(x, y) + greens.get(x, y) + blues.get(x, y))/3;
	    	int sepiaRed = sepiaAvg + sepiaDepth *2;
	    	int sepiaGreen = sepiaAvg +sepiaDepth;
	    	int sepiaBlue =sepiaAvg;
	    	
	        int red = bound(sepiaRed, 256);
	        int green = bound(sepiaGreen, 256);
	        int blue = bound(sepiaBlue - sepiaIntensity, 256);
	        outputImage.setRGB(x, y, (red << 16) | (green << 8) | blue | -0x01000000);
	      }
	    }
	    
	    return outputImage;
	}
}

