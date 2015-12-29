package inducesmile.com.androidnavigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by RayhanulMasud on 12/11/2015.
 */
public class ViewPagerAdapterTourOperator extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    int id;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterTourOperator(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb,int id) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.id=id;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {

            OperatorTab opTab = new OperatorTab();
            Bundle bundle= new Bundle();
            bundle.putString("choice", Integer.toString(position+1));
            bundle.putString("id",Integer.toString(id+1));
            opTab.setArguments(bundle);

            return opTab;
        }
        else
        {
            OperatorTab opTab = new OperatorTab();
            Bundle bundle= new Bundle();
            bundle.putString("choice", Integer.toString(position+1));
            bundle.putString("id",Integer.toString(id+1));

            opTab.setArguments(bundle);

            return opTab;
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
