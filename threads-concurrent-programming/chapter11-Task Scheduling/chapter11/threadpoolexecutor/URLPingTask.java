package chapter11.threadpoolexecutor;

import java.net.*;

public class URLPingTask implements Runnable {

    public interface URLUpdate {
        public void isAlive(boolean b);
    }

    URL url;
    URLUpdate uu;

    public URLPingTask(URL url) {
        this(url, null);
    }

    public URLPingTask(URL url, URLUpdate uu) {
        this.url = url;
        this.uu = uu;
    }


    public void run() {
    	int code = -1;
        try {
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setConnectTimeout(1000);
            huc.setReadTimeout(1000);
            code = huc.getResponseCode();
            System.out.println(Thread.currentThread().getName() + " - " + url + " -\tResponse Code :" + code);
            if (uu != null)
                uu.isAlive(true);
        } catch (Exception e) {
        	System.out.println(Thread.currentThread().getName() + " - " + url + " -\tResponse Code :" + code);
            if (uu != null)
               uu.isAlive(false);
        }
    }
}
