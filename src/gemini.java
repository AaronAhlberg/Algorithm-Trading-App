import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class gemini {
	WebDriver driver;
	static sendData con = new sendData();
	int count=0;

	public static void main(String[] args) throws PropertyVetoException {
		gemini test = new gemini();
		test.connect("btcusd");
		con.setDataSource();
			while(true){
				test.poll("btcusd", 5000);				
			}
		
	
}
		
private boolean connect(String ticker) {
	try {
		//set options and properties of chromdriver
		System.setProperty("webdriver.chrome.driver", "C://Users/Aaron/drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1600,1000");
		driver = new ChromeDriver(options);
		//go to url
		driver.navigate().to("https://api.gemini.com/v1/pubticker/"+ticker);
		WebDriverWait wait = new WebDriverWait(driver,10);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/pre")));
	
		return true;
	}
	catch(Exception e) {
		e.printStackTrace();
		System.out.println("COULD NOT CONNECT");
		driver.quit();
		return false;
	}
	
	
}
private boolean poll(String ticker,int delay) {
	try {
	
	WebDriverWait wait = new WebDriverWait(driver,10);
	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/pre")));
	 WebElement data = driver.findElement(By.xpath("/html/body/pre"));
	 String line = data.getText();
	Scanner scan = new Scanner(line);
	scan.useDelimiter("\"");
	scan.next();
	scan.next();
	scan.next();
	String bid= scan.next();
	scan.next();
	scan.next();
	scan.next();
	String ask= scan.next();
	scan.next();
	scan.next();
	scan.next();
	scan.next();
	scan.next();
	scan.next();
	scan.next();
	scan.next();
	scan.next();
	scan.next();
	scan.next();
	scan.next();
	 String timeStamp= scan.next();
	scan.next();
	scan.next();
	String lastTrade= scan.next();
	//get current time
	 DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
     Calendar calobj = Calendar.getInstance();
    String currentTime= df.format(calobj.getTime());
    
   
	 System.out.println(bid+","+ask+","+lastTrade+","+currentTime+",");
	 con.updateTable(ticker, currentTime, bid, ask, lastTrade);
	 Thread.sleep(delay);
	 count++;
	 scan.close();
	 return true;
	
	}
	catch(Exception e) {
		System.out.println("ERROR WHILE POLLING");
		 e.printStackTrace();
		 driver.quit();
		 return false;
		
	}

	
}



}
