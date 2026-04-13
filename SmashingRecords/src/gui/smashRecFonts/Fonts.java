package gui.smashRecFonts;

import processing.core.PApplet;
import processing.core.PFont;

import static gui.smashRecFonts.Sizes.*;

/**
 * Gestiona las fuentes tipográficas de la aplicación The Smashing Records.
 * Carga y almacena un conjunto de {@link processing.core.PFont} desde los archivos
 * de datos del proyecto y proporciona accesores individuales y por índice.
 */
public class Fonts {

    /** Array interno que almacena las fuentes cargadas de la aplicación. */
    PFont[] fonts;

    /**
     * Crea una nueva instancia de {@code Fonts} y carga todas las fuentes de la aplicación.
     * @param p5 instancia de Processing necesaria para invocar {@code createFont}
     */
    // Constructor
    public Fonts(PApplet p5){ this.setFonts(p5); }

    /**
     * Carga las fuentes de la aplicación desde los archivos en la carpeta {@code data/}
     * y las almacena en el array interno {@link #fonts}.
     * @param p5 instancia de Processing necesaria para invocar {@code createFont}
     */
    // Establecer fuentes
    public void setFonts(PApplet p5){
        this.fonts = new PFont[5];
        this.fonts[0] = p5.createFont("data/DonGraffiti.otf", medidaTitulo);
        this.fonts[1] = p5.createFont("data/Halloween.ttf", medidaTitulo-8);
        this.fonts[2] = p5.createFont("data/coolveticaRG.otf", medidaSubtitulo);
        this.fonts[3] = p5.createFont("data/hack_regular.ttf", medidaParrafo);
    }

    /**
     * Devuelve el número total de fuentes cargadas.
     * @return número de fuentes disponibles
     */
    // Getters
    public int getNumFonts(){ return this.fonts.length; }

    /**
     * Devuelve la primera fuente (DonGraffiti), usada para títulos principales.
     * @return fuente en índice 0
     */
    public PFont getFirstFont(){ return this.fonts[0]; }

    /**
     * Devuelve la segunda fuente (Halloween), usada para títulos secundarios.
     * @return fuente en índice 1
     */
    public PFont getSecondFont(){ return this.fonts[1]; }

    /**
     * Devuelve la tercera fuente (Coolvetica), usada para subtítulos y la barra lateral.
     * @return fuente en índice 2
     */
    public PFont getThirdFont(){ return this.fonts[2]; }

    /**
     * Devuelve la cuarta fuente (Hack Regular), usada para párrafos y campos de texto.
     * @return fuente en índice 3
     */
    public PFont getForthFont(){ return this.fonts[3]; }

    /**
     * Devuelve la fuente correspondiente al índice indicado.
     * @param i índice de la fuente (0–3)
     * @return fuente en la posición {@code i}
     */
    public PFont getFontAt(int i){ return this.fonts[i]; }

    /**
     * Dibuja una muestra de todas las fuentes disponibles en pantalla,
     * útil para depuración y previsualización tipográfica.
     * @param p5 instancia de Processing usada para el dibujado
     * @param x  posición horizontal de inicio en píxeles
     * @param y  posición vertical de la primera fuente en píxeles
     * @param h  separación vertical entre fuentes en píxeles
     */
    // Dibuja las fuentes de la app
    void displayFonts(PApplet p5, float x, float y, float h){
        p5.pushStyle();
            for(int i=0; i<getNumFonts(); i++){
                p5.fill(0); p5.stroke(0); p5.strokeWeight(3);
                p5.textFont(getFontAt(i));
                p5.text("Tipografía "+i, x, y+i*h);
            }
        p5.popStyle();
    }
}
