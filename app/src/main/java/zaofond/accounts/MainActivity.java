package zaofond.accounts;

import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static ArrayList<Account> filteredAccountsFR1;
    public static ArrayList<Account> filteredAccountsFR2;
    public static ArrayAdapter adapterFR1;
    public static ArrayAdapter adapterFR2;
    public static int curFragmentPosition = 0;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        filteredAccountsFR1 = new ArrayList<Account>();
        //Account.filterAccountFR1(Account.accounts,filteredAccountsFR1,"0");
        adapterFR1 = new AccountAdapterFR1(this);

        filteredAccountsFR2 = new ArrayList<Account>();
        //Account.filterAccountFR2(Account.accounts,filteredAccountsFR2,"0");
        adapterFR2 = new AccountAdapterFR2(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        //position = curFragment;
        switch (position+1){
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment_1.newInstance(position + 1))
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment_2.newInstance(position + 1))
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refreshdata) {
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isFinishing()) // активность не завершена
                    adapterFR1.notifyDataSetChanged();
                }
            });*/

            Account.accounts.clear();
            Account.accounts.add(new Account("1", "Пензин Антон","Компания Мастер","1200","","0"));
            Account.accounts.add(new Account("2", "Русских А.В.","ООО ЭлектроСтрой","24256","","0"));
            Account.accounts.add(new Account("3", "Пензин Антон","Арсенал+","12600","","0"));
            Account.accounts.add(new Account("4", "Меркин М.А.","ОАО СтальМостСтрой","400","","0"));
            Account.accounts.add(new Account("5", "Овчаренко С.В.","Компания Север","42150","","0"));
            Account.accounts.add(new Account("6", "Гумиров Р.И.","Компания Мега","2650","В работу","1"));
            Account.accounts.add(new Account("7", "Ершов А.Ю.","ООО СерерРыбХоз","120000","В работу","1"));
            Account.accounts.add(new Account("8", "Меркин М.А.","ЗАО СибНефтеМаш","20000","Зайдите","2"));
            Account.accounts.add(new Account("9", "Пензин Антон","Компания Мастер","1200","В работу","1"));

            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void refreshAdapter(ArrayAdapter adapter, ArrayList<Account> accounts){
        adapter.clear();
        adapter.addAll(accounts);
    }

    private class AccountAdapterFR1 extends ArrayAdapter<Account> {
        public AccountAdapterFR1(Context context) {
            super(context, R.layout.account_listitem_1, filteredAccountsFR1);
        }

        /*@Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }*/

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Account account = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.account_listitem_1, null);
            }
            ((TextView) convertView.findViewById(R.id.cardFIO))
                    .setText(account.COL_AUTHOR);
            ((TextView) convertView.findViewById(R.id.cardPrice))
                    .setText(account.COL_PRICE + " р.");

            convertView.setId(Integer.valueOf(account.COL_ID));
            return convertView;
        }

       /* @Override
        public Filter getFilter() {
            if (filter == null){
                filter  = new AccountFilterFR1();
            }
            return filter;
        }

        private class AccountFilterFR1 extends Filter
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
                        if(account.COL_STATUS == constraint)
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
                //MainActivity.filteredAccountsFR1.clear();
                for(int i = 0, l = accountList.size(); i < l; i++){
                    add(accountList.get(i));
                    //MainActivity.filteredAccountsFR1.add(accountList.get(i));
                    //Log.d("1232312",accountList.get(i).COL_AUTHOR);
                }
                notifyDataSetInvalidated();
            }
        }*/
    }

    private class AccountAdapterFR2 extends ArrayAdapter<Account> {
        public AccountAdapterFR2 (Context context) {
            super(context, R.layout.account_listitem_2, filteredAccountsFR2);
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

            convertView.setId(Integer.valueOf(account.COL_ID));

            return convertView;
        }

        /*@Override
        public Filter getFilter() {
            if (filter == null){
                filter  = new AccountFilterFR2();
            }
            return filter;
        }

        private class AccountFilterFR2 extends Filter
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
                for(int i = 0, l = accountList.size(); i < l; i++){
                    add(accountList.get(i));
                }
                notifyDataSetInvalidated();
            }
        }*/
    }

}
