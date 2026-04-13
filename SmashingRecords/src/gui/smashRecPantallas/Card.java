package gui.smashRecPantallas;

import processing.core.PApplet;
import processing.core.PImage;
/**
 * Clase base para las tarjetas visuales de la aplicación.
 * Representa un elemento de la colección (álbum, concierto o estadística) y
 * encapsula sus datos de presentación (título, subtítulo, imagen) junto con
 * su geometría y colores de renderizado.
 *
 * <p>Las subclases concretas ({@link AlbumCard}, {@link ConcertCard}, {@link StatsCard})
 * redefinen el método {@link #display(PApplet, boolean)} para adaptar la apariencia
 * al tipo de contenido.</p>
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class Card {
    /**
     * Tipos de tarjeta disponibles en la aplicación.
     * <ul>
     *   <li>{@code ALBUM}    – Tarjeta de vinilo o CD ({@link AlbumCard}).</li>
     *   <li>{@code CONCERT}  – Tarjeta de concierto ({@link ConcertCard}).</li>
     *   <li>{@code ESTADIST} – Tarjeta de estadística ({@link StatsCard}).</li>
     * </ul>
     */
    public enum tipoCard{ALBUM, CONCERT, ESTADIST}
    /** Imagen asociada a la tarjeta (portada del álbum, foto del concierto, etc.). */
    PImage img;

    /** Título principal mostrado en la tarjeta. */
    String title;

    /** Subtítulo (artista, descripción, etc.) mostrado bajo el título. */
    String subtitle;

    /** Sección o categoría adicional de la tarjeta. */
    String section;

    /** Posición horizontal de la esquina superior izquierda en píxeles. */
    float x;

    /** Posición vertical de la esquina superior izquierda en píxeles. */
    float y;

    /** Anchura de la tarjeta en píxeles. */
    float w;

    /** Altura de la tarjeta en píxeles. */
    float h;

    /** Radio de las esquinas redondeadas del rectángulo base. */
    float b;

    /** Color naranja fuerte, usado para el estado seleccionado. */
    int naraFuerte;

    /** Color naranja suave, usado para el estado de hover. */
    int naraFlojo;

    /** Color blanco, usado para el fondo normal de la tarjeta. */
    int blanco;

    /** Color negro, usado para bordes y textos. */
    int negro;

    /** Datos en bruto procedentes de la base de datos ({@code [id, título, artista, imagen]}). */
    String[] rawData;

    /**
     * Crea una nueva {@code Card} con título, subtítulo y dimensiones explícitos.
     * Se usa principalmente para tarjetas de estadísticas.
     *
     * @param title    título principal de la tarjeta
     * @param subTitle subtítulo de la tarjeta
     * @param x        posición horizontal en píxeles
     * @param y        posición vertical en píxeles
     * @param w        anchura en píxeles
     * @param h        altura en píxeles
     */
    // Constructors
    public Card(String title, String subTitle, float x, float y, float w, float h){
        this.title = title;
        this.subtitle = subTitle;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * Crea una nueva {@code Card} a partir de un array de datos de la base de datos.
     * Se espera el formato {@code [id, título, artista]} o {@code [id, título, artista, imagen]}.
     *
     * @param info array de cadenas con los datos del elemento; {@code info[1]} es el título,
     *             {@code info[2]} el subtítulo e {@code info[3]} (si existe) la sección
     */
    public Card(String[] info){
        this.rawData = info;
        this.title = info[1];
        this.subtitle = info[2];
        this.section = info.length>3 ? info[3] : "";
    }

    /**
     * Establece la posición, dimensiones y radio de esquina de la tarjeta.
     *
     * @param x posición horizontal en píxeles
     * @param y posición vertical en píxeles
     * @param w anchura en píxeles
     * @param h altura en píxeles
     * @param b radio de las esquinas redondeadas en píxeles
     */
    //Setters
    public void setDimensions(float x, float y, float w, float h, float b){
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        this.b = b;
    }

    /**
     * Asigna la imagen que se mostrará en la tarjeta.
     *
     * @param img imagen a mostrar (portada, foto, etc.)
     */
    public void setImage(PImage img){
        this.img = img;
    }

    /**
     * Asigna la paleta de colores utilizada para renderizar la tarjeta.
     *
     * @param naraFuerte color naranja intenso (estado seleccionado)
     * @param naraFlojo  color naranja suave (estado hover)
     * @param blanco     color blanco (fondo normal)
     * @param negro      color negro (bordes y texto)
     */
    public void setCardColors(int naraFuerte, int naraFlojo, int blanco, int negro){
        this.naraFuerte = naraFuerte;
        this.naraFlojo = naraFlojo;
        this.blanco = blanco;
        this.negro = negro;
    }

    /**
     * Devuelve los datos de presentación de la tarjeta: título, subtítulo y sección.
     *
     * @return array {@code [título, subtítulo, sección]}
     */
    // Getters
    public String[] getData() {
        return new String[]{title, subtitle, section};
    }

    /**
     * Devuelve el array de datos en bruto de la base de datos tal como se almacenó
     * al construir la tarjeta.
     *
     * @return array con los datos originales de la BD ({@code [id, título, artista, ...]})
     */
    public String[] getRawData() { return rawData; }

    /**
     * Renderiza la tarjeta en pantalla con el estilo por defecto.
     * El color de fondo varía según el estado: naranja fuerte si está seleccionada,
     * naranja suave si el cursor está encima, o blanco en reposo.
     * Dibuja la imagen en el tercio izquierdo de la tarjeta y el título y subtítulo
     * centrados en los dos tercios restantes.
     *
     * @param p5           instancia de Processing usada para el dibujado
     * @param selectedCard {@code true} si la tarjeta está seleccionada; {@code false} en caso contrario
     */
    // Dibuixa la Card
    public void display(PApplet p5, boolean selectedCard){
        p5.pushStyle();

        // Color según el estado
        if(selectedCard){
            p5.fill(naraFuerte);
        }
        else if(this.mouseOver(p5)){
            p5.fill(naraFlojo);
        }
        else {
            p5.fill(blanco);
        }
        // Rectángulo base
        p5.stroke(negro);
        p5.rect(x, y, w, h, b/2);

        // Dibujo imagen
        float imgW = (w/3) - 2*b;
        float imgH = h - 2*b;
        if(img!=null){
            p5.image(img, x + b, y + b, imgW, imgH);
            p5.stroke(selectedCard? naraFuerte:blanco);
            p5.noFill(); p5.rect(x + b, y + b, imgW, imgH);
        }

        // Texto
        p5.fill(negro); p5.textSize(24); p5.textAlign(p5.CORNER);
        p5.text(title, x+30, 4.9f*y);
        p5.text(subtitle, x+30, 5.2f*y);

        // Títol
        p5.fill(0); p5.textSize(24); p5.textAlign(p5.CENTER);
        p5.text(title, x + 2*w/3, y + h/5);
        p5.text(subtitle, x + 2*w/3, y + h/2);

        p5.popStyle();
    }

    /**
     * Indica si el cursor del ratón se encuentra dentro del área rectangular de la tarjeta.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si el ratón está sobre la tarjeta; {@code false} en caso contrario
     */
    public boolean mouseOver(PApplet p5){
        return this.x < p5.mouseX && p5.mouseX < this.x + this.w &&
                this.y < p5.mouseY && p5.mouseY < this.y + this.h;
    }
}
