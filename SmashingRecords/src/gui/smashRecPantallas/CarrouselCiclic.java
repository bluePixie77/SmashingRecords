
package gui.smashRecPantallas;

import gui.smashRecPantallas.Carrousel;
import processing.core.PApplet;
import processing.core.PImage;

public class CarrouselCiclic extends Carrousel {

    // Constructor
    public CarrouselCiclic(int x, int y, int w, int h, int nv){
        super(x, y, w, h, nv);
    }

    // Sobre-càrrega el mètode next() heretat
    public void next(){
        if(this.currentImage<=this.numTotalImatges){
            this.currentImage++;
        }
        else {
            this.currentImage = 0;
        }
    }

    // Sobre-càrrega del mètode prev() heretat
    public void prev(){
        if(this.currentImage>=0){
            this.currentImage--;
        }
        if (this.currentImage < 0){
            this.currentImage = this.numTotalImatges-1;
        }
    }

}