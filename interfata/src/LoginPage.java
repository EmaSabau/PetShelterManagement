import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JFrame currentFrame;

    public LoginPage() {
        setTitle("Animal Shelter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea panoului principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);

        // Adăugarea titlului
        JLabel titleLabel = new JLabel("Animal Shelter");
        titleLabel.setFont(new Font("Valentine Taste", Font.BOLD, 32));
        titleLabel.setForeground(new Color(150, 107, 157));
        mainPanel.add(titleLabel, gbc);

        // Adăugarea textului sub titlu
        gbc.gridy++;
        JLabel infoLabel = new JLabel("Cum dorești să te loghezi?");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(infoLabel, gbc);

        // Adăugarea butoanelor
        gbc.gridy++;
        JButton clientButton = new JButton("client login");
        JButton ownerButton = new JButton("owner login");

        // Stilizare butoane
        clientButton.setFont(new Font("Arial", Font.PLAIN, 15));
        ownerButton.setFont(new Font("Arial", Font.PLAIN, 15));

        clientButton.setBackground(new Color(150, 107, 157)); // Culoare mov
        clientButton.setForeground(Color.WHITE); // Text alb

        ownerButton.setBackground(new Color(150, 107, 157)); // Culoare mov
        ownerButton.setForeground(Color.WHITE); // Text alb

        // Adăugarea acțiunilor pentru butoane
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClientLogin();
            }
        });

        ownerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOwnerLogin();
            }
        });

        mainPanel.add(clientButton, gbc);
        gbc.gridy++;
        mainPanel.add(ownerButton, gbc);

        // Adăugarea panoului principal la fereastră
        add(mainPanel);

        // Setarea dimensiunilor și vizibilității
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // Setarea ferestrei curente
        currentFrame = this;
    }

    private void openClientLogin() {
        // Implementarea deschiderii paginii de login pentru client
        SwingUtilities.invokeLater(() -> {
            new LogInCustomer();
            // currentFrame.dispose();
        });
    }

    private void openOwnerLogin() {
        // Implementarea deschiderii paginii de login pentru owner
        SwingUtilities.invokeLater(() -> {
            new LogInOwner();
            // currentFrame.dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}
