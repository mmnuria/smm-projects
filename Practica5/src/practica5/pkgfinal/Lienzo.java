package practica5.pkgfinal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author mmnuria
 */
public class Lienzo extends javax.swing.JPanel { //NO USAR NUNCA GRAPHICS SIEMPRE GRAPHICS2D
    
    private Shape forma = new Line2D.Float();
    private Color color = Color.red;
    private boolean relleno = false;
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
        
        if(relleno) g2d.fill(forma);
        
        //Linea
        Point2D p1 = new Point2D.Float(70,70);
        Point2D p2=new Point2D.Float(200,200);
        Line2D linea = new Line2D.Float(p1,p2);
        g2d.draw(linea);//si no hay draw, sabemos que esta mal
        
        //Rectangulo
        color = Color.blue;
        g2d.setPaint(color);
        Rectangle2D rectangle = new Rectangle2D.Float(50,50,50,50);
        
        g2d.draw(rectangle);
        
        //Roound rectangle
        color = Color.BLACK;
        g2d.setPaint(color);
        RoundRectangle2D roundRectangle = new RoundRectangle2D.Float(60,60,50,50,10,10);
        g2d.draw(roundRectangle);
        
        //Ellipse
        color = Color.MAGENTA;
        g2d.setPaint(color);
        Ellipse2D elipse = new Ellipse2D.Float(100,100,50,50);
        g2d.draw(elipse);
        
        //Arc2D
        color = Color.GREEN;
        g2d.setPaint(color);
        Arc2D arc = new Arc2D.Float(10,10,100,100,40,100,1);
        g2d.draw(arc);
        
        //QuadCurve2D --> PINTA LA TANGENTE DEL PUNTO DE CONTROL
        color = Color.cyan;
        g2d.setPaint(color);
        QuadCurve2D curve = new QuadCurve2D.Float(100,200,50,50,200,300);
        g2d.draw(curve);
        
        //CubicCurve2D
        color = Color.yellow;
        g2d.setPaint(color);
        CubicCurve2D cubic = new CubicCurve2D.Float(100,200,50,50,50,50,200,300);
        g2d.draw(cubic);
        
        // draw GeneralPath (polyline)
        color = Color.black;
        g2d.setPaint(color);
        int x2Points[] = {0, 100, 0, 100};
        int y2Points[] = {0, 50, 50, 0};
        GeneralPath polyline = 
                new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Points.length);

        polyline.moveTo (x2Points[0], y2Points[0]);

        for (int index = 1; index < x2Points.length; index++) {
                 polyline.lineTo(x2Points[index], y2Points[index]);
        };
        polyline.closePath();
        g2d.draw(polyline);
        
        //CREAR UNA PERA CON AREA
        
        // Crear la parte superior de la pera (forma circular)
        Ellipse2D topCircle = new Ellipse2D.Float(70, 80, 60, 60);
        Area topPear = new Area(topCircle);
        
        // Crear la parte inferior de la pera (forma más ancha)
        // Utilizamos una elipse alargada
        Ellipse2D bottomEllipse = new Ellipse2D.Float(50, 100, 100, 120);
        Area bottomPear = new Area(bottomEllipse);
        
        // Combinar todas las áreas
        Area pearShape = new Area();
        pearShape.add(topPear);
        pearShape.add(bottomPear);
        
        // Suavizar las transiciones con pequeñas elipses adicionales
        //Ellipse2D leftBlend = new Ellipse2D.Float(55, 130, 40, 80);
        //Ellipse2D rightBlend = new Ellipse2D.Float(105, 130, 40, 80);
        
        //pearShape.add(new Area(leftBlend));
        //pearShape.add(new Area(rightBlend));
        // Rellenar la pera con color verde
        g2d.setColor(Color.green);
        g2d.fill(pearShape);
        
        // Dibujar el contorno de la pera
        g2d.setColor(Color.green);
        g2d.draw(pearShape);
        
        // Dibujar el tallo de la pera
        g2d.setColor(Color.DARK_GRAY);
        
        QuadCurve2D stem = new QuadCurve2D.Float(100, 80, 90, 65, 80, 50);
        g2d.draw(stem);
        
        // Dibujar una pequeña hoja
        g2d.setColor(Color.LIGHT_GRAY);
        Area leaf = new Area();
        
        // Crear la forma de la hoja con dos elipses
        Ellipse2D leafBase = new Ellipse2D.Double(65, 45, 30, 20);
        leaf.add(new Area(leafBase));
        
        // Recortar la parte inferior para formar la hoja
        Rectangle2D clipRect = new Rectangle2D.Double(65, 55, 30, 10);
        leaf.subtract(new Area(clipRect));
        
        g2d.fill(leaf);
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
        switch(herramientaDibujo){
            case linea:
                forma = new Line2D.Float(pPressed,pPressed); //Se crea
                break;
            case rectangulo:
                forma = new Rectangle();
                break;
            case elipse:
                forma = new Ellipse2D.Float(pPressed.x,pPressed.y,0,0);
                break;
        }
        
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
       //no es necesario, porque sera el mismo que el Dragged, pero si se tiene que hacer algo mas al terminar, si seria util
    }//GEN-LAST:event_formMouseReleased

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point actualPoint = evt.getPoint();
    
        if(forma instanceof Line2D) {
            Line2D linea = (Line2D) forma;
            linea.setLine(pPressed, actualPoint);
        }
        else if(forma instanceof Rectangle2D) {
            Rectangle2D rectangulo = (Rectangle2D) forma;
            rectangulo.setFrameFromDiagonal(pPressed, actualPoint);
        }
        else if(forma instanceof Ellipse2D) {
            Ellipse2D elipse = (Ellipse2D) forma;
            elipse.setFrameFromDiagonal(pPressed, actualPoint);
        }

        this.repaint();
        
    }//GEN-LAST:event_formMouseDragged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
