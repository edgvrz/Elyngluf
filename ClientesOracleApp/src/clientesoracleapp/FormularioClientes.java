
package clientesoracleapp;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormularioClientes extends JFrame {
    private JTable tablaClientes;
    private JButton btnCargar, btnBuscar, btnEditar, btnEliminar, btnGuardar, btnNuevo;
    private JTextField txtBuscar, txtIdc, txtDni, txtNom, txtApel, txtTel, txtCorr, txtDirec;
    private ClienteDAO dao = new ClienteDAO();

    public FormularioClientes() {
        setTitle("Clientes - Oracle App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        initComponentes();
    }

    private void initComponentes() {
        Color colorFondo = new Color(245, 250, 255);
        Color colorPanel = new Color(225, 235, 245);
        Color colorBotonPrimario = new Color(10, 102, 204);
        Color colorBotonAccion = new Color(40, 167, 69);
        Color colorTextoBoton = Color.WHITE;
        Color colorLabel = new Color(45, 55, 72);
        Color colorInputFondo = new Color(255, 255, 255);
        Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 14);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 18);
        Font fuenteLabel = new Font("Segoe UI", Font.BOLD, 14);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(colorFondo);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel superior (búsqueda)
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelTop.setBackground(colorPanel);
        panelTop.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 230), 2, true),
                "Buscar Clientes",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(10, 50, 120)
        ));

        txtBuscar = new JTextField(22);
        txtBuscar.setFont(fuenteGeneral);
        txtBuscar.setBackground(colorInputFondo);
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 180, 220), 1, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        btnBuscar = new JButton("Buscar");
        btnCargar = new JButton("Cargar todos");
        btnNuevo = new JButton("Nuevo");

        for (JButton btn : new JButton[]{btnBuscar, btnCargar, btnNuevo}) {
            btn.setBackground(colorBotonPrimario);
            btn.setForeground(colorTextoBoton);
            btn.setFont(fuenteGeneral);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(8, 85, 170), 2, true),
                    BorderFactory.createEmptyBorder(8, 16, 8, 16)
            ));
            // Hover efecto
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(0, 80, 160));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorBotonPrimario);
                }
            });
        }

        JLabel lblBuscar = new JLabel("Buscar por DNI o Nombre:");
        lblBuscar.setFont(fuenteLabel);
        lblBuscar.setForeground(colorLabel);

        panelTop.add(lblBuscar);
        panelTop.add(txtBuscar);
        panelTop.add(btnBuscar);
        panelTop.add(btnCargar);
        panelTop.add(btnNuevo);

        // Tabla
        tablaClientes = new JTable();
        tablaClientes.setFont(fuenteGeneral);
        tablaClientes.setRowHeight(28);
        tablaClientes.setGridColor(new Color(210, 210, 210));
        tablaClientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        tablaClientes.getTableHeader().setBackground(new Color(190, 210, 240));
        tablaClientes.getTableHeader().setForeground(new Color(30, 40, 70));
        tablaClientes.setSelectionBackground(new Color(140, 180, 255));
        tablaClientes.setSelectionForeground(Color.BLACK);
        tablaClientes.setShowHorizontalLines(true);
        tablaClientes.setShowVerticalLines(false);

        // Centrar celdas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tablaClientes.setDefaultRenderer(Object.class, centrado);

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 230), 2, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Panel inferior (formulario)
        JPanel panelBottom = new JPanel(new GridLayout(7, 2, 12, 12)); // Cambié 8 a 7 filas
        panelBottom.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 230), 2, true),
                "Datos del Cliente",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(10, 50, 120)
        ));
        panelBottom.setBackground(colorFondo);

        txtIdc = new JTextField();
        txtIdc.setEditable(false);
        txtDni = new JTextField();
        txtNom = new JTextField();
        txtApel = new JTextField();
        txtTel = new JTextField();
        txtCorr = new JTextField();
        txtDirec = new JTextField();

        JTextField[] campos = {txtIdc, txtDni, txtNom, txtApel, txtTel, txtCorr, txtDirec};
        for (JTextField campo : campos) {
            campo.setFont(fuenteGeneral);
            campo.setBackground(colorInputFondo);
            campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(160, 190, 230), 1, true),
                    BorderFactory.createEmptyBorder(6, 8, 6, 8)
            ));
        }

        JLabel[] etiquetas = {
                new JLabel("ID Cliente:"), new JLabel("DNI:"), new JLabel("Nombre:"), new JLabel("Apellido:"),
                new JLabel("Teléfono:"), new JLabel("Correo:"), new JLabel("Dirección:")
        };
        for (JLabel label : etiquetas) {
            label.setFont(fuenteLabel);
            label.setForeground(colorLabel);
        }

        panelBottom.add(etiquetas[0]);
        panelBottom.add(txtIdc);
        panelBottom.add(etiquetas[1]);
        panelBottom.add(txtDni);
        panelBottom.add(etiquetas[2]);
        panelBottom.add(txtNom);
        panelBottom.add(etiquetas[3]);
        panelBottom.add(txtApel);
        panelBottom.add(etiquetas[4]);
        panelBottom.add(txtTel);
        panelBottom.add(etiquetas[5]);
        panelBottom.add(txtCorr);
        panelBottom.add(etiquetas[6]);
        panelBottom.add(txtDirec);

        // Botones de acción
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnGuardar = new JButton("Guardar");

        for (JButton btn : new JButton[]{btnEditar, btnEliminar, btnGuardar}) {
            btn.setBackground(colorBotonAccion);
            btn.setForeground(colorTextoBoton);
            btn.setFont(fuenteGeneral);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(30, 130, 50), 2, true),
                    BorderFactory.createEmptyBorder(8, 20, 8, 20)
            ));
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(30, 140, 60));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorBotonAccion);
                }
            });
        }

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelBotones.setBackground(colorFondo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(panelBottom, BorderLayout.CENTER);
        southPanel.add(panelBotones, BorderLayout.SOUTH);
        southPanel.setBackground(colorFondo);

        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);

        JPanel panelNavegacion = new JPanel();
        panelNavegacion.setLayout(new GridLayout(5, 1, 10, 10));
        panelNavegacion.setBackground(new Color(44, 52, 64));
        panelNavegacion.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "Proyectos",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                Color.WHITE
        ));

        // Botones de acceso a otras interfaces
        JButton btnProyectos = new JButton("Proyectos");
        JButton btnCP = new JButton("Cliente-Proyecto");

        JButton[] botones = {btnProyectos, btnCP};
        for (JButton b : botones) {
            b.setBackground(new Color(60, 68, 82));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelNavegacion.add(b);
        }

        // Panel contenedor que une tu panel principal + el de navegación
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.add(panelNavegacion, BorderLayout.EAST);

        // Este reemplaza al add(panel); original:
        add(contenedor);

        // Eventos
        btnCargar.addActionListener(e -> cargarClientes());
        btnBuscar.addActionListener(e -> buscarClientes());
        tablaClientes.getSelectionModel().addListSelectionListener(e -> mostrarDatosSeleccionados());
        btnEditar.addActionListener(e -> habilitarEdicion(true));
        btnGuardar.addActionListener(e -> guardarCambios());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnNuevo.addActionListener(e -> {
            FormularioNuevoCliente nuevo = new FormularioNuevoCliente(this);
            nuevo.setVisible(true);
        });
        btnCP.addActionListener(e -> {
            ClienteProyectoFrame ventanaCP = new ClienteProyectoFrame();
            ventanaCP.setVisible(true);
        });
        btnProyectos.addActionListener(e -> {
            Proyectos proyectosFrame = new Proyectos();
            proyectosFrame.setVisible(true);
        });

        habilitarEdicion(false);
    }

    private void cargarClientes() {
        List<Cliente> lista = dao.listarClientes();
        cargarTabla(lista);
    }

    private void buscarClientes() {
        String criterio = txtBuscar.getText().trim();
        List<Cliente> lista = dao.buscarClientes(criterio);
        cargarTabla(lista);
    }

    private void cargarTabla(List<Cliente> lista) {
        String[] columnas = {"ID", "DNI", "Nombre", "Apellido", "Teléfono", "Correo", "Dirección"}; // Quité "Fecha Afiliacion."
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        for (Cliente c : lista) {
            Object[] fila = {
                    c.getIdc(), c.getDni(), c.getNom(), c.getApel(),
                    c.getTel(), c.getCorr(), c.getDirec()
            };
            modelo.addRow(fila);
        }
        tablaClientes.setModel(modelo);
    }

    private void mostrarDatosSeleccionados() {
        int fila = tablaClientes.getSelectedRow();
        if (fila >= 0) {
            txtIdc.setText(tablaClientes.getValueAt(fila, 0).toString());
            txtDni.setText(tablaClientes.getValueAt(fila, 1).toString());
            txtNom.setText(tablaClientes.getValueAt(fila, 2).toString());
            txtApel.setText(tablaClientes.getValueAt(fila, 3).toString());
            txtTel.setText(tablaClientes.getValueAt(fila, 4).toString());
            txtCorr.setText(tablaClientes.getValueAt(fila, 5).toString());
            txtDirec.setText(tablaClientes.getValueAt(fila, 6).toString());
            habilitarEdicion(false);
        }
    }

    private void habilitarEdicion(boolean estado) {
        txtDni.setEditable(estado);
        txtNom.setEditable(estado);
        txtApel.setEditable(estado);
        // txtFa removed
        txtTel.setEditable(estado);
        txtCorr.setEditable(estado);
        txtDirec.setEditable(estado);

        btnGuardar.setEnabled(estado);
        btnEditar.setEnabled(!estado);
    }

    private void guardarCambios() {
        Cliente c = new Cliente();
        c.setIdc(txtIdc.getText());
        c.setDni(txtDni.getText());
        c.setNom(txtNom.getText());
        c.setApel(txtApel.getText());
     
        c.setTel(txtTel.getText());
        c.setCorr(txtCorr.getText());
        c.setDirec(txtDirec.getText());

        boolean actualizado = dao.actualizarCliente(c);
        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            cargarClientes();
            habilitarEdicion(false);
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar cliente.");
        }
    }

    private void eliminarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.");
            return;
        }
        int id = Integer.parseInt(tablaClientes.getValueAt(fila, 0).toString());
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean ok = dao.eliminarCliente(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado.");
                cargarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.");
            }
        }
    }
    private void limpiarCampos() {
        txtIdc.setText("");
        txtDni.setText("");
        txtNom.setText("");
        txtApel.setText("");
        txtTel.setText("");
        txtCorr.setText("");
        txtDirec.setText("");
    }
}