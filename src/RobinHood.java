import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RobinHood {
	WebDriver driver;
	
	public static void main(String[] args) throws IOException, ParseException,
	InterruptedException, AWTException {
		RobinHood test= new RobinHood();
		test.logIn("", "");
		test.buyStock("bks", 100);
}
void logIn(String username,String pswd) {
	//set options and properties of chromdriver
	System.setProperty("webdriver.chrome.driver", "C://Users/Aaron/drivers/chromedriver.exe");
	ChromeOptions options = new ChromeOptions();
	//options.addArguments("--headless");
	options.addArguments("--disable-gpu");
	options.addArguments("--window-size=1600,1000");
	driver = new ChromeDriver(options);
	//go to url
	driver.navigate().to("https://robinhood.com/login");
	//get elements and send keys and submit signal
	WebElement email = driver.findElement(By.xpath("//*[@id='react_root']/div/div/div/div[2]/div/div/form/div/div/div[1]/label/div[2]/input"));
	WebElement password = driver.findElement(By.xpath("//*[@id='react_root']/div/div/div/div[2]/div/div/form/div/div/div[2]/label/div[2]/input"));
	WebElement signIn = driver.findElement(By.xpath("//*[@id='react_root']/div/div/div/div[2]/div/div/form/footer/div/button"));
	email.sendKeys(username);
	password.sendKeys(pswd);
	signIn.click();	
	
}
void buyStock(String ticker, int shares) throws AWTException {
	 WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='react_root']/div/main/div[2]/div/div[2]/div/div/div[1]/section[1]/header/h1/span/div/div[1]")));
		driver.navigate().to("https://robinhood.com/stocks/"+ticker.toUpperCase());
		  wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='react_root']/div/main/div[2]/div/div[2]/div/main/div[2]/div[2]/div/form/div[1]/div[1]/div[1]/label/div[2]/input")));
			WebElement sharesEntry= driver.findElement(By.xpath("//*[@id='react_root']/div/main/div[2]/div/div[2]/div/main/div[2]/div[2]/div/form/div[1]/div[1]/div[1]/label/div[2]/input"));
			WebElement reviewBtn= driver.findElement(By.xpath("//*[@id='react_root']/div/main/div[2]/div/div[2]/div/main/div[2]/div[2]/div/form/div[1]/div[2]/div[2]/button"));
			sharesEntry.sendKeys(""+shares);	
			reviewBtn.click();
}
void sellStock(String ticker, int shares) {
	 WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='react_root']/div/main/div[2]/div/div[2]/div/div/div[1]/section[1]/header/h1/span/div/div[1]")));
		driver.navigate().to("https://robinhood.com/stocks/BKS");
}


}
