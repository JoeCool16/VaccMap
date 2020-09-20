import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;

class CSVREADER {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("COVID19_Data\\us_counties_covid19_daily.csv");
        File county = new File("County_Coords_CSV.csv");
		
        if (!file.isFile()) {
            System.out.println("This is the problem!");
        }
		
        Scanner countiesReader = new Scanner(county);
        countiesReader.nextLine();
        Scanner reader = new Scanner(file);
        HashMap<String, String[]> fileString = new HashMap<>();
        ArrayList<String[]> todaysData = new ArrayList<>();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().toLowerCase().split(",");
            fileString.put(line[1], new String[3]);
            fileString.get(line[1])[0] = line[4];
        }
		
        Set<String> keySet = fileString.keySet();
        while (countiesReader.hasNextLine()) {
            String[] line = countiesReader.nextLine().toLowerCase().split(",");
            if (keySet.contains(line[5]) || keySet.contains(line[1])) {
                int x = 5;
                if (!keySet.contains(line[5])) {
                    x = 1;
                }
                fileString.get(line[x])[1] = line[3];
                fileString.get(line[x])[2] = line[4];
            }
        }
        ArrayList<String> toRemove = new ArrayList<>();
        for (String key : keySet) {
            if (fileString.get(key).contains(null)) {
                toRemove.add(key);
                continue;
            }


        }
		
		for(String key: toRemove){
			fileString.remove(key);
		}
		
        File javascript = new File("test.js");
        Scanner javascriptScanner = new Scanner(javascript);
        ArrayList<String> code = new ArrayList<>();
        while (javascriptScanner.hasNextLine()) {
            String line = javascriptScanner.nextLine();
            if (line.contains("<CODE INSERTION POINT>")) {
                line += " [";
                int counter = 0;
                for (String key : fileString.keySet()) {
                    if(counter == 3){break;}
                    line += "{location: new google.maps.LatLng(" + fileString.get(key)[1] + ", " + fileString.get(key)[2] + "), weight: " + fileString.get(key)[0] + "}, ";
					counter++;
                }
                line += "];";
            }
            code.add(line);

        }
		
		
		System.out.println(fileString.size());

        try {
            FileWriter fw = new FileWriter("test.js");
            for (int i = 0; i < code.size(); i++) {
                System.out.println(code.get(i));
                fw.write(code.get(i) + "\n");
            }
            fw.close();

        } catch (Exception e) {
            System.out.println("RIP CODE!");
        }


    }
}

