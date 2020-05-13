
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class prediction {

		
	
	ArrayList<String> poll(String inputFile,int sampleSize) throws IOException {
		ArrayList<String> data= new ArrayList<String>();
		ArrayList<String> regValues= new ArrayList<String>();
		data=getData(inputFile);
			if(data.size()>sampleSize) {//check to see if enough information to calculate regression
				for (int i=data.size()-sampleSize;i<data.size();i++) { //pulls data from end of file
					regValues.add(data.get(i));	
					}
				Apache abc= new Apache();
				return abc.predict(regValues);
			}
			else
				return null;
			//calculates prediction and returns values

		
	}
	ArrayList<String> getData(String inputFile) throws FileNotFoundException {
		File file=new File(inputFile);
		ArrayList<String> data= new ArrayList<String>();
		algo check= new algo();
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
		scan.useDelimiter(",");
		try {
		while(scan.hasNext()) {
			String time= scan.next().replace("\n", "");
			
			if(!time.equals("")&&check.checkTime(time)) {
				
				String vwap=scan.next().replace("\n", "");
				String bestPrice=scan.next().replace("\n", "");
				data.add(time+","+vwap+","+bestPrice+",");
				
			}
		}
		return data;
		}
		catch(Exception e) {
		
			return null;
		}
	}
}
