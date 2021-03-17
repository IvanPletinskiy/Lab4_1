package com.handen.plugin;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

class Main {
    public static void main(String[] args) {
        String mode = args[0];
        String input = args[1];
        //        String input = "<EmployeesList><employees><employees _type=\"MobileDeveloper\"><id>0</id><name>Ivan</name><surname>Pletinskiy</surname><salary>100500</salary><positionTitle>Mobile Developer</positionTitle><mentorId>0</mentorId><mentor/></employees></employees></EmployeesList>";
        //        String input = "<EmployeesList><employees><employees _type=\"MobileDeveloper\" id=\"0\" name=\"Ivan\" positionTitle=\"Mobile Developer\" salary=\"100500\" surname=\"Pletinskiy\"><mentorId>0</mentorId><mentor/></employees></employees></EmployeesList>";

        String output = "";

        if(mode.equals("encode")) {
            String formattedInput = "<?xml version = \"1.0\"?>\n" + input;
            output = replaceTagsWithAttributes(formattedInput);
        }
        else {
            String formattedInput = "<?xml version = \"1.0\"?>\n" + input;
            output = replaceAttributesWithTags(formattedInput);
        }

        System.out.print(output);
    }

    private static String replaceTagsWithAttributes(String input) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(input)));
            doc.getDocumentElement();
            NodeList employees = doc.getDocumentElement().getChildNodes().item(0).getChildNodes();
            for(int i = 0; i < employees.getLength(); i++) {
                Element employee = (Element) employees.item(i);
                NodeList innerTags = employee.getChildNodes();

                for(int j = 0; j < innerTags.getLength(); j++) {
                    Node innerTag = innerTags.item(j);
                    if(innerTag.getNodeName().contains("mentor")) {
                        continue;
                    }
                    String attrName = innerTags.item(j).getNodeName();
                    String attrValue = innerTags.item(j).getFirstChild().getNodeValue();
                    employee.setAttribute(attrName, attrValue);
                    employee.removeChild(innerTag);
                    j--;
                }
            }

            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.transform(domSource, result);
            return writer.toString();
        }
        catch(ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String replaceAttributesWithTags(String input) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(input)));
            doc.getDocumentElement();
            NodeList employees = doc.getDocumentElement().getChildNodes().item(0).getChildNodes();
            for(int i = 0; i < employees.getLength(); i++) {
                Element employee = (Element) employees.item(i);
                NamedNodeMap attributes = employee.getAttributes();
                for(int j = 0; j < attributes.getLength(); j++) {
                    String attrName = attributes.item(j).getNodeName();
                    if(attrName.contains("type")) {
                        continue;
                    }
                    String attrValue = attributes.item(j).getNodeValue();
                    Node tag = doc.createElement(attrName);
                    Node value = doc.createTextNode(attrValue);
                    tag.appendChild(value);
                    employee.appendChild(tag);
                    employee.removeAttribute(attrName);
                    j--;
                }
            }

            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.transform(domSource, result);
            return writer.toString();
        }
        catch(ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
        return "";
    }
}