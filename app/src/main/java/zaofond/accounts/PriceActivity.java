package zaofond.accounts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.File;

public class PriceActivity extends FragmentActivity{

    private static int ID = 0;
    private TextView tvAuthor;
    private TextView tvKontr;
    private TextView tvPrice;
    public static Account curAccount;
    public static Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        ID = (Integer) getIntent().getExtras().get("ID");

        curAccount = Account.getAccountByID(ID);

        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvKontr = (TextView) findViewById(R.id.tvKontr);
        tvPrice = (TextView) findViewById(R.id.tvPrice);

        refreshUI(curAccount);

        File imgFile = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"test.jpg");
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ZoomableImageView myImage = (ZoomableImageView) findViewById(R.id.ivTest);
            myImage.setImageBitmap(myBitmap);
        }

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 1) nextAccountToUI();
                if (msg.what == 2) nextAccountToUI();
            };
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.price, menu);

        boolean showButtons = (Boolean) getIntent().getExtras().get("showButtons");
        showStatusButtons(menu,showButtons);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_like) {
            /*Account acc;
            acc = Account.accounts.get(position);
            acc.likeAccount();*/

            DialogSign dSign = new DialogSign();
            Bundle args = new Bundle();
            args.putString("title", getString(R.string.title_dialog_like));
            args.putInt("hint", R.string.to_work);
            args.putInt("ID",ID);
            args.putString("status", "like");
            dSign.setArguments(args);
            dSign.show(getFragmentManager(), "dSign");

            return true;
        }

        if (id == R.id.action_unlike) {
            DialogSign dSign = new DialogSign();
            Bundle args = new Bundle();
            args.putString("title", getString(R.string.title_dialog_unlike));
            args.putInt("hint",R.string.come_in);
            args.putInt("ID",ID);
            args.putString("status", "unlike");
            dSign.setArguments(args);
            dSign.show(getFragmentManager(), "dSign");

            /*Account acc;
            acc = Account.nextAccount(ID,MainActivity.filteredAccountsFR1);
            Log.d("111111","След. "+acc.COL_AUTHOR+"ID "+ID);*/

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void showStatusButtons(Menu menu, boolean show){
        menu.findItem(R.id.action_like).setVisible(show);
        menu.findItem(R.id.action_unlike).setVisible(show);
    }

    private void refreshUI(Account account){
        tvAuthor.setText(account.COL_AUTHOR);
        tvKontr.setText(account.COL_KONTR);
        tvPrice.setText(account.COL_PRICE);
    }

    public void nextAccountToUI(){
        curAccount = Account.nextAccount(ID,MainActivity.filteredAccountsFR1);
        if (curAccount != null){
            MainActivity.filteredAccountsFR1 = Account.filterAccountFR1(Account.accounts,"0");
            ID = Integer.valueOf(curAccount.COL_ID);

            MainActivity.refreshAdapter(MainActivity.adapterFR1,MainActivity.filteredAccountsFR1);

            refreshUI(curAccount);
        }
        else {
            MainActivity.filteredAccountsFR1 = Account.filterAccountFR1(Account.accounts,"0");
            MainActivity.refreshAdapter(MainActivity.adapterFR1, MainActivity.filteredAccountsFR1);
            finish();
        }
    }

}
