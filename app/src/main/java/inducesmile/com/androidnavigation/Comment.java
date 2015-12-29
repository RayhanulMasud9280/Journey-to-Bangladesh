package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 12/5/2015.
 */
public class Comment {

    private String userName;
    private String comment;

    public Comment(String userName, String comment){

        this.userName=userName;
        this.comment=comment;
    }

    String getUserName(){
        return userName;
    }
    String getComment(){
        return  comment;

    }

    void setUserName(String userName){
        this.userName=userName;
    }
    void setComment(String comment){
        this.comment=comment;
    }
}
