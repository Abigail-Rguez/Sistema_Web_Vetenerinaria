package ModeloDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import Config.Conexion;
import Modelo.Detalle_Compras;

public class Detalle_CompraDAO extends Conexion{
	
	Detalle_Compras D_Compra = new Detalle_Compras();
	
	String query;
	
	public Detalle_CompraDAO() {
		
	}
	
	public String Listar_JSON(int IDCompra) {

		PreparedStatement ps=null;
		ResultSet rs=null;
		
		String retorno = new String("[");
		
		this.query = "SELECT R_Producto, productos.Nombre, detalle_compras.Cantidad, productos.Precio_Compra "
				+ "FROM veterinaria.detalle_compras " + 
				"INNER JOIN productos ON detalle_compras.R_Producto=productos.idProductos where R_Compra=?;";
    	
    	try {
            ps = getConnection().prepareStatement(query);
            ps.setInt(1,IDCompra);
            rs = ps.executeQuery();
            
            while(rs.next()) {
            	Detalle_Compras dcompra = new Detalle_Compras();
            	dcompra.setR_Producto(rs.getString("R_Producto"));
            	dcompra.setNombre_Producto(rs.getString("Nombre"));
            	dcompra.setCantidad(rs.getInt("Cantidad"));
            	dcompra.setPrecio(rs.getFloat("Precio_Compra"));
            	
            	retorno+= dcompra.crear_JSON();
            	if(!rs.isLast())
            		retorno+= ",";
            }
	    } catch (Exception var4) {
	        var4.printStackTrace();
	    }
    	retorno+="]";
		return retorno;
	}
	
	public boolean add(Detalle_Compras Dcompra) {
		PreparedStatement ps=null;
		
		this.query = "INSERT INTO detalle_compras (R_Compra,R_Producto,Cantidad) VALUES (?,?,?);";
		
		try {
        	ps = getConnection().prepareStatement(query);
            
            ps.setInt(1,Dcompra.getR_Compra());
            ps.setString(2,Dcompra.getR_Producto());
            ps.setInt(3,Dcompra.getCantidad());
            
            ps.executeUpdate();
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
        return true;
    }
	 
	public int buscar_compra(Date Fecha, Date Hora) {

		PreparedStatement ps=null;
		ResultSet rs=null;
		int ID=0;

		this.query = "SELECT idCompras FROM compras WHERE Fecha = ? and Hora = ?;";
		
		try {
			ps=getConnection().prepareStatement(this.query);
			
			SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-mm-dd"); 
			ps.setString(1,String.format(objSDF.format(Fecha)));
			
			objSDF = new SimpleDateFormat("HH:mm");
            ps.setString(2,String.format(objSDF.format(Hora)));
            
            rs = ps.executeQuery();
            while(rs.next()) {
            	ID=rs.getInt("idCompras");
            }
            
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ID;
	}
	
	

}


