package com.mta.sadna19.sadna;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.mta.sadna19.sadna.MenuRegisters.DataOption;
import com.mta.sadna19.sadna.MenuRegisters.Option;

public class AddServiceDialog extends AppCompatDialogFragment {
    private FirebaseAuth mAuth;
    private EditText mServiceName, mServiceAvatar;
    private EditText mServiceGenre;
    private SwitchCompat isDataOption;
    private EditText mPass, btnReEnterPassword;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnAddService;
    private Option option;
    private DataOption dataOption;
    private ServerHandler mServerHandler;
    private OnAddServiceListener mOnAddServiceListener;
    private ServiceItem service;

    public interface OnAddServiceListener {
        void onAddServiceClicked(ServiceItem serviceItem);
    }

    public void SetOnAddServiceListener(OnAddServiceListener i_OnAddServiceListener) {
        mOnAddServiceListener = i_OnAddServiceListener;
    }

    public AddServiceDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_service_dialog, null);
        builder.setView(view);

        mServiceName = view.findViewById(R.id.etServiceName);
        mServiceAvatar = view.findViewById(R.id.etServiceAvatar);
        mServiceGenre = view.findViewById(R.id.etServiceGenre);
        btnAddService = view.findViewById(R.id.btnAddService);
        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceItem newService = createService();
                if (mOnAddServiceListener != null)
                    mOnAddServiceListener.onAddServiceClicked(newService);
                dismiss();
            }
        });
        if (getArguments()!=null)
        {
            service = getArguments().getParcelable("service");
            updateServiceData();
        }


        return builder.create();
    }

    private void updateServiceData()
    {
        mServiceName.setText(service.getM_name());
        mServiceAvatar.setText(service.getM_avatar());
        mServiceGenre.setText(service.getM_genre());
    }

    private ServiceItem createService() {
        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setM_name(mServiceName.getText().toString());
        serviceItem.setM_genre(mServiceGenre.getText().toString());

        serviceItem.setM_avatar(mServiceAvatar.getText().toString());
        if (serviceItem.getM_avatar().equals(""))
            serviceItem.setM_avatar("https://firebasestorage.googleapis.com/v0/b/dialrectly.appspot.com/o/Avatars%2F1.jpeg?alt=media&token=6425eabf-5970-4257-9855-e08b8f0f5561");
        return serviceItem;
    }
}