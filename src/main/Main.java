package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Main {

	//FOR THE TESTING
	//Where the output files for runPaletteTest() is saved
	private static final String TEST_OUTPUT_LOCATION = "C:\\Users\\Morten\\Desktop\\TestJava\\output\\";
	//Folder of where the palettes are located for runPaletteTest()
	private static final String TEST_PALETTES_LOCATION = "C:\\Users\\Morten\\Desktop\\TestJava\\palettes";
	//Folder of the files you wish to see with the different palettes
	private static final String TEST_IMAGES_LOCATION = "C:\\Users\\Morten\\Desktop\\TestJava\\images";
	//Which imagetype to affect
	private static final String TEST_IMAGES_TYPE = ".png";		
			
	
	//FOR THE ACTUAL CHANGE
	//Where the palette selected are located for runPaletteChange()
	private static final String CHANGE_PALETTE_LOCATION_PATH = "C:\\Users\\Morten\\Desktop\\TestJava\\palettes\\Cloud 64 color palette.txt";
	//Where all the images you want to change are located.(WARNING: This will affect all files in folders and subfolders!)
	private static final String CHANGE_FILE_DIRECTORY_PATH = "C:\\Users\\Morten\\Desktop\\Devistial\\DevistialProject\\textures";
	//Which imagetype to affect(if multiple, run several times with different values)
	private static final String CHANGE_FILE_TYPE = ".png";
	//Which prefix for the images to include(eg. 'player' would only affected files that starts their filename with player). Leave empty for all
	private static final String CHANGE_FILE_PREFIX = "";
	
	public static void main(String[] args) throws IOException {
		runPaletteTest();
		//runPaletteChange();
	}
	
	public static void runPaletteChange() throws IOException {
		Color[] colorPalette = parseColorPaletteFile(CHANGE_PALETTE_LOCATION_PATH, ",");
		ArrayList<String> allImages = FileReaderUtil.readAllFilesInPath(CHANGE_FILE_DIRECTORY_PATH, CHANGE_FILE_PREFIX, CHANGE_FILE_TYPE);
		int counter = 0;
		int PRINT_COUNTER = 50;
		for(String image : allImages) {
			BufferedImage img = ImageIO.read(new File(image));
			img = giveAdjustedImg(img, colorPalette);
			ImageIO.write(img, "png", new File(image));
			counter++;
			if(counter % PRINT_COUNTER == 0) {
				System.out.println("Currently processing at "+String.valueOf(counter)+"/"+allImages.size());
			}
		}
	}
	
	public static void runPaletteTest() throws IOException {
		ArrayList<String> palettes = FileReaderUtil.readAllFilesInPath(TEST_PALETTES_LOCATION, "", "");
		ArrayList<Color[]> colorPalettes = new ArrayList<>();
		for(String palette : palettes) {
			colorPalettes.add(parseColorPaletteFile(palette, ","));
		}
		
		ArrayList<String> files = FileReaderUtil.readAllFilesInPath(TEST_IMAGES_LOCATION, "", TEST_IMAGES_TYPE);
		
		for(String f : files) {
			File file = new File(f);
			String nameOfFile = file.getName().replace(TEST_IMAGES_TYPE, "");
			BufferedImage img = ImageIO.read(file);
			for(int i = 0; i < colorPalettes.size(); i++) {
				BufferedImage copy = giveAdjustedImg(img, colorPalettes.get(i));
				String fileName = palettes.get(i);
				String[] tempSplit = fileName.split("\\\\");
				fileName = tempSplit[tempSplit.length-1];
				File outputFile = new File(TEST_OUTPUT_LOCATION+nameOfFile+"_"+fileName.replace(".txt", ".png").replace(" ", "_"));
				System.out.println("Created a output_file: "+(outputFile.getAbsolutePath()));
				ImageIO.write(copy, "png", outputFile);
			}
		}
	}
	
	private static BufferedImage giveAdjustedImg(BufferedImage img, Color[] colorPalette) {
		//imageType can't be colorModel since that uses closest colors, while we want the actual colors.
		BufferedImage copy = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		for(int x = 0; x < copy.getWidth(); x++) {
			for(int y = 0; y < copy.getHeight(); y++) {
				Color pos = new Color(img.getRGB(x, y), true);
				Color bestMatch = null;
				double bestMatchDifference = 0.0;
				for(Color e : colorPalette) {
					if(bestMatch == null) {
						bestMatch = e;
						bestMatchDifference = getDifferenceBetweenColors(pos, e);
					} else {
						double diff = getDifferenceBetweenColors(pos, e);
						if(diff < bestMatchDifference) {
							bestMatch = e;
							bestMatchDifference = diff;
						}
					}
				}
				Color temp = new Color(bestMatch.getRed(), bestMatch.getGreen(), bestMatch.getBlue(), pos.getAlpha());
				copy.setRGB(x, y, temp.getRGB());
			}
		}
		return copy;
	}
	
	public static double getDifferenceBetweenColors(Color one, Color two) {
		double distance = Math.pow((one.getRed() - two.getRed()), 2.0) + Math.pow((one.getGreen() - two.getGreen()), 2.0) + Math.pow((one.getBlue() - two.getBlue()), 2.0);
		return Math.abs(distance);
	}
	
	public static Color[] parseColorPaletteFile(String path, String regex) throws IOException {
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String colorsString = br.readLine();
		String[] colors = colorsString.split(regex);
		Color[] colorsDecoded = new Color[colors.length];
		for(int i = 0; i < colors.length; i++) {
			colorsDecoded[i] = Color.decode("#"+colors[i]);
		}
		br.close();
		return colorsDecoded;
	}

}
