/**
 *
 * @author Diana Robles LAD 1195255 y Rodrigo Marcos 919806 ITC
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.abs;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class JframeExamen1 extends JFrame implements Runnable, KeyListener,
        MouseListener{
    
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    private int iVidas;                 // Variable int que maneja las vidas
    private Personaje perChanguita;     // Objeto Personaje Changuita
    private Personaje perCaminador;     // Objeto Personaje Caminador
    private Personaje perCorredor;      // Objeto Personaje Corredor
    private Personaje perPlayPause;     // Objeto Personaje Play y Pausa
    private Image imaChanguita;         // Imagen de la changuita
    private Image imaCaminador;         // imagen del caminador
    private Image imaCorredor;          // imagen del corredor
    private Image imaPlayPause;         // Imagen del boton Play y Pausa
    private int iDireccion;             // Variable int que maneja direccion
    private int iRandom;                // Variable int que maneja posiciones
    private int iRandom2;               // Variable int que maneja posiciones
    private int iScore;                 // Variable int que maneja score
    private int iChoco;                 // variable int que suma los choques
    private LinkedList lnkCaminadores;  // Linked list de Caminadores
    private LinkedList lnkCorredores;   // Linked list de Corredores
    private SoundClip aucMagic;         // Sonido magico
    private SoundClip aucsplash;        // Sonido de splash
    private boolean bPausa;             // Boleano Pausa o Play
    private int iVelCaminador;          // Guarda velocidad para save y load
    
    public JframeExamen1 () {
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     */
        // hago el applet de un tamaño 800,600
        setSize(800, 600);
        // Las vidas tomarán un valor random entre 3 y 5
        iVidas = (int) (Math.random() * 3 + 3);
        
        // iDireccion empieza en 0 para que no haya movimiento
        iDireccion = 0;
        
        // El score empieza en 0
        iScore = 0;
        
        // El numero de choques con Corredor empieza en 0
        iChoco = 0;
        
        //creo el sonido magico
        aucMagic = new SoundClip ("Magic.wav");//creo el sonido del gong
        
        //creo el sonido del splash
        aucsplash = new SoundClip ("splash.wav");
        
        // se crea imagen de Changuita
        imaChanguita = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("nena.gif"));
        
        // Creo a Changuita
	perChanguita = new Personaje(getWidth() / 4, getHeight()  / 4,
                imaChanguita);
        
        // Se posiciona a la Changuita justo en medio del applet
        perChanguita.setX((getWidth() / 2) - (perChanguita.getAncho() / 2));
        perChanguita.setY((getHeight() / 2) - (perChanguita.getAlto() / 2));
        
        perChanguita.setVelocidad(3);
        
        
        //Imagen de boton de Play
        imaPlayPause = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("playpause.png"));
        //se crea como personaje para utilizar los metodos como colisona
        perPlayPause = new Personaje(600, 500, imaPlayPause);
        
        //Se inicializa en false
        bPausa = false;
        
        // Llamo a lnkCaminadores
        lnkCaminadores = new LinkedList() ;
        // Creo variable random que vale entre 8 y 10
        iRandom = (int) (Math.random() * 3 + 8);
        // Se crean y añaden los Caminadores a la linkedlist
        for (int iI = 0; iI < iRandom; iI++) {
             // se crea Caminador 
            imaCaminador = Toolkit.getDefaultToolkit().
                    getImage( this.getClass().getResource("alien1Camina.gif"));
            perCaminador = new Personaje(0, 0, imaCaminador);
            // se posiciona a Caminador en alguna parte al azar del borde
            // izquierdo
            int posX = (int) (0 - perCaminador.getAncho());    
            int posY = (int) (Math.random() * abs(getHeight() -
                    perCaminador.getAlto()));    
            perCaminador.setX(posX);
            perCaminador.setY(posY);
            perCaminador.setVelocidad((int)(Math.random() * 3 + 3));
            lnkCaminadores.add( perCaminador );
        }
        
        // Llamo a lnkCorredores
        lnkCorredores = new LinkedList() ;
        // Creo variable random que vale entre 8 y 10
        iRandom2 = (int) (Math.random() * 5 + 10);
        // Se crean y añaden los Corredores a la linkedlist
        for (int iI = 0; iI < iRandom2; iI++) {
             // se crea Corredor 
            imaCorredor = Toolkit.getDefaultToolkit().
                    getImage( this.getClass().getResource("alien2Corre.gif"));
            perCorredor = new Personaje(0,0,
                    imaCorredor);
            // se posiciona a Caminador en alguna parte al azar del borde
            // izquierdo
            int posX = (int) (Math.random() * abs(getWidth() -
                    perCorredor.getAncho()));    
            int posY = (int) (0 - perCorredor.getAlto());    
            perCorredor.setX(posX);
            perCorredor.setY(posY);
            lnkCorredores.add( perCorredor );
        }
        
        // Para que nos pele el teclado y el mouse
        addKeyListener(this);
        addMouseListener(this);
	
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
    
    public void run () {
        // se realiza el ciclo del juego en este caso nunca termina
        while (iVidas > 0) {
            /* mientras dure el juego, se actualizan posiciones de jugadores
               se checa si hubo colisiones para desaparecer jugadores o corregir
               movimientos y se vuelve a pintar todo
            */
            //si no esta pausado el juego, puede correr actualiza y checa 
            //colision
            if(!bPausa){
                actualiza();
                checaColision();
            }
            repaint();
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError)	{
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
	}
    }
	
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion del objeto Personaje
     * 
     */
    public void actualiza() {
        // Cambia la direccion de la changuita dependiendo de iDireccion
        if (iDireccion == 1) {
            perChanguita.arriba();
        }
        else if (iDireccion == 2) {
            perChanguita.abajo();
        }
        else if (iDireccion == 3) {
            perChanguita.izquierda();
        }
        else if (iDireccion == 4) {
            perChanguita.derecha();
        }
        
        // Hace que los caminadores vayan a la derecha
        for ( Object objCaminador : lnkCaminadores) {
            perCaminador = (Personaje) objCaminador;
            perCaminador.derecha();
        }
        
        // Hace que los corredores vayan hacia abajo
        for ( Object objCorredor : lnkCorredores) {
            perCorredor = (Personaje) objCorredor;
            perCorredor.abajo();
        }
        
        //dependiendo de las vidas, es la velocidad de los corredores
        if ( iVidas > 3) {
            perCorredor.setVelocidad(1);
        }
        else if (iVidas == 2) {
            perCorredor.setVelocidad(2);
        }
        else if (iVidas == 1) {
            perCorredor.setVelocidad(3);
        }
    }
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision del objeto Personaje
     * con las orillas del <code>Applet</code>.
     * 
     */
    public void checaColision() {
        
        // Si la Changuita colisiona con el applet que se quede ahi
        if (perChanguita.getY() <= 0 ) {
            iDireccion = 0;
        }
        else if ((perChanguita.getY() + perChanguita.getAlto()) >=
                getHeight()) {
            iDireccion = 0;
        }
        else if ((perChanguita.getX() + perChanguita.getAncho() >=
                getWidth())) {
            iDireccion = 0;
        }
        else if (perChanguita.getX() <= 0 ) {
            iDireccion = 0;
        }
        
        // Si el Caminador colisiona con la changuita se suma el score
        for (Object objCaminador : lnkCaminadores) {
            perCaminador = (Personaje) objCaminador;
            if(perCaminador.colisiona(perChanguita)) {
                iScore += 1;
                int posX = (int) (0 - perCaminador.getAncho());    
                int posY = (int) (Math.random() * abs(getHeight() -
                        perCaminador.getAlto()));    
                perCaminador.setX(posX);
                perCaminador.setY(posY);
                aucMagic.play(); // suena el sonido magico
            }
        
        
            // Si el Caminador colisiona con el applet se regresa a pos inicial
            else if ((perCaminador.getX() + perCaminador.getAncho()) >=
                getWidth()) {
                int posX = (int) (0 - perCaminador.getAncho());    
                int posY = (int) (Math.random() * abs(getHeight() -
                        perCaminador.getAlto()));    
                perCaminador.setX(posX);
                perCaminador.setY(posY);
            }
        }
            
            // Si el Corredor colisiona con la changuita 
        for (Object objCorredor : lnkCorredores) {
            perCorredor = (Personaje) objCorredor;
            if(perCorredor.colisiona(perChanguita)) {
                int posX = (int) (Math.random() * abs(getWidth() -
                        perCorredor.getAncho()));    
                int posY = (int) (0 - perCorredor.getAlto());    
                perCorredor.setX(posX);
                perCorredor.setY(posY);
                iChoco += 1; 
                aucsplash.play();       // suena el sonido splash
            }
             
            // Si el Corredor colisiona con el applet se regresa a pos inicial
            else if ((perCorredor.getY() + perCorredor.getAlto()) >= 
                    getHeight()) {
                int posX = (int) (Math.random() * abs(getWidth() -
                        perCorredor.getAncho()));    
                int posY = (int) (0 - perCorredor.getAlto());    
                perCorredor.setX(posX);
                perCorredor.setY(posY);
            }
        }
        // Si ya choco la Changuita con los corredores 5 veces resta 1 vida
        if (iChoco >= 4) {
            iVidas -= 1;
            iChoco -= 5;
        }
        
        //se checa colision del click del mouse con el boton de Pausa
    }

	
    /**
     * update
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint (Graphics graGrafico) {
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null) {
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        if (iVidas > 0) {
            // creo imagen para el background pero es un error por que el fondo 
            //esta en imagenraton
            URL urlImagenFondo = this.getClass().getResource("espacio.jpg");
            Image imaImagenEspacio = 
                    Toolkit.getDefaultToolkit().getImage(urlImagenFondo);

            // Despliego la imagen
            graGraficaApplet.drawImage(imaImagenEspacio, 0, 0, 
                    getWidth(), getHeight(), this);
        }
        //Si iVidas es igual a 0, mostrar imagen de Game Over
        else {
            // creo imagen para el background pero es un error por que el fondo 
            //esta en imagenraton
            URL urlImagenFondo = this.getClass().getResource("GAMEOVER.jpg");
            Image imaImagenEspacio = 
                    Toolkit.getDefaultToolkit().getImage(urlImagenFondo);

            // Despliego la imagen
            graGraficaApplet.drawImage(imaImagenEspacio, 0, 0, 
                    getWidth(), getHeight(), this);
        }

        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint1(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }
    
    /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint1(Graphics g) {
        //Si la imagen ya se cargo
        if (perChanguita != null && perCaminador != null &&
                perCorredor != null) {
            //Dibuja la imagen de la Changuita en la posicion actualizada
            g.drawImage(perChanguita.getImagen(), perChanguita.getX(),
                    perChanguita.getY(), this);
            
            //Dibuja las imagenes de play y pausa
            g.drawImage(perPlayPause.getImagen(), perPlayPause.getX(),
                    perPlayPause.getY(),this);
        
            for (Object objCaminador : lnkCaminadores) {
                perCaminador = (Personaje) objCaminador;
                //Dibuja la imagen de Caminador en la posicion actualizada
                g.drawImage(perCaminador.getImagen(), perCaminador.getX(),
                        perCaminador.getY(), this);
            }
        
            for (Object objCorredor : lnkCorredores) {
                perCorredor = (Personaje) objCorredor;
                //Dibuja la imagen del Corredor en la posicion actualizada
                g.drawImage(perCorredor.getImagen(), perCorredor.getX(),
                        perCorredor.getY(), this);
            }
        }
        
        
        else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("No se cargo la imagen..", 20, 40);
        }
        // define el font en color rojo
        g.setColor(Color.red);
        // dibuja el score
        g.drawString("El puntaje es " + iScore + "  Y tienes " + iVidas +
                " vidas", 20, 40);
    }
    
    /**
     * FileSave
     * Graba archivo con informacion del juego actual para poder ser abierto
     * en algun otro momento
     * @throws IOException 
     */
    
    public void FileSave() throws IOException {
        PrintWriter fileOut = new PrintWriter(new FileWriter("datos.txt"));
        
        //graba vidas en una linea
        fileOut.println(iVidas);
        //graba score en una linea
        fileOut.println(iScore);
        //graba posicion 'x' de nena
        fileOut.println(perChanguita.getX());
        //graba posicion 'y' de nena
        fileOut.println(perChanguita.getY());
        //graba velocidad de Corredores
        fileOut.println(perCorredor.getVelocidad());
        //graba cantidad de corredores
        fileOut.println(iRandom2);
        //graba posiciones de corredores
        for (Object objCorredor : lnkCorredores) {
            perCorredor = (Personaje) objCorredor;
            fileOut.println(perCorredor.getX());
            fileOut.println(perCorredor.getY());
        }
        //graba cantidad de caminadores
        fileOut.println(iRandom);
        //graba posiciones de caminadores
        for (Object objCaminador : lnkCaminadores) {
            perCaminador = (Personaje) objCaminador;
            fileOut.println(perCaminador.getX());
            fileOut.println(perCaminador.getY());
        }
        //si termino de guardar, lo cierro
        fileOut.close();
    }
    
    /**
     * FileOpen
     * Abre archivo guardado con los datos anteriormente guardados, en caso de
     * no haber dato guardado, crea uno y luego lo carga.
     * @throws IOException 
     */
    
    public void FileOpen() throws IOException {
        BufferedReader brwEntrada;
        try {
            //si hay archivo guardado, leelo
            brwEntrada = new BufferedReader(new FileReader("datos.txt"));
        } catch(FileNotFoundException e) {
            //si no existia el archivo, lo creo
            File filDatos = new File("datos.txt");
            PrintWriter prwSalida = new PrintWriter(filDatos);
            
            //Le grabo vidas
            prwSalida.println((int) (Math.random() * 3 + 3));
            //le grabo Score
            prwSalida.println("0");
            //posicion 'x' de nena
            prwSalida.println(400);
            //posicion 'y' de nena
            prwSalida.println(400);
            //velocidad de corredores
            if ( iVidas > 3) {
                prwSalida.println(1);
            }
            else if (iVidas == 2) {
                prwSalida.println(2);
            }
            else if (iVidas == 1) {
                prwSalida.println(3);
            }
            //velocidad de caminadores
            prwSalida.println((int)(Math.random() * 3 + 3));
            //cantidad de corredores
            iRandom2 = (int) (Math.random() * 5 + 10);
            prwSalida.println(iRandom2);
            //posicion de corredores
            for (int iI = 0; iI < iRandom2; iI++) {
                // se crea Corredor 
                imaCorredor = Toolkit.getDefaultToolkit().
                        getImage( this.getClass().getResource("alien2Corre.gif"));
                perCorredor = new Personaje(0, 0, imaCorredor);
                // se posiciona a Caminador en alguna parte al azar del borde
                // izquierdo
                int posX = (int) (Math.random() * abs(getWidth() -
                        perCorredor.getAncho()));    
                int posY = (int) (0 - perCorredor.getAlto());    
                perCorredor.setX(posX);
                perCorredor.setY(posY);
                lnkCorredores.add( perCorredor );
            }
            //cantidad de caminadores
            iRandom = (int) (Math.random() * 3 + 8);
            prwSalida.println(iRandom);
            //posicion de caminadores
            for (int iI = 0; iI < iRandom; iI++) {
                // se crea Caminador 
                imaCaminador = Toolkit.getDefaultToolkit().
                        getImage( this.getClass().getResource("alien1Camina.gif"));
                perCaminador = new Personaje(0, 0, imaCaminador);
                // se posiciona a Caminador en alguna parte al azar del borde
                // izquierdo
                int posX = (int) (0 - perCaminador.getAncho());    
                int posY = (int) (Math.random() * abs(getHeight() -
                        perCaminador.getAlto()));    
                perCaminador.setX(posX);
                perCaminador.setY(posY);
                prwSalida.println(perCaminador.getX());
                prwSalida.println(perCaminador.getY());
            }
            
            //lo cierro
            prwSalida.close();
            //se vuelve a abrir para leer los datos
            brwEntrada = new BufferedReader(new FileReader("datos.txt"));
        }
        //con el archivo abierto empiezo a leer los datos
        
        //primero las vidas
        iVidas = Integer.parseInt(brwEntrada.readLine());
        //luego score
        iScore = Integer.parseInt(brwEntrada.readLine());
        //posicion 'x' de nena
        perChanguita.setX(Integer.parseInt(brwEntrada.readLine()));
        //posicion 'y' de nena
        perChanguita.setY(Integer.parseInt(brwEntrada.readLine()));
        //velocidad de Corredores
        perCorredor.setVelocidad(Integer.parseInt(brwEntrada.readLine()));
        //cantidad de corredores
        iRandom2 = Integer.parseInt(brwEntrada.readLine());
        //corredores y posiciones
        //hay que limpiar la lista de corredores
        lnkCorredores.clear();
        for(int iI2 = 0; iI2 < iRandom2; iI2++) {
            imaCorredor = Toolkit.getDefaultToolkit().
                    getImage( this.getClass().getResource("alien2Corre.gif"));
            perCorredor = new Personaje(0, 0, imaCorredor);
            perCorredor.setX(Integer.parseInt(brwEntrada.readLine()));
            perCorredor.setY(Integer.parseInt(brwEntrada.readLine()));
            lnkCorredores.add(perCorredor);
        }
        //cantidad de caminadores
        iRandom = Integer.parseInt(brwEntrada.readLine());
        //caminadores y posiciones
        //hay que limpiar la lista de corredores
        lnkCaminadores.clear();
        for(int iI = 0; iI < iRandom; iI++) {
            imaCaminador = Toolkit.getDefaultToolkit().
                    getImage( this.getClass().getResource("alien1Camina.gif"));
            perCaminador = new Personaje(0, 0, imaCaminador);
            perCaminador.setX(Integer.parseInt(brwEntrada.readLine()));
            perCaminador.setY(Integer.parseInt(brwEntrada.readLine()));
            perCaminador.setVelocidad((int)(Math.random() * 3 + 3));
            lnkCaminadores.add(perCaminador); 
        }
        //si y a termino de leer los datos, lo cierro
        brwEntrada.close();
    }
    
    /**
    * KeyTyped
    * 
    * @param e 
    */
    
    public void keyTyped(KeyEvent e) {
        // Se debe escribir el metodo
    }

    /**
     * KeyPressed
     * 
     * @param e 
     */
    public void keyPressed(KeyEvent e) {
        //Se debe escribir el metodo
    }
    
    /**
     * KeyReleased
     * 
     * Metodo que controla posicion de Changuita de acuerdo a las teclas
     * 
     * @param e 
     */

   
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            iDireccion = 1;   
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            iDireccion = 2;
        }
        else if (e.getKeyCode() == KeyEvent.VK_A) {
            iDireccion = 3;
        }
        else if (e.getKeyCode() == KeyEvent.VK_D) {
            iDireccion = 4;
        }
        if (e.getKeyCode() == KeyEvent.VK_P){
            bPausa = !bPausa;
            iDireccion = 0;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_G){
            bPausa = true;
            try {
                FileSave();
            } catch (IOException ex) {
                Logger.getLogger(JframeExamen1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if ((e.getKeyCode() == KeyEvent.VK_C) || 
                (e.getKeyCode() == KeyEvent.VK_L)){
            bPausa = true;
            try {
                FileOpen();
            } catch (IOException ex) {
                Logger.getLogger(JframeExamen1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * mouseClicked
     * 
     * @param e 
     */
    public void mouseClicked(MouseEvent e) {
        
    }
    
    /**
     * mousePressed
     * 
     * @param e 
     */
    public void mousePressed(MouseEvent e) {
        
    }
    
    /**
     * mouseRealeased
     * 
     * @param e 
     */
    public void mouseReleased(MouseEvent e) {
        if(perPlayPause.colisiona(e.getX(), e.getY())){
            bPausa = !bPausa;
            iDireccion = 0;
        }
    }
    
    /**
     * mouseEntered
     * 
     * @param e 
     */
    public void mouseEntered(MouseEvent e) {
        
    }
    
    /**
     * mouseExited
     * 
     * @param e 
     */
    public void mouseExited(MouseEvent e) {
        
    }
}
