package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;

import static gui.smashRecFonts.Sizes.medidaParrafo;
import static java.lang.Math.min;
import static processing.core.PApplet.constrain;
import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.CODED;

/**
 * Componente gráfico de área de texto editable centrado en texto plano.
 * Gestionar visualización, edición (teclado) y selección por ratón.
 *
 * @author Equipo SmashRecords
 */
public class TextArea {

    // Propiedades del campo de texto
    /**
     * Coordenada X superior‑izquierda del área de texto.
     */
    public float x;
    /**
     * Coordinada Y superior‑izquierda del área de texto.
     */
    public float y;
    /**
     * Altura del área de texto en píxeles.
     */
    public float h;
    /**
     * Anchura del área de texto en píxeles.
     */
    public float w;
    /**
     * Número de columnas por fila de texto (límite de caracteres por línea).
     */
    int numCols;
    /**
     * Número máximo de filas de texto.
     */
    int numRows;

    // Colores
    /**
     * Color de relleno del área de texto cuando no está seleccionada.
     */
    int fillColor;
    /**
     * Color de relleno cuando el área está seleccionada.
     */
    int selectedColor;
    /**
     * Color del borde del área de texto.
     */
    int borderColor;
    /**
     * Anchura en píxeles del borde del área de texto.
     */
    int borderWeight = 1;

    // Texto del campo
    /**
     * Líneas de texto descompuestas según {@link #numCols}.
     */
    String[] lines;
    /**
     * Texto completo almacenado en el área de texto.
     */
    public String text = "";
    /**
     * Tamaño del texto en píxeles.
     */
    int textSize = (int) medidaParrafo;

    /**
     * Indica si el área de texto está seleccionada (recibe entrada de teclado).
     */
    boolean selected = false;


    /**
     * Constructor de un área de texto rectangular asociada a una PApplet.
     *
     * @param p5 referencia a la instancia de Processing (PApplet) para dibujar.
     * @param appColors conjunto de colores de la aplicación.
     * @param x coordenada X superior‑izquierda del área.
     * @param y coordenada Y superior‑izquierda del área.
     * @param w anchura del área de texto.
     * @param h altura del área de texto.
     * @param nc número de columnas por fila (límite de caracteres por línea).
     * @param nr número máximo de filas de texto.
     */
    public TextArea(PApplet p5, Colors appColors, float x, float y, float w, float h, int nc, int nr) {
        this.x = x; this.y = y; this.w = w; this.h = h;
        this.numCols = nc; this.numRows = nr;
        this.lines = new String[nr];
        setColors(appColors);
    }

    /**
     * Actualiza los colores del área de texto usando el esquema de la aplicación.
     *
     * @param appColors conjunto de colores de la aplicación.
     */
    // Setters
    public void setColors(Colors appColors){
        this.fillColor = appColors.getFifthColor();
        this.selectedColor = appColors.getThirdColor();
        this.borderColor = appColors.getFourthColor();
        this.borderWeight = 1;

    }

    /**
     * Fija el contenido de texto completo del área; actualiza internamente las líneas.
     *
     * @param text cadena de texto que se mostrará en el área.
     */
    public void setText(String text){
        this.text = text;
        updateLines();
    }

    /**
     * Devuelve el texto completo almacenado en el área.
     *
     * @return contenido de texto actual.
     */
    // Getters
    public String getText() {
        return this.text;
    }

    /**
     * Dibuja visualmente el área de texto en la ventana de Processing.
     * Se rellena con {@link #fillColor} o {@link #selectedColor} según el estado,
     * se dibuja un borde con {@link #borderColor} y las líneas de texto.
     *
     * @param p5 referencia a la instancia de Processing (PApplet) para dibujar.
     */
    // Dibuixa el Camp de Text
    public void display(PApplet p5) {
        p5.pushStyle();
        if (selected) {
            p5.fill(selectedColor);
        } else {
            p5.fill(fillColor);
        }

        p5.strokeWeight(borderWeight);
        p5.stroke(borderColor);
        p5.rect(x, y, w, h, 5);

        p5.fill(borderColor);
        p5.textSize(textSize);
        for(int i=0; i<lines.length; i++){
            if(lines[i]!=null){
                p5.text(lines[i], x + 5, y + (i+1)* textSize);
            }
        }
        p5.popStyle();
    }

