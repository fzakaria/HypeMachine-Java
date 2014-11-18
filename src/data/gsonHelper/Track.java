package data.gsonHelper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mitchross on 11/17/14.
 */
public class Track
{
    @SerializedName("id")
    public String id;

    @SerializedName("key")
    public String key;

    @SerializedName("song")
    public String title;

    @SerializedName("artist")
    public String artist;

}
