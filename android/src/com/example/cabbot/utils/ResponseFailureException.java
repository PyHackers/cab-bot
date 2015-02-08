/* $Id: $ */
package com.example.cabbot.utils;

import org.apache.http.StatusLine;

public class ResponseFailureException extends Exception 
{
	private static final long serialVersionUID = 1L;
	private int statusCode=404;

	public ResponseFailureException(StatusLine sLine)
	{
		this(sLine.getReasonPhrase(), sLine.getStatusCode());
	}
	public ResponseFailureException(String message) 
	{
		this(message, 404);
	}
	public ResponseFailureException(String message, int statusCode)
	{
		super(message);
		this.statusCode=statusCode;
	}
	public int getStatusCode()
	{
		return statusCode;
	}

}
