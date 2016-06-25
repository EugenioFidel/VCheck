import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import org.springframework.web.client.RestTemplate;

import Model.*;


public class VCheckerMaven extends VChecker{
	
	private boolean outcome=true;
	
	public ArrayList<String> check(String server, String ip, List<Art> artifacts) {
		
		ArrayList<String> result=new ArrayList<String>();
		
		for (Art a : artifacts) {
		
		//Mimics searching by coordinate in Advanced Search.  This search 
				//uses all coordinates (“g” for groupId, “a” for artifactId, “v” for version, 
				//		“p” for packaging, “l” for classifier) and uses “AND” to require all 
				//		terms by default.  Only one term is required for the search to work.  
				//		Terms can also be connected by “OR” separated to make them 
				//		optional 
				//http://search.maven.org/solrsearch/select?q=g:”com.google.inject”%20AND%20a:”guice”%20AND%20v:”3.0”%20AND%20l:”javadoc”%20AND%20p:”jar”&rows=20&wt=json
						
				//building the url
		     	String path="http://search.maven.org/solrsearch/select?q=g:\""+ip+"\" AND a:\""+a.getArtifact()+"\" AND v:\""+a.getVersion()+"\" OR l:\"javadoc\" OR l:\"jar\"&rows=20&wt=json";
				
				try {			
					URL url=new URL(path);
					String nullFragment = null;			
					URI uri=new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(),nullFragment );
					RestTemplate resttemplate=new RestTemplate();
					RespMavenCentral res=resttemplate.getForObject(uri, RespMavenCentral.class);			
					
					//################################################################################################
					// At this point res contains a correct RespMavenCentral object returned by mavencentral repository
					//################################################################################################
					
					if(!(res.getResponse().getNumFound()==0)){
						//Checking if the version and the artifact are the same.
						Doc dc=res.getResponse().getDocs().get(0);
						String repo=dc.getA();
						String version=dc.getV();
						if (repo.equals(a.getArtifact()) && version.equals(a.getVersion())){
							result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is present in Maven under " +server);
						}
					}else{
						    result.add("The artifact " + a.getArtifact() + " with version " + a.getVersion() + " is not present in Maven under "+server);
						    outcome=false;
					}
									
				}catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}
		return result;
	}
	
	public boolean output() {
		
		return outcome;
	}
	
}
