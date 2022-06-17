package sri.factura.envio;

import java.io.File;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ec.gob.sri.comprobantes.ws.RecepcionComprobantesOffline;
import ec.gob.sri.comprobantes.ws.RecepcionComprobantesOfflineService;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOffline;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesOfflineService;
import ec.gob.sri.comprobantes.ws.aut.Mensaje;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import ec.gob.sri.comprobantes.ws.aut.RespuestaLote;

public class AutorizacionConsulta {
	public static void main(String[] args) throws Exception {
		AutorizacionComprobantesOfflineService autorizacion = new AutorizacionComprobantesOfflineService();
		URL wsdlLocation;
		wsdlLocation = new URL("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl");
		AutorizacionComprobantesOfflineService webSerAutComp = new AutorizacionComprobantesOfflineService(wsdlLocation,
				new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflineService"));
		AutorizacionComprobantesOffline service = webSerAutComp.getAutorizacionComprobantesOfflinePort();
		//RespuestaLote response = service.autorizacionComprobanteLote("");
		
		String request = "0";
		RespuestaComprobante response = service.autorizacionComprobante(request);
		response.getClaveAccesoConsultada();
		response.getNumeroComprobantes();
		for (Autorizacion retDto: response.getAutorizaciones().getAutorizacion()) {
			System.out.println("Estado " + retDto.getEstado());
			System.out.println("FechaAutorizacion " + retDto.getFechaAutorizacion());
			System.out.println("Comprobante " + retDto.getComprobante());
			System.out.println("NumeroAutorizacion " + retDto.getNumeroAutorizacion());
			for (Mensaje retMsg: retDto.getMensajes().getMensaje()) {
				System.out.println("Identificador " + retMsg.getIdentificador());
				System.out.println("Mensaje " + retMsg.getMensaje());
				System.out.println("InformacionAdicional " + retMsg.getInformacionAdicional());
				System.out.println("Tipo " + retMsg.getTipo() +"\n");
		}
		}
	 }
	
	public int autorizacion(String pathEnviados,String claveAcceso,String pathAutorizado,String pathNoAutorizado,int ambiente) throws Exception {
		AutorizacionComprobantesOfflineService autorizacion = new AutorizacionComprobantesOfflineService();
		URL wsdlLocation;
		String urlPruebas = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
		String urlProduccion = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
        String urlEnvio = "";
        if (ambiente == 1) {
        	urlEnvio = urlPruebas;
        }else if (ambiente == 2) {
        	urlEnvio = urlProduccion;
        }
		wsdlLocation = new URL(urlEnvio);
		AutorizacionComprobantesOfflineService webSerAutComp = new AutorizacionComprobantesOfflineService(wsdlLocation,
				new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflineService"));
		AutorizacionComprobantesOffline service = webSerAutComp.getAutorizacionComprobantesOfflinePort();
		RespuestaComprobante response = service.autorizacionComprobante(claveAcceso);
		response.getClaveAccesoConsultada();
		response.getNumeroComprobantes();
		for (Autorizacion retDto: response.getAutorizaciones().getAutorizacion()) {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            String xmlFilePath = pathAutorizado + claveAcceso+".xml";
            // root element
            Element root = document.createElement("autorizacion");
            document.appendChild(root);

            // estado element
            Element estado = document.createElement("estado");
            estado.appendChild(document.createTextNode(retDto.getEstado()));
            root.appendChild(estado);
            
            // numeroAutorizacion element
            Element numeroAutorizacion = document.createElement("numeroAutorizacion");
            numeroAutorizacion.appendChild(document.createTextNode(retDto.getNumeroAutorizacion()));
            root.appendChild(numeroAutorizacion);
 
            // fechaAutorizacion element
            Element fechaAutorizacion = document.createElement("fechaAutorizacion");
            fechaAutorizacion.appendChild(document.createTextNode(retDto.getFechaAutorizacion().toString()));
            root.appendChild(fechaAutorizacion);
       
            // comprobante element
            Element comprobante = document.createElement("comprobante");
            comprobante.appendChild(document.createCDATASection(retDto.getComprobante()));
            root.appendChild(comprobante);            
            
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            
            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging 
 
            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");
            
            
			System.out.println("Estado " + retDto.getEstado());
			System.out.println("FechaAutorizacion " + retDto.getFechaAutorizacion());
			System.out.println("NumeroAutorizacion " + retDto.getNumeroAutorizacion());
			for (Mensaje retMsg: retDto.getMensajes().getMensaje()) {
				System.out.println("Identificador " + retMsg.getIdentificador());
				System.out.println("Mensaje " + retMsg.getMensaje());
				System.out.println("InformacionAdicional " + retMsg.getInformacionAdicional());
				System.out.println("Tipo " + retMsg.getTipo() +"\n");
		}
		}
		return Integer.valueOf(response.getNumeroComprobantes());
	 }
}
