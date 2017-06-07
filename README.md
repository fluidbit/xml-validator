# XML Validator
Validates a XML file against a XSD file using XML Schema 1.1 via Xerces Beta Lib (2.12-beta)

## Usage
```
java -jar xml-validator.jar --xml myxmlfile.xml --xsd myxmlfile_v1.xsd
```

## Exit Codes

|exit code|description|
|---------|-----------|
|0|Valid XML file|
|40|XSD_PARAMETER_NOT_SPECIFIED|
|41|XML_PARAMETER_NOT_SPECIFIED|
|50|XSD_FILE_NOT_FOUND|
|51|XML_FILE_NOT_FOUND|
|52|INVALID_XSD_FILE|
|53|XML_VALIDATION_ERROR|
|54|XML_VALIDATION_WARNING|
|55|XML_FILE_PARSING_ERROR|


