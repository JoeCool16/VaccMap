import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;

class CSVREADER {
    public static File file = new File("COVID19_Data\\us_counties_covid19_daily.csv");
    public static File county = new File("County_Coords_CSV.csv");
    public static int DEATHS = 5;
    public static int CASES = 4;
    public static HashMap<String, String[]> finalDataMap;
    public static TreeMap<Long, ArrayList<String>> topCases;
    public static TreeMap<Long, ArrayList<String>> topDeaths;

    public static void makeMaps() throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        reader.nextLine();
        finalDataMap = new HashMap<>();
        topCases = new TreeMap<>(Collections.reverseOrder());
        topDeaths = new TreeMap<>(Collections.reverseOrder());
        while (reader.hasNextLine()) {
            //line contains the data!
            String[] line = reader.nextLine().toLowerCase().split(",");
            finalDataMap.put(line[1], new String[3]);
            finalDataMap.get(line[1])[0] = line[DEATHS];

            topCases.putIfAbsent(Long.parseLong(line[4]), new ArrayList<String>());
            topCases.get(Long.parseLong(line[4])).add(line[1]);
            topDeaths.putIfAbsent(Long.parseLong(line[5]), new ArrayList<String>());
            topDeaths.get(Long.parseLong(line[5])).add(line[1]);
        }
    }

    public static void editCode(String fileName) throws FileNotFoundException {
        //code insertion
        File javascript = new File(fileName);
        Scanner javascriptScanner = new Scanner(javascript);
        ArrayList<String> code = new ArrayList<>();
        while (javascriptScanner.hasNextLine()) {
            String line = javascriptScanner.nextLine();
            if (line.contains("<CODE INSERTION POINT>")) {
                line += " [";
                for (String key : finalDataMap.keySet()) {
                    line += "{location: new google.maps.LatLng(" + finalDataMap.get(key)[1] + ", " + finalDataMap.get(key)[2] + "), weight: " + Math.sqrt(Double.parseDouble(finalDataMap.get(key)[0])) + "}, ";
                }
                line += "];";
            }
            code.add(line);

        }

        for (int i = 0; i < code.size(); i++) {
            System.out.println(code.get(i));
        }

        System.out.println(finalDataMap.size());

        try {
            FileWriter fw = new FileWriter(fileName);
            for (int i = 0; i < code.size(); i++) {
                System.out.println(code.get(i));
                fw.write(code.get(i) + "\n");
            }
            fw.close();

        } catch (Exception e) {
            System.out.println("RIP CODE!");
        }
    }

    public static void setLongLat() throws FileNotFoundException {
        //gets latitude and longitude of county
        Scanner countiesReader = new Scanner(county);
        countiesReader.nextLine();
        Set<String> keySet = finalDataMap.keySet();
        while (countiesReader.hasNextLine()) {
            String[] line = countiesReader.nextLine().toLowerCase().split(",");
            if (keySet.contains(line[5]) || keySet.contains(line[1])) {
                int x = 5;
                if (!keySet.contains(line[5])) {
                    x = 1;
                }
                finalDataMap.get(line[x])[1] = line[3];
                finalDataMap.get(line[x])[2] = line[4];
            }
        }
        ArrayList<String> toRemove = new ArrayList<>();
        for (String key : keySet) {
            if (finalDataMap.get(key)[2] == null) {
                toRemove.add(key);
                continue;
            }
        }
        // removes any counties with no lat or long
        for (String key : toRemove) {
            finalDataMap.remove(key);
        }
    }

    public static String stringTOP(TreeMap<Long, ArrayList<String>> map, int numberofTop) {
        HashSet<String> duplicates = new HashSet<>();
        String answer = "";
        boolean number = false;
        int counter = 0;
        for (long key : map.keySet()) {
            if (counter == numberofTop) {
                break;
            }
            for (String stringCounty : map.get(key)) {
                if (!duplicates.contains(stringCounty)) {
                    duplicates.add(stringCounty);
                    answer += stringCounty + " ";
                    counter++;
                    number = true;
                }
                if (number) {
                    answer += key + "\n";
                    number = false;
                }

            }

        }
        return answer;
    }

    public static void main(String[] args) throws FileNotFoundException {

            makeMaps();
            System.out.println(stringTOP(topCases,20));
            System.out.println(stringTOP(topDeaths,20));


    }
}


