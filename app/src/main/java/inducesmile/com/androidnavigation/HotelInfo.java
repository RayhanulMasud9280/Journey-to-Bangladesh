package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 12/12/2015.
 */
public class HotelInfo {

    private String name;
    private String contact;
    public HotelInfo(String name,String contact){
        this.name=name;
        this.contact=contact;
    }

    public String getName(){
        return this.name;
    }
    public String getContact(){
        return this.contact;
    }
}
