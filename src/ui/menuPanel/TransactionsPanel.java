package ui.menuPanel;

// Imports Swing constants for alignment
import javax.swing.SwingConstants;

// Imports table cell renderer
import javax.swing.table.DefaultTableCellRenderer;

// Imports LoginPanel to get logged-in user ID
import ui.auth.LoginPanel;

public class TransactionsPanel extends javax.swing.JPanel {

    // Constructor
    // Runs when TransactionsPanel is created
    public TransactionsPanel() {

        // Initializes all UI components
        initComponents();

        // Placeholder text for search field
        txtSearchLogs.putClientProperty(
                "JTextField.placeholderText",
                "Search by Receipt ID or Cashier Name"
        );

        // Styles the table header font
        tblTransactions.getTableHeader().setFont(
                new java.awt.Font(
                        "Geist SemiBold",
                        java.awt.Font.PLAIN,
                        10
                )
        );

        // Changes table header background color
        tblTransactions.getTableHeader().setBackground(
                new java.awt.Color(245, 245, 245)
        );

        // Changes table header text color
        tblTransactions.getTableHeader().setForeground(
                new java.awt.Color(80, 80, 80)
        );

        // Removes spacing between table cells
        tblTransactions.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        // Removes table grid lines
        tblTransactions.setShowGrid(false);

        // Creates center alignment renderer
        DefaultTableCellRenderer centerRenderer
                = new DefaultTableCellRenderer();

        // Centers text horizontally
        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // Applies center alignment to all columns
        for (int i = 0; i < tblTransactions.getColumnCount(); i++) {

            tblTransactions.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        // ================= LIVE SEARCH LISTENER =================
        // Automatically reloads transactions while typing
        txtSearchLogs.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

            @Override
            public void changedUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                loadTransactions(txtSearchLogs.getText());
            }

            @Override
            public void removeUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                loadTransactions(txtSearchLogs.getText());
            }

