package gui.smashRecPantallas;

import processing.core.PApplet;

public class CheckBoxStarList {

    // Propietats (posició i dimensions)
    float x, y, w, h;
    int marge = 15;

    // Propietats(checkboxes)
    CheckBoxStar[] cbs;

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


    // Dibuixam el checkboxlist
    public void display(PApplet p5) {
        for (CheckBoxStar cb : cbs) {
            cb.display(p5);
        }
    }

    // Comprova si feim click sobre algun checkboxstar
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

    // Setter del número de estrelles activades
    public void setCheckBoxStars(int n){
        for (int i=0; i<n; i++) {
            cbs[i].checked = true;
        }
        for (int i=n; i<cbs.length; i++) {
            cbs[i].checked = false;
        }
    }


    // Retorna si el checkBox n està seleccionat
    public boolean isChecked(int n) {
        return cbs[n].checked;
    }

    // Retorna true si el mouse està sobre algun checkbox
    public boolean checkCursor(PApplet p5) {
        for (CheckBoxStar cb : cbs) {
            if (cb.onMouseOver(p5)) {
                return true;
            }
        }
        return false;
    }

    // Retorna el número d'elements seleccionats
    public int getNumSelected() {
        int n = 0;
        for (CheckBoxStar cb : cbs) {
            if (cb.checked) {
                n++;
            }
        }
        return n;
    }
}