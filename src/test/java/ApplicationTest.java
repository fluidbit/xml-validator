import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static junit.framework.Assert.assertEquals;

public class ApplicationTest {

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Test
	public void testHappyPath() {
		String xmlFilePath = getClass().getResource("sample-file.xml").getPath();
		String xsdFilePath = getClass().getResource("sample-schema.xsd").getPath();
		Application.main(new String[] {"--xml", xmlFilePath, "--xsd", xsdFilePath});

		assertEquals("[OK] Valid".trim(), systemOutRule.getLog().trim());
	}

	@Test
	public void testAppError40() {
		exit.expectSystemExitWithStatus(ApplicationError.XSD_PARAMETER_NOT_SPECIFIED.getCode());
		String xmlFilePath = getClass().getResource("sample-file.xml").getPath();
		Application.main(new String[] {"--xml", xmlFilePath});
	}

	@Test
	public void testAppError41() {
		exit.expectSystemExitWithStatus(ApplicationError.XML_PARAMETER_NOT_SPECIFIED.getCode());
		String xsdFilePath = getClass().getResource("sample-schema.xsd").getPath();
		Application.main(new String[] {"--xsd", xsdFilePath});
	}

	@Test
	public void testAppError50() {
		exit.expectSystemExitWithStatus(ApplicationError.XSD_FILE_NOT_FOUND.getCode());
		Application.main(new String[] {"--xml", "wrong-path.xml", "--xsd", "wrong-path.xsd"});
	}

	@Test
	public void testAppError51() {
		exit.expectSystemExitWithStatus(ApplicationError.XML_FILE_NOT_FOUND.getCode());
		String xsdFilePath = getClass().getResource("sample-schema.xsd").getPath();
		Application.main(new String[] {"--xml", "wrong-path.xml", "--xsd", xsdFilePath});
	}

	@Test
	public void testAppError52() {
		exit.expectSystemExitWithStatus(ApplicationError.INVALID_XSD_FILE.getCode());
		String xmlFilePath = getClass().getResource("sample-file.xml").getPath();
		String xsdFilePath = getClass().getResource("invalid-schema.xsd").getPath();
		Application.main(new String[] {"--xml", xmlFilePath, "--xsd", xsdFilePath});
	}

	@Test
	public void testAppError53() {
		exit.expectSystemExitWithStatus(ApplicationError.XML_VALIDATION_ERROR.getCode());
		String xmlFilePath = getClass().getResource("not-schema-compliant-file.xml").getPath();
		String xsdFilePath = getClass().getResource("sample-schema.xsd").getPath();
		Application.main(new String[] {"--xml", xmlFilePath, "--xsd", xsdFilePath});
	}
}
