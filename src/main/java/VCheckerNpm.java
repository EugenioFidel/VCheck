import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.*;

import Model.*;


public class VCheckerNpm extends VChecker{

	private boolean outcome=true;

	public ArrayList<String> check(String server,String ip,List<Art> artifacts) {

				
		ArrayList<String> result=new ArrayList<String>();
		
		for (Art a : artifacts) {
		
		// "Npm view" command let us gather complete information regarding an artifact specified. In particular,
		// we get the complete list of versions available for the artifact. Managing that list against the version
		// to be checked, let us conclude whether or not it can be found in NPM package registry
			
		String cmd="npm view "+a.getArtifact();
		Process process=null;
		InputStream is=null;
		
	    try {
	    	
	    	process=Runtime.getRuntime().exec(cmd);	
			is=process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    StringBuilder out = new StringBuilder();
		    String line;
		    String json="";
			while ((line = reader.readLine()) != null) {
			        out.append(line.trim());			       
			}
			json=json+out.toString();
			reader.close();
			
			if(json.equals("")){
				
				// In case the artifact specified is not found in the npm package registry, the output string will be empty
				
				result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is not present in Npm for" +server);
			    outcome=false;
			    
			}else {
				
				// If the artifact is found, npm view will provide us with all related information. In particular,
				// we will get the versions available for the artifact. By using some String processing, we are able
				// to determine whether the version under study is in the npm registry.
				
				// Disable comment if ouput has to be shown on screen (prints the string content read from the input stream)
				//System.out.println(json);  
				
				// Create the substring with the data to analyze 
				
		   	    String json2=(json.substring((json.indexOf("versions:"))+10,(json.indexOf("maintainers:"))-1));
		   	    
		   	    // Disable comment if ouput has to be shown on screen
		   	    //System.out.println(json2);
		   	    
		   	    if (json2.contains(a.getVersion())) {
		   	    	result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is present in Npm for " +server);
		   	    } else {
		   	    	result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is not present in Npm for " +server);
		   	        outcome=false;
		   	    }
		   	    
			}
		    
		    
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
		
		}
		return result;
		
	}
	
	
	public boolean output() {
		
		return outcome;
	}

}
