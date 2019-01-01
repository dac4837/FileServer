package com.collins.fileserver.storage;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 2489731173559440985L;
	
	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
