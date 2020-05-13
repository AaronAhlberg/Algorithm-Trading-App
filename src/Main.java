import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
	Browser browser = new Browser();
	Writer writer = new Writer();
	ArrayList<String> bid = new ArrayList<String>();
	ArrayList<String> ask = new ArrayList<String>();
	ArrayList<String> bidShares = new ArrayList<String>();
	ArrayList<String> askShares = new ArrayList<String>();
	ArrayList<String> bidTotal = new ArrayList<String>();
	ArrayList<String> askTotal = new ArrayList<String>();
	String usrname;
	String psword;

public static void main(String[] args) throws IOException, ParseException,
			InterruptedException {
	}
 void loadWindow(String username,String password,String ticker){
	 	usrname=username;
	 	psword=password;
		browser.create(ticker);
		browser.login(username, password);
		
		}
	
void recordBookViewer(String regressionFile,String ticker,int delay) throws IOException, ParseException, InterruptedException{
		browser.launchBookviewer(ticker);
		System.out.println("bookViewer");
		browser.ColumnSelect();
		writer.setFile(regressionFile);
		long startTime = System.currentTimeMillis();
		long elapsedMinutes =0;
		
		
		
while (elapsedMinutes<=(long)30) {
			long time = System.currentTimeMillis();
			double bidTotalShares=0.0;
			double askTotalShares=0.0;
			ArrayList<Double> PriceBid = new ArrayList<Double>();
			ArrayList<Double> SharesBid = new ArrayList<Double>();
			ArrayList<Double> PriceAsk = new ArrayList<Double>();
			ArrayList<Double> SharesAsk = new ArrayList<Double>();
			browser.refresh();
			bid = browser.bidPrice();
			ask = browser.askPrice();
			bidShares = browser.bidShare();
			askShares = browser.askShare();
		//check if date is in range
	
			if(checkData()&&bid.size()!=0&&ask.size()!=0) {
				//gets bid weight*************************************
				for (int i = 0; i < bid.size(); i++) {
					//format the string numbers into Double and handle cases
					double price = Double.parseDouble(bid.get(i).replace("$", "").replace(",", ""));
					double shares= Double.parseDouble(bidShares.get(i).replace(",", ""));
					PriceBid.add(price);//adds all prices for that time
					SharesBid.add(shares);// adds all shares for that time
					bidTotalShares += shares;
				}
				//calculates bid weight*********************************
				double weightBid=0;
				for (int i = 0; i < PriceBid.size(); i++) {
					weightBid+=(PriceBid.get(i)*(SharesBid.get(i)/bidTotalShares));
				}
				//gets ask weight***********************************
				for (int i = 0; i < ask.size(); i++) {
					//format the string numbers into Double and handle cases
					double price = Double.parseDouble(ask.get(i).replace("$", "").replace(",", ""));
					double shares= Double.parseDouble(askShares.get(i).replace(",", ""));
					PriceAsk.add(price);//adds all prices for that time
					SharesAsk.add(shares);// adds all shares for that time
					askTotalShares += shares;
				}
				//calculate ask weight********************
				double weightAsk=0;
				for (int i = 0; i < PriceAsk.size(); i++) {
					weightAsk+=(PriceAsk.get(i)*(SharesAsk.get(i)/askTotalShares));
				}
				//calculates best price and AWP
				if(PriceAsk.size()!=0) {
				double bestPrice= (PriceAsk.get(0));
				Double AWP=(weightAsk+weightBid)/2;
				//only writes id quote is within certain range
				if(AWP<80&&AWP>1)
				writer.write(browser.getTime()+browser.getDate()+","+AWP+","+bestPrice+",\n");
			System.out.println(System.currentTimeMillis() - time);
				}
			}
		
			//Restarts the browser if data returns null
			 if(!checkData()) {
				browser.closeBookviewer();
			    browser = new Browser();
				browser.create(ticker);
				browser.login(usrname, psword);
				browser.launchBookviewer(ticker);
				browser.ColumnSelect();
				TimeUnit.SECONDS.sleep(delay);
	
				
			}
			long elapsedTime = System.currentTimeMillis() - startTime;
			long elapsedSeconds = elapsedTime / 1000;
			elapsedMinutes = elapsedSeconds / 60;
			//close bookviewer
		}
		browser.closeBookviewer();
		writer.stop();
	}
	boolean checkData() {
		if ((bid==null||ask==null||bidShares==null||askShares==null)) {
			return false;
		}
		else return true;
	}
}

