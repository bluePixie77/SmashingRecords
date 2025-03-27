package POO.profes;

public class MainProfes {
    public static void main(String[] args){
        Profe p1, p2, p3;  // Declaració

        // Cridades als constructors
        p1 = new Profe("Manel", 35, Profe.DEPARTAMENT.FÍSICA);
        p2 = new Profe("Xesca", 22, Profe.DEPARTAMENT.CATALÀ);
        p3 = new Profe("Biel", Profe.DEPARTAMENT.MATES);

        // Setters (mutadores)
        p1.setNom("Manel Ernest");
        p2.setDepartament(Profe.DEPARTAMENT.ANGLÈS);
        p3.setAnys(15);

        // Getters
        int sumaAnys = p1.getAnys() + p2.getAnys() + p3.getAnys();
        System.out.printf("SUMA ANYS: %d.\n", sumaAnys);

        // Altres
        p1.printf();
        p2.printf();
        p3.printf();
    }
}
