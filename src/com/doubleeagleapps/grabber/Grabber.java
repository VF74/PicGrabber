package com.doubleeagleapps.grabber;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Rob Moon
 * Downloads pics from a designated URL
 *
 */
public class Grabber 
{

	public Grabber() {
		//Sean Pic Grabber
	}
	
	/**
	 * 
	 * @param agrs
	 */
	public static void main(String agrs[])
	{
		String inputFile = "test4.txt";
		
		Grabber grab = new Grabber();
		System.out.println("*** Processing Input File");
		StringBuilder buffer = grab.openFile(inputFile);
		
		System.out.println("*** Looking for URL's");
		List<String> urlList = grab.processInputFile(buffer);

		System.out.println("*** Downloading Files");
		grab.processDownload(urlList);
		
		System.out.println("*** Program Ended Successfully ***");
	}
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
	private StringBuilder openFile(String filename)
	{
		StringBuilder  buffer = new StringBuilder ();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

			String sCurrentLine = "";
			while ((sCurrentLine = br.readLine()) != null) {
				buffer.append(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return buffer;
		
	}
	
	//"http://images.pixieset.com/7277446/92006464cac3deb8d608ae251cc8135c-large.jpg"
	private List<String> processInputFile(StringBuilder buffer)
	{
		int counter = 0;
		List<String>picList = new ArrayList();
		String text = buffer.toString();
	
		String patternString1 = "(//images.pixieset.com/......./.......................................jpg)";
	
	    Pattern pattern = Pattern.compile(patternString1);
	    Matcher matcher = pattern.matcher(text);
	
	    while(matcher.find()) {
	        System.out.println("found: " + matcher.group(1)); //+ matcher.group(2));
	        picList.add( matcher.group(1));
	        counter++;
	    }
	    
	    System.out.println("--->>> Number of matches found: " + counter); 
	    return picList;
	}
	
	//http://images.pixieset.com/7277446/92006464cac3deb8d608ae251cc8135c-large.jpg
	private void processDownload(List<String>picList)
	{	
		int counter = 0;
			String picName = "";
			String targetDirectory = "c:\\pics";
			
			try {
				
				for(String sourceUrl: picList) {
					picName =  sourceUrl.substring(sourceUrl.lastIndexOf('/'));
					 System.out.println("--->>> Downloading image " + picName); 
					downloadImage("http:"+sourceUrl, targetDirectory+picName);
					counter++;
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			 System.out.println("--->>> Number of pics downloaded: " + counter); 
	}
	
	
	 private void downloadImage(String sourceUrl, String targetDirectory) throws MalformedURLException, IOException, FileNotFoundException
     {
		 URL url = new URL(sourceUrl);
		 InputStream in = new BufferedInputStream(url.openStream());
		 OutputStream out = new BufferedOutputStream(new FileOutputStream(targetDirectory));

		 for(int i; (i = in.read()) != -1;) {
		     out.write(i);
		 }
		 in.close();
		 out.close();
	 }	
	

}
