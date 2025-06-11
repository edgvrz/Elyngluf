package clientesoracleapp;

import javax.swing.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
public class ClienteProyectoFrame extends JFrame {
private JTextField txtBuscarDni, txtBuscarIdProyecto,txtIdCliente, txtDniCliente, txtIdProyecto, txtNombreCliente, txtNombreProyecto, txtFechaContrato;
    private JTextArea txtDetalleContrato;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel modelo;

    private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String USER = "SYSTEM";
    private final String PASSWORD = "Oracle";

    public ClienteProyectoFrame() {
        setTitle("Gestión Cliente - Proyecto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        Font fuenteCampos = new Font("Segoe UI", Font.PLAIN, 14);
        Color colorFondo = new Color(245, 250, 255);
        Color colorPanel = new Color(225, 235, 245);
        Color colorBotonGuardar = new Color(70, 130, 180);
        Color colorBotonEliminar = new Color(220, 20, 60);
        Color colorBotonLimpiar = new Color(34, 139, 34);
        Color colorBotonActualizar = new Color(70, 130, 180);
        Color colorBotonBuscar = new Color(70, 130, 180);

        // PANEL CAMPOS
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(colorPanel);
        panelCampos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Datos del Cliente y Proyecto",
                TitledBorder.LEFT, TitledBorder.TOP, fuenteCampos, Color.DARK_GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtBuscarDni = new JTextField(10);
        txtBuscarDni.setFont(fuenteCampos);
        txtBuscarIdProyecto = new JTextField(10);
        txtBuscarIdProyecto.setFont(fuenteCampos);

        txtIdCliente = crearCampoTexto(fuenteCampos);
        txtDniCliente = crearCampoTexto(fuenteCampos);
        txtNombreCliente = crearCampoTexto(fuenteCampos);
        txtIdProyecto = crearCampoTexto(fuenteCampos);
        txtNombreProyecto = crearCampoTexto(fuenteCampos);
        txtFechaContrato = crearCampoTexto(fuenteCampos);

        txtDetalleContrato = new JTextArea(4, 20);
        txtDetalleContrato.setFont(fuenteCampos);
        txtDetalleContrato.setLineWrap(true);
        txtDetalleContrato.setWrapStyleWord(true);
        JScrollPane scrollDetalle = new JScrollPane(txtDetalleContrato);

        agregarCampo(panelCampos, gbc, 0, "ID Cliente:", txtIdCliente);
        agregarCampo(panelCampos, gbc, 1, "DNI Cliente:", txtDniCliente);
        agregarCampo(panelCampos, gbc, 2, "Nombre Cliente:", txtNombreCliente);
        agregarCampo(panelCampos, gbc, 3, "ID Proyecto:", txtIdProyecto);
        agregarCampo(panelCampos, gbc, 4, "Nombre Proyecto:", txtNombreProyecto);
        agregarCampo(panelCampos, gbc, 5, "Fecha Contrato (yyyy-MM-dd):", txtFechaContrato);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelCampos.add(new JLabel("Ingresar Detalle del Contrato:"), gbc);
        gbc.gridx = 1;
        panelCampos.add(scrollDetalle, gbc);

        add(panelCampos, BorderLayout.NORTH);

        // TABLA
        modelo = new DefaultTableModel(
                new String[]{"ID Cliente", "DNI", "Nombre Cliente", "ID Proyecto", "Nombre Proyecto", "Fecha Contrato", "Detalle Contrato"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.setFont(fuenteCampos);
        tabla.setSelectionBackground(new Color(173, 216, 230));
        JScrollPane scrollTabla = new JScrollPane(tabla);
        add(scrollTabla, BorderLayout.CENTER);

        // BOTONES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(colorPanel);

        btnGuardar = crearBoton("Guardar", colorBotonGuardar);
        btnActualizar = crearBoton("Actualizar", colorBotonActualizar);
        btnEliminar = crearBoton("Eliminar", colorBotonEliminar);
    
        btnLimpiar = crearBoton("Limpiar", colorBotonLimpiar);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        JButton btnBuscarDni = crearBoton("Buscar DNI", colorBotonBuscar);
JButton btnBuscarIdProyecto = crearBoton("Buscar Proyecto", colorBotonBuscar);

panelBotones.add(new JLabel("Buscar por DNI:"));
panelBotones.add(txtBuscarDni);
panelBotones.add(btnBuscarDni);

panelBotones.add(new JLabel("Buscar por ID Proyecto:"));
panelBotones.add(txtBuscarIdProyecto);
panelBotones.add(btnBuscarIdProyecto);
   
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);

        // EVENTOS
        btnGuardar.addActionListener(e -> {
            FormularioNuevoClienteProyecto FormularioNuevoClienteProyecto = new FormularioNuevoClienteProyecto();
            FormularioNuevoClienteProyecto.setVisible(true);
        });
        JButton btnRefrescar = crearBoton("Refrescar", colorBotonActualizar);
panelBotones.add(btnRefrescar);
btnRefrescar.addActionListener(e -> cargarDatos());
        btnEliminar.addActionListener(e -> eliminarRegistro());
        btnLimpiar.addActionListener(e -> limpiarCampos());
     
        btnActualizar.addActionListener(e -> actualizarRegistro());
        btnBuscarDni.addActionListener(e -> buscarRegistros());
btnBuscarIdProyecto.addActionListener(e -> buscarRegistros());
        

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtIdCliente.setText(modelo.getValueAt(fila, 0).toString());
                txtDniCliente.setText(modelo.getValueAt(fila, 1).toString());
                txtNombreCliente.setText(modelo.getValueAt(fila, 2).toString());
                txtIdProyecto.setText(modelo.getValueAt(fila, 3).toString());
                txtNombreProyecto.setText(modelo.getValueAt(fila, 4).toString());
                txtFechaContrato.setText(modelo.getValueAt(fila, 5) == null ? "" : modelo.getValueAt(fila, 5).toString());
                txtDetalleContrato.setText(modelo.getValueAt(fila, 6) == null ? "" : modelo.getValueAt(fila, 6).toString());
            }
        });

