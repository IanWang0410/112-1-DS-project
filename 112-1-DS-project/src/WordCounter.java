

import java.io.IOException;

public class WordCounter {
	URLProcessor u = new URLProcessor();
	
    public int countKeyword(Web w, String keyword) throws IOException{
		
		//To do a case-insensitive search, we turn the whole content and keyword into upper-case:
		String content = u.fetchContent(w).toUpperCase();
		keyword = keyword.toUpperCase();
	
		int retVal = 0;
		int fromIdx = 0;
		int found = -1;
	
		while ((found = content.indexOf(keyword, fromIdx)) != -1){
		    retVal++;
		    fromIdx = found + keyword.length();
		}
		
		return retVal;
    }
}