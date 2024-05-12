import  java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    	ArrayList<String> titles = new ArrayList<>();
    	
    	try (FileInputStream fin = new FileInputStream("titles.bin")){
    		ObjectInputStream in = new ObjectInputStream(fin);
    		titles = (ArrayList<String>) in.readObject();
    		in.close();
    	}
    	catch(FileNotFoundException e) {
    		FileOutputStream fout = new FileOutputStream("titles.bin");
    		ObjectOutputStream out = new ObjectOutputStream(fout);
    		out.writeObject(titles);
    	}
    	Command.show(titles);
    }
}
