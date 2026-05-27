package ui.dialogs;

// Imports Swing components
import javax.swing.*;

// Imports AWT classes for image handling
import java.awt.*;

// Imports POSPanel so this card can add items to cart
import ui.menuPanel.POSPanel;

public class ProductCard extends javax.swing.JPanel {

    // Stores product ID
    private int id;

    // Stores product name
    private String name;

    // Stores product price
    private double price;

    // Reference to the POS Panel
    // Used to call addToCart()
    private POSPanel parentPanel;

    // Constructor
    // Creates a product card
    public ProductCard(
            int id,
            String name,
            double price,
            String imagePath,
            POSPanel parentPanel
    ) {

        // Stores product information
        this.id = id;
        this.name = name;
        this.price = price;

        // Stores reference to POS Panel
        this.parentPanel = parentPanel;

        // Initializes UI components
        initComponents();

        // Displays product name
        // HTML is used so long names wrap into multiple lines
        lblProductName.setText(
                "<html><body style='width: 105px;'>"
                + name
                + "</body></html>"
        );

        // Displays formatted price
        lblPrice.setText(
                String.format("%.2f", price)
        );

        try {

            // If image path is empty, use default image
            if (imagePath == null || imagePath.isEmpty()) {

                imagePath = "/images/default.png";
            }

            // Gets image resource from project folder
            java.net.URL imgURL
                    = getClass().getResource(imagePath);

            // Checks if image exists
            if (imgURL != null) {

                // Loads original image
                ImageIcon originalIcon
                        = new ImageIcon(imgURL);

                // Resizes image smoothly
                Image scaledImage
                        = originalIcon.getImage()
                                .getScaledInstance(
                                        156,
                                        96,
                                        Image.SCALE_SMOOTH
                                );

                // Displays resized image
                lblImage.setIcon(
                        new ImageIcon(scaledImage)
                );

                // Removes placeholder text
                lblImage.setText("");

            } else {

                // Displays message if image is missing
                lblImage.setText("No Image");
            }

            // Centers image horizontally
            lblImage.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            // Centers image vertically
            lblImage.setVerticalAlignment(
                    SwingConstants.CENTER
            );

            // Sets image label size
            lblImage.setSize(156, 96);

            // Sets image label position
            lblImage.setLocation(2, 2);

        } catch (Exception e) {

            // Prints image loading error
            System.out.println(
                    "Could not load image: " + imagePath
            );
        }

        // Default border design
        jPanel2.setBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(226, 232, 240),
                        1
                )
        );

        // Mouse events for product card interactions
        java.awt.event.MouseAdapter interactiveAdapter
                = new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(
                    java.awt.event.MouseEvent e
            ) {

                // Adds product to cart when clicked
                parentPanel.addToCart(id, name, price);
            }

            @Override
            public void mouseEntered(
                    java.awt.event.MouseEvent e
            ) {

                // Slightly lifts card upward
                jPanel2.setLocation(0, -3);

                // Changes border color and thickness
                jPanel2.setBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new java.awt.Color(148, 163, 184),
                                2
                        )
                );

                // Changes background color on hover
                jPanel2.setBackground(
                        new java.awt.Color(248, 250, 252)
                );
            }

            @Override
            public void mouseExited(
                    java.awt.event.MouseEvent e
            ) {

                // Returns card to original position
                jPanel2.setLocation(0, 0);

                // Restores default border
                jPanel2.setBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new java.awt.Color(226, 232, 240),
                                1
                        )
                );

                // Restores default background
                jPanel2.setBackground(
                        new java.awt.Color(255, 255, 255)
                );
            }
        };

        // Adds mouse interaction to all card components
        this.addMouseListener(interactiveAdapter);
        jPanel2.addMouseListener(interactiveAdapter);
        lblImage.addMouseListener(interactiveAdapter);
        lblProductName.addMouseListener(interactiveAdapter);
        lblPrice.addMouseListener(interactiveAdapter);

        // Changes mouse cursor into hand cursor
        this.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblProductName = new javax.swing.JLabel();
        Total = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(160, 180));
        setMinimumSize(new java.awt.Dimension(160, 180));
        setPreferredSize(new java.awt.Dimension(160, 180));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(160, 180));
        jPanel2.setMinimumSize(new java.awt.Dimension(160, 180));
        jPanel2.setPreferredSize(new java.awt.Dimension(160, 180));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProductName.setFont(new java.awt.Font("Geist", 0, 14)); // NOI18N
        lblProductName.setText("Item Name");
        jPanel2.add(lblProductName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 130, 40));

        Total.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        Total.setText("₱");
        jPanel2.add(Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        lblPrice.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lblPrice.setText(" 0.00");
        jPanel2.add(lblPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 20));
        jPanel2.add(lblImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 80));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Total;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblProductName;
    // End of variables declaration//GEN-END:variables
}
