package Aparkarma;

import Aparkarma.SpringBootIniciador;
import java.awt.Color;
import javax.swing.JOptionPane;
import org.springframework.boot.SpringApplication;

/**
 *
 * @author Mohamed Roukdi
 */
public class Finestra extends javax.swing.JFrame {

    private Thread hiloServidor;
    public static boolean activat = false;
    private final Color COLOR_ATURAT = Color.RED;
    private final Color COLOR_INICIAT = Color.GREEN;

    public Finestra() {
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLblMainTitle = new javax.swing.JLabel();
        jBtnStart = new javax.swing.JButton();
        jBtnStop = new javax.swing.JButton();
        jLblStatus = new javax.swing.JLabel();
        jLblStatusInfo = new javax.swing.JLabel();
        jLblPortNumber = new javax.swing.JLabel();
        jTxtPortNumber = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLblMainTitle.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLblMainTitle.setText("Aparkarma");

        jBtnStart.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnStart.setText("Iniciar el Servidor");
        jBtnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnStartActionPerformed(evt);
            }
        });

        jBtnStop.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnStop.setText("Aturar el Servidor");
        jBtnStop.setEnabled(false);
        jBtnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnStopActionPerformed(evt);
            }
        });

        jLblStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblStatus.setText("Estat actual:");

        jLblStatusInfo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblStatusInfo.setForeground(new java.awt.Color(255, 51, 51));
        jLblStatusInfo.setText("Aturat");

        jLblPortNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblPortNumber.setText("Port a utilitzar:");

        jTxtPortNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLblMainTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(69, 69, 69))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jBtnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLblPortNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLblStatus))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLblStatusInfo)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTxtPortNumber)
                                .addComponent(jBtnStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 27, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblMainTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblPortNumber)
                    .addComponent(jTxtPortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnStart)
                    .addComponent(jBtnStop))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblStatus)
                    .addComponent(jLblStatusInfo))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnStopActionPerformed
        if (activat) {
            try {
                activat = false;
                jBtnStop.setEnabled(activat);
                jBtnStart.setEnabled(!activat);
                jLblStatusInfo.setText("Aturat");
                jLblStatusInfo.setForeground(COLOR_ATURAT);
                SpringBootIniciador.serverSocket.close();
                System.out.println("Servidor cerrado");
            } catch (Exception ex) {
                System.out.println("No se ha podido cerrar el socket correctamente");
            }
        }
    }//GEN-LAST:event_jBtnStopActionPerformed


    private void jBtnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnStartActionPerformed
        String port = jTxtPortNumber.getText().trim();

        if (!port.isEmpty()) {
            try {
                // Verificar si el puerto es válido
                int portNumero = Integer.parseInt(port);

                // Actualizar el estado del servidor
                activat = true;
                jLblStatusInfo.setText("Iniciat");
                jLblStatusInfo.setForeground(COLOR_INICIAT);

                // Configurar el puerto en las propiedades de Spring Boot
                System.setProperty("server.port", String.valueOf(portNumero));

                // Ejecutar el servidor en un hilo separado
                hiloServidor = new Thread(() -> {
                    jBtnStop.setEnabled(activat);
                    jBtnStart.setEnabled(!activat);
                    try {
                        SpringApplication.run(SpringBootIniciador.class, port);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error al iniciar el servidor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        activat = false;
                        jLblStatusInfo.setText("Aturat");
                        jLblStatusInfo.setForeground(COLOR_ATURAT);
                    }
                });
                hiloServidor.start();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El puerto debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                jTxtPortNumber.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Falta el número de puerto", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jBtnStartActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnStart;
    private javax.swing.JButton jBtnStop;
    private javax.swing.JLabel jLblMainTitle;
    private javax.swing.JLabel jLblPortNumber;
    private javax.swing.JLabel jLblStatus;
    private javax.swing.JLabel jLblStatusInfo;
    private javax.swing.JTextField jTxtPortNumber;
    // End of variables declaration//GEN-END:variables

}
