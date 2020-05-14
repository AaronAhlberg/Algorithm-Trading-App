
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Runner {
	int currentInterval = 0;

	public static void main(String[] args) throws IOException, ParseException, InterruptedException {
		Runner one = new Runner();
		 one.pullData("chrs",13, 150);
		//one.Trader("vxx", 500, "117724921", 7800);
		//one.performanceEval("btcusd",7800);	
//		sendOrder test = new sendOrder();
//		test.buyMarketOrder(100, "gnc");
//		TimeUnit.SECONDS.sleep(60);
//		test.sellMarketOrder(100, "gnc");
		
//		 File file = new File("C://Users/Aaron/Desktop/Master/vxx/AWPtest.txt");
//		 Writer write = new Writer();
//		 write.createFile("C://Users/Aaron/Desktop/Master/vxx/AWP.txt");
//		 write.setFile("C://Users/Aaron/Desktop/Master/vxx/AWP.txt");
//		 Scanner scan = new Scanner(file);
//		 scan.useDelimiter("");
//		 while(scan.hasNext()) {
//		 write.write(scan.next().replace("\t", "")+",");
//		 }
		 
	}

	void pullData(String ticker, int interval, int browserDelay)
			throws IOException, ParseException, InterruptedException {
		Main scraper = new Main();
		int count = 0;
		while (count < interval) {
			count++;
			currentInterval++;
			scraper.loadWindow("", "", ticker);// username and password for account
			scraper.recordBookViewer("C://Users/Aaron/Desktop/Master/" + ticker + "/AWP.txt", ticker, browserDelay);
		}

	}

	void Trader(String ticker, int shares, String ID, int simulationSize) throws IOException, InterruptedException {
		String filePath = "C://Users/Aaron/Desktop/Master/" + ticker + "/tradeValues.txt";
		long startTime = System.currentTimeMillis();
		long elapsedMinutes = 0;
		prediction one = new prediction();
		Writer writer = new Writer();
		File file = new File(filePath);
		writer.setFile(filePath);
		writer.newLine();// read values to
		// trade buy
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
		scan.useDelimiter(" - ");
		String numBuy = scan.next();
		String numSell = scan.next();
		String buySignal = scan.next();
		String sellSignal = scan.next();
		String signalTriggered = scan.next();
		String time = scan.next();
		String buy = scan.next();
		String sell = scan.next();
		String timeTrigger = scan.next();
		String SHORT = scan.next();
		String text = "";

		while (elapsedMinutes < (long) 390) {

			ArrayList<String> data = one.poll("C://Users/Aaron/Desktop/Master/" + ticker + "/AWP.txt", simulationSize); 
			if (data != null) {
				// adds criteria to trade with and helps keep a record of trader's status and
				// values to get passed along

				data.add(numBuy);
				data.add(numSell);
				data.add(buySignal);
				data.add(sellSignal);
				data.add(signalTriggered);
				data.add(time);
				data.add(buy);
				data.add(sell);
				data.add(timeTrigger);
				data.add(SHORT);

				algo temp = new algo();
				ArrayList<String> output = temp.trade(data, 
						"C://Users/Aaron/Desktop/Master/" + ticker + "/log.txt", 
						ticker, // symbol
						shares, // shares to be traded
						ID // systemID
				);
				numBuy = output.get(0);
				numSell = output.get(1);
				buySignal = output.get(2);
				sellSignal = output.get(3);
				signalTriggered = output.get(4);
				time = output.get(5);
				buy = output.get(6);
				sell = output.get(7);
				timeTrigger = output.get(8);
				SHORT = output.get(9);

			}

			text = numBuy + " - " + numSell + " - " + buySignal + " - " + sellSignal + " - " + signalTriggered + " - "
					+ time + " - " + buy + " - " + sell + " - " + timeTrigger + " - " + SHORT;
			TimeUnit.MILLISECONDS.sleep(5000);
			long elapsedTime = System.currentTimeMillis() - startTime;
			long elapsedSeconds = elapsedTime / 1000;
			elapsedMinutes = elapsedSeconds / 60;

		}
		// create new file and write values to the file
		writer.createFile(filePath);
		writer.write(text);
	}

	void cleanData(String file) throws IOException {
		prediction getData = new prediction();
		ArrayList<String> data = getData.getData(file);
		System.out.println(data.size());
		if (data.size() >= 50000) {
			Writer write = new Writer();
			write.createFile(file);
			write.setFile(file);
			for (int i = 0; i < data.size(); i++) {
				if (i >= data.size() - 50000 - 1) {
					write.write(data.get(i) + "\n");
				}
			}
		}

	}

	void performanceEval(String ticker, int simulationSize) throws IOException {
		Apache apache = new Apache();
		algo algoTest = new algo();

	//	apache.start("C://Users/Aaron/Desktop/Master/" + ticker + "/AWP.txt", // INPUT FILE TO
	//			"C://Users/Aaron/Desktop/Master/" + ticker + "/RegressionDeviation.txt", simulationSize);// OUTPUT FILE
	algoTest.startBuySim("C://Users/Aaron/Desktop/Master/" + ticker + "/RegressionDeviation.txt", // input // file
				"C://Users/Aaron/Desktop/Master/" + ticker + "/PerformanceResults.txt");
		algoTest.startShortSim("C://Users/Aaron/Desktop/Master/" + ticker + "/RegressionDeviation.txt", // input // file
				"C://Users/Aaron/Desktop/Master/" + ticker + "/PerformanceResults.txt");
		gainPerTrade("C://Users/Aaron/Desktop/Master/" + ticker + "/PerformanceResults.txt", ticker);
//		bestPriceAwpDeviation("C://Users/Aaron/Desktop/Master/" + ticker + "/AWP.txt", ticker);
		//cleanData("C://Users/Aaron/Desktop/Master/" + ticker + "/AWP.txt");
	}

	void gainPerTrade(String filePath, String ticker) throws IOException {
		// pull data from the performance file and creates new file
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		scan.useDelimiter(",");
		String tradeStrategy = "";
		Double performance = 0.0;
		Double trades = 0.0;
		Double gainPerTrade = 0.0;
		double lowest=9999999.99;
		String bestStrategy="";
		Writer write = new Writer();
		write.createFile("C://Users/Aaron/Desktop/Master/" + ticker + "/gainPerTrade.txt");
		write.setFile("C://Users/Aaron/Desktop/Master/" + ticker + "/gainPerTrade.txt");
		// write the strategy, varience, and standard deviation to file
		Double count = 0.0;
		while (scan.hasNext()) {
			try {

				tradeStrategy = scan.next().replace("\n", "");
				performance = Double.parseDouble(scan.next().replace("\n", ""));
				trades = Double.parseDouble(scan.next().replace("\n", ""));
				if (performance != 0 && trades != 0)
					gainPerTrade = trades / performance;
				else
					gainPerTrade = 0.0;
				
				if(gainPerTrade<lowest&&gainPerTrade>0.0) {
					bestStrategy=tradeStrategy;
					lowest=gainPerTrade;
				}
				write.write(tradeStrategy + "," + gainPerTrade + ",\n");
			} catch (Exception e) {
				break;
			}		
		}
		 scan.close();
		 scan = new Scanner(bestStrategy);
		 scan.useDelimiter(" : ");
		 setTradeValues(Double.parseDouble(scan.next()),Double.parseDouble(scan.next().replace("-1","")),ticker);
		// close writer/scanner
		write.stop();
		scan.close();
	}

	void bestPriceAwpDeviation(String filePath, String ticker) throws IOException {
		// pull data from the performance file and creates new file, records varience.
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		scan.useDelimiter(",");
		String time = "";
		Double awp = 0.0;
		Double bestPrice = 0.0;
		Double ans = 0.0;
		Double alpha = 0.0;
		algo checkTime = new algo();
		Writer write = new Writer();
		write.createFile("C://Users/Aaron/Desktop/Master/" + ticker + "/bestPriceAwpDeviation.txt");
		write.setFile("C://Users/Aaron/Desktop/Master/" + ticker + "/bestPriceAwpDeviation.txt");
		// write the strategy, varience, and standard deviation to file
		Double count = 1.0;
		while (scan.hasNext()) {
			try {

				time = scan.next().replace("\n", "");
				if (checkTime.checkTime(time)) {
					awp = Double.parseDouble(scan.next().replace("\n", ""));
					bestPrice = Double.parseDouble(scan.next().replace("\n", ""));
					if ((awp / bestPrice) == 1)
						ans = 0.0;
					else {
						ans = ((awp / bestPrice) - 1) * 100;
					}
					alpha += ans;
					write.write(time + "," + ans + ",\n");
					count++;

				} else {
					awp = Double.parseDouble(scan.next().replace("\n", ""));
					bestPrice = Double.parseDouble(scan.next().replace("\n", ""));
				}
			} catch (Exception e) {
				break;
			}
		}
		// close writer
		write.stop();
		System.out.println("Average deviation from VWAP:" + alpha / count);

	}

	void setTradeValues(double buyValue,double sellValue, String ticker) throws IOException {
		// get trade values
		ArrayList<String> tradeValues = new ArrayList<String>();
		String filePath="C://Users/Aaron/Desktop/Master/" + ticker + "/tradeValues.txt";
		File file = new File(filePath);
		Scanner scan = new Scanner(file);
		scan.useDelimiter(" - ");
		while (scan.hasNext()) {
			tradeValues.add(scan.next());
		}
		
			// change the trade values to buy position
			tradeValues.set(6, ""+buyValue);
		
			// set tradeValues for sell position
			tradeValues.set(7, ""+sellValue);
			
		// write back to file
		Writer write = new Writer();
		write.createFile(filePath);
		write.setFile(filePath);
		for(int i=0;i<tradeValues.size();i++) {
			write.write(tradeValues.get(i)+" - ");
		}
		scan.close();
	}

	String getTime() {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		return df.format(dateobj);
	}

	int getInterval() {
		return currentInterval;
	}
}
