package CodeGeneration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import coreTypes.CompositeType;
import coreTypes.EnumType;
import coreTypes.FieldType;
import coreTypes.ListType;

import coreOperations.*;

public class JavaCodeGenerator {
	
	public static void main(String args[]) {

		if (args.length < 2) {
			System.out.println("Incorrect Argument \n-@Arg1: xml file \n-@Arg2: output directory");
		}
		String fileName = args[0];
		String outputDir = args[1];
		if (!(fileName.endsWith("xml") || fileName.endsWith("XML"))) {
			System.out.println("File extension is incorrect !! Use xml or XML ");
		}
		try {

			FileInputStream fis = new FileInputStream(new File(fileName));
			BufferedInputStream is = new BufferedInputStream(fis);

			// Create the DOM Parser
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final Document document = factory.newDocumentBuilder().parse(is);
			final XPath xPath = XPathFactory.newInstance().newXPath();

			// parse different tags in XML
			Map<String, CompositeType> compositeTypeMap = populateCompositeType(document, xPath);
			Map<String, EnumType> enumTypeMap = populateEnumMap(document, xPath);
			Map<String, CompositeType> messageMap = populateMessageMap(document, xPath);
			
			//Generate the Static Type utility class for Enum and  Inner Class
			StaticUtilGenerator sUtilGen = new StaticUtilGenerator(outputDir);
			sUtilGen.utilGenerator(compositeTypeMap, enumTypeMap);
			
			//Generate Java objects

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	

	private static Map<String, CompositeType> populateCompositeType(Document document, XPath xPath) throws XPathExpressionException {
		
		LinkedHashMap<String, CompositeType> map = new LinkedHashMap<>();

		forEach((NodeList) xPath.compile(CodeGenerationsConstants.COMPOSITE_XPATH_EXPR).evaluate(document,
				XPathConstants.NODESET), (node) -> addCompositeType(map, node));

		return map;
	}

	private static Map<String, CompositeType> populateMessageMap(Document document, XPath xPath)
			throws XPathExpressionException {
		LinkedHashMap<String, CompositeType> map = new LinkedHashMap<>();

		forEach((NodeList) xPath.compile(CodeGenerationsConstants.MESSAGE_XPATH_EXPR).evaluate(document,
				XPathConstants.NODESET), (node) -> addMessages(map, node));

		return map;
	}

	private static void addMessages(LinkedHashMap<String, CompositeType> map, Node node) {
		CompositeType compType = new CompositeType();
		compType.setName(node.getAttributes().getNamedItem("name").getNodeValue());
		Map<String, FieldType> memberMetaData = compType.getFields();
		
		//Add members of composite type
		addFileds(memberMetaData,node);
		
		map.put(compType.getName(), compType);
	}



	private static Map<String, EnumType> populateEnumMap(Document document, XPath xPath)
			throws XPathExpressionException {
		LinkedHashMap<String, EnumType> map = new LinkedHashMap<>();
		forEach((NodeList) xPath.compile(CodeGenerationsConstants.ENUM_XPATH_EXPR).evaluate(document,
				XPathConstants.NODESET), (node) -> addEnumType(map, node));

		return map;
	}

	private static Object addFileds(Map<String, FieldType> map, Node node) {
		
		NodeList member = node.getChildNodes();
		for (int i = 0; i < member.getLength(); i++) {
			Node child = member.item(i);
			if (child.hasAttributes()) {
				String name = child.getAttributes().getNamedItem("name").getNodeValue();
				String type = child.getAttributes().getNamedItem("type").getNodeValue();
				if(child.getNodeName() == "field"){
					map.put(name, new FieldType(name,type));	
				}else if(child.getNodeName() == "list"){
					int length = Integer.parseInt(child.getAttributes().getNamedItem("length").getNodeValue());
					map.put(name, new ListType(name, type, length));
				}
				
			}
		}
		
		return map;
	}

	private static void addEnumType(LinkedHashMap<String, EnumType> map, Node node) {
		NodeList member = node.getChildNodes();

		EnumType enumType = new EnumType(node.getAttributes().getNamedItem("name").getNodeValue());
		Map<String, String> values = enumType.getValues();

		for (int i = 0; i < member.getLength(); i++) {
			Node child = member.item(i);
			if (child.hasAttributes()) {
				String name = child.getAttributes().getNamedItem("name").getNodeValue();
				String value = child.getTextContent();
				values.put(name, value);
			}
		}
		//Add enum type object to Map
		
		map.put(enumType.getName(), enumType);
	}

	private static void addCompositeType(LinkedHashMap<String, CompositeType> map, Node node) {

		CompositeType compType = new CompositeType();
		compType.setName(node.getAttributes().getNamedItem("name").getNodeValue());
		Map<String, FieldType> memberMetaData = compType.getFields();
		
		//Add members of composite type
		addFileds(memberMetaData,node);
		
		//Add composite type to the Map 
		map.put(compType.getName(), compType);
	}

	private static void forEach(final NodeList nodeList, final NodeFunction func) throws XPathExpressionException {
		for (int i = 0, size = nodeList.getLength(); i < size; i++) {
			func.execute(nodeList.item(i));
		}
	}
}
