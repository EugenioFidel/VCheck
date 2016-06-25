    import java.io.IOException;
	import java.net.URI;
	import java.net.URISyntaxException;
	import java.util.*;
	
	import Model.*;

	import org.springframework.web.client.RestTemplate;
	import com.fasterxml.jackson.core.JsonParseException;
	import com.fasterxml.jackson.databind.JsonMappingException;
	import com.fasterxml.jackson.databind.ObjectMapper;
	
	/**
	 * 
	 * @author 
	 * Eugenio F. González (eugeniofidel@gmail.com)
	 * Javier Cabezas (jcabezasgivica@gmail.com)
	 * 
	 */
	
	public class VCheckerGitHub extends VChecker {
	
		/**
		 * Method addressed to query GitHub via REST services in order to get current tags of Kurento-java project
		 * @return	List with all the versions gathered from Github
		 */
		
		boolean outcome=true;
		
		public ArrayList<String> check (String server,String ip, List<Art> artifacts){
			
			ArrayList<String> result= new ArrayList<String>();
			ArrayList<String> tags = new ArrayList<String>();
			
			
			for (Art b : artifacts) {
			
			// Get the GitHub server repository from which we will check tags
			// Input will come in the form of "https://github.com/server", having therefore to take "server" part
			
			String servers=b.getArtifact();
			String srv=servers.substring(19,servers.length());
			//System.out.println(servers);
			//System.out.println(srv);
			
	        //Building the url to be used by the REST client to get the desired information
			
			tags.clear();
			String url="https://api.github.com/repos/"+srv+"/git/refs/tags/";
				
			try {
				
				String reference;
				URI uri=new URI(url);
				RestTemplate resttemplate=new RestTemplate();
				
				/** The following sentence does not work as per information on https://jira.spring.io/browse/SPR-8263
				List<Tag> mylist=resttemplate.getForObject(uri, List.class);
				**/
				
				String res=resttemplate.getForObject(uri, String.class);
				
				// Disable comment if the string gathered from the uri has to be shown in console
				// System.out.println(res);
				
				ObjectMapper mapper = new ObjectMapper();		
								
				try {
					
					List<Tag> myObjects = mapper.readValue(res, mapper.getTypeFactory().constructCollectionType(List.class, Tag.class));
					
				//############################################################################################################################
				// At this point myObjects contains a perfect mapping of the json file returned by the REST service (list of Tag objects) 
				//############################################################################################################################
					
				
					for (Tag t : myObjects ) {
						
						reference=t.getRef();
						String ref2=reference.substring(10,reference.length());
						// Disable comment if each tag has to be shown in console
						//System.out.println(ref2);
						tags.add(ref2);
					}	
					
														
						if(tags.contains(b.getVersion())) {
							result.add("The tag " + b.getVersion() + " is present in GitHub under " + srv );
						}
						else {
							result.add("The tag " + b.getVersion() + " is not present in GitHub under " + srv);
							outcome=false;
						}	
						
					
										
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			
			} catch (URISyntaxException e) {
				e.printStackTrace();			
			}		
		}
			
			return result;
		}
		
		public boolean output() {
			
			return outcome;
		}
		
		}
	