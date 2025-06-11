
package clientesoracleapp;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class Proyectos extends javax.swing.JFrame {
private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtCodigo, txtNombreProyecto, txtCapacidad, txtTamanoLote, txtUbicacion, txtPrecio, txtUtilidades;
    private JTextField txtBuscar;
    private JButton btnModificar, btnEliminar, btnBuscar, btnRefrescar, btnAgregar;

    private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String USER = "SYSTEM";
    private final String PASSWORD = "Oracle";

    public Proyectos() {
        setTitle("Gestión de Proyectos");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initFormulario();
        cargarDatos();
        agregarEventos();
    }

    private void initFormulario() {
        setLayout(new BorderLayout());

        // Encabezado
        JLabel lblTitulo = new JLabel("Formulario de Proyectos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(45, 52, 54));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setPreferredSize(new Dimension(1000, 60));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabla
        String[] columnas = { "Codigo", "NombreProyecto", "Capacidad", "TamanoDelLote", "Ubicacion", "Precio", "Utilidades" };
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new GridLayout(2, 1));

        // Campos
        JPanel panelCampos = new JPanel(new GridLayout(2, 7, 5, 5));
        txtCodigo = new JTextField();
        txtNombreProyecto = new JTextField();
        txtCapacidad = new JTextField();
        txtTamanoLote = new JTextField();
        txtUbicacion = new JTextField();
        txtPrecio = new JTextField();
        txtUtilidades = new JTextField();

        panelCampos.add(new JLabel("Código"));
        panelCampos.add(new JLabel("Nombre Proyecto"));
        panelCampos.add(new JLabel("Capacidad"));
        panelCampos.add(new JLabel("Tamaño Lote"));
        panelCampos.add(new JLabel("Ubicación"));
        panelCampos.add(new JLabel("Precio"));
        panelCampos.add(new JLabel("Utilidades"));

        panelCampos.add(txtCodigo);
        panelCampos.add(txtNombreProyecto);
        panelCampos.add(txtCapacidad);
        panelCampos.add(txtTamanoLote);
        panelCampos.add(txtUbicacion);
        panelCampos.add(txtPrecio);
        panelCampos.add(txtUtilidades);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());

        txtBuscar = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");
btnAgregar = new JButton("Agregar"); // <-- Nuevo botón agregado

panelBotones.add(new JLabel("Buscar por código:"));
panelBotones.add(txtBuscar);
panelBotones.add(btnBuscar);
panelBotones.add(btnAgregar); // <-- Añadido botón Agregar aquí
panelBotones.add(btnModificar);
panelBotones.add(btnEliminar);
panelBotones.add(btnRefrescar);

        panelInferior.add(panelCampos);
        panelInferior.add(panelBotones);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void agregarEventos() {
        // Seleccionar fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtCodigo.setText(tabla.getValueAt(fila, 0).toString());
                txtNombreProyecto.setText(tabla.getValueAt(fila, 1).toString());
                txtCapacidad.setText(tabla.getValueAt(fila, 2).toString());
                txtTamanoLote.setText(tabla.getValueAt(fila, 3).toString());
                txtUbicacion.setText(tabla.getValueAt(fila, 4).toString());
                txtPrecio.setText(tabla.getValueAt(fila, 5).toString());
                txtUtilidades.setText(tabla.getValueAt(fila, 6).toString());
            }
            
        });

        
        // Botón modificar
        btnModificar.addActionListener(e -> modificarRegistro());

        // Botón eliminar
        btnEliminar.addActionListener(e -> eliminarRegistro());

        // Botón buscar
        btnBuscar.addActionListener(e -> buscarRegistros());

        // Botón refrescar
        btnRefrescar.addActionListener(e -> cargarDatos());
        btnAgregar.addActionListener(e -> {
    FormularioAgregarProyecto agregarFrame = new FormularioAgregarProyecto();
    agregarFrame.setVisible(true);
});
    }
    
    

    private void cargarDatos() {
        modelo.setRowCount(0);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Proyectos")) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("Codigo"),
                    rs.getString("NombreProyecto"),
                    rs.getInt("Capacidad"),
                    rs.getInt("TamanoDelLote"),
                    rs.getString("Ubicacion"),
                    rs.getDouble("Precio"),
                    rs.getDouble("Utilidades")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }

    private void modificarRegistro() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "UPDATE Proyectos SET NombreProyecto=?, Capacidad=?, TamanoDelLote=?, Ubicacion=?, Precio=?, Utilidades=? WHERE Codigo=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, txtNombreProyecto.getText());
                ps.setInt(2, Integer.parseInt(txtCapacidad.getText()));
                ps.setInt(3, Integer.parseInt(txtTamanoLote.getText()));
                ps.setString(4, txtUbicacion.getText());
                ps.setDouble(5, Double.parseDouble(txtPrecio.getText()));
                ps.setDouble(6, Double.parseDouble(txtUtilidades.getText()));
                ps.setInt(7, Integer.parseInt(txtCodigo.getText()));

                int filas = ps.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(this, "Proyecto modificado con éxito");
                    cargarDatos();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage());
        }
    }

    private void eliminarRegistro() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar");
            return;
        }

        int codigo = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

        int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este proyecto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "DELETE FROM Proyectos WHERE Codigo=?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, codigo);
                    int filas = ps.executeUpdate();
                    if (filas > 0) {
                        JOptionPane.showMessageDialog(this, "Proyecto eliminado correctamente");
                        cargarDatos();
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void buscarRegistros() {
    String criterio = txtBuscar.getText().trim();

    if (criterio.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese un código para buscar.");
        return;
    }

    modelo.setRowCount(0); // Limpiar tabla

    String sql = "SELECT * FROM Proyectos WHERE Codigo = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, Integer.parseInt(criterio)); // Convertimos a entero

        ResultSet rs = ps.executeQuery();

        boolean encontrado = false;

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getInt("Codigo"),
                rs.getString("NombreProyecto"),
                rs.getInt("Capacidad"),
                rs.getInt("TamanoDelLote"),
                rs.getString("Ubicacion"),
                rs.getDouble("Precio"),
                rs.getDouble("Utilidades")
            });
            encontrado = true;
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún proyecto con ese código.");
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El código debe ser un número.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Proyectos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Proyectos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Proyectos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proyectos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Proyectos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
