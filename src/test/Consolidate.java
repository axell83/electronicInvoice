package test;

import sri.factura.msacess.ReadInvoice;
import sri.factura.msacess.ReadParameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import sri.factura.firma.XAdESBESSignature;
import sri.factura.msacess.Invoice;
import sri.factura.xml.GenerateXml;

public class Consolidate {
static String pathBdd;
static String pathGenerados;
static String pathFirmados;	
static String pathEnviados;
static String pathAutorizados ;
static String pathNoAutorizados;
static String pathRechazada;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		consultarPathMdb();
		
		ReadParameters parameters = new ReadParameters();
		ReadInvoice invoice = new ReadInvoice();
		System.out.println("Ingrese la fecha: [MM/dd/yyyy]");
		Scanner scan = new Scanner(System.in);
		String fecha = scan.nextLine();
		System.out.println(fecha);
		parameters.ReturnParameters(pathBdd);
		invoice.consultaOperacion(fecha,fecha,pathBdd);
		int ultComprobante = 0;
		for(Invoice rdtDto : invoice.getFacturas()){
			GenerateXml generateXml = new GenerateXml(rdtDto,parameters,pathGenerados,pathAutorizados,ultComprobante);
		}
		
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Firmar
		String pathSignature = "C:\\ComprobantesElectronicosOffline\\FctXal\\certificado.p12";
		String passSignature = "XXXXXXXX";
		File folder = new File(pathGenerados);
		File[] listOfFiles = folder.listFiles();		
	}
	public static void consultarPathMdb() {
		Properties prop = new Properties();
		String fileName = "C:\\ComprobantesElectronicosOffline\\FctXal\\app.config";
		try (FileInputStream fis = new FileInputStream(fileName)) {
		    prop.load(fis);
		} catch (FileNotFoundException ex) {
		    System.out.println("No se encontro el archivo de configuraciones" + ex); // FileNotFoundException catch is optional and can be collapsed
		} catch (IOException ex) {
			System.out.println("No se encontro el archivo de configuraciones" + ex);
		}
		pathBdd = prop.getProperty("PathMdb");
		pathGenerados = prop.getProperty("PathGenerados");
		pathFirmados = prop.getProperty("PathFirmados");
		pathEnviados = prop.getProperty("PathEnviados");
		pathAutorizados = prop.getProperty("PathAutorizados");
		pathNoAutorizados = prop.getProperty("PathNoAutorizados");
		pathRechazada = prop.getProperty("PathRechazada");
	}
}