    /**
     * Actualiza el array {@link #lines} a partir de {@link #text},
     * dividiendo el texto en filas de hasta {@link #numCols} caracteres.
     * Limita el número de líneas según {@link #numRows}.
     */
    public void updateLines(){
        if(text.length()>0){
            int numLines = constrain(text.length() / numCols, 0, this.numRows-1);
            for(int i=0; i<=numLines; i++){
                int start = i*numCols;
                int end = min(text.length(), (i+1)*numCols);
                lines[i] = text.substring(start, end);
            }
        }
        else {
            for(int i=0; i<lines.length; i++){
                lines[i] ="";
            }
        }
    }

    /**
     * Gestiona la entrada de teclado usando el código de tecla.
     * Si el área está seleccionada, trata retroceso y espacio;
     * otros caracteres codificados se ignoran.
     *
     * @param key caracter pulsado (si es imprimible).
     * @param keyCode código de la tecla (BACKSPACE, etc.).
     */
    // Añade, quita el texto que se teclea
    public void keyPressed(char key, int keyCode) {
        if (selected) {
            if (keyCode == (int)BACKSPACE) {
                removeText();
            } else if (keyCode == 32) {
                addText(' '); // SPACE
            } else if(key!=CODED) {
                addText(key);
            }
        }
    }

    /**
     * Variant de {@link #keyPressed(char, int)} para teclas sin caracter asociado.
     * Solo procesa retroceso si el área está seleccionada.
     *
     * @param keyCode código de la tecla.
     */
    public void keyPressed(int keyCode) {
        if (!selected) return;

        if (keyCode == BACKSPACE) {
            removeText();
        }
    }

    /**
     * Gestiona la entrada de texto real (incluyendo acentos y caracteres Unicode).
     * Ignora caracteres de control (salto de línea, retorno de carro, retroceso).
     * Si el área está seleccionada, añade el carácter mediante {@link #addText(char)}.
     *
     * @param key caracter introducido por el usuario.
     */
    // Gestiona entrada de texto real (incluye acentos)
    public void keyTyped(char key) {
        if (!selected) return;

        // Evita caracteres de control
        if (key == '\n' || key == '\r' || key == '\b') return;

        addText(key);
    }

    /**
     * Añade un carácter al final del texto si aún cabe según el límite
     * impuesto por {@link #numCols} y {@link #numRows}.
     * Llama a {@link #updateLines()} para recalcular las filas.
     *
     * @param c carácter a añadir.
     */
    // Añade la letra c al final del texto
    public void addText(char c) {
        if (this.text.length() < this.numCols*this.numRows) {
            this.text += c;
        }
        updateLines();
    }


    /**
     * Quita el último carácter del texto si hay contenido.
     * Llama a {@link #updateLines()} para recalcular las filas.
     */
    // Quita la última letra del texto
    public void removeText() {
        if (text.length()> 0) {
            text = text.substring(0, text.length()-1);
        }
        updateLines();
    }

    /**
     * Comprueba si el ratón está sobre el área de texto.
     * Se utiliza para seleccionar/deseleccionar el componente.
     *
     * @param p5 referencia a la instancia de Processing (PApplet).
     * @return {@code true} si el puntero está dentro del rectángulo del área.
     */
    // Ratón sobre el campo de texto
    public boolean mouseOverTextField(PApplet p5) {
        return (p5.mouseX >= this.x && p5.mouseX <= this.x + this.w && p5.mouseY >= this.y && p5.mouseY <= this.y + this.h);
    }

    /**
     * Selecciona el área de texto si el ratón está sobre ella,
     * o la deselecciona si el clic se produce fuera.
     *
     * @param p5 referencia a la instancia de Processing (PApplet).
     */
    public void isPressed(PApplet p5) {
        if (mouseOverTextField(p5)) {
            selected = true;
        } else {
            selected = false;
        }
    }

    /**
     * Cambia el número de columnas por fila de texto.
     * Ajusta internamente el tamaño máximo de cada línea y
     * recalcula las líneas con {@link #updateLines()}.
     *
     * @param nc nuevo número de columnas.
     */
    public void setNumCols(int nc) {
        this.numCols = nc;
        updateLines();
    }
}
