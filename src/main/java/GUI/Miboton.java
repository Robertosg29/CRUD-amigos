
package GUI;

import data.Main;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

/**
 *
 * @author Rober
 */
public class Miboton extends JButton{

    public Miboton(String nom) {
        
        setFont((new Font("Franklin Gothic Medium", 0, 18)));
        setBackground(new Color(113,135,193));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 67, 114),3));
        setForeground(Color.white);
        setText(nom);
    }
    
    
    
}
