package POO.alumnes;

public class Alumne { // Alumne.java
    // Atributs o propietats
    String nom;
    int curs;
    char grup;

    // Mètodes (funcions)

    // Constructor(s)
    Alumne(String n, int c, char g){
        nom = n;
        curs = c;
        grup = g;
    }
    Alumne(String n){
        nom = n;
        curs = 1;
        grup = 'A';
    }

    // Setter(s) (mutador(es))
    void setNom(String n){nom = n;}
    void setCurs(int c){curs = c;}
    void setGrup(char g){grup = g;}

    void setCursGrup(int c, char g){curs = c; grup = g;}

    // Getter(s) (accesores)
    String getNom(){return nom;}
    int getCurs(){return curs;}
    char getGrup(){return grup;}

    // Altres
    void print(){
        System.out.printf("%s - %d%c.\n", nom , curs, grup);
    }
}

