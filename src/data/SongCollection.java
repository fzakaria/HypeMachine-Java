/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import ui.HypeMachineGUI;

/**
 *
 * @author fzakaria
 */
public class SongCollection {
    
    private ArrayList<Song> _songs = null;
    private int _pageNumber;
    private final int INITIAL_PAGE_NUMBER = 1;
    private static SongCollection _instance = null;
    
    private SongCollection()
    {
        _songs = new ArrayList<Song>();
        _pageNumber = INITIAL_PAGE_NUMBER;
    }
    

    public static SongCollection getInstance()
    {
        if (_instance == null)
        {
            _instance = new SongCollection();
        }
        return _instance;
    }
      
    
    /**
     * @return the _songs
     */
    public ArrayList<Song> getSongs() {
        return _songs;
    }

    /**
     * @return the _pageNumber
     */
    public int getPageNumber() {
        return _pageNumber;
    }

    /**
     * @param pageNumber the _pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this._pageNumber = pageNumber;
    }
    
    
    public void refresh()
    {
        this._pageNumber = INITIAL_PAGE_NUMBER;
        _songs.clear();
        collectSongs();
    }
    
    public void collectSongs()
    {
        collectSongs(this._pageNumber);
        this._pageNumber++;
    }
    
    public void collectSongs(int pageNumber)
    {
        boolean listChanged = _songs.addAll( HypeMachineParser.getInstance().collectSongs(pageNumber) );
    }
    
    public int findIndex(Song song)
    {
        return this._songs.indexOf(song);
    }
    
    
}
