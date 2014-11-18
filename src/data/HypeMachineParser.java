/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.gsonHelper.Track;
import data.gsonHelper.TrackListings;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author fzakaria
 */
public class HypeMachineParser {

    private static HypeMachineParser _instance = null;
    private String _url = "";
    
    private HypeMachineParser()
    {
    }
    
    public static HypeMachineParser getInstance()
    {
        if (_instance == null)
        {
            _instance = new HypeMachineParser();
        }
        return _instance;
    }
    
    public String getURL()
    {
        return _url;
    }
    
    public void setURL(String URL)
    {
        this._url = URL;
    }
    
    public ArrayList<Song> collectSongs(String URL, int pageNumber)
    {
        this.setURL(URL);
        return collectSongs(pageNumber);
    }
    
    public ArrayList<Song> collectSongs(int pageNumber)
    {
        ArrayList<Song> songs = new ArrayList<Song>();
        HttpClient httpclient = new DefaultHttpClient();
        
        String urlLink = this._url + "/" + pageNumber + "?ax=1&ts=" + (new Date()).getTime();
        
        HttpGet httpget = new HttpGet(urlLink);
        HttpResponse response = null;


        try {
            response = httpclient.execute(httpget);
            Header cookieHeader = response.getFirstHeader("Set-Cookie");
            String cookie = cookieHeader.getValue();
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                String HTMLfile = EntityUtils.toString(entity);
                org.jsoup.nodes.Document document;
                 document = Jsoup.parse(HTMLfile);
                String trackListJSON = document.select("script#displayList-data").first().html();
                trackListJSON = trackListJSON.replace("\\", "" );
                Gson gson = new GsonBuilder().create();
                TrackListings trackListings = gson.fromJson( trackListJSON, TrackListings.class);
                int i = 1;

                for( Track t : trackListings.tracks)
                {

                    Song newSong = new Song(t.id, t.key, t.title, t.artist);
                    newSong.setCookie(cookie);

                    songs.add(newSong);
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(HypeMachineParser.class.getName()).log(Level.SEVERE, null, ex);
            return songs;
        }
        
        return songs;
    }
    
    
}
