package ui.dialogs;

// Imports database connection class
import database.DBConnection;

// Imports SQL classes
import java.sql.Connection;
import java.sql.PreparedStatement;

// Imports JOptionPane for popup messages
import javax.swing.JOptionPane;

public class UpdateStockDialog extends javax.swing.JDialog {

    // Logger used for debugging or tracking errors
    private static final java.util.logging.Logger logger
            = java.util.logging.Logger.getLogger(
                    UpdateStockDialog.class.getName()
            );

    // Stores selected product ID
    private int productId;

    // Default constructor
    public UpdateStockDialog(java.awt.Frame parent, boolean modal) {

        // Calls parent constructor
        super(parent, modal);

        // Initializes all UI components
        initComponents();
    }

    // Constructor used when updating stock
    public UpdateStockDialog(
            java.awt.Frame parent,
            boolean modal,
            int id,
            String name,
            int currentStock
    ) {

        // Calls parent constructor
        super(parent, modal);

        // Initializes all UI components
        initComponents();

        // Stores selected product ID
        this.productId = id;

        // Displays product name
        lblProductName.setText(name);

        // Displays current stock
        txtNewStock.setText(
                String.valueOf(currentStock)
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNewStock = new javax.swing.JTextField();
        lblProductName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setModal(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnCancel.setBackground(new java.awt.Color(241, 245, 249));
        btnCancel.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        btnSave.setBackground(new java.awt.Color(227, 83, 10));
        btnSave.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save Stock");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(291, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addGap(14, 14, 14))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        jLabel1.setText("Updating Stock For");

        jLabel2.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        jLabel2.setText("New Stock");

        txtNewStock.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        txtNewStock.addActionListener(this::txtNewStockActionPerformed);

        lblProductName.setFont(new java.awt.Font("Geist Medium", 0, 14)); // NOI18N
        lblProductName.setText("Product Name");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNewStock)
                    .addComponent(lblProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblProductName))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNewStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(102, 102, 102)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNewStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNewStockActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // Closes dialog
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // Gets entered stock value
        String stockText
                = txtNewStock.getText().trim();

        // Validation:
        // Checks if stock field is empty
        if (stockText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a stock value.",
                    "Empty Field",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            // Converts stock text into integer value
            int newStock
                    = Integer.parseInt(stockText);

            // Validation:
            // Stock cannot be negative
            if (newStock < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Stock cannot be a negative number!",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Connects to database
            Connection conn
                    = DBConnection.getConnection();

            // SQL query:
            // If inventory record exists → UPDATE stock
            // If inventory record does not exist → INSERT new stock record
            String sql
                    = "IF EXISTS "
                    + "(SELECT 1 FROM Inventory WHERE product_id = ?) "
                    + " UPDATE Inventory "
                    + " SET current_stock = ? "
                    + " WHERE product_id = ? "
                    + "ELSE "
                    + " INSERT INTO Inventory "
                    + "(product_id, current_stock) "
                    + " VALUES (?, ?)";

            // Creates PreparedStatement
            PreparedStatement pstmt
                    = conn.prepareStatement(sql);

            // Checks if product exists
            pstmt.setInt(1, productId);

            // Updates stock quantity
            pstmt.setInt(2, newStock);

            // Selects correct product
            pstmt.setInt(3, productId);

            // Inserts product ID if inventory record does not exist
            pstmt.setInt(4, productId);

            // Inserts new stock quantity
            pstmt.setInt(5, newStock);

            // Executes UPDATE or INSERT query
            pstmt.executeUpdate();

            // Success message
            JOptionPane.showMessageDialog(
                    this,
                    "Stock updated successfully!"
            );

            // Closes dialog
            this.dispose();

        } catch (NumberFormatException e) {

            // Runs if stock input is not a whole number
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid whole number.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            // Prints error in console
            e.printStackTrace();

            // Shows database/system error
            JOptionPane.showMessageDialog(
                    this,
                    "An error occurred while updating the stock. Please try again.",
                    "System Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        // Opens dialog safely in Event Dispatch Thread
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                // Creates dialog window
                UpdateStockDialog dialog
                        = new UpdateStockDialog(
                                new javax.swing.JFrame(),
                                true
                        );

                // Closes application when dialog closes
                dialog.addWindowListener(
                        new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(
                            java.awt.event.WindowEvent e) {

                        System.exit(0);
                    }
                }
                );

                // Displays dialog
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JTextField txtNewStock;
    // End of variables declaration//GEN-END:variables
}
