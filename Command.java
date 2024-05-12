import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Command {
    public static void show(ArrayList<String> titles) throws ClassNotFoundException, IOException {
        System.out.println("1-Add");
        System.out.println("2-Remove");
        System.out.println("3-Notes");
        System.out.println("4-Export");
        System.out.println("press 0 to exit");
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        Command.commandManager(x,titles);
    }


    public static void commandManager(int command, ArrayList<String> titles) throws ClassNotFoundException, IOException {
        if (command==0) {
            FileOutputStream fOut = new FileOutputStream("src/files/titles.bin");
            ObjectOutputStream out = new ObjectOutputStream(fOut);
            out.writeObject(titles);
            fOut.close();
            out.close();
            System.exit(0);
        }
        if (command==1) {
            Command.add(titles);
        }
        if (command==2) {
            Command.remove(titles);
        }
    }
    public static void add(ArrayList<String> titles) throws IOException, ClassNotFoundException {
        System.out.println("Please choose a title for your note (enter 0 to back to menu)");
        Scanner sc = new Scanner(System.in);
        String title = sc.nextLine();
        while (Command.duplicate(title, titles)) {
            System.out.println("A note with this title already exist please try another title"
                    + " (enter 0 to back to menu)");
            title = sc.nextLine();
        }
        if (title.equals("0")) {
            Command.show(titles);
            return;
        }
        ArrayList<String> notes = new ArrayList<>();
        System.out.println("now enter your note (for finish enter the # at the last line)");
        String noteLine;
        while (true) {
            noteLine = sc.nextLine();
            if (noteLine.equals("#"))
                break;
            notes.add(noteLine);
        }
        LocalDateTime time = LocalDateTime.now();
        String t = time.getYear() + " " + time.getMonthValue() + " " +
                time.getDayOfMonth() + time.getHour() + " " + time.getMinute();
        Note note = new Note(title ,t ,notes);
        titles.add(title);
        FileOutputStream fOut1 = new FileOutputStream("src/files/"+title+".bin");
        ObjectOutputStream out1 = new ObjectOutputStream(fOut1);
        out1.writeObject(note);
        fOut1.close();
        out1.close();
        Command.show(titles);
    }

    public static void remove(ArrayList<String> titles) throws IOException, ClassNotFoundException
    {
        System.out.println("choose on of notes to remove (enter 0 to back to menu");
        int noteIndex = 1;
        if (titles.isEmpty())
        {
            System.out.println("no notes have been added.");
            show(titles);
            return;
        }
        try{
            for (String title : titles) {
                FileInputStream fin = new FileInputStream("src/files/" + title + ".bin");
                ObjectInputStream in = new ObjectInputStream(fin);
                Note note = (Note) in.readObject();
                System.out.println(noteIndex + "." + note.getTitle() + "  " + note.getTime());
                fin.close();
                in.close();
                noteIndex++;
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("file not found");
        }
        Scanner sc = new Scanner(System.in);
        int index;
        while (true)
        {
            index = sc.nextInt();
            if (index == 0) {
                Command.show(titles);
                return;
            }
            else if (index >= noteIndex)
            {
                System.out.println("index out of bound, please enter a correct number:");
                continue;
            }
            else break;
        }
        index--;//ایندکس کاربر از 1 شروع میشود ولی در array list از 0 شروع میشود
        File f = new File("src/files/"+titles.get(index)+".bin");
        f.delete();
        titles.remove(index);
        Command.show(titles);


    }
    public static boolean duplicate(String title, ArrayList<String> titles) {
        Scanner sc = new Scanner(System.in);
        for (String i : titles) {
            if (i.equals(title)) {
                return true;
            }
        }
        return false;
    }
}