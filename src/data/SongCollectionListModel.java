/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.AbstractListModel;

/**
 *
 * @author fzakaria
 */
public class SongCollectionListModel extends AbstractListModel implements Observer {
     private SongCollection collection;
     private ExecutorService exec = Executors.newCachedThreadPool();
    
    public SongCollectionListModel() {
        this.collection = SongCollection.getInstance();
    }
    
    public void refresh()
    {
        this.collection.refresh();
        this.fireContentsChanged(this, 0, this.getSize() );
    }
    
    public void collectMoreSongs()
    {
        int oldSize = this.getSize();
        this.collection.collectSongs(); //collects the next page of songs
        this.fireContentsChanged(this, oldSize, this.getSize() );
    }
    
    public void updateSongList()
    {
        this.fireContentsChanged(this, 0, this.getSize() );
    }
    
    public void updateSong(int index)
    {
        if (index > this.getSize())
        {
            return;
        }
        this.fireContentsChanged(this, index, index );
    }
    
    @Override
    public int getSize() {
        return collection.getSongs().size();
    }

    @Override
    public Song getElementAt(int i) {
        return collection.getSongs().get(i);
    }
    
    public void downloadSong(int index)
    {
        Song song = this.getElementAt(index);
        song.addObserver(this);
        exec.execute(song);
    }

    @Override
    public void update(Observable o, Object o1) {
       Song song = (Song)o;
       
       this.updateSong(this.collection.findIndex(song)); //to update the jList
       
       song.deleteObserver(this);
    }
}
