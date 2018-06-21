/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marketsource.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dell
 */
@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet{
    

    // Properties ---------------------------------------------------------------------------------

    private String imagePath;

    // Init ---------------------------------------------------------------------------------------

    public void init() throws ServletException {

        // Define base path somehow. You can define it as init-param of the servlet.
        this.imagePath = "C:\\Users\\dell\\Desktop\\MarketData\\Images";

        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\var\webapp\images".
        // In Linux/Mac/UNIX, it is just straightforward "/var/webapp/images".
    }

    // Actions ------------------------------------------------------------------------------------

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get requested image by path info.
        System.out.println("....found");
        response.sendRedirect("admin.xhtml");

        // Check if file name is actually supplied to the request URI.
        

        // Decode the file name (might contain spaces and on) and prepare file object.
        

        // Check if file actually exists in filesystem.
       

        // Get content type by filename.
        

        // Check if file is actually an image (avoid download of other files by hackers!).
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        

        // Init servlet response.
        

        // Write image content to response.
        
    }
    
}
