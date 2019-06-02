/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module2activity22;

import java.util.Scanner;
import org.netbeans.saas.root.Rootwe;
import org.netbeans.saas.RestResponse;

/**
 *
 * @author Olivier
 */
public class Module2Activity22 {

    /**
     * @param args the command line arguments
     */
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
                    System.out.println("Get command: ");
                    try {
                        get();
                    } catch (Exception e) {
                        System.out.println("Wrong entry format or book not found");
                    }
                    break;

                case "2":
                    System.out.println("Post command: \n"
                            + "Enter book info (Title,Author,ISBN)");
                    entry = in.nextLine();
                    buffer = entry.split(",");
                    try {
                        post(buffer[0], buffer[1], buffer[2]);
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

    public static void quit() {
        System.exit(0);
    }

    public static void get() {

        try {

            RestResponse result = Rootwe.getBook();
            System.out.println("The SaasService returned: "+result.getDataAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void post(String title, String author, String isbn) {

        try {

            RestResponse result = Rootwe.addBook(title, author, isbn);
            System.out.println("The SaasService returned: " + result.getDataAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
