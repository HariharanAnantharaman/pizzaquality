package pizza.pizzaquality;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;

/**
 * Hello world!
 *
 */
public class GetFlickrImages 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World.Lets get URL" );
        
        String apiKey = args[0];
        String sharedSecret = args[1];
        String folderloc=args[2];
        int numResults=Integer.parseInt(args[3]);
        int keywordsize=Integer.parseInt(args[4]);
        int endPos=5+keywordsize;
        String[] parameters=new String[keywordsize];
        
        for (int startIndex=5,index=0;startIndex<endPos;startIndex++,index++){
        	parameters[index]=args[startIndex].replace("&", " ");
        	System.out.println("index:"+index);
        	System.out.println(parameters[index]);
        	
        }
        //String[] parameters=(String[])tags.toArray();
        Flickr f = new Flickr(apiKey, sharedSecret, new REST());
     
        SearchParameters sp=new SearchParameters();
        
        sp.setTags(parameters);
        sp.setTagMode("AND");
      
       try {
        	 PhotoList<Photo> list= f.getPhotosInterface().search(sp, numResults,1);
        	 System.out.println("Result size"+list.size());
        	 list.parallelStream().forEach(photo -> {
        		 try {
        		 	System.out.println(photo.getLargeUrl());
        		 	URL url=new URL(photo.getLargeUrl());
					InputStream inputStream=url.openStream();
					File file = new File(folderloc+photo.getId()+".jpg");
					file.createNewFile();
					Files.copy(inputStream, Paths.get(folderloc+photo.getId()+".jpg"), StandardCopyOption.REPLACE_EXISTING);
        		 }catch ( IOException  e ) {
 					e.printStackTrace();
 				}
        	 });
          				
		} catch (FlickrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
