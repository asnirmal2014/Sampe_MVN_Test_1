package com.nirmal.selentest;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.safari.SafariDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.opera.OperaDriver;         // Deprecated
//import io.github.bonigarcia.wdm.WebDriverManager;

public class IFrame_1
{
	static WebDriver driver = null;
	static WebElement element = null;

	public static void main(String[] args) throws InterruptedException, IOException
    {
		/*  OLD TECHNIQUE-1 */
	    /*
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
	
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
		driver = new FirefoxDriver();
	
		System.setProperty("webdriver.gecko.driver", "C:/jars_selenium/drivers/geckodriver.exe");
		driver = new FirefoxDriver();
	
		System.setProperty("webdriver.chrome.driver", "C:/jars_selenium/drivers/chromedriver.exe");
		driver = new ChromeDriver();
	
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
	    */
		
		/*  OLD TECHNIQUE-2 */
		/*
		WebDriverManager.chromedriver().setup();                      // 4.4.3
    	WebDriver driver = new ChromeDriver();
    	[OR]
    	WebDriver driver = WebDriverManager.chromedriver().create();  // 5.9.1
    	*/
				
		// [OR]
				
		/*
			ChromeOptions co = new ChromeOptions();
			co.setHeadless(true);
			WebDriver driver = WebDriverManager.chromedriver().capabilities(co).create();  // 5.1.1
		*/	
				
		/*
			ChromeOptions co = new ChromeOptions();
			co.setHeadless(false);
			WebDriver driver = WebDriverManager.chromedriver().capabilities(co).create();  // 5.1.1
		*/
		
		/*
			ChromeOptions options = new ChromeOptions();
			Map<String, Integer> prefs = new HashMap<String, Integer>();
			prefs.put("profile.default_content_setting_values.notifications", 0);  // 0-Ask(Default), 1-Allow, 2-Block
			options.setExperimentalOption("prefs", prefs);
			// options.addArguments("disable-notifications"); // To disable the notifications based permission popup
    		// options.addArguments("disable-geolocation");   // To disable the location based permission popup
    		// options.addArguments("disable-media-stream");  // To disable the microphone or camera based permission popup
			System.setProperty("webdriver.chrome.driver", "C:/jars_selenium/drivers/chromedriver.exe");
			driver = new ChromeDriver(options);
        */

		driver = new ChromeDriver();
		//driver = new FirefoxDriver();
		//driver = new EdgeDriver();
		//driver = new SafariDriver();
		//driver = new InternetExplorerDriver();
		//driver = new OperaDriver();            // Deprecated
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.manage().deleteAllCookies();
		
 	    driver.get("https://jqueryui.com/draggable/");
 	    System.out.println(driver.getTitle());
 	    driver.switchTo().frame(0);                 // Switch to the first iframe. The index of iframe starts with '0'.
 	    System.out.println(driver.findElement(By.xpath("//*[@id=\"draggable\"]/p")).getText());
 	    driver.switchTo().defaultContent();         // Switches the WebDriver's focus entirely out of all iframes and back to the main, top-level HTML document.
 	    System.out.println(driver.getTitle());
 	    
        Thread.sleep(3000);
        
		// driver.close();  // Close the current window, quitting the browser if it's the last window currently open.

		// driver.quit();   // Quits this driver, closing every associated window. Closes all the windows which are currently open.
		
        driver.quit();
	}
}

