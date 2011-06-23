/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ProgressMonitorInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import ui.HypeMachineGUI;

/**
 *
 * @author fzakaria
 */

public class Song extends Observable implements Runnable {
    
    private String _title;
    private String _key = "";
    private String _cookie = "";
    private String _id = "";
    private String _artist = "";
    private String _downloadURL = "";
    
    public static String BASE_FILE_PATH = "";
    
    private Boolean _hasDownloaded;
   

    Song(String currentID, String currentKey, String currentTitle, String currentArtist) {
        this._title = currentTitle;
        this._id = currentID;
        this._key = currentKey;
        this._artist = currentArtist;
        this._hasDownloaded = false;
        this._downloadURL = "http://hypem.com/serve/play/" +this._id + "/" + this._key + ".mp3";
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return _title;
    }


    /**
     * @return the hasDownloaded
     */
    public Boolean getHasDownloaded() {
        return _hasDownloaded;
    }

    /**
     * @param hasDownloaded the hasDownloaded to set
     */
    public void setHasDownloaded(Boolean hasDownloaded) {
        this._hasDownloaded = hasDownloaded;
    }

    /**
     * @return the _artist
     */
    public String getArtist() {
        return _artist;
    }

    /**
     * @param artist the _artist to set
     */
    public void setArtist(String artist) {
        this._artist = artist;
    }
    
    
    /**
     * @return the _cookie
     */
    public String getCookie() {
        return _cookie;
    }

    /**
     * @param cookie the _cookie to set
     */
    public void setCookie(String cookie) {
        this._cookie = cookie;
    }
    

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName()).append(" Object { ").append(NEW_LINE);
        result.append(" Title: ").append(this._title).append(NEW_LINE);
        result.append(" Artist: ").append(this.getArtist()).append(NEW_LINE);
        result.append("}");
        
        return result.toString();
    }

    @Override
    public void run() {
        //We wil download it here
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(this._downloadURL);
        httpget.addHeader("cookie", this._cookie);
        String strFilePath = BASE_FILE_PATH + "/"+ this._title + ".mp3";
        FileOutputStream fos = null;
        
        
        try {
            HypeMachineGUI.getInstance().logText("Starting download of : " + this._title + "...");
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            
            long songSize = entity.getContentLength();
            long stepSize = songSize/10;
            long byteCount = 0;
            
            if (entity != null)
            {
                int currentByte;
                InputStream is = entity.getContent();
                fos = new FileOutputStream(strFilePath);
                
                //ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(null, "Downloading Song", is);
                //pmis.getProgressMonitor().setMaximum((int) songSize);
                
                while((currentByte = is.read()) != -1)
                {
                   fos.write(currentByte);
                   
                   byteCount++;
                   if (byteCount % stepSize == 0)
                   {
                       //HypeMachineGUI.getInstance().logText(byteCount + " : " + songSize);
                   }
                   
                }
            }
            HypeMachineGUI.getInstance().logText("Song: " + this._title + " downloaded!");
            
            this.setHasDownloaded(true);
            this.notifySongListeners(STATUS_CODE.SONG_DOWNLOADED);
        } catch (IOException ex) {
            HypeMachineGUI.getInstance().logText("Song: " + this._title + " has been canceled!");
        }
        
    }
    
    private void notifySongListeners(Object data)
    {
        this.setChanged();
        this.notifyObservers(data);
    }
    
}