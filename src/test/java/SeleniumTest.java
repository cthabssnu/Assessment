

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class SeleniumTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.labcorp.com/");
    }

    @Test(priority = 1)
    public void testJobSearchAndSelection() {
        WebElement careers = driver.findElement(By.xpath("//a[text()='Careers']"));
        careers.click();

        WebElement globalSearch = driver.findElement(By.xpath("//input[@id='typehead']"));
        globalSearch.sendKeys("QA Test Automation Developer");

        WebElement link = driver.findElement(By.xpath("//ul[@data-ph-at-id='jobs-list']//li//a[text()='QA Test Automation Developer']"));
        link.click();
    }

    @Test(priority = 2)
    public void testJobDescriptionDetails() {
        WebElement thirdParagraph = driver.findElement(By.xpath("//div[@data-ph-at-id='jobdescription-text']//p[3]"));
        String expectedTitle = "The right candidate for this role will participate in the test automation technology development and best practice models.";
        String actualTitle = thirdParagraph.getAttribute("innerHTML");
        Assert.assertEquals(actualTitle, expectedTitle, "Third paragraph mismatch");

        WebElement secondBulletPoint = driver.findElement(By.xpath("//div[@data-ph-at-id='jobdescription-text']//p[contains(text(),'Management Support')]"));
        String expectedTitle2 = "Prepare test plans, budgets, and schedules.";
        String actualTitle2 = secondBulletPoint.getAttribute("innerHTML");
        Assert.assertEquals(actualTitle2, expectedTitle2, "BulletPoint mismatch");

        WebElement thirdRequirement = driver.findElement(By.xpath("//div[@data-ph-at-id='jobdescription-text']//ul[2]/li[3]"));
        String expectedTitle3 = "5+ years of experience in QA automation development and scripting.";
        String actualTitle3 = thirdRequirement.getAttribute("innerHTML");
        Assert.assertEquals(actualTitle3, expectedTitle3, "Third requirement mismatch");
    }

    @Test(priority = 3)
    public void testTechnicalToolsDetails() {
        List<WebElement> technicalTools = driver.findElements(By.xpath("//div[@data-ph-at-id='jobdescription-text']//ul[3]//li"));
        for (WebElement e : technicalTools) {
            String expectedTitle4 = "Selenium";
            String actualTitle4 = e.getAttribute("innerHTML");
            Assert.assertEquals(actualTitle4, expectedTitle4, "Tool requirement mismatch");
        }
    }

    @Test(priority = 4)
    public void testJobApplication() {
        String jobTitle = driver.findElement(By.xpath("//div[@class='css-gyk776']/h3[@class='css-18mbozw']")).getAttribute("innerHTML");

        WebElement applyNow = driver.findElement(By.xpath("//a[@data-ph-at-id='apply-link']"));
        applyNow.click();

        String jobTitleOnApplication = driver.findElement(By.xpath("//h1[@data-ph-id='ph-page-element-page4-Cl9ckS']")).getAttribute("innerHTML");
        Assert.assertEquals(jobTitleOnApplication, jobTitle, "Job title mismatch");

        driver.navigate().back();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
