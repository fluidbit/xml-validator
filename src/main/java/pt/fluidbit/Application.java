package pt.fluidbit;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;
import pt.fluidbit.xml.XmlSchema;
import pt.fluidbit.xml.exception.SAXWarningException;

import java.io.File;
import java.io.IOException;

public class Application {

	private static class ApplicationState {
		public String xsdFilePath;
		public String xmlFilePath;
		public XmlSchema schema;
		public File xsdFile;
		public File xmlFile;
	}

	private static final String PARAM_XSD = "--xsd";
	private static final String PARAM_XML = "--xml";
	private static ApplicationState state;

	public synchronized static void main(String[] args) {
		state = new ApplicationState();
		parseInputParams(args);
		validateInputParams();

		try {
			state.schema = new XmlSchema(state.xsdFile);
		} catch (SAXException e) {
			System.exit(emitError(ApplicationError.INVALID_XSD_FILE));
		}

		try {
			state.schema.validate(state.xmlFile);
		} catch (SAXWarningException e) {
			emitError(ApplicationError.XML_VALIDATION_WARNING, e);
		} catch (IOException e) {
			System.exit(emitError(ApplicationError.XML_FILE_PARSING_ERROR, e));
		} catch (SAXException e) {
			System.exit(emitError(ApplicationError.XML_VALIDATION_ERROR, e));
		}

		System.out.println("[OK] Valid");
	}

	private static void validateInputParams() {
		if (StringUtils.isEmpty(state.xsdFilePath)) {
			System.exit(emitError(ApplicationError.XSD_PARAMETER_NOT_SPECIFIED));
		}
		if (StringUtils.isEmpty(state.xmlFilePath)) {
			System.exit(emitError(ApplicationError.XML_PARAMETER_NOT_SPECIFIED));
		}

		state.xsdFile = new File(state.xsdFilePath);
		if (!state.xsdFile.exists()) {
			System.exit(emitError(ApplicationError.XSD_FILE_NOT_FOUND));
		}

		state.xmlFile = new File(state.xmlFilePath);
		if (!state.xmlFile.exists()) {
			System.exit(emitError(ApplicationError.XML_FILE_NOT_FOUND));
		}
	}

	private static void parseInputParams(String[] args) {
		for (int argi = 0; argi < args.length; argi++) {
			if (args[argi].equalsIgnoreCase(PARAM_XSD) && (argi + 1) < args.length) {
				state.xsdFilePath = args[++argi];
				continue;
			}
			if (args[argi].equalsIgnoreCase(PARAM_XML) && (argi + 1) < args.length) {
				state.xmlFilePath = args[++argi];
			}
		}
	}

	private static int emitError(ApplicationError appError) {
		return emitError(appError, null);
	}

	private static int emitError(ApplicationError appError, Exception cause) {
		System.err.println(String.format("[%d] %s", appError.getCode(), appError.getMessage()));
		if (cause != null) {
			cause.printStackTrace();
		}
		return appError.getCode();
	}
}
