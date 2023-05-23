package GUI;

import data.GestionaBDD;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Rober y Cris
 */
public class VentanaPpal extends JFrame {

    GestionaBDD gb;

    public VentanaPpal() {
        
        super("AGENDA AMIGOS");
        this.gb=new GestionaBDD();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        ponPanel(new PanelInicial(this));
    }

    public GestionaBDD getGb() {
        return gb;
    }

    public void ponPanel(JPanel p) {
        setContentPane(p);
        this.revalidate();
    }

}
