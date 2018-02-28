package fahamu.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Resources {

    public  String rootResourcePath="";
    public static String BUSINESS_NAME = "SMART STOCK MANAGER"; //default name
    public byte[] serverIPv4Address = new byte[]{(byte) 192, (byte) 168, 0, 2};


    public URL getResourceAsUrl(String name) {
        Path path= Paths.get(rootResourcePath,name);
        File file=path.toFile();
        try {
           // System.out.println(file.toURI().toURL());
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Path getResourceAsPath(String name){
        //System.out.println(Paths.get(rootResourcePath,name));
        return Paths.get(rootResourcePath,name);
    }
}
