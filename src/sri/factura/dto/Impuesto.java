package sri.factura.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "impuesto")
@XmlType(propOrder = { "codigo", "codigoPorcentaje", "tarifa", "baseImponible", "valor" })
public class Impuesto {
	int codigo;
	int codigoPorcentaje;
	int tarifa;
	double baseImponible;
	Double valor;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodigoPorcentaje() {
		return codigoPorcentaje;
	}
	public void setCodigoPorcentaje(int codigoPorcentaje) {
		this.codigoPorcentaje = codigoPorcentaje;
	}
	public int getTarifa() {
		return tarifa;
	}
	public void setTarifa(int tarifa) {
		this.tarifa = tarifa;
	}
	public double getBaseImponible() {
		return baseImponible;
	}
	public void setBaseImponible(double baseImponible) {
		this.baseImponible = baseImponible;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
}