/*
NOTE:
-----
driver.findElement(By.xpath("//*[@id=\"fname\"]")).sendKeys("Selenium Automation");

The above line will throw an Exception.
//Exception in thread "main" org.openqa.selenium.NoSuchElementException: no such element: Unable to locate element: {"method":"xpath","selector":"//*[@id="fname"]"}
  Exception in thread "main" org.openqa.selenium.NoSuchElementException: no such element: Unable to locate element: {"method":"xpath","selector":"//*[@id="fname"]"}

This Exception occurs due to the xpath value which we copy.
Even though our xpath value is correct we get an Exception.
It shows that the compiler could not find the xpath i.e., driver.findElement(By.xpath("//*[@id=\"fname\"]")).sendKeys("Selenium Automation");
When the xpath which is present inside an iframe, then the compiler cannot locate the element.
We need to switch into the iframe, perform the action and switch back to the parent frame.

First we need to locate the iframe then 
we need to switch to the iframe and then 
we need to locate the web element and 
after locating the web element we need to perform the action and then
we need to switch back from the iframe.

In the source code of a web page use 'CTRL + F' and in the search box type '//iframe' and press 'ENTER' Key.
It will show the number of iframes present in that particular web page.
We can use the Up and Down arrows to find our desired iframe.
When we press the Up and Down arrow, It will highlight the element one by one. By which we can identify our desired iframe.
*/

/*
NOTE:
-----
// Switch to the first iframe. The index of iframe starts with '0'.
driver.switchTo().frame(0);

// Switches the WebDriver's focus exactly one level up to the immediate parent frame of the current iframe context.
// If the focus already at the top-level main webpage, the driver focus remains unchanged.
driver.switchTo().parentFrame();

// Switches the WebDriver's focus entirely out of all iframes and back to the main, top-level HTML document.
driver.switchTo().defaultContent();
*/

/*
Short implicit waits + targeted explicit waits = faster, predictable tests.
Short implicit waits + Long explicit waits = faster, predictable tests.  
*/

/*
We need to use 'driver.switchTo().defaultContent()' in the finally block.

You need `driver.switchTo().defaultContent()` in the `finally` block because 
each loop iteration must start from a known, consistent state i.e., the top-level document.
https://www.selenium.dev/documentation/webdriver/waits/

Why reset to `defaultContent()` every time?
- Selenium’s frame context is cumulative: Once you call `driver.switchTo().frame(i)`, 
  WebDriver stays inside that frame until you explicitly switch out. 
  If an exception occurs (e.g., element not found), the driver may still be inside that frame. 
  https://stackoverflow.com/questions/28139824/webdriver-org-openqa-selenium-nosuchelementexception-unable-to-locate-element
- Your loop uses `frame(i)` by index: 
  On the next iteration, if you don’t reset, `driver.switchTo().frame(i)` is interpreted relative to the current frame context, 
  not the main page. 
  That can cause:
  - Wrong iframe being selected.
  - 'NoSuchElementException' even when the element exists in another iframe.
  - Unpredictable behavior when there are many iframes (like your 24).

  The Selenium docs emphasize that waits and context must be controlled to avoid flaky tests. 
  https://www.selenium.dev/documentation/webdriver/waits/

- `finally` guarantees reset even on exception: 
   If `findElement` throws `NoSuchElementException`, 
   the `catch` handles it, but without `finally`, the driver would remain in the failed iframe. 
   The `finally` block ensures that whether the element is found or not, 
   you always return to the main document before the next `i`. 
   https://stackoverflow.com/questions/28139824/webdriver-org-openqa-selenium-nosuchelementexception-unable-to-locate-element

What happens if you remove it?
Without `defaultContent()`:
- Iteration 0: `frame(0)` → try to find element → not found → driver stays in `frame(0)`
- Iteration 1: `frame(1)` now means go to frame with index 1 which is inside frame 0, not frame 1 of the main page so this quickly breaks 
  the logic when there are multiple nested or many iframes.

Given your page has 24 iframes, this mistake would make the loop effectively search in completely wrong contexts after the first failure.

Better pattern (optional improvement):

For clarity and robustness, many teams prefer:
- Switch by index or name/ID once.
- Use explicit waits inside the frame.
- Always switch back to `defaultContent()` after work is done.

But the core reason for your `finally` block is simple: 
to guarantee that every iteration starts from the main document, so `frame(i)` always refers to the i-th iframe of the top-level page. 
https://www.selenium.dev/documentation/webdriver/waits/
*/
