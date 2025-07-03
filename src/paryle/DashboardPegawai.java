/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paryle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;
/**
 *
 * @author adi
 */
public class DashboardPegawai extends javax.swing.JFrame {

    // Konstanta harga kamar
    private final double SINGLE_ROOM_PRICE = 100000;
    private final double DOUBLE_ROOM_PRICE = 150000;
    private final double SUITE_ROOM_PRICE = 300000;
    private final double DELUXE_ROOM_PRICE = 250000;

    private int selectedCustomerId = -1; // Menyimpan ID customer yang dipilih untuk diedit/dihapus
    private String originalRoomIdBooked = null; // Menyimpan ID kamar asli saat mode edit

    /**
     * Creates new form DashboardPegawai
     */
  public DashboardPegawai() {
        initComponents();
      // Inisialisasi item JComboBox
        if (jComboBoxTipeKamar != null && jComboBoxTipeKamar.getItemCount() == 0) {
            jComboBoxTipeKamar.addItem("pilih"); // Default placeholder
            jComboBoxTipeKamar.addItem("Single Room");
            jComboBoxTipeKamar.addItem("Double Room");
            jComboBoxTipeKamar.addItem("Suite Room");
            jComboBoxTipeKamar.addItem("Deluxe Room");
        }
        clearFields(); // Bersihkan field saat pertama kali dibuka
        loadCustomerData(""); // Muat semua data customer saat dashboard pegawai dibuka (tanpa filter awal)

        // Tambahkan listener untuk pemilihan baris di tabel
        jTableCustomer.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTableCustomer.getSelectedRow() != -1) {
                int selectedRow = jTableCustomer.getSelectedRow();
                selectedCustomerId = (int) jTableCustomer.getValueAt(selectedRow, 0); // Ambil ID customer
                
                jTextFieldNama.setText(jTableCustomer.getValueAt(selectedRow, 1).toString());
                jTextFieldNIK.setText(jTableCustomer.getValueAt(selectedRow, 2).toString());
                jComboBoxTipeKamar.setSelectedItem(jTableCustomer.getValueAt(selectedRow, 3).toString());
                originalRoomIdBooked = jTableCustomer.getValueAt(selectedRow, 4) != null ? jTableCustomer.getValueAt(selectedRow, 4).toString() : null; // Ambil ID Kamar Dibooking asli
                jTextFieldLamaMenginap.setText(jTableCustomer.getValueAt(selectedRow, 5).toString());
                jTextFieldTotalHarga.setText(jTableCustomer.getValueAt(selectedRow, 6).toString());
                jTextFieldUangBayar.setText(""); // Kosongkan uang bayar dan kembali untuk input baru saat edit
                jTextFieldUangKembali.setText("");

                // Aktifkan tombol Edit dan Hapus
                jButtonEdit.setEnabled(true);
                jButtonHapus.setEnabled(true);
                jButtonBooking.setEnabled(false); // Nonaktifkan tombol booking saat mode edit
            } else {
                clearFields(); // Bersihkan field jika tidak ada baris yang dipilih atau seleksi dibatalkan
            }
        });
    }

    // Method untuk memuat data customer ke dalam tabel dengan parameter pencarian
    private void loadCustomerData(String searchTerm) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("NIK");
        model.addColumn("Tipe Kamar");
        model.addColumn("ID Kamar Dibooking"); // Tambahkan kolom baru
        model.addColumn("Lama Menginap");
        model.addColumn("Total Bayar");
        model.addColumn("Tanggal Booking");

        Connection conn = null; // Deklarasikan conn di luar try
        PreparedStatement pst = null; // Deklarasikan pst di luar try
        ResultSet rs = null; // Deklarasikan rs di luar try

        try {
            conn = Koneksi.configDB();
            String sql = "SELECT id, nama, nik, tipe_kamar, id_kamar_booked, lama_menginap, total_bayar, tanggal_booking FROM customers"; // Ambil juga kolom id_kamar_booked dan tanggal_booking
            
            // Tambahkan kondisi WHERE jika ada searchTerm
            if (!searchTerm.isEmpty()) {
                sql += " WHERE nama LIKE ? OR nik LIKE ? OR id_kamar_booked LIKE ?";
            }
            
            pst = conn.prepareStatement(sql);

            if (!searchTerm.isEmpty()) {
                pst.setString(1, "%" + searchTerm + "%");
                pst.setString(2, "%" + searchTerm + "%");
                pst.setString(3, "%" + searchTerm + "%");
            }

            rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("nik"),
                    rs.getString("tipe_kamar"),
                    rs.getString("id_kamar_booked"), // Ambil data id_kamar_booked
                    rs.getInt("lama_menginap"),
                    rs.getDouble("total_bayar"),
                    rs.getTimestamp("tanggal_booking")
                });
            }
            jTableCustomer.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saat memuat data customer: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Pastikan semua resource ditutup di blok finally
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close(); // Tutup koneksi di sini
            } catch (SQLException closeEx) {
                System.err.println("Error closing resources in loadCustomerData: " + closeEx.getMessage());
            }
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
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNama = new javax.swing.JTextField();
        jTextFieldNIK = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldTotalHarga = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldUangBayar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldUangKembali = new javax.swing.JTextField();
        jButtonBooking = new javax.swing.JButton();
        jTextFieldLamaMenginap = new javax.swing.JTextField();
        jComboBoxTipeKamar = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaKeterangan = new javax.swing.JTextArea();
        jButtonKeluar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCustomer = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldCari = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButtonCari = new javax.swing.JButton();
        jButtonEdit = new javax.swing.JButton();
        jButtonHapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(21, 62, 84));

        jLabel1.setFont(new java.awt.Font("Felix Titling", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BOOKING HOTEL");

        jPanel3.setBackground(new java.awt.Color(30, 89, 120));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama");

        jTextFieldNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNamaActionPerformed(evt);
            }
        });

        jTextFieldNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNIKActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("NIK");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Total Harga");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Pilih Tipe Kamar");

        jTextFieldTotalHarga.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Uang Bayar");

        jTextFieldUangBayar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextFieldUangBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUangBayarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Uang Kembali");

        jTextFieldUangKembali.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jButtonBooking.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonBooking.setText("Booking");
        jButtonBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBookingActionPerformed(evt);
            }
        });

        jTextFieldLamaMenginap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextFieldLamaMenginap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLamaMenginapActionPerformed(evt);
            }
        });

        jComboBoxTipeKamar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBoxTipeKamar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pilih", "Single Room", "Double Room", "Suite Room", "Deluxe Room", " " }));
        jComboBoxTipeKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipeKamarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Lama Menginap (Hari)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldUangKembali)
                    .addComponent(jTextFieldUangBayar)
                    .addComponent(jTextFieldTotalHarga)
                    .addComponent(jTextFieldNIK)
                    .addComponent(jTextFieldNama)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldLamaMenginap)
                    .addComponent(jComboBoxTipeKamar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 67, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNIK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldLamaMenginap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTipeKamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldUangBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldUangKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jButtonBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextAreaKeterangan.setColumns(20);
        jTextAreaKeterangan.setRows(5);
        jScrollPane1.setViewportView(jTextAreaKeterangan);

        jButtonKeluar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonKeluar.setText("Keluar");
        jButtonKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKeluarActionPerformed(evt);
            }
        });

        jTableCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nama", "NIK", "Tipe Kamar", "Lama", "Total Bayar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCustomerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableCustomer);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Data Customer");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Keterangan");

        jTextFieldCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCariActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Cari ");

        jButtonCari.setText("Cari");
        jButtonCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCariActionPerformed(evt);
            }
        });

        jButtonEdit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonEdit.setText("Edit");
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });

        jButtonHapus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonHapus.setText("Hapus");
        jButtonHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel4))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldCari, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonCari))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonEdit)
                        .addGap(80, 80, 80)
                        .addComponent(jButtonHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonKeluar)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jButtonCari))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonKeluar)
                    .addComponent(jButtonHapus)
                    .addComponent(jButtonEdit))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNamaActionPerformed

    private void jTextFieldNIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNIKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNIKActionPerformed

    private void jComboBoxTipeKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipeKamarActionPerformed
        updateKeteranganAndTotal();
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipeKamarActionPerformed

    private void jButtonBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBookingActionPerformed
        saveNewBooking();
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBookingActionPerformed

    private void jButtonKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKeluarActionPerformed
        new Login().setVisible(true); // Kembali ke Login
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonKeluarActionPerformed

    private void jTextFieldLamaMenginapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLamaMenginapActionPerformed
        updateKeteranganAndTotal();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLamaMenginapActionPerformed

    private void jTextFieldUangBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUangBayarActionPerformed
        updateUangKembali();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUangBayarActionPerformed

    private void jTextFieldCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCariActionPerformed
         jButtonCariActionPerformed(evt);
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCariActionPerformed

    private void jButtonCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCariActionPerformed
        String searchTerm = jTextFieldCari.getText().trim();
        loadCustomerData(searchTerm);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCariActionPerformed

    private void jButtonHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHapusActionPerformed
        deleteBooking();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonHapusActionPerformed

    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed
        editBooking();
            // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEditActionPerformed

    private void jTableCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCustomerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableCustomerMouseClicked

   // --- Logic Methods ---
    private void updateKeteranganAndTotal() {
        String selectedRoomType = (String) jComboBoxTipeKamar.getSelectedItem();
        int lamaMenginap = 0;
        try {
            String lamaMenginapText = jTextFieldLamaMenginap.getText().trim();
            if (lamaMenginapText.isEmpty() || !lamaMenginapText.matches("\\d+")) {
                jTextFieldTotalHarga.setText("Input Tidak Valid");
                jTextAreaKeterangan.setText("Lama Menginap harus berupa angka.");
                jTextFieldUangBayar.setText("");
                jTextFieldUangKembali.setText("");
                return;
            }
            lamaMenginap = Integer.parseInt(lamaMenginapText);
            if (lamaMenginap <= 0) {
                 jTextFieldTotalHarga.setText("Input Tidak Valid");
                 jTextAreaKeterangan.setText("Lama Menginap harus lebih dari 0.");
                 jTextFieldUangBayar.setText("");
                 jTextFieldUangKembali.setText("");
                 return;
            }

        } catch (NumberFormatException ex) {
            jTextFieldTotalHarga.setText("Input Tidak Valid");
            jTextAreaKeterangan.setText("Lama Menginap harus berupa angka.");
            jTextFieldUangBayar.setText("");
            jTextFieldUangKembali.setText("");
            return;
        }

        double pricePerNight = 0;
        String roomDetails = "";

        switch (selectedRoomType) {
            case "Single Room":
                pricePerNight = SINGLE_ROOM_PRICE;
                roomDetails = "Single Room: - 1 kamar tidur\n- Bath tub\n- AC\n- TV\n- Wi-Fi";
                break;
            case "Double Room":
                pricePerNight = DOUBLE_ROOM_PRICE;
                roomDetails = "Double Room: - 1 double bed\n- Kamar Mandi\n- AC\n- TV\n- Wi-Fi";
                break;
            case "Suite Room":
                pricePerNight = SUITE_ROOM_PRICE;
                roomDetails = "Suite Room: - Kamar Luas\n- Bed King Size\n- Bath tub\n- AC\n- TV+Netflix\n- Minibar";
                break;
            case "Deluxe Room":
                pricePerNight = DELUXE_ROOM_PRICE;
                roomDetails = "Deluxe Room: - Ruang tamu\n- Bed King Size\n- Bath tub\n- AC\n- 2 TV+Netflix\n- Sarapan\n- CS 24 jam";
                break;
            default:
                pricePerNight = 0;
                jTextFieldTotalHarga.setText("");
                jTextFieldUangBayar.setText("");
                jTextFieldUangKembali.setText("");
                jTextAreaKeterangan.setText("Silakan pilih tipe kamar.");
                return;
        }

        double totalHarga = pricePerNight * lamaMenginap;
        jTextFieldTotalHarga.setText(String.format("%.2f", totalHarga));

        jTextAreaKeterangan.setText("--- Detail Booking Anda ---\n" +
                                   "Tipe Kamar: " + selectedRoomType + "\n" +
                                   "Lama Menginap: " + lamaMenginap + " hari\n" +
                                   "Harga per Malam: Rp " + String.format("%.2f", pricePerNight) + "\n" +
                                   "Total Harga: Rp " + String.format("%.2f", totalHarga) + "\n\n" +
                                   "--- Fasilitas Kamar ---\n" +
                                   roomDetails);

        updateUangKembali();
    }

    private void updateUangKembali() {
        try {
            String totalHargaText = jTextFieldTotalHarga.getText().trim();
            String uangBayarText = jTextFieldUangBayar.getText().trim();

            if (totalHargaText.isEmpty() || totalHargaText.equalsIgnoreCase("Input Tidak Valid") || !totalHargaText.matches("-?\\d+(\\.\\d+)?")) {
                jTextFieldUangKembali.setText("Total Harga Invalid");
                return;
            }
            if (uangBayarText.isEmpty() || !uangBayarText.matches("-?\\d+(\\.\\d+)?")) {
                jTextFieldUangKembali.setText("Uang Bayar Invalid");
                return;
            }

            double totalHarga = Double.parseDouble(totalHargaText);
            double uangBayar = Double.parseDouble(uangBayarText);

            double uangKembali = uangBayar - totalHarga;

            jTextFieldUangKembali.setText(String.format("%.2f", uangKembali));

        }
        catch (NumberFormatException ex) {
            jTextFieldUangKembali.setText("Format Angka Salah");
        } catch (Exception ex) {
            jTextFieldUangKembali.setText("Error");
        }
    }


    private void saveNewBooking() {
        String nama = jTextFieldNama.getText();
        String nik = jTextFieldNIK.getText();
        String tipeKamar = (String) jComboBoxTipeKamar.getSelectedItem();
        int lamaMenginap;
        double totalBayar;
        double uangBayar;
        String idKamarDibooking = null; // Variabel untuk menyimpan ID kamar yang dibooking

        // Validasi input
        if (nama.isEmpty() || nik.isEmpty() || tipeKamar == null || tipeKamar.equals("pilih") || 
            jTextFieldLamaMenginap.getText().isEmpty() || jTextFieldTotalHarga.getText().isEmpty() || 
            jTextFieldUangBayar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mohon lengkapi semua data!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (jTextFieldTotalHarga.getText().equalsIgnoreCase("Input Tidak Valid") || 
            jTextFieldUangKembali.getText().equalsIgnoreCase("Total Harga Invalid") || 
            jTextFieldUangKembali.getText().equalsIgnoreCase("Uang Bayar Invalid") || 
            jTextFieldUangKembali.getText().equalsIgnoreCase("Format Angka Salah") ||
            jTextFieldUangKembali.getText().equalsIgnoreCase("Error")) {
             JOptionPane.showMessageDialog(this, "Ada kesalahan dalam input harga atau pembayaran. Mohon periksa kembali.", "Input Error", JOptionPane.ERROR_MESSAGE);
             return;
        }

        try {
            lamaMenginap = Integer.parseInt(jTextFieldLamaMenginap.getText());
            totalBayar = Double.parseDouble(jTextFieldTotalHarga.getText());
            uangBayar = Double.parseDouble(jTextFieldUangBayar.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lama Menginap, Total Harga, Uang Bayar harus angka!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (uangBayar < totalBayar) {
            JOptionPane.showMessageDialog(this, "Uang Bayar tidak cukup!", "Pembayaran Kurang", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement pstCustomer = null;
        PreparedStatement pstKamarUpdate = null;
        ResultSet rsKamar = null;

        try {
            conn = Koneksi.configDB();
            conn.setAutoCommit(false); // Mulai transaksi

            // 1. Cari kamar yang tersedia berdasarkan tipe kamar
            String sqlFindAvailableRoom = "SELECT id_kamar FROM kamar WHERE tipe_kamar = ? AND ketersediaan = 'Tersedia' LIMIT 1";
            PreparedStatement pstFindRoom = conn.prepareStatement(sqlFindAvailableRoom);
            pstFindRoom.setString(1, tipeKamar);
            rsKamar = pstFindRoom.executeQuery();

            if (rsKamar.next()) {
                idKamarDibooking = rsKamar.getString("id_kamar");

                // 2. Update status ketersediaan kamar menjadi 'Tidak Tersedia'
                String sqlKamarUpdate = "UPDATE kamar SET ketersediaan = 'Tidak Tersedia' WHERE id_kamar = ?";
                pstKamarUpdate = conn.prepareStatement(sqlKamarUpdate);
                pstKamarUpdate.setString(1, idKamarDibooking);
                pstKamarUpdate.executeUpdate();

                // 3. Simpan data booking customer, termasuk id_kamar_booked dan tanggal_booking
                String sqlCustomer = "INSERT INTO customers (nama, nik, tipe_kamar, id_kamar_booked, lama_menginap, total_bayar, tanggal_booking) VALUES (?, ?, ?, ?, ?, ?, NOW())";
                pstCustomer = conn.prepareStatement(sqlCustomer);
                pstCustomer.setString(1, nama);
                pstCustomer.setString(2, nik);
                pstCustomer.setString(3, tipeKamar);
                pstCustomer.setString(4, idKamarDibooking); // Simpan ID kamar yang dibooking
                pstCustomer.setInt(5, lamaMenginap);
                pstCustomer.setDouble(6, totalBayar);
                // tanggal_booking diatur otomatis oleh NOW() di SQL
                pstCustomer.executeUpdate();

                conn.commit(); // Commit transaksi jika semua berhasil
                JOptionPane.showMessageDialog(this, "Booking Berhasil disimpan! Kamar ID: " + idKamarDibooking + " telah dibooking.");
                clearFields();
                loadCustomerData(""); // Muat ulang data tabel setelah booking baru, tanpa filter
            } else {
                JOptionPane.showMessageDialog(this, "Maaf, tidak ada kamar tipe " + tipeKamar + " yang tersedia saat ini.", "Kamar Tidak Tersedia", JOptionPane.WARNING_MESSAGE);
                conn.rollback(); // Rollback jika tidak ada kamar tersedia
            }
        } catch (SQLException ex) {
            try {
                if (conn != null) conn.rollback(); // Rollback jika ada error
            } catch (SQLException rbEx) {
                System.err.println("Rollback failed: " + rbEx.getMessage());
            }
            JOptionPane.showMessageDialog(this, "Error saat menyimpan booking atau mengupdate status kamar: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rsKamar != null) rsKamar.close();
                if (pstCustomer != null) pstCustomer.close();
                if (pstKamarUpdate != null) pstKamarUpdate.close();
                if (conn != null) conn.setAutoCommit(true); // Kembalikan auto-commit
                if (conn != null) conn.close();
            } catch (SQLException closeEx) {
                System.err.println("Error closing resources: " + closeEx.getMessage());
            }
        }
    }


    private void editBooking() {
        if (selectedCustomerId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data customer dari tabel untuk diedit.", "Pilih Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nama = jTextFieldNama.getText();
        String nik = jTextFieldNIK.getText();
        String tipeKamarBaru = (String) jComboBoxTipeKamar.getSelectedItem();
        int lamaMenginap;
        double totalBayar;
        double uangBayar;
        String idKamarBaruDibooking = originalRoomIdBooked; // Default: tetap pakai kamar yang sama

        // Validasi input
        if (nama.isEmpty() || nik.isEmpty() || tipeKamarBaru == null || tipeKamarBaru.equals("pilih") || 
            jTextFieldLamaMenginap.getText().isEmpty() || jTextFieldTotalHarga.getText().isEmpty() || 
            jTextFieldUangBayar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mohon lengkapi semua data!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (jTextFieldTotalHarga.getText().equalsIgnoreCase("Input Tidak Valid") || 
            jTextFieldUangKembali.getText().equalsIgnoreCase("Total Harga Invalid") || 
            jTextFieldUangKembali.getText().equalsIgnoreCase("Uang Bayar Invalid") || 
            jTextFieldUangKembali.getText().equalsIgnoreCase("Format Angka Salah") ||
            jTextFieldUangKembali.getText().equalsIgnoreCase("Error")) {
             JOptionPane.showMessageDialog(this, "Ada kesalahan dalam input harga atau pembayaran. Mohon periksa kembali.", "Input Error", JOptionPane.ERROR_MESSAGE);
             return;
        }

        try {
            lamaMenginap = Integer.parseInt(jTextFieldLamaMenginap.getText());
            totalBayar = Double.parseDouble(jTextFieldTotalHarga.getText());
            uangBayar = Double.parseDouble(jTextFieldUangBayar.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lama Menginap, Total Harga, Uang Bayar harus angka!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (uangBayar < totalBayar) {
            JOptionPane.showMessageDialog(this, "Uang Bayar tidak cukup!", "Pembayaran Kurang", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement pstCustomerUpdate = null;
        PreparedStatement pstKamarOldUpdate = null;
        PreparedStatement pstKamarNewUpdate = null;
        ResultSet rsKamar = null;

        try {
            conn = Koneksi.configDB();
            conn.setAutoCommit(false); // Mulai transaksi

            // Ambil tipe kamar lama dari database untuk perbandingan
            String oldTipeKamar = null;
            String sqlGetOldRoomData = "SELECT tipe_kamar, id_kamar_booked FROM customers WHERE id = ?";
            PreparedStatement pstGetOldRoom = conn.prepareStatement(sqlGetOldRoomData);
            pstGetOldRoom.setInt(1, selectedCustomerId);
            ResultSet rsOldData = pstGetOldRoom.executeQuery();
            if (rsOldData.next()) {
                oldTipeKamar = rsOldData.getString("tipe_kamar");
                originalRoomIdBooked = rsOldData.getString("id_kamar_booked"); // Pastikan originalRoomIdBooked terisi
            }
            rsOldData.close();
            pstGetOldRoom.close();

            boolean roomTypeChanged = !tipeKamarBaru.equals(oldTipeKamar);

            if (roomTypeChanged) {
                // Jika tipe kamar berubah, bebaskan kamar lama
                if (originalRoomIdBooked != null && !originalRoomIdBooked.isEmpty()) {
                    String sqlReleaseOldRoom = "UPDATE kamar SET ketersediaan = 'Tersedia' WHERE id_kamar = ?";
                    pstKamarOldUpdate = conn.prepareStatement(sqlReleaseOldRoom);
                    pstKamarOldUpdate.setString(1, originalRoomIdBooked);
                    pstKamarOldUpdate.executeUpdate();
                }

                // Cari kamar baru yang tersedia untuk tipe kamar baru
                String sqlFindNewRoom = "SELECT id_kamar FROM kamar WHERE tipe_kamar = ? AND ketersediaan = 'Tersedia' LIMIT 1";
                PreparedStatement pstFindNewRoom = conn.prepareStatement(sqlFindNewRoom);
                pstFindNewRoom.setString(1, tipeKamarBaru);
                rsKamar = pstFindNewRoom.executeQuery();

                if (rsKamar.next()) {
                    idKamarBaruDibooking = rsKamar.getString("id_kamar");
                    // Book kamar baru
                    String sqlBookNewRoom = "UPDATE kamar SET ketersediaan = 'Tidak Tersedia' WHERE id_kamar = ?";
                    pstKamarNewUpdate = conn.prepareStatement(sqlBookNewRoom);
                    pstKamarNewUpdate.setString(1, idKamarBaruDibooking);
                    pstKamarNewUpdate.executeUpdate();
                } else {
                    JOptionPane.showMessageDialog(this, "Maaf, tidak ada kamar tipe " + tipeKamarBaru + " yang tersedia saat ini untuk perubahan booking.", "Kamar Tidak Tersedia", JOptionPane.WARNING_MESSAGE);
                    conn.rollback();
                    return;
                }
            } else {
                // Jika tipe kamar tidak berubah, idKamarBaruDibooking tetap sama dengan originalRoomIdBooked
                idKamarBaruDibooking = originalRoomIdBooked;
            }

            // Update data customer
            String sqlCustomerUpdate = "UPDATE customers SET nama = ?, nik = ?, tipe_kamar = ?, id_kamar_booked = ?, lama_menginap = ?, total_bayar = ? WHERE id = ?";
            pstCustomerUpdate = conn.prepareStatement(sqlCustomerUpdate);
            pstCustomerUpdate.setString(1, nama);
            pstCustomerUpdate.setString(2, nik);
            pstCustomerUpdate.setString(3, tipeKamarBaru);
            pstCustomerUpdate.setString(4, idKamarBaruDibooking);
            pstCustomerUpdate.setInt(5, lamaMenginap);
            pstCustomerUpdate.setDouble(6, totalBayar);
            pstCustomerUpdate.setInt(7, selectedCustomerId);
            pstCustomerUpdate.executeUpdate();

            conn.commit(); // Commit transaksi
            JOptionPane.showMessageDialog(this, "Data Booking berhasil diupdate! Kamar ID: " + idKamarBaruDibooking);
            clearFields();
            loadCustomerData(""); // Muat ulang data
        } catch (SQLException ex) {
            try {
                if (conn != null) conn.rollback(); // Rollback jika ada error
            } catch (SQLException rbEx) {
                System.err.println("Rollback failed: " + rbEx.getMessage());
            }
            JOptionPane.showMessageDialog(this, "Error saat mengupdate booking: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rsKamar != null) rsKamar.close();
                if (pstCustomerUpdate != null) pstCustomerUpdate.close();
                if (pstKamarOldUpdate != null) pstKamarOldUpdate.close();
                if (pstKamarNewUpdate != null) pstKamarNewUpdate.close();
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException closeEx) {
                System.err.println("Error closing resources: " + closeEx.getMessage());
            }
        }
    }


    private void deleteBooking() {
        if (selectedCustomerId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data customer dari tabel untuk dihapus.", "Pilih Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus booking atas nama: " + jTextFieldNama.getText() + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            PreparedStatement pstCustomerDelete = null;
            PreparedStatement pstKamarUpdate = null;

            try {
                conn = Koneksi.configDB();
                conn.setAutoCommit(false); // Mulai transaksi

                // Dapatkan ID Kamar yang dibooking oleh customer ini
                String idKamarToRelease = null;
                String sqlGetRoomId = "SELECT id_kamar_booked FROM customers WHERE id = ?";
                PreparedStatement pstGetRoom = conn.prepareStatement(sqlGetRoomId);
                pstGetRoom.setInt(1, selectedCustomerId);
                ResultSet rs = pstGetRoom.executeQuery();
                if (rs.next()) {
                    idKamarToRelease = rs.getString("id_kamar_booked");
                }
                rs.close();
                pstGetRoom.close();

                // 1. Hapus data customer
                String sqlCustomerDelete = "DELETE FROM customers WHERE id = ?";
                pstCustomerDelete = conn.prepareStatement(sqlCustomerDelete);
                pstCustomerDelete.setInt(1, selectedCustomerId);
                pstCustomerDelete.executeUpdate();

                // 2. Update status ketersediaan kamar menjadi 'Tersedia' jika ada id_kamar_booked
                if (idKamarToRelease != null && !idKamarToRelease.isEmpty()) {
                    String sqlKamarUpdate = "UPDATE kamar SET ketersediaan = 'Tersedia' WHERE id_kamar = ?";
                    pstKamarUpdate = conn.prepareStatement(sqlKamarUpdate);
                    pstKamarUpdate.setString(1, idKamarToRelease);
                    pstKamarUpdate.executeUpdate();
                }

                conn.commit(); // Commit transaksi
                JOptionPane.showMessageDialog(this, "Booking berhasil dihapus dan kamar telah dibebaskan!");
                clearFields();
                loadCustomerData(""); // Muat ulang data
            } catch (SQLException ex) {
                try {
                    if (conn != null) conn.rollback(); // Rollback jika ada error
                } catch (SQLException rbEx) {
                    System.err.println("Rollback failed: " + rbEx.getMessage());
                }
                JOptionPane.showMessageDialog(this, "Error saat menghapus booking: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (pstCustomerDelete != null) pstCustomerDelete.close();
                    if (pstKamarUpdate != null) pstKamarUpdate.close();
                    if (conn != null) conn.setAutoCommit(true);
                    if (conn != null) conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Error closing resources: " + closeEx.getMessage());
                }
            }
        }
    }


    private void clearFields() {
        jTextFieldNama.setText("");
        jTextFieldNIK.setText("");
        if (jComboBoxTipeKamar != null) {
            jComboBoxTipeKamar.setSelectedIndex(0);
        }
        jTextFieldLamaMenginap.setText("");
        jTextFieldTotalHarga.setText("");
        jTextFieldUangBayar.setText("");
        jTextFieldUangKembali.setText("");
        jTextFieldCari.setText(""); // Bersihkan juga field pencarian
        jTextAreaKeterangan.setText("Informasi atau Fasilitas Kamar akan ditampilkan di sini.");

        selectedCustomerId = -1; // Reset ID customer yang dipilih
        originalRoomIdBooked = null; // Reset ID kamar asli

        // Aktifkan tombol Booking, nonaktifkan Edit dan Hapus
        jButtonBooking.setEnabled(true);
        jButtonEdit.setEnabled(false);
        jButtonHapus.setEnabled(false);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DashboardPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardPegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBooking;
    private javax.swing.JButton jButtonCari;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JButton jButtonHapus;
    private javax.swing.JButton jButtonKeluar;
    private javax.swing.JComboBox jComboBoxTipeKamar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCustomer;
    private javax.swing.JTextArea jTextAreaKeterangan;
    private javax.swing.JTextField jTextFieldCari;
    private javax.swing.JTextField jTextFieldLamaMenginap;
    private javax.swing.JTextField jTextFieldNIK;
    private javax.swing.JTextField jTextFieldNama;
    private javax.swing.JTextField jTextFieldTotalHarga;
    private javax.swing.JTextField jTextFieldUangBayar;
    private javax.swing.JTextField jTextFieldUangKembali;
    // End of variables declaration//GEN-END:variables
}
