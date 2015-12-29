package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 10/30/2015.
 */
public class TourOperatorItem {

    private String operatorName;
    //private String address;
    //private int photoId;

    public TourOperatorItem(String operatorName) {
        this.operatorName=operatorName;
    }

    public String getName() {
        return operatorName;
    }


    public void setName(String operatorName){
        this.operatorName=operatorName;
    }
}
