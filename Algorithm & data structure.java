/** -------------------------------------------------------
 *  COMP 352 - Assignment #3/#4
 *  Programming Question - Main Algorithm
 *  
 *   -------------------------------------------------------- */

//Importers
//import java.util.ArrayList; 
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class SmartAR {

	/*Variables*/
	
	//The Sequence version of the SmartAR:
	public static ArrayList<ArrayList<String>> SmartARS = new ArrayList<ArrayList<String>>();
	
	//The Hash Map version of the SmartAR
	public static Map<String, ArrayList<String>> SmartARM = new HashMap<>();

	
	//Common variables
	private static ArrayList<String> newValList = new ArrayList<>();
	private static int threshold; 
	private final static String alphaNumString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	
	
	
	/*Method use to have an alphanumerical generator*/
	
	private static String alphaNum(int a) {
		String newVar = "";
		for(int i = 1; i <= a; i++) {
			int randomVar = ThreadLocalRandom.current().nextInt(0, alphaNumString.length());
			newVar += alphaNumString.charAt(randomVar);
		}
		return newVar;
	}//End of method alphaNum
	
	
	/*Methods asked to be made*/
	
	//Method setThreshold
	public static void setThreshold(int t) {
		threshold = t;
	}//End of method setThreshold
	
	//Method setKeyLength
	public static String setKeyLength(String k) {
		if(k.length() < 6) {
			k += alphaNum(6 - k.length());
		}
		else if(k.length() > 12) {
			k = k.substring(0, 11);
		}
		return k;
	}//End of method setKeyLength
	
	

	//Method generate
	public static void generate(int n) {
		
		String newKey = "";
		int i = 1;
		
		//The sequence version
		if(threshold < 100) {
			
			boolean check = false;
			do {
				check = false;
				newKey = alphaNum(ThreadLocalRandom.current().nextInt(6, 13));
				for(int j = 0; j < SmartARS.size(); j++) {
					if(SmartARS.get(j).contains(newKey) == true) {
						check = true;
						break;}
					}
				
				if(check == true)
					continue;
			
				newValList.add(newKey);
				SmartARS.add(newValList);
				i++;
			} while(check==true|| i != n);
			
			return;
		}
		
		
		//The hash map version
		do {
			newKey = alphaNum(ThreadLocalRandom.current().nextInt(6, 13));
			if(SmartARM.containsKey(newKey) == true)
				continue;
			SmartARM.put(newKey, newValList);
			i++;
			
		} while(SmartARM.containsKey(newKey)==true|| i != n);
		
	}//End of method generate
	
	
	//Method allKeys
	public static String allKeys() {
		String answer = "";
		
		//The sequence version
		if(threshold < 100) {
			for(int j = 0; j < SmartARS.size(); j++) {
				answer += "\n" + SmartARS.get(j).get(0);
			}

			return answer;
		}
		
		//The hash map version
		Map<String, ArrayList<String>> sortedMap = new TreeMap<String, ArrayList<String>>(SmartARM);
		System.out.print("Sorted keys sequence: ");
		for(String key : sortedMap.keySet())
			answer += "\n" + key;
		return answer;

	}//End of method allKeys
	
	
	//Method add
	public static void add(String k, String val) {
		//The sequence version
		if(threshold < 100) {
			
			for(int j = 0; j < SmartARS.size(); j++) {
				if(SmartARS.get(j).contains(k) == true) 
					newValList = SmartARS.get(j);
				
				newValList.add(1, val);
				SmartARS.set(j,newValList);
				
			}
			
			return;
		}
		
		//The hash map version
		ArrayList<String> newValList = new ArrayList<>();

		if(SmartARM.containsKey(k) == true)
			newValList = SmartARM.get(k);
		
		newValList.add(0,val);
		SmartARM.put(k, newValList);
		
	}//End of method add
	
	
	//Method remove
	public static void remove(String k) {
		//The sequence version
		if(threshold < 100) {
			
			for(int j = 0; j < SmartARS.size(); j++) {
				if(SmartARS.get(j).contains(k) == true) {
					SmartARS.remove(j);
					break;
				}		
			}
			
			return;
		}
		
		//The hash map version
		SmartARM.remove(k);
		
	}//End of method remove
	
	
	//Method getValues
	public static String getValues(String k) {
		String answer = "";

		//The sequence version
		if(threshold < 100) {
			for(int j = 0; j < SmartARS.size(); j++) {
				if(SmartARS.get(j).contains(k) == true) 
					for(int m = 1; m < SmartARS.get(j).size(); m++)
						answer += "\n" + SmartARS.get(j).get(m);
				
			}
			
			return answer;
		}
		
		//The hash map version
		newValList = SmartARM.get(k);
		for(int i = 0; i < newValList.size(); i++)
			answer += "\n" + newValList.get(i);
		return answer;
	}//End of method getValues
	
	
	//Method nextKey
	@SuppressWarnings("unlikely-arg-type")
	public static String nextKey(String k) {
		String answer = "";
		int s = 0;

		//The sequence version
		if(threshold < 100) {
			for(int j = 0; j < SmartARS.size(); j++) 
				if(SmartARS.get(j).contains(k) == true) 
					s = SmartARS.indexOf(j) + 1;
					answer = SmartARS.get(s).get(0);
					
			return answer;
		}
		
		//The hash map version
		String prev = null;
		for (Iterator<String> i = SmartARM.keySet().iterator(); i.hasNext();) {
		    String current = i.next();

		    if(prev == k) {
            	answer = current;
            	break;
            	}
		    prev = current;
		}
		return answer;
	}//End of method nextKey
	
	
	//Method prevKey
	@SuppressWarnings("unlikely-arg-type")
	public static String prevKey(String k) {
		String answer = "";
		int s = 0;

		//The sequence version
		if(threshold < 100) {
			for(int j = 0; j < SmartARS.size(); j++) 
				if(SmartARS.get(j).contains(k) == true) 
					s = SmartARS.indexOf(j) - 1;
					answer = SmartARS.get(s).get(0);
			
			return answer;
		}
		//The hash map version
		String prev = null;
		for (Iterator<String> i = SmartARM.keySet().iterator(); i.hasNext();) {
		    String current = i.next();

		    if(current == k) {
            	answer = prev;
            	break;
            	}
		    
		    prev = current;
		}

		return answer;
	}//End of method prevKey
	
	
	//Method previousCars
	public static String previousCars(String k) {
		String answer = "All the previous cars with the same licence plates:";

		//The sequence version
		if(threshold < 100) {
			return answer + getValues(k);
		}
		//The hash map version
		
		return answer + getValues(k);
	}//End of method previousCars
	
	
	
	/*Testing zone*/
	public static void main(String[] args) throws Exception{
		
		//Test file 1
		InputStream inputStream;
		

		
		String[] keys;
		String s = "";
		String f = "";
		int t = 0;

		//Test file 1
		inputStream = new FileInputStream("ar_test_file1.txt");
		Scanner scanner1 = new Scanner(inputStream); 
		
		while(scanner1.hasNextLine()){
			
			f = scanner1.nextLine();
			s = s + setKeyLength(f) + "\n";
			t++;
		 }
		setThreshold(t);
		
		keys = s.split("\n");
		for (String key : keys) {
		    add(key,null);
		}
		System.out.print(allKeys());
		System.out.println("\n");

		inputStream = null;
		//Test file 2
		
		
		inputStream = new FileInputStream("ar_test_file2.txt");
		Scanner scanner2 = new Scanner(inputStream); 
		t = 0;

		while(scanner2.hasNextLine()){
			
			f = scanner2.nextLine();
			s = s + setKeyLength(f) + "\n";
			t++;
		 }
		setThreshold(t);
		
		keys = s.split("\n");
		for (String key : keys) {
		    add(key,null);
		}

		add("UAI432MU9LQ","apple");
		System.out.print(getValues("UAI432MU9LQ"));
		System.out.println("\n");
		inputStream = null;

		
		//Test file 3
		inputStream = new FileInputStream("ar_test_file3.txt");
		Scanner scanner3 = new Scanner(inputStream); 

		t = 0;

		while(scanner3.hasNextLine()){
			
			f = scanner3.nextLine();
			s = s + setKeyLength(f) + "\n";
			t++;
		 }
		setThreshold(t);
		
		keys = s.split("\n");
		for (String key : keys) {
		    add(key,null);
		}

		add("P5ARD5","apple");
		add("P5ARD5","pear");
		add("P5ARD5","oranges");
		System.out.print(getValues("P5ARD5"));
		
		remove("P5ARD5");
		System.out.print(allKeys());
		System.out.println("\n");
		inputStream = null;

		
		scanner1.close();
		scanner2.close();
		scanner3.close();

	}

}
