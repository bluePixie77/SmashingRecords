import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;

public class Tauler {

    // Propietats
    float midaCasella;
    Casella[][] caselles;
    boolean hiHaGuanyador;

    char guanyador;         // 'X' o 'O'
    boolean finalPartida;
    int numTirades;         // entre '0' a '9'

    PImage imgCreu, imgCercle;

    SoundFile soClick;

    // Constructor
    public Tauler(int n, int w){ // n: num caselles per fila / w: mida total tauler
        this.midaCasella = w/n;
        caselles = new Casella[n][n];
        hiHaGuanyador = false;
        guanyador = ' ';
        finalPartida = false;
        numTirades = 0;

        for(int f=0; f<caselles.length; f++){
            for(int c=0; c<caselles[f].length; c++){
                caselles[f][c] = new Casella(f, c, (int)midaCasella*c, (int)midaCasella*f,
                                            (int)midaCasella);
            }
        }
    }

    // Setters
    public void setImatges(PApplet p5){
        this.imgCreu = p5.loadImage("creu.png");
        this.imgCercle = p5.loadImage("cercle.png");
    }
    public void setSoClick(PApplet p5){
        this.soClick = new SoundFile(p5,"click.wav");
    }

    // Altres mètodes
        // Dibuixar tauler
    public void display(PApplet p5){
        for(int f=0; f<caselles.length; f++){
            for(int c=0; c<caselles[f].length; c++){
                caselles[f][c].display(p5, imgCreu, imgCercle);   // Display de Casella
            }
        }
    }

        // Si casella es clica... dibuixar CERCLE/CREU
    public void casellaPitjada(PApplet p5){
       if(!finalPartida){
           for(int f=0; f<caselles.length; f++){
               for(int c=0; c<caselles[f].length; c++){
                   if(caselles[f][c].estaDins(p5.mouseX, p5.mouseY) &&
                      caselles[f][c].valor == Casella.VALOR.BLANC){
                        soClick.play();
                       if(numTirades%2==0) {
                           caselles[f][c].setValor(Casella.VALOR.CERCLE);
                       }else{
                           caselles[f][c].setValor(Casella.VALOR.CREU);
                       }
                       numTirades++;
                       break;
                   }
               }
           }
       }
    }

        // Comprovació valors fila f són iguals
    public boolean filaIguals(int f){
        if(caselles[f][0].valor == caselles[f][1].valor &&
           caselles[f][1].valor == caselles[f][2].valor &&
           caselles[f][2].valor != Casella.VALOR.BLANC){
            return true;
        }else{
            return false;
        }
    }

        // Comprovació valors columna c són iguals
    public boolean colIguals(int c){
        if(caselles[0][c].valor == caselles[1][c].valor &&
           caselles[1][c].valor == caselles[2][c].valor &&
           caselles[0][c].valor != Casella.VALOR.BLANC){
            return true;
        }else{
            return false;
        }
    }

        // Comprovació valors diagonalDescendent són iguals
    public boolean diagDescIguals(){
        if(caselles[0][0].valor == caselles[1][1].valor &&
           caselles[1][1].valor == caselles[2][2].valor &&
           caselles[0][0].valor != Casella.VALOR.BLANC){
            return true;
        }else{
            return false;
        }
    }

        // Comprovació valors diagonalAscendent són iguals
    public boolean diagAscIguals(){
        if(caselles[2][0].valor == caselles[1][1].valor &&
           caselles[1][1].valor == caselles[0][2].valor &&
           caselles[2][0].valor != Casella.VALOR.BLANC){
            return true;
        }else{
            return false;
        }
    }

        // Comprovació files, columnes i diagonals
    public boolean comprovaGuanyador(){
        // Files
        for(int f=0; f<caselles.length; f++){
            if(filaIguals(f)){
                return true;
            }
        }
        // Columnes
        for(int c=0; c<caselles.length; c++){
            if(colIguals(c)){
                return true;
            }
        }
        // Diagonals
        return diagDescIguals() || diagAscIguals();
    }

        // Actualització comprovació guanyador (propietat hiHaGuanyador actualitzada)
    public void actualitzaGuanyador(){
        hiHaGuanyador = comprovaGuanyador();
        if(hiHaGuanyador){
            guanyador = numTirades%2==0 ? 'X' : 'O';    // Operador condicional
        }
        if(numTirades==9 || hiHaGuanyador){
            finalPartida = true;
        }
    }
}
