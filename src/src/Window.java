package src;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import util.ArrayData;


public class Window implements Runnable, ActionListener {  
	private JFrame frame =new JFrame();//creating instance of JFrame 
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu= new JMenu("File");
	private JMenu editMenu= new JMenu("Edit");
	private JMenu colorFilterMenu = new JMenu("Color Filter");	
	
	private JMenuItem openMenuItem = new JMenuItem("Open");
	private JMenuItem saveMenuItem = new JMenuItem("Save");
	private JMenuItem undoMenuItem = new JMenuItem("Undo");
	private JMenuItem boxMenuItem = new JMenuItem("Box");
	private JMenuItem gaussianMenuItem = new JMenuItem("Gaussian");
	private JMenuItem cropMenuItem = new JMenuItem("Crop");
	private JMenuItem sepiaMenuItem = new JMenuItem("Sepia");
	private JMenuItem redFilterItem =  new JMenuItem("Red Filter");
	private JMenuItem greenFilterItem =  new JMenuItem("Green Filter");
	private JMenuItem blueFilterItem =  new JMenuItem("Blue Filter");
	private JMenuItem sobelMenuItem = new JMenuItem("Sobel");
	
	private JPanel panel = new JPanel();
	
	private JTextField filename = new JTextField();
	private JTextField dir = new JTextField();
	
	private JLabel lblOriginalImage;
	private JLabel lblCurrentImage;
	
	private BufferedImage image;
	
	private List<BufferedImage> previousImages;
	
	int startX = 0;
	int startY= 0;
	int endX = 0;
	int endY = 0;
	
	boolean crop = false;
	
	boolean cropped = false;
	
	public static void main(String[] args) throws IOException {  
			SwingUtilities.invokeLater( new Window());
	}
	
	public void run(){
		//JButton b=new JButton("click");//creating instance of JButton  
		//b.setBounds(130,100,100, 40);//x axis, y axis, width, height  
		
		previousImages = new ArrayList<BufferedImage>();
		prepareMenus();
		
		createCropMouseListener();
		
		panel.setSize(600,600);
		
		frame.setTitle("Trab 3 - Felipe e Fernando"); // goyzice
		frame.setLayout(new BorderLayout()); // sei la n�o sei oq � melhor
		frame.getContentPane().add(menuBar, BorderLayout.PAGE_START);
		frame.getContentPane().add(panel, BorderLayout.CENTER);//adding button in JFrame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	          
		frame.setSize(600,600); //  tamanho padr�o 
		frame.setVisible(true);//making the frame visible  
	}

	private void prepareMenus() {
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		editMenu.add(undoMenuItem);
		editMenu.add(boxMenuItem);
		editMenu.add(gaussianMenuItem);
		editMenu.add(cropMenuItem);
		editMenu.add(sepiaMenuItem);
		colorFilterMenu.add(redFilterItem);
		colorFilterMenu.add(greenFilterItem);
		colorFilterMenu.add(blueFilterItem);
		editMenu.add(sobelMenuItem);
		
		undoMenuItem.setEnabled(false);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(colorFilterMenu);
		menuBar.setSize(600, 20);
		
		openMenuItem.addActionListener(this);  //botar oq tem q fazer
		saveMenuItem.addActionListener(this);
		undoMenuItem.addActionListener(this);
		boxMenuItem.addActionListener(this);
		gaussianMenuItem.addActionListener(this);
		cropMenuItem.addActionListener(this);
		sepiaMenuItem.addActionListener(this);
		redFilterItem.addActionListener(this);
		greenFilterItem.addActionListener(this);
		blueFilterItem.addActionListener(this);
		sobelMenuItem.addActionListener(this);
	}
	
	private void createCropMouseListener() {
		CropMouseListener cropMouseListener = new CropMouseListener();
		panel.addMouseListener(cropMouseListener);
		panel.addMouseMotionListener(cropMouseListener);
	}
	
