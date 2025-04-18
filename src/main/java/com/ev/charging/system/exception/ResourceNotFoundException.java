package com.ev.charging.system.exception;

public class ResourceNotFoundException extends RuntimeException {

	//Code Fix
	private final String resourceName;

	public ResourceNotFoundException(String resourceName) {
		super(String.format("%s not found", resourceName));
		this.resourceName = resourceName;

	}

	// Getters and setters
	public String getResourceName() {
		return resourceName;
	}

}
