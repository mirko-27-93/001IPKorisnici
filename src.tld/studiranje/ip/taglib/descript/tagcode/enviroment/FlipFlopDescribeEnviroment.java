package studiranje.ip.taglib.descript.tagcode.enviroment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 * Окружење, валидација и баратање са садржајем тага описа. 
 * @author Masinacc
 * @version 1.0
 */
public class FlipFlopDescribeEnviroment implements Serializable{
	private static final long serialVersionUID = -3866927407328657356L;
	private String bodyContent; 
	private URI schemaLocation;
	
	public String getBodyContent() {
		return bodyContent;
	}
	public void setBodyContent(String bodyContent) {
		if(bodyContent==null) bodyContent = ""; 
		this.bodyContent = bodyContent;
	}
	public URI getSchemaLocation() {
		return schemaLocation;
	}
	public void setSchemaLocation(URI schemaLocation) {
		this.schemaLocation = schemaLocation;
	} 
	public boolean isSchemaRestricted() {
		return schemaLocation != null; 
	}
	
	
	public FlipFlopDescribeEnviroment() {
		setBodyContent("");
	}
	public FlipFlopDescribeEnviroment(String bodyContent) {
		try {
			setBodyContent(bodyContent);
			setSchemaLocation(getClass().getResource("/studiranje/ip/taglib/descript/tagcode/schema/describe.xsd").toURI());
		}catch(Exception ex) {
			throw new RuntimeException("XSD SCHEMA NOT FOUND.");
		}
	}
	public FlipFlopDescribeEnviroment(String bodyContent, URI xmlScheme) {
		setBodyContent(bodyContent);
		setSchemaLocation(xmlScheme);
	}
	
	public boolean exceptioningValidate() throws ValidityException, ParsingException, IOException, SAXException {
		Builder builder = new Builder();
		Document document = builder.build(new ByteArrayInputStream(bodyContent.getBytes()));
		Source xmlCode = new StreamSource(new ByteArrayInputStream(document.toXML().getBytes())); 
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaLocation.toURL());
		Validator validator = schema.newValidator(); 
		validator.validate(xmlCode);
		return true;
	}
	public boolean generalValidate() throws IOException, SAXException, ValidityException, ParsingException {
		Builder builder = new Builder();
		Document document = builder.build(new ByteArrayInputStream(bodyContent.getBytes()));
		Source xmlCode = new StreamSource(new ByteArrayInputStream(document.toXML().getBytes())); 
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(schemaLocation.toURL());
		Validator validator = schema.newValidator(); 
		try {validator.validate(xmlCode);}catch(SAXException ex) {return false;}
		return true; 
	}
	public boolean strictValidate() throws IOException, SAXException, ValidityException, ParsingException {
		if(schemaLocation!=null) return generalValidate();
		return false; 
	}
	public boolean flexibleValidate() throws IOException, SAXException, ValidityException, ParsingException {
		if(schemaLocation!=null) return generalValidate();
		return true; 
	}
}
