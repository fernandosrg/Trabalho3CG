package src;

import java.awt.image.BufferedImage;
import java.io.IOException;

import util.ArrayData;

public class Convolucao {
	BufferedImage image;
	ArrayData[] arrayData;
	ArrayData filter;
	int filterSize;
	int filterIterations;
	int filterNormalizer;
	ImageToArrayData transformer;
	
	public Convolucao(BufferedImage image, int filterSize , int filterIterations){
		this.image = image;
		this.filterSize = filterSize;
		this.filterIterations =  filterIterations;
		this.filterNormalizer=  filterSize*filterSize;
		this.transformer =   new ImageToArrayData(image);
		this.arrayData =  transformer.getArrayData();
		
		createFilter(filterSize);
	}
	
	public Convolucao(BufferedImage image, ArrayData filter , int filterIterations){
		this.image = image;
		this.filterSize = filter.width;
		this.filterIterations =  filterIterations;
		this.transformer =   new ImageToArrayData(image);
		this.arrayData =  transformer.getArrayData();
		
		calculateFilterNormalizer();
		
		this.filter = filter;
	}

	protected void calculateFilterNormalizer() {
		filterNormalizer = 0;
		for (int v : filter.dataArray)
			filterNormalizer += v;
	}
	
	public void createFilter(int size)
	 {
	    ArrayData filterArray = new ArrayData(size,size);
	    int value = 1;
	    for (int i = 0; i < size; i++)
	    {
	      //System.out.print("[");
	      for (int j = 0; j < size; j++)
	      {
	        filterArray.set(j, i, value);
	        //System.out.print(" " + filterArray.get(j, i) + " ");
	      }
	      //System.out.println("]");
	    }
	     
	    filter = filterArray;
	 }
	
	  private static int bound(int value, int endIndex)
	  {
	    if (value < 0)
	      return 0;
	    if (value < endIndex)
	      return value;
	    return endIndex - 1;
	  }
	  
	  public ArrayData convolute(ArrayData inputData) {
		  return convolute(inputData, this.filter);
	  }
	  
	public ArrayData convolute(ArrayData inputData, ArrayData filter){
		int inputWidth = inputData.width;
	    int inputHeight = inputData.height;
	    int filterSizeW = filter.width;
	    if ((filterSizeW <= 0) || ((filterSizeW & 1) != 1))
	      throw new IllegalArgumentException("Filtro precisa ter tamanho impar");
	    int filterRadius = filterSizeW >>> 1;
	    
	    ArrayData outputData = new ArrayData(inputWidth, inputHeight);
	    
	    for (int i = inputWidth - 1; i >= 0; i--)
	    {
	      for (int j = inputHeight - 1; j >= 0; j--)
	      {
	        double newValue = 0.0;
	        for (int kw = filterSizeW - 1; kw >= 0; kw--)
	          for (int kh = filterSizeW - 1; kh >= 0; kh--)
	            newValue += filter.get(kw, kh) * inputData.get(
	                          bound(i + kw - filterRadius, inputWidth),
	                          bound(j + kh - filterRadius, inputHeight));
	        outputData.set(i, j, (int)Math.round(newValue / filterNormalizer));
	      }
	    }
	    return outputData;
	}
	
	public ArrayData[] rodaFiltro(){
		for (int n = 0; n < filterIterations; n++)
	        for (int i = 0; i < arrayData.length; i++)
	            arrayData[i] = convolute(arrayData[i]);
	    return arrayData;
	}
	
	public BufferedImage writeOutputImage(ArrayData[] redGreenBlue) throws IOException {
	  ArrayData reds = redGreenBlue[0];
	  ArrayData greens = redGreenBlue[1];
	  ArrayData blues = redGreenBlue[2];
	  BufferedImage outputImage = new BufferedImage(reds.width, reds.height,
	                                                BufferedImage.TYPE_INT_ARGB);
	  for (int y = 0; y < reds.height; y++)
	  {
	    for (int x = 0; x < reds.width; x++)
	    {
	      int red = bound(reds.get(x, y), 256);
	      int green = bound(greens.get(x, y), 256);
	      int blue = bound(blues.get(x, y), 256);
	      outputImage.setRGB(x, y, (red << 16) | (green << 8) | blue | -0x01000000);
	    }
	  }
	  
	  return outputImage;
	}
	
}
