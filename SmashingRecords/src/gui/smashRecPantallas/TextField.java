package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;

import static gui.smashRecFonts.Sizes.medidaIntermedia;
import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.ENTER;

/**
 * Campo de texto de una sola línea interactivo para la interfaz gráfica de la aplicación.
 * Permite al usuario introducir y editar texto mediante el teclado, con soporte para
 * caracteres especiales y acentos. Visualmente muestra un rectángulo redondeado que
 * cambia de color cuando está seleccionado.
 *
 * <p>Se integra con la librería Processing ({@code PApplet}) para el renderizado
 * y la gestión de eventos de teclado y ratón.</p>
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class TextField {

    /** Posición horizontal del campo en píxeles. */
    public float x;

    /** Posición vertical del campo en píxeles. */
    public float y;

    /** Altura del campo en píxeles. */
    public float h;

    /** Anchura del campo en píxeles. */
    public float w;

    /** Número máximo de caracteres que puede contener el campo. */
    int length;

    /** Color de fondo del campo cuando no está seleccionado. */
    int fillColor;

    /** Color de fondo del campo cuando está seleccionado. */
    int selectedColor;

    /** Color del borde y del texto del campo. */
    int borderColor;

    /** Grosor del borde del campo en píxeles. */
    int borderWeight;

    /** Cadena de texto actualmente contenida en el campo. */
    public String text = "";

    /** Tamaño en puntos del texto mostrado dentro del campo. */
    int textSize = (int) medidaIntermedia-1;

    /** Indica si el campo está actualmente seleccionado y listo para recibir entrada. */
    boolean selected = false;

    /**
     * Crea un nuevo {@code TextField} con posición, dimensiones y longitud máxima indicadas.
     * Inicializa los colores a partir de la paleta de la aplicación.
     *
     * @param p5        instancia principal de Processing, necesaria para acceder al contexto gráfico
     * @param appColors paleta de colores de la aplicación
     * @param length    número máximo de caracteres permitidos
     * @param x         posición horizontal en píxeles
     * @param y         posición vertical en píxeles
     * @param w         anchura del campo en píxeles
     * @param h         altura del campo en píxeles
     */
    // Constructor
    public TextField(PApplet p5, Colors  appColors, int length, float x, float y, float w, float h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
        this.length = length;
        setColors(appColors);
    }

    /**
     * Asigna los colores del campo a partir de la paleta de la aplicación.
     * El color inactivo se toma del quinto color, el seleccionado del tercero
     * y el borde del cuarto.
     *
     * @param appColors paleta de colores de la aplicación
     */
    // Setters
    public void setColors(Colors appColors){
        this.fillColor = appColors.getFifthColor();
        this.selectedColor = appColors.getThirdColor();
        this.borderColor = appColors.getFourthColor();
        this.borderWeight = 1;
    }

    /**
     * Renderiza el campo de texto en la pantalla.
     * Dibuja un rectángulo redondeado cuyo color de fondo varía según el estado de
     * selección, y muestra el texto actual alineado a la izquierda y centrado verticalmente.
     *
     * @param p5 instancia principal de Processing usada para el dibujado
     */
    // Dibujar
    public void display(PApplet p5) {
        p5.pushStyle();
        if (selected) {
            p5.fill(selectedColor);
        } else {
            p5.fill(fillColor);
        }

        p5.strokeWeight(borderWeight);
        p5.stroke(borderColor);
        p5.rectMode(p5.CORNER);
        p5.rect(x, y, w, h, 5);

        p5.fill(borderColor);
        p5.textSize(textSize); p5.textAlign(p5.LEFT, p5.CENTER);
        p5.text(text, x + 10, y + h - textSize);
        p5.popStyle();
    }

    /**
     * Gestiona la pulsación de teclas especiales (retroceso y enter) mediante códigos de tecla.
     * Este método maneja {@code BACKSPACE} para borrar el último carácter y {@code ENTER}
     * para deseleccionar el campo. Los caracteres imprimibles deben gestionarse mediante
     * {@link #keyTyped(char)}.
     *
     * @param key     carácter de la tecla pulsada (puede no ser imprimible)
     * @param keyCode código numérico de la tecla pulsada
     */
    // Añadir y/o quitar el texto que se teclea
    public void keyPressed(char key, int keyCode) {
        if (selected) {
            if (keyCode == (int)BACKSPACE) {
                removeText();
            } else if (keyCode == 32) {
                addText(' '); // SPACE
            } else if (keyCode == ENTER){
                selected = false;
            }else{
                addText(key);
            }
        }
    }

    /**
     * Gestiona la pulsación de la tecla de retroceso ({@code BACKSPACE}) usando solo el código
     * de tecla. Solo actúa si el campo está seleccionado.
     *
     * @param keyCode código numérico de la tecla pulsada
     */
    public void keyPressed(int keyCode) {
        if (!selected) return;

        if (keyCode == BACKSPACE) {
            removeText();
        }
    }

    /**
     * Gestiona la entrada de texto real del teclado, incluyendo caracteres con acentos
     * y otros caracteres especiales del teclado. Ignora caracteres de control como
     * salto de línea, retorno de carro y retroceso. Solo actúa si el campo está seleccionado.
     *
     * @param key carácter introducido por el usuario
     */
    // Gestiona entrada de texto real (incluye acentos)
    public void keyTyped(char key) {
        if (!selected) return;

        // Evita caracteres de control
        if (key == '\n' || key == '\r' || key == '\b') return;

        addText(key);
    }

    /**
     * Añade un carácter al final del texto actual, siempre que no se haya alcanzado
     * la longitud máxima definida en {@link #length}.
     *
     * @param c carácter a añadir
     */
    // Añadir la letra c al final del texto
    public void addText(char c) {
        if (this.text.length()  < length) {
            this.text += c;
        }
    }

    /**
     * Elimina el último carácter del texto actual.
     * Si el texto está vacío, no realiza ninguna acción.
     */
    // Quitar la última letra del texto
    public void removeText() {
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }
    }

    /**
     * Elimina todo el contenido del campo de texto, dejándolo vacío.
     */
    // Quitar todo el texto
    public void removeAllText(){
        this.text = "";
    }

    /**
     * Devuelve el texto actualmente contenido en el campo.
     *
     * @return cadena de texto del campo
     */
    // Recuperar el texto
    public String getText(){
        return this.text;
    }

    /**
     * Establece el texto del campo, reemplazando el contenido actual.
     *
     * @param t nueva cadena de texto que se mostrará en el campo
     */
    // Setter del texto
    public void setText(String t){
        this.text= t;
    }

    /**
     * Indica si el cursor del ratón se encuentra dentro de los límites del campo de texto.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si el ratón está sobre el campo; {@code false} en caso contrario
     */
    // Indica si el ratón está sobre el campo
    public boolean mouseOverTextField(PApplet p5) {
        return (p5.mouseX >= this.x && p5.mouseX <= this.x + this.w && p5.mouseY >= this.y && p5.mouseY <= this.y + this.h);
    }

    /**
     * Gestiona la selección o deselección del campo en función de dónde ha hecho clic el usuario.
     * Si el clic se produce dentro del área del campo, este queda seleccionado y listo para
     * recibir texto. Si el clic es fuera, el campo se deselecciona.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     */
    // Selecciona el campo de texto si pulsamos encima
    // Deselecciona el campo de texto si pulsamos fuera
    public void isPressed(PApplet p5) {
        System.out.println("IS PRESSED");
        if(mouseOverTextField(p5)) {

            selected = true;
        } else {
            selected = false;
        }
        System.out.println("SELECTED: "+selected);
    }
}