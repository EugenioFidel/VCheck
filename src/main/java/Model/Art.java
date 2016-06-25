package Model;

public class Art {
	String artifact;
	String version;
	
	
	public Art(){}
	
	public Art(String artifact, String version) {
		this.artifact = artifact;
		this.version = version;
		
	}

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}

