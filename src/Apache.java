import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

//Calculates deviation from the bid/ask quote
public class Apache {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

	}
	//calculates the regression of inputted data and
	//returns the prediction value
	ArrayList<String> predict(ArrayList<String> data) {
		double ans=0.0;
		double weight=0.0;
		double bestPrice=0.0;
		String time="";
		PolynomialCurveFitter fit = PolynomialCurveFitter.create(6);
		WeightedObservedPoints obs = new WeightedObservedPoints();
		int count=1;
		for(int i=0;i<data.size();i++) {
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(data.get(i));
			scan.useDelimiter(",");
			time=scan.next();
			weight=Double.parseDouble(scan.next());
			bestPrice=Double.parseDouble(scan.next());
			
			//algo checker= new algo();
			//if(checker.checkTime(time)) {
			obs.add(count,weight);
			count++;
			//}
		}
	
		double[] arr = fit.fit(obs.toList());
		double pred = prediction(arr, count);
		if(pred!=0)
		ans = ((pred) );
		ArrayList<String> info = new ArrayList<String>();
		info.add(time);
		info.add(""+ans);
		info.add(""+bestPrice);
		return info;
	}

	void start(String inputFile, String outputFile,int simulationSize) throws IOException {

		Apache regression=new Apache();
		int count=1;
		ArrayList<String> data= new ArrayList<String>();
		ArrayList<String> calculation= new ArrayList<String>();
		File file= new File(inputFile);
		Scanner scan= new Scanner(file);
		scan.useDelimiter(",");
		algo check= new algo();
		Writer write= new Writer();
		write.createFile(outputFile);
		write.setFile(outputFile);
	
		while(scan.hasNext()) {
			String time=scan.next().replace("\n", "");
			Long epoch= Long.parseLong(time);
			time=convertEpochTime(epoch);
			try {
			Double bid=Double.parseDouble(scan.next().replace("\n", ""));
			Double ask=Double.parseDouble(scan.next().replace("\n", ""));
			String lastPrice=scan.next().replace("\n", "");
			Double awp= ask-bid;
		//if(check.checkTime(time)) {
			data.add(time+","+awp+","+lastPrice+",");
			if(count>=simulationSize) {
				calculation=regression.predict(data);
				write.write(calculation.get(0)+","+calculation.get(1)+","+calculation.get(2)+",\n");
				System.out.println(calculation.get(0)+","+calculation.get(1)+","+calculation.get(2)+",");
				data.remove(0);
			}
			
			count++;
		
			}
			
			catch(Exception NoSuchElementException) {
				System.out.println("Regression done");
				break;
			}
		}

//	}

	}

	public static Double prediction(double[] coef, double num) {
		num+=1;
		Double ans;
		ans = (coef[0] + coef[1] * (Math.pow(num, 1)) + coef[2] * (Math.pow(num, 2)) + coef[3] * (Math.pow(num, 3))
				+ coef[4] * (Math.pow(num, 4)) + coef[5] * (Math.pow(num, 5))) + coef[6] * (Math.pow(num, 6));
		return ans;
	}
	String convertEpochTime(long time) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ssyyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		return sdf.format(time);
	}
}
