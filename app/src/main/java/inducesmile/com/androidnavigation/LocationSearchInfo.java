package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 12/11/2015.
 */
public class LocationSearchInfo implements Comparable<LocationSearchInfo>{

    private String loc_id;
    private String loc_name;
    private String division_id;
    private double distance;
    private String address;

    public LocationSearchInfo(String loc_id, String loc_name, String division_id, double distance, String address){

        this.loc_id=loc_id;
        this.loc_name=loc_name;
        this.division_id=division_id;
        this.distance= distance;
        this.address=address;
    }

    public void setLoc_id(String loc_id){
        this.loc_id=loc_id;
    }

    public String getLoc_id(){
        return this.loc_id;
    }

    public void setLoc_name(String loc_name){
        this.loc_name=loc_name;
    }

    public String getLoc_name(){
        return this.loc_name;
    }

    public void setDivision_id(String division_id){
        this.division_id=division_id;
    }

    public String getDivision_id(){
        return this.division_id;
    }

    public void setDistance(double distance){
        this.distance=distance;
    }

    public double getDistance(){
        return distance;
    }

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){
        return address;
    }



    @Override
    public int compareTo(LocationSearchInfo o) {
        return new Double(distance).compareTo( o.distance);
    }
    @Override
    public String toString() {
        return String.valueOf(distance);
    }



}
