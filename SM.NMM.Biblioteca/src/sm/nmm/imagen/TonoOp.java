/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.nmm.imagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Esta clase implementa un operador de imagen que modifica
 * el tono de los píxeles de una imagen que se asemejan a un tono
 * de referencia, desplazándolos según un ángulo definido.
 *
 * Este efecto se basa en el espacio de color HSB (Hue, Saturation, Brightness),
 * donde se compara el componente de tono de cada píxel con la del color seleccionado.
 * 
 * Si la diferencia entre tonos está dentro del umbral definido, el píxel
 * será alterado con un desplazamiento circular en el tono.
 * 
 * @author mmnuria
 */
public class TonoOp extends BufferedImageOpAdapter {

    /**
     * Tono de referencia extraído del color proporcionado, en grados [0, 360).
     */
    private final float tonoSeleccionado; // H del color C ∈ [0,360]
    
    /**
     * Umbral en grados [0, 180]. Define el rango alrededor del
     * tono seleccionado en el que se aplica el desplazamiento.
     */
    private final float umbral;           // T ∈ [0,180]
    
    /**
     * Desplazamiento angular en el tono, en grados [0, 360).
     * Se suma al tono de los píxeles afectados de forma circular.
     */
    private final float desplazamiento;   // φ ∈ [0,360]

    /**
     * Construye una nueva operación de tono.
     *
     * @param color          es el color de referencia (se extrae su componente HSB).
     * @param umbral         es el rango de tolerancia tonal para considerar un píxel como "similar".
     * @param desplazamiento es el desplazamiento angular a aplicar en los tonos similares.
     */
    public TonoOp(Color color, float umbral, float desplazamiento) {
        // Convertimos color C a HSB y tomamos su tono (H) en [0,360]
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.tonoSeleccionado = hsb[0] * 360f;
        this.umbral = umbral;
        this.desplazamiento = desplazamiento;
    }

    /**
     * Aplica el filtro de modificación de tono a la imagen de entrada.
     * 
     * Para cada píxel de la imagen, se calcula su tono en HSB y se compara con el
     * tono de referencia. Si la distancia angular entre tonos está dentro del umbral,
     * el tono se desplaza según el valor especificado. Saturación y brillo se mantienen.
     *
     * @param src  es la imagen de origen (no se modifica).
     * @param dest es la imagen de destino (puede ser null para crear una nueva).
     * @return devuelve la imagen modificada con el filtro aplicado.
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) throw new NullPointerException("src image is null");

        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }

        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();

        int[] rgb = new int[3];

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, rgb);

                // Convertimos a HSB
                float[] hsb = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
                float h = hsb[0] * 360f; // tono en [0,360]
                float s = hsb[1];
                float b = hsb[2];

                // Calculamos la distancia
                float distancia = Math.abs(tonoSeleccionado - h);
                if (distancia > 180f) {
                    distancia = 360f - distancia;
                }

                // Si el tono es similar, modificamos el tono
                if (distancia <= umbral) {
                    h = (h + desplazamiento) % 360f;
                }

                // Convertimos de vuelta a RGB
                int rgbInt = Color.HSBtoRGB(h / 360f, s, b);
                rgb[0] = (rgbInt >> 16) & 0xFF;
                rgb[1] = (rgbInt >> 8) & 0xFF;
                rgb[2] = rgbInt & 0xFF;

                destRaster.setPixel(x, y, rgb);
            }
        }

        return dest;
    }
}
