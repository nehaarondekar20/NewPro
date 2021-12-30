package newpac;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class NewTest {
	WebDriver driver;
	static Logger logger=Logger.getLogger("NewTest.class");
	
  
@Test(dataProvider = "credentials")
  public void f(String uname,String pwd) throws Exception {
	 BasicConfigurator.configure();
	 logger.info("Logging in");
	 driver.findElement(By.id("txtUsername")).sendKeys(uname);
	 driver.findElement(By.xpath("//div[@class='textInputContainer']//input[@name='txtPassword']")).sendKeys(pwd);
	 driver.findElement(By.id("btnLogin")).click();
	 
//	 File ssFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//	 org.openqa.selenium.io.FileHandler.copy(ssFile,new File("D:\\Nehase\\screenshot.png"));
//
//	 
	 String pageTitle=driver.getTitle();
//	 logger.info(pageTitle);
//	 String expectedTitle="OrangeHRM";
//	 Assert.assertEquals(pageTitle, expectedTitle);
//	 logger.info("Employees on leave");
//	 driver.findElement(By.id("menu_leave_viewLeaveModule")).click();
//	 WebElement tabl=driver.findElement(By.id("resultTable"));
//	 List<WebElement> rows=tabl.findElements(By.tagName("tr"));
//	 for(WebElement row:rows) {
//		 List<WebElement> cols=row.findElements(By.tagName("td"));
//		 for(WebElement col:cols) {
//			 System.out.println(col.getText());
//		 }
//	 }
//	 
	 logger.info("ADD Emplyee");
	 driver.findElement(By.id("menu_pim_viewPimModule")).click();
	 driver.findElement(By.id("menu_pim_addEmployee")).click();
	 
	 File file=new File("D:\\Nehase\\data.xlsx");
	 FileInputStream fis=new FileInputStream(file);
	 XSSFWorkbook wb=new XSSFWorkbook(fis);
	 XSSFSheet sheet=wb.getSheet("Sheet1");
	 int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
	 for(int i=1;i<=rowCount;i++) {
		 driver.findElement(By.id("firstName")).sendKeys(sheet.getRow(i).getCell(0).getStringCellValue());
		 driver.findElement(By.id("lastName")).sendKeys(sheet.getRow(i).getCell(1).getStringCellValue());
		 //driver.findElement(By.id("chkLogin")).click();
		 
		 logger.info("Text matches or not");
			String linktobechekc=driver.findElement(By.xpath("//*[@id=\"frmAddEmp\"]/fieldset/ol/li[3]/label[2]")).getText();
			 
			 if(linktobechekc.equals("Accepts jpg, .png, .gif up to 1MB. Recommended dimensions: 200px X 200px")) {
				 System.out.println("Link matchees");
			 }
			 else
				 System.out.println("Link does not matchees");
		 
		 driver.findElement(By.id("btnSave")).click();
		 
		 Assert.assertTrue(driver.findElement(By.id("btnSave")).isEnabled(), "Button is disable");
		 
		 
		 driver.findElement(By.xpath("//input[@id='btnSave'][@value='Edit']")).click();
		 driver.findElement(By.id("personal_optGender_2")).click();
		 WebElement nationality=driver.findElement(By.id("personal_cmbNation"));
		 Select select = new Select(nationality);
		 select.selectByIndex(82);
		 driver.findElement(By.xpath("//input[@id='btnSave'][@value='Save']")).click();
		 driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		 Row newRow=wb.getSheet("Sheet1").getRow(i);
		 Cell cell=newRow.createCell(2);
		 cell.setCellValue(pageTitle);
	 }
	 
	 
	 logger.info("Number of links");
	 List<WebElement> links=driver.findElements(By.tagName("a"));
	 System.out.println("No. of links= "+links.size());
	 
	 logger.info("Text present or not");
	 if(driver.getPageSource().contains("Marketplace")) {
		 System.out.println("Text present");
	 }
	 else
		 System.out.println("Text not present");
	 FileOutputStream fout=new FileOutputStream(file);
	 wb.write(fout);
	 wb.close();
	 
	 
  }
  @DataProvider(name="credentials")
  public Object[][] getData(){
	  Object[][] data=new Object[2][2];
	  data[0][0]="Admin";
	  data[0][1]="admin123";
	  
//	  data[1][0]="Admin1";
//	  data[1][1]="admin123";
	return data;
	  
  }
  
  @BeforeMethod
  @Parameters({"URL"})
  public void beforeMethod(String url) {
	  System.setProperty("webdriver.chrome.driver", "D:\\Nehase\\chromedriver_win32 (1)\\chromedriver.exe");
	  driver=new ChromeDriver();
	  driver.get(url);
	  driver.manage().window().maximize();
	  
  }

  @AfterMethod
  public void afterMethod() {
	  
	  //driver.close();
  }

}
