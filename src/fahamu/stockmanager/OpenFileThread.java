package fahamu.stockmanager;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenFileThread implements Runnable {
    private URI url;
    private int service;

    OpenFileThread(int service){
        Thread thread=new Thread("open file thread");
        thread.start();
        this.service=service;
        try {
            this.url=new URI("www.google.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        switch (service){
            case 1:
                try {
                    Desktop.getDesktop().browse(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
