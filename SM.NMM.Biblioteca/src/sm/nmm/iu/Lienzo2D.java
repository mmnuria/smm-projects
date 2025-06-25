/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sm.nmm.iu;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sm.nmm.graficos.NCurva2D;
import sm.nmm.graficos.NElipse2D;
import sm.nmm.graficos.NHerramientaDibujo;
import static sm.nmm.graficos.NHerramientaDibujo.curva;
import static sm.nmm.graficos.NHerramientaDibujo.elipse;
import static sm.nmm.graficos.NHerramientaDibujo.linea;
import static sm.nmm.graficos.NHerramientaDibujo.rectangulo;
import sm.nmm.graficos.NLinea2D;
import sm.nmm.graficos.NRectangulo2D;
import sm.nmm.graficos.NShape;

/**
 *
 * @author mmnuria
 */
public class Lienzo2D extends javax.swing.JPanel {

    /**
     * ******** ATRIBUTOS **********
     *
     * /**
     * Guarda la forma que se quiere dibujar
     */
    private NShape forma;

    /**
     * Guarda una lista de las figuras pintadas
     */
    private List<NShape> vShape;

    /**
     * Guarda el color de la figura
     */
    private Color color;

    /**
     * Guarda true si la figura se dibuja rellena o false si no
     */
    private boolean relleno;

    /**
     * Guarda true si la figura está en movimiento y false si no
     */
    private boolean mover;

    /**
     * Guarda el punto donde se presiona el ratón
     */
    private Point pPressed;

    /**
     * Guarda la forma seleccionada
     */
    private NHerramientaDibujo herramientaDibujo;

    /**
     * Guarda el trazo de la figura
     */
    private Stroke trazo;

    /**
     * Guarda el tamaño del trazo de la figura
     */
    private int grosorTrazo;

    /**
     * Guarda true si la figura se dibujará con transparencia o false si no
     */
    private boolean transparencia;

    /**
     * Guarda la opacidad de la figura
     */
    private Composite opacidad;

    /**
     * Guarda true si se quiere alisar la figura y false si no
     */
    private boolean alisar;

    /**
     * Guarda el alisado de la figura
     */
    private RenderingHints alisado;

    /**
     * Guarda true si el paso 1 del dibujado de la curva ha comenzado (se hace
     * en dos pasos)
     */
    private boolean paso1;

    /**
     * Buffer que almacena la imagen de fondo vinculada al lienzo
     */
    private BufferedImage img;

    /**
     * Guarda true si estamos en modo edicion y false en caso contrario
     */
    private boolean modoEdicion = false;

    /**
     * Guarda true si estamos en modo fijar y false en caso contrario
     */
    private boolean modoFijar = false;
    
    /**
     * Guarda true si estamos en modo eliminar y false en caso contrario
     */
    private boolean modoEliminar = false;
    
    /**
     * Guardan los archivos de sonido de fijado o eliminación
     */
    private File sonidoFijar, sonidoEliminar;
    /* Variables para recorte dinámico
    private int offsetX = 0;
    private int offsetY = 0;
    private double scale = 1.0;*/

