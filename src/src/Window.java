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
	JFrame f=new JFrame();//creating instance of JFrame 
	
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu= new JMenu("File");
	JMenu editMenu= new JMenu("Edit");
	
	JMenuItem openMenuItem = new JMenuItem("Open");
	JMenuItem saveMenuItem = new JMenuItem("Save");
	JMenuItem convoMenuItem = new JMenuItem("Convolu��o");
	JMenuItem cropMenuItem = new JMenuItem("Crop");
	JMenuItem sepiaMenuItem = new JMenuItem("Sepia");
	
	JPanel panel = new JPanel();
	
	JTextField filename = new JTextField();
	JTextField dir = new JTextField();
	
	JLabel label;
	
	BufferedImage image;
	BufferedImage imageCrop;
	BufferedImage imageSepia;
	BufferedImage imageConvo;
	
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

			    openMenuItem.addActionListener(this);  //botar oq tem q fazer
				saveMenuItem.addActionListener(this);
				convoMenuItem.addActionListener(this);
				cropMenuItem.addActionListener(this);
				sepiaMenuItem.addActionListener(this);
				
				CropMouseListener cropMouseListener = new CropMouseListener();
				panel.addMouseListener(cropMouseListener);
				panel.addMouseMotionListener(cropMouseListener);
				
				panel.setSize(600,600);
				
				fileMenu.add(openMenuItem);
				fileMenu.add(saveMenuItem);
				editMenu.add(convoMenuItem);
				editMenu.add(cropMenuItem);
				editMenu.add(sepiaMenuItem);
				
				menuBar.add(fileMenu);
				menuBar.add(editMenu);
				menuBar.setSize(600, 20);
				
				f.setTitle("Trab 3 - Felipe e Fernando"); // goyzice
				f.setLayout(new BorderLayout()); // sei la n�o sei oq � melhor
				f.getContentPane().add(menuBar, BorderLayout.PAGE_START);
				f.getContentPane().add(panel, BorderLayout.CENTER);//adding button in JFrame
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	          
				f.setSize(600,600); //  tamanho padr�o 
				f.setVisible(true);//making the frame visible  
			
	}
	public void actionPerformed(ActionEvent ev){
		if(ev.getSource() == openMenuItem){ // se veio do open
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
				label = new JLabel(new ImageIcon(image)); // botando a imagem num label. ???? alguma coisa melhor pra isso ???? - tem que ter , �ocorro.
				panel.add(label);
				f.pack(); // resize
				f.setVisible(true); // desenhar again pra botar a imagem
			}else {
				// se n�o selecionar imagem ?
			}
		    
		}
		if(ev.getSource() == saveMenuItem){
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  	
		      }
		      if (rVal == JFileChooser.CANCEL_OPTION) {
		        filename.setText("You pressed cancel");
		        dir.setText("");
		      }
		}
		if(ev.getSource() == convoMenuItem){
			int filterSize =5;
			int filterIterations = 1;
			Convolucao convo = new Convolucao(image,filterSize ,filterIterations);
			ArrayData[] array = convo.rodaFiltro();
			JLabel label1 = null;
			try {
				image = convo.writeOutputImage(array);     // desenha a imagem nova tendo o array resultante da convolu��o
				label1 = new JLabel(new ImageIcon(image)); //adiciona a imagem em outro label , ainda n�o gosto disso mas parece unica op��o
			} catch (IOException e) {
				// TODO Auto-generated catch block// n�o tinha imagem
				System.out.println("Exception dps de tentar escrever a image no label");
				e.printStackTrace();
			} 
			//panel.remove(label); // remover ou n�o a imagem original
			panel.add(label1 , BorderLayout.CENTER);// botando a imagem num label. ?
			f.pack(); // resize
			f.setVisible(true); // desenhar again pra botar a imagem
		}
		
		if(ev.getSource() ==  cropMenuItem){ // Not working
			crop = true;
		}
		
		if(ev.getSource() == sepiaMenuItem){
			SepiaFilter sepiaFilter = new SepiaFilter(image);
			int sepiaIntensity = 30 ;
			int sepiaDepth = 20;
			try {
				image = sepiaFilter.writeOutputImageSepia(sepiaIntensity, sepiaDepth);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JLabel label3 = new JLabel(new ImageIcon(image));
			panel.add(label3, BorderLayout.CENTER);
			f.pack();
			f.setVisible(true);
		}
	}
	
	private class CropMouseListener implements MouseListener, MouseMotionListener {
		private int startX;
		private int startY;
		
		private int endX;
		private int endY;

		@Override
		public void mousePressed(MouseEvent e) {
			if (!crop) return;
			
			System.out.println("cliquei o mouse");
			this.startX = e.getX();
			this.startY = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			this.endX = e.getX();
			this.endY = e.getY();
			Crop cropper = new Crop();
			imageCrop = cropper.crop(image, startX, startY, endX, endY); // pegar events do mouse
			JLabel label2 = new JLabel(new ImageIcon(imageCrop));
			panel.add(label2);
			f.pack();
			f.setVisible(true);
			
			cropped= true;
			crop = false;
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

