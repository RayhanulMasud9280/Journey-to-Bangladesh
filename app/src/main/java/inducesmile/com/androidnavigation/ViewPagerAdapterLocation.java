package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 10/26/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ViewPagerAdapterLocation extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    String locationId;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterLocation(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb,String locationId) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.locationId=locationId;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            DescriptionTab descr = new DescriptionTab();

            return descr;
        }
        else if(position==1)
        {
            HowToGoTab how = new HowToGoTab();
            return how;
        }
        /*else if(position==2)
        {
            HotelsTab hotel = new HotelsTab();
            return hotel;
        }*/
        else
        {
            CommentTab comment = new CommentTab();
            Bundle bundle= new Bundle(2);
            //Bundle bundle = new Bundle().put
            bundle.putString("locationId", locationId);
            comment.setArguments(bundle);
            return comment;
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
