
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class sendOrder {
	String systemID = "117308152";

	public static void main(String[] args) {

	}

	void setID(String id) {
		systemID = id;
	}





	void ShortMarketOrder(int numOfShares, String ticker) throws InterruptedException, IOException {
		String buy = "http://www.collective2.com/cgi-perl/signal.mpl?" + "cmd=signal&" + "systemid=" + systemID + "&"
				+ "pw=MorganB205&" + "action=SSHORT" + "&quant=" + numOfShares + "&instrument=stock&" + "symbol="
				+ ticker + "&" + "duration=DAY";
		System.setProperty("webdriver.chrome.driver", "C://Users/Aaron/drivers/chromedriver.exe");
		// Navigates to the specific URL
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(buy);
		// wait for validation text
		TimeUnit.SECONDS.sleep(30);
		driver.quit();

		// writes signal to file
		Writer write = new Writer();
		write.setFile("C://Users/Aaron/Desktop/BuySignals");
		write.write(buy);
		write.newLine();
		write.stop();

	}

	void buyToCoverMarketOrder(int numOfShares, String ticker) throws InterruptedException, IOException {
		String buy = "http://www.collective2.com/cgi-perl/signal.mpl?" + "cmd=signal&" + "systemid=" + systemID + "&"
				+ "pw=MorganB205&" + "action=BTC" + "&quant=" + numOfShares + "&instrument=stock&" + "symbol=" + ticker
				+ "&" + "duration=DAY";
		System.setProperty("webdriver.chrome.driver", "C://Users/Aaron/drivers/chromedriver.exe");
		// Navigates to the specific URL
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(buy);
		// wait for validation text
		TimeUnit.SECONDS.sleep(30);
		driver.quit();
		writeSellSignal(buy);

	}

	void buyMarketOrder(int numOfShares, String ticker) throws InterruptedException, IOException {
		String buy = "http://www.collective2.com/cgi-perl/signal.mpl?" + "cmd=signal&" + "systemid=" + systemID + "&"
				+ "pw=MorganB205&" + "action=BTO" + "&quant=" + numOfShares + "&instrument=stock&" + "symbol=" + ticker
				+ "&" + "duration=DAY";
		System.setProperty("webdriver.chrome.driver", "C://Users/Aaron/drivers/chromedriver.exe");
		// Navigates to the specific URL
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(buy);
		// wait for validation text
		TimeUnit.SECONDS.sleep(30);
		driver.quit();
		// writes signal to file
		Writer write = new Writer();
		write.setFile("C://Users/Aaron/Desktop/BuySignals");
		write.write(buy);
		write.newLine();
		write.stop();

	}

	void sellMarketOrder(int numOfShares, String ticker) throws InterruptedException, IOException {
		String sell = "http://www.collective2.com/cgi-perl/signal.mpl?" + "cmd=signal&" + "systemid=" + systemID + "&"
				+ "pw=MorganB205&" + "action=STC" + "&quant=" + numOfShares + "&instrument=stock&" + "symbol=" + ticker
				+ "&" + "duration=DAY";
		System.setProperty("webdriver.chrome.driver", "C://Users/Aaron/drivers/chromedriver.exe");
		// Navigates to the specific URL
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(sell);
		TimeUnit.SECONDS.sleep(30);
		driver.quit();
		writeSellSignal(sell);
	}

	

	void writeSellSignal(String s) throws IOException {
		Writer write = new Writer();
		write.setFile("C://Users/Aaron/Desktop/SellSignals");
		write.write(s);
		write.newLine();
		write.stop();
	}
	void writeBuySignal(String b) throws IOException {
		Writer write = new Writer();
		write.setFile("C://Users/Aaron/Desktop/BuySignals");
		write.write(b);
		write.newLine();
		write.stop();
	}
}
