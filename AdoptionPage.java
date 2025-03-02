import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdoptionPage extends JFrame {

    public AdoptionPage(String shelterName) {
        // Setările ferestrei
        setTitle(shelterName + " - Adoption Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));  // Dimensiune predefinită

        // Crearea componentelor
        JLabel titleLabel = new JLabel(shelterName + " Adoption");
        titleLabel.setFont(new Font("Valentine Taste", Font.BOLD, 24));
        titleLabel.setForeground(new Color(150, 107, 157));

        // Crearea modelului pentru tabelul cu animale
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Animal ID");
        tableModel.addColumn("Animal Name");
        tableModel.addColumn("Species");
        tableModel.addColumn("Adopt");

        // Adăugare animale în tabel
        addAnimalsToTable(shelterName, tableModel);

        // Crearea tabelului cu animale
        JTable animalTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(animalTable);

        // Crearea panel-ului principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Adăugarea titlului în partea de sus
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Adăugarea tabelului în partea de mijloc
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Adăugarea panel-ului principal la fereastră
        add(mainPanel);

        // Ajustarea aspectului ferestrei
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // Adăugarea unui ascultător pentru evenimentul de selectare a butonului "Adopt"
        animalTable.getColumnModel().getColumn(2).setCellRenderer((TableCellRenderer) new ButtonRenderer());
        animalTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox(), shelterName));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(shelterName);
            }

            private void refreshTable(String shelterName) {
                // Șterge toate rândurile din tabel
                tableModel.setRowCount(0);

                // Adaugă din nou animalele în tabel
                addAnimalsToTable(shelterName, tableModel);
            }
        });

        // Adăugarea butonului de refresh în partea de jos a ferestrei
        mainPanel.add(refreshButton, BorderLayout.SOUTH);
    }

    private void addAnimalsToTable(String shelterName, DefaultTableModel tableModel) {
        // Conectare la baza de date
        String url = "jdbc:mysql://localhost/AdapostAnimale?user=root&password=root";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Executare interogare SQL pentru a obține animalele unice din adapostul specificat
            String sql = "SELECT DISTINCT a.id_animal, a.nume_animal, a.specie FROM Animal a " +
                    "JOIN Adapost ad ON a.id_adapost = ad.id_adapost " +
                    "WHERE ad.nume_adapost = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, shelterName);

                try (ResultSet resultSet = statement.executeQuery()) {
                    // Adăugare animale în modelul tabelului
                    while (resultSet.next()) {
                        String IDAnimal = resultSet.getString("id_animal");
                        String numeAnimal = resultSet.getString("nume_animal");
                        String specie = resultSet.getString("specie");

                        tableModel.addRow(new Object[]{numeAnimal, specie, "Adopt"});
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Clasa ButtonRenderer
    static class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Clasa ButtonEditor
    static class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String shelterName;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox, String shelterName) {
            super(checkBox);
            this.shelterName = shelterName;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }
            button.setText("Adopt");
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                new AdoptionDialog((JFrame) SwingUtilities.getWindowAncestor(button), shelterName).setVisible(true);
            }
            isPushed = false;
            return new String("Adopt");
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdoptionPage("Nume Adapost Exemplu"));
    }
}