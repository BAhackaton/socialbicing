package com.mobilenik.socialBicing.ws;

import org.w3c.dom.Document;

import com.mobilenik.core.ws.IMKResult;
import com.mobilenik.core.xml.ParseBodyException;
import com.mobilenik.core.xml.ParseDocumentException;
import com.mobilenik.core.xml.ParseHeaderException;

/**
 * 
 */
public abstract class MKResult implements IMKResult
{
    public MKResultHeader header;
    
    public void parse(Document doc) throws ParseDocumentException, ParseHeaderException, ParseBodyException
    {
    	parseHeader(doc);
    	parseBody(doc);
    }

	protected abstract void parseHeader(Document doc) throws ParseHeaderException;
	protected abstract void parseBody(Document doc) throws ParseBodyException;
} 
