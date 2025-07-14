import processing.core.PApplet;
import processing.core.PFont;

public class TicTacToe extends PApplet {

    public static void main(String[] args) {
        PApplet.main("TicTacToe"); // "package.nomClasse" (si està dins un package)
    }

    // Propietats
    Tauler t;
    PFont f1, f2;

    public void settings(){
        size(800, 800);
    }

    public void setup(){
        t = new Tauler(3, width); // Instanciació objectes
        t.setImatges(this);  // Carregar imatges
        t.setSoClick(this);  // Carregar àudio
        // Carregar fonts
        f1 = createFont("Dimitri.ttf", 45);
        f2 = createFont("Platinum.ttf", 20);
    }
    public void draw(){
        background(200, 100, 100);
        t.display(this);
        if(t.hiHaGuanyador || (t.hiHaGuanyador&&t.finalPartida) || t.finalPartida) {
            fill(255, 200);
            rect(0, 0, width, height);
        }
        if(t.hiHaGuanyador){
            textAlign(CENTER); textSize(24); fill(0);
            textFont(f1);
            text("GUANYADOR '"+t.guanyador+"'", width/2, height/2-20);
        }
        if(t.finalPartida){
            textAlign(CENTER); textSize(24); fill(0);
            textFont(f2);
            text("CLICAR 'R' PER REINICIAR", width/2, height/2+20);
        }
    }
    public void mousePressed(){
        t.casellaPitjada(this);
        t.actualitzaGuanyador();
    }
    public void keyPressed(){
        if(key=='r' || key=='R'){
            t = new Tauler(3, width);
            t.setImatges(this);
            t.setSoClick(this);
        }
    }
}
