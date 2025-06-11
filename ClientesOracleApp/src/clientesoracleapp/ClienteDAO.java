package clientesoracleapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(llenarCliente(rs));
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public List<Cliente> buscarClientes(String criterio) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE LOWER(dni) LIKE ? OR LOWER(nombre) LIKE ? OR LOWER(apellido) LIKE ?";

        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String busqueda = "%" + criterio.toLowerCase() + "%";
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(llenarCliente(rs));
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Error al buscar clientes: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarCliente(Cliente c) {
        String sql = "UPDATE cliente SET dni=?, nombre=?, apellido=?, telefono=?, correoelectronico=?, direccion_residencial=? WHERE id_cliente=?";
        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getDni());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getApel());
            ps.setString(4, c.getTel());
            ps.setString(5, c.getCorr());
            ps.setString(6, c.getDirec());
            ps.setString(7, c.getIdc());

            int r = ps.executeUpdate();
            return r > 0;

        } catch (Exception e) {
            System.out.println("❌ Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean insertarCliente(Cliente c) {
        String sql = "INSERT INTO cliente (id_cliente, dni, nombre, apellido, telefono, correoelectronico, direccion_residencial) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getIdc());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getNom());
            ps.setString(4, c.getApel());
            ps.setString(5, c.getTel());
            ps.setString(6, c.getCorr());
            ps.setString(7, c.getDirec());

            int r = ps.executeUpdate();
            return r > 0;

        } catch (Exception e) {
            System.out.println("❌ Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    private Cliente llenarCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdc(rs.getString("ID_CLIENTE"));
        c.setDni(rs.getString("DNI"));
        c.setNom(rs.getString("NOMBRE"));
        c.setApel(rs.getString("APELLIDO"));
        c.setTel(rs.getString("TELEFONO"));
        c.setCorr(rs.getString("CORREOELECTRONICO"));
        c.setDirec(rs.getString("DIRECCION_RESIDENCIAL"));
        return c;
    }
}