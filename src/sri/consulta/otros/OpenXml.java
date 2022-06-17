package sri.consulta.otros;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class OpenXml {



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Detalle> lista = new ArrayList<Detalle>();
		ReadFile("","",lista);
	}

	public static void ReadFile(String Directory,String Xmlpath,List<Detalle> name) {
		String comprobante = "";
		try {
			File fXmlFile = new File(Directory + Xmlpath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Xml File :" + Xmlpath);
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("autorizacion");
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("fecha Autorizacion : "
							+ eElement.getElementsByTagName("fechaAutorizacion").item(0).getTextContent());

					comprobante = eElement.getElementsByTagName("comprobante").item(0).getTextContent();
				}
			}
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource inputSource = new InputSource(new StringReader(comprobante));
			Document document = db.parse(inputSource);
			// Create XPathFactory object
			XPathFactory xpathFactory = XPathFactory.newInstance();

			// Create XPath object
			XPath xpath = xpathFactory.newXPath();
			getEmployeeNameById(document, xpath,name);
			System.out.println("Employee Name with ID 4: " + Arrays.toString(name.toArray()));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.out.println(e.toString());
		}
	}

	private static void getEmployeeNameById(Document doc, XPath xpath,List<Detalle> list) {
		try {
			XPathExpression expr = xpath.compile("/factura/infoTributaria/secuencial/text()");
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			String numFactura = nodes.item(0).getNodeValue();
			expr = xpath.compile("/factura/infoFactura/fechaEmision/text()");	
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			String fechaEmision = nodes.item(0).getNodeValue();
			expr = xpath.compile("/factura/infoFactura/importeTotal/text()");	
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			String importeTotal = nodes.item(0).getNodeValue();
			 expr = xpath.compile("/factura/detalles/detalle");
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Detalle det = new Detalle();
				det.numFactura = numFactura;
				det.fechaEmision = fechaEmision;
				det.importeTotal = String.valueOf(round(Double.valueOf(importeTotal),2));
				det.codigoPrincipal = (nodes.item(i).getChildNodes().item(1).getTextContent());
				det.codigoAuxiliar = (nodes.item(i).getChildNodes().item(3).getTextContent());
				det.descripcion = (nodes.item(i).getChildNodes().item(5).getTextContent());
				det.cantidad = (nodes.item(i).getChildNodes().item(7).getTextContent());
				det.precioUnitario = String.valueOf(round(Double.valueOf((nodes.item(i).getChildNodes().item(9).getTextContent())),2));
				det.precioTotalSinImp = (nodes.item(i).getChildNodes().item(13).getTextContent());
				list.add(det);
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

}