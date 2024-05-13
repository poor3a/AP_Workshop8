import  java.io.*;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<String> titles = new ArrayList<>();

        try (FileInputStream fin = new FileInputStream("src/files/titles.bin")){
            ObjectInputStream in = new ObjectInputStream(fin);
            titles = (ArrayList<String>) in.readObject();
            fin.close();
            in.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("created titles!");
            FileOutputStream fout = new FileOutputStream("src/files/titles.bin");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(titles);
        }
        Command.showMenu(titles);

    }
}