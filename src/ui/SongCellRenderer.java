/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import data.Song;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author fzakaria
 */
public class SongCellRenderer extends JLabel implements ListCellRenderer
{

    public SongCellRenderer()
    {
        setOpaque(true);
    }
    
    @Override
    public Component getListCellRendererComponent(JList jList, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        if (value instanceof Song)
        {
            Song song = (Song) value;
            setBackground(isSelected ? Color.lightGray : Color.white);
            setForeground(song.getHasDownloaded() ? Color.GREEN : Color.black);
            this.setText(song.getArtist() + " - " + song.getTitle());
        }
        return this;
    }
    
}

