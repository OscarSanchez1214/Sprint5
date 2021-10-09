package restful.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import restful.Model.Conexion;
import restful.Model.ProductoModel;

public class ProductoService {

   public ArrayList<ProductoModel> getProductos() {
        ArrayList<ProductoModel> lista = new ArrayList<>();
        Conexion conn = new Conexion();
        String sql = "SELECT * FROM producto";
       
       try {
       Statement stm = conn.getCon().createStatement();
       ResultSet rs = stm.executeQuery(sql);
       while (rs.next()){       
                ProductoModel producto = new ProductoModel();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getString("precio"));
                lista.add(producto);
        } }catch (SQLException e) {
        }

        return lista;
    }

    public ProductoModel getProducto(int id) {
        ProductoModel producto = new ProductoModel();
        Conexion conex = new Conexion();
        String Sql = "SELECT * FROM producto WHERE id_producto = ?";

        try {

            PreparedStatement pstm = conex.getCon().prepareStatement(Sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                producto.setId_producto(rs.getInt("id_producto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getString("precio"));
                
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return producto;
    }

    public ProductoModel addProducto(ProductoModel producto) {
        Conexion conex = new Conexion();
        String Sql = "INSERT INTO producto(id_producto,descripcion,precio)";
         Sql = Sql + "values (?,?,?)";

        try {
            PreparedStatement pstm = conex.getCon().prepareStatement(Sql);
            pstm.setInt(1, producto.getId_producto());
            pstm.setString(2, producto.getDescripcion());
            pstm.setString(3, producto.getPrecio());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return producto;
    }

    public ProductoModel updateProducto(ProductoModel producto) {
        Conexion conn = new Conexion();
        String sql = "UPDATE producto SET descripcion=?,precio=? WHERE id_producto= ?";
        try {
            PreparedStatement pstm = conn.getCon().prepareStatement(sql);
            pstm.setString(1,producto.getDescripcion());
            pstm.setString(2, producto.getPrecio());
                       pstm.setInt(3, producto.getId_producto());
            pstm.executeUpdate();
        } catch (SQLException excepcion) {
            System.out.println("Ha ocurrido un error al eliminar  " + excepcion.getMessage());
            return null;
        }
        return producto;
    }

    public String delProducto(int id) {
        Conexion conn = new Conexion();

        String sql = "DELETE FROM producto WHERE id_producto= ?";
        try {
            PreparedStatement pstm = conn.getCon().prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException excepcion) {
            System.out.println("Ha ocurrido un error al eliminar  " + excepcion.getMessage());
            return "{\"Accion\":\"Error\"}";
        }
        return "{\"Accion\":\"Registro Borrado\"}";
    }
}

  