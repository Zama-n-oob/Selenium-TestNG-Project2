package rahulshettyacademy;

import java.util.List;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ECommerceE2ETest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
	
	//String productName = "ZARA COAT 3";
	WebDriver driver = (WebDriver) new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.get("https://rahulshettyacademy.com/client");
	driver.findElement(By.id("userEmail")).sendKeys("ahmedzaman.069@gmail.com");
	driver.findElement(By.id("userPassword")).sendKeys("Pranta69*");
	driver.findElement(By.id("login")).click();
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
	
	//Iterating particular product over Products List using java stream method
	List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	
	WebElement prod = products.stream().filter(product->
	product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null);
	prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
	
	//Wait until toast message appears after a product add to cart
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
	//Wait until the loading animation is disappeared  
	wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
	driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
	
	//Validate if a product added to cart is present in cart module
	List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase("ZARA COAT 3"));
	Assert.assertTrue(match);
	driver.findElement(By.cssSelector(".totalRow button")).click();
	
	Actions a = new Actions(driver);
	a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")), "Bangladesh").build().perform();
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	
	driver.findElement(By.xpath("//button[contains(@class,'ta-item')]")).click();
	
	//JavascriptExecutor js = (JavascriptExecutor) driver;
	//js.executeScript("window.scrollBy(0,150)");
	//Thread.sleep(4000);

	driver.findElement(By.cssSelector(".btnn.action__submit")).click();
	
	String confirmText = driver.findElement(By.cssSelector("[class='hero-primary']")).getText();
	Assert.assertTrue(confirmText.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	driver.close();
	
	}
}
