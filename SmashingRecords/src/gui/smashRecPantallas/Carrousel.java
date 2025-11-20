package gui.smashRecPantallas;

import processing.core.PApplet;
import processing.core.PImage;

public class Carrousel {

        // Dimensions del carrousel
        int x, y, w, h;
        float imgW;
        float margeH = 15;

        // Informació del carrousel
        int numTotalImatges, numImatgesVisibles;

        // Index imatge actual
        int currentImage;

        // Títols de les imatges
        String[] noms;

        // Imatges del carrousel
        PImage[] imgs;

        // Botons del carrousel
        RoundButton bPrev, bNext;

        // Constructor
        public Carrousel(int x, int y, int w, int h, int nv){
            this.x = x; this.y = y; this.w = w; this.h = h;
            this.numImatgesVisibles = nv;
            this.currentImage = 0;
            this.imgW = (w - margeH*(nv-1)) / (float) nv;
        }

        // Setters

        public void setImages(PApplet p5, String[] noms){
            this.noms = noms;
            this.numTotalImatges = noms.length;
            this.imgs = new PImage[noms.length];
            for(int i=0; i<imgs.length; i++){
                imgs[i] = p5.loadImage(noms[i]);
            }
        }

        public void setButtons(PApplet p5, String img1, String img2){
            PImage imgPrev = p5.loadImage(img1);
            bPrev = new RoundButton(p5, imgPrev, x, y + h/2, 30);

            PImage imgNext = p5.loadImage(img2);
            bNext = new RoundButton(p5, imgNext, x + w, y + h/2, 30);
        }

        public void next(){
            if(this.currentImage<this.numTotalImatges - this.numImatgesVisibles){
                this.currentImage++;
            }
        }

        public void prev(){
            if(this.currentImage>0){
                this.currentImage--;
            }
        }


        // Dibuixa el Carroussel
        public void display(PApplet p5){

            p5.pushStyle();

            p5.fill(150); p5.stroke(0);
            p5.rect(x-5, y-5, w+10, h+10);

            for(int i=0; i<this.numImatgesVisibles; i++){

                int index = i + this.currentImage;
                float xPos = x + i*(this.imgW + this.margeH);

                // Imatge a mostrar
                PImage img = imgs[index];
                p5.image(img, xPos, y, this.imgW, h);

                // Titol de la imatge
                p5.fill(0); p5.textAlign(p5.CENTER); p5.textSize(24);
                p5.text(noms[index], xPos + this.imgW/2, y + h + 50);

                // Número de la imatge
                p5.fill(200, 100, 100); p5.noStroke();
                p5.ellipse(xPos + 30, y + 30, 50, 50);
                p5.fill(0);
                p5.text(index, xPos + 30, y + 40);
            }

            if(bNext!=null){
                bNext.display(p5);
            }
            if(bPrev!=null){
                bPrev.display(p5);
            }
        }

        public void checkButtons(PApplet p5){
            if(bNext.mouseOverButton(p5) && bNext.enabled){
                this.next();
            }
            else if(bPrev.mouseOverButton(p5) && bPrev.enabled){
                this.prev();
            }
        }

        public boolean checkCursor(PApplet p5){
            if(bNext.mouseOverButton(p5) && bNext.enabled){
                return true;
            }
            else if(bPrev.mouseOverButton(p5) && bPrev.enabled){
                return true;
            }
            return false;
        }


    }
