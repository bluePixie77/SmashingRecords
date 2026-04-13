package gui.smashRecFonts;

/**
 * Clase de constantes estáticas que define los tamaños tipográficos estándar
 * utilizados en toda la interfaz gráfica de la aplicación.
 *
 * <p>Al ser campos estáticos públicos, pueden usarse directamente desde cualquier
 * clase sin necesidad de instanciar {@code Sizes}.</p>
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class Sizes {
    // static para poder acceder sin un objeto;
    /** Tamaño en puntos para textos de título principal (60 pt). */
    public static float medidaTitulo = 60;

    /** Tamaño en puntos para textos de subtítulo (30 pt). */
    public static float medidaSubtitulo = 30;

    /** Tamaño en puntos para textos de tamaño intermedio, como etiquetas de sección (25 pt). */
    public static float medidaIntermedia = 25;

    /** Tamaño en puntos para textos de párrafo y contenido general (20 pt). */
    public static float medidaParrafo = 20;
}
