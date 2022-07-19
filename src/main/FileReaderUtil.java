package main;

import java.io.File;
import java.util.ArrayList;

public class FileReaderUtil {

	public static ArrayList<String> readAllFilesInPath(String folder, String prefix, String endfix) {
		File pathLoc = new File(folder);
		ArrayList<String> files = listFilesInFolder(pathLoc, prefix, endfix);
		return files;
	}
	
	private static ArrayList<String> listFilesInFolder(File folder, String prefix, String endfix){
		ArrayList<String> files = new ArrayList<String>();
		for(final File fileEntry : folder.listFiles()) {
			if(fileEntry.isDirectory()) {
				files.addAll(listFilesInFolder(fileEntry, prefix, endfix));
			} else {
				if(fileEntry.getName().startsWith(prefix) && fileEntry.getName().endsWith(endfix)) {
					files.add(fileEntry.getAbsolutePath());
				}
			}
		}
		return files;
	}
	
}
