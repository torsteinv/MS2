package no.torsteinv.MS2.Main.Miscellaneous;

public class Message {
	public String message = "LOL";
	public int viewed = 255;

	public Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getViewed() {
		return viewed;
	}

	public void setViewed(int viewed) {
		this.viewed = viewed;
	}
}
