package sri.factura.msacess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReadParameters {
	String tipoCompro;
	String ruc;
	String tipoAmbiente;
	String codigoNumerico;
	String tipoEmision;
	String razonSocial;
	String nombreComercial;
	String dirMatriz;
	String dirEstablecimiento;
	String obliContabilidad;
	String codPorcentaje;
	String valorIva;
	String monedaFactura;
	String unidadTiempo;
	String plazoPago;
	String idFacturaXml;
	String versionFacturaXml;

	public void ReturnParameters(String path) {
		try {
			path = "jdbc:ucanaccess://"+path+ "almacen_par.mdb";
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(path);
			System.out.println("Connected Successfully");
			// Using SQL SELECT Query
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from parametros where codigo in (?) ");
			preparedStatement.setString(1, "TIPOCOMPRO");
			// Creaing Java ResultSet object
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				/*
				 * String Username=resultSet.getString("ID"); String
				 * City=resultSet.getString("codigo"); String
				 * Valor=resultSet.getString("valor");
				 */
				this.tipoCompro = resultSet.getString("valor");
				// Printing Results
				// System.out.println(Username+" "+City+" "+Valor);
			}

			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'RUC'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.ruc = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'AMBIENTE'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.tipoAmbiente = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'CODNUMERICO'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.codigoNumerico = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'TIPOEMI'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.tipoEmision = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'RAZONSOCIAL'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.razonSocial = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'NOMBRECOM'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.nombreComercial = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'DIRMATRIZ'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.dirMatriz = resultSet.getString("valor");
			}
			preparedStatement = connection
					.prepareStatement("select * from parametros where codigo = 'DIRESTABLECIMIENTO'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.dirEstablecimiento = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'CONTABILIDAD'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.obliContabilidad = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'CODPORCENTAJE'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.codPorcentaje = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'VALORIVA'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.valorIva = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'MONEDAFACTURA'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.monedaFactura = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'UNIDADTIEMPO'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.unidadTiempo = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'PLAZOPAGO'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.plazoPago = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'IDFACTURA'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.idFacturaXml = resultSet.getString("valor");
			}
			preparedStatement = connection.prepareStatement("select * from parametros where codigo = 'VERSIONFACTURA'");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.versionFacturaXml = resultSet.getString("valor");
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception e) {
			System.out.println("Error in connection");

		}
	}

	public String getTipoCompro() {
		return tipoCompro;
	}

	public void setTipoCompro(String tipoCompro) {
		this.tipoCompro = tipoCompro;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTipoAmbiente() {
		return tipoAmbiente;
	}

	public void setTipoAmbiente(String tipoAmbiente) {
		this.tipoAmbiente = tipoAmbiente;
	}

	public String getCodigoNumerico() {
		return codigoNumerico;
	}

	public void setCodigoNumerico(String codigoNumerico) {
		this.codigoNumerico = codigoNumerico;
	}

	public String getTipoEmision() {
		return tipoEmision;
	}

	public void setTipoEmision(String tipoEmision) {
		this.tipoEmision = tipoEmision;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getDirMatriz() {
		return dirMatriz;
	}

	public void setDirMatriz(String dirMatriz) {
		this.dirMatriz = dirMatriz;
	}

	public String getDirEstablecimiento() {
		return dirEstablecimiento;
	}

	public void setDirEstablecimiento(String dirEstablecimiento) {
		this.dirEstablecimiento = dirEstablecimiento;
	}

	public String getObliContabilidad() {
		return obliContabilidad;
	}

	public void setObliContabilidad(String obliContabilidad) {
		this.obliContabilidad = obliContabilidad;
	}

	public String getCodPorcentaje() {
		return codPorcentaje;
	}

	public void setCodPorcentaje(String codPorcentaje) {
		this.codPorcentaje = codPorcentaje;
	}

	public String getValorIva() {
		return valorIva;
	}

	public void setValorIva(String valorIva) {
		this.valorIva = valorIva;
	}

	public String getMonedaFactura() {
		return monedaFactura;
	}

	public void setMonedaFactura(String monedaFactura) {
		this.monedaFactura = monedaFactura;
	}

	public String getUnidadTiempo() {
		return unidadTiempo;
	}

	public void setUnidadTiempo(String unidadTiempo) {
		this.unidadTiempo = unidadTiempo;
	}

	public String getPlazoPago() {
		return plazoPago;
	}

	public void setPlazoPago(String plazoPago) {
		this.plazoPago = plazoPago;
	}

	public String getIdFacturaXml() {
		return idFacturaXml;
	}

	public void setIdFacturaXml(String idFacturaXml) {
		this.idFacturaXml = idFacturaXml;
	}

	public String getVersionFacturaXml() {
		return versionFacturaXml;
	}

	public void setVersionFacturaXml(String versionFacturaXml) {
		this.versionFacturaXml = versionFacturaXml;
	}
}
