import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAvailableSpots extends JFrame {

    public ViewAvailableSpots() {
        setTitle("Available Spots");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea panoului pentru afișarea locurilor disponibile
        JPanel spotsPanel = createAvailableSpotsPanel();

        // Crearea panoului principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Adăugarea componentelor la panou
        mainPanel.add(createTitlePanel(), BorderLayout.NORTH);
        mainPanel.add(spotsPanel, BorderLayout.CENTER);

        // Adăugarea panoului principal la fereastră
        add(mainPanel);

        // Ajustarea aspectului ferestrei
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Available Animal Spots"));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return titlePanel;
    }

    private JPanel createAvailableSpotsPanel() {
        JPanel spotsPanel = new JPanel();
        spotsPanel.setLayout(new BoxLayout(spotsPanel, BoxLayout.Y_AXIS));

        // Conectare la baza de date
        String url = "jdbc:mysql://localhost/AdapostAnimale?user=root&password=root";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Executare interogare SQL pentru a obține informațiile din vederea adapostlocuridisponibile
            String sql = "SELECT * FROM adapostlocuridisponibile";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int idAdapost = resultSet.getInt("id_adapost");
                        String numeAdapost = resultSet.getString("nume_adapost");
                        String adresaAdapost = resultSet.getString("adresa_adapost");
                        int locuriDisponibile = resultSet.getInt("locuri_disponibile");

                        // Crearea unui panou pentru fiecare rând din vederea adapostlocuridisponibile
                        JPanel rowPanel = createSpotRowPanel(numeAdapost, adresaAdapost, locuriDisponibile);
                        spotsPanel.add(rowPanel);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return spotsPanel;
    }

    private JPanel createSpotRowPanel(String numeAdapost, String adresaAdapost, int locuriDisponibile) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));

        // Adăugarea detaliilor despre adapost
        JLabel nameLabel = new JLabel("Adapost: " + numeAdapost);
        JLabel addressLabel = new JLabel("Adresa: " + adresaAdapost);
        JLabel spotsLabel = new JLabel("Locuri Disponibile: " + locuriDisponibile);

        // Stilizare font
        Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        nameLabel.setFont(boldFont);

        // Adăugarea componentelor în panoul rândului
        rowPanel.add(nameLabel);
        rowPanel.add(addressLabel);
        rowPanel.add(spotsLabel);

        // Adăugarea spațiului între rânduri
        rowPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        return rowPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewAvailableSpots::new);
    }
}
