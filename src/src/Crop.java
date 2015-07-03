package src;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crop {
	
	public BufferedImage crop(BufferedImage image, int startX,int startY, int endX , int endY){
		BufferedImage img = image.getSubimage(startX, startY, endX, endY); //fill in the corners of the desired crop location here
		BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = copyOfImage.createGraphics();
		g.drawImage(img, 0, 0, null);
		return copyOfImage; //or use it however you want
		}
	

public BufferedImage crop1(BufferedImage src, Rectangle rect)
{
    BufferedImage dest = new BufferedImage((int)rect.getWidth(),(int) rect.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
    Graphics g = dest.getGraphics();
    g.drawImage(src, 0, 0, (int) rect.getWidth(),(int) rect.getHeight(),(int) rect.getX(),(int) rect.getY(),(int) rect.getX() + (int) rect.getWidth(),(int) rect.getY() + (int)rect.getHeight(), null);
    g.dispose();
    return dest;
}
}