        getContentPane().setBackground(colorFondo);
        setSize(980, 600);
        setLocationRelativeTo(null);
        cargarDatos();
    }

    private JTextField crearCampoTexto(Font fuente) {
        JTextField campo = new JTextField(15);
        campo.setFont(fuente);
        campo.setBackground(Color.white);
        return campo;
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Ingresar " + etiqueta), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(campo, gbc);
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.white);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setFocusPainted(false);
        return boton;
    }

    private void limpiarCampos() {
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtNombreCliente.setText("");
        txtIdProyecto.setText("");
        txtNombreProyecto.setText("");
        txtFechaContrato.setText("");
        txtDetalleContrato.setText("");
       
        tabla.clearSelection();
    }

    private boolean validarCampos() {
        if (txtIdCliente.getText().trim().isEmpty() || txtIdProyecto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Cliente e ID Proyecto son obligatorios.", "Atención", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM CLIENTE_PROYECTO ORDER BY ID_CLIENTE")) {

            while (rs.next()) {
                Vector<String> fila = new Vector<>();
                fila.add(rs.getString("ID_CLIENTE"));
                fila.add(rs.getString("DNI"));
                fila.add(rs.getString("NOMBRE_CLIENTE"));
                fila.add(rs.getString("ID_PROYECTO"));
                fila.add(rs.getString("NOMBRE_PROYECTO"));
                fila.add(rs.getString("FECHA_CONTRATO"));
                fila.add(rs.getString("DETALLE_CONTRATO"));
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarRegistro() {
        if (!validarCampos()) return;

        String sql = "INSERT INTO CLIENTE_PROYECTO (ID_CLIENTE, DNI, NOMBRE_CLIENTE, ID_PROYECTO, NOMBRE_PROYECTO, FECHA_CONTRATO, DETALLE_CONTRATO) " +
                "VALUES (?, ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtIdCliente.getText().trim());
            ps.setString(2, txtDniCliente.getText().trim());
            ps.setString(3, txtNombreCliente.getText().trim());
            ps.setString(4, txtIdProyecto.getText().trim());
            ps.setString(5, txtNombreProyecto.getText().trim());
            ps.setString(6, txtFechaContrato.getText().trim().isEmpty() ? null : txtFechaContrato.getText().trim());
            ps.setString(7, txtDetalleContrato.getText().trim());

            int n = ps.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Registro guardado correctamente.");
                limpiarCampos();
                cargarDatos();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar registro: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarRegistro() {
        if (!validarCampos()) return;

        String sql = "UPDATE CLIENTE_PROYECTO SET DNI=?, NOMBRE_CLIENTE=?, NOMBRE_PROYECTO=?, FECHA_CONTRATO=TO_DATE(?, 'YYYY-MM-DD'), DETALLE_CONTRATO=? WHERE ID_CLIENTE=? AND ID_PROYECTO=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtDniCliente.getText().trim());
            ps.setString(2, txtNombreCliente.getText().trim());
            ps.setString(3, txtNombreProyecto.getText().trim());
            ps.setString(4, txtFechaContrato.getText().trim().isEmpty() ? null : txtFechaContrato.getText().trim());
            ps.setString(5, txtDetalleContrato.getText().trim());
            ps.setString(6, txtIdCliente.getText().trim());
            ps.setString(7, txtIdProyecto.getText().trim());

            int n = ps.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el registro para actualizar.", "Atención", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar registro: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRegistro() {
        if (txtIdCliente.getText().trim().isEmpty() || txtIdProyecto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro para eliminar (ID Cliente e ID Proyecto).", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este registro?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM CLIENTE_PROYECTO WHERE ID_CLIENTE=? AND ID_PROYECTO=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtIdCliente.getText().trim());
            ps.setString(2, txtIdProyecto.getText().trim());

            int n = ps.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el registro para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar registro: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarRegistros() {
    String dniBusqueda = txtBuscarDni.getText().trim();
    String idProyectoBusqueda = txtBuscarIdProyecto.getText().trim();
    modelo.setRowCount(0);

    if (dniBusqueda.isEmpty() && idProyectoBusqueda.isEmpty()) {
        cargarDatos();
        return;
    }

    String sql;
    boolean buscarPorDni = !dniBusqueda.isEmpty();
    boolean buscarPorIdProyecto = !idProyectoBusqueda.isEmpty();

    if (buscarPorDni && buscarPorIdProyecto) {
        // Si quieren buscar por ambos, podemos hacer un filtro AND
        sql = "SELECT * FROM CLIENTE_PROYECTO WHERE DNI LIKE ? AND ID_PROYECTO LIKE ? ORDER BY ID_CLIENTE";
    } else if (buscarPorDni) {
        sql = "SELECT * FROM CLIENTE_PROYECTO WHERE DNI LIKE ? ORDER BY ID_CLIENTE";
    } else {
        sql = "SELECT * FROM CLIENTE_PROYECTO WHERE ID_PROYECTO LIKE ? ORDER BY ID_CLIENTE";
    }

    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement ps = con.prepareStatement(sql)) {

        if (buscarPorDni && buscarPorIdProyecto) {
            ps.setString(1, "%" + dniBusqueda + "%");
            ps.setString(2, "%" + idProyectoBusqueda + "%");
        } else if (buscarPorDni) {
            ps.setString(1, "%" + dniBusqueda + "%");
        } else {
            ps.setString(1, "%" + idProyectoBusqueda + "%");
        }

        ResultSet rs = ps.executeQuery();
        boolean encontrado = false;

        while (rs.next()) {
            encontrado = true;
            Vector<String> fila = new Vector<>();
            fila.add(rs.getString("ID_CLIENTE"));
            fila.add(rs.getString("DNI"));
            fila.add(rs.getString("NOMBRE_CLIENTE"));
            fila.add(rs.getString("ID_PROYECTO"));
            fila.add(rs.getString("NOMBRE_PROYECTO"));
            fila.add(rs.getString("FECHA_CONTRATO"));
            fila.add(rs.getString("DETALLE_CONTRATO"));
            modelo.addRow(fila);
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "No se encontraron registros con el criterio de búsqueda.",
                    "Buscar", JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error en la búsqueda: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClienteProyectoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClienteProyectoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClienteProyectoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClienteProyectoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClienteProyectoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
