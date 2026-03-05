
public class BBDDClassConnectionTest {
    public static DataBase db;

    public static void main(String[] args){
        db = new DataBase("admin", "l0n3lyr04d", "SmashingRecords");
        db.connect();

        // Contingut d'una casella   (taula on cercar, qué es vol cercar, clau de la taula amb la qual es vol filtrar, filtrar per)
        String s = db.getInfo("Ubicación", "Nombre", "Descripción", "abajo");
        System.out.println(s);

        int f = db.getNumFilesTaula("Género");
        System.out.println("\n"+f);

        String[] g = db.getInfoArray("Ubicación", "Nombre");
        for(int i=0;i<g.length;i++){
            System.out.println(g[i]);
        }
    }



}
