package sri.factura.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "infoFactura")
@XmlType(propOrder = { "fechaEmision", "dirEstablecimiento" ,"contribuyenteEspecial","obligadoContabilidad","tipoIdentificacionComprador","guiaRemision"
		,"razonSocialComprador","identificacionComprador","direccionComprador","totalSinImpuestos","totalDescuento"
		,"totalConImpuestos","propina","importeTotal","moneda","pagos","valorRetIva","valorRetRenta"})
public class InfoFactura {
	String fechaEmision;
	String dirEstablecimiento;
	String contribuyenteEspecial;
	String obligadoContabilidad;
	String tipoIdentificacionComprador;
	String guiaRemision;
	String razonSocialComprador;
	String identificacionComprador;
	String direccionComprador;
	double totalSinImpuestos;
	double totalDescuento;
	TotalConImpuestos totalConImpuestos ;
    double propina;
    double importeTotal;
    String moneda;
    Pagos pagos;
    double valorRetIva;
    double valorRetRenta;
    
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getDirEstablecimiento() {
		return dirEstablecimiento;
	}
	public void setDirEstablecimiento(String dirEstablecimiento) {
		this.dirEstablecimiento = dirEstablecimiento;
	}
	public String getContribuyenteEspecial() {
		return contribuyenteEspecial;
	}
	public void setContribuyenteEspecial(String contribuyenteEspecial) {
		this.contribuyenteEspecial = contribuyenteEspecial;
	}
	public String getObligadoContabilidad() {
		return obligadoContabilidad;
	}
	public void setObligadoContabilidad(String obligadoContabilidad) {
		this.obligadoContabilidad = obligadoContabilidad;
	}
	public String getTipoIdentificacionComprador() {
		return tipoIdentificacionComprador;
	}
	public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
		this.tipoIdentificacionComprador = tipoIdentificacionComprador;
	}
	public String getGuiaRemision() {
		return guiaRemision;
	}
	public void setGuiaRemision(String guiaRemision) {
		this.guiaRemision = guiaRemision;
	}
	public String getRazonSocialComprador() {
		return razonSocialComprador;
	}
	public void setRazonSocialComprador(String razonSocialComprador) {
		this.razonSocialComprador = razonSocialComprador;
	}
	public String getIdentificacionComprador() {
		return identificacionComprador;
	}
	public void setIdentificacionComprador(String identificacionComprador) {
		this.identificacionComprador = identificacionComprador;
	}
	public String getDireccionComprador() {
		return direccionComprador;
	}
	public void setDireccionComprador(String direccionComprador) {
		this.direccionComprador = direccionComprador;
	}
	public double getTotalSinImpuestos() {
		return totalSinImpuestos;
	}
	public void setTotalSinImpuestos(double totalSinImpuestos) {
		this.totalSinImpuestos = totalSinImpuestos;
	}
	public double getTotalDescuento() {
		return totalDescuento;
	}
	public void setTotalDescuento(double totalDescuento) {
		this.totalDescuento = totalDescuento;
	}
	public TotalConImpuestos getTotalConImpuestos() {
		return totalConImpuestos;
	}
	public void setTotalConImpuestos(TotalConImpuestos totalConImpuestos) {
		this.totalConImpuestos = totalConImpuestos;
	}
	public double getPropina() {
		return propina;
	}
	public void setPropina(double propina) {
		this.propina = propina;
	}
	public double getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public Pagos getPagos() {
		return pagos;
	}
	public void setPagos(Pagos pagos) {
		this.pagos = pagos;
	}
	public double getValorRetIva() {
		return valorRetIva;
	}
	public void setValorRetIva(double valorRetIva) {
		this.valorRetIva = valorRetIva;
	}
	public double getValorRetRenta() {
		return valorRetRenta;
	}
	public void setValorRetRenta(double valorRetRenta) {
		this.valorRetRenta = valorRetRenta;
	}
}
