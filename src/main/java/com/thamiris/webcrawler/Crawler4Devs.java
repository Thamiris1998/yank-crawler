package com.thamiris.webcrawler;

import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Crawler4Devs {

	private static String URL_GERADOR_CPF = "https://www.4devs.com.br/gerador_de_cpf";
	private static String URL_GERADOR_NICKS = "https://www.4devs.com.br/gerador_de_nicks";

	public static void main(String[] args) throws IOException {

		//AQUI É NECESSÁRIO COLOCAR O PATH EM QUE O ARQUIVO GECKDRIVER SE ENCONTRA E FAZER O APONTAMENTO
		System.setProperty("webdriver.gecko.driver", "/Users/thamirislopes/Downloads/yank-crawler/drives/geckodriver/geckodriver");

		//AQUI É NECESSÁRIO COLOCAR O PATH EM QUAL LUGAR DESEJA GERAR O ARQUIVO NickAndCpf.txt
		File dir = new File("/Users/thamirislopes/Downloads/yank-crawler");
		File file = new File(dir, "NickAndCpf.txt");
		file.createNewFile();
		FileWriter fileWriter = new FileWriter(file, false);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println("NICK; CPF");

		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);

		List<String> nicks = getNicks(options);
		WebDriver driver = new FirefoxDriver(options);
		driver.get(URL_GERADOR_CPF);
		for (String nick : nicks) {
			String cpf = getCpf(driver);
			printWriter.println(nick + "; " + cpf);
			System.out.println(nick + "; " + cpf);
		}
		driver.close();
		printWriter.flush();
		printWriter.close();
	}

	public static String getCpf(WebDriver driver){
		WebElement gerarCpf = driver.findElement(By.id("bt_gerar_cpf"));
		gerarCpf.click();
		String cpf = Jsoup.parse(driver.getPageSource()).getElementById("texto_cpf").text();
		System.out.println(cpf);
		return (!cpf.equals("Gerando...")) ?  cpf  :  getCpf(driver);
	}

	public static List<String> getNicks(FirefoxOptions options){
		WebDriver driver = new FirefoxDriver(options);
		driver.get(URL_GERADOR_NICKS);

		Select method = new Select(driver.findElement(By.id("method")));
		method.selectByVisibleText("Aleatório");

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].value='50';", driver.findElement(By.id("quantity")));

		Select limit = new Select(driver.findElement(By.id("limit")));
		limit.selectByVisibleText("8");

		WebElement gerarNicks = driver.findElement(By.id("bt_gerar_nick"));
		gerarNicks.click();

		List<String> nicks = Jsoup.parse(driver.getPageSource()).getElementsByClass("generated-nick").eachText();
		driver.close();
		return nicks;
	}
}
