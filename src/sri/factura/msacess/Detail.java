package sri.factura.msacess;

public class Detail {
String cantidad;
String precio;
String producto;
String codProducto;
String codAuxiliar;
String grabaIva;
public String getCantidad() {
	return cantidad;
}
public void setCantidad(String cantidad) {
	this.cantidad = cantidad;
}
public String getPrecio() {
	return precio;
}
public void setPrecio(String precio) {
	this.precio = precio;
}
public String getProducto() {
	return producto;
}
public void setProducto(String producto) {
	this.producto = producto;
}
public String getCodProducto() {
	return codProducto;
}
public void setCodProducto(String codProducto) {
	this.codProducto = codProducto;
}
public String getCodAuxiliar() {
	return codAuxiliar;
}
public void setCodAuxiliar(String codAuxiliar) {
	this.codAuxiliar = codAuxiliar;
}
public String getGrabaIva() {
	return grabaIva;
}
public void setGrabaIva(String grabaIva) {
	this.grabaIva = grabaIva;
}
}
