package zaofond.accounts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class PriceActivity extends FragmentActivity {

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        position = (Integer) getIntent().getExtras().get("position");

        TextView tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        TextView tvKontr = (TextView) findViewById(R.id.tvKontr);
        TextView tvPrice = (TextView) findViewById(R.id.tvPrice);

        tvAuthor.setText(Account.accounts.get(position).COL_AUTHOR);
        tvKontr.setText(Account.accounts.get(position).COL_KONTR);
        tvPrice.setText(Account.accounts.get(position).COL_PRICE);

        File imgFile = new File(Environment.getExternalStorageDirectory().toString()+File.separator+"test.jpg");
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ZoomableImageView myImage = (ZoomableImageView) findViewById(R.id.ivTest);
            myImage.setImageBitmap(myBitmap);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.price, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_like) {
            Account acc;
            acc = Account.accounts.get(position);
            acc.likeAccount();
            return true;
        }

        if (id == R.id.action_unlike) {
            Account acc;
            acc = Account.accounts.get(position);
            acc.unlikeAccount();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
