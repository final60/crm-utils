package crm.utils;

import static java.lang.String.format;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Utils methods related to String.
 * 
 * @author Chris Mepham
 */
public abstract class StringUtils {

	/**
	 * Returns true if the input String is not null and contains content.
	 * 
	 * @param input The String.
	 * 
	 * @return True if the input String is not null and has content.
	 */
	public static final boolean hasText(String input) {
		
		return (input != null && input.trim().length() > 0);
	}
	
	/**
	 * Formats the input XML String.
	 * 
	 * 
	 * @param input The XML String that requires 'Prettifying'.
	 * @param indentation The number of spaces to indent child nodes.
	 * 
	 * @return The XML-formatted String.
	 * 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws ParserConfigurationException 
	 */
	public static final String prettyPrintXml(String input, int indentation) throws SAXException, 
																				    IOException, 
																				    TransformerFactoryConfigurationError, 
																				    TransformerException, 
																				    ParserConfigurationException {
		Document doc = parseDocument(input);
		
		Transformer transformer = getTransformer(indentation);
		
		StreamResult result = new StreamResult(new StringWriter());
		
		DOMSource source = new DOMSource(doc);
		
		transformer.transform(source, result);
		
		String xmlString = result.getWriter().toString();
		
		return xmlString;
	}

	private static Transformer getTransformer(int indentation) throws TransformerConfigurationException, 
																	  TransformerFactoryConfigurationError, 
																	  IllegalArgumentException {
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indentation));
		return transformer;
	}

	private static Document parseDocument(String input) throws ParserConfigurationException, 
															   SAXException, 
															   IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(input));
		Document doc = builder.parse(is);
		return doc;
	}
	
	/**
	 * Formats the input Json String.
	 * 
	 * @param input The Json String that requires 'prettifying'.
	 * @param indentation The number of spaces to indent each child.
	 * 
	 * @return The Json-formatted String.
	 * 
	 * @throws ScriptException
	 */
	public static final String prettyPrintJson(String input, int indentation) throws ScriptException {
		
		ScriptEngine scriptEngine = getScriptEngine();
		
		String prettyPrintedJson = evaluateString(input, indentation, scriptEngine);
		
		return prettyPrintedJson;
	}

	private static final String evaluateString(String input, int indentation, ScriptEngine scriptEngine) throws ScriptException {
		scriptEngine.put("jsonString", input.trim());
		scriptEngine.eval(format("result = JSON.stringify(JSON.parse(jsonString), null, %s)", indentation));
		String prettyPrintedJson = (String) scriptEngine.get("result");
		return prettyPrintedJson;
	}

	private static final ScriptEngine getScriptEngine() {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine scriptEngine = manager.getEngineByName("JavaScript");
		return scriptEngine;
	}
}
