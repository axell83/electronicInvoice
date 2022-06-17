package sri.factura.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "infoAdicional")
public class InfoAdicional {
List<CampoAdicional> campoAdicional;

public List<CampoAdicional> getInfoAdicional() {
    if (campoAdicional == null) {
    	campoAdicional = new ArrayList<CampoAdicional>();
    }
	return campoAdicional;
}
@XmlElement(name = "campoAdicional")
public void setInfoAdicional(List<CampoAdicional> campoAdicional) {
	this.campoAdicional = campoAdicional;
}
}
