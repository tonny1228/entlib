package org.llama.library.database;

public class DAOException extends java.lang.RuntimeException {
	public DAOException() {
	}

	public DAOException(Exception e) {
		super(e);
	}

	public DAOException(String e) {
		super(e);
	}
}
