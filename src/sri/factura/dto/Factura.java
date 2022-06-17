package sri.factura.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "factura")
@XmlType(propOrder = { "infoTributaria", "infoFactura" ,"detalles","infoAdicional"})
public class Factura {
	String id;
	String version;
	InfoTributaria infoTributaria;
	InfoFactura infoFactura;
	Detalles detalles;
    InfoAdicional infoAdicional;

public String getId() {
	return id;
}
//@XmlElement
@XmlAttribute
public void setId(String id) {
	this.id = id;
}
public String getVersion() {
	return version;
}
@XmlAttribute
public void setVersion(String version) {
	this.version = version;
}
public InfoTributaria getInfoTributaria() {
	return infoTributaria;
}
public void setInfoTributaria(InfoTributaria infoTributaria) {
	this.infoTributaria = infoTributaria;
}
public InfoFactura getInfoFactura() {
	return infoFactura;
}
public void setInfoFactura(InfoFactura infoFactura) {
	this.infoFactura = infoFactura;
}
public Detalles getDetalles() {
	return detalles;
}
public void setDetalles(Detalles detalles) {
	this.detalles = detalles;
}
public InfoAdicional getInfoAdicional() {
	return infoAdicional;
}
public void setInfoAdicional(InfoAdicional infoAdicional) {
	this.infoAdicional = infoAdicional;
}
}