	public void actionPerformed(ActionEvent ev){
		if(ev.getSource() == openMenuItem){ // se veio do open
			openImage();
		}
		
		if(ev.getSource() == saveMenuItem){
			 saveImage();
		}
		
		if(ev.getSource() == undoMenuItem){
			undoLastAction();
			updateImage();
		}
		
		if(ev.getSource() == boxMenuItem){
			applyBoxFilter();
			updateUndoMenu();
			updateImage();
		}
		
		if(ev.getSource() == gaussianMenuItem){
			applyGaussianFilter();
			updateUndoMenu();
			updateImage();
		}
		
		if(ev.getSource() == sepiaMenuItem){
			applySepiaFilter();
			updateUndoMenu();
			updateImage();
		}
		
		if(ev.getSource() == sobelMenuItem){
			applySobelFilter();
			updateUndoMenu();
			updateImage();
		}
		
		if(ev.getSource() ==  cropMenuItem){ // Not working
			crop = true;
		}
		if(ev.getSource() == redFilterItem){
			applyRedFilter();
			updateUndoMenu();
			updateImage();
		}
		if(ev.getSource() == greenFilterItem){
			applyGreenFilter();
			updateUndoMenu();
			updateImage();
		}
		if(ev.getSource() == blueFilterItem){
			applyBlueFilter();
			updateUndoMenu();
			updateImage();
		}	
	}

	private void undoLastAction() {
		image = previousImages.remove(previousImages.size()-1);
		updateUndoMenu();
	}

	private void updateUndoMenu() {
		if (previousImages.isEmpty()) {
			undoMenuItem.setEnabled(false);
		} else {
			undoMenuItem.setEnabled(true);
		}
	}

	private void applyBoxFilter() {
		int filterSize = 5;
		int filterIterations = 1;
		Convolucao convo = new Convolucao(image,filterSize ,filterIterations);
		ArrayData[] array = convo.rodaFiltro();
		try {
			previousImages.add(image);
			image = convo.writeOutputImage(array);     // desenha a imagem nova tendo o array resultante da convolu��o
		} catch (IOException e) {
			System.out.println("Exception dps de tentar escrever a image no label");
			e.printStackTrace();
		}
	}
	
	private void applyGaussianFilter() {
		int filterSize = 5;
		int filterIterations = 1;
		
		GaussianFilter gaussianFilter = new GaussianFilter(image,filterSize ,filterIterations);
		ArrayData[] array = gaussianFilter.rodaFiltro();
		try {
			previousImages.add(image);
			image = gaussianFilter.writeOutputImage(array);     // desenha a imagem nova tendo o array resultante da convolu��o
		} catch (IOException e) {
			System.out.println("Exception dps de tentar escrever a image no label");
			e.printStackTrace();
		}
	}

