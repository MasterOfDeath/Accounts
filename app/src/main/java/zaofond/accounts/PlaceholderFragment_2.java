/**
 * Created by rinat on 17.06.14.
 */
package zaofond.accounts;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment_2 extends Fragment {

    public static ArrayAdapter adapterFR2;
    public static ListView lvFR2;
    private ArrayList<Account> copyAccounts;

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

        copyAccounts = new ArrayList<Account>();
        copyAccounts.addAll(Account.accounts);

        adapterFR2 = new AccountAdapter(getActivity());

        lvFR2 = (ListView)rootView.findViewById(R.id.lvFR2);
        lvFR2.setAdapter(adapterFR2);
        lvFR2.setClickable(true);
        //lvFR2.setTextFilterEnabled(true);
        lvFR2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(getActivity(), PriceActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                //Toast.makeText(getActivity(),Integer.toString(position),Toast.LENGTH_SHORT).show();
            }
        });

        adapterFR2.getFilter().filter("0");

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private class AccountAdapter extends ArrayAdapter<Account> {

        //private ArrayList<Account> originalList;
        private ArrayList<Account> accountList;
        private AccountFilter filter;

        public AccountAdapter(Context context) {
            super(context, R.layout.account_listitem_2, copyAccounts);
            this.accountList = new ArrayList<Account>();
            this.accountList.addAll(copyAccounts);
            //this.originalList = new ArrayList<Account>();
            //this.originalList.addAll(copyAccounts);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Account account = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.account_listitem_2, null);
            }
            ((TextView) convertView.findViewById(R.id.cardFIO))
                    .setText(account.COL_AUTHOR);
            ((TextView) convertView.findViewById(R.id.cardPrice))
                    .setText(account.COL_PRICE + " р.");
            ((TextView) convertView.findViewById(R.id.cardComment))
                    .setText(account.COL_COMM);

            if (account.COL_STATUS == "1")
            ((ImageView) convertView.findViewById(R.id.cardStatus))
                    .setImageResource(R.drawable.ic_item_like);

            if (account.COL_STATUS == "2")
                ((ImageView) convertView.findViewById(R.id.cardStatus))
                        .setImageResource(R.drawable.ic_item_unlike);

            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (filter == null){
                filter  = new AccountFilter();
            }
            return filter;
        }

        private class AccountFilter extends Filter
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                if(constraint != null && constraint.toString().length() > 0)
                {
                    ArrayList<Account> filteredItems = new ArrayList<Account>();

                    for(int i = 0, l = Account.accounts.size(); i < l; i++)
                    {
                        Account account = Account.accounts.get(i);
                        if(account.COL_STATUS != constraint)
                            filteredItems.add(account);
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;
                }
                else
                {
                    synchronized(this)
                    {
                        result.values = Account.accounts;
                        result.count = Account.accounts.size();
                    }
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {

                accountList = (ArrayList<Account>)results.values;
                notifyDataSetChanged();
                clear();
                for(int i = 0, l = accountList.size(); i < l; i++)
                    add(accountList.get(i));
                notifyDataSetInvalidated();
            }
        }
    }
}
