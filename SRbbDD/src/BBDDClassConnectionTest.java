public class BBDDClassConnectionTest {
    public static DataBase db;

    public static void main(String[] args){
        db = new DataBase("admin", "l0n3lyr04d", "SmashingRecords");
        db.connect();
    }


}
