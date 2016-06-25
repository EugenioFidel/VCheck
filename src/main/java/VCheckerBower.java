import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import Model.*;


public class VCheckerBower extends VChecker{
   
	private boolean outcome=true;
	
	public ArrayList<String> check(String server, String ip, List<Art> artifacts) {
        
		ArrayList<String> result=new ArrayList<String>();
		
		for (Art a : artifacts) {
		
		String cmd="bower info "+a.getArtifact()+"#"+a.getVersion();
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
			        out.append(line);			       
			}
			json=json+out.toString();
			reader.close();
			// Disable comment if command output has to be shown on screen
			// System.out.println(json); 
		    
			if (json.equals("")){
			    
				// This covers those cases in which the artifact specified is incorrect (Bower returns an empty output)
				
				   result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is not present in Bower for " +server);
			       outcome=false;
			
			} else {
				
				if(!(json.contains("{") & json.contains("}"))){
					
					// This covers those cases in which the artifact is correct but the version specified isn't (Bower's output is not empty)	
				
					result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is not present in Bower for " +server);
					outcome=false;
			    }
				
				else {
					  
					// If we hit this point, then both the artifact and the versions are correct from Bower's perspective
					// The String gathered from the input will contain both "{" and "}", meaning that a Json file has been received
					// Bower does not return an empty Json in case nothing is found (that is, no "{" "}" symbols)
					
					result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is present in Bower for " +server);
		    		
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
