/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.nmm.graficos;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;

/**
 *
 * @author mmnuria
 */
public interface NShape extends Shape{
    
    /******** ATRIBUTOS *********/
    /**
     * Grosor del trazo
     */
    float[] trazo = {1f, 0f};
    
    /**
     * Relleno opaco
     */
    Composite opaco = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
    
    /**
     * Relleno con transparencia
     */
    Composite transparencia = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    
    /**
     * Borde alisado
     */
    RenderingHints alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    /**
     * Borde sin alisar
     */
    RenderingHints no_alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    
    /**
     * Almacena si la figura esta seleccionada o no
     */
    boolean seleccionada = false;
    
    /********* MÉTODOS GET/SET **********/
    /**
     * Método con el que puedes establecer todos los atributos de la figura creada
     * @param color color de la figura
     * @param trazo grosor del trazo de la figura
     * @param relleno aplicar relleno a la figura (si o no)
     * @param alisar aplicar alisado a la figura (si o no)
     * @param transparencia aplicar transparencia a la figura (si o no)
     */
    void setAtributos(Color color, int trazo, boolean relleno, boolean alisar, boolean transparencia);

    /**
     * Método que modifica el atributo color de la figura
     * @param color seleccion de color para la figura
     */
    void setColor(Color color);
    
    /**
     * Método que muestra el color de la figura
     * @return color
     */
    Color getColor();

    /**
     * Método que modifica el atributo del relleno de la figura
     * @param relleno seleccion de true o false dependiendo si 
     * quieres que la figura esté rellena o no
     */
    void setRelleno(boolean relleno);
    
    /**
     * Método que muestra si la figura está rellenada o no
     * @return si está rellena devuelve true y si no false
     */
    boolean isRelleno();
    
    /**
     * Método que modifica el atributo color de la figura
     * @param color seleccion de color para la figura
     */
    //void setGrosor(Stroke grosor);
    
    /**
     * Método que modifica el atributo del grosor del trazo de la figura
     * @param grosor seleccion del grosor del trazo para la figura
     */
    void setTrazo(int grosor);
    
     /**
     * Método que muestra el trazo de la figura
     * @return trazo
     */
    Stroke getTrazo();
    
    /**
     * Método que modifica el atributo de transparencia de la figura
     * @param transparencia seleccion de true o false dependiendo si 
     * quieres que la figura tenga transparencia o no
     */
    void setTransparencia(boolean transparencia);
    
    /**
     * Método que muestra si una figura tiene transparencia o no
     * @return si la figura tiene transparencia muestra true y si no false
     */
    boolean getTransparencia();

    /**
     * Método que modifica el atributo de opacidad de la figura
     * @param opacidad seleccion de la opacidad de la figura 
     */
    void setOpacidad(Composite opacidad);

    /**
     *  Método que muestra la opacidad de una figura
     * @return opacidad
     */
    Composite getOpacidad();

    /**
     * Método que modifica el atributo de alisado de la figura
     * @param alisar seleccion de true o false dependiendo si quieres que la 
     * figura esté alisada o no
     */
    void setAlisar(boolean alisar);

    /**
     * Método que muestra si la figura esta alisada
     * @return si está alisada el valor que muestra es true y si no false
     */
    boolean getAlisar();
    
    /**
     * Método que modifica el atributo de seleccion de la figura
     * @param seleccionada toma el valor de true o false dependiendo si quieres que la 
     * figura esté seleccionada o no
     */
    public void setSeleccionada(boolean seleccionada);

    /**
     * Método que muestra si la figura esta seleccionada
     * @return si está seleccionada el valor que muestra es true y si no false
     */
    public boolean getSeleccionada();
    
    /********* MÉTODO DE DIBUJADO **********

    /**
     * Método de dibujado
     * @param g2d gráfico a dibujar
     */
    public void draw(Graphics2D g2d);
      

}
