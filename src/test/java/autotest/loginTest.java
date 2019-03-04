package autotest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class loginTest {
    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window();
        driver.get ("https://epay.railways.kz");
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
        {
            assert wd != null;
            return ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete");
        });
    }

    @Test
    public void userLogin() {
        driver.findElement(By.className("jsLinkAuth")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        WebElement modal = driver.findElement(By.xpath("//div[contains(@class,'modal-content')]"));
        WebElement inputlogin = modal.findElement(By.xpath("//input[contains(@name,'CLIENT_LOGIN')]"));
        WebElement inputpass = modal.findElement(By.xpath("//input[contains(@name,'CLIENT_PWD')]"));
        WebElement login_enter = modal.findElement(By.xpath("//button[contains(@id,'sbmLogin')]"));

        String maxLengthLogin = inputlogin.getAttribute("maxlength");
        if (maxLengthLogin == null) {
            System.out.println("No limit is set on login input");
        }
        else {
            if (maxLengthLogin.equals("25")) {
                System.out.println("Max character limit is set as expected.");
            }
        }

        String maxLengthPassword = inputpass.getAttribute("maxlength");
        if (maxLengthPassword == null) {
            System.out.println("No limit is set on password input");
        }
        else {
            if (maxLengthPassword.equals("25")) {
                System.out.println("Max character limit is set as expected.");
            }
        }
        inputlogin.sendKeys("logintestproject");
        inputpass.sendKeys("logintestproject");
        login_enter.sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Assert.assertTrue(driver.findElement(By.xpath("//b[contains(.,'Муратбеков Арслан Газизович')]")).isDisplayed());
        driver.findElement(By.xpath("//div[@id='userDiv']/figure/div/button/span")).click();
        driver.findElement(By.xpath("//a[contains(@onclick,'loginDo(2);')]")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//b[contains(.,'Войти')]")).isDisplayed());
    }

    @Test
    public void userLoginPassEmpty() {
        driver.findElement(By.className("jsLinkAuth")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement modal = driver.findElement(By.xpath("//div[contains(@class,'modal-content')]"));
        WebElement login_enter = modal.findElement(By.xpath("//button[contains(@id,'sbmLogin')]"));
        Assert.assertFalse(login_enter.isEnabled());
    }

    @Test
    public void userPassEmpty() {
        driver.findElement(By.className("jsLinkAuth")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement modal = driver.findElement(By.xpath("//div[contains(@class,'modal-content')]"));
        WebElement inputlogin = modal.findElement(By.xpath("//input[contains(@name,'CLIENT_LOGIN')]"));
        inputlogin.sendKeys("logintestproject");
        WebElement login_enter = modal.findElement(By.xpath("//button[contains(@id,'sbmLogin')]"));
        Assert.assertFalse(login_enter.isEnabled());
    }

    @Test
    public void userPassError() {
        driver.findElement(By.className("jsLinkAuth")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        WebElement modal = driver.findElement(By.xpath("//div[contains(@class,'modal-content')]"));
        WebElement inputlogin = modal.findElement(By.xpath("//input[contains(@name,'CLIENT_LOGIN')]"));
        WebElement inputpass = modal.findElement(By.xpath("//input[contains(@name,'CLIENT_PWD')]"));
        WebElement login_enter = modal.findElement(By.xpath("//button[contains(@id,'sbmLogin')]"));

        inputlogin.sendKeys("logintestproject");
        inputpass.sendKeys("1234567");
        login_enter.sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Assert.assertFalse(driver.findElement(By.xpath("//div[contains(@id,'loginErrDiv')]")).isDisplayed());
    }

    @Test
    public void userLoginScript() {
        driver.findElement(By.className("jsLinkAuth")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        WebElement modal = driver.findElement(By.xpath("//div[contains(@class,'modal-content')]"));
        WebElement inputlogin = modal.findElement(By.xpath("//input[contains(@name,'CLIENT_LOGIN')]"));
        WebElement inputpass = modal.findElement(By.xpath("//input[contains(@name,'CLIENT_PWD')]"));
        WebElement login_enter = modal.findElement(By.xpath("//button[contains(@id,'sbmLogin')]"));

        inputlogin.sendKeys("<script>alert(123)</script>");
        inputpass.sendKeys("logintestproject");
        login_enter.sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='loginErrDiv']/font/b")).getText(), "403" );
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
