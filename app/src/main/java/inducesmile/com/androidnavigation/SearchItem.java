package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 10/30/2015.
 */

public class SearchItem {

    private String searchType;
    //private String address;
    private int photoId;

    public SearchItem(String searchType,int photoId) {
        this.searchType = searchType;
        //this.address = address;
        this.photoId = photoId;
    }

    public String getName() {
        return searchType;
    }

    public void setName(String name) {
        this.searchType = name;
    }

    /*public String getAddress() {
        return address;
    }

    //public void setAddress(String address) {
        this.address = address;
    }*/

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
