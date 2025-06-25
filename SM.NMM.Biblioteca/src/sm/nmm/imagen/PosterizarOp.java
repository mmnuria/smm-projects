/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.nmm.imagen;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Esta clase implementa un operador de imagen que aplica
 * un efecto de posterización sobre una imagen. Este efecto reduce la cantidad
 * de niveles de color posibles en la imagen, creando áreas de color plano y
 * bordes definidos.
 *
 * Extiende la clase BufferedImageOpAdapter.
 *
 * El nivel de posterización se controla mediante el parámetro niveles,
 * que define cuántos niveles distintos de color se deben mantener.
 *
 * Por ejemplo, si niveles = 2, cada componente de color (RGB) solo
 * podrá tomar dos valores posibles, resultando en una imagen con apariencia
 * muy simplificada.
 * 
 * Añado que esta operación se aplica por canal (banda), por lo
 * que afecta a cada componente RGB de forma independiente.
 * 
 * @author mmnuria
 */
public class PosterizarOp extends BufferedImageOpAdapter {

    /**
     * Número de niveles de color deseados tras la posterización.
     * Debe ser un entero positivo mayor que 1.
     */
    private int niveles;

    /**
     * Crea un nuevo filtro de posterización con el número de niveles especificado.
     *
     * @param niveles número de niveles de color (por canal) que se desean conservar.
     *                Por ejemplo, 4 dividirá cada canal en 4 niveles de intensidad.
     */
    public PosterizarOp(int niveles) {
        this.niveles = niveles;
    }

    /**
     * Aplica el efecto de posterización a la imagen de origen "src" y escribe
     * el resultado en "dest". Si "dest es null, se crea una
     * imagen de destino compatible automáticamente.
     *
     * El algoritmo funciona dividiendo los valores de color en bloques de tamaño
     * uniforme y asignando cada píxel al valor base de su bloque.
     *
     * @param src  es la imagen de origen que se va a procesar.
     * @param dest es la imagen destino donde se almacenará el resultado.
     *             Si es null, se crea una nueva imagen compatible.
     * @return devuelve la imagen posterizada.
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
        int sample;

        float K = 256.f/niveles;
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    sample = srcRaster.getSample(x, y, band);
                    
                    //Por hacer: efecto posterizar --> COMPLETADO
                    sample = (int)(K * (int)(sample/K));
                    destRaster.setSample(x, y, band, sample);
                }
            }
        }
        return dest;
    }

}

