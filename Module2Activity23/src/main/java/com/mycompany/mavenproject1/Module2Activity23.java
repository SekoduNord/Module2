/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Olivier
 */
public class Module2Activity23 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        //Application menu
        System.out.println("Select a function:\n"
                + "1-Get, 2-Post, 3-Update, 4-Delete, 5-Quit");
        String entry = in.nextLine();
        String[] buffer = {"Title", "Description", "Isbn"};

        while (!entry.equals("5")) {
            switch (entry) {
                case "1":
                    System.out.println("GET : \n");
                    try {
                        get();
                    } catch (Exception e) {
                        System.out.println("Wrong entry format or book not found");
                    }
                    break;

                case "2":
                    System.out.println("POST : \n"
                            + "Enter book info (Title,Description,ISBN)");
                    entry = in.nextLine();
                    buffer = entry.split(",");
                    try {
                        post(buffer[0], buffer[1], buffer[2]);
                    } catch (Exception e) {
                        System.out.println("Wrong entry format");
                        System.out.println(e.getMessage());
                        for (StackTraceElement stackTrace : e.getStackTrace()) {
                            System.out.println(stackTrace);
                        }
                    }
                    break;

                case "3":
                    System.out.println("PUT : \n"
                            + "Enter book info (Id, Title,Description,ISBN)");
                    entry = in.nextLine();
                    buffer = entry.split(",");
                    try {
                        update(Integer.parseInt(buffer[0]), buffer[1], buffer[2], buffer[3]);
                    } catch (Exception e) {
                        System.out.println("Wrong entry format or book not found");
                    }
                    break;

                case "4":
                    System.out.println("DELETE : \n"
                            + "Enter id requested");
                    try {
                        int id = in.nextInt();
                        Module2Activity23 module2Activity23 = new Module2Activity23();
                        module2Activity23.delete(id);
                    } catch (Exception e) {
                        System.out.println("Wrong entry format or book not found");
                    }
                    entry = in.nextLine();
                    break;

                default:
                    System.out.println("Wrong entry");
            }
            System.out.println("Select a function:\n"
                    + "1-Get, 2-Post, 3-Update, 4-Delete, 5-Quit");
            entry = in.nextLine();

        }
        System.out.println("Exit selected");
        quit();
    }

    public static void get() {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            //Define a HttpGet request; You can choose between HttpPost , HttpDelete or HttpPut also.
            //Choice depends on type of method you will be invoking.
            HttpGet getRequest = new HttpGet("http://localhost:8084/Module2Activity1/webresources/getBook");

            //Send the request; It will immediately return the response in HttpResponse object
            HttpResponse response = httpClient.execute(getRequest);

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }

            //Now pull back the response object
            HttpEntity httpEntity = response.getEntity();
            String apiOutput = EntityUtils.toString(httpEntity);

            //Lets see what we got from API
            System.out.println(apiOutput);

        } catch (IOException | ParseException ex) {
            Logger.getLogger(Module2Activity23.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }
    }

    public static void post(String title, String author, String isbn) {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            //Define a postRequest request
            HttpPost postRequest = new HttpPost("http://localhost:8084/Module2Activity1/webresources/addBook");

            StringWriter writer = new StringWriter();
            writer.write("title=" + title + "&" + "author=" + author + "&"
                    + "isbn=" + isbn);

            //Set the API media type in http content-type header
            postRequest.addHeader("content-type", "application/x-www-form-urlencoded");

            //Set the request post body
            StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
            postRequest.setEntity(userEntity);

            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = httpClient.execute(postRequest);

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (!(statusCode == 201 | statusCode == 204)) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            } 
        } catch (IOException ex) {
            Logger.getLogger(Module2Activity23.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }
    }

    public static void update(int id, String title, String author, String isbn) {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            //Define a postRequest request
            HttpPut putRequest = new HttpPut("http://localhost:8084/Module2Activity1/webresources/updateBook");

            StringWriter writer = new StringWriter();
            writer.write("id=" + id + "&" + "title=" + title + "&" + "author=" + author + "&"
                    + "isbn=" + isbn);

            //Set the API media type in http content-type header
            putRequest.addHeader("content-type", "application/x-www-form-urlencoded");

            //Set the request post body
            StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
            putRequest.setEntity(userEntity);

            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = httpClient.execute(putRequest);

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (!(statusCode == 201 | statusCode == 204)) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            } 
        } catch (IOException ex) {
            Logger.getLogger(Module2Activity23.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }
    }

    public void delete(int id) {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        try {
            //Define a postRequest request
            MyDelete deleteRequest = new MyDelete("http://localhost:8084/Module2Activity1/webresources/deleteBook");

            StringWriter writer = new StringWriter();
            writer.write("id=" + id);

            //Set the API media type in http content-type header
            deleteRequest.addHeader("content-type", "application/x-www-form-urlencoded");

            //Set the request post body
            StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
            deleteRequest.setEntity(userEntity);

            //Send the request; It will immediately return the response in HttpResponse object if any
            HttpResponse response = httpClient.execute(deleteRequest);

            //verify the valid error code first
            int statusCode = response.getStatusLine().getStatusCode();
            if (!(statusCode == 201 | statusCode == 204)) {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            } 
        } catch (IOException ex) {
            Logger.getLogger(Module2Activity23.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Important: Close the connect
            httpClient.getConnectionManager().shutdown();
        }
    }

    public static void quit() {
        System.exit(0);
    }

    class MyDelete extends HttpPost {

        public MyDelete(String url) {
            super(url);
        }

        @Override
        public String getMethod() {
            return "DELETE";
        }
    }
}
