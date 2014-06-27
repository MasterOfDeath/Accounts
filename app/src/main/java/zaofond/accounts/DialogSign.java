package zaofond.accounts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by rinat on 25.06.14.
 */
public class DialogSign extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_sign, null);

        String title = this.getArguments().getString("title");
        int hint = this.getArguments().getInt("hint");
        final int ID = this.getArguments().getInt("ID");
        final String status = this.getArguments().getString("status");

        final EditText etDialog = (EditText)view.findViewById(R.id.etDialog);
        etDialog.setHint(hint);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String comment = etDialog.getText().toString();

                        Account acc;
                        acc = Account.getAccountByID(ID);

                        if (status == "like") {
                            if (comment.isEmpty()) comment = getString(R.string.to_work);
                            acc.likeAccount(comment);
                            PriceActivity.h.sendEmptyMessage(1);
                        }
                        if (status == "unlike") {
                            if (comment.isEmpty()) comment = getString(R.string.come_in);
                            acc.unlikeAccount(comment);
                            PriceActivity.h.sendEmptyMessage(2);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .setTitle(title);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
