package com.example.java;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PenghitungDiskonApp extends JFrame {
    private JTextField inputHargaAsli, inputKodeKupon;
    private JLabel labelHargaAkhir, labelPenghematan;
    private JComboBox<String> dropdownDiskon;
    private JSlider sliderDiskon;
    private JTextArea areaRiwayat;
    private JButton tombolHitung;

    public PenghitungDiskonApp() {
        setTitle("Penghitung Diskon");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelInput = new JPanel(new GridLayout(5, 2, 15, 15));
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Data"));
        panelInput.add(new JLabel("Harga Asli:"));
        inputHargaAsli = new JTextField();
        panelInput.add(inputHargaAsli);

        panelInput.add(new JLabel("Persentase Diskon:"));
        dropdownDiskon = new JComboBox<>(new String[]{"0%", "10%", "20%", "30%", "50%"});
        panelInput.add(dropdownDiskon);

        panelInput.add(new JLabel("Atau Gunakan Slider:"));
        sliderDiskon = new JSlider(0, 100, 0);
        sliderDiskon.setMajorTickSpacing(10);
        sliderDiskon.setPaintTicks(true);
        sliderDiskon.setPaintLabels(true);
        panelInput.add(sliderDiskon);

        panelInput.add(new JLabel("Kode Kupon Diskon:"));
        inputKodeKupon = new JTextField();
        panelInput.add(inputKodeKupon);

        tombolHitung = new JButton("Hitung");
        tombolHitung.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelInput.add(tombolHitung);
        panelInput.add(new JLabel());

        JPanel panelOutput = new JPanel(new GridLayout(2, 1));
        panelOutput.setBorder(BorderFactory.createTitledBorder("Hasil"));
        labelHargaAkhir = new JLabel("Harga Akhir: -", JLabel.CENTER);
        labelPenghematan = new JLabel("Penghematan: -", JLabel.CENTER);
        labelHargaAkhir.setFont(new Font("Serif", Font.PLAIN, 16));
        labelPenghematan.setFont(new Font("Serif", Font.PLAIN, 16));
        panelOutput.add(labelHargaAkhir);
        panelOutput.add(labelPenghematan);

        JPanel panelRiwayat = new JPanel(new BorderLayout());
        panelRiwayat.setBorder(BorderFactory.createTitledBorder("Riwayat Perhitungan"));
        areaRiwayat = new JTextArea();
        areaRiwayat.setEditable(false);
        panelRiwayat.add(new JScrollPane(areaRiwayat), BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(panelInput, BorderLayout.NORTH);
        add(panelOutput, BorderLayout.CENTER);
        add(panelRiwayat, BorderLayout.SOUTH);

        tombolHitung.addActionListener(e -> hitungDiskon());
        dropdownDiskon.addItemListener(e -> sliderDiskon.setValue(getDiskonDariDropdown()));
        sliderDiskon.addChangeListener(e -> dropdownDiskon.setSelectedItem(sliderDiskon.getValue() + "%"));

        inputHargaAsli.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Masukkan hanya angka!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void hitungDiskon() {
        try {
            String hargaAsliText = inputHargaAsli.getText();
            if (hargaAsliText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Masukkan harga asli!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double hargaAsli = Double.parseDouble(hargaAsliText);
            int diskon = getDiskonDariDropdown();

            String kodeKupon = inputKodeKupon.getText().trim();
            if (kodeKupon.equalsIgnoreCase("DISKON10")) {
                diskon += 10;
                JOptionPane.showMessageDialog(this, "Kode kupon valid! Diskon tambahan 10%.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }

            if (diskon > 100) diskon = 100;

            double penghematan = hargaAsli * diskon / 100;
            double hargaAkhir = hargaAsli - penghematan;

            labelHargaAkhir.setText(String.format("Harga Akhir: Rp %.2f", hargaAkhir));
            labelPenghematan.setText(String.format("Penghematan: Rp %.2f", penghematan));

            areaRiwayat.append(String.format("Harga Asli: Rp %.2f, Diskon: %d%%, Harga Akhir: Rp %.2f\n", hargaAsli, diskon, hargaAkhir));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getDiskonDariDropdown() {
        String selected = (String) dropdownDiskon.getSelectedItem();
        return Integer.parseInt(selected.replace("%", ""));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PenghitungDiskonApp app = new PenghitungDiskonApp();
            app.setVisible(true);
        });
    }
}

