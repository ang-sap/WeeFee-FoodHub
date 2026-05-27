package ui.menuPanel;

// Imports popup message dialogs
import javax.swing.JOptionPane;

// Imports table cell renderer
import javax.swing.table.DefaultTableCellRenderer;

// Imports Swing constants for alignment
import javax.swing.SwingConstants;

// Imports AddProduct dialog
import ui.dialogs.AddProductDialog;

// Imports LoginPanel to get logged-in user ID
import ui.auth.LoginPanel;

public class ProductsPanel extends javax.swing.JPanel {

    // Stores selected product ID
    private int productId;

    // Constructor
    // Runs when ProductsPanel is created
    public ProductsPanel() {

        // Initializes all UI components
        initComponents();

        // Loads products into table
        loadProducts("");

        // Styles the table header font
        tblProducts.getTableHeader().setFont(
                new java.awt.Font(
                        "Geist SemiBold",
                        java.awt.Font.PLAIN,
                        11
                )
        );

        // Changes table header background color
        tblProducts.getTableHeader().setBackground(
                new java.awt.Color(245, 245, 245)
        );

        // Changes table header text color
        tblProducts.getTableHeader().setForeground(
                new java.awt.Color(80, 80, 80)
        );

        // Removes spacing between table cells
        tblProducts.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        // Removes table grid lines
        tblProducts.setShowGrid(false);

        // Creates center alignment renderer
        DefaultTableCellRenderer centerRenderer
                = new DefaultTableCellRenderer();

        // Centers text horizontally
        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // Applies center alignment to all columns
        for (int i = 0; i < tblProducts.getColumnCount(); i++) {

            tblProducts.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }
    }

    // Loads product records from database
    public void loadProducts(String searchQuery) {

        // Checks if archived products should be shown
        boolean showArchived
                = chkShowArchived.isSelected();

        // Stores SQL query
        String sql;

        // ================= ARCHIVED PRODUCTS VIEW =================
        if (showArchived) {

            // SQL query:
            // Gets archived products only
            sql = "SELECT p.product_id, "
                    + "p.name AS product_name, "
                    + "c.category_name, "
                    + "p.price, "
                    + "ISNULL(i.current_stock, 0) "
                    + "AS current_stock "
                    + "FROM Products p "
                    + "JOIN Categories c "
                    + "ON p.category_id = c.category_id "
                    + "LEFT JOIN Inventory i "
                    + "ON p.product_id = i.product_id "
                    + "WHERE p.is_archived = 1 "
                    + "AND (p.name LIKE ? "
                    + "OR c.category_name LIKE ?) "
                    + "ORDER BY p.name ASC";

            // ================= ACTIVE PRODUCTS VIEW =================
        } else {

            // SQL query:
            // Gets active products using SQL View
            sql = "SELECT product_id, "
                    + "product_name, "
                    + "category_name, "
                    + "price, "
                    + "current_stock "
                    + "FROM vw_ProductList "
                    + "WHERE product_name LIKE ? "
                    + "OR category_name LIKE ? "
                    + "ORDER BY product_name ASC";
        }

        try (
                // Connects to database
                java.sql.Connection conn
                = database.DBConnection.getConnection(); // Creates PreparedStatement
                 java.sql.PreparedStatement pstmt
                = conn.prepareStatement(sql)) {

            // Adds wildcard search filter
            String searchParam
                    = "%" + searchQuery.trim() + "%";

            // Inserts search parameter
            pstmt.setString(1, searchParam);
            pstmt.setString(2, searchParam);

            try (
                    // Executes SELECT query
                    java.sql.ResultSet rs
                    = pstmt.executeQuery()) {

                // Gets table model
                javax.swing.table.DefaultTableModel model
                        = (javax.swing.table.DefaultTableModel) tblProducts.getModel();

                // Clears existing rows
                model.setRowCount(0);

                // Loops through product records
                while (rs.next()) {

                    // Adds row into table
                    model.addRow(new Object[]{
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category_name"),
                        rs.getDouble("price"),});
                }
            }

        } catch (Exception e) {

            // Prints error in console
            e.printStackTrace();
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
        java.awt.GridBagConstraints gridBagConstraints;

        cardContainer = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        chkShowArchived = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(248, 250, 252));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 25, 30, 25));
        setPreferredSize(new java.awt.Dimension(1042, 610));
        setLayout(new java.awt.GridBagLayout());

        cardContainer.setBackground(new java.awt.Color(255, 255, 255));
        cardContainer.setMaximumSize(new java.awt.Dimension(980, 610));
        cardContainer.setMinimumSize(new java.awt.Dimension(980, 610));
        cardContainer.setPreferredSize(new java.awt.Dimension(980, 610));
        cardContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(226, 232, 240)));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("Manage Menu Products");

        btnAdd.setBackground(new java.awt.Color(227, 83, 10));
        btnAdd.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add Product");
        btnAdd.setPreferredSize(new java.awt.Dimension(120, 35));
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnUpdate.setBackground(new java.awt.Color(241, 245, 249));
        btnUpdate.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnUpdate.setText("Edit");
        btnUpdate.setPreferredSize(new java.awt.Dimension(100, 35));
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnArchive.setBackground(new java.awt.Color(254, 226, 226));
        btnArchive.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnArchive.setForeground(new java.awt.Color(153, 27, 27));
        btnArchive.setText("Archived");
        btnArchive.setPreferredSize(new java.awt.Dimension(100, 35));
        btnArchive.addActionListener(this::btnArchiveActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 423, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnArchive, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnArchive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cardContainer.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 70));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(930, 400));

        tblProducts.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Category", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProducts.setRowHeight(35);
        jScrollPane2.setViewportView(tblProducts);

        cardContainer.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 470));

        chkShowArchived.setFont(new java.awt.Font("Geist SemiBold", 0, 10)); // NOI18N
        chkShowArchived.setText("Show Archived");
        chkShowArchived.addActionListener(this::chkShowArchivedActionPerformed);
        cardContainer.add(chkShowArchived, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 100, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(36, 31, 36, 31);
        add(cardContainer, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Gets current window
        java.awt.Window parentWindow
                = javax.swing.SwingUtilities
                        .getWindowAncestor(this);

        // Converts window into Frame
        java.awt.Frame parentFrame
                = (parentWindow instanceof java.awt.Frame)
                        ? (java.awt.Frame) parentWindow
                        : null;

        // Opens AddProductDialog
        AddProductDialog dialog
                = new AddProductDialog(
                        parentFrame,
                        true
                );

        // Centers dialog relative to parent frame
        dialog.setLocationRelativeTo(parentFrame);

        // Displays dialog
        dialog.setVisible(true);

        // Reloads updated product records
        loadProducts("");
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        // Gets selected row from table
        int selectedRow
                = tblProducts.getSelectedRow();

        // Validation:
        // Checks if user selected a row
        if (selectedRow == -1) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select a product first."
            );

            return;
        }

        // Gets selected product ID
        int productId
                = (int) tblProducts.getValueAt(
                        selectedRow,
                        0
                );

        // Gets selected product name
        String productName
                = (String) tblProducts.getValueAt(
                        selectedRow,
                        1
                );

        // Checks if currently viewing archived products
        boolean isArchivedView
                = chkShowArchived.isSelected();

        // Determines action text
        String actionWord
                = isArchivedView
                        ? "restore"
                        : "archive";

        // Determines audit log action
        String logAction
                = isArchivedView
                        ? "RESTORE_PRODUCT"
                        : "ARCHIVE_PRODUCT";

        // Determines archive status value
        int newArchiveStatus
                = isArchivedView
                        ? 0
                        : 1;

        // Confirmation dialog
        int confirm
                = javax.swing.JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to "
                        + actionWord
                        + " '"
                        + productName
                        + "'?",
                        "Confirm Action",
                        javax.swing.JOptionPane.YES_NO_OPTION
                );

        // Runs if user clicks YES
        if (confirm
                == javax.swing.JOptionPane.YES_OPTION) {

            try (
                    // Connects to database
                    java.sql.Connection conn
                    = database.DBConnection.getConnection()) {

                // ================= PRODUCT ARCHIVE UPDATE =================
                // SQL query:
                // Updates archive status
                String sql
                        = "UPDATE Products "
                        + "SET is_archived = ? "
                        + "WHERE product_id = ?";

                try (
                        // Creates PreparedStatement
                        java.sql.PreparedStatement pstmt
                        = conn.prepareStatement(sql)) {

                    // Updates archive status
                    pstmt.setInt(1, newArchiveStatus);

                    // Selects correct product
                    pstmt.setInt(2, productId);

                    // Executes UPDATE query
                    pstmt.executeUpdate();
                }

                // ================= AUDIT LOG =================
                // SQL query:
                // Calls stored procedure for audit logs
                String logSql
                        = "{call sp_InsertAuditLog(?, ?, ?)}";

                try (
                        // Creates CallableStatement
                        java.sql.CallableStatement cstmtLog
                        = conn.prepareCall(logSql)) {

                    // Inserts logged-in user ID
                    cstmtLog.setInt(
                            1,
                            LoginPanel.loggedInUserId
                    );

                    // Inserts action type
                    cstmtLog.setString(
                            2,
                            logAction
                    );

                    // Inserts audit description
                    cstmtLog.setString(
                            3,
                            actionWord.substring(0, 1)
                                    .toUpperCase()
                            + actionWord.substring(1)
                            + "d Product ID: "
                            + productId
                    );

                    // Executes stored procedure
                    cstmtLog.execute();
                }

                // Success message
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Product "
                        + actionWord
                        + "d successfully."
                );

                // Reloads updated product records
                loadProducts("");

            } catch (Exception e) {

                // Prints error in console
                e.printStackTrace();

                // Shows database/system error
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Error processing request: "
                        + e.getMessage()
                );
            }
        }
    }//GEN-LAST:event_btnArchiveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // Gets selected row from table
        int selectedRow
                = tblProducts.getSelectedRow();

        // Validation:
        // Checks if user selected a row
        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a product to edit first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Gets selected product information
        int id
                = (int) tblProducts.getValueAt(
                        selectedRow,
                        0
                );

        String name
                = (String) tblProducts.getValueAt(
                        selectedRow,
                        1
                );

        String category
                = (String) tblProducts.getValueAt(
                        selectedRow,
                        2
                );

        double price
                = (double) tblProducts.getValueAt(
                        selectedRow,
                        3
                );

        // Gets current window
        java.awt.Window parentWindow
                = javax.swing.SwingUtilities
                        .getWindowAncestor(this);

        // Converts window into Frame
        java.awt.Frame parentFrame
                = (parentWindow instanceof java.awt.Frame)
                        ? (java.awt.Frame) parentWindow
                        : null;

        // Opens EditProductDialog
        ui.dialogs.EditProductDialog dialog
                = new ui.dialogs.EditProductDialog(
                        parentFrame,
                        true,
                        id,
                        name,
                        category,
                        price
                );

        // Centers dialog relative to parent frame
        dialog.setLocationRelativeTo(parentFrame);

        // Displays dialog
        dialog.setVisible(true);

        // Reloads updated product records
        loadProducts("");
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void chkShowArchivedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowArchivedActionPerformed
        // Changes button text based on checkbox state
        if (chkShowArchived.isSelected()) {

            btnArchive.setText("Restore");

        } else {

            btnArchive.setText("Archive");
        }

        // Reloads products based on selected view
        loadProducts("");
    }//GEN-LAST:event_chkShowArchivedActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel cardContainer;
    private javax.swing.JCheckBox chkShowArchived;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblProducts;
    // End of variables declaration//GEN-END:variables
}
