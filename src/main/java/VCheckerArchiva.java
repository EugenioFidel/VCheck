import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.web.client.RestTemplate;

import Model.*;
import java.util.*;



public class VCheckerArchiva extends VChecker{
	
	private boolean outcome=true;
	
	public ArrayList<String> check(String server,String ip, List<Art> artifacts) {
		
		
		ArrayList<String> result=new ArrayList<String>();
		boolean control;
		
		for (Art a : artifacts) {
			
		control=false;
		
		//Building the url
					
		String url="http://"+ip+":8080/restServices/archivaServices/browseService/versionsList/"+server+"/"+a.getArtifact();
		
		try {
			URI uri=new URI(url);
			RestTemplate resttemplate=new RestTemplate();
			RespArchiva res=resttemplate.getForObject(uri, RespArchiva.class);
			
			//################################################################################################
			// At this point res contains a correct RespArchiva object returned by Archiva repository
			//################################################################################################
			
			// The object "res" , according to the model, contains an attribute "versions" which is a list of Strings
			// containing the versions for the artifact under study.
			// The idea would be to walk through the list and check whether the version under study is present in the list
			
			Iterator<String> it=res.getVersions().iterator();
			while(it.hasNext()){
				
				// We create an array of Strings from each String belonging to the list, this time using "-" as separator
				
				String[]structure=it.next().split("-");
				if(structure[0].equals(a.getVersion())){
					result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is present in Archiva for " + server);
					control=true;
				}
			}
			
		} catch (URISyntaxException e) {
			
			System.out.println("");
			System.out.println("********************************************************");
			System.out.println("URI is not built correctly and/or has illegal parameters");
			System.out.println("********************************************************");
			e.printStackTrace();	
			
			
		}
		// If, after checking the whole list, the version under study is not found, then control variable will have a false value.
		// Additionally, we will have to change the outcome value to false since the global result is not satisfactory, regardless
		// we have more artifact versions to check in the "for" loop.
		
		if (!control) {
		    result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is not present in Archiva for " +server);
		    outcome=false;
		}
		
	}
	return result;
	
   }
	
    public boolean output() {
		
		return outcome;
	}

}
