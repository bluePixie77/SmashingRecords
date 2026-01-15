package gui.smashRecPantallas;

import gui.smashRecColors.Colors;
import processing.core.PApplet;
import processing.core.PImage;

public class TestPagedCard2D extends PApplet {

        // Dimensions dels botons
        Button b1, b2;
        float buttonW = 60, buttonH = 60;

        // Cards Paginades
        PagedCard2D pc;
        Colors appColors;


        // Dimensions de les cards
        float cardsW = 800, cardsH = 700;

        // Dades de les cards
        String[][] info = {
                {"Títol 0", "Lloc 0", "Data 0", "Secció 0", "Descripció 0"},
                {"Títol 1", "Lloc 1", "Data 1", "Secció 1", "Descripció 1"},
                {"Títol 2", "Lloc 2", "Data 2", "Secció 2", "Descripció 2"},
                {"Títol 3", "Lloc 3", "Data 3", "Secció 1", "Descripció 3"},
                {"Títol 4", "Lloc 4", "Data 4", "Secció 1", "Descripció 4"},
                {"Títol 5", "Lloc 5", "Data 5", "Secció 2", "Descripció 5"},
                {"Títol 6", "Lloc 6", "Data 6", "Secció 2", "Descripció 6"},
                {"Títol 7", "Lloc 7", "Data 7", "Secció 1", "Descripció 7"},
                {"Títol 8", "Lloc 8", "Data 8", "Secció 8", "Descripció 8"},
                {"Títol 9", "Lloc 9", "Data 9", "Secció 9", "Descripció 9"},
                {"Títol 10", "Lloc 10", "Data 10", "Secció 10", "Descripció 10"},
        };

        // Imatges de les cards
        PImage img1, img2;

        boolean cursorHand = false;

        public static void main(String[] args) {
            PApplet.main("gui.smashRecPantallas.TestPagedCard2D", args);
        }

        public void settings(){
            size(1200, 800);     // Dimensions de la Pantalla
            smooth(10);
        }

        public void setup(){

            // Imatges de les Categories
            img1 = loadImage("data/musicPredetBlackBG.png");
            img2 = loadImage("data/musicPredetWhiteBG.png");

            // Creació de la taula
            pc = new PagedCard2D(this, 2, 4, appColors);
            pc.setDimensions(50, 50, cardsW, cardsH);
            pc.setData(info);
            pc.setCards();
            pc.setImages(img1, img2);

            // Creació dels botons
            b1 = new Button(this, appColors, "NEXT", 100 + cardsW, 80, buttonW, buttonH);
            b2 = new Button(this, appColors, "PREV", 100 + cardsW, 100 + buttonH, buttonW, buttonH);
        }

        public void draw(){
            background(255);

            // Dibuja las Cards paginadas
            pc.display(this);
            pc.printSelectedCard(this);

            // Actualitza forma del cursor
            updateCursor(this);
        }

        // Modifica el cursor
        void updateCursor(PApplet p5){
           if((b1.mouseOverButton(p5) && b1.isEnabled())||
                    (b2.mouseOverButton(p5) && b2.isEnabled())){
                cursorHand = true;
            }
            else {
                cursorHand = pc.checkMouseOver(p5);
            }

            if(cursorHand){
                cursor(HAND);
            }
            else {
                cursor(ARROW);
            }
        }


        // ******************* KEYBOARD interaction ***************************** //

        public void keyPressed(){
        }

        // ******************* MOUSE interaction ***************************** //

        public void mousePressed(){
            if(b1.mouseOverButton(this) && b1.isEnabled()){
                pc.nextPage();
            }
            else if(b2.mouseOverButton(this) && b2.isEnabled()){
                pc.prevPage();
            }
            else {
                pc.checkCardSelection(this);
            }
        }

        public void mouseDragged(){
            println("MOUSE DRAGGED");
        }

        public void mouseReleased() {
            println("MOUSE RELEASED");
        }
}
