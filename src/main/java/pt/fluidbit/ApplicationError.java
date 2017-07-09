package pt.fluidbit;

public enum ApplicationError {
	XSD_PARAMETER_NOT_SPECIFIED(40, "The required parameter --xsd must be specified"),
	XML_PARAMETER_NOT_SPECIFIED(41, "The required parameter --xml must be specified"),

	XSD_FILE_NOT_FOUND(50, "The given XSD file was not found"),
	XML_FILE_NOT_FOUND(51, "The given XML file was not found"),
	INVALID_XSD_FILE(52, "Invalid XSD file"),
	XML_VALIDATION_ERROR(53, "XML file validation error"),
	XML_VALIDATION_WARNING(54, "XML file validation warning"),
	XML_FILE_PARSING_ERROR(55, "An error occurred while parsing the XML file");

	private int code;
	private String message;

	ApplicationError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode(){
		return code;
	}

	public String getMessage(){
		return message;
	}
}
