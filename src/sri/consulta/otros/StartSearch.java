package sri.consulta.otros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StartSearch {
	static String pathBdd;
	static String pathGenerados;
	static String pathFirmados;
	static String pathEnviados;
	static String pathAutorizados;
	static String pathNoAutorizados;
	static String pathRechazada;
	static String pathConsultados;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// AMBIENTE
		// 1 PRUEBAS
		// 2 PRODUCCION
		int ambiente = 2;
		String proovedor = "";
		proovedor = "//";
		consultarPathMdb();
		pathConsultados +=proovedor ;
		SearchInvoice searchInvoice = new SearchInvoice();
		searchInvoice.autorizacion(ambiente,pathConsultados);
		List<Detalle> detalles = new ArrayList<Detalle>();

		File folder = new File(pathConsultados);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				if (!file.getName().contains(".out")) {
					OpenXml openXml = new OpenXml();
					openXml.ReadFile(pathConsultados, file.getName(), detalles);
				}
			}
		}

		writeXlsxFile(detalles,proovedor.replace("//", ""));
	}

	public static void writeXlsxFile(List<Detalle> detalles, String proovedor) {
		try {
			FileWriter writer = new FileWriter("C:\\borrar\\ventas"+proovedor+".csv");
			writer.write("fechaEmision;numFactura;codigoPrincipal;descripcion;cantidad;precioUnitario;precioUnitarioIva;precioTotalSinImp;precioTotalConImp;importeTotal;codigoAuxiliar");
			writer.write("\n");
			for (Detalle rtoDto : detalles) {
				String cadena = "";
				String separador = ";";
				cadena += rtoDto.fechaEmision + separador;
				cadena += rtoDto.numFactura + separador;
				cadena += rtoDto.codigoPrincipal + separador;
				cadena += rtoDto.descripcion + separador;
				cadena += rtoDto.cantidad + separador;
				cadena += rtoDto.precioUnitario + separador;
				cadena += String.valueOf(round((Double.valueOf(rtoDto.precioUnitario) * 1.12),2)) + separador;
				if (rtoDto.precioTotalSinImp != null){
									cadena += rtoDto.precioTotalSinImp + separador;
				cadena += String.valueOf(round((Double.valueOf(rtoDto.precioTotalSinImp) * 1.12),2)) + separador;
				}else
					cadena+= separador +separador;

				cadena += rtoDto.importeTotal + separador;
				cadena += rtoDto.codigoAuxiliar + separador;
				writer.write(cadena);
				writer.write("\n");
			}

			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void consultarPathMdb() {
		Properties prop = new Properties();
		String fileName = "";
		try (FileInputStream fis = new FileInputStream(fileName)) {
			prop.load(fis);
		} catch (FileNotFoundException ex) {
			System.out.println("No se encontro el archivo de configuraciones" + ex); // FileNotFoundException catch is
																						// optional and can be collapsed
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
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
