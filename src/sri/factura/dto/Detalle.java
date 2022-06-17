package sri.factura.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "detalle")
@XmlType(propOrder = { "codigoPrincipal", "codigoAuxiliar" ,"descripcion","cantidad","precioUnitario","descuento"
		,"precioTotalSinImpuesto","detallesAdicionales","impuestos","infoAdicional"})
public class Detalle {
	String codigoPrincipal;
	String codigoAuxiliar;
	String descripcion;
	double cantidad;
	double precioUnitario;
	double descuento;
	double precioTotalSinImpuesto;
	DetallesAdicionales detallesAdicionales;
	Impuestos impuestos;
	InfoAdicional infoAdicional;
	
	public String getCodigoPrincipal() {
		return codigoPrincipal;
	}
	public void setCodigoPrincipal(String codigoPrincipal) {
		this.codigoPrincipal = codigoPrincipal;
	}
	public String getCodigoAuxiliar() {
		return codigoAuxiliar;
	}
	public void setCodigoAuxiliar(String codigoAuxiliar) {
		this.codigoAuxiliar = codigoAuxiliar;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public double getDescuento() {
		return descuento;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	public double getPrecioTotalSinImpuesto() {
		return precioTotalSinImpuesto;
	}
	public void setPrecioTotalSinImpuesto(double precioTotalSinImpuesto) {
		this.precioTotalSinImpuesto = precioTotalSinImpuesto;
	}
	public DetallesAdicionales getDetallesAdicionales() {
		return detallesAdicionales;
	}
	public void setDetallesAdicionales(DetallesAdicionales detallesAdicionales) {
		this.detallesAdicionales = detallesAdicionales;
	}
	public Impuestos getImpuestos() {
		return impuestos;
	}
	public void setImpuestos(Impuestos impuestos) {
		this.impuestos = impuestos;
	}
	public InfoAdicional getInfoAdicional() {
		return infoAdicional;
	}
	public void setInfoAdicional(InfoAdicional infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
}
