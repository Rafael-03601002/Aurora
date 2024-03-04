package com.arctic.aurora;

import android.widget.ImageView;
import android.widget.VideoView;

import java.util.HashMap;

abstract class workout {
    protected String name;
    protected String body_part;
    protected ImageView image;
    protected VideoView video;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody_part() {
        return body_part;
    }

    public void setBody_part(String body_part) {
        this.body_part = body_part;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public VideoView getVideo() {
        return video;
    }

    public void setVideo(VideoView video) {
        this.video = video;
    }

    abstract void setIntoProgram();
}