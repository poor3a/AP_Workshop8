import java.io.*;
import java.util.ArrayList;

public class Note implements Serializable {
    private final String title;
    private ArrayList<String> linesOfNote;
    private String time;

    public Note(String title, String time, ArrayList<String> notes) {
        this.title = title;
        this.time = time;
        this.linesOfNote = notes;
    }

    public String getTitle() {
        return this.title;
    }

    public ArrayList<String> getLinesOfNote() {
        return this.linesOfNote;
    }

    public String getTime() {
        return this.time;
    }

    public boolean addLine(String line) {
        return this.linesOfNote.add(line);
    }

    public void showNote() {
        System.out.print("///////////      *");
        System.out.print(this.title);
        System.out.println("*      ///////////");
        System.out.println();
        for(String line : linesOfNote){
            System.out.println(line);
        }
    }
    public void export() throws IOException
    {
        FileWriter fileWriter = new FileWriter("src/exports/" +title +".txt");
        fileWriter.write(title + "               " +time + "\n");
        for (String line : linesOfNote)
        {
            fileWriter.write(line + "\n");
        }
        fileWriter.close();
    }
}