    /**
     * *********** CONSTRUCTOR ************
     *
     * /**
     * Creates new form Lienzo2D
     */
    public Lienzo2D() {
        initComponents();

        vShape = new ArrayList();
        forma = null;
        color = Color.black;
        relleno = false;
        mover = false;
        herramientaDibujo = NHerramientaDibujo.linea;

        transparencia = false;

        trazo = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.0f, null, 0.0f);
        alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        opacidad = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);

        paso1 = true;
    }

    /**
     * ********* MÉTODOS GET/SET ***********
     *
     * /**
     * Método que añade una lista de formas
     *
     * @param nueva_lista lista con las nuevas formas
     */
    public void setShapeList(List<NShape> nueva_lista) {
        this.vShape = nueva_lista;
    }

    /**
     * Método que muestra las figuras dibujadas
     *
     * @return lista de figuras dibujadas
     */
    public List<NShape> getShapeList() {
        return vShape;
    }

    /**
     * Método que modifica el color de la figura
     *
     * @param color color seleccionado para la figura
     */
    public void setColor(Color color) {
        this.color = color;

        if (modoEdicion && forma != null) {
            forma.setColor(color);
            repaint();
        }
    }

    /**
     * Método que muestra el color de la figura
     *
     * @return color de la figura
     */
    public Color getColor() {
        return color;
    }

    /**
     * Método que modifica si la figura tiene relleno o no
     *
     * @param relleno true si se dibuja con relleno o false si no
     */
    public void setRelleno(boolean relleno) {
        this.relleno = relleno;

        if (modoEdicion && forma != null) {
            forma.setRelleno(relleno);
            repaint();
        }
    }

    /**
     * Método que muestra si la figura está rellenada o no
     *
     * @return si está rellena devuelve true y si no false
     */
    public boolean isRelleno() {
        return relleno;
    }

    /**
     * Método que modifica si una figura se puede mover o no
     *
     * @param mover true si la figura se puede mover, false si no
     */
    public void setMover(boolean mover) {
        this.mover = mover;
    }

    /**
     * Método que muestra si la figura se puede mover
     *
     * @return si se puede mover muestra true y si no false
     */
    public boolean getMover() {
        return mover;
    }

    /**
     * Método que modifica el atributo del grosor del trazo de la figura
     *
     * @param grosor seleccion del grosor del trazo para la figura
     */
    public void setTrazo(int grosor) {
        this.grosorTrazo = grosor;
        this.trazo = new BasicStroke(grosorTrazo);

        if (modoEdicion && forma != null) {
            this.grosorTrazo = grosor;
            forma.setTrazo(grosorTrazo);
            repaint();
        }

    }

    /**
     * Método que muestra el trazo de la figura
     *
     * @return trazo
     */
    public Stroke getTrazo() {
        return trazo;
    }

    /**
     * Método que comprueba si la figura tiene transparencia o no
     *
     * @param transparencia true si la figura tiene transparencia y false si no
     */
    public void setTransparencia(boolean transparencia) {
        if (transparencia) {
            opacidad = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            this.transparencia = true;
        } else {
            opacidad = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            this.transparencia = false;
        }
        if (modoEdicion && forma != null) {
            forma.setTransparencia(transparencia);
            repaint();
        }
    }

    /**
     * Método que muestra si una figura tiene transparencia o no
     *
     * @return si la figura tiene transparencia muestra true y si no false
     */
    public boolean getTransparencia() {
        return transparencia;
    }

    /**
     * Método que modifica si la figura tiene alisado o no
     *
     * @param alisar true si tiene alisado, false si no
     */
    public void setAlisar(boolean alisar) {
        if (alisar) {
            this.alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            this.alisar = true;
        } else {
            this.alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            this.alisar = false;
        }
        if (modoEdicion && forma != null) {
            forma.setAlisar(alisar);
            repaint();
        }
    }

    /**
     * Método que muestra si la figura esta alisada
     *
     * @return si está alisada el valor que muestra es true y si no false
     */
    public boolean getAlisar() {
        return alisar;
    }

    /**
     * Método que muestra el alisado de una figura
     *
     * @return alisado
     */
    public RenderingHints getAlisado() {
        return alisado;
    }

    /**
     * Método que selecciona una figura en el lienzo
     *
     * @param p guarda el punto donde se hizo pressed
     * @return figura seleccionada en el lienzo
     */
    private NShape figuraSeleccionada(Point2D p) {
        for (NShape s : vShape) {
            if (s.contains(p)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Método que modifica la herramienta de dibujo a dibujar
     *
     * @param eleccion herramienta elegida
     */
    public void setHerramientaDibujo(NHerramientaDibujo eleccion) {

        this.herramientaDibujo = eleccion;
        this.mover = false;
        this.modoEdicion = false;

    }

    /**
     * Método que comprueba si la herramienta a dibujar es una linea
     *
     * @return muestra true si es una linea y false si no
     */
    public boolean getLinea() {
        return herramientaDibujo == NHerramientaDibujo.linea;
    }

    /**
     * Método que comprueba si la herramienta a dibujar es un rectangulo
     *
     * @return muestra true si es un rectangulo y false si no
     */
    public boolean getRectangulo() {
        return herramientaDibujo == NHerramientaDibujo.rectangulo;
    }

    /**
     * Método que comprueba si la herramienta a dibujar es una elipse
     *
     * @return muestra true si es una elipse y false si no
     */
    public boolean getElipse() {
        return herramientaDibujo == NHerramientaDibujo.elipse;
    }

    /**
     * Método que comprueba si la herramienta a dibujar es una curva
     *
     * @return muestra true si es una curva y false si no
     */
    public boolean getCurva() {
        return herramientaDibujo == NHerramientaDibujo.curva;
    }

    /**
     * Método que establece la imagen base del lienzo y ajusta su tamaño
     * preferido al de la imagen.
     *
     * @param img la imagen que se va a mostrar en el lienzo.
     */
    public void setImage(BufferedImage img) {
        this.img = img;
        if (img != null) {
            setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        }
    }

    /**
     * Método que obtiene la imagen actualmente cargada en el lienzo.
     *
     * @return muestra la imagen base del lienzo, o null si no hay ninguna
     * establecida.
     */
    public BufferedImage getImage() {
        return img;
    }

    /**
     * Método que obtiene una imagen compuesta que incluye la imagen base y las
     * figuras dibujadas en el lienzo.
     *
     * @return devuelve una nueva BufferedImage que representa el lienzo
     * completo con las figuras, o null si no hay imagen base.
     */
    public BufferedImage getPaintedImage() {
        if (img == null) {
            return null;
        }

        BufferedImage imgout = new BufferedImage(
                img.getWidth(),
                img.getHeight(),
                img.getType()
        );

        Graphics2D g2dImagen = imgout.createGraphics();

        // (1) Pintar imagen base
        g2dImagen.drawImage(img, 0, 0, this);

        // (2) Pintar figuras del vector
        for (NShape s : vShape) {
            s.draw(g2dImagen);
        }
        return imgout;
    }

    /**
     * Método que activa o desactiva el modo de edición de figuras.
     *
     * @param modo toma el valor true para activar el modo de edición y false
     * para desactivarlo.
     */
    public void setModoEdicion(boolean modo) {
        this.modoEdicion = modo;
        if (modo) {
            // Desactivamos los demás
            this.modoFijar = false;
            this.modoEliminar = false;
        }
        if (!modo && forma != null) {
            forma.setSeleccionada(false);
            forma = null;

        }
        repaint();
    }

    /**
     * Método que comprueba si el modo de edición está activado.
     *
     * @return devuelve true si el modo de edición está activo o false en caso
     * contrario.
     */
    public boolean getModoEdicion() {
        return modoEdicion;
    }

    /**
     * Método que activa o desactiva el modo de fijar figuras. Al activarlo, se
     * desactivan los modos de eliminar y edición.
     *
     * @param fijar toma el valor true para activar el modo de fijar o false
     * para desactivarlo.
     */
    public void setModoFijar(boolean fijar) {
        this.modoFijar = fijar;
        if (fijar) {
            this.modoEliminar = false;
            this.modoEdicion = false;
            this.mover = false;
        }

        repaint();
    }

    /**
     * Método que consulta si el modo de fijar está activado.
     *
     * @return devuelve true si el modo de fijar está activo o false en caso
     * contrario.
     */
    public boolean getModoFijar() {
        return modoFijar;
    }

    /**
     * Método que activa o desactiva el modo de eliminar figuras.
     *
     * @param eliminar toma el valor de true para activar el modo de eliminar o
     * false para desactivarlo.
     */
    public void setModoEliminar(boolean eliminar) {
        this.modoEliminar = eliminar;
        if (eliminar) {
            this.modoFijar = false;
            this.modoEdicion = false;
            this.mover = false;
        }
        repaint();
    }

    /**
     * Método que consulta si el modo de eliminar está activado.
     *
     * @return devuelve el valor de true si el modo de eliminar está activo o
     * false en caso contrario.
     */
    public boolean getModoEliminar() {
        return modoEliminar;
    }

    /**
     * Método que establece el archivo de sonido que se reproducirá al fijar una
     * figura.
     *
     * @param f archivo de audio a usar como sonido de fijado.
     */
    public void setSonidoFijar(File f) {
        this.sonidoFijar = f;
    }

    /**
     * Método que obtiene el archivo de sonido configurado para la acción de
     * fijar figuras.
     *
     * @return Devuelve el archivo de sonido asignado al fijado, o null si no se
     * ha establecido.
     */
    public File getSonidoFijar() {
        return sonidoFijar;
    }

    /**
     * Método que establece el archivo de sonido que se reproducirá al eliminar
     * una figura.
     *
     * @param f archivo de audio a usar como sonido de eliminación.
     */
    public void setSonidoEliminar(File f) {
        this.sonidoEliminar = f;
    }

    /**
     * Método que obtiene el archivo de sonido configurado para la acción de
     * eliminar figuras.
     *
     * @return devuelve el archivo de sonido asignado a la eliminación, o null
     * si no se ha establecido.
     */
    public File getSonidoEliminar() {
        return sonidoEliminar;
    }

    /**
     * ********* MÉTODOS ***********
     *
     * /**
     * Método de dibujado
     *
     * @param g graficos a dibujar
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        if (img != null) {
            //FALTA QUE FUNCIONE BIEN EL CUADRADO PORQUE AL MOVER LA VENTANA HACE COSAS RARAS
            //setAreaRecorte(g2d);

            // Transformaciones (zoom y desplazamiento)
            /*AffineTransform at = new AffineTransform();
            at.translate(offsetX, offsetY);
            at.scale(scale, scale);
            g2d.setTransform(at);*/
            g2d.drawImage(img, 0, 0, this);
        }

        for (NShape s : vShape) {
            s.draw(g2d);
        }
    }

    /**
     * Método que reproduce un archivo de sonido especificado.
     *
     * @param f el archivo de sonido a reproducir. Debe ser un archivo
     * compatible con javax.sound.sampled.AudioSystem.
     */
    public void play(File f) {
        try {
            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(f));
            sound.start();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Método que define un área de recorte (clip).
     *
     * @param g2d contexto gráfico sobre el cual se aplicará el área de recorte.
     */
    public void setAreaRecorte(Graphics2D g2d) {
        if (img != null) {
            //int width = (int) (img.getWidth() * scale);
            //int height = (int) (img.getHeight() * scale);
            //g2d.setClip(new Rectangle(offsetX, offsetY, width, height));
        }
    }

    /**
     * Método que desplaza el área visible de la imagen en el lienzo.
     *
     * @param dx cantidad de píxeles a desplazar en el eje X.
     * @param dy cantidad de píxeles a desplazar en el eje Y.
     */
    public void moverClip(int dx, int dy) {
        //offsetX += dx;
        //offsetY += dy;
        repaint();
    }

    /**
     * Método que establece el nivel de zoom aplicado a la imagen.
     *
     * @param nuevaEscala valor de escala a aplicar. Un valor mayor que 1 amplía
     * la imagen, y un valor menor que 1 la reduce.
     */
    public void setZoom(double nuevaEscala) {
        //scale = nuevaEscala;
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

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

    /**
     * Método que controla el evento del pressed, donde se crean las figuras
     *
     * @param evt objeto que guarda el evento generado por el pressed
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        pPressed = evt.getPoint();
        if (modoEdicion) {

            forma = figuraSeleccionada(pPressed);
            for (NShape s : vShape) {
                s.setSeleccionada(s == forma);
            }
            repaint();

        } else {
            if (mover) {
                forma = figuraSeleccionada(pPressed);
            } else {
                switch (herramientaDibujo) {
                    case linea:
                        forma = new NLinea2D(pPressed, pPressed);
                        forma.setAtributos(color, grosorTrazo, relleno, alisar, transparencia);
                        break;

                    case rectangulo:

                        forma = new NRectangulo2D(pPressed.x, pPressed.y, pPressed.x, pPressed.y);
                        forma.setAtributos(color, grosorTrazo, relleno, alisar, transparencia);
                        break;

                    case elipse:
                        forma = new NElipse2D(pPressed.x, pPressed.y, pPressed.x, pPressed.y);
                        forma.setAtributos(color, grosorTrazo, relleno, alisar, transparencia);

                        break;

                    case curva:
                        if (paso1) {
                            forma = new NCurva2D(pPressed, pPressed, pPressed);
                            forma.setAtributos(color, grosorTrazo, relleno, alisar, transparencia);
                            paso1 = false;
                        } else {
                            paso1 = true;
                        }
                        break;

                }

                vShape.add(forma);
            }
        }


    }//GEN-LAST:event_formMousePressed

    /**
     * Método que controla el evento del dragged, donde se pintan las figuras
     *
     * @param evt objeto que guarda el evento generado por el dragged
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point actualPoint = evt.getPoint();

        if (mover) {
            if (forma != null) {
                if (forma instanceof NLinea2D) {
                    ((NLinea2D) forma).setLocation(actualPoint);
                } else if (forma instanceof NRectangulo2D) {
                    ((NRectangulo2D) forma).setFrame(actualPoint.x, actualPoint.y, forma.getBounds2D().getWidth(), forma.getBounds2D().getHeight());
                } else if (forma instanceof NElipse2D) {
                    ((NElipse2D) forma).setFrame(actualPoint.x, actualPoint.y, forma.getBounds2D().getWidth(), forma.getBounds2D().getHeight());
                } else if (forma instanceof NCurva2D) {
                    ((NCurva2D) forma).setLocation(actualPoint);
                }
            }

        } else {
            switch (herramientaDibujo) {
                case linea:
                    NLinea2D linea = (NLinea2D) forma;
                    linea.setLine(pPressed, actualPoint);
                    break;

                case rectangulo:
                    NRectangulo2D rectangulo = (NRectangulo2D) forma;
                    rectangulo.setFrameFromDiagonal(pPressed, actualPoint);
                    break;

                case elipse:
                    NElipse2D elipse = (NElipse2D) forma;
                    elipse.setFrameFromDiagonal(pPressed, actualPoint);
                    break;

                case curva:
                    if (!paso1) {
                        NCurva2D curva = (NCurva2D) forma;
                        curva.setCurve(((NCurva2D) forma).getP1(), actualPoint, actualPoint);
                    } else {
                        NCurva2D curva = (NCurva2D) forma;
                        curva.setCurve(((NCurva2D) forma).getP1(), actualPoint, ((NCurva2D) forma).getP2());
                    }
                    break;
            }
        }
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    /**
     * Método que controla el evento del ratón al entrar en el lienzo, éste
     * cambia el cursor
     *
     * @param evt objeto que guarda el evento generado por entrar al lienzo
     */
    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        if (mover)
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }//GEN-LAST:event_formMouseEntered

    /**
     * Método que controla el evento del ratón al salir del lienzo, éste cambia
     * el cursor
     *
     * @param evt objeto que guarda el evento generado por salir del lienzo
     */
    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_formMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
