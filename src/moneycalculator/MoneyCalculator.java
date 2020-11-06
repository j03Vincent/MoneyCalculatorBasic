package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MoneyCalculator {
    
    double amount;
    double exchangerate;
    String currencyFrom;
    String currencyTo;

    public static void main(String[] args) throws IOException {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.control();
    }
    
    private void control() throws IOException {
        input();
        process();
        output();
    }
    
    private void input() {
        System.out.println("Introduce una cantidad: ");
        Scanner scanner = new Scanner(System.in);
        amount = scanner.nextDouble();

        System.out.println("Introduce moneda inicial: ");
        currencyFrom = scanner.next().toUpperCase();
        
        System.out.println("Introduce moneda resultado: ");
        currencyTo = scanner.next().toUpperCase();
        
        while(currencyTo.equals(currencyFrom)){
            System.out.println("No puedes convertir dos monedas iguales.");
            System.out.println("Introduce moneda resultado: ");
            currencyTo = scanner.next().toUpperCase();
        }
    }
    
    private void process() throws IOException{
        exchangerate = getExchangeRate(currencyFrom,currencyTo);
    }

    private void output() {
        System.out.println(amount + " " + currencyFrom + " = " + 
                amount*exchangerate + " " + currencyTo);
    }

    private static double getExchangeRate(String from, String to) throws IOException {
        URL url = 
            new URL("https://free.currconv.com/api/v7/convert?apiKey=do-not-use-this-key&q=" +
                    from +"_" + to +"&compact=y");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine(); //leer cadena completa
            String line1 = line.substring(line.indexOf(to)+12, line.indexOf("}"));
            return Double.parseDouble(line1);
        }
    }
}