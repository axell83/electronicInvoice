package sri.factura.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Detalles {
List <Detalle> detalles;

public List<Detalle> getDetalles() {
    if (detalles == null) {
    	detalles = new ArrayList<Detalle>();
    }
	return detalles;
}
@XmlElement(name = "detalle")
public void setDetalles(List<Detalle> detalles) {
	this.detalles = detalles;
}
}
