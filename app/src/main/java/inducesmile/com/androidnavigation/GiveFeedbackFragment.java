package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 10/29/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class GiveFeedbackFragment extends Fragment {


    public GiveFeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.give_feedback, container, false);
    }

}
