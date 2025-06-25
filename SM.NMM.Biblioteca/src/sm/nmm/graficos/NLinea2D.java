/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.nmm.graficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Clase personalizada que sustituye a Line2D
 *
 * @author mmnuria
 */
public class NLinea2D extends Line2D.Float implements NShape {

    /**
     * ********** ATRIBUTOS **********
     */
    /**
     * Color de la figura
     */
    private Color color;

    /**
     * Controla la opacidad de la figura
     */
    private boolean transparencia;

    /**
     * Opacidad de la figura
     */
    private Composite opacidad;

    /**
     * Controla el relleno de la figura
     */
    private boolean relleno;

    /**
     * Grosor de la figura
     */
    private Stroke trazo;

    /**
     * Tipo de suavizado de la figura
     */
    private RenderingHints alisado;

    /**
     * Controla el suavizado de la figura
     */
    private boolean alisar;

    /**
     * Almacena si la figura esta seleccionada o no
     */
    private boolean seleccionada;

    /**
     * ********* CONSTRUCTOR *********
     */
    /**
     * Constructor de la Linea2D
     *
     * @param p1 Punto inicial
     * @param p2 Punto final
     */
    public NLinea2D(Point2D p1, Point2D p2) {
        super(p1, p2);

        color = Color.BLACK;
        transparencia = false;
        opacidad = NShape.opaco;
        relleno = false;
        trazo = new BasicStroke();
        alisado = NShape.no_alisado;
        alisar = false;
    }

    /**
     * ********* MÉTODO DE DIBUJADO **********
     */
    /**
     * Método de dibujado
     *
     * @param g2d gráfico a dibujar
     */
    @Override
    public void draw(Graphics2D g2d) {

        g2d.setPaint(color);
        g2d.setStroke(trazo);
        g2d.setRenderingHints(alisado);
        g2d.setComposite(opacidad);

        if (relleno) {
            g2d.fill(this);
        }
        g2d.draw(this);

        if (seleccionada) {
            Rectangle bounds = this.getBounds(); // Bounding box de la línea
            Stroke dashed = new BasicStroke(
                    1f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL,
                    0,
                    new float[]{5f},
                    0
            );

            g2d.setColor(Color.RED);
            g2d.setStroke(dashed);
            g2d.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

            // ▪️ Dibujar marcadores en las esquinas
            int size = 6;
            g2d.setColor(Color.RED);
            g2d.fillRect(bounds.x - size / 2, bounds.y - size / 2, size, size);
            g2d.fillRect(bounds.x + bounds.width - size / 2, bounds.y - size / 2, size, size);
            g2d.fillRect(bounds.x - size / 2, bounds.y + bounds.height - size / 2, size, size);
            g2d.fillRect(bounds.x + bounds.width - size / 2, bounds.y + bounds.height - size / 2, size, size);
        }
    }

    /**
     * ******** MÉTODOS SET/GET *********
     */
    /**
     * Método con el que puedes establecer todos los atributos de la figura
     * creada
     *
     * @param color color de la figura
     * @param trazo grosor del trazo de la figura
     * @param relleno aplicar relleno a la figura (si o no)
     * @param alisar aplicar alisado a la figura (si o no)
     * @param transparencia aplicar transparencia a la figura (si o no)
     */
    @Override
    public void setAtributos(Color color, int trazo,
            boolean relleno, boolean alisar, boolean transparencia) {
        setColor(color);
        setTrazo(trazo);
        setRelleno(relleno);
        setAlisar(alisar);
        setTransparencia(transparencia);
    }

