package sri.factura.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pagos")
public class Pagos {
List<Pago> pago;

public List<Pago> getPagos() {
    if (pago == null) {
    	pago = new ArrayList<Pago>();
    }
	return pago;
}
@XmlElement(name = "pago")
public void setPagos(List<Pago> pago) {
	this.pago = pago;
} 
}
