package sri.factura.envio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import ec.gob.sri.comprobantes.ws.Comprobante;
import ec.gob.sri.comprobantes.ws.Mensaje;
import ec.gob.sri.comprobantes.ws.RecepcionComprobantesOffline;
import ec.gob.sri.comprobantes.ws.RecepcionComprobantesOfflineService;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;

public class SendXml {



	public void enviarXmlSri(String pathFirmados,String archivo,String pathEnviados,String pathRechazadas,int ambiente) throws Exception {
		File file = new File(pathFirmados + archivo);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(file);
		document.getDocumentElement().normalize();
		
		byte[] arg0  =asByteArray(document,"UTF-8");
		String urlPruebas = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
		String urlProduccion = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
        String urlEnvio = "";
        if (ambiente == 1) {
        	urlEnvio = urlPruebas;
        }else if(ambiente == 2) {
        	urlEnvio = urlProduccion;
        }
		URL wsdlLocation;
		wsdlLocation = new URL(urlEnvio);
		RecepcionComprobantesOfflineService webSerRecepcionComprobante = new RecepcionComprobantesOfflineService(wsdlLocation,
				new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService"));
		RecepcionComprobantesOffline service = webSerRecepcionComprobante.getRecepcionComprobantesOfflinePort();
		RespuestaSolicitud response = service.validarComprobante(arg0);
		response.getComprobantes();
		File fout = new File(pathFirmados+archivo+".out");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		System.out.println("Estado: " +response.getEstado());
		bw.write("Estado: " +response.getEstado());
		bw.newLine();
		for (Comprobante retDto: response.getComprobantes().getComprobante()) {
			bw.write("ClaveAcceso: " +retDto.getClaveAcceso());
			System.out.println("ClaveAcceso: " +retDto.getClaveAcceso());
			for (Mensaje msgDto: retDto.getMensajes().getMensaje()) {
				System.out.println("Identificador: " +msgDto.getIdentificador());
				System.out.println("InformacionAdicional: " +msgDto.getInformacionAdicional());
				System.out.println("Mensaje: " +msgDto.getMensaje());
				System.out.println("Tipo: " +msgDto.getTipo() + "\n");
				bw.write("Identificador: " +msgDto.getIdentificador());
				bw.newLine();
				bw.write("InformacionAdicional: " +msgDto.getInformacionAdicional());
				bw.newLine();
				bw.write("Mensaje: " +msgDto.getMensaje());
				bw.newLine();
				bw.write("Tipo: " +msgDto.getTipo() );
				bw.newLine();
			}
			System.out.println(retDto.getMensajes().getMensaje());
		}
		bw.close();
		if (response.getEstado().compareTo("RECIBIDA") == 0) {
			System.out.println("Operación correcta");
			file.renameTo(new File(pathEnviados+file.getName()));
			fout.renameTo(new File(pathEnviados+fout.getName()));
			
		}else {
			System.out.println("Operación incorrecta");
			file.renameTo(new File(pathRechazadas+file.getName()));
			fout.renameTo(new File(pathRechazadas+fout.getName()));
		}
	}
    public static byte[] asByteArray(Document doc, String encoding) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        return writer.getBuffer().toString().getBytes();
    }
}
