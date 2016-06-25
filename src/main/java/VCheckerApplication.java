import java.io.File;
import java.io.IOException;
import java.util.*;

import Model.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VCheckerApplication {
	
	
	public static void main(String[] args) {
		
		String file="./"+args[0];
		ArrayList<String> resultarchiva;
		ArrayList<String> resultmaven;
		ArrayList<String> resultnpm;
		ArrayList<String> resultgithub;
		ArrayList<String> resultbower;
		Boolean out=true;
						
		try {
						
			Checker prueba = new ObjectMapper().readValue(new File (file), Checker.class);
			HashMap<String,ArtifactList> result = new HashMap<String,ArtifactList>();
			
			if (prueba.getArchiva() !=null){
			  result.put("archiva", prueba.getArchiva());
			}
			if (prueba.getBower() !=null){
			   result.put("bower", prueba.getBower());
			}
			if (prueba.getMaven() !=null){
			   result.put("maven", prueba.getMaven());
			}
			if (prueba.getGithub() !=null){
			   result.put("github", prueba.getGithub());
			}   
			if (prueba.getNpm() !=null){
			   result.put("npm", prueba.getNpm());	
			}
			
			//HashMap<String,String> result = new ObjectMapper().readValue(new File (file), HashMap.class);
			//Map<String, MyPojo> typedMap = mapper.readValue(jsonStream, new TypeReference<Map<String, MyPojo>>() {});
			//Map<String, ArtifactList> typedMap = mapper.readValue(new File(file),new TypeReference<Map<String, ArtifactList>>() {});
				
			Iterator it = result.entrySet().iterator();
			
			while (it.hasNext()) {
				
				Map.Entry e = (Map.Entry)it.next();
				ArtifactList l=(ArtifactList)e.getValue();
				String val=(String)e.getKey();
				Types value =Types.valueOf(val);
				
				// The map containing the data related to the server and IP has a single entry
				
				HashMap<String,String> data=l.getMetadata();
				Map.Entry d= (Map.Entry)data.entrySet().iterator().next();
				String server=(String)d.getKey();
				String ip=(String)d.getValue();
				
				
				// Switch structure using Strings is only available from Java 1.7 on.
				// We will use enumeration types for this.
				
				switch (value) {
				
				case archiva:
					
					System.out.println("");
					System.out.println("*****************************************");
					System.out.println("Checking artifact versions in Archiva...");
					System.out.println("*****************************************");
					System.out.println("");
					VCheckerArchiva arch= new VCheckerArchiva();
					resultarchiva=arch.check(server,ip,l.getArtifacts());
					
					System.out.println("");
					System.out.println("**************************************************************************************************************");
					for (String a : resultarchiva) {
						System.out.println(a);
					}
					System.out.println("**************************************************************************************************************");
					
					// Checking the global result of the different artifact checks
					
					if (!arch.output()) {
						out=false;
					}
				    break;
				    
				case maven: 
					
					System.out.println("");
					System.out.println("*****************************************");
					System.out.println("Checking artifact versions in Maven...");
					System.out.println("*****************************************");
					System.out.println("");
					VCheckerMaven maven= new VCheckerMaven();
					resultmaven=maven.check(server,ip,l.getArtifacts());
					
					System.out.println("");
					System.out.println("**************************************************************************************************************");
					for (String a : resultmaven) {
						System.out.println(a);
					}
					System.out.println("**************************************************************************************************************");
					
					// Checking the global result of the different artifact checks

					if (!maven.output()) {
						out=false;
					}
									
					break;
					
				case npm:
					
					System.out.println("");
					System.out.println("*****************************************");
					System.out.println("Checking artifact versions in NPM...");
					System.out.println("*****************************************");
					System.out.println("");	
					
					VCheckerNpm npm=new VCheckerNpm();
					resultnpm=npm.check(server,ip,l.getArtifacts());
					
					System.out.println("");
					System.out.println("**************************************************************************************************************");
					for (String a : resultnpm) {
						System.out.println(a);
					}
					System.out.println("**************************************************************************************************************");
					
					// Checking the global result of the different artifact checks
					
					if (!npm.output()) {
						out=false;
					}
					
				    break;
				    
				case bower:
					
					System.out.println("");
					System.out.println("*****************************************");
					System.out.println("Checking artifact versions in Bower...");
					System.out.println("*****************************************");
					System.out.println("");
					
					VCheckerBower bow=new VCheckerBower();
					resultbower=bow.check(server,ip,l.getArtifacts());
					
					System.out.println("");
					System.out.println("**************************************************************************************************************");
					for (String a : resultbower) {
						System.out.println(a);
					}
					System.out.println("**************************************************************************************************************");
					
					// Checking the global result of the different artifact checks
					
					if (!bow.output()) {
						out=false;
					}
					
					break;
					
				case github:
							
					System.out.println("");
					System.out.println("*****************************************");
					System.out.println("Checking tags in Github...");
					System.out.println("*****************************************");
					System.out.println("");
					
					VCheckerGitHub git= new VCheckerGitHub();
					resultgithub=git.check(server,ip,l.getArtifacts());
					
					System.out.println("");
					System.out.println("**************************************************************************************************************");
					for (String a : resultgithub) {
						System.out.println(a);
					}
					System.out.println("**************************************************************************************************************");
					
					// Checking the global result of the different artifact checks
					
					if (!git.output()) {
						out=false;
					}
										
					break;
				}
				
			}
					
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// If "out=true", that means that all the artifacts listed in the config.json were found
		// In case one ( or more than one) artifact is not found, then we exit with code "1", meaning that an error was found
		
		
		if (out) {
			
			System.exit(0);
			
			
		}else {
			
			System.exit(1);
			
		}
		
		}
	
    private enum Types {
		
		archiva,bower,npm,github,maven;
	}
    
	}
    
		
	


