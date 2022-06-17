package sri.consulta.otros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import sri.factura.envio.AutorizacionConsulta;
import sri.factura.envio.AutorizacionXml;

public class SearchInvoice {
	static String pathBdd;
	static String pathGenerados;
	static String pathFirmados;
	static String pathEnviados;
	static String pathAutorizados;
	static String pathNoAutorizados;
	static String pathRechazada;
	static String pathConsultados;
	static int maxCompro = 0;
	static String fechaCompra;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// AMBIENTE
		// 1 PRUEBAS
		// 2 PRODUCCION
		int ambiente = 2;
		consultarPathMdb();
		autorizacion(ambiente,pathConsultados);
	}

	public static void autorizacion(int ambiente,String pathConsultados) {
		File folder = new File(pathConsultados);
		File[] listOfFiles = folder.listFiles();
		String firstPart = "";
		String secondPart = "";
		for (File file : listOfFiles) {
			if (file.isFile()) {
				try {
					AutorizacionXml sendXml = new AutorizacionXml();
					if (!file.getName().contains(".out")) {
						if (Integer.valueOf(file.getName().substring(30, 39)) > maxCompro) {
							maxCompro = Integer.valueOf(file.getName().substring(30, 39));
							fechaCompra = file.getName().substring(0, 8);
							firstPart = file.getName().substring(8,30);
							secondPart = file.getName().substring(39,48);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		int retornoComprobante = 0;
		Calendar fechaSig = Calendar.getInstance();
		fechaSig.add(Calendar.DATE, 1);
		String timeStamp = new SimpleDateFormat("ddMMyyyy").format(fechaSig.getTime());
		int salir = 0;
		while (salir == 0) {

			if (fechaCompra.compareTo(timeStamp) == 0) {
				salir = 1;
				break;
			}
			String cadena = fechaCompra + firstPart + String.format("%09d", maxCompro) + secondPart;
			int digVerificador = calculaDigito(cadena);
			String claveAcceso = cadena + digVerificador;
			System.out.println("Intento 0: "+claveAcceso);
			AutorizacionConsulta sendXml = new AutorizacionConsulta();
			try {
				retornoComprobante = sendXml.autorizacion(pathConsultados, claveAcceso, pathConsultados,
						pathConsultados, ambiente);
				String found = "N";
				if (retornoComprobante == 0) {
					for (int i = 1; i < 3; i++) {
						cadena = fechaCompra + firstPart + String.format("%09d", maxCompro + i)+ secondPart;
						int digVerifInt = calculaDigito(cadena);
						cadena +=  digVerifInt;
						System.out.println("Intento "+ i + ": " +cadena);
						retornoComprobante = sendXml.autorizacion(pathConsultados, cadena, pathConsultados,pathConsultados, ambiente);
						if (retornoComprobante != 0) {
							maxCompro += i;
							found = "S";
							break;
							
						}
						TimeUnit.SECONDS.sleep(4);
					}
					if (found.compareTo("N") == 0) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date fechaFormato = null;
						String fechaIni = fechaCompra.substring(0, 2) + "/" + fechaCompra.substring(2, 4) + "/"
								+ fechaCompra.substring(4, 8);
						try {
							fechaFormato = sdf.parse(fechaIni);
							Date nuevoDay = addDays(fechaFormato, 1);

							sdf.applyPattern("dd/MM/yyyy");
							fechaCompra = sdf.format(nuevoDay).replace("/", "");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					maxCompro += 1;
				}

				TimeUnit.SECONDS.sleep(4);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	public static void consultarPathMdb() {
		Properties prop = new Properties();
		String fileName = "C:\\ComprobantesElectronicosOffline\\FctXal\\app.config";
		try (FileInputStream fis = new FileInputStream(fileName)) {
			prop.load(fis);
		} catch (FileNotFoundException ex) {
			System.out.println("No se encontro el archivo de configuraciones" + ex); 
																						
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
		pathConsultados = prop.getProperty("PathConsultados");
	}

	private static int calculaDigito(String claveAcceso) {
		int suma = 0;
		int factor = 2;
		for (int i = 0; i < claveAcceso.length(); i++) {
			suma += Integer.valueOf(claveAcceso.substring(claveAcceso.length() - i - 1, claveAcceso.length() - i))
					* factor;
			if (factor == 7) {
				factor = 2;
			} else {
				factor++;
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
}
