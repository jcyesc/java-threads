/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TypingPanel.java
 *
 * Created on Apr 19, 2011, 1:54:23 PM
 */

package com.typing.gui;

import com.typing.RangeGenerator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.ListDataListener;

/**
 *
 * @author yescas
 */
public class TypingPanel extends javax.swing.JPanel {

    /** Creates new form TypingPanel */
    public TypingPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnStop = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();
        lblCharacters = new javax.swing.JLabel();
        jComboBoxLevels = new javax.swing.JComboBox();
        jLabelMessage = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelPoints = new javax.swing.JLabel();

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        btnStop.setText("Stop");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        lblCharacters.setFont(new java.awt.Font("Lucida Grande", 1, 24));
        lblCharacters.setForeground(new java.awt.Color(0, 0, 204));
        lblCharacters.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCharacters.setText("Alphabet");

        jComboBoxLevels.setModel(levelModel);

        jLabelMessage.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabelMessage.setForeground(new java.awt.Color(0, 0, 204));
        jLabelMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMessage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("Points:");

        jLabelPoints.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabelPoints.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPoints.setText("0");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(32, 32, 32)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, lblCharacters, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, btnStart)
                            .add(jLabelPoints, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(btnStop)
                            .add(jComboBoxLevels, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(38, 38, 38))
                    .add(layout.createSequentialGroup()
                        .add(jLabelMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 501, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(34, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .addContainerGap()
                                .add(lblCharacters, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(55, 55, 55)
                                .add(jComboBoxLevels, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(162, 162, 162)
                        .add(jLabelMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 35, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(btnStart)
                            .add(btnStop)))
                    .add(layout.createSequentialGroup()
                        .add(58, 58, 58)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(jLabelPoints))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private boolean stop = false;
    private RangeGenerator rg = new RangeGenerator();
    private int speed = 3; // medium = 3, slow = 1; fast = 6

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        stop = false;
        System.out.println("Start");
        this.requestFocus();
        start = true;
        Thread t = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    symbol = rg.getCharacter() +"";
                    for(x = 590; x > 12; x-=getSpeed()) {
                        repaint();
                        
                        if(stop)
                            return;
                       
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ie) {
                            return;
                        }
                    }
                    points -= 20;
                    jLabelPoints.setText(points + "");
                    jLabelMessage.setText("");

                }
            }
        });
        t.start();
    }//GEN-LAST:event_btnStartActionPerformed

    int x, y;
    boolean start;
    String symbol = "";
    int points = 0;
    protected synchronized void paintComponent(Graphics gc) {
        if(start) {
            y = 150;
            gc.setColor(Color.BLACK);
            Dimension d = getSize();
            gc.clearRect(0, 0, d.width, d.height);
            Map attributes = new HashMap();
            attributes.put(TextAttribute.SIZE, new Integer(35));
            attributes.put(TextAttribute.WEIGHT_BOLD, new Integer(30));
            gc.setFont(new Font(attributes));
            gc.drawString(symbol.toUpperCase(), x, y);
        }
    }

    javax.swing.ComboBoxModel levelModel = new javax.swing.ComboBoxModel() {
        String selected = "Level 1";

        public void setSelectedItem(Object o) {
            selected = o.toString();
            rg.setLevel(Integer.parseInt(selected.replaceAll("Level", "").trim()));
            TypingPanel.this.requestFocus();
        }

        public Object getSelectedItem() {
            return selected;
        }

        public int getSize() {
           return rg.getNumberLevels();
        }

        public Object getElementAt(int i) {
            return "Level " + (i + 1);
        }

        public void addListDataListener(ListDataListener ll) {
        }

        public void removeListDataListener(ListDataListener ll) {
        }

    };

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        // TODO add your handling code here:
        this.stop = true;
        this.btnStart.setEnabled(true);
        this.btnStop.setEnabled(false);
    }//GEN-LAST:event_btnStopActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        //System.out.println("Symbol " + symbol + " Key " + evt.getKeyChar() );

        if(symbol.equalsIgnoreCase(evt.getKeyChar() + "")) {
            jLabelMessage.setForeground(Color.BLUE);
        	jLabelMessage.setText("WELL DONE!!");
            x= 590;
            symbol = rg.getCharacter() + "";
            points += 10;
        } else {
            jLabelMessage.setForeground(Color.RED);
        	jLabelMessage.setText("TRY AGAIN!!");            
            points -= 20;
        }
        jLabelPoints.setText(points + "");

        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JComboBox jComboBoxLevels;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelMessage;
    private javax.swing.JLabel jLabelPoints;
    private javax.swing.JLabel lblCharacters;
    // End of variables declaration//GEN-END:variables

}