            @Override
            public void insertUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                loadTransactions(txtSearchLogs.getText());
            }
        });

        // Loads all transactions initially
        loadTransactions("");
    }

    // Loads transaction records from database
    public void loadTransactions(String searchQuery) {

        // SQL query:
        // Gets transaction history with cashier username
        String sql
                = "SELECT t.transaction_id, "
                + "t.transaction_date, "
                + "u.username, "
                + "t.total_amount, "
                + "t.status "
                + "FROM Transactions t "
                + "INNER JOIN Users u "
                + "ON t.user_id = u.user_id "
                + "WHERE u.username LIKE ? "
                + "OR CAST(t.transaction_id AS VARCHAR) LIKE ? "
                + "ORDER BY t.transaction_id DESC";

        try (
                // Connects to database
                java.sql.Connection conn
                = database.DBConnection.getConnection(); // Creates PreparedStatement
                 java.sql.PreparedStatement pstmt
                = conn.prepareStatement(sql)) {

            // Adds wildcard search filter
            String searchParam
                    = "%" + searchQuery.trim() + "%";

            // Inserts search parameters
            pstmt.setString(1, searchParam);
            pstmt.setString(2, searchParam);

            try (
                    // Executes SELECT query
                    java.sql.ResultSet rs
                    = pstmt.executeQuery()) {

                // Gets table model
                javax.swing.table.DefaultTableModel model
                        = (javax.swing.table.DefaultTableModel) tblTransactions.getModel();

                // Clears existing rows
                model.setRowCount(0);

                // Date format for transaction timestamp
                java.text.SimpleDateFormat sdf
                        = new java.text.SimpleDateFormat(
                                "MMM dd, yyyy - hh:mm a"
                        );

                // Loops through transaction records
                while (rs.next()) {

                    // Adds row into table
                    model.addRow(new Object[]{
                        rs.getInt("transaction_id"),
                        // Formats date and time
                        sdf.format(
                        rs.getTimestamp("transaction_date")
                        ),
                        rs.getString("username"),
                        rs.getDouble("total_amount"),
                        rs.getString("status")
                    });
                }
            }

        } catch (Exception e) {

            // Prints error in console
            e.printStackTrace();

            // Shows database/system error
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Error loading transactions: "
                    + e.getMessage()
            );
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

        cardContainer = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnViewDetails = new javax.swing.JButton();
        btnVoidSale = new javax.swing.JButton();
        txtSearchLogs = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransactions = new javax.swing.JTable();

        setBackground(new java.awt.Color(248, 250, 252));
        setLayout(new java.awt.GridBagLayout());

        cardContainer.setBackground(new java.awt.Color(255, 255, 255));
        cardContainer.setMaximumSize(new java.awt.Dimension(980, 610));
        cardContainer.setMinimumSize(new java.awt.Dimension(980, 610));
        cardContainer.setPreferredSize(new java.awt.Dimension(980, 610));
        cardContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(226, 232, 240)));
        jPanel2.setPreferredSize(new java.awt.Dimension(980, 70));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("Sales History");

        btnViewDetails.setBackground(new java.awt.Color(231, 90, 14));
        btnViewDetails.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnViewDetails.setForeground(new java.awt.Color(255, 255, 255));
        btnViewDetails.setText("View Details");
        btnViewDetails.setPreferredSize(new java.awt.Dimension(113, 30));
        btnViewDetails.addActionListener(this::btnViewDetailsActionPerformed);

        btnVoidSale.setBackground(new java.awt.Color(254, 226, 226));
        btnVoidSale.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnVoidSale.setForeground(new java.awt.Color(153, 27, 27));
        btnVoidSale.setText("Void");
        btnVoidSale.setPreferredSize(new java.awt.Dimension(113, 30));
        btnVoidSale.addActionListener(this::btnVoidSaleActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 620, Short.MAX_VALUE)
                .addComponent(btnViewDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVoidSale, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoidSale, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(13, 13, 13))
        );

        cardContainer.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, -1));

        txtSearchLogs.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        txtSearchLogs.setToolTipText("Search by Receipt ID or Cashier Name");
        txtSearchLogs.addActionListener(this::txtSearchLogsActionPerformed);
        cardContainer.add(txtSearchLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 260, 30));

        btnSearch.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);
        cardContainer.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 100, 30));

        tblTransactions.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        tblTransactions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Receipt ID", "Date & Time", "Cashier", "Total Amount", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTransactions.setRowHeight(35);
        jScrollPane1.setViewportView(tblTransactions);

        cardContainer.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 940, 460));

        add(cardContainer, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // Reloads transactions using search text
        loadTransactions(txtSearchLogs.getText());
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnViewDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDetailsActionPerformed
        // Gets selected row from table
        int selectedRow
                = tblTransactions.getSelectedRow();

        // Validation:
        // Checks if user selected a row
        if (selectedRow == -1) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select a transaction to view.",
                    "No Selection",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Gets selected transaction ID
        int transactionId
                = (int) tblTransactions.getValueAt(
                        selectedRow,
                        0
                );

        try {

            // Connects to database
            java.sql.Connection conn
                    = database.DBConnection.getConnection();

            // ================= TRANSACTION HEADER =================
            // SQL query:
            // Gets transaction header information
            String sqlHeader
                    = "SELECT u.username, "
                    + "t.total_amount, "
                    + "t.cash_tendered, "
                    + "t.transaction_date "
                    + "FROM Transactions t "
                    + "INNER JOIN Users u "
                    + "ON t.user_id = u.user_id "
                    + "WHERE t.transaction_id = ?";

            // Creates PreparedStatement
            java.sql.PreparedStatement pstmtHeader
                    = conn.prepareStatement(sqlHeader);

            // Selects correct transaction
            pstmtHeader.setInt(1, transactionId);

            // Executes SELECT query
            java.sql.ResultSet rsHeader
                    = pstmtHeader.executeQuery();

            // Default values
            String cashierName = "Unknown";
            double totalAmount = 0.0;
            double cashReceived = 0.0;
            String date = "";

            // Gets transaction information
            if (rsHeader.next()) {

                cashierName
                        = rsHeader.getString("username");

                totalAmount
                        = rsHeader.getDouble("total_amount");

                cashReceived
                        = rsHeader.getDouble("cash_tendered");

                // Formats transaction date
                date = new java.text.SimpleDateFormat(
                        "MMM dd, yyyy hh:mm a"
                ).format(
                        rsHeader.getTimestamp("transaction_date")
                );
            }

            // Calculates customer change
            double change
                    = cashReceived - totalAmount;

            // Calculates VATable sales
            double vatableSales
                    = totalAmount / 1.12;

            // Calculates VAT amount
            double vatAmount
                    = totalAmount - vatableSales;

            // Creates receipt text
            StringBuilder receipt
                    = new StringBuilder();

            // ================= RECEIPT HEADER =================
            receipt.append("==========================================\n");
            receipt.append("              WEEFEE FOODHUB\n");
            receipt.append("        1878 Tayuman St. Sta. Cruz\n");
            receipt.append("              Manila Philippines\n");
            receipt.append("------------------------------------------\n");
            receipt.append("              SALES INVOICE\n");
            receipt.append("TIN: 123-456-789-000\n");
            receipt.append("VAT REG TIN\n");
            receipt.append("MIN: 24010123456789012\n");
            receipt.append("==========================================\n");

            receipt.append(
                    String.format(
                            "Receipt No : %d\n",
                            transactionId
                    )
            );

            receipt.append(
                    String.format(
                            "Date       : %s\n",
                            date
                    )
            );

            receipt.append(
                    String.format(
                            "Cashier    : %s\n",
                            cashierName
                    )
            );

            receipt.append("------------------------------------------\n");

            receipt.append(
                    String.format(
                            "%-22s %-5s %11s\n",
                            "ITEM",
                            "QTY",
                            "TOTAL"
                    )
            );

            receipt.append("------------------------------------------\n");

            // ================= RECEIPT ITEMS =================
            // SQL query:
            // Gets purchased items
            String sqlItems
                    = "SELECT p.name, "
                    + "td.quantity, "
                    + "td.selling_price, "
                    + "(td.quantity * td.selling_price) "
                    + "AS subtotal "
                    + "FROM Transaction_Details td "
                    + "INNER JOIN Products p "
                    + "ON td.product_id = p.product_id "
                    + "WHERE td.transaction_id = ?";

            // Creates PreparedStatement
            java.sql.PreparedStatement pstmtItems
                    = conn.prepareStatement(sqlItems);

            // Selects correct transaction
            pstmtItems.setInt(1, transactionId);

            // Executes SELECT query
            java.sql.ResultSet rsItems
                    = pstmtItems.executeQuery();

            // Loops through purchased items
            while (rsItems.next()) {

                // Gets product name
                String rawName
                        = rsItems.getString("name");

                // Shortens long product names
                String itemName
                        = rawName.length() > 18
                        ? rawName.substring(0, 18)
                        : rawName;

                // Gets quantity
                int qty
                        = rsItems.getInt("quantity");

                // Gets subtotal
                double subtotal
                        = rsItems.getDouble("subtotal");

                // Adds item into receipt
                receipt.append(
                        String.format(
                                "%-22s %-5d   ₱%8.2f\n",
                                itemName,
                                qty,
                                subtotal
                        )
                );
            }

            receipt.append("------------------------------------------\n");

            // Displays VATable sales
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "VATable Sales",
                            vatableSales
                    )
            );

            // Displays VAT amount
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "VAT Amount",
                            vatAmount
                    )
            );

            // Displays total amount
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "TOTAL",
                            totalAmount
                    )
            );

            // Displays customer cash payment
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "CASH",
                            cashReceived
                    )
            );

            // Displays customer change
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "CHANGE",
                            change
                    )
            );

            receipt.append("==========================================\n");
            receipt.append("      THIS SERVES AS YOUR SALES INVOICE   \n");
            receipt.append("            THANK YOU! COME AGAIN         \n");
            receipt.append("==========================================\n");

            // ================= RECEIPT DISPLAY =================
            // Creates text area for receipt
            javax.swing.JTextArea txtReceipt
                    = new javax.swing.JTextArea(
                            receipt.toString()
                    );

            // Receipt font style
            txtReceipt.setFont(
                    new java.awt.Font(
                            "Monospaced",
                            java.awt.Font.BOLD,
                            14
                    )
            );

            // Prevents editing
            txtReceipt.setEditable(false);

            // White background
            txtReceipt.setBackground(
                    new java.awt.Color(255, 255, 255)
            );

            // Dark text color
            txtReceipt.setForeground(
                    new java.awt.Color(15, 23, 42)
            );

            // Adds padding
            txtReceipt.setMargin(
                    new java.awt.Insets(20, 20, 20, 20)
            );

            // Creates scroll pane for receipt
            javax.swing.JScrollPane scrollPane
                    = new javax.swing.JScrollPane(
                            txtReceipt
                    );

            // Receipt dialog size
            scrollPane.setPreferredSize(
                    new java.awt.Dimension(400, 500)
            );

            // Adds border
            scrollPane.setBorder(
                    javax.swing.BorderFactory.createLineBorder(
                            new java.awt.Color(226, 232, 240)
                    )
            );

            // Displays receipt dialog
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Digital Receipt",
                    javax.swing.JOptionPane.PLAIN_MESSAGE
            );

        } catch (Exception e) {

            // Prints error in console
            e.printStackTrace();

            // Shows database/system error
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Error loading receipt details: "
                    + e.getMessage(),
                    "Database Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnViewDetailsActionPerformed

    private void btnVoidSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoidSaleActionPerformed
        // Gets selected row from table
        int selectedRow
                = tblTransactions.getSelectedRow();

        // Validation:
        // Checks if user selected a row
        if (selectedRow == -1) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select a transaction to void.",
                    "No Selection",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Gets selected transaction ID
        int transactionId
                = (int) tblTransactions.getValueAt(
                        selectedRow,
                        0
                );

        // Gets current transaction status
        String currentStatus
                = (String) tblTransactions.getValueAt(
                        selectedRow,
                        4
                );

        // Validation:
        // Checks if transaction is already voided
        if ("VOIDED".equalsIgnoreCase(currentStatus)) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "This transaction has already been voided.",
                    "Already Voided",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        // Confirmation dialog
        int confirm
                = javax.swing.JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to VOID Receipt #"
                        + transactionId
                        + "?\nThis will restore the items to inventory.",
                        "Confirm Void",
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );

        // Stops process if user clicks NO
        if (confirm
                != javax.swing.JOptionPane.YES_OPTION) {

            return;
        }

        java.sql.Connection conn = null;

        try {

            // Connects to database
            conn = database.DBConnection.getConnection();

            // Starts transaction mode
            // All queries must succeed together
            conn.setAutoCommit(false);

            // ================= TRANSACTION VOID =================
            // SQL query:
            // Updates transaction status into VOIDED
            String sqlVoid
                    = "UPDATE Transactions "
                    + "SET status = 'VOIDED' "
                    + "WHERE transaction_id = ?";

            try (
                    // Creates PreparedStatement
                    java.sql.PreparedStatement pstmtVoid
                    = conn.prepareStatement(sqlVoid)) {

                // Selects correct transaction
                pstmtVoid.setInt(1, transactionId);

                // Executes UPDATE query
                pstmtVoid.executeUpdate();
            }

            // ================= INVENTORY RESTORE =================
            // SQL query:
            // Gets sold items from transaction
            String sqlGetItems
                    = "SELECT product_id, quantity "
                    + "FROM Transaction_Details "
                    + "WHERE transaction_id = ?";

            // SQL query:
            // Restores stock back into inventory
            String sqlRestore
                    = "UPDATE Inventory "
                    + "SET current_stock = current_stock + ? "
                    + "WHERE product_id = ?";

            try (
                    // Creates PreparedStatement
                    java.sql.PreparedStatement pstmtGetItems
                    = conn.prepareStatement(sqlGetItems); java.sql.PreparedStatement pstmtRestore
                    = conn.prepareStatement(sqlRestore)) {

                // Selects correct transaction
                pstmtGetItems.setInt(1, transactionId);

                try (
                        // Executes SELECT query
                        java.sql.ResultSet rsItems
                        = pstmtGetItems.executeQuery()) {

                    // Loops through sold items
                    while (rsItems.next()) {

                        // Gets product ID
                        int productId
                                = rsItems.getInt("product_id");

                        // Gets sold quantity
                        int qtyToReturn
                                = rsItems.getInt("quantity");

                        // Restores stock quantity
                        pstmtRestore.setInt(1, qtyToReturn);

                        // Selects correct product
                        pstmtRestore.setInt(2, productId);

                        // Adds query into batch
                        pstmtRestore.addBatch();
                    }
                }

                // Executes all inventory updates together
                pstmtRestore.executeBatch();
            }

            // ================= AUDIT LOG =================
            // SQL query:
            // Calls stored procedure for audit logs
            String sqlLog
                    = "{call sp_InsertAuditLog(?, ?, ?)}";

            try (
                    // Creates CallableStatement
                    java.sql.CallableStatement cstmtLog
                    = conn.prepareCall(sqlLog)) {

                // Inserts logged-in user ID
                cstmtLog.setInt(
                        1,
                        LoginPanel.loggedInUserId
                );

                // Inserts action type
                cstmtLog.setString(
                        2,
                        "VOID_SALE"
                );

                // Inserts audit description
                cstmtLog.setString(
                        3,
                        "Voided Transaction ID: "
                        + transactionId
                        + " and restored inventory."
                );

                // Executes stored procedure
                cstmtLog.execute();
            }

            // Saves all database changes permanently
            conn.commit();

            // Success message
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Transaction #"
                    + transactionId
                    + " successfully voided.\n"
                    + "Inventory has been restored.",
                    "Void Successful",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            // Reloads updated transaction records
            loadTransactions(txtSearchLogs.getText());

        } catch (Exception e) {

            try {

                // Cancels all database changes if an error happens
                if (conn != null) {

                    conn.rollback();
                }

            } catch (Exception ex) {

            }

            // Prints error in console
            e.printStackTrace();

            // Shows database/system error
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Error voiding transaction: "
                    + e.getMessage(),
                    "Database Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

        } finally {

            if (conn != null) {

                try {

                    // Turns auto-commit back on
                    conn.setAutoCommit(true);

                } catch (Exception ex) {

                }

                try {

                    // Closes database connection
                    conn.close();

                } catch (Exception ex) {

                }
            }
        }
    }//GEN-LAST:event_btnVoidSaleActionPerformed

    private void txtSearchLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchLogsActionPerformed
        // Reloads transactions using search text
        loadTransactions(txtSearchLogs.getText());
    }//GEN-LAST:event_txtSearchLogsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JButton btnVoidSale;
    private javax.swing.JPanel cardContainer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTransactions;
    private javax.swing.JTextField txtSearchLogs;
    // End of variables declaration//GEN-END:variables
}
