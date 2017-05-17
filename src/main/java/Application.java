import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;
import pt.fluidbit.xml.exception.SAXWarningException;
import pt.fluidbit.xml.XmlSchema;

import java.io.File;
import java.io.IOException;

public class Application {

	private static final String PARAM_XSD = "--xsd";
	private static final String PARAM_XML = "--xml";
	private static String xsdFilePath;
	private static String xmlFilePath;
	private static XmlSchema schema;
	private static File xsdFile;
	private static File xmlFile;

	public static void main(String[] args) {
		parseInputParams(args);
		validateInputParams();

		try {
			schema = new XmlSchema(xsdFile);
		} catch (SAXException e) {
			System.exit(emitError(ApplicationError.INVALID_XSD_FILE));
		}

		try {
			schema.validate(xmlFile);
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
		if (StringUtils.isEmpty(xsdFilePath)) {
			System.exit(emitError(ApplicationError.XSD_PARAMETER_NOT_SPECIFIED));
		}
		if (StringUtils.isEmpty(xmlFilePath)) {
			System.exit(emitError(ApplicationError.XML_PARAMETER_NOT_SPECIFIED));
		}

		xsdFile = new File(xsdFilePath);
		if (!xsdFile.exists()) {
			System.exit(emitError(ApplicationError.XSD_FILE_NOT_FOUND));
		}

		xmlFile = new File(xmlFilePath);
		if (!xmlFile.exists()) {
			System.exit(emitError(ApplicationError.XML_FILE_NOT_FOUND));
		}
	}

	private static void parseInputParams(String[] args) {
		for (int argi = 0; argi < args.length; argi++) {
			if (args[argi].equalsIgnoreCase(PARAM_XSD) && (argi + 1) < args.length) {
				xsdFilePath = args[++argi];
				continue;
			}
			if (args[argi].equalsIgnoreCase(PARAM_XML) && (argi + 1) < args.length) {
				xmlFilePath = args[++argi];
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
