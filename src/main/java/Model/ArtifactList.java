package Model;

import java.util.*;

	public class ArtifactList {
		
		List<Art> artifacts;
		HashMap<String,String> metadata;
		
		ArtifactList(){}
	
		public HashMap<String, String> getMetadata() {
			return metadata;
		}

		public void setMetadata(HashMap<String, String> metadata) {
			this.metadata = metadata;
		}

		public ArtifactList(List<Art> artifacts) {
			this.artifacts = artifacts;
		}

		public List<Art> getArtifacts() {
			return artifacts;
		}

		public void setArtifacts(List<Art> artifacts) {
			this.artifacts = artifacts;
		}	
	}

