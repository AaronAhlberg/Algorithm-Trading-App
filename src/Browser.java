import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Browser {

	WebDriver driver;
	String homeHandle;
	String bidLocation, askLocation, bidTotalLocation, askTotalLocation, askSharesLocation, bidSharesLocation;


	public static void main(String[] args) {

	}


	void create(String ticker) {
		System.setProperty("webdriver.chrome.driver", "C://Users/Aaron/drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.logfile", "C://Users/Aaron/Desktop/"+ticker+".log");
		// System.setProperty("webdriver.chrome.verboseLogging", "true");

		// Navigates to the specific URL
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--window-size=1600,1000");
		options.addArguments("--disable-gpu");
		// Disable extensions and hide infobars
		options.addArguments("--disable-extensions");
		options.addArguments("disable-infobars");

		Map<String, Object> prefs = new HashMap<>();

		// Enable Flash
		prefs.put("profile.default_content_setting_values.plugins", 1);
		prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
		prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);

		// Hide save credentials prompt
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);

		// Add options and launch
		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();

		driver.navigate().to("https://data.nasdaq.com/BookViewer.aspx");

	}

	void login(String usrname, String pswd) {
		// logs into the nasdaq site
		WebElement username = driver.findElement(By.id("txtUserName"));
		WebElement password = driver.findElement(By.id("txtPassword"));
		WebElement login = driver.findElement(By.id("btnLogin"));
		username.sendKeys(usrname);
		password.sendKeys(pswd);
		login.click();
		homeHandle = driver.getWindowHandle();
	}

	void launchBookviewer(String mpid) {
		// finds button and clicks it
		WebElement popup = driver.findElement(By.id("PageContent_btnLaunchBv3"));
		popup.click();
		// waits until window loads

		// gets and changes the Drivers handle (Switches to the newly created window
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		WebDriverWait wait = new WebDriverWait(driver, 10);
		// waits 10 seconds and if search box can't be found, throws error
		// else enters mpid symbol and clicks go
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mpid-input")));
		WebElement search = driver.findElement(By.id("mpid-input"));
		search.clear();

		search.sendKeys(mpid);
		WebElement btn = driver.findElement(By.xpath("//*[@id='mpid-area']/div/button[1]"));
		btn.click();
		WebElement pause = driver.findElement(By.xpath("//*[@id='mpid-area']/div/button[2]/div"));
		pause.click();
		// Makes sure column select loads
	    wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='header']/button[3]")));

	}

	void ColumnSelect() throws InterruptedException {
	
		WebElement btn = driver.findElement(By.xpath("//*[@id='header']/button[3]"));

		btn.click();
		String prevHandle = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='column-selections']/ul/li[14]/input")));
		wait = new WebDriverWait(driver, 5);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='column-selections']/ul/li[14]/span")));
		ArrayList<WebElement> buttons = new ArrayList<WebElement>();
		for (int count = 1; count <= 14; count++) {
			buttons.add(driver.findElement(By.xpath("//*[@id='column-selections']/ul/li[" + (count) + "]/input")));
		}

		for (int count = 0; count < 14; count++) {
			WebElement Btn = buttons.get(count);
			WebElement text = driver
					.findElement(By.xpath("//*[@id='column-selections']/ul/li[" + (count + 1) + "]/span"));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			// choose who gets selected
			String category = text.getText();

			if (category.equals("BID")) {
				if (!Btn.isSelected())
					executor.executeScript("arguments[0].click();", Btn);
				bidLocation = "" + (count + 1);
			} else if (category.equals("Shares (buy)")) {
				if (!Btn.isSelected())
					executor.executeScript("arguments[0].click();", Btn);
				bidSharesLocation = "" + (count + 1);
			} else if (category.equals("Total (buy)")) {
				if (!Btn.isSelected())
					executor.executeScript("arguments[0].click();", Btn);
				bidTotalLocation = "" + (count + 1);
			} else if (category.equals("ASK")) {
				if (!Btn.isSelected())
					executor.executeScript("arguments[0].click();", Btn);
				askLocation = "" + (count - 6);
			} else if (category.equals("Shares (sell)")) {
				if (!Btn.isSelected())
					executor.executeScript("arguments[0].click();", Btn);
				askSharesLocation = "" + (count - 6);
			} else if (category.equals("Total (sell)")) {
				if (!Btn.isSelected())
					executor.executeScript("arguments[0].click();", Btn);
				askTotalLocation = "" + (count - 6);
			}

			else {
				if (Btn.isSelected()) {
					executor.executeScript("arguments[0].click();", Btn);

				}
			}
		}
		driver.findElement(By.xpath("//*[@id='column-selections-footer']/div/span")).click();// get apply button
		// click apply
		btn.click();

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='ng-app']/body/div[3]/div/div/div[3]/button")));
		driver.findElement(By.xpath("//*[@id='ng-app']/body/div[3]/div/div/div[3]/button")).click();
		driver.switchTo().window(prevHandle);
		TimeUnit.MILLISECONDS.sleep(200);
	}

	ArrayList<String> bidPrice() throws ParseException, InterruptedException {
		
		ArrayList<String> price = new ArrayList<String>();
		try {
		for (int count = 0; count < 30; count++) {
			
				price.add(driver.findElement(By.xpath(
						"//*[@id='main-container']/table[1]/tbody[1]/tr[" + (count + 1) + "]/td[" + bidLocation + "]"))
						.getText());
				//*[@id="main-container"]/table[1]/tbody[1]/tr[1]/td[7]
		}
		return price;
		}
		catch(NullPointerException e) {
			System.out.println("Null Pointer Exception thrown");
					return price;
		}
		catch (NoSuchElementException e) {
			if(price.isEmpty()) {
				System.out.println("No Such Element arraylist empty Exception thrown");
				return null;
			}
			else {
			System.out.println("Null Pointer Exception thrown");
			return price;
			
			}
		}
		catch (Exception e) {
			System.out.println("ERROR***************************");
			return null;
		}
	}

	ArrayList<String> askPrice() throws ParseException, InterruptedException {

		ArrayList<String> price = new ArrayList<String>();
		try {
		for (int count = 0; count < 30; count++) {
				price.add(driver
						.findElement(By
								.xpath("//*[@id='sell-grid']/tbody[1]/tr[" + (count + 1) + "]/td[" + askLocation + "]"))
						.getText());
		}

		return price;
		}
		catch(NullPointerException e) {

					return price;
		}
		catch (NoSuchElementException e) {
			if(price.isEmpty())
				return null;
				else 
			return price;
		}
	catch (Exception e) {

		return null;
	}
	}

	ArrayList<String> bidShare() throws ParseException, InterruptedException {
		
		ArrayList<String> share = new ArrayList<String>();
		try {
		for (int count = 0; count < 30; count++) {
				share.add(driver.findElement(By.xpath("//*[@id='main-container']/table[1]/tbody[1]/tr[" + (count + 1)
						+ "]/td[" + bidSharesLocation + "]")).getText());
		}
		return share;// *[@id="main-container"]/table[1]/tbody[1]/tr[1]/td[6]
	}
		catch(NullPointerException e) {
	
					return share;
		}
		catch (NoSuchElementException e) {
			if(share.isEmpty())
				return null;
				else 
					return share;
		}
		catch (Exception e) {

			return null;
		}
	}

	ArrayList<String> askShare() throws ParseException, InterruptedException {
		ArrayList<String> share = new ArrayList<String>();
		try {
		for (int count = 0; count < 30; count++) {
				share.add(driver
						.findElement(By.xpath(
								"//*[@id='sell-grid']/tbody[1]/tr[" + (count + 1) + "]/td[" + askSharesLocation + "]"))
						.getText());
		}
		return share;
		}
		catch(NullPointerException e) {
					return share;
		}
		catch (NoSuchElementException e) {
			if(share.isEmpty())
				return null;
				else 
					return share;
		
		}
		catch (Exception e) {

			return null;
		}
	}


	String getTime() {
		String text = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		text = sdf.format(cal.getTime());
		return text;
	}

	String getDate() {
		String text = "";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
		text = sdf.format(cal.getTime());
		return text;
	}

	boolean refresh() throws InterruptedException {
		try {
			
			WebElement btn = driver.findElement(By.xpath("//*[@id='mpid-area']/div/button[2]/div"));
			btn.click();
			TimeUnit.MILLISECONDS.sleep(1000);
			btn.click();
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main-container']/table[1]/tbody[1]/tr[1]/td[" + bidLocation + "]")));
			TimeUnit.MILLISECONDS.sleep(500);
		
			return false;
		} catch (org.openqa.selenium.WebDriverException e) {
			return true;
		}
	}

	void closeBookviewer() {
		//driver.switchTo().window(homeHandle);
		driver.quit();
	}

}
