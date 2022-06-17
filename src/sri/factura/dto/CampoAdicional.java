package sri.factura.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
@XmlRootElement(name = "CampoAdicional")
//@XmlType(propOrder = { "nombre", "valor" })
public class CampoAdicional {
String nombre;
String valor;

public String getNombre() {
	return nombre;
}
@XmlAttribute
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getValor() {
	return valor;
}
@XmlValue
public void setValor(String valor) {
	this.valor = valor;
}


}
