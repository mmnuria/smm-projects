/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.nmm.imagen;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Esta clase implementa un operador de imagen que resalta las
 * regiones rojas de una imagen, mientras convierte el resto a escala
 * de grises.
 *
 * El criterio para mantener un píxel en color es que el componente rojo
 * supere significativamente la suma del verde y azul, de acuerdo con un
 * umbral definido.
 *
 * Extiende la clase BufferedImageOpAdapter.
 * 
 * @author mmnuria
 */
public class RojoOp extends BufferedImageOpAdapter {

    /**
     * Umbral de diferencia de color roja con respecto a los otros componentes (G y B).
     * Si {@code R - G - B >= umbral}, el píxel se conserva en color. En caso contrario,
     * se convierte a escala de grises.
     */
    private int umbral;

    /**
     * Construye una nueva instancia del filtro resaltador de rojo.
     *
     * @param umbral es el umbral mínimo de diferencia entre el componente rojo y los
     *               componentes verde y azul para que el color se mantenga.
     */
    public RojoOp(int umbral) {
        this.umbral = umbral;
    }

    /**
     * Aplica el filtro de resaltado rojo sobre la imagen fuente.
     * Los píxeles que cumplan con el criterio del umbral se mantienen en color,
     * mientras que el resto se convierte a escala de grises utilizando la media
     * de los tres componentes RGB.
     *
     * @param src  es la imagen de origen que se va a procesar.
     * @param dest es la imagen destino donde se almacena el resultado.
     *             Si es null, se crea una copia compatible automáticamente.
     * @return devuelve una imagen con el filtro aplicado.
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        int[] pixelComp = new int[srcRaster.getNumBands()];
        int[] pixelCompDest = new int[srcRaster.getNumBands()];

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);
                //Por hacer: efecto resaltar rojo --> COMPLETADO

                int r = pixelComp[0];
                int g = pixelComp[1];
                int b = pixelComp[2];

                int media = (r + g + b) / 3;

                if ((r - g - b) >= umbral) {
                    // Mantener color original
                    pixelCompDest[0] = r;
                    pixelCompDest[1] = g;
                    pixelCompDest[2] = b;
                } else {
                    // Convertir a gris
                    pixelCompDest[0] = media;
                    pixelCompDest[1] = media;
                    pixelCompDest[2] = media;
                }
                destRaster.setPixel(x, y, pixelCompDest);
            }

        }
        return dest;
    }
}
