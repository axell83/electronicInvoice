package sri.factura.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "totalImpuesto")
@XmlType(propOrder = { "codigo", "codigoPorcentaje" ,"descuentoAdicional","baseImponible","valor"})
public class TotalImpuesto {
	int codigo;
	int codigoPorcentaje;
	double descuentoAdicional;
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
	public double getDescuentoAdicional() {
		return descuentoAdicional;
	}
	public void setDescuentoAdicional(double descuentoAdicional) {
		this.descuentoAdicional = descuentoAdicional;
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
