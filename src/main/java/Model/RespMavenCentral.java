package Model;

public class RespMavenCentral {
	ResponseHeader responseHeader;
	Response response;
	
	RespMavenCentral(){}

	public RespMavenCentral(Model.ResponseHeader responseHeader,
			Model.Response response) {
		super();
		this.responseHeader = responseHeader;
		this.response = response;
	}

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	
}
