
package clientesoracleapp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class FormularioNuevoCliente extends JDialog {
     private JTextField txtIdc, txtDni, txtNom, txtApel, txtTel, txtCorr, txtDirec;
    private JButton btnGuardar, btnCancelar;
    private ClienteDAO dao = new ClienteDAO();

    public FormularioNuevoCliente(JFrame parent) {
        super(parent, "Nuevo Cliente", true);
        setSize(450, 480);
        setLocationRelativeTo(parent);
        initComponentes();
    }

    private void initComponentes() {
        Color colorFondo = new Color(34, 40, 49);
        Color colorPanel = new Color(44, 52, 64);
        Color colorTexto = new Color(200, 200, 200);
        Color colorEtiqueta = new Color(170, 170, 170);
        Color colorInputFondo = new Color(60, 68, 82);
        Color colorBotonGuardar = new Color(39, 174, 96);
        Color colorBotonCancelar = new Color(192, 57, 43);
        Color colorTextoBoton = Color.WHITE;
        Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 14);
        Font fuenteEtiqueta = new Font("Segoe UI", Font.BOLD, 13);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10)); // Ajustado a 7 filas porque 7 campos
        panel.setBackground(colorPanel);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        txtIdc = new JTextField();
        txtDni = new JTextField();
        txtNom = new JTextField();
        txtApel = new JTextField();
        txtTel = new JTextField();
        txtCorr = new JTextField();
        txtDirec = new JTextField();

        // Si el ID es autogenerado, deshabilita el campo para evitar edición:
        // txtIdc.setEnabled(false);

        JTextField[] campos = {txtIdc, txtDni, txtNom, txtApel, txtTel, txtCorr, txtDirec};
        for (JTextField campo : campos) {
            campo.setFont(fuenteGeneral);
            campo.setBackground(colorInputFondo);
            campo.setForeground(colorTexto);
            campo.setBorder(new CompoundBorder(
                    new LineBorder(new Color(85, 110, 130), 1, true),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)
            ));
            campo.setCaretColor(colorTexto);
        }

        JLabel[] etiquetas = {
            new JLabel("ID Cliente:"), new JLabel("DNI (*):"), new JLabel("Nombre (*):"), new JLabel("Apellido (*):"),
            new JLabel("Teléfono:"), new JLabel("Correo:"), new JLabel("Dirección:")
        };
        for (JLabel etiqueta : etiquetas) {
            etiqueta.setForeground(colorEtiqueta);
            etiqueta.setFont(fuenteEtiqueta);
        }

        for (int i = 0; i < etiquetas.length; i++) {
            panel.add(etiquetas[i]);
            panel.add(campos[i]);
        }

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        JButton[] botones = {btnGuardar, btnCancelar};
        Color[] coloresBotones = {colorBotonGuardar, colorBotonCancelar};

        for (int i = 0; i < botones.length; i++) {
            JButton btn = botones[i];
            Color colorOriginal = coloresBotones[i];
            btn.setBackground(colorOriginal);
            btn.setForeground(colorTextoBoton);
            btn.setFont(fuenteGeneral);
            btn.setFocusPainted(false);
            btn.setBorder(new LineBorder(colorOriginal.darker(), 2, true));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(110, 38));
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(btn.getBackground().brighter());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(colorOriginal);
                }
            });
        }

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(colorPanel);
        panelBotones.setBorder(new EmptyBorder(10, 0, 15, 0));
        panelBotones.add(btnGuardar);
        panelBotones.add(Box.createRigidArea(new Dimension(20, 0)));
        panelBotones.add(btnCancelar);

        getContentPane().setBackground(colorFondo);
        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarCliente());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarCliente() {
        if (txtDni.getText().isEmpty() || txtNom.getText().isEmpty() || txtApel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos DNI, Nombre y Apellido son obligatorios.",
                    "Campos requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente c = new Cliente();
        c.setIdc(txtIdc.getText());
        c.setDni(txtDni.getText());
        c.setNom(txtNom.getText());
        c.setApel(txtApel.getText());
        c.setTel(txtTel.getText());
        c.setCorr(txtCorr.getText());
        c.setDirec(txtDirec.getText());

        boolean exito = dao.insertarCliente(c);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar cliente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}