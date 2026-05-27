package ui.menuPanel;

// Imports Swing constants for table alignment
import javax.swing.SwingConstants;

// Imports table cell renderer
import javax.swing.table.DefaultTableCellRenderer;

public class HomePanel extends javax.swing.JPanel {

    // Constructor
    // Runs when HomePanel is created
    public HomePanel() {

        // Initializes all UI components
        initComponents();

        // Styles the table header font
        tblLowStock.getTableHeader().setFont(
                new java.awt.Font(
                        "Geist SemiBold",
                        java.awt.Font.PLAIN,
                        11
                )
        );

        // Changes table header background color
        tblLowStock.getTableHeader().setBackground(
                new java.awt.Color(245, 245, 245)
        );

        // Changes table header text color
        tblLowStock.getTableHeader().setForeground(
                new java.awt.Color(80, 80, 80)
        );

        // Removes spacing between table cells
        tblLowStock.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        // Removes table grid lines
        tblLowStock.setShowGrid(false);

        // Creates center alignment renderer
        DefaultTableCellRenderer centerRenderer
                = new DefaultTableCellRenderer();

        // Centers text horizontally
        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // Applies center alignment to all columns
        for (int i = 0; i < tblLowStock.getColumnCount(); i++) {

            tblLowStock.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        // Loads dashboard data
        loadDashboardData();
    }

    // Loads dashboard statistics and low stock data
    public void loadDashboardData() {

        try {

            // Connects to database
            java.sql.Connection conn
                    = database.DBConnection.getConnection();

            // ================= TODAY'S REVENUE =================
            // SQL query:
            // Gets total revenue for today's completed transactions
            String sqlRev
                    = "SELECT ISNULL(SUM(total_amount), 0) "
                    + "AS today_rev "
                    + "FROM Transactions "
                    + "WHERE status != 'Voided' "
                    + "AND CAST(transaction_date AS DATE) "
                    + "= CAST(GETDATE() AS DATE)";

            // Executes revenue query
            java.sql.ResultSet rsRev
                    = conn.createStatement()
                            .executeQuery(sqlRev);

            // Displays today's revenue
            if (rsRev.next()) {

                lblTodayRevenue.setText(
                        String.format(
                                "%,.2f",
                                rsRev.getDouble("today_rev")
                        )
                );
            }

            // ================= PENDING RESTOCKS =================
            // SQL query:
            // Counts all pending purchase orders
            String sqlPending
                    = "SELECT COUNT(*) AS pending_count "
                    + "FROM Purchases "
                    + "WHERE status = 'Pending'";

            // Executes pending query
            java.sql.ResultSet rsPending
                    = conn.createStatement()
                            .executeQuery(sqlPending);

            // Displays pending restock count
            if (rsPending.next()) {

                lblPendingCount.setText(
                        String.valueOf(
                                rsPending.getInt("pending_count")
                        )
                );
            }

            // ================= LOW STOCK ITEMS =================
            // SQL query:
            // Gets products with stock less than or equal to 15
            String sqlLowStock
                    = "SELECT p.name AS ProductName, "
                    + "c.category_name AS Category, "
                    + "i.current_stock "
                    + "FROM Products p "
                    + "JOIN Categories c "
                    + "ON p.category_id = c.category_id "
                    + "JOIN Inventory i "
                    + "ON p.product_id = i.product_id "
                    + "WHERE i.current_stock <= 15 "
                    + "AND p.is_archived = 0 "
                    + "ORDER BY i.current_stock ASC";

            // Executes low stock query
            java.sql.ResultSet rsLow
                    = conn.createStatement()
                            .executeQuery(sqlLowStock);

            // Gets table model
            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel) tblLowStock.getModel();

            // Clears existing rows
            model.setRowCount(0);

            // Counter for low stock products
            int lowStockItemsCount = 0;

            // Loops through low stock products
            while (rsLow.next()) {

                // Adds product into table
                model.addRow(new Object[]{
                    rsLow.getString("ProductName"),
                    rsLow.getString("Category"),
                    rsLow.getInt("current_stock")
                });

                // Increases low stock counter
                lowStockItemsCount++;
            }

            // Displays low stock count
            lblLowStockCount.setText(
                    String.valueOf(lowStockItemsCount)
            );

            // Changes text color to red if there are low stock items
            if (lowStockItemsCount > 0) {

                lblLowStockCount.setForeground(
                        new java.awt.Color(220, 38, 38)
                );
            }

        } catch (Exception e) {

            // Prints error in console
            e.printStackTrace();

            // Displays dashboard loading error
            System.out.println(
                    "Dashboard Load Error: "
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
        java.awt.GridBagConstraints gridBagConstraints;

        cardContainer = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTodayRevenue = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblLowStockCount = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        lblPendingCount = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLowStock = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(248, 250, 252));
        setLayout(new java.awt.GridBagLayout());

        cardContainer.setBackground(new java.awt.Color(255, 255, 255));
        cardContainer.setMaximumSize(new java.awt.Dimension(980, 610));
        cardContainer.setMinimumSize(new java.awt.Dimension(980, 610));
        cardContainer.setPreferredSize(new java.awt.Dimension(980, 610));
        cardContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(226, 232, 240)));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("Welcome to WeeFee FoodHub!");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addContainerGap(753, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(20, 20, 20))
        );

        cardContainer.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel6.setPreferredSize(new java.awt.Dimension(300, 120));

        jLabel8.setFont(new java.awt.Font("Geist Medium", 0, 12)); // NOI18N
        jLabel8.setText("TODAY'S REVENUE");

        jLabel9.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        jLabel9.setText("₱");

        lblTodayRevenue.setFont(new java.awt.Font("Georgia", 0, 24)); // NOI18N
        lblTodayRevenue.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(2, 2, 2)
                        .addComponent(lblTodayRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTodayRevenue))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        cardContainer.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel7.setPreferredSize(new java.awt.Dimension(300, 120));

        jLabel10.setFont(new java.awt.Font("Geist Medium", 0, 12)); // NOI18N
        jLabel10.setText("LOW STOCK ITEMS");

        lblLowStockCount.setFont(new java.awt.Font("Georgia", 0, 24)); // NOI18N
        lblLowStockCount.setText("0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(lblLowStockCount, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLowStockCount)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        cardContainer.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel8.setPreferredSize(new java.awt.Dimension(300, 120));

        lblPendingCount.setFont(new java.awt.Font("Georgia", 0, 24)); // NOI18N
        lblPendingCount.setText("0");

        jLabel13.setFont(new java.awt.Font("Geist Medium", 0, 12)); // NOI18N
        jLabel13.setText("PENDING RESTOCKS");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(lblPendingCount, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPendingCount)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        cardContainer.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, -1, -1));

        tblLowStock.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        tblLowStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Product Name", "Category", "Current Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLowStock.setRowHeight(35);
        jScrollPane1.setViewportView(tblLowStock);

        cardContainer.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 920, 300));

        jLabel12.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel12.setText("Critical Stock Alerts");
        cardContainer.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(cardContainer, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardContainer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLowStockCount;
    private javax.swing.JLabel lblPendingCount;
    private javax.swing.JLabel lblTodayRevenue;
    private javax.swing.JTable tblLowStock;
    // End of variables declaration//GEN-END:variables
}
