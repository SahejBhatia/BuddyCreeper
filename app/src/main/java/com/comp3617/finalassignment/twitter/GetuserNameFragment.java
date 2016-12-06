package com.comp3617.finalassignment.twitter;


import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetuserNameFragment extends DialogFragment implements TextView.OnEditorActionListener {

    private GetuserNameListner listener;
    private EditText input;

    public GetuserNameFragment() {
        // Required empty public constructor
    }

    public interface GetuserNameListner {
        void onFinishEditDialog(String searchUsername);
    }

    public static GetuserNameFragment newInstance(String title){
        GetuserNameFragment nameFragment= new GetuserNameFragment();
        Bundle  args = new Bundle();
        args.putString("title",title);
        nameFragment.setArguments(args);
        return nameFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_getuser_name, container, false);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GetuserNameListner) {
            listener = (GetuserNameListner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Search By @UserName");

        //setting inpyt text box
        input = new EditText(getContext());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialogBuilder.setView(input);

        alertDialogBuilder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("fromonclick", "onEditorAction: "+ input.getText().toString());
                listener.onFinishEditDialog(input.getText().toString());

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            listener = (GetuserNameListner) getActivity();
            listener.onFinishEditDialog(input.getText().toString());
            dismiss();
            return true;
        }
        return false;
    }
}
