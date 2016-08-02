package ga;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Population {
	
	Image [] images;
	Image master= null;
	int width;
	int height;
	int size;
	public Population(int size, Image m)
	{
		images= new Image[size];
		master= m;
	}
	public Population(int size, BufferedImage m) {
		 images = new Image[size];
		 master= new Image(m);
	}

	public void populate() {
		width = this.master.getImg().getWidth();
		height = this.master.getImg().getHeight();
		size = images.length;
		for (int n=0; n<size; n++)
		{
			images[n] = newImage();
			for(int i=0; i<width; i++)
			{
				for(int j=0; j<height; j++)
				{
					int p= randomPixel();
					images[n].getImg().setRGB(i, j, p);
				}
			}
			images[n].setFitness(calculateFitness(images[n]));

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
	private Image newImage() {
		return new Image(new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB));
	}

	private int calculateFitness(Image image) {
		int fitness=0;
		for(int i=0; i<width; i++)
		{
			for(int j=0; j<height; j++)
			{	
				Color c1 = new Color(master.getImg().getRGB(i, j));	
				Color c2 = new Color(image.getImg().getRGB(i, j));
				int pixelFitness= colorDelta(c1,c2); 	
  				fitness += pixelFitness;
			}
		}
		return fitness;
	}
	
	private int colorDelta(Color c1, Color c2) {
		//get delta per color
		int deltaAlpha = c1.getAlpha()- c2.getAlpha();
		int deltaRed = c1.getRed() - c2.getRed();
		int deltaGreen = c1.getGreen() - c2.getGreen();
		int deltaBlue = c1.getBlue() - c2.getBlue();
		return (int) Math.sqrt(((deltaAlpha*deltaAlpha)+ (deltaRed*deltaRed) + (deltaGreen*deltaGreen) + (deltaBlue*deltaGreen))); 
	}

	public Population evolve(boolean elitism) {

		Population  newPop= new Population(100, master);
		// Keep our best image
		int elitismOffset;
        if (elitism) {
            newPop.images[0] = this.getFittest();
            elitismOffset = 1;
        }
        else {
            elitismOffset = 0;
        }
		
        for (int n = 0; n < images.length; n++) 
        {
        	// Crossover population
        	BufferedImage img1= this.images[n].getImg();
        	BufferedImage img2= this.getFittest().getImg();
		        	
        	newPop.images[n]= crossover(img1, img2);
            newPop.images[n].mutate();
            newPop.images[n].setFitness(calculateFitness(newPop.images[n]));
        }
		return newPop;
	}
	private Image tournamentSelect(Population pop) {
		return null;
	}
	
	// Crossover images
    public Image crossover(BufferedImage img1, BufferedImage img2) {
        Image img = newImage();
        
		for(int i=0; i<width; i++)
		{
			for(int j=0; j<height; j++)
			{
				Color c = new Color(master.getImg().getRGB(i, j));	
				Color c1 = new Color(img1.getRGB(i, j));
				Color c2 = new Color(img2.getRGB(i, j));
				int p1= colorDelta(c,c1);
				int p2= colorDelta(c,c2);
				if(p1<p2)
				{
					int p = (c1.getAlpha()<<24)| (c1.getRed()<<16)| (c1.getGreen()<<8)|c1.getBlue();
					img.getImg().setRGB(i, j, p);
				}
				else
				{
					int p = (c2.getAlpha()<<24)| (c2.getRed()<<16)| (c2.getGreen()<<8)|c2.getBlue();
					img.getImg().setRGB(i, j, p);
				}
				
			}
			
		}
        return img;
    }

	public Image getFittest() {
		Image fittest= images[0];
		for(int i=1; i<images.length; i++)
		{
			if(fittest.getFitness()<= images[i].getFitness())
			{
				fittest= images[i];
			}
		}
		return fittest;
	}

}
