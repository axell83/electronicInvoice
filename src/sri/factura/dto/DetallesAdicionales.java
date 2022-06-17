package sri.factura.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class DetallesAdicionales {
List <DetAdicional> detallesAdicionales;

public List<DetAdicional> getDetallesAdicionales() {
    if (detallesAdicionales == null) {
    	detallesAdicionales = new ArrayList<DetAdicional>();
    }
	return detallesAdicionales;
}
@XmlElement(name = "detAdicional")
public void setDetallesAdicionales(List<DetAdicional> detallesAdicionales) {
	this.detallesAdicionales = detallesAdicionales;
}
}
