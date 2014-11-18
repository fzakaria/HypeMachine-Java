package data.gsonHelper;

import com.google.gson.annotations.SerializedName;
import data.Song;

import java.util.ArrayList;

/**
 * Created by mitchross on 11/17/14.
 */
public class TrackListings {

    @SerializedName("tracks")
    public ArrayList<Track> tracks;
}
