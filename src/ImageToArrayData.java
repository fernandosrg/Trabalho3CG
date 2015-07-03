
import java.awt.image.BufferedImage;

import util.ArrayData;
		
public class ImageToArrayData {
	BufferedImage image;
	public ImageToArrayData(BufferedImage image){
		this.image = image;
	}
	
    public ArrayData[] getArrayData(){
    	int width = image.getWidth();
        int height = image.getHeight();
        int[] rgbData = image.getRGB(0, 0, width, height, null, 0, width);
        	
    	
	    ArrayData reds = new ArrayData(width, height);
	    ArrayData greens = new ArrayData(width, height);
	    ArrayData blues = new ArrayData(width, height);
	    
	    for(int y = 0; y < height; y++){
		    for(int x = 0; x < width; x++){
		        int rgbValue = rgbData[y * width + x];
		        reds.set(x, y, (rgbValue >>> 16) & 0xFF);
		        greens.set(x, y, (rgbValue >>> 8) & 0xFF);
		        blues.set(x, y, rgbValue & 0xFF);
		    }
		}
		
	    return new ArrayData[] { reds, greens, blues };

}
}
