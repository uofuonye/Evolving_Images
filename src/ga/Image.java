package ga;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Image {
	
	private BufferedImage img;
	private static final double MUTATION_RATE = 0.01;

	int fitness;
	public Image(BufferedImage img) {
		this.img= img;
		fitness =0;
	}
	public int getFitness() {
		return fitness;
	}
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	public int getWidth() {
		return img.getWidth();
	}
	public int getHeight() {
		return img.getHeight();
	}
	public int getType() {
		return img.getType();
	}
	public BufferedImage getImg()
	{
		return img;
	}
	public void mutate() {

        for (int i = 0; i < getWidth(); i++) 
        {
        	for(int j=0;j <getHeight(); j++)
        	{
        		if (Math.random() <= MUTATION_RATE) {
        			int p= randomPixel();
        			img.setRGB(i, j, p);
                }
        	}
        }
	}
	
	private int randomPixel() {
		
		Random rand= new Random();
		int a = rand.nextInt(255);//alpha
		int r = rand.nextInt(255);//red
		int g = rand.nextInt(255);//green
		int b = rand.nextInt(255);//blue
		int p = (a<<24)| (r<<16)| (g<<8)|b;
		return p;	
	}

}
