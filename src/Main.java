/**
 *
 * @author Riana Robles 1195255 LAD y Rodrigo Marcos 919806 ITC
 */

import javax.swing.JFrame;

public class Main {
    public static void main (String[] args) {
        JframeExamen1 jfrJuego = new JframeExamen1();
        jfrJuego.setVisible(true);
        //Define lo que pasará cuando se cierre la ventana
        //Al cerrar el programa terminará su ejecución
        jfrJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Define tamaño de la imagen
        jfrJuego.setSize(800, 600);
    }
}
