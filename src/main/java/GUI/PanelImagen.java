
package GUI;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Rober
 */
public class PanelImagen extends javax.swing.JPanel {
    
    Image img;
    public PanelImagen() {
        
        initComponents();
    }
 @Override
        public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        img = new ImageIcon(".\\res\\niños.jpg").getImage();
        g.drawImage(img, 0, 0,this.getWidth(),this.getHeight(), null);
        setOpaque(false);
        }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
