import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ConversorMonedas extends JFrame {

    private JComboBox<String> monedaOrigenCombo;
    private JComboBox<String> monedaDestinoCombo;
    private JTextField cantidadTextField;
    private JLabel resultadoLabel;
    private JButton convertirButton;
    private JButton limpiarButton;
    private Map<String, Double> tasasDeCambio;

    public ConversorMonedas() {
        // Configuración de la ventana principal
        super("Conversor de Monedas - Challenge Alura");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Inicializar tasas de cambio (con respecto al dólar americano)
        inicializarTasasDeCambio();

        // Crear panel principal con GridBagLayout para mejor control del diseño
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Título
        JLabel titleLabel = new JLabel("Conversor de Monedas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Cantidad a convertir
        JLabel cantidadLabel = new JLabel("Cantidad:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(cantidadLabel, gbc);

        cantidadTextField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(cantidadTextField, gbc);

        // Moneda de origen
        JLabel origenLabel = new JLabel("De:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(origenLabel, gbc);

        monedaOrigenCombo = new JComboBox<>(new String[]{
                "Peso Mexicano (MXN)",
                "Dólar (USD)",
                "Euro (EUR)",
                "Libra Esterlina (GBP)",
                "Yen Japonés (JPY)",
                "Won Surcoreano (KRW)",
                "Real Brasileño (BRL)",
                "Peso Argentino (ARS)",
                "Peso Colombiano (COP)",
                "Peso Chileno (CLP)"
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(monedaOrigenCombo, gbc);

        // Moneda de destino
        JLabel destinoLabel = new JLabel("A:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(destinoLabel, gbc);

        monedaDestinoCombo = new JComboBox<>(new String[]{
                "Dólar (USD)",
                "Euro (EUR)",
                "Libra Esterlina (GBP)",
                "Yen Japonés (JPY)",
                "Won Surcoreano (KRW)",
                "Peso Mexicano (MXN)",
                "Real Brasileño (BRL)",
                "Peso Argentino (ARS)",
                "Peso Colombiano (COP)",
                "Peso Chileno (CLP)"
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(monedaDestinoCombo, gbc);

        // Panel para botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        convertirButton = new JButton("Convertir");
        limpiarButton = new JButton("Limpiar");

        buttonPanel.add(convertirButton);
        buttonPanel.add(limpiarButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Resultado
        resultadoLabel = new JLabel("Resultado: ", SwingConstants.CENTER);
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(resultadoLabel, gbc);

        // Añadir eventos a los botones
        convertirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertir();
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });

        // Añadir el panel principal a la ventana
        add(mainPanel);
        setVisible(true);
    }

    private void inicializarTasasDeCambio() {
        tasasDeCambio = new HashMap<>();
        // Tasas con respecto al dólar (USD)
        tasasDeCambio.put("Dólar (USD)", 1.0);
        tasasDeCambio.put("Euro (EUR)", 0.91);
        tasasDeCambio.put("Libra Esterlina (GBP)", 0.78);
        tasasDeCambio.put("Yen Japonés (JPY)", 148.50);
        tasasDeCambio.put("Won Surcoreano (KRW)", 1334.85);
        tasasDeCambio.put("Peso Mexicano (MXN)", 16.78);
        tasasDeCambio.put("Real Brasileño (BRL)", 5.04);
        tasasDeCambio.put("Peso Argentino (ARS)", 880.0);
        tasasDeCambio.put("Peso Colombiano (COP)", 3895.0);
        tasasDeCambio.put("Peso Chileno (CLP)", 912.0);
    }

    private void convertir() {
        try {
            // Obtener los valores de los controles
            String monedaOrigen = (String) monedaOrigenCombo.getSelectedItem();
            String monedaDestino = (String) monedaDestinoCombo.getSelectedItem();

            // Validar que no sean la misma moneda
            if (monedaOrigen.equals(monedaDestino)) {
                JOptionPane.showMessageDialog(this,
                        "La moneda de origen y destino no pueden ser iguales",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener cantidad
            String cantidadStr = cantidadTextField.getText().trim();
            if (cantidadStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor ingrese una cantidad",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double cantidad = Double.parseDouble(cantidadStr);

            // Realizar la conversión
            double tasaOrigen = tasasDeCambio.get(monedaOrigen);
            double tasaDestino = tasasDeCambio.get(monedaDestino);

            // Primero convertimos a USD y luego al destino
            double enDolares = cantidad / tasaOrigen;
            double resultado = enDolares * tasaDestino;

            // Formatear el resultado
            DecimalFormat df = new DecimalFormat("#,##0.00");

            // Mostrar el resultado
            String simboloOrigen = obtenerSimbolo(monedaOrigen);
            String simboloDestino = obtenerSimbolo(monedaDestino);

            resultadoLabel.setText(simboloOrigen + df.format(cantidad) + " = " +
                    simboloDestino + df.format(resultado));

            // Mostrar un mensaje con la conversión realizada
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Desea continuar usando el programa?",
                    "Conversión Exitosa",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this,
                        "Programa finalizado",
                        "Gracias por usar el conversor",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un valor numérico válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ha ocurrido un error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerSimbolo(String moneda) {
        if (moneda.contains("Dólar")) return "$";
        if (moneda.contains("Euro")) return "€";
        if (moneda.contains("Libra")) return "£";
        if (moneda.contains("Yen")) return "¥";
        if (moneda.contains("Won")) return "₩";
        if (moneda.contains("Peso Mexicano")) return "MX$";
        if (moneda.contains("Real")) return "R$";
        if (moneda.contains("Peso Argentino")) return "AR$";
        if (moneda.contains("Peso Colombiano")) return "COP ";
        if (moneda.contains("Peso Chileno")) return "CLP ";
        return "";
    }

    private void limpiar() {
        cantidadTextField.setText("");
        monedaOrigenCombo.setSelectedIndex(0);
        monedaDestinoCombo.setSelectedIndex(0);
        resultadoLabel.setText("Resultado: ");
        cantidadTextField.requestFocus();
    }

    public static void main(String[] args) {
        // Configurar look and feel nativo del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ConversorMonedas();
            }
        });
    }
}