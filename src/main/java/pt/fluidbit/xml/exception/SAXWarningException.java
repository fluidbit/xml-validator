package pt.fluidbit.xml.exception;

import org.xml.sax.SAXException;

public class SAXWarningException extends SAXException {
	public SAXWarningException(SAXException e) {
		super(e);
	}
}
