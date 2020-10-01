
import java.util.*;
import java.util.Scanner;
import java.io.*;

public class AminoAcid {
	/* The main method asks the user for two text files(DNA sequence file and amino acid file)
	and then uses File to hold the file and Scanner to read from both of those files. It calls 
	a method called getData and gives the scanners as parameters to that method */
    public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the DNA sequence file ending with .txt: ");
		String dnaFile = keyboard.nextLine();
		System.out.print("Enter the amino acid file ending with .txt: ");
		String aminoFile = keyboard.nextLine();
		File file = new File(dnaFile); 
		Scanner DNAreader = new Scanner(file);
		File file1 = new File(aminoFile); 
		Scanner aminoReader = new Scanner(file1);				
		getData(DNAreader, aminoReader);
	}
	/* This method gets both scanners as parameters and then reads all the lines of
	DNAreader scanner and stores them into a stringbuffer and stores all the integers 
	into and arraylist which is not used in the program. It reads the codon and 
	amino acid of aminoReader scanner seperately and stores them into a Map. */
	public static void getData(Scanner DNAreader, Scanner aminoReader) {
		MyRedBlackTreeMap<String, String> theMap= new MyRedBlackTreeMap<String, String>();
		StringBuffer nString= new StringBuffer();
		List<Integer> integers = new ArrayList<Integer>();
		while (DNAreader.hasNext()) { 
			nString.append(DNAreader.next());
			while (DNAreader.hasNextInt()) {
			integers.add(DNAreader.nextInt());
			}
		}
		while (aminoReader.hasNext()) {
			String codon= aminoReader.next();
			String Amino= aminoReader.next();
			theMap.put(codon, Amino);
		}
		getPosition(nString, theMap);
	}
	/* This method gets the length of new stringbuffer and goes through a for loop,
	in the for loop it gets the substring from n to n+1 and stores it into the old String 
	which is null in the beginning of loop. After every the length of nString reaches 
	3 it uses Map to get those three characters out, which is the amino acid in this case. 
	If Map has it, it returns them else null. It then uses another Map to check if the Map2 
	contain that amino acid, if it returns null, then it just simply add 
	the position of that into Set. If it is not null then it will make that Set equal to 
	the amino acid and add the position to Set. Then it puts both the amino acid String 
	and Set containing position into Map, makes the old String empty and add 3 to the position(i)
	since the codon are three character long.It also adds a timer in the start of the loop and 
	stops it at the end of the loop to calculate the time it takes for the data to run.*/
	public static void getPosition(StringBuffer nString,  MyRedBlackTreeMap<String, String> theMap) {
		MyTimer timer= new MyTimer();
		MyRedBlackTreeMap<String, MyRedBlackTreeSet<Integer>> theMap2= new MyRedBlackTreeMap<String, MyRedBlackTreeSet<Integer>>();
		int i= 0;
		StringBuffer newString= new StringBuffer();
		int length1= nString.length();
		timer.start();
		for (int n= 0; n < length1; n++) {
			MyRedBlackTreeSet<Integer> theSet = new MyRedBlackTreeSet<>();
			newString.append(nString.substring(n, n+1));
			while (newString.length()==3) {
				if(theMap2.containsKey(theMap.get(newString.toString()))) {
					theSet= theMap2.get(theMap.get(newString.toString()));
					theSet.add(i);
				} else { 
					theSet.add(i);
				}
				theMap2.put(theMap.get(newString.toString()), theSet);
				newString.setLength(0);
				i= i+3;	
			}
		}
		timer.stop();
		String printing= timer.toString();
		printResult(theMap2, printing);
	}
	/* This method uses and itertor to Store the map data since map has no toString method
	and then goes through line and prints it and also prints the time used.*/ 
	public static void printResult(MyRedBlackTreeMap<String, MyRedBlackTreeSet<Integer>> theMap2, String printing) {
		Iterator<MapEntry<String, MyRedBlackTreeSet<Integer>>> iterate = theMap2.iterator();
		while(iterate.hasNext()) {
			System.out.println(iterate.next());
		}
		System.out.println("Time: " + printing);
	}
}