package com.mta.sadna19.sadna;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mta.sadna19.sadna.MenuRegisters.DataOption;
import com.mta.sadna19.sadna.MenuRegisters.Option;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddOptionDialog extends AppCompatDialogFragment {
    private FirebaseAuth mAuth;
    private EditText mName, mKeys;
    private EditText mPostKeys,mDataType;
    private SwitchCompat isDataOption;
    private EditText mPass, btnReEnterPassword;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnAddOption;
    private Option option;
    private DataOption dataOption;
    private ServerHandler mServerHandler;
    private OnEditOptionListener mOnEditOptionListener;
    private Option opt;
    public interface OnEditOptionListener{
        void onEditOptionClicked(Option option);
    }

    public void SetOnEditOptionListener(OnEditOptionListener i_OnEditOptionListener)
    {
        mOnEditOptionListener = i_OnEditOptionListener;
    }
    public AddOptionDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_option_dialog, null);
        builder.setView(view);
        if (getArguments()!=null)
            opt = getArguments().getParcelable("option");


        mName = view.findViewById(R.id.etAddName);
        mKeys = view.findViewById(R.id.etKeys);
        mPostKeys = view.findViewById(R.id.etPostKeys);
        mDataType = view.findViewById(R.id.etDataType);
        btnAddOption = view.findViewById(R.id.btnAddOption);
        isDataOption = view.findViewById(R.id.switchDataOption);
        btnAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDataOption.isChecked())
                {
                    option = new DataOption(mName.getText().toString(),mDataType.getText().toString(),"%23");
                }
                else{
                    option = new Option();
                    option.setKeys(mKeys.getText().toString());
                    option.setName(mName.getText().toString());
                    option.setPostKeys(mPostKeys.getText().toString());
                }
                if (opt!=null)
                    option.set_SubMenu((ArrayList<Option>) opt.getSubMenu());

                    if (true)
                {
                    //no problem in option
                    if (mOnEditOptionListener!=null)
                        mOnEditOptionListener.onEditOptionClicked(option);
                    dismiss();

                }
            }
        });
        isDataOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDataOption.isChecked())
                    whenSwitchChecked();
                else
                    whenSwitchNotChecked();

            }
        });
        if (opt!=null)
        {

            Log.e("TEST ",""+opt.toString());
            updateOptionInfo(opt);

        }

        return builder.create();
    }

    private void whenSwitchChecked()
    {
        mDataType.setVisibility(View.VISIBLE);
    }
    private void whenSwitchNotChecked()
    {
        mDataType.setVisibility(View.INVISIBLE);

    }
    private void updateOptionInfo(Option i_option)
    {
        if (i_option instanceof DataOption)
        {
            mDataType.setText(((DataOption) i_option).getDataType());
        }
        mName.setText(i_option.getName());
        mKeys.setText(i_option.getKeys());
        mPostKeys.setText(i_option.getPostKeys());

    }





}