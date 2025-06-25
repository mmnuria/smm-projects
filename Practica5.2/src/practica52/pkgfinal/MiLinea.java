/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica52.pkgfinal;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author mmnuria
 */
class MiLinea extends Line2D.Float {
    public MiLinea(Point2D p1, Point2D p2) {
        super(p1,p2);
    }
    
    public boolean isNear(Point2D p){
        if(this.getP1().equals(this.getP2())) 
            return this.getP1().distance(p)<=2.0; //p1=p2
         
        return this.ptLineDist(p)<=2.0; // p1!=p2
    }
    @Override
    public boolean contains(Point2D p) {
        return isNear(p);
    }
    public void setLocation(Point2D pos){
        double dx=pos.getX()-this.getX1();
        double dy=pos.getY()-this.getY1();
        Point2D newp2 = new Point2D.Double(this.getX2()+dx,this.getY2()+dy);
        this.setLine(pos,newp2);
    }
}
