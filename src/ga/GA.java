package ga;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GA {
	static final int MAX_GENERATION= 50;
	static final boolean ELITISM = false;
	static int SIZE = 100;
	public static int generation;
	public static void main(String[] args) {
		BufferedImage master = getMasterImage();
		
		Population myPop = new Population(SIZE, master);
		myPop.populate();
		generation=0;		
		while(generation<MAX_GENERATION)
		{

			myPop=myPop.evolve(ELITISM);
			Image prime = myPop.getFittest();
			generation++;
			draw(master,prime.getImg(), generation);
		    System.out.println(generation+ " " + prime);
		    
		    
		    File outputfile = new File("mona"+ generation + ".png");
			 try {
				ImageIO.write(prime.getImg(), "png", outputfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
	}

	private static void draw(BufferedImage master, BufferedImage prime, int generation) {
		// TODO Auto-generated method stub
		
	}

	private static BufferedImage getMasterImage() {
		// TODO Auto-generated method stub
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("Images/facebook.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return img;
	}

}
