import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		Command.cammandManager(x,titles);	
	}
	
	
	public static void cammandManager(int command, ArrayList<String> titles) throws ClassNotFoundException, IOException {
		if (command==0) {
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
		String notelines = sc.nextLine();
		while (!notelines.equals("#")) {
			notes.add(notelines);
		}
		LocalDateTime time = LocalDateTime.now();
		String t = time.getYear() + " " + time.getMonthValue() + " " + 
		time.getDayOfMonth() + time.getHour() + " " + time.getMinute();
		Note note = new Note(title,t,notes);
		titles.add(title);
		FileOutputStream fout = new FileOutputStream("titles.bin");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(titles);
		fout.close();
		out.close();
		FileOutputStream fout1 = new FileOutputStream(title+".bin");
		ObjectOutputStream out1 = new ObjectOutputStream(fout1);
		fout1.close();
		out1.close();
		Command.show(titles);
	}
	
	public static void remove(ArrayList<String> titles) throws IOException, ClassNotFoundException {
		System.out.println("choose on of notes to remove (enter 0 to back to menu");
		int noteIndex = 1;
		for (String i : titles) {
			FileInputStream fin = new FileInputStream(i+".bin");
			ObjectInputStream in = new ObjectInputStream(fin);
			Note note = (Note) in.readObject();
			System.out.println(noteIndex+"."+note.getTitle()+"  "+note.getTime());
			fin.close();
			in.close();
			noteIndex++;
		}
		Scanner sc = new Scanner(System.in);
		int index = sc.nextInt();
		if (index==0) {
			Command.show(titles);
			return;
		}
		titles.remove(index-1);
		FileOutputStream fout = new FileOutputStream("titles.bin");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(titles);
		fout.close();
		out.close();
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
