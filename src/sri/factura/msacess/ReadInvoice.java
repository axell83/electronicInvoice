package sri.factura.msacess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReadInvoice {
	List<Invoice> facturas;
	public List<Invoice> getFacturas() {
		return facturas;
	}
	public void setFacturas(List<Invoice> facturas) {
		this.facturas = facturas;
	}
	
	public List<Detail> consultaDetalle(String idVenta,String path)
	{
		List<Detail> lstDetail = new ArrayList<Detail>();
		try{
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	        Connection connection= DriverManager.getConnection(path);
	        System.out.println("Connected Successfully");
	        PreparedStatement preparedStatement=connection.prepareStatement("select codprod,cantidad, precio, ventadet.producto,codaux,grabaiva from ventadet, producto where codprod = producto.codigo AND idventa = ?");
	        preparedStatement.setString(1, idVenta);
	        ResultSet resultSet=preparedStatement.executeQuery();
	        
	        while(resultSet.next()){
	        	Detail detail = new Detail();
	        	detail.setCantidad(resultSet.getString("cantidad"));
	        	detail.setCodAuxiliar(resultSet.getString("codaux"));
	        	detail.setCodProducto(resultSet.getString("codprod"));
	        	detail.setGrabaIva(resultSet.getString("grabaiva"));
	        	detail.setPrecio(resultSet.getString("precio"));
	        	detail.setProducto(resultSet.getString("producto"));
	        	lstDetail.add(detail);
	        }
            resultSet.close();
	        preparedStatement.close();
	    }catch(Exception e){
	        System.out.println("Error in connection" + e);
	    }	
		return lstDetail;
	}
	public void consultaOperacion(String fechaIni, String fechaFin,String Path) {
		fechaIni = "#" + fechaIni + " 00:00:00#";
		fechaFin = "#" + fechaFin + " 23:59:59#";
		String queryComprobantes = "SELECT venta.[id],venta.[idcliente],venta.[fecha],venta.[subtotal],venta.[descuento],venta.[total],venta.[fpago],venta.[numfactura],venta.[formapago],venta.[iva], "
				+ "cliente.[apellidos] ,cliente.[nombres] ,cliente.[direccion] ,cliente.[telefono] ,cliente.[cedula],cliente.[tipo] ,cliente.[tipoid],cliente.[relacionado],cliente.[email]"
				+ " FROM venta,cliente"
				+ " WHERE ( cliente.[cod] = venta.[idcliente] AND venta.[comprob] = 'f' AND venta.[anulado] is NULL AND ((venta.[fecha])>="+ fechaIni +") AND ((venta.[fecha])<=" +fechaFin+"));";
		System.out.println(queryComprobantes);
		try{
			Path = "jdbc:ucanaccess://"+Path+ "almacen.mdb";
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection= DriverManager.getConnection(Path);
	        System.out.println("Connected Successfully");
	        //Using SQL SELECT Query
	        PreparedStatement preparedStatement=connection.prepareStatement(queryComprobantes);
	        //Creaing Java ResultSet object
	        ResultSet resultSet=preparedStatement.executeQuery();
	        this.facturas = new ArrayList<Invoice>();
	        while(resultSet.next()){
	        	Invoice factura = new Invoice();
	        	Cliente cliente= new Cliente();
	        	Header header = new Header();
	        	cliente.setCedula(resultSet.getString("cliente.cedula"));
	        	cliente.setDireccion(resultSet.getString("cliente.direccion"));
	        	cliente.setNombre(resultSet.getString("cliente.apellidos") +" "+resultSet.getString("cliente.nombres"));
	        	cliente.setRelacionado(resultSet.getString("cliente.relacionado"));
	        	cliente.setTelefono(resultSet.getString("cliente.telefono"));
	        	cliente.setTipo(resultSet.getString("cliente.tipo"));
	        	cliente.setTipoId(resultSet.getString("cliente.tipoid"));
	        	cliente.setEmail(resultSet.getString("cliente.email"));
	        	factura.setCliente(cliente);
	        	header.setDescuento(resultSet.getString("descuento"));
	        	header.setFecha(resultSet.getString("fecha"));
	        	header.setFormapago(resultSet.getString("formapago"));
	        	header.setFpago(resultSet.getString("fpago"));
	        	header.setId(resultSet.getString("id"));
	        	header.setIdcliente(resultSet.getString("idcliente"));
	        	header.setIva(resultSet.getString("iva"));
	        	header.setNumfactura(resultSet.getString("numfactura"));
	        	header.setSubtotal(resultSet.getString("subtotal"));
	        	header.setTotal(resultSet.getString("total"));
	        	factura.setHeader(header);
	        	factura.setDetails(consultaDetalle(header.getId(),Path));;
	        	facturas.add(factura);
	        }
	        
            resultSet.close();
	        preparedStatement.close();
	    }catch(Exception e){
	        System.out.println("Error in connection" + e);

	    }
	}
}
