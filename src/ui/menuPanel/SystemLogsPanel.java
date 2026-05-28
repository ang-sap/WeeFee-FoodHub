package ui.menuPanel;

// Imports Swing constants for alignment
import javax.swing.SwingConstants;

// Imports table cell renderer
import javax.swing.table.DefaultTableCellRenderer;

public class SystemLogsPanel extends javax.swing.JPanel {

    // Constructor
    // Runs when SystemLogsPanel is created
    public SystemLogsPanel() {

        // Initializes all UI components
        initComponents();

        // Placeholder text for search field
        txtSearchLogs.putClientProperty(
                "JTextField.placeholderText",
                "Search by action, user, or details..."
        );

        // Styles the table header font
        tblLogs.getTableHeader().setFont(
                new java.awt.Font(
                        "Geist SemiBold",
                        java.awt.Font.PLAIN,
                        12
                )
        );

        // Changes table header background color
        tblLogs.getTableHeader().setBackground(
                new java.awt.Color(245, 245, 245)
        );

        // Changes table header text color
        tblLogs.getTableHeader().setForeground(
                new java.awt.Color(80, 80, 80)
        );

        // Removes spacing between table cells
        tblLogs.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        // Removes table grid lines
        tblLogs.setShowGrid(false);

        // Creates center alignment renderer
        DefaultTableCellRenderer centerRenderer
                = new DefaultTableCellRenderer();

        // Centers text horizontally
        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // Applies center alignment to all columns
        for (int i = 0; i < tblLogs.getColumnCount(); i++) {

            tblLogs.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        // ================= LIVE SEARCH LISTENER =================

        // Automatically reloads logs while typing
        txtSearchLogs.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

            @Override
            public void changedUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                loadLogs(txtSearchLogs.getText());
            }

            @Override
            public void removeUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                loadLogs(txtSearchLogs.getText());
            }

            @Override
            public void insertUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                loadLogs(txtSearchLogs.getText());
            }
        });

        // Loads all logs initially
        loadLogs("");
    }

    // Loads audit logs from database
    public void loadLogs(String searchQuery) {

        try {

            // Connects to database
            java.sql.Connection conn
                    = database.DBConnection.getConnection();

            // SQL query:
            // Gets audit logs with username information
            String sql
                    = "SELECT a.log_id, "
                    + "a.log_date, "
                    + "ISNULL(u.username, 'System') "
                    + "AS username, "
                    + "a.action, "
                    + "a.description "
                    + "FROM AuditLogs a "
                    + "LEFT JOIN Users u "
                    + "ON a.user_id = u.user_id "
                    + "WHERE a.action LIKE ? "
                    + "OR a.description LIKE ? "
                    + "OR ISNULL(u.username, '') LIKE ? "
                    + "ORDER BY a.log_id DESC";

            // Creates PreparedStatement
            java.sql.PreparedStatement pstmt
                    = conn.prepareStatement(sql);

            // Adds wildcard search filter
            String searchParam
                    = "%" + searchQuery.trim() + "%";

            // Inserts search parameters
            pstmt.setString(1, searchParam);
            pstmt.setString(2, searchParam);
            pstmt.setString(3, searchParam);

            // Executes SELECT query
            java.sql.ResultSet rs
                    = pstmt.executeQuery();

            // Gets table model
            javax.swing.table.DefaultTableModel model
                    = (javax.swing.table.DefaultTableModel)
                            tblLogs.getModel();

            // Clears existing rows
            model.setRowCount(0);

            // Date format for log timestamp
            java.text.SimpleDateFormat sdf
                    = new java.text.SimpleDateFormat(
                            "MMM dd, yyyy - hh:mm a"
                    );

            // Loops through audit log records
            while (rs.next()) {

                // Adds row into table
                model.addRow(new Object[]{
                    rs.getInt("log_id"),

                    // Formats date and time
                    sdf.format(
                            rs.getTimestamp("log_date")
                    ),

                    rs.getString("username"),
                    rs.getString("action"),

                    // Displays blank if description is null
                    rs.getString("description") != null
                    ? rs.getString("description")
                    : ""
                });
            }

        } catch (Exception e) {

            // Prints error in console
            e.printStackTrace();

            // Shows database/system error
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Unable to fetch system logs. Please contact the administrator.",
                    "System Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearchLogs = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLogs = new javax.swing.JTable();

        setBackground(new java.awt.Color(248, 250, 252));
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(980, 610));
        jPanel1.setMinimumSize(new java.awt.Dimension(980, 610));
        jPanel1.setPreferredSize(new java.awt.Dimension(980, 610));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(226, 232, 240)));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(15, 23, 42));
        jLabel1.setText("System Activity Logs");

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

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        txtSearchLogs.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        txtSearchLogs.addActionListener(this::txtSearchLogsActionPerformed);
        jPanel1.add(txtSearchLogs, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 250, 30));

        btnSearch.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 100, 30));

        tblLogs.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        tblLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Log ID", "Timestamp", "User", "Action", "Details"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tblLogs.setRowHeight(35);
        jScrollPane1.setViewportView(tblLogs);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 920, 450));

        add(jPanel1, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchLogsActionPerformed
        // Reloads logs using search text
        loadLogs(txtSearchLogs.getText());
    }//GEN-LAST:event_txtSearchLogsActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // Reloads logs using search text
        loadLogs(txtSearchLogs.getText());
    }//GEN-LAST:event_btnSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLogs;
    private javax.swing.JTextField txtSearchLogs;
    // End of variables declaration//GEN-END:variables
}
