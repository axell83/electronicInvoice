package sri.factura.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "pago")
@XmlType(propOrder = { "formaPago", "total" ,"plazo","unidadTiempo"})
public class Pago {
	String formaPago;
	double total;
	int plazo;
	String unidadTiempo;
	
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getPlazo() {
		return plazo;
	}
	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}
	public String getUnidadTiempo() {
		return unidadTiempo;
	}
	public void setUnidadTiempo(String unidadTiempo) {
		this.unidadTiempo = unidadTiempo;
	}
}
