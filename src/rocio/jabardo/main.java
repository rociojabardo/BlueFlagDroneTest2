package rocio.jabardo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Blue Flag Drone Test reads a set of drone instructions from a file, storing the locations where
 * the billboards were photographed and the number of time they were.
 * @author rociojabardovelasco
 *@param input file name. In case there is no file name as an argument, the program will ask for it on console
 */
public class main {

	//map with the location of photographed billboards
	static HashMap <Integer, HashMap> map = new HashMap<Integer, HashMap>();
	//total number of photographed billboards
	static int numOfBillboards=0;

	public static void main(String[] args) {
		
		//current position in the map of the drone 1
		MapLocation positionDrone1 = new MapLocation(true);
		//current position in the map of the drone 2
		MapLocation positionDrone2 = new MapLocation(false);

		BufferedReader br = null;
		String fileName=null;
		try{
			//There is no input file as an argument, it reads from console
			if(args.length!=1)
			{
				System.out.println("Please insert <path/name> of the file");
				BufferedReader brInput = new BufferedReader(new InputStreamReader(System.in));
				fileName= new String(brInput.readLine());

			}
			else
				fileName=args[0];

			br = new BufferedReader(new FileReader(fileName));
			int sCurrentChar;

			//Read input file char by char and process every instruction
			while ((sCurrentChar =br.read()) != -1) {
				char instruction=(char)sCurrentChar;
				if(positionDrone1.isTurn())
				{
					storeInstruction(instruction,positionDrone1 );
					//swap the drones' turns
					positionDrone1.setTurn(false);
					positionDrone2.setTurn(true);
				}
				else
				{
					storeInstruction(instruction,positionDrone2);
					//swap the drones' turns
					positionDrone1.setTurn(true);
					positionDrone2.setTurn(false);
				}
			}

			printResult(fileName);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally {

			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * Method that process an instruction. It can be
	 * '<' : move West
	 * '^' : move North
	 * 'v' : move South
	 * '>' : move East
	 * 'x' : take a photo
	 * @param instruction
	 * @return void. Print an error in case that the instruction is not correct.
	 */
	private static void storeInstruction(char instruction, MapLocation positionDrone){

		switch (instruction){
		case '<': positionDrone.moveWest();
		break;
		case '^': positionDrone.moveNorth();
		break;
		case 'v': positionDrone.moveSouth();
		break;
		case '>': positionDrone.moveEast();
		break;
		case 'x': takePhoto(positionDrone);
		break;
		default: System.out.println("Character "+instruction+" is not a valid instruction.");
		}


	}

	/**
	 * Method that process the instruction 'take a photo'.
	 * It updates the current map, storing a new location where the photo was taken
	 * or adding one more photo to an existing location.
	 * @param void
	 * @return void
	 */
	private static void takePhoto(MapLocation positionDrone)
	{
		HashMap <Integer, Integer> mapaux = new HashMap<Integer, Integer>();

		int x=positionDrone.getX();
		int y=positionDrone.getY();

		mapaux=map.get(x);
		//If there are more billboards in the same 'x' coordinate
		if(mapaux!=null){
			Integer numPhoto=mapaux.get(y);
			//If the billboard has been already photographed, add one more to the sum
			if(numPhoto!=null)
				mapaux.put(y, numPhoto+1);
			//If it is the first time the billboard is photographed
			else{
				mapaux.put(y, 1);
				numOfBillboards++;
			}
			map.put(x, mapaux);
		}
		//First photographed billboard in 'x' coordinate
		else{
			HashMap <Integer, Integer> newMapaux = new HashMap<Integer, Integer>();
			newMapaux.put(y, 1);
			map.put(x, newMapaux);
			numOfBillboards++;
		}

	}

	/**
	 * Method that prints the result.
	 * On console: total number of billboards photographed at least once
	 * On file: All the locations of photographed billboard and number of times they were photographed
	 * @param file: name of the input file
	 * @throws IOException
	 * @return void. This method generates a new file with the result. The file is located in the same path as the input file, and is named as <inputFileName>TestResult.txt
	 */
	private static void printResult(String file) throws IOException{

		System.out.println("Number of billboards photographed at least once: "+numOfBillboards);

		String[] resultFileName = file.split(".txt");
		//Create the output file
		FileWriter resultFile = new FileWriter(resultFileName[0]+"TestResult.txt");

		HashMap <Integer, Integer> mapaux = new HashMap<Integer, Integer>();

		//iterate the current map through all the stored locations
		Iterator itMap = map.entrySet().iterator();
		while (itMap.hasNext()){

			Map.Entry entry = (Entry) itMap.next();
			mapaux=(HashMap<Integer, Integer>) entry.getValue();

			Iterator itMapaux = mapaux.entrySet().iterator();
			while (itMapaux.hasNext()){
				Map.Entry entryaux = (Entry) itMapaux.next();
				//write the result on the output file
				resultFile.write("Position "+entry.getKey()+","+entryaux.getKey()+" Number of photos: "+entryaux.getValue()+'\n');
			}
		}
		resultFile.close();
	}

}
