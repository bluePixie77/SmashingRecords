package gui.smashRecPantallas;

import processing.core.PApplet;
/**
 * Grupo de botones de opción ({@link RadioButton}) de selección única.
 * Garantiza que en todo momento solo uno de los botones del grupo esté marcado.
 * Gestiona tanto la visualización del conjunto como la lógica de selección
 * al hacer clic sobre cualquiera de sus elementos.
 *
 * @author Equipo SmashRecords
 * @version 1.0
 */
public class RadioButtonGroup {

    /** Array de botones de opción que forman el grupo. */
    RadioButton[] rbuttons;

    /** Índice del botón actualmente seleccionado dentro del array {@link #rbuttons}. */
    public int selectedOption;

    /**
     * Crea un nuevo grupo de botones de opción con capacidad para {@code n} botones.
     * La opción seleccionada por defecto es la primera (índice 0).
     *
     * @param n número de botones de opción del grupo
     */
    public RadioButtonGroup(int n){
        rbuttons = new RadioButton[n];
        selectedOption = 0;
    }

    /**
     * Asigna los botones de opción al grupo mediante varargs.
     * Los botones se almacenan en el mismo orden en que se pasan como argumentos.
     *
     * @param rbs botones de opción a añadir al grupo
     */
    public void setRadioButtons(RadioButton ... rbs){
        for(int i=0; i<rbs.length; i++){
            this.rbuttons[i] = rbs[i];
        }
    }

    /**
     * Selecciona programáticamente el botón de la posición indicada y desmarca el resto.
     *
     * @param n índice del botón a seleccionar (basado en cero)
     */
    public void setSelected(int n) {
        selectedOption = n;
        for (int i = 0; i < rbuttons.length; i++) {
            if (rbuttons[i] != null) {
                rbuttons[i].setChecked(i == n);
            }
        }
    }

    /**
     * Dibuja todos los botones del grupo en pantalla.
     *
     * @param p5 instancia de Processing usada para el dibujado
     */
    public void display(PApplet p5){
        for(int i=0; i<rbuttons.length; i++){
            if(rbuttons[i]!=null){
                rbuttons[i].display(p5);
            }
        }
    }

    /**
     * Actualiza la selección del grupo en respuesta a un clic del ratón.
     * Si el clic se produce sobre uno de los botones, ese botón queda marcado
     * y todos los demás se desmarcan, actualizando también {@link #selectedOption}.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     */
    public void updateOnClick(PApplet p5){
        if(clickOnOneRadioButton(p5)){
            for(int i=0; i<rbuttons.length; i++){
                if(rbuttons[i]!=null && rbuttons[i].onMouseOver(p5)){
                    selectedOption = i;
                    rbuttons[i].setChecked(true);
                }
                else{
                    rbuttons[i].setChecked(false);
                }
            }
        }
    }

    /**
     * Indica si el clic del ratón se ha producido sobre alguno de los botones del grupo.
     *
     * @param p5 instancia de Processing para obtener la posición del ratón
     * @return {@code true} si el ratón está sobre algún botón; {@code false} en caso contrario
     */
    public boolean clickOnOneRadioButton(PApplet p5){
        for(int i=0; i<rbuttons.length; i++){
            if(rbuttons[i]!=null && rbuttons[i].onMouseOver(p5)){
                return true;
            }
        }
        return false;
    }

    /**
     * Restablece el grupo al estado inicial: el primer botón queda marcado
     * y todos los demás se desmarcan. {@link #selectedOption} se pone a 0.
     */
    public void reset() {
        // 1. Volvemos al valor por defecto (la primera opción)
        this.selectedOption = rbuttons.length-1;

        // 2. Recorremos los botones individuales y los desmarcamos
        for (int i = 0; i < rbuttons.length; i++) {
            if (i == 0) {
                rbuttons[i].setChecked(true);  // La primera opción marcada
            } else {
                rbuttons[i].setChecked(false); // Las demás desmarcadas
            }
        }
    }
}