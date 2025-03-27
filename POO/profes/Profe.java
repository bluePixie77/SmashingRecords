package POO.profes;

public class Profe { // Classe de definició, no de ús

    enum DEPARTAMENT{FÍSICA, MATES, CATALÀ, ANGLÈS}
    // Atributs o propietats
    String nom;
    int anys;
    DEPARTAMENT departament;

    // Mètodes

    // Constructor(s)                       Operador this (desambiguar)
    Profe(String nom, int a, DEPARTAMENT d){this.nom = nom; this.anys = a; this.departament = d;}
    Profe(String n, DEPARTAMENT d){this.nom = n; this.anys = 0; this.departament = d;}

    // Setters (mutadores)
    void setNom(String n) {nom = n;}
    void setAnys(int a){anys = a;}
    void setDepartament(DEPARTAMENT d){departament = d;}

    // Getters (accesores)
    String getNom(){return nom;}
    int getAnys(){return anys;}
    DEPARTAMENT getDepartament(){return departament;}

    // Altres [objecte.mètode]
    void printf(){
        System.out.printf("%s - %s (%d).\n", nom, departament, anys);
    }
}
