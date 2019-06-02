/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.root;

import java.io.IOException;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * Rootwe Service
 *
 * @author Olivier
 */
public class Rootwe {

    /**
     * Creates a new instance of Rootwe
     */
    public Rootwe() {
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable th) {
        }
    }

    /**
     *
     * @return an instance of RestResponse
     */
    public static RestResponse getBook() throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{};
        RestConnection conn = new RestConnection("http://localhost:8084/Module2Activity1/webresources///getBook/", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }

    /**
     *
     * @param title
     * @param author
     * @param isbn
     * @return an instance of RestResponse
     */
    public static RestResponse addBook(String title, String author, String isbn) throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{{"title", title}, {"author", author}, {"isbn", isbn}};
        RestConnection conn = new RestConnection("http://localhost:8084/Module2Activity1/webresources///addBook/", pathParams, null);
        sleep(1000);
        return conn.post(null, queryParams);
    }

    /**
     *
     * @return an instance of RestResponse
     */
    public static RestResponse getBookId() throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{};
        RestConnection conn = new RestConnection("http://localhost:8084/Module2Activity1/webresources///getBookId/{id:d+}", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }
}
