/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module2activity21;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


/**
 *
 * @author Olivier
 */
public class Module2Activity21 {

    private final String USER_AGENT = "Mozilla/5.0";
    private final static String url = "http://localhost:8084/Module2Activity1/webresources/";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        //Application menu
        System.out.println("Select a function:\n"
                + "1-Get, 2-Post, 3-Quit");
        String entry = in.nextLine();
        String[] buffer = {"Title", "Author", "Isbn"};

        while (!entry.equals("3")) {
            switch (entry) {
                case "1":
                    System.out.println("Get command: \n"
                            + "Enter id requested");
                    try {
                        int id = in.nextInt();
                        display(id);
                    } catch (Exception e) {
                        System.out.println("Wrong entry format or book not found");
                    }
                    entry = in.nextLine();
                    break;

                case "2":
                    System.out.println("Post command: \n"
                            + "Enter book info (Title,Author,ISBN)");
                    entry = in.nextLine();
                    buffer = entry.split(",");
                    try {
                        add(buffer[0], buffer[1], buffer[2]);
                    } catch (Exception e) {
                        System.out.println("Wrong entry format");
                    }
                    break;

                 default:
                    System.out.println("Wrong entry");
            }
            System.out.println("Select a function:\n"
                    + "1-Get, 2-Post, 3-Quit");
            entry = in.nextLine();

        }
        System.out.println("Exit selected");
        quit();
    }

    public static void display(int id) {
        Module2Activity21 http = new Module2Activity21();
        try {
            http.sendGet(url + "getBookId/" + id);
            System.out.println("OK");
        } catch (Exception e) {
            System.out.print(e.getCause());
            System.out.println(" - ERR");
        }
    }

    public static void add(String title, String author, String isbn) {
        Module2Activity21 http = new Module2Activity21();
        try {
            http.sendPost(url + "addBook/", title, author, isbn);
            System.out.println("OK");
        } catch (Exception e) {
            System.out.print(e.getCause());
            System.out.println(" - ERR");
        }
    }

    public static void quit() {
        System.exit(0);
    }

    // HTTP GET request
    private void sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine + " ");
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
    private void sendPost(String url, String title, String author, String isbn) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("title=" + title + "&" + "author=" + author + "&"
                + "isbn=" + isbn);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

   
}
