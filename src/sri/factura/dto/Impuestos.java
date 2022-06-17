package sri.factura.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "impuestos")
public class Impuestos {
List <Impuesto> impuestos;

public List<Impuesto> getImpuestos() {
    if (impuestos == null) {
    	impuestos = new ArrayList<Impuesto>();
    }
	return impuestos;
}
@XmlElement(name = "impuesto")
public void setImpuestos(List<Impuesto> impuestos) {
	this.impuestos = impuestos;
}
}