	private void applySepiaFilter() {
		SepiaFilter sepiaFilter = new SepiaFilter(image);
		int sepiaIntensity = 30 ;
		int sepiaDepth = 20;
		try {
			previousImages.add(image);
			image = sepiaFilter.writeOutputImageSepia(sepiaIntensity, sepiaDepth);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void applySobelFilter() {
		SobelFilter convo = new SobelFilter(image);
		ArrayData[] array = convo.rodaFiltro();
		try {
			previousImages.add(image);
			image = convo.writeOutputImage(array);     // desenha a imagem nova tendo o array resultante da convolu��o
		} catch (IOException e) {
			System.out.println("Exception dps de tentar escrever a image no label");
			e.printStackTrace();
		}
	}

	private void updateImage() {
		lblCurrentImage.setIcon(new ImageIcon(image)); //adiciona a imagem em outro label , ainda n�o gosto disso mas parece unica op��o
		//panel.add(imageLabel , BorderLayout.CENTER);// botando a imagem num label. ?
		frame.pack(); // resize
		frame.setVisible(true); // desenhar again pra botar a imagem
	}

	private void saveImage() {
		JFileChooser c = new JFileChooser();
		  // Demonstrate "Save" dialog:
		  int rVal = c.showSaveDialog(panel);
		  if (rVal == JFileChooser.APPROVE_OPTION) {
			File file = c.getSelectedFile();
		    filename.setText(c.getSelectedFile().getName());
		    dir.setText(c.getCurrentDirectory().toString());
		    File saveFile =  new File(file +".png");
		    try {
				ImageIO.write(image , "PNG" ,saveFile); // salvar em jpg vai com cores erradas
			} catch (IOException e) {
				e.printStackTrace();
			}  	
		  }
		  if (rVal == JFileChooser.CANCEL_OPTION) {
		    filename.setText("You pressed cancel");
		    dir.setText("");
		  }
	}

	private void openImage() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("C:/Users/Felipe/Documents/GitHub/Trabalho3CG/Trabalho-3-CG")); // come�a no diret�rio do projeto. NAO SEI BOTAR DINAMICO
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif"); // filtro de extens�es
		chooser.setFileFilter(filter);
		int val = chooser.showOpenDialog(panel);
		
		if(val == JFileChooser.APPROVE_OPTION){
		    File file = chooser.getSelectedFile();
			
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				image = null;
				e.printStackTrace();
			}
			
			lblOriginalImage = new JLabel(new ImageIcon(image)); // botando a imagem original num label. ???? alguma coisa melhor pra isso ???? - tem que ter , �ocorro.
			panel.add(lblOriginalImage);
			
			lblCurrentImage = new JLabel();
			panel.add(lblCurrentImage , BorderLayout.CENTER);// botando a imagem atual num label.
			
			frame.pack(); // resize
			frame.setVisible(true); // desenhar again pra botar a imagem
		}else {
			// se n�o selecionar imagem ?
		}
	}
	private void applyRedFilter() {
		ColorFilters redFilter = new ColorFilters(image);
		previousImages.add(image);
		image = redFilter.filterRed();
		
	}
	
	private void applyGreenFilter() {
		ColorFilters greenFilter = new ColorFilters(image);
		previousImages.add(image);
		image = greenFilter.filterGreen();
					
	}

	private void applyBlueFilter() {
		ColorFilters blueFilter = new ColorFilters(image);
		previousImages.add(image);
		image = blueFilter.filterBlue();
					
	}
	
	private class CropMouseListener implements MouseListener, MouseMotionListener {
		private int startX;
		private int startY;
		
		private int endX;
		private int endY;
		
		private int offsetX;
		private int offsetY;
		
		private boolean selecting;
		
		private boolean isInsideImage(int x, int y) {
			int width = image.getWidth();
			int height = image.getHeight();
			
			return x >= offsetX && y > offsetY &&
				   x < (offsetX + width) && y < (offsetY + height);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (!crop) return;
			
			calculateOffset();
			
			if (!isInsideImage(e.getX(), e.getY())) return;
			
			this.startX = e.getX();
			this.startY = e.getY();
			
			selecting = true;
		}

		private void calculateOffset() {
			if (lblCurrentImage.getIcon() != null) {
				offsetX = lblCurrentImage.getX();
				offsetY = lblCurrentImage.getY();
			} else {
				offsetX = lblOriginalImage.getX();
				offsetY = lblOriginalImage.getY();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (!crop || !selecting) return;
			
			this.endX = e.getX();
			this.endY = e.getY();
			
			previousImages.add(image);

			crop();
			
			updateImage();
			
			cropped= true;
			
			selecting = false;
			crop = false;
			
			updateUndoMenu();
		}

		private void crop() {
			Crop cropper = new Crop();
			
			int aX = startX - offsetX;
			int aY = startY - offsetY;
			
			int bX = endX - offsetX;
			int bY = endY - offsetY;

			if (bX < 0) {
				bX = 0;
			} else if (bX > image.getWidth()) {
				bX = image.getWidth() - 1;
			}
			
			if (bY < 0) {
				bY = 0;
			} else if (bY > image.getHeight()) {
				bY = image.getHeight() - 1;
			}
			
			image = cropper.crop(image, aX, aY, bX, bY);
		}
		
		
		@Override
		public void mouseClicked(MouseEvent arg0) { }
		
		@Override
		public void mouseEntered(MouseEvent arg0) { }
		
		@Override
		public void mouseExited(MouseEvent arg0) { }

		@Override
		public void mouseDragged(MouseEvent e) { }

		@Override
		public void mouseMoved(MouseEvent e) { }
	}
	
}  
