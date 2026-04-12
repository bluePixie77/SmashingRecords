package gui.smashRecPantallas;

import processing.core.PApplet;

public class RadioButtonGroup {

    // Propietats
    RadioButton[] rbuttons;
    public int selectedOption;

    public RadioButtonGroup(int n){
        rbuttons = new RadioButton[n];
        selectedOption = 0;
    }

    public void setRadioButtons(RadioButton ... rbs){
        for(int i=0; i<rbs.length; i++){
            this.rbuttons[i] = rbs[i];
        }
    }

    public void setSelected(int n) {
        selectedOption = n;
        for (int i = 0; i < rbuttons.length; i++) {
            if (rbuttons[i] != null) {
                rbuttons[i].setChecked(i == n);
            }
        }
    }

    public void display(PApplet p5){
        for(int i=0; i<rbuttons.length; i++){
            if(rbuttons[i]!=null){
                rbuttons[i].display(p5);
            }
        }
    }

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

    public boolean clickOnOneRadioButton(PApplet p5){
        for(int i=0; i<rbuttons.length; i++){
            if(rbuttons[i]!=null && rbuttons[i].onMouseOver(p5)){
                return true;
            }
        }
        return false;
    }

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