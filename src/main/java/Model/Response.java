package Model;

import java.util.List;

public class Response {
	Integer numFound;
	Integer start;
	List<Doc>docs;
	
	Response(){}

	public Response(Integer numFound, Integer start, List<Doc> docs) {
		super();
		this.numFound = numFound;
		this.start = start;
		this.docs = docs;
	}

	public Integer getNumFound() {
		return numFound;
	}

	public void setNumFound(Integer numFound) {
		this.numFound = numFound;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}
	
	
	
}
