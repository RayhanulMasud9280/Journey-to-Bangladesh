package inducesmile.com.androidnavigation;

import android.graphics.Bitmap;

/**
 * Created by RayhanulMasud on 10/29/2015.
 */
public class VisitedPlaces {

    private String name;
    private Bitmap image;
    private int imageId;
    private String placeId="1";

    /*public VisitedPlaces(String name,Bitmap image,String placeId) {
        this.name = name;
        this.image = image;
        this.placeId=placeId;
    }*/

    public VisitedPlaces(String name,String placeId) {
        this.name = name;
        this.placeId=placeId;
        this.imageId = imageId;
        //this.placeId=placeId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaceId(String placeId){ this.placeId=placeId; }

    public String getPlaceId(){ return  placeId; }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap image){
        this.image=image;
    }
}
