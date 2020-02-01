package pl.dentistoffice.exception;

public class AlreadyScheduledDateTimeVisitException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyScheduledDateTimeVisitException() {
		super();
	}

	public AlreadyScheduledDateTimeVisitException(String message) {
		super(message);
	}

	public AlreadyScheduledDateTimeVisitException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
