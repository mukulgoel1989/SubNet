/**
 * Created by mukul on 07/08/14.
 */

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;
import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetToSubnet {
    ArrayList<String[]> subNetGraph;


   public static void main(String args[]){
    getFollowers(35765232L,1);
   }

    static private void getFollowers(long id, int maxDepth) {
        List<String[]> edgeRows;  //Stores data of 1 file at a time
        int depth = 0;  //counter to keep track of current depth
        List<String[]> subgraph = new ArrayList<String[]>(); //This list will contain the final edge list of subgraph for particular id and with a depth

        List<String> parentIds = new ArrayList<String>(); //List to maintain list of nodes to track
        CSV csv = CSV.separator(',').create();
        parentIds.add(id + "");
        int count = 0;
            while (depth < maxDepth) {  //Loops to depth
                for(int k = 0; k<parentIds.size();k++){ //While there are nodes whose follower we need to check
                    id = Long.parseLong(parentIds.get(k));
                    //parentIds.remove(0);  //remove the current id from list
                    for (int i = 0; i <= 0; i++) {  //iterate over all files
                        String fileName = "/home/mukul/IDE/idea-IC-135.909/bin/part-" + String.format("%05d", i);
                        System.out.println("Going for file " + fileName);

                        CSVReader rd = csv.reader(fileName);

                        try {
                            edgeRows = rd.readAll();
                            if ((id >= Long.parseLong(edgeRows.get(1)[0])) || (id <= Long.parseLong(edgeRows.get(edgeRows.size() - 1)[0]))) { //first column is sorted by id so check if required id is in the list or not by checking range
                                for (int j = 1; j < edgeRows.size(); j++) { //first row is header

                                    if (id == Long.parseLong(edgeRows.get(j)[0])) { //go line by line and if matches add to subgraph
                                        System.out.println("Matching for id "+ id);
                                        parentIds.add(edgeRows.get(j)[1]);
                                        subgraph.add(new String[]{edgeRows.get(j)[0], edgeRows.get(j)[1]});
                                    }
                                }
                            } else { //if id is not in file then start with new file
                                continue;
                            }
                            edgeRows.clear();
                            rd.close();
                        } catch (IOException ioe) {
                            System.out.println(ioe);
                        }finally {

                        }

                }


            }
        ++depth; //increase depth
        }
        for(String[] a: subgraph)
        System.out.println(a[0]+","+a[1]);
    }


}
