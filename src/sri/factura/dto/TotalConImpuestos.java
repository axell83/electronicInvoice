package sri.factura.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class TotalConImpuestos {
  List<TotalImpuesto> totalImpuestos;

public List<TotalImpuesto> getTotalImpuestos() {
    if (totalImpuestos == null) {
    	totalImpuestos = new ArrayList<TotalImpuesto>();
    }
	return totalImpuestos;
}
@XmlElement(name = "totalImpuesto")
public void setTotalImpuestos(List<TotalImpuesto> totalImpuestos) {
	this.totalImpuestos = totalImpuestos;
}
}
