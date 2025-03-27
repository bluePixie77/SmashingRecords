package POO.alumnes;

public class MainAlumnes {

    public static void main(String[] args){

        Alumne a1, a2, a3;  // Declaració

        // Cridades a constructors
        a1 = new Alumne("Paco", 1, 'A');
        a2 = new Alumne("Bel", 3, 'F');
        a3 = new Alumne("Jaume");

        // Setters [objecte.mètode]
        a2.setCurs(4);
        a1.setCurs('F');
        a3.setNom("James");
        a3.setCursGrup(2, 'B');

        // Getter
        System.out.printf("%s va a %d.\n", a2.getNom(), a2.getCurs());

        // Altres
        a1.print(); a2.print(); a3.print();
    }
}
