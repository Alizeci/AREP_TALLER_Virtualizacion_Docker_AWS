package co.edu.escuelaing.sparkweb.dockerdemolab.model;

public class Document {
	
	String string;
	String date;
	
	public Document(String string, String date) {
		super();
		this.string = string;
		this.date = date;
	}
	
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
