package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import sri.factura.envio.AutorizacionXml;
import sri.factura.envio.SendXml;

public class SendSri {
	static String pathBdd;
	static String pathGenerados;
	static String pathFirmados;	
	static String pathEnviados;
	static String pathAutorizados ;
	static String pathNoAutorizados;
	static String pathRechazada;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//AMBIENTE
		//1 PRUEBAS
		//2 PRODUCCION
		int ambiente = 2;
		consultarPathMdb();
		//enviarXml(ambiente) ;
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		autorizacion(ambiente);
	       	    
	}

	public static void enviarXml(int ambiente) {
		File folder = new File(pathFirmados);
	    File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
	        if (file.isFile()) {
	        	try {
                    SendXml sendXml = new SendXml();
                    //AMBIENTE
                    //1 PRUEBAS
                    //2 PRODUCCION
                    sendXml.enviarXmlSri(pathFirmados,file.getName(),pathEnviados,pathRechazada,ambiente);
					System.out.println(file.getName());
					//boolean result = Files.deleteIfExists(file.toPath());
				} catch (CertificateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	    
	    try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void autorizacion(int ambiente) {
	    File folder = new File(pathEnviados);
	    File[] listOfFiles = folder.listFiles();
	    for (File file : listOfFiles) {
	        if (file.isFile()) {
	        	try {
                    AutorizacionXml sendXml = new AutorizacionXml();
                    if (!file.getName().contains(".out"))
                    {
                     sendXml.autorizacion(pathEnviados, file.getName().replace(".xml", ""), pathAutorizados, pathNoAutorizados,ambiente);
					System.out.println(file.getName());
					//boolean result = Files.deleteIfExists(file.toPath());                   	
                    }

				} catch (CertificateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }	
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
