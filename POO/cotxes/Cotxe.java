package POO.cotxes;

public class Cotxe {

    enum MARCA{SEAT, SKODA, RENAULT, KIA}
    // Atributs o propietats
    MARCA marca;
    String matricula;
    float velocitat;
    int numPortes;

    // MÈTODES
    // Constructors
    Cotxe(MARCA m, String mat, float v, int nP){
        marca = m;
        matricula = mat;
        velocitat = v;
        numPortes = nP;
    }
    Cotxe(MARCA m, String mat){
        marca = m;
        matricula = mat;
        velocitat = 0;
        numPortes = 5;
    }

    // Setters (mutadores)
    void setMarca(MARCA m){marca = m;}
    void setMatricula(String mat){matricula = mat;}
    void setVelocitat(float v){velocitat = v;}
    void setNumPortes(int nP){numPortes = nP;}

    void setVelocitatNumPortes(float v, int nP){velocitat = v; numPortes = nP;}

    // Getters (accesores)
    MARCA getMarca(){return marca;}
    String getMatricula(){return matricula;}
    float getVelocitat(){return velocitat;}
    int getNumPortes(){return numPortes;}

    // Altres
    void print(){
        System.out.printf("%s - %s (numP: %d) --> %f km/h.\n", marca, matricula, numPortes, velocitat);
    }

    void accelerar(float q) {
        velocitat += q;
    }
}
