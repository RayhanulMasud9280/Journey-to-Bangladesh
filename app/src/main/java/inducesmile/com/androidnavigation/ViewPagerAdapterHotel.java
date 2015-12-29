package inducesmile.com.androidnavigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by RayhanulMasud on 12/12/2015.
 */
public class ViewPagerAdapterHotel extends FragmentStatePagerAdapter{

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterHotel(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        HotelsTab divTab = new HotelsTab();
        Bundle bundle= new Bundle(2);
        bundle.putString("division", Integer.toString(position+1));
        divTab.setArguments(bundle);

        return divTab;
        /*if(position == 0) // if the position is 0 we are returning the First tab
        {
            HotelsTab divTab = new HotelsTab();
            Bundle bundle= new Bundle(2);
            bundle.putString("division", Integer.toString(position+1));
            divTab.setArguments(bundle);

            return divTab;
        }
        else if(position==1)
        {
            HotelTab divTab = new HotelTab();
            Bundle bundle= new Bundle(2);
            bundle.putString("division", Integer.toString(position+1));
            divTab.setArguments(bundle);
            return divTab;
        }
        else if(position==2)
        {
            HotelTab divTab = new HotelTab();
            Bundle bundle= new Bundle(2);
            bundle.putString("division", Integer.toString(position+1));
            divTab.setArguments(bundle);
            return divTab;
        }
        else if(position==3)
        {
            HotelTab divTab = new HotelTab();
            Bundle bundle= new Bundle(2);
            bundle.putString("division", Integer.toString(position+1));
            divTab.setArguments(bundle);
            return divTab;
        }
        else if(position==4)
        {
            HotelTab divTab = new HotelTab();
            Bundle bundle= new Bundle(2);
            bundle.putString("division", Integer.toString(position+1));
            divTab.setArguments(bundle);
            return divTab;
        }
        else if(position==5)
        {
            HotelTab divTab = new HotelTab();
            Bundle bundle= new Bundle(2);
            bundle.putString("division", Integer.toString(position+1));
            divTab.setArguments(bundle);
            return divTab;
        }
        else if(position==6)
        {
            DivisionTab divTab = new DivisionTab();
            Bundle bundle= new Bundle(2);
            bundle.putString("division", Integer.toString(position+1));
            divTab.setArguments(bundle);
            return divTab;
        }else
        {
            DivisionTab divTab = new DivisionTab();
            Bundle bundle= new Bundle(2);
            bundle.putString("division", Integer.toString(position+1));
            divTab.setArguments(bundle);
            return divTab;
        }*/


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
