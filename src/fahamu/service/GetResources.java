package fahamu.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetResources {

    public static String rootResourcePath;
    public static String BUSINESS_NAME = "SMART STOCK MANAGER"; //default name
    public byte[] serverIPv4Address = new byte[]{(byte) 192, (byte) 168, 0, 2};


    public URL getResourceAsUrl(String currentDir, String name) throws MalformedURLException {
        Path path= Paths.get(currentDir,name);
        File file=path.toFile();
        return file.toURI().toURL();
    }

    public Path getResourceAsPath(String currentDir, String name){
        return Paths.get(currentDir,name);
    }
}
