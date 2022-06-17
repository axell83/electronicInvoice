package sri.factura.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sri.factura.dto.CampoAdicional;
import sri.factura.dto.DetAdicional;
import sri.factura.dto.Detalle;
import sri.factura.dto.Detalles;
import sri.factura.dto.DetallesAdicionales;
import sri.factura.dto.Factura;
import sri.factura.dto.Impuesto;
import sri.factura.dto.Impuestos;
import sri.factura.dto.InfoAdicional;
import sri.factura.dto.InfoFactura;
import sri.factura.dto.InfoTributaria;
import sri.factura.dto.Pago;
import sri.factura.dto.Pagos;
import sri.factura.dto.TotalConImpuestos;
import sri.factura.dto.TotalImpuesto;
import sri.factura.msacess.Cliente;
import sri.factura.msacess.Header;
import sri.factura.msacess.Invoice;
import sri.factura.msacess.ReadParameters;

public class GenerateXml {

	public  GenerateXml(Invoice invoice,ReadParameters parameters,String path,String pathAutorizados,int ultFactura) {
		Header header = invoice.getHeader();
		Cliente cliente = invoice.getCliente();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaFormato = null;
		try {
			fechaFormato = sdf.parse(header.getFecha());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf.applyPattern("dd/MM/yyyy");
		header.setFecha(sdf.format(fechaFormato));

		int numFactura = Integer.valueOf(header.getNumfactura().substring(8,17)) - ultFactura;
		
		String claveAcceso = header.getFecha().replace("/", "") + //Fecha de Emisión
				parameters.getTipoCompro()+ //Tipo de Comprobante
				parameters.getRuc()+ //Número de RUC
				parameters.getTipoAmbiente()+//Tipo de Ambiente
				header.getNumfactura().substring(0,3) +//Serie
				header.getNumfactura().substring(4,7)+ //Serie1
		String.format("%09d", numFactura) +//Número del Comprobante
		parameters.getCodigoNumerico()+ //Código Numérico
		parameters.getTipoEmision();//Tipo de Emisión

		//INFO TRIBUTARIA
		InfoTributaria infoTributaria = new InfoTributaria();
		//Tabla 4
		//1 Pruebas 1		Obligatorio
		//2 Producción 2
		infoTributaria.setAmbiente(parameters.getTipoAmbiente());//(1);
		//TABLA 2
		//1 Emisión Normal2 1 Obligatorio
		infoTributaria.setTipoEmision(parameters.getTipoEmision());//(1);
		infoTributaria.setRazonSocial(parameters.getRazonSocial());
		infoTributaria.setNombreComercial(parameters.getNombreComercial());
		infoTributaria.setRuc(parameters.getRuc());
		int digitoVerificador = calculaDigito(claveAcceso);
		claveAcceso = claveAcceso + String.valueOf(digitoVerificador);
		infoTributaria.setClaveAcceso(claveAcceso);//PENDIENTE
		//Tabla 3
		//1 FACTURA 01
		//6 COMPROBANTE DE RETENCIÓN 07
		infoTributaria.setCodDoc(parameters.getTipoCompro());//("01");
		infoTributaria.setEstab(header.getNumfactura().substring(0,3));//("001");
		infoTributaria.setPtoEmi(header.getNumfactura().substring(4,7));//("001");
		infoTributaria.setSecuencial(String.format("%09d", numFactura)); //9
		infoTributaria.setDirMatriz(parameters.getDirMatriz());
				
		//INFOFACTURA
		InfoFactura infoFactura= new InfoFactura();
		infoFactura.setFechaEmision(header.getFecha());//dd/mm/aaaa
		infoFactura.setDirEstablecimiento(parameters.getDirEstablecimiento());//300
		//infoFactura.setContribuyenteEspecial("");//13
		infoFactura.setObligadoContabilidad(parameters.getObliContabilidad());//SI / NO
		//Tabla 6
		//1 RUC 04 Obligatorio
		//2 CEDULA 05 Obligatorio
		//3 PASAPORTE 06 Obligatorio
		//4 VENTA A CONSUMIDOR FINAL* 07 Obligatorio
		//5 IDENTIFICACION DELEXTERIOR* 08 Obligatorio
		infoFactura.setTipoIdentificacionComprador(cliente.getTipoId());//05
		//infoFactura.setGuiaRemision("");//15
		infoFactura.setRazonSocialComprador(cliente.getNombre()); //Max300
		infoFactura.setIdentificacionComprador(cliente.getCedula());//Max 20
		infoFactura.setDireccionComprador(cliente.getDireccion());//Max 300
		
		infoFactura.setTotalSinImpuestos(Double.valueOf(header.getSubtotal()));//Max14 XXXX
		infoFactura.setTotalDescuento(0);//Max 14    XXXX
		
		//TotalImpuesto
		TotalImpuesto totalImpuesto = new TotalImpuesto();
		//Tabla 16
		//IVA 2
		//ICE 3
		//IRBPNR 5
		totalImpuesto.setCodigo(Integer.valueOf(parameters.getCodPorcentaje()));//2 XXXX
		//Tabla 17 o //Tabla 18 Dependiendo de Codigo
/*		Porcentaje de IVA Código
		0% 0
		12% 2
		14% 3
		No Objeto de Impuesto 6
		Exento de IVA 7*/
		totalImpuesto.setCodigoPorcentaje(Integer.valueOf(parameters.getCodPorcentaje()));//Min 1 Max 4
		totalImpuesto.setBaseImponible(Double.valueOf(header.getSubtotal()));//Max 14
		Double valorIva = round(Double.valueOf(header.getSubtotal()) * 0.12,2);
		totalImpuesto.setValor(valorIva);//Max 14		
		//TotalConImpuestos
		TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
		totalConImpuestos.getTotalImpuestos().add(totalImpuesto);
		infoFactura.setTotalConImpuestos(totalConImpuestos);
		infoFactura.setPropina(0.00);//Max 14
		infoFactura.setImporteTotal(Double.valueOf(header.getTotal()));//Max 14
		infoFactura.setMoneda(parameters.getMonedaFactura());//Max 15 

		Pago pago = new Pago();
		//Tabla 24
		/*
FORMAS DE PAGO CÓDIGO FECHA INICIO FECHA FIN
SIN UTILIZACION DEL SISTEMA FINANCIERO 01 01/01/2013 -
COMPENSACIÓN DE DEUDAS 15 01/01/2013 -
TARJETA DE DÉBITO 16 01/06/2016 -
DINERO ELECTRÓNICO 17 01/06/2016 -
TARJETA PREPAGO 18 01/06/2016 -
TARJETA DE CRÉDITO 19 01/06/2016 -
OTROS CON UTILIZACION DEL SISTEMA FINANCIERO 20 01/06/2016 -
ENDOSO DE TÍTULOS 21 01/06/2016 -
		 */
		pago.setFormaPago("01");  //XXXX
		pago.setTotal(Double.valueOf(header.getTotal()));    //XXXX
		pago.setPlazo(0);       //XXXX
		pago.setUnidadTiempo(parameters.getUnidadTiempo());//Max 10//XXXX
		
		//PAGOS
		Pagos pagos = new Pagos();
		pagos.getPagos().add(pago);
		
		infoFactura.setPagos(pagos);
		infoFactura.setValorRetIva(0.00);
		infoFactura.setValorRetRenta(0.00);
		//Detalles
		Detalles detalles = new Detalles();		
		for(sri.factura.msacess.Detail rtoDetail:invoice.getDetails()) {
			
			//Impuesto
			Impuesto impuesto = new Impuesto();
			//Tabla 16
	/*Impuesto Código
	IVA 2
	ICE 3
	IRBPNR 5*/		
			if (rtoDetail.getGrabaIva().compareTo("Y") == 0 ) {
				impuesto.setCodigo(2);//XXXX
			}
			
			//Tabla 17 o 18 Dependiendo de campo anterior
	/*TABLA 17: TARIFA DEL IVA
	Porcentaje de IVA Código
	0% 0
	12% 2
	14% 3
	No Objeto de Impuesto 6
	Exento de IVA 7*/		
			impuesto.setCodigoPorcentaje(Integer.valueOf(parameters.getCodPorcentaje()));//XXXX
			impuesto.setTarifa(Integer.valueOf(parameters.getValorIva()));//Min 1 Max 4//XXXX
			Double baseImponible = Double.valueOf(rtoDetail.getCantidad()) * Double.valueOf(rtoDetail.getPrecio());
			impuesto.setBaseImponible(baseImponible);//XXXX
			Double valorIvaPrd = round(baseImponible *  Integer.valueOf(parameters.getValorIva())/ 100,2);
			impuesto.setValor(valorIvaPrd);//Max 14	
			
			//Impuestos
			Impuestos impuestos = new Impuestos();
			impuestos.getImpuestos().add(impuesto);			
			Detalle detalle = new Detalle();
			detalle.setCodigoPrincipal(rtoDetail.getProducto());//Inventado Max 25
			detalle.setDescripcion(rtoDetail.getCodAuxiliar());//Max 300
			detalle.setCantidad(Double.valueOf(rtoDetail.getCantidad()));
			detalle.setPrecioUnitario(Double.valueOf(rtoDetail.getPrecio()));
			detalle.setDescuento(0.00);
			detalle.setPrecioTotalSinImpuesto(baseImponible);	
		    //detalle.setDetallesAdicionales(detallesAdicionales);
		    detalle.setImpuestos(impuestos);
		    
		    detalles.getDetalles().add(detalle);			
		}				
		//FACTURA
		 
		Factura factura = new Factura();
		factura.setId(parameters.getIdFacturaXml());//comprobante
		factura.setVersion(parameters.getVersionFacturaXml());//1.0.0
		factura.setInfoTributaria(infoTributaria);
		factura.setInfoFactura(infoFactura);
		factura.setDetalles(detalles);
		
		if (cliente.getEmail().compareTo("")!=0) {
		//CampoAdicional	
		CampoAdicional campoAdicional = new CampoAdicional();
		campoAdicional.setNombre("EMAIL");
		campoAdicional.setValor(cliente.getEmail());
		
		//InfoAdicional
		InfoAdicional infoAdicional = new InfoAdicional();
		infoAdicional.getInfoAdicional().add(campoAdicional);	
		factura.setInfoAdicional(infoAdicional);
		}		
		
		jaxbObjectToXML(factura,claveAcceso+".xml",path,pathAutorizados);
	}
	
	private static void jaxbObjectToXML(Factura solicitud,String fileName,String path,String pathAutorizado) {
		String xmlString = "";
		try {
			JAXBContext context = JAXBContext.newInstance(Factura.class);
			Marshaller m = context.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

			StringWriter sw = new StringWriter();
			m.marshal(solicitud, sw);
			try {
				FileOutputStream fileOutput = new FileOutputStream(path + fileName);
				m.marshal(solicitud, fileOutput);
				fileOutput.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			xmlString = sw.toString();
			createXmlPdf(pathAutorizado,fileName,xmlString) ;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private static int calculaDigito(String claveAcceso) {
		int suma = 0;
		int factor = 2;
		for (int i=0; i< claveAcceso.length();i++)
		{
			suma +=  Integer.valueOf(claveAcceso.substring(claveAcceso.length()-i-1, claveAcceso.length()-i)) * factor;
			if (factor == 7) {
				factor = 2;
			}else {
				factor ++;
			}
		}
		int digitoVerif = 0;
		digitoVerif = (11 - (suma % 11));
		if (digitoVerif == 10)
			digitoVerif = 1;
		if (digitoVerif == 11)
			digitoVerif = 0;
		return digitoVerif;
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	public static void createXmlPdf(String pathAutorizado,String claveAcceso,String comprobanteXml) {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			
        String xmlFilePath = pathAutorizado + "tmp_"+claveAcceso+".xml";
        // root element
        Element root = document.createElement("autorizacion");
        document.appendChild(root);

        // estado element
        Element estado = document.createElement("estado");
        estado.appendChild(document.createTextNode("AUTORIZADO"));
        root.appendChild(estado);
        
        // numeroAutorizacion element
        Element numeroAutorizacion = document.createElement("numeroAutorizacion");
        numeroAutorizacion.appendChild(document.createTextNode("090420220117384932200110010010000428301234657811"));
        root.appendChild(numeroAutorizacion);

        // fechaAutorizacion element
        Element fechaAutorizacion = document.createElement("fechaAutorizacion");
        fechaAutorizacion.appendChild(document.createTextNode("2022-04-11T16:42:13-05:00"));
        root.appendChild(fechaAutorizacion);
   
        // comprobante element
        Element comprobante = document.createElement("comprobante");
        comprobante.appendChild(document.createCDATASection(comprobanteXml));
        root.appendChild(comprobante);            
        
        // create the xml file
        //transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        			transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
        
        // If you use
        // StreamResult result = new StreamResult(System.out);
        // the output will be pushed to the standard output ...
        // You can use that for debugging 

        transformer.transform(domSource, streamResult);
        System.out.println("Done creating XML File");
        
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
