import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class WebServerTest
{
    File root= new  File("TestSite");
    File fileHTML= new File(root,"typefile.html");
    File fileCSS= new File(root,"typefile.css");
    File fileJS= new File(root,"typefile.js");
    File fileN1= new File(root,"typefile.js..d");

    WebServer server;

    @Before
    public void setup() {
        server=new WebServer();
    }

    @Test
    public void testHTML()
    {
        assertEquals("text/html",server.getFilePath(fileHTML));
    }

    @Test
    public void testCSS()
    {
        assertEquals("text/css",server.getFilePath(fileCSS));
    }

    @Test
    public void testJS()
    {
        assertEquals("text/js",server.getFilePath(fileJS));
    }

    @Test
    public void testBadFile()
    {
        assertEquals("text/html",server.getFilePath(fileN1));
    }

    @Test
    public void testPortUnderZero()
    {
        assertEquals(false,server.setPort(-1));
    }

    @Test
    public void testPortAboveMax()
    {
        assertEquals(false,server.setPort(66000));
    }


    @Test
    public void testSetPort1()
    {
        int port=0;
        server.setPort(port);
        assertEquals(false,server.acceptServerPort());
    }

    @Test
    public void test_acceptServerPort()
    {
        server.setPort(10005);
        assertEquals(true,server.acceptServerPort());
    }

    @Test
    public void test_Exception1_acceptServerPort()
    {
        server.setPort(100000);
        assertEquals(false,server.acceptServerPort());
    }

    @Test
    public void test_Exception2_acceptServerPort()
    {
        server.setPort(-10);
        assertEquals(false,server.acceptServerPort());
    }

    @Test
    public void test_Exception3_acceptServerPort()
    {
        server.setPort(1024);
        assertEquals(false,server.acceptServerPort());
    }


    @Test
    public void test_Exception4_acceptServerPort()
    {
        server.setPort(65000);
        assertEquals(false,server.acceptServerPort());
    }

    @Test
    public void testListen()
    {
        server.setPort(8444);
        server.acceptServerPort();
        int myVar=server.conectionClient;
        assertEquals(0,myVar);

    }
    @Test
    public void test() throws IOException {
        WebServer server=new WebServer();

        File root= new  File("www");
        File fileHTML= new File(root,"not_supported.html");
        int fileLength =(int)fileHTML.length();

        byte[] binarFileT= Files.readAllBytes(Paths.get("www/not_supported.html"));
        byte[] binarFileR = server.readFileData(fileHTML, fileLength);
        System.out.print(Arrays.toString(binarFileR));
        assertEquals(binarFileT,binarFileR);
    }

    @Test
    public void testRead2() throws IOException  {
        WebServer server =new WebServer();

        File root= new  File("www");
        File fileHTML= new File(root,"not_supported.html");
        int fileLength =(int)fileHTML.length();

        byte[] binarFileT="".getBytes();
        byte[] binarFileR = server.readFileData(null, 0);
        System.out.print(Arrays.toString(binarFileR));
        assertEquals(NullPointerException.class,binarFileR);
    }

    

}
