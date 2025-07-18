package practica52.pkgfinal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author mmnuria
 */
public class Lienzo extends javax.swing.JPanel { //NO USAR NUNCA GRAPHICS SIEMPRE GRAPHICS2D
    
    private Shape forma;
    private List<Shape> vShape = new ArrayList();
    private Color color = Color.red;
    private boolean relleno = false;
    private boolean mover = false;
    private Point pPressed;
    public enum HerramientaDibujo{linea,rectangulo,elipse};
    
    private HerramientaDibujo herramientaDibujo;

    /**
     * Creates new form Lienzo
     */
    public Lienzo() {
        initComponents();
        this.herramientaDibujo = HerramientaDibujo.linea;//necesito incializarlo para que no sea null
        
    }
    @Override //ponerlo siempre
    public void paint(Graphics g) { //Hay que tenerlo siempre, todo depende en sobrecargar este metodo (EXAMEN). NO SE AÑADEN NEW EN EL PAINT
        super.paint(g); //haz lo que tenia el padre y ahora me añades el resto
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setPaint(color);
        
        for(Shape s:vShape){
            if(relleno) g2d.fill(s);
            g2d.draw(s);//si no hay draw, sabemos que esta mal
        }    
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setRelleno(boolean relleno){
        this.relleno = relleno;
    }
    
    public boolean isRelleno(){
        return relleno;
    }
    
    public void setHerramientaDibujo(HerramientaDibujo eleccion){
        this.herramientaDibujo = eleccion;
    }
    
    public HerramientaDibujo getHerramientaDibujo(){
        return herramientaDibujo;
    }
    
    public void setMover(boolean mover){
        this.mover = mover; 
    }
    
    public boolean getMover(){
        return mover;
    }
    
    private Shape figuraSeleccionada(Point2D p){
        for(Shape s:vShape){
            if (s.contains(p)){
                return s;
            }
        }
        return null; //DEVUELVE SIEMPRE NULL CON LINEA Y ELIPSE, PORQUE?
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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
       pPressed = evt.getPoint();
       if(mover){
           
            forma = figuraSeleccionada(pPressed);
            System.out.println("forma 2" +forma);
            
        }else{
           switch(herramientaDibujo){
                case linea:
                    forma = new MiLinea.Float(pPressed,pPressed); //Se crea
                    System.out.println("forma 1" +forma);
                    break;
                case rectangulo:
                    forma = new Rectangle();
                    break;
                case elipse:
                    forma = new MiElipse.Float(pPressed.x,pPressed.y,0,0);
                    break;
            }
           vShape.add(forma);
       }
        
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
       //no es necesario, porque sera el mismo que el Dragged, pero si se tiene que hacer algo mas al terminar, si seria util
    }//GEN-LAST:event_formMouseReleased

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point actualPoint = evt.getPoint();
        
        if(mover){
            System.out.println("forma 3" +forma);
            if (forma != null) {
                if (forma instanceof Rectangle)
                    ((Rectangle)forma).setLocation(actualPoint);
                else if (forma instanceof MiLinea)
                    ((MiLinea)forma).setLocation(actualPoint);
                else if (forma instanceof MiElipse)
                    ((MiElipse)forma).setLocation(actualPoint);
            }
        }else{
            /*if(forma instanceof MiLinea) {
                ((MiLinea) forma).setLine(pPressed, actualPoint);
            }
            else if(forma instanceof Rectangle) {
                ((Rectangle) forma).setFrameFromDiagonal(pPressed, actualPoint);
            }
            else if(forma instanceof MiElipse) {
                ((MiElipse) forma).setFrameFromDiagonal(pPressed, actualPoint);
            }*/
            /*switch (herramientaDibujo) {
                case linea:
                    MiLinea linea = (MiLinea) forma;
                    linea.setLine(pPressed, actualPoint);
                    break;
                    
                case rectangulo:
                    ((Rectangle)forma).setFrameFromDiagonal(pPressed, actualPoint);
                    break;
                    
                case elipse:
                    ((MiElipse)forma).setFrameFromDiagonal(pPressed, actualPoint);
                    break;
            }*/
            if(forma instanceof Line2D) {
                Line2D linea = (Line2D) forma;
                linea.setLine(pPressed, actualPoint);
            }
            else if(forma instanceof Rectangle) {
                Rectangle rectangulo = (Rectangle) forma;
                rectangulo.setFrameFromDiagonal(pPressed, actualPoint);
            }
            else if(forma instanceof Ellipse2D) {
                Ellipse2D elipse = (Ellipse2D) forma;
                elipse.setFrameFromDiagonal(pPressed, actualPoint);
            }
        }
        
        this.repaint(); 
    }//GEN-LAST:event_formMouseDragged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
