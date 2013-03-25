package com.irongroup.unit;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParse implements XmlDocument {
	private Document document;
	private String fileName;

	public DomParse() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.document = builder.newDocument();

		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		}
	}

	public void createXml(String fileName) {

		Element root = this.document.createElement("campany");
		root.setAttribute("name", "maymay");
		this.document.appendChild(root);

		Element department = this.document.createElement("department");
		root.appendChild(department);

		Element name = this.document.createElement("name");
		name.appendChild(this.document.createTextNode("Human Resource"));

		department.appendChild(name);

		Element employees = this.document.createElement("employees");
		department.appendChild(employees);

		Element staff1 = this.document.createElement("staff");
		Element staff2 = this.document.createElement("staff");
		Element staff3 = this.document.createElement("staff");

		staff1.appendChild(this.document.createTextNode("Ying Liu"));
		staff2.appendChild(this.document.createTextNode("Hongwei Dai"));
		staff3.appendChild(this.document.createTextNode("Fang Liu"));

		employees.appendChild(staff1);
		employees.appendChild(staff2);
		employees.appendChild(staff3);

		TransformerFactory tf = TransformerFactory.newInstance();

		try {
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);

		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}
	}

	public Map<String, String> parserXml(InputStream is) {
		Map<String, String> map =new HashMap<String, String>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(is);
			// Document document = db.parse(fileName);
			NodeList campanys = document.getChildNodes();

			for (int i = 0; i < campanys.getLength(); i++) {
				Node departments = campanys.item(i);

				NodeList department = departments.getChildNodes();

				for (int j = 0; j < department.getLength(); j++) {
					Node node = department.item(j);
					System.out.println(node.getNodeName());
					System.out.println(node.getTextContent());
					map.put(node.getNodeName(), node.getTextContent());
				}
			}
		
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return map;
	}

	@Override
	public void parserXml(String fileName) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		String xmlString = "<xml><ToUserName><![CDATA[gh_f1f5840e9def]]></ToUserName>"
				+ "<FromUserName><![CDATA[oREeBjgMGfmIwIdxlgxaOCBQZ3fU]]></FromUserName>"
				+ "<CreateTime>1362670119</CreateTime>"
				+ "<MsgType><![CDATA[text]]></MsgType>"
				+ "<Content><![CDATA[test]]></Content>"
				+ "<MsgId>5852623596441428236</MsgId>" + "</xml>";
		xmlString="<?xml version=\"1.0\" encoding=\"utf-8\" ?>"+xmlString;
		InputStream is=new ByteArrayInputStream(xmlString.getBytes());
		DomParse parse=new DomParse();
		parse.parserXml(is);
	}
}
