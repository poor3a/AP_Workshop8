
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Command {

    public static void showMenu(ArrayList<String> titles) throws ClassNotFoundException, IOException {
        System.out.println("1-Add");
        System.out.println("2-Remove");
        System.out.println("3-Notes");
        System.out.println("4-Export");
        System.out.println("press 0 to exit");
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        Command.commandManager(x,titles);
    }


    private static void commandManager(int command, ArrayList<String> titles) throws ClassNotFoundException, IOException {
        switch (command)
        {
            case 0 ->
            {
                FileOutputStream fOut = new FileOutputStream("src/files/titles.bin");
                ObjectOutputStream out = new ObjectOutputStream(fOut);
                out.writeObject(titles);
                fOut.close();
                out.close();
                System.exit(0);
            }
            case 1 -> Command.add(titles);
            case 2 -> Command.remove(titles);
            case 3 -> Command.showNotes(titles);
            case 4 -> Command.export(titles);
            default ->
            {
                System.out.println("wrong input");
                showMenu(titles);
            }

        }

    }
    private static void add(ArrayList<String> titles) throws IOException, ClassNotFoundException {
        System.out.println("Please choose a title for your note (enter 0 to back to menu)");
        Scanner sc = new Scanner(System.in);
        String title = sc.nextLine();
        while (Command.duplicate(title, titles)) {
            System.out.println("A note with this title already exist please try another title"
                    + " (enter 0 to back to menu)");
            title = sc.nextLine();
        }
        if (title.equals("0")) {
            Command.showMenu(titles);
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
        String t = time.getYear() + "/" + time.getMonthValue() + "/" +
                time.getDayOfMonth() +"  "+ time.getHour() + ":" + time.getMinute();
        Note note = new Note(title ,t ,notes);
        titles.add(title);
        FileOutputStream fOut1 = new FileOutputStream("src/files/"+title+".bin");
        ObjectOutputStream out1 = new ObjectOutputStream(fOut1);
        out1.writeObject(note);
        fOut1.close();
        out1.close();
        Command.showMenu(titles);
    }

    private static void remove(ArrayList<String> titles) throws IOException, ClassNotFoundException
    {
        System.out.println("choose on of notes to remove (enter 0 to back to menu");
        int noteIndex = showTitles(titles ,1);
        Scanner sc = new Scanner(System.in);
        int index;
        while (true)
        {
            index = sc.nextInt();
            if (index == 0) {
                Command.showMenu(titles);
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
        Command.showMenu(titles);

    }
    private static int showTitles(ArrayList<String> titles ,int noteIndex) throws IOException, ClassNotFoundException {
        if (titles.isEmpty())
        {
            System.out.println("no notes have been added.");
            showMenu(titles);
            return 0;
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
        return noteIndex;
    }

    public static void showNotes(ArrayList<String> titles) throws IOException, ClassNotFoundException {
        int noteIndex = showTitles(titles ,1);
        if(noteIndex == 0)
            return;
        Scanner sc = new Scanner(System.in);
        int index;
        while (true)
        {
            index = sc.nextInt();
            if (index == 0)
            {
                Command.showMenu(titles);
                return;
            }
            else if (index >= noteIndex)
            {
                System.out.println("index out of bound, please enter a correct number:");
                continue;
            }
            else break;
        }
        index--;
        try(FileInputStream fIn = new FileInputStream("src/files/"+titles.get(index)+".bin"))
        {
            ObjectInputStream in = new ObjectInputStream(fIn);
            Note note = (Note) in.readObject();
            fIn.close();
            in.close();
            note.showNote();
        }catch (FileNotFoundException e)
        {
            System.out.println("file not found");
            showMenu(titles);
        }
        showMenu(titles);
    }
    private static void export(ArrayList<String> titles) throws IOException, ClassNotFoundException {
        int noteIndex = showTitles(titles ,1);
        if(noteIndex == 0)
            return;
        Scanner sc = new Scanner(System.in);
        int index;
        while (true)
        {
            index = sc.nextInt();
            if (index == 0)
            {
                Command.showMenu(titles);
                return;
            }
            else if (index >= noteIndex)
            {
                System.out.println("index out of bound, please enter a correct number:");
                continue;
            }
            else break;
        }
        index--;
        try(FileInputStream fIn = new FileInputStream("src/files/"+titles.get(index)+".bin"))
        {
            ObjectInputStream in = new ObjectInputStream(fIn);
            Note note = (Note) in.readObject();
            fIn.close();
            in.close();
            note.export();
        }catch (FileNotFoundException e)
        {
            System.out.println("file not found");
            showMenu(titles);
        }
        System.out.println("successfully exported in folder src/exports");
        showMenu(titles);
    }
    private static boolean duplicate(String title, ArrayList<String> titles) {
        Scanner sc = new Scanner(System.in);
        for (String i : titles) {
            if (i.equals(title)) {
                return true;
            }
        }
        return false;
    }
}
