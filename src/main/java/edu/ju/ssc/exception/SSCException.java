package edu.ju.ssc.exception;

public class SSCException extends Exception {

	/**
	 *  The serial version UID
	 */
	private static final long serialVersionUID = -3740952605109138393L;
	
	/**
	 * THe default constructor
	 */
	public SSCException() {

	}

	/**
	 * Constructs an Exception with the specified message
	 * 
	 * @param message
	 *            The Error Message
	 */
	public SSCException(final String message) {
		super(message);
	}

	/**
	 * Constructs an Exception with the specified message and Throwable Cause
	 * 
	 * @param message
	 *            The Error Message
	 * @param t
	 *            The throwable
	 */
	public SSCException(final String message, final Throwable t) {
		super(message, t);
	}

}
