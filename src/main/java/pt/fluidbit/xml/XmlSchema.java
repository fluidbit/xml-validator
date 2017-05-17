package pt.fluidbit.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import pt.fluidbit.xml.exception.SAXWarningException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlSchema {

	private class XmlSchemaErrorHandler implements ErrorHandler {
		@Override
		public void warning(SAXParseException exception) throws SAXException {
			throw new SAXWarningException(exception);
		}

		@Override
		public void error(SAXParseException exception) throws SAXException {
			throw exception;
		}

		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			throw exception;
		}
	}

	public static final String XML_SCHEMA_VERSION = "http://www.w3.org/XML/XMLSchema/v1.1";
	private final SchemaFactory schemaFactory;
	private final Schema schema;

	public XmlSchema(File xsdFile) throws SAXException {
		schemaFactory = SchemaFactory.newInstance(XML_SCHEMA_VERSION);
		schema = schemaFactory.newSchema(xsdFile);
	}

	public boolean validate(File xmlFile) throws IOException, SAXException {
		Validator validator = schema.newValidator();
		validator.setErrorHandler(new XmlSchemaErrorHandler());
		validator.validate(new StreamSource(xmlFile));
		return true;
	}
}
