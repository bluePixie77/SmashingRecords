package POO.cotxes;
import java.util.Scanner;

public class MainCotxes {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Declaració
        Cotxe c1, c2, c3;
        float q;

        System.out.print("Enter acceleration: ");
        q = input.nextFloat();

        // Cridades als constructors
        c1 = new Cotxe(Cotxe.MARCA.SKODA, "1979TSP");
        c2 = new Cotxe(Cotxe.MARCA.SEAT, "4321FCL", 199, 3);
        c3 = new Cotxe(Cotxe.MARCA.KIA, "5891MCF", 88, 2);

        // Setters (mutadores)
        c1.setVelocitatNumPortes(77, 5);
        c2.setMarca(Cotxe.MARCA.RENAULT);
        c3.setMatricula("1985MCF");

        // Getters
        int SumaNumPortes = c1.getNumPortes() + c2.getNumPortes() + c3.getNumPortes();
        System.out.printf("SUMA NUM PORTES: %d.\n", SumaNumPortes);

        // Altres
        c1.accelerar(q);
        c2.accelerar(q);
        c3.accelerar(q);

        c1.print();
        c2.print();
        c3.print();
    }
}
