package Model;

public class Tag {

	    String ref;
		String url;
		Obj object;
		
		public Tag(){}
		
		public Tag (String ref, String url, Obj object) {
			this.ref = ref;
			this.url = url;
			this.object = object;
		}

		public String getRef() {
			return ref;
		}

		public void setRef(String ref) {
			this.ref = ref;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Obj getObject () {
			return object;
		}
		
		public void setObject(Obj object) {
			this.object=object;
		}

		
	}


