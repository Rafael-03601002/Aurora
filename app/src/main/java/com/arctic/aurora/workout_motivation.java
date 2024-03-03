package com.arctic.aurora;

import android.widget.ImageView;
import android.widget.VideoView;

import java.util.HashMap;

abstract class workout_motivation {
    // attributes
    protected String Name;
    protected final HashMap<Integer, String> parts;
    protected ImageView Image;
    protected VideoView Video;

    // initialize hashmap
    private workout_motivation(HashMap<Integer, String> parts) {
        parts.put(1, "Chest");
        parts.put(2, "Shoulders");
        parts.put(3, "Arms");
        parts.put(4, "Back");
        parts.put(5, "Abs");
        parts.put(6, "Glutes");
        parts.put(7, "Thighs");
        parts.put(8, "Calves");

        // assign hashmap to this.parts
        this.parts = parts;
    }

    // method
    protected void setName(String Name) {
        this.Name = Name;
    }
    public void setImage(ImageView Image) {
        this.Image = Image;
    }
    public void setExampleVideo(VideoView Video) {
        this.Video = Video;
    }
    public String getName() {
        return Name;
    }
    public ImageView getImage() {
        return Image;
    }
    public VideoView getVideo() {
        return Video;
    }
}


