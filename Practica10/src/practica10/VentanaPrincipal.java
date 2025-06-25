/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package practica10;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import sm.nmm.graficos.NHerramientaDibujo;
import sm.nmm.iu.Lienzo2D;
import sm.image.KernelProducer;

/**
 *
 * @author mmnuria
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    private BufferedImage imgFuente;
    private BufferedImage imgFuenteContraste;
    private BufferedImage imgEmborronar;
    private BufferedImage imgPerfilar;

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
        this.setSize(700, 700);

    }

    private Lienzo2D getSelectedLienzo() {
        VentanaInterna vi;
        vi = (VentanaInterna) escritorio.getSelectedFrame();
        return vi != null ? vi.getLienzo2D() : null;
    }

    private String getFileExtension(File f) {
        String extension = "";
        String filename = f.getName();
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i + 1);
        }
        return extension;
    }

    private Kernel crearMascaraMedia(int tam) {
        float[] datos = new float[tam * tam];
        float valor = 1.0f / (tam * tam);
        Arrays.fill(datos, valor);
        return new Kernel(tam, tam, datos);
    }

    private void aplicarFiltro(String filtro) {
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi == null) {
            return;
        }

        BufferedImage img = vi.getLienzo2D().getImage();
        if (img == null) {
            return;
        }

        Kernel k = null;

        switch (filtro) {
            case "Emborronamiento media":
                k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
                break;
            case "Emborronamiento binomial":
                k = KernelProducer.createKernel(KernelProducer.TYPE_BINOMIAL_3x3);
                break;
            case "Enfoque":
                k = KernelProducer.createKernel(KernelProducer.TYPE_ENFOQUE_3x3);
                break;
            case "Relieve":
                k = KernelProducer.createKernel(KernelProducer.TYPE_RELIEVE_3x3);
                break;
            case "Fronteras (Laplaciano)":
                k = KernelProducer.createKernel(KernelProducer.TYPE_LAPLACIANA_3x3);
                break;
            case "Media 5x5":
                k = crearMascaraMedia(5);
                break;
            case "Media 7x7":
                k = crearMascaraMedia(7);
                break;
        }

        if (k != null) {
            ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
            BufferedImage imgFiltrada = cop.filter(img, null);
            vi.getLienzo2D().setImage(imgFiltrada);
            escritorio.repaint();
        }

    }

    private void aplicarEmborronamientoInteractivo(int tam) {
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi == null || imgEmborronar == null) {
            return;
        }

        Kernel k = crearMascaraMedia(tam);
        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage filtrada = cop.filter(imgEmborronar, null);
        vi.getLienzo2D().setImage(filtrada);
        escritorio.repaint();

    }

    private Kernel crearMascaraPerfilado(int a) {
        float[] datos = {
            0, -a, 0,
            -a, 4 * a + 1, -a,
            0, -a, 0
        };
        return new Kernel(3, 3, datos);
    }

    private void aplicarPerfiladoInteractivo(int a) {
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi == null) {
            return;
        }

        BufferedImage img = vi.getLienzo2D().getImage();
        if (img == null) {
            return;
        }

        Kernel k = crearMascaraPerfilado(a);
        ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage filtrada = cop.filter(img, null);
        vi.getLienzo2D().setImage(filtrada);
        escritorio.repaint();
    }

    private BufferedImage getImageBand(BufferedImage img, int banda) {
        //Creamos el modelo de color de la nueva imagen basado en un espcio de color GRAY
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ComponentColorModel cm = new ComponentColorModel(cs, false, false,
                Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);
        //Creamos el nuevo raster a partir del raster de la imagen original
        int vband[] = {banda};
        WritableRaster bRaster = (WritableRaster) img.getRaster().createWritableChild(0, 0,
                img.getWidth(), img.getHeight(), 0, 0, vband);
        //Creamos una nueva imagen que contiene como raster el correspondiente a la banda
        return new BufferedImage(cm, bRaster, false, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupFiguras = new javax.swing.ButtonGroup();
        buttonGroupArchivo = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        buttonNuevo = new javax.swing.JToggleButton();
        buttonAbrir = new javax.swing.JToggleButton();
        buttonGuardar = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        buttonLinea = new javax.swing.JToggleButton();
        buttonRectangulo = new javax.swing.JToggleButton();
        buttonElipse = new javax.swing.JToggleButton();
        buttonCurva = new javax.swing.JToggleButton();
        buttonEdicion = new javax.swing.JToggleButton();
        buttonFijar = new javax.swing.JToggleButton();
        buttonBorrar = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        panelColor = new javax.swing.JPanel();
        buttonColor = new javax.swing.JButton();
        buttonRellenar = new javax.swing.JToggleButton();
        buttonTransparencia = new javax.swing.JToggleButton();
        buttonAlisar = new javax.swing.JToggleButton();
        sliderGrosor = new javax.swing.JSlider();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        PanelSur = new javax.swing.JPanel();
        panelBarraEstado = new javax.swing.JPanel();
        LabelBarraDeEstado = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        imagenBrillo = new javax.swing.JLabel();
        sliderBrillo = new javax.swing.JSlider();
        imagenContraste = new javax.swing.JLabel();
        sliderContraste = new javax.swing.JSlider();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        comboBoxFiltros = new javax.swing.JComboBox<>();
        imagenEmborronar = new javax.swing.JLabel();
        sliderEmborronar = new javax.swing.JSlider();
        imagenPerfilar = new javax.swing.JLabel();
        sliderPerfilar = new javax.swing.JSlider();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        buttonBandas = new javax.swing.JButton();
        comboBoxEspacioColor = new javax.swing.JComboBox<>();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        imagenCombinar = new javax.swing.JLabel();
        escritorio = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        ItemNuevo = new javax.swing.JMenuItem();
        ItemAbrir = new javax.swing.JMenuItem();
        ItemGuardar = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        menuRescaleOp = new javax.swing.JMenuItem();
        menuConvolveOp = new javax.swing.JMenuItem();
        menuBandCombineOp = new javax.swing.JMenuItem();
        menuColorConvertOp = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        buttonGroupArchivo.add(buttonNuevo);
        buttonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/nuevo.png"))); // NOI18N
        buttonNuevo.setFocusable(false);
        buttonNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonNuevo);

        buttonGroupArchivo.add(buttonAbrir);
        buttonAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/abrir.png"))); // NOI18N
        buttonAbrir.setFocusable(false);
        buttonAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAbrirActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonAbrir);

        buttonGroupArchivo.add(buttonGuardar);
        buttonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar.png"))); // NOI18N
        buttonGuardar.setFocusable(false);
        buttonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonGuardar);
        jToolBar1.add(jSeparator2);

        buttonGroupFiguras.add(buttonLinea);
        buttonLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/linea.png"))); // NOI18N
        buttonLinea.setSelected(true);
        buttonLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLineaActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonLinea);
        buttonLinea.getAccessibleContext().setAccessibleDescription("");

        buttonGroupFiguras.add(buttonRectangulo);
        buttonRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/rectangulo.png"))); // NOI18N
        buttonRectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRectanguloActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonRectangulo);
        buttonRectangulo.getAccessibleContext().setAccessibleDescription("");

        buttonGroupFiguras.add(buttonElipse);
        buttonElipse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/elipse.png"))); // NOI18N
        buttonElipse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonElipseActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonElipse);

        buttonGroupFiguras.add(buttonCurva);
        buttonCurva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/curva.png"))); // NOI18N
        buttonCurva.setFocusable(false);
        buttonCurva.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonCurva.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonCurva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCurvaActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonCurva);

        buttonGroupFiguras.add(buttonEdicion);
        buttonEdicion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/seleccion.png"))); // NOI18N
        buttonEdicion.setFocusable(false);
        buttonEdicion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonEdicion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonEdicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEdicionActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonEdicion);

        buttonGroupFiguras.add(buttonFijar);
        buttonFijar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/fijar.png"))); // NOI18N
        buttonFijar.setFocusable(false);
        buttonFijar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonFijar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonFijar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFijarActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonFijar);

        buttonGroupFiguras.add(buttonBorrar);
        buttonBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar.png"))); // NOI18N
        buttonBorrar.setFocusable(false);
        buttonBorrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonBorrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBorrarActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonBorrar);
        jToolBar1.add(jSeparator1);

        panelColor.setMaximumSize(new java.awt.Dimension(30, 30));
        panelColor.setPreferredSize(new java.awt.Dimension(30, 30));
        panelColor.setLayout(new java.awt.BorderLayout());

        buttonColor.setBackground(new java.awt.Color(0, 0, 0));
        buttonColor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonColor.setMaximumSize(new java.awt.Dimension(25, 25));
        buttonColor.setMinimumSize(new java.awt.Dimension(20, 20));
        buttonColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonColorActionPerformed(evt);
            }
        });
        panelColor.add(buttonColor, java.awt.BorderLayout.CENTER);

        jToolBar1.add(panelColor);

        buttonRellenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/rellenar.png"))); // NOI18N
        buttonRellenar.setFocusable(false);
        buttonRellenar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonRellenar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonRellenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRellenarActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonRellenar);

        buttonTransparencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/transparencia.png"))); // NOI18N
        buttonTransparencia.setFocusable(false);
        buttonTransparencia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonTransparencia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonTransparencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTransparenciaActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonTransparencia);

        buttonAlisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/alisar.png"))); // NOI18N
        buttonAlisar.setFocusable(false);
        buttonAlisar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonAlisar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonAlisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAlisarActionPerformed(evt);
            }
        });
        jToolBar1.add(buttonAlisar);

        sliderGrosor.setMinimum(1);
        sliderGrosor.setSnapToTicks(true);
        sliderGrosor.setValue(1);
        sliderGrosor.setMaximumSize(new java.awt.Dimension(20, 20));
        sliderGrosor.setPreferredSize(new java.awt.Dimension(50, 50));
        sliderGrosor.setSize(new java.awt.Dimension(100, 20));
        sliderGrosor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGrosorStateChanged(evt);
            }
        });
        jToolBar1.add(sliderGrosor);
        jToolBar1.add(jSeparator3);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        PanelSur.setLayout(new java.awt.BorderLayout());

        LabelBarraDeEstado.setText("Barra de estado");

        javax.swing.GroupLayout panelBarraEstadoLayout = new javax.swing.GroupLayout(panelBarraEstado);
        panelBarraEstado.setLayout(panelBarraEstadoLayout);
        panelBarraEstadoLayout.setHorizontalGroup(
            panelBarraEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarraEstadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelBarraDeEstado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBarraEstadoLayout.setVerticalGroup(
            panelBarraEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBarraEstadoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LabelBarraDeEstado))
        );

        PanelSur.add(panelBarraEstado, java.awt.BorderLayout.SOUTH);

        jToolBar2.setRollover(true);
        jToolBar2.setMaximumSize(new java.awt.Dimension(10, 10));
        jToolBar2.setMinimumSize(new java.awt.Dimension(10, 10));

        imagenBrillo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/brillo.png"))); // NOI18N
        jToolBar2.add(imagenBrillo);

        sliderBrillo.setMinimum(1);
        sliderBrillo.setPreferredSize(new java.awt.Dimension(50, 50));
        sliderBrillo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderBrilloStateChanged(evt);
            }
        });
        sliderBrillo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusLost(evt);
            }
        });
        jToolBar2.add(sliderBrillo);

        imagenContraste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/contraste.png"))); // NOI18N
        jToolBar2.add(imagenContraste);

        sliderContraste.setPreferredSize(new java.awt.Dimension(50, 50));
        sliderContraste.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderContrasteStateChanged(evt);
            }
        });
        sliderContraste.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderContrasteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderContrasteFocusLost(evt);
            }
        });
        jToolBar2.add(sliderContraste);
        jToolBar2.add(jSeparator4);

        comboBoxFiltros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Emborronamiento media", "Emborronamiento binomial", "Enfoque (realce o perfilado)", "Relieve", "Detector de fronteras laplaciano", "Media 5x5", "Media 7x7" }));
        comboBoxFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxFiltrosActionPerformed(evt);
            }
        });
        jToolBar2.add(comboBoxFiltros);

        imagenEmborronar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/emborronar.png"))); // NOI18N
        jToolBar2.add(imagenEmborronar);

        sliderEmborronar.setMaximum(31);
        sliderEmborronar.setValue(0);
        sliderEmborronar.setPreferredSize(new java.awt.Dimension(50, 50));
        sliderEmborronar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderEmborronarStateChanged(evt);
            }
        });
        sliderEmborronar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderEmborronarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderEmborronarFocusLost(evt);
            }
        });
        jToolBar2.add(sliderEmborronar);

        imagenPerfilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/perfilar.png"))); // NOI18N
        jToolBar2.add(imagenPerfilar);

        sliderPerfilar.setMaximum(15);
        sliderPerfilar.setValue(0);
        sliderPerfilar.setPreferredSize(new java.awt.Dimension(50, 50));
        sliderPerfilar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderPerfilarStateChanged(evt);
            }
        });
        sliderPerfilar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderPerfilarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderPerfilarFocusLost(evt);
            }
        });
        jToolBar2.add(sliderPerfilar);
        jToolBar2.add(jSeparator5);

        buttonBandas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/bandas.png"))); // NOI18N
        buttonBandas.setFocusable(false);
        buttonBandas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        buttonBandas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonBandas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBandasActionPerformed(evt);
            }
        });
        jToolBar2.add(buttonBandas);

        comboBoxEspacioColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "sRGB", "YCC", "Grey" }));
        comboBoxEspacioColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxEspacioColorActionPerformed(evt);
            }
        });
        jToolBar2.add(comboBoxEspacioColor);
        jToolBar2.add(jSeparator6);

        imagenCombinar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/combinar.png"))); // NOI18N
        jToolBar2.add(imagenCombinar);

        PanelSur.add(jToolBar2, java.awt.BorderLayout.NORTH);

        getContentPane().add(PanelSur, java.awt.BorderLayout.PAGE_END);

        escritorio.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                escritorioMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 741, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );

        getContentPane().add(escritorio, java.awt.BorderLayout.CENTER);

        menuArchivo.setText("Archivo");

        ItemNuevo.setText("Nuevo");
        ItemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(ItemNuevo);

        ItemAbrir.setText("Abrir");
        ItemAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(ItemAbrir);

        ItemGuardar.setText("Guardar");
        ItemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(ItemGuardar);

        jMenuBar1.add(menuArchivo);

        jMenu1.setText("Imagen");

        menuRescaleOp.setText("RescaleOp");
        menuRescaleOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRescaleOpActionPerformed(evt);
            }
        });
        jMenu1.add(menuRescaleOp);

        menuConvolveOp.setText("ConvolveOp");
        menuConvolveOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConvolveOpActionPerformed(evt);
            }
        });
        jMenu1.add(menuConvolveOp);

        menuBandCombineOp.setText("BandCombineOp");
        menuBandCombineOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBandCombineOpActionPerformed(evt);
            }
        });
        jMenu1.add(menuBandCombineOp);

        menuColorConvertOp.setText("ColorConvertOp");
        menuColorConvertOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuColorConvertOpActionPerformed(evt);
            }
        });
        jMenu1.add(menuColorConvertOp);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLineaActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setHerramientaDibujo(NHerramientaDibujo.linea);
        }
        LabelBarraDeEstado.setText("Linea");
    }//GEN-LAST:event_buttonLineaActionPerformed

    private void buttonRectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRectanguloActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setHerramientaDibujo(NHerramientaDibujo.rectangulo);
        }
        LabelBarraDeEstado.setText("Rectangulo");
    }//GEN-LAST:event_buttonRectanguloActionPerformed

    private void buttonElipseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonElipseActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setHerramientaDibujo(NHerramientaDibujo.elipse);
        }
        LabelBarraDeEstado.setText("Elipse");
    }//GEN-LAST:event_buttonElipseActionPerformed

    private void ItemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemNuevoActionPerformed
        //AÑADIR ALGO PARA CUANDO LE DAMOS A CANCELAR O X QUE NO NOS PREGUNTE OTRA COSA ¿TRY CATCH?
        String width = JOptionPane.showInputDialog(this, "Introduce el ancho de la imagen:");
        if (width == null) {
            return;
        }
        String height = JOptionPane.showInputDialog(this, "Introduce el alto de la imagen:");
        if (height == null) {
            return;
        }

        try {
            int w = Integer.parseInt(width);
            int h = Integer.parseInt(height);
            VentanaInterna vi = new VentanaInterna();
            escritorio.add(vi);
            vi.setVisible(true);
            vi.addInternalFrameListener(new ManejadorVentanaInterna());

            BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) img.getGraphics();
            g.setColor(Color.gray);
            g.fillRect(0, 0, w, h);

            vi.getLienzo2D().setImage(img);

            // Cargar sonidos
            File f = new File(getClass().getResource("/sonidos/fijar.wav").getFile());
            vi.getLienzo2D().setSonidoFijar(f);
            f = new File(getClass().getResource("/sonidos/eliminar.wav").getFile());
            vi.getLienzo2D().setSonidoEliminar(f);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un valor válido para las dimensiones", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_ItemNuevoActionPerformed

    private void ItemAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemAbrirActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de imagen", "jpg", "png", "bmp", "gif");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try {
                File f = fileChooser.getSelectedFile();
                BufferedImage img = ImageIO.read(f);
                VentanaInterna vi = new VentanaInterna();
                vi.getLienzo2D().setImage(img);
                this.escritorio.add(vi);
                vi.setTitle(f.getName());
                vi.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al leer la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        LabelBarraDeEstado.setText("Abrir");
    }//GEN-LAST:event_ItemAbrirActionPerformed

    private void buttonCurvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCurvaActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setHerramientaDibujo(NHerramientaDibujo.curva);
        }
        LabelBarraDeEstado.setText("Curva");
    }//GEN-LAST:event_buttonCurvaActionPerformed

    private void buttonEdicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEdicionActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setMover(buttonEdicion.isSelected());
            lienzo2D.setModoEdicion(buttonEdicion.isSelected());
            buttonBorrar.setSelected(false);
            buttonFijar.setSelected(false);
        }
        LabelBarraDeEstado.setText("Modo edición");
    }//GEN-LAST:event_buttonEdicionActionPerformed

    private void buttonRellenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRellenarActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setRelleno(buttonRellenar.isSelected());
        }
        LabelBarraDeEstado.setText("Relleno");
    }//GEN-LAST:event_buttonRellenarActionPerformed

    private void buttonTransparenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTransparenciaActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setTransparencia(buttonTransparencia.isSelected());
        }
        LabelBarraDeEstado.setText("Transparencia");
    }//GEN-LAST:event_buttonTransparenciaActionPerformed

    private void buttonAlisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlisarActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setAlisar(buttonAlisar.isSelected());
        }
        LabelBarraDeEstado.setText("Alisado");
    }//GEN-LAST:event_buttonAlisarActionPerformed

    private void sliderGrosorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderGrosorStateChanged
        int grosor = (int) sliderGrosor.getValue();
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setTrazo(grosor);
        }
        LabelBarraDeEstado.setText("Cambio de grosor");
    }//GEN-LAST:event_sliderGrosorStateChanged

    private void buttonColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonColorActionPerformed
        Color color = JColorChooser.showDialog(this, "Elige un color", Color.red);
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setColor(color);
            buttonColor.setBackground(color);
        }
    }//GEN-LAST:event_buttonColorActionPerformed

    private void ItemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemGuardarActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getPaintedImage();
            if (img != null) {
                JFileChooser dlg = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos JPG", "jpg", "jpeg");
                dlg.setFileFilter(filter);
                int resp = dlg.showSaveDialog(this);
                if (resp == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = dlg.getSelectedFile();
                        String extension = getFileExtension(f);
                        ImageIO.write(img, extension, f);
                        vi.setTitle(f.getName());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al guardar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_ItemGuardarActionPerformed

    private void buttonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNuevoActionPerformed
        ItemNuevoActionPerformed(evt);
    }//GEN-LAST:event_buttonNuevoActionPerformed

    private void buttonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAbrirActionPerformed
        ItemAbrirActionPerformed(evt);
    }//GEN-LAST:event_buttonAbrirActionPerformed

    private void buttonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGuardarActionPerformed
        ItemGuardarActionPerformed(evt);
    }//GEN-LAST:event_buttonGuardarActionPerformed

    private void escritorioMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_escritorioMouseMoved


    }//GEN-LAST:event_escritorioMouseMoved

    private void buttonFijarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFijarActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setModoFijar(buttonFijar.isSelected());
            buttonBorrar.setSelected(false);
            buttonEdicion.setSelected(false);
        }
        LabelBarraDeEstado.setText("Modo fijar");
    }//GEN-LAST:event_buttonFijarActionPerformed

    private void buttonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBorrarActionPerformed
        Lienzo2D lienzo2D = getSelectedLienzo();
        if (lienzo2D != null) {
            lienzo2D.setModoEliminar(buttonBorrar.isSelected());
            buttonFijar.setSelected(false);
            buttonEdicion.setSelected(false);
        }
        LabelBarraDeEstado.setText("Modo borrar");
    }//GEN-LAST:event_buttonBorrarActionPerformed

    private void menuRescaleOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRescaleOpActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                try {
                    RescaleOp rop = new RescaleOp(1.0F, 100.0F, null);
                    rop.filter(img, img);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuRescaleOpActionPerformed

    private void menuConvolveOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConvolveOpActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                float filtro[] = {
                    0.1f, 0.1f, 0.1f,
                    0.1f, 0.2f, 0.1f,
                    0.1f, 0.1f, 0.1f
                };
                Kernel k = new Kernel(3, 3, filtro);
                ConvolveOp cop = new ConvolveOp(k);

                // aplicar a imagen nueva
                BufferedImage imgdest = cop.filter(img, null);
                vi.getLienzo2D().setImage(imgdest);
                vi.getLienzo2D().repaint();
            }
        }
    }//GEN-LAST:event_menuConvolveOpActionPerformed

    private void sliderBrilloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            ColorModel cm = vi.getLienzo2D().getImage().getColorModel();
            WritableRaster raster = vi.getLienzo2D().getImage().copyData(null);
            boolean alfaPre = vi.getLienzo2D().getImage().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_sliderBrilloFocusGained

    private void sliderBrilloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusLost
        imgFuente = null;
        sliderBrillo.setValue(0);
    }//GEN-LAST:event_sliderBrilloFocusLost

    private void sliderBrilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderBrilloStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null && imgFuente != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                try {
                    int brillo = sliderBrillo.getValue();
                    RescaleOp rop = new RescaleOp(1.0f, brillo, null);
                    rop.filter(imgFuente, img);
                    escritorio.repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderBrilloStateChanged

    private void sliderContrasteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderContrasteFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            ColorModel cm = vi.getLienzo2D().getImage().getColorModel();
            WritableRaster raster = vi.getLienzo2D().getImage().copyData(null);
            boolean alfaPre = vi.getLienzo2D().getImage().isAlphaPremultiplied();
            imgFuenteContraste = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_sliderContrasteFocusGained

    private void sliderContrasteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderContrasteFocusLost
        imgFuenteContraste = null;
        sliderContraste.setValue(0);
    }//GEN-LAST:event_sliderContrasteFocusLost

    private void sliderContrasteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderContrasteStateChanged
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null && imgFuenteContraste != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                try {
                    float escala = sliderContraste.getValue() / 100.0f;
                    RescaleOp rop = new RescaleOp(escala, 0.0f, null);
                    rop.filter(imgFuenteContraste, img);
                    escritorio.repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

    }//GEN-LAST:event_sliderContrasteStateChanged

    private void comboBoxFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxFiltrosActionPerformed
        String filtroSeleccionado = (String) comboBoxFiltros.getSelectedItem();
        aplicarFiltro(filtroSeleccionado);
    }//GEN-LAST:event_comboBoxFiltrosActionPerformed

    private void sliderEmborronarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderEmborronarStateChanged
        if (!sliderEmborronar.getValueIsAdjusting()) {
            int size = sliderEmborronar.getValue();
            if (size % 2 == 0) {
                size++; // solo tamaños impares
            }
            aplicarEmborronamientoInteractivo(size);
        }
    }//GEN-LAST:event_sliderEmborronarStateChanged

    private void sliderEmborronarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderEmborronarFocusGained
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                imgEmborronar = img;
            }
        }
    }//GEN-LAST:event_sliderEmborronarFocusGained

    private void sliderEmborronarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderEmborronarFocusLost
        imgEmborronar = null;
        sliderEmborronar.setValue(0);
    }//GEN-LAST:event_sliderEmborronarFocusLost

    private void sliderPerfilarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderPerfilarStateChanged
        if (!sliderPerfilar.getValueIsAdjusting()) {
            int a = sliderPerfilar.getValue();
            aplicarPerfiladoInteractivo(a);
        }
    }//GEN-LAST:event_sliderPerfilarStateChanged

    private void sliderPerfilarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderPerfilarFocusGained
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                imgPerfilar = img;
            }
        }
    }//GEN-LAST:event_sliderPerfilarFocusGained

    private void sliderPerfilarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderPerfilarFocusLost
        imgPerfilar = null;
        sliderPerfilar.setValue(0);
    }//GEN-LAST:event_sliderPerfilarFocusLost

    private void menuBandCombineOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBandCombineOpActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                try {
                    float[][] matriz = {{1.0F, 0.0F, 0.0F},
                    {0.0F, 0.0F, 1.0F},
                    {0.0F, 1.0F, 0.0F}};
                    BandCombineOp bcop = new BandCombineOp(matriz, null);
                    bcop.filter(img.getRaster(), img.getRaster());
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuBandCombineOpActionPerformed

    private void menuColorConvertOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuColorConvertOpActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                try {
                    ColorSpace cs = new sm.image.color.GreyColorSpace();//ColorSpace.getInstance(ColorSpace.CS_GRAY);
                    ColorConvertOp op = new ColorConvertOp(cs, null);
                    BufferedImage imgdest = op.filter(img, null);
                    vi.getLienzo2D().setImage(imgdest);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuColorConvertOpActionPerformed

    private void buttonBandasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBandasActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                for(int i = 0; i<img.getRaster().getNumBands(); i++){
                    BufferedImage imgBanda = getImageBand(img,i);
                    vi = new VentanaInterna();
                    vi.getLienzo2D().setImage(imgBanda);
                    escritorio.add(vi);
                    vi.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_buttonBandasActionPerformed

    private void comboBoxEspacioColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxEspacioColorActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo2D().getImage();
            if (img != null) {
                ColorSpace cs = null;
                int seleccion = comboBoxEspacioColor.getSelectedIndex();
                // Asignar el espacio de color según la selección
                switch (seleccion) {
                    case 0: // RGB
                        cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                        break;
                    case 1: // YCbCr (YCC)
                        cs = new sm.image.color.YCbCrColorSpace();
                        break;
                    case 2: // GRAY
                        cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                        break;
                    default:
                        System.err.println("Espacio de color no soportado");
                        return;
                }
                try {
                    ColorConvertOp op = new ColorConvertOp(cs, null);
                    BufferedImage imgdest = op.filter(img, null);
                    
                    VentanaInterna viTransf = new VentanaInterna();
                    viTransf.getLienzo2D().setImage(imgdest);
                    escritorio.add(viTransf);
                    viTransf.setVisible(true);  
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_comboBoxEspacioColorActionPerformed

    private class ManejadorVentanaInterna extends InternalFrameAdapter {

        public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            VentanaInterna vi = (VentanaInterna) evt.getInternalFrame();
            buttonLinea.setSelected(vi.getLienzo2D().getLinea());
            buttonRectangulo.setSelected(vi.getLienzo2D().getRectangulo());
            buttonElipse.setSelected(vi.getLienzo2D().getCurva());
            buttonCurva.setSelected(vi.getLienzo2D().getCurva());
            buttonEdicion.setSelected(vi.getLienzo2D().getModoEdicion());
            buttonFijar.setSelected(vi.getLienzo2D().getModoFijar());
            buttonBorrar.setSelected(vi.getLienzo2D().getModoEliminar());
            buttonRellenar.setSelected(vi.getLienzo2D().isRelleno());
            buttonTransparencia.setSelected(vi.getLienzo2D().getTransparencia());
            buttonAlisar.setSelected(vi.getLienzo2D().getAlisar());
            sliderBrillo.setValue(50);
            sliderContraste.setValue(50);
            sliderEmborronar.setValue(0);
            sliderPerfilar.setValue(0);
            //preguntar como hacer que vuelva el color tambien al que tenga guardado la ventana interna

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ItemAbrir;
    private javax.swing.JMenuItem ItemGuardar;
    private javax.swing.JMenuItem ItemNuevo;
    private javax.swing.JLabel LabelBarraDeEstado;
    private javax.swing.JPanel PanelSur;
    private javax.swing.JToggleButton buttonAbrir;
    private javax.swing.JToggleButton buttonAlisar;
    private javax.swing.JButton buttonBandas;
    private javax.swing.JToggleButton buttonBorrar;
    private javax.swing.JButton buttonColor;
    private javax.swing.JToggleButton buttonCurva;
    private javax.swing.JToggleButton buttonEdicion;
    private javax.swing.JToggleButton buttonElipse;
    private javax.swing.JToggleButton buttonFijar;
    private javax.swing.ButtonGroup buttonGroupArchivo;
    private javax.swing.ButtonGroup buttonGroupFiguras;
    private javax.swing.JToggleButton buttonGuardar;
    private javax.swing.JToggleButton buttonLinea;
    private javax.swing.JToggleButton buttonNuevo;
    private javax.swing.JToggleButton buttonRectangulo;
    private javax.swing.JToggleButton buttonRellenar;
    private javax.swing.JToggleButton buttonTransparencia;
    private javax.swing.JComboBox<String> comboBoxEspacioColor;
    private javax.swing.JComboBox<String> comboBoxFiltros;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.JLabel imagenBrillo;
    private javax.swing.JLabel imagenCombinar;
    private javax.swing.JLabel imagenContraste;
    private javax.swing.JLabel imagenEmborronar;
    private javax.swing.JLabel imagenPerfilar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem menuBandCombineOp;
    private javax.swing.JMenuItem menuColorConvertOp;
    private javax.swing.JMenuItem menuConvolveOp;
    private javax.swing.JMenuItem menuRescaleOp;
    private javax.swing.JPanel panelBarraEstado;
    private javax.swing.JPanel panelColor;
    private javax.swing.JSlider sliderBrillo;
    private javax.swing.JSlider sliderContraste;
    private javax.swing.JSlider sliderEmborronar;
    private javax.swing.JSlider sliderGrosor;
    private javax.swing.JSlider sliderPerfilar;
    // End of variables declaration//GEN-END:variables
}
