import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Main {
    public static void main(String[] args){
        // 1. Init driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 2. Open login page
        driver.get("https://the-internet.herokuapp.com/login");
        // --- KHỞI TẠO EXPLICIT WAIT ---
        // Thiết lập người canh gác với thời gian tối đa 10 giây
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 3. Find username field and enter "tomsmith"
        //WebElement txtUsername = driver.findElement(By.id("username"));
        WebElement txtUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        txtUsername.sendKeys("tomsmith");

        // 4. Find Password field and enter correct password
        WebElement txtPassword = driver.findElement(By.name("password"));
        txtPassword.sendKeys("SuperSecretPassword!"); // Đã thêm dấu "!"

        // 5. Find login button and click
        // Dùng contains để tránh lỗi khoảng trắng thừa
        WebElement btnLogin = driver.findElement(By.xpath("//button[@type='submit']"));
        //WebElement btnLogin = driver.findElement(By.xpath("//button[contains(.,'Login')]"));//
        //WebElement btnLogin = driver.findElement(By.cssSelector("button.radius"));
        btnLogin.click();

        // 6. Validation
        try{
            wait.until(ExpectedConditions.urlContains("secure"));
            System.out.println("Login successfully - Test Pass");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            //Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Login fail - Test Fail");
            //throw new RuntimeException(e);
        }

//        String currentUrl = driver.getCurrentUrl();
//        if (currentUrl.contains("secure")){
//            System.out.println("Login successfully - Test Pass");
//        }
//        else {
//            System.out.println("Login fail - Test Fail");
//        }

        // Quit brower
        driver.quit();
    }
}