package sri.factura.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "detAdicional")
@XmlType(propOrder = { "nombre", "valor" })
public class DetAdicional {
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
@XmlAttribute
public void setValor(String valor) {
	this.valor = valor;
}
}
