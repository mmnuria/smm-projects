package practica6.pkgfinal;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mmnuria
 */
public class MiElipse extends Ellipse2D.Float {
    
    public MiElipse(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
      public boolean isNear(Point2D p) {
        // Verificar si está dentro de la elipse con un margen de tolerancia
        // Calcular el centro de la elipse
        double centerX = getX() + getWidth() / 2.0;
        double centerY = getY() + getHeight() / 2.0;
        
        // Calcular distancia al centro
        double distance = p.distance(centerX, centerY);
        
        // Radio promedio
        double avgRadius = (getWidth() + getHeight()) / 4.0;
        
        // Verificar si está cerca (dentro o a 2 unidades del borde)
        return distance <= avgRadius + 2.0;
    }
    
    @Override
    public boolean contains(Point2D p) {
        return isNear(p);
    }
 
    public void setLocation(Point2D pos) {
        // Mantener el ancho y alto originales
        float width = this.width;
        float height = this.height;
        
        // Establecer el nuevo frame
        this.setFrame(
            (float)pos.getX(), 
            (float)pos.getY(), 
            width, 
            height
        );
    }
    
    
}