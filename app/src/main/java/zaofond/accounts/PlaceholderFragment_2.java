/**
 * Created by rinat on 17.06.14.
 */
package zaofond.accounts;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment_2 extends Fragment {


    public static ListView lvFR2;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment_2 newInstance(int sectionNumber) {
        PlaceholderFragment_2 fragment = new PlaceholderFragment_2();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment_2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_2, container, false);

        MainActivity.curFragmentPosition = 1;

        MainActivity.filteredAccountsFR2 = Account.filterAccountFR2(Account.accounts,"0");
        MainActivity.refreshAdapter(MainActivity.adapterFR2,MainActivity.filteredAccountsFR2);

        lvFR2 = (ListView)rootView.findViewById(R.id.lvFR2);
        lvFR2.setAdapter(MainActivity.adapterFR2);
        lvFR2.setClickable(true);
        //lvFR2.setTextFilterEnabled(true);
        lvFR2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PriceActivity.class);
                intent.putExtra("ID", view.getId());
                intent.putExtra("showButtons",false);
                startActivity(intent);
            }
        });



        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


}
