import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class algo {
	void startBuySim(String inputFile, String outputFile) throws IOException {
		// tests different performances on buy and sell values along with time
		// to determine most profitable and outputs the performance of
		// every test to txt file denoted PerformanceTest
		File log = new File(outputFile);
		@SuppressWarnings("resource")
		Writer output = new Writer();
		output.createFile(outputFile);
		output.setFile(outputFile);
		algo one = new algo();
		double[] data;
		double performance = 0.0;
		double biggest = -55555;
		double smallest = 55555;
		String greatestReturn = "";
		String worstReturn = "";

		for (double buy = -6.0; buy <= 0.0; buy += .1) {
			for (double sell = 0; sell <= 6.0; sell += .1) {
				for (int time = 1; time <= 1; time += 1) {
					data = one.run(inputFile, buy, sell, time);
					performance = data[0];
					if (performance > biggest) {
						biggest = performance;
						greatestReturn = "" + buy + " : " + sell + "-" + time + " " + biggest;
					}
					if (performance < smallest) {
						smallest = performance;
						worstReturn = "" + buy + " : " + sell + "-" + time + " " + smallest;

					}
					System.out.println(+buy + "," + sell + "," + time + "," + performance + "," + data[1] + ",");

					output.write("" + buy + " : " + sell + "-" + time + "," + performance + "," + data[1] + ",\n");

				}
			}
		}
		output.stop();

		System.out.println("best return:" + greatestReturn);
		System.out.println("worst return:" + worstReturn);
	
	}

	// takes in buy and sell variable and than calculates profitibility of that
	// strategy
	double[] run(String inputFile, Double buyIndicatorLow, double sellIndicatorHigh, double timeTrigger)
			throws IOException {
		File file = new File(inputFile);
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
		scan.useDelimiter(",");

		boolean SIGNAL = false;
		boolean sell = false;
		boolean signalTriggered = false;
		int numBuy = 0;
		int numSell = 0;
		double counter = 0;
		double priceShort = 0.0;
		double priceBought = 0;
		double totalProfit = 0;

		double trades = 0.0;
		while (scan.hasNext()) {
			// scans in time, deviation, and NBBO
			String time = scan.next().replace("\n", "");
			if (scan.hasNextDouble()) {
				Double ans = Double.parseDouble(scan.next().replace("\n", ""));
				Double price = Double.parseDouble(scan.next().replace("\n", ""));

				counter++;
				// if so many ticks go gy check these conditions
				//if (checkTime(time)) {

					// sends buy signal buys at price that is calculated at the time of the spread
					// best bid and ask quote
					if (ans < buyIndicatorLow && !signalTriggered) {
						numBuy++;

						if (numBuy > timeTrigger) {
							SIGNAL = true;
							numBuy = 0;
						}
						if (SIGNAL) {
							if (priceShort != 0.0) {
								double gain = (1 - (priceShort / price)) * 100;
								trades += 1.0;
								totalProfit += gain*=1.0;
							}
							// send buy signal
							SIGNAL = false;
							signalTriggered = true;
							priceBought = price;
						}

					}

					// sends sell signal and calculates running profit
					if (ans > sellIndicatorHigh && signalTriggered) {
						numSell++;

						if (numSell > 0) {
							numSell = 0;
							sell = true;
						}

						if (sell) {
							double profit = (1 - (priceBought / price)) * 100;
							trades += 1.0;
							// send sell signal
							sell = false;
							signalTriggered = false;
							totalProfit += profit;
							priceShort = price;
						}

					}
				}

		//	} // end if timecheck

			else
				break;
		}
		// returns the total profit
		double info[] = { totalProfit, trades, (totalProfit / trades) };
		scan.close();
		return info;
	}

	void startShortSim(String inputFile, String outputFile) throws IOException {
		// tests different performances on buy and sell values along with time
		// to determine most profitable and outputs the performance of
		// every test to txt file denoted PerformanceTest
		File log = new File(outputFile);
		@SuppressWarnings("resource")
		Writer output = new Writer();
		output.setFile(outputFile);
		algo one = new algo();
		double[] data;
		double performance = 0.0;
		double biggest = -55555;
		double smallest = 55555;
		String greatestReturn = "";
		String worstReturn = "";

		for (double buy = 0.0; buy <= 6.0; buy += .1) {
			for (double sell = -6.0; sell <= 0.0; sell += .1) {
				for (int time = 1; time <= 1; time += 1) {
					data = one.runShortSim(inputFile, buy, sell, time);
					performance = data[0];
					if (performance > biggest) {
						biggest = performance;
						greatestReturn = "" + buy + " : " + sell + "-" + time + " " + biggest;
					}
					if (performance < smallest) {
						smallest = performance;
						worstReturn = "" + buy + " : " + sell + "-" + time + " " + smallest;

					}
					System.out.println(+buy + "," + sell + "," + time + "," + performance + "," + data[1] + ",");

					output.write("" + buy + " : " + sell + "-" + time + "," + performance + "," + data[1] + ",\n");

				}
			}
		}
		output.stop();

		System.out.println("best return:" + greatestReturn);
		System.out.println("worst return:" + worstReturn);
	
	}

	// takes in buy and sell variable and than calculates profitibility of that
	// strategy
	double[] runShortSim(String inputFile, Double buyIndicatorLow, double sellIndicatorHigh, double timeTrigger)
			throws IOException {
		File file = new File(inputFile);
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(file);
		scan.useDelimiter(",");

		boolean SIGNAL = false;
		boolean sell = false;
		boolean signalTriggered = false;
		int numBuy = 0;
		int numSell = 0;
		double counter = 0;
		double priceShort = 0.0;
		double priceBought = 0;
		double totalProfit = 0;

		double trades = 0.0;
		while (scan.hasNext()) {
			// scans in time, deviation, and NBBO
			String time = scan.next().replace("\n", "");
			if (scan.hasNextDouble()) {
				Double ans = Double.parseDouble(scan.next().replace("\n", ""));
				Double price = Double.parseDouble(scan.next().replace("\n", ""));

				counter++;
				// if so many ticks go gy check these conditions
				//if (checkTime(time)) {

					// sends buy signal buys at price that is calculated at the time of the spread
					// best bid and ask quote
					if (ans < sellIndicatorHigh  && !signalTriggered) {
						numBuy++;

						if (numBuy > timeTrigger) {
							SIGNAL = true;
							numBuy = 0;
						}
						if (SIGNAL) {
							if (priceShort != 0.0) {
								double gain = (1 - (priceShort / price)) * 100;
								//gain*=-1.0;
								trades += 1.0;
								totalProfit += gain;
							}
							// send buy signal
							SIGNAL = false;
							signalTriggered = true;
							priceBought = price;
						}

					}

					// sends sell signal and calculates running profit
					if (ans > buyIndicatorLow && signalTriggered) {
						numSell++;

						if (numSell > 0) {
							numSell = 0;
							sell = true;
						}

						if (sell) {
							double profit = (1 - (priceBought / price)) * 100;
							trades += 1.0;
							profit *= -1.0;
							// send sell signal
							sell = false;
							signalTriggered = false;
							totalProfit += profit;
							priceShort = price;
						}

					}
				}

		//	} // end if timecheck

			else
				break;
		}
		// returns the total profit
		double info[] = { totalProfit, trades, (totalProfit / trades) };
		scan.close();
		return info;
	}

	ArrayList<String> trade(ArrayList<String> data, String outputfile, String ticker, int Shares, String systemID)
			throws IOException, InterruptedException {
		String time = data.get(0);
		Writer write = new Writer();
		write.setFile(outputfile);
		double weight = Double.parseDouble(data.get(1));
		double price = Double.parseDouble(data.get(2));
		round(price, 2);
		int numBuy = Integer.parseInt(data.get(3));
		int numSell = Integer.parseInt(data.get(4));
		boolean buySignal = Boolean.parseBoolean(data.get(5));
		boolean sellSignal = Boolean.parseBoolean(data.get(6));
		boolean signalTriggered = Boolean.parseBoolean(data.get(7));
		String time2 = (data.get(8));
		double buyIndicator = Double.parseDouble(data.get(9));
		double sellIndicator = Double.parseDouble(data.get(10));
		double timeTrigger = Double.parseDouble(data.get(11));
		boolean inSHORT = Boolean.parseBoolean(data.get(12));
		sendOrder sender = new sendOrder();
		sender.setID(systemID);
		System.out.print(weight+" "+time+"\n");
		if (checkTime(time)) {
			// sends buy signal buys at price that is calculated at the time of the spread
			// best bid and ask quote
			if (weight < buyIndicator && !signalTriggered) {

				numBuy++;

				if (numBuy > timeTrigger) {
					buySignal = true;
					numBuy = 0;
				}
				if (buySignal) {
					buySignal = false;
					signalTriggered = true;

					// buy to cover if in short position
					if (inSHORT) {
						sender.sellMarketOrder(Shares, ticker);
						inSHORT = false;
						Toolkit.getDefaultToolkit().beep();
						write.write("Sell: " + time + " price:" + price);
						System.out.println("Sell: " + time + " price:" + price+"\n");
						write.newLine();
					}
					// send buy signal
					write.write("Short: " + time + " price:" + price);
					System.out.println("Short: " + time + " price:" + price+"\n");
					write.newLine();
					sender.ShortMarketOrder(Shares, ticker);
					Toolkit.getDefaultToolkit().beep();
				}
			}
			// sends sell signal
			if (weight > sellIndicator && signalTriggered) {

				numSell++;

				if (numSell > 1) {
					sellSignal = true;
					numSell = 0;
				}

				if (sellSignal) {
					// send sell signal
					sellSignal = false;
					signalTriggered = false;
					sender.buyToCoverMarketOrder(Shares, ticker);
					write.write("BuyTocCover: " + time + " price:" + price);
					System.out.println("BuyToCover: " + time + " price:" + price+"\n");
					Toolkit.getDefaultToolkit().beep();
					write.newLine();
					// send short order
					if (!inSHORT) {

						inSHORT = true;
						write.write("Buy: " + time + " price:" + price);
						System.out.println("Buy: " + time + " price:" + price+"\n");
						write.newLine();
						sender.buyMarketOrder(Shares, ticker);
						Toolkit.getDefaultToolkit().beep();
					}
				}
			}
		}
		// returns data
		ArrayList<String> info = new ArrayList<String>();
		info.add("" + numBuy);
		info.add("" + numSell);
		info.add("" + buySignal);
		info.add("" + sellSignal);
		info.add("" + signalTriggered);
		info.add("" + time);
		info.add("" + buyIndicator);
		info.add("" + sellIndicator);
		info.add("" + timeTrigger);
		info.add("" + inSHORT);
		// close writer
		write.stop();
		return info;
	}

	// checks the time, split it up and make sure program only checks between
	// certain times
	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	boolean checkTime(String timeDate) {
		int time = 0;
		if (timeDate != "")
			time = (int) Double.parseDouble(timeDate.split("/")[0].replace(":", ""));

		if (time < 13000000 && time > 6300000) {
			return true;
		} else
			return false;
	}
}
