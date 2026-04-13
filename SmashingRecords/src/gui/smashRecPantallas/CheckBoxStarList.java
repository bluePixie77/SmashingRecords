package gui.smashRecPantallas;

import processing.core.PApplet;
/**
 * Lista de casillas de verificación en forma de estrellas ({@link CheckBoxStar})
 * para representar una valoración de 0 a N estrellas.
 *
 * <p>La selección funciona de forma acumulativa: marcar la estrella de posición
 * {@code i} activa todas las estrellas desde la 0 hasta la {@code i}, y desactiva
 * las restantes. Si se hace clic en la primera estrella y ya es la única activa,
 * se alterna su estado.</p>
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class CheckBoxStarList {

    /** Posición horizontal de la lista en píxeles. */
    float x;

    /** Posición vertical de la lista en píxeles. */
    float y;

    /** Anchura de cada estrella en píxeles. */
    float w;

    /** Altura de cada estrella en píxeles. */
    float h;

    /** Separación horizontal entre estrellas en píxeles. */
    int marge = 15;

    /** Array de estrellas individuales que componen la lista. */
    CheckBoxStar[] cbs;

    /**
     * Crea una nueva {@code CheckBoxStarList} con {@code nun} estrellas,
     * cargando las imágenes de estrella activada y desactivada.
     *
     * @param p5   instancia de Processing necesaria para cargar las imágenes
     * @param nun  número de estrellas de la lista
     * @param imgs array de dos rutas de imagen: {@code [0]} estrella activada,
     *             {@code [1]} estrella desactivada
     * @param x    posición horizontal de la primera estrella en píxeles
     * @param y    posición vertical en píxeles
     * @param w    anchura de cada estrella en píxeles
     * @param h    altura de cada estrella en píxeles
     */
    // Constructor
    public CheckBoxStarList(PApplet p5, int nun, String[] imgs, float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.cbs = new CheckBoxStar[nun];
        for (int i=0; i<cbs.length; i++) {
            cbs[i] = new CheckBoxStar(x +(h+marge)*i, y, w, h);
            cbs[i].setImages(p5, imgs[0], imgs[1]);
        }
    }

    /**
     * Dibuja todas las estrellas de la lista en pantalla.
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    // Dibujar el checkboxlist
    public void display(PApplet p5) {
        for (CheckBoxStar cb : cbs) {
            cb.display(p5);
        }
    }

    /**
     * Comprueba si el usuario ha hecho clic sobre alguna estrella y actualiza
     * la selección de forma acumulativa.
     *
     * <p>Comportamiento detallado:</p>
     * <ul>
     *   <li>Si se hace clic en la estrella {@code i > 0}, las estrellas de 0 a {@code i}
     *       quedan activadas y las de {@code i+1} en adelante se desactivan.</li>
     *   <li>Si se hace clic en la estrella de índice 0, se alterna su estado
     *       individualmente y se desactivan las restantes.</li>
     * </ul>
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     */
    // Comprobación click sobre algún checkboxstar
    public void checkMouse(PApplet p5) {

        for (int i=0; i<cbs.length; i++) {
            CheckBoxStar cb = cbs[i];
            if (cb.onMouseOver(p5)) {
                if (i==0) {
                    cb.toggle();
                    for (int k=i+1; k<cbs.length; k++) {
                        cbs[k].checked = false;
                    }
                } else {
                    for (int k=0; k<=i; k++) {
                        cbs[k].checked = true;
                    }
                    for (int k=i+1; k<cbs.length; k++) {
                        cbs[k].checked = false;
                    }
                }
            }
        }
    }

    /**
     * Activa las primeras {@code n} estrellas y desactiva las restantes.
     * Permite establecer programáticamente una valoración concreta.
     *
     * @param n número de estrellas a activar (0 desactiva todas)
     */
    // Setter del número de estrellas activadas
    public void setCheckBoxStars(int n){
        for (int i=0; i<n; i++) {
            cbs[i].checked = true;
        }
        for (int i=n; i<cbs.length; i++) {
            cbs[i].checked = false;
        }
    }

    /**
     * Indica si el cursor del ratón se encuentra sobre alguna estrella de la lista.
     * Útil para cambiar el cursor del puntero a mano.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si el ratón está sobre alguna estrella; {@code false} en caso contrario
     */
    // Devuelve true si el mouse está sobre algún checkbox
    public boolean checkCursor(PApplet p5) {
        for (CheckBoxStar cb : cbs) {
            if (cb.onMouseOver(p5)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el número de estrellas actualmente activadas.
     *
     * @return número entero de estrellas seleccionadas (entre 0 y el tamaño de la lista)
     */
    // Devuelve el número de elementos seleccionados
    public int getNumSelected() {
        int n = 0;
        for (CheckBoxStar cb : cbs) {
            if (cb.checked) {
                n++;
            }
        }
        return n;
    }
    /**
     * Desactiva todas las estrellas de la lista, restableciendo la valoración a cero.
     */
    public void reset() {
        if (this.cbs != null) {
            for (int i = 0; i < this.cbs.length; i++) {
                this.cbs[i].setChecked(false);
            }
        }
    }
}