package chapter11.timer;

import java.util.*;
import java.net.*;

public class URLPingTask extends TimerTask {

    public interface URLUpdate {
        public void isAlive(boolean b);
    }

    URL url;
    URLUpdate updater;

    public URLPingTask(URL url) {
        this(url, null);
    }

    public URLPingTask(URL url, URLUpdate uu) {
        this.url = url;
        updater = uu;
    }

    public void run() {
        if (System.currentTimeMillis() - scheduledExecutionTime() > 5000) {
            // Let the next task do it
            return;
        }

        int code = -1;
        try {
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setConnectTimeout(1000);
            huc.setReadTimeout(1000);
            code = huc.getResponseCode();
            System.out.println(Thread.currentThread().getName() + " - " + url + " -\tResponse Code :" + code);
            if (updater != null)
                updater.isAlive(true);
        } catch (Exception e) {
        	System.out.println(Thread.currentThread().getName() + " - " + url + " -\tResponse Code :" + code);
            if (updater != null)
                updater.isAlive(false);
        }
    }
}

