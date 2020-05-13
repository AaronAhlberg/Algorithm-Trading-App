import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class calculate {
	//calculate takes the data file source and than converts it into
	//the Average Weighted Price outputting: TIME, AWP, Bestprice
	//to text file specified
	@SuppressWarnings("resource")
public void main(String outputFile, String inputBid, String inputAsk,int max,int min) throws IOException {
		//output file
		File file = new File(outputFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		ArrayList<String> bid= new ArrayList<String>();
		ArrayList<String> ask= new ArrayList<String>();
		//input files
		bid=get(inputBid);
		ask=get(inputAsk);
		int stop=0;
		Scanner scan1,scan2;
		if(bid.size()-ask.size()>=0)
			 stop=ask.size();
		else
			stop=bid.size();
		for(int i=0;i<stop;i++) {
			
			scan1=new Scanner(bid.get(i)).useDelimiter(" ");
			scan2=new Scanner(ask.get(i)).useDelimiter(" ");
			//writes time from the bid date and advances the scanner
			//to the data in the key for scan 2
			String time=scan1.next();
			scan2.next();
			//gets all the data and sets it to a variable and calculates 
			Double bidWeight=Double.parseDouble(scan1.next());
			Double askWeight=Double.parseDouble(scan2.next());
			Double bestBid=Double.parseDouble(scan1.next());
			Double bestAsk=Double.parseDouble(scan2.next());
			double ans= (askWeight+bidWeight)/2; 
			double bestPrice= (bestBid+bestAsk)/2;
			
			//writes final line to buffered reader and flushes it. only writes
			//if the AWP within limits, handles outliars that can affect regression
			if(ans<max&&ans>min)
			bw.write(time+","+ans+","+bestPrice+",\n");
			bw.flush();
		}
		//close buffered writer
		bw.close();
		
	}
	
	
	//takes string entry for file path and returns an array of string characters
	//that follow the structure of "timedate bidWeight bestBid"
	@SuppressWarnings("unused")
	static ArrayList<String> get(String location) throws FileNotFoundException {
		File file = new File(location);
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
		scan.useDelimiter("-");
		String time = "";
		String date = "";
		double price;
		double shares;
		double totalShares;
		double totalOfShares = 0;
		ArrayList<String> arr= new ArrayList<String>();
		//Handles the input of each individual value in certain order
		//in the text file seperated by a "-"
		//the primary key is: time+date(HH::MM::SS) and price, shares, and total 
		//are attributes to each key
		//time is used as primary key to coincide with the time compared to other
		//file
		while (scan.hasNext()) {
			totalOfShares = 0;
			ArrayList<Double> Price = new ArrayList<Double>();
			ArrayList<Double> Shares = new ArrayList<Double>();

			do {
				time = scan.next();
				date = scan.next();
				//format the string numbers into Double and handle cases
				price = Double.parseDouble(scan.next().replace("$", "").replace(",", ""));
				shares = Double.parseDouble(scan.next().replace(",", ""));
				scan.next();
				
				Price.add(price);//adds all prices for that time
				Shares.add(shares);// adds all shares for that time
				totalOfShares += shares;
			} while (scan.hasNext(time));//scan the next elements if they have the format of the last scanned time
			
			
			//calculates AWP
			double weight=0;
			for (int i = 0; i < Price.size(); i++) {
				weight+=(Price.get(i)*(Shares.get(i)/totalOfShares));
			}
			//calculates quoted spread
			double bestBid= Price.get(0);
			algo test=new algo();
			if(test.checkTime(time+date))
			arr.add(time+date+" "+weight+" "+bestBid+" ");//adds in any value to the output dataset
		}
			System.out.println("ask Done");
			return arr;
	}
}
