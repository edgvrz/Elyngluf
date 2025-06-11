package clientesoracleapp;

import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Look and Feel, usando el por defecto.");
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            new FormularioClientes().setVisible(true);
        });
    }
}
