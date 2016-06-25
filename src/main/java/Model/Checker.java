package Model;

public class Checker {

	    ArtifactList bower;
		ArtifactList archiva;
		ArtifactList maven;
		ArtifactList npm;
        ArtifactList github;	
		
        public Checker(){} 
		
		public Checker (ArtifactList bower, ArtifactList archiva , ArtifactList maven,ArtifactList npm, ArtifactList github ) {
			this.bower=bower;
			this.archiva=archiva;
			this.maven=maven;
			this.npm=npm;
			this.github=github;
		}
				
		public ArtifactList getBower() {
			return bower;
		}

		public void setBower(ArtifactList bower) {
			this.bower = bower;
		}	

		public ArtifactList getArchiva() {
			return archiva;
		}

		public void setArchiva(ArtifactList archiva) {
			this.archiva = archiva;
		}

		public ArtifactList getMaven() {
			return maven;
		}

		public void setMaven(ArtifactList maven) {
			this.maven = maven;
		}

		public ArtifactList getNpm() {
			return npm;
		}

		public void setNpm(ArtifactList npm) {
			this.npm = npm;
		}

		public ArtifactList getGithub() {
			return github;
		}

		public void setGithub(ArtifactList github) {
			this.github = github;
		}
		
	}


