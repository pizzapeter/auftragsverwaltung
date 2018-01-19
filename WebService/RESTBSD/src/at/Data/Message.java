package at.Data;

public class Message {
	public static enum Severity {
		INFO, WARNING, ERROR
	}

	public Severity severity;
	public String message;
	public boolean technical;

	public Message(Severity severity, String message, boolean technical) {
		super();
		this.severity = severity;
		this.message = message;
		this.technical = technical;
	}

	public Message(Severity severity, String message) {
		this(severity, message, false);
	}

	public String toString() {
		return severity.name() + ": " + message;
	};
}
