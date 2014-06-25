/**
 * Created by rinat on 17.06.14.
 */
package zaofond.accounts;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment_1 extends Fragment {



    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    public static ArrayAdapter adapterFR1;
    public static ListView lvFR1;
    private ArrayList<Account> copyAccounts;

    public static PlaceholderFragment_1 newInstance(int sectionNumber) {
        PlaceholderFragment_1 fragment = new PlaceholderFragment_1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment_1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_1, container, false);

        copyAccounts = new ArrayList<Account>();
        copyAccounts.addAll(Account.accounts);

        adapterFR1 = new AccountAdapter(getActivity());

        lvFR1 = (ListView)rootView.findViewById(R.id.lvFR1);
        lvFR1.setAdapter(adapterFR1);
        lvFR1.setClickable(true);
        lvFR1.setTextFilterEnabled(true);
        lvFR1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PriceActivity.class);
                intent.putExtra("ID", view.getId());
                startActivity(intent);
                //int ID = view.getId();
                //Toast.makeText(getActivity(),Integer.toString(position),Toast.LENGTH_SHORT).show();
                //Log.d("My", "itemClick: position = " + position + ", ID = " + ID);
            }
        });

        adapterFR1.getFilter().filter("0");


        /*final String[] outPut = {null};

        final ZoomableImageView ivTest = (ZoomableImageView) rootView.findViewById(R.id.ivTest);
        final TextView tvTest = (TextView) rootView.findViewById(R.id.tvTest);
        Button testButton1 = (Button) rootView.findViewById(R.id.testButton1);
        testButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                *//*connect.put("URL","http://192.168.10.219/upp1/ws/Output");
                connect.put("NAMESPACE","http://zaofond72.ru/");
                connect.put("METHOD_NAME","OutputStr");
                connect.put("USERNAME","121212");
                connect.put("PASSWORD","111");*//*
                connect.put("URL","http://192.168.10.219/upp1/ws/Output");
                connect.put("NAMESPACE","http://zaofond72.ru/");
                connect.put("METHOD_NAME","OutputScr");
                connect.put("USERNAME","121212");
                connect.put("PASSWORD","111");
                connect.put("NUMBER","00000000001");
                connect.put("DATE","20140616000000");

                TaskOutputStr taskOutputStr = new TaskOutputStr();
                taskOutputStr.execute(connect);

                try {
                    outPut[0] = taskOutputStr.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                //tvTest.setText(outPut[0]);
                Bitmap picture = decodeBase64(outPut[0]);
                if (picture != null) {
                    ivTest.setImageBitmap(picture);
                }
            }
        });*/


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private class TaskOutputStr extends AsyncTask<Map<String,String>, Integer, String> {
        protected String doInBackground(Map<String,String> ... con) {

            String ret = null;

            SoapObject so = new SoapObject(con[0].get("NAMESPACE"), con[0].get("METHOD_NAME"));
            if (con[0].containsKey("NUMBER")) {
                so.addProperty("Number", con[0].get("NUMBER"));
            }
            if (con[0].containsKey("DATE")) {
                so.addProperty("Date", con[0].get("DATE"));
            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(so);

            StringBuffer auth = new StringBuffer(con[0].get("USERNAME"));
            auth.append(':').append(con[0].get("PASSWORD"));
            byte[] raw = auth.toString().getBytes();
            auth.setLength(0);
            auth.append("Basic ");
            org.kobjects.base64.Base64.encode(raw, 0, raw.length, auth);
            List headers = new ArrayList();
            headers.add(new HeaderProperty("Authorization", auth.toString()));

            //
            HttpTransportSE httpTransport = null;
            try {
                httpTransport = new HttpTransportSE(con[0].get("URL"));
                httpTransport.setXmlVersionTag("");
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                httpTransport.call("http://zaofond72.ru/#Output:OutputScr", envelope,headers);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    throw e;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            //SoapFault error = (SoapFault)envelope.bodyIn;
            //System.out.println("Error message : "+error.toString());

            try {
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                ret = resultsRequestSOAP.getProperty("return").toString();
            } catch (Exception e){
                e.printStackTrace();
            }


            return ret;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            //showDialog("Downloaded " + result + " bytes");
        }
    }

    public static Bitmap decodeBase64(String input)
    {
        if (input == null) {return null;}
        byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private class AccountAdapter extends ArrayAdapter<Account> {

        //private ArrayList<Account> originalList;
        private ArrayList<Account> accountList;
        private AccountFilter filter;

        public AccountAdapter(Context context) {
            super(context, R.layout.account_listitem_1, copyAccounts);
            this.accountList = new ArrayList<Account>();
            //this.accountList.add(new Account("1", "Пензин Антон","Компания Мастер","1200","","0"));
            //System.arraycopy(Account.accounts,0,this.accountList,0,5);
            //Account.accounts.toArray().clone();
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
                        .inflate(R.layout.account_listitem_1, null);
            }
            ((TextView) convertView.findViewById(R.id.cardFIO))
                    .setText(account.COL_AUTHOR);
            ((TextView) convertView.findViewById(R.id.cardPrice))
                    .setText(account.COL_PRICE + " р.");

            convertView.setId(Integer.valueOf(account.COL_ID));
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
                for(int i = 0, l = accountList.size(); i < l; i++)
                    add(accountList.get(i));
                notifyDataSetInvalidated();
            }
        }
    }



}