    /**
     * Método que modifica el atributo color de la figura
     *
     * @param color seleccion de color para la figura
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Método que muestra el color de la figura
     *
     * @return color
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Método que modifica el atributo del relleno de la figura
     *
     * @param relleno seleccion de true o false dependiendo si quieres que la
     * figura esté rellena o no
     */
    @Override
    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }

    /**
     * Método que muestra si la figura está rellenada o no
     *
     * @return si está rellena devuelve true y si no false
     */
    @Override
    public boolean isRelleno() {
        return relleno;
    }

    /**
     * Método que modifica el atributo del grosor del trazo de la figura
     *
     * @param grosor seleccion del grosor del trazo para la figura
     */
    @Override
    public void setTrazo(int grosor) {
        this.trazo = new BasicStroke(grosor);
    }

    /**
     * Método que muestra el trazo de la figura
     *
     * @return trazo
     */
    @Override
    public Stroke getTrazo() {
        return trazo;
    }

    /**
     * Método que modifica el atributo de transparencia de la figura
     *
     * @param transparencia seleccion de true o false dependiendo si quieres que
     * la figura tenga transparencia o no
     */
    @Override
    public void setTransparencia(boolean transparencia) {
        if (transparencia) {
            opacidad = NShape.transparencia;
            this.transparencia = true;
        } else {
            opacidad = NShape.opaco;
            this.transparencia = false;
        }
    }

    /**
     * Método que muestra si una figura tiene transparencia o no
     *
     * @return si la figura tiene transparencia muestra true y si no false
     */
    @Override
    public boolean getTransparencia() {
        return transparencia;
    }

    /**
     * Método que modifica el atributo de opacidad de la figura
     *
     * @param opacidad seleccion de la opacidad de la figura
     */
    @Override
    public void setOpacidad(Composite opacidad) {
        this.opacidad = opacidad;
    }

    /**
     * Método que muestra la opacidad de una figura
     *
     * @return opacidad
     */
    @Override
    public Composite getOpacidad() {
        return opacidad;
    }

    /**
     * Método que modifica el atributo de alisado de la figura
     *
     * @param alisar seleccion de true o false dependiendo si quieres que la
     * figura esté alisada o no
     */
    @Override
    public void setAlisar(boolean alisar) {
        if (alisar) {
            alisado = NShape.alisado;
            this.alisar = true;
        } else {
            alisado = NShape.no_alisado;
            this.alisar = false;
        }
    }

    /**
     * Método que muestra si la figura esta alisada
     *
     * @return si está alisada el valor que muestra es true y si no false
     */
    @Override
    public boolean getAlisar() {
        return alisar;
    }

    /**
     * Método que modifica el atributo de seleccion de la figura
     *
     * @param seleccionada toma el valor de true o false dependiendo si quieres
     * que la figura esté seleccionada o no
     */
    @Override
    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    /**
     * Método que muestra si la figura esta seleccionada
     *
     * @return si está seleccionada el valor que muestra es true y si no false
     */
    @Override
    public boolean getSeleccionada() {
        return seleccionada;
    }

    /**
     * ********* MÉTODOS PRINCIPALES **********
     */
    /**
     * Método que muestra true o false en el momento de hacer click en el lienzo
     * para saber si una línea está o no cerca del punto del pressed
     *
     * @param p guarda el punto donde se hizo el pressed
     * @return muestra true en el caso de que el pressed esté cerca de 2
     * unidades y false en caso contrario
     */
    public boolean isNear(Point2D p) {
        if (this.getP1().equals(this.getP2())) {
            return this.getP1().distance(p) <= 2.0; //p1=p2
        }
        return this.ptLineDist(p) <= 2.0; // p1!=p2
    }

    /**
     * Método que sobreescribe el método contains de la clase Line2D para que
     * use la función anterior isNear, verificando si está o no cerca el pressed
     * de la línea
     *
     * @param p guarda el punto donde se hizo el pressed
     * @return muestra true en el caso de que el pressed esté cerca de 2
     * unidades y false en caso contrario
     */
    @Override
    public boolean contains(Point2D p) {
        return isNear(p);
    }

    /**
     * Método que calcula la nueva posición de una linea al moverla
     *
     * @param pos guarda el valor de la nueva posición
     */
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - this.getX1();
        double dy = pos.getY() - this.getY1();

        Point2D newp2 = new Point2D.Double(this.getX2() + dx, this.getY2() + dy);
        setLine(pos, newp2);
    }

}
