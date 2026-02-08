package com.example.blipplyprototype.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.blipplyprototype.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProfileAccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_account, container, false);

        TextView name = view.findViewById(R.id.textAccountName);
        TextView email = view.findViewById(R.id.textAccountEmail);
        TextView phone = view.findViewById(R.id.textAccountPhone);
        Button editButton = view.findViewById(R.id.buttonEditAccount);

        editButton.setOnClickListener(v -> showEditDialog(name, email, phone));

        return view;
    }

    private void showEditDialog(TextView name, TextView email, TextView phone) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.dialog_edit_account, null);

        EditText inputName = dialogView.findViewById(R.id.inputEditName);
        EditText inputEmail = dialogView.findViewById(R.id.inputEditEmail);
        EditText inputPhone = dialogView.findViewById(R.id.inputEditPhone);

        inputName.setText(name.getText());
        inputEmail.setText(email.getText());
        inputPhone.setText(phone.getText());

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Edit Account")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    name.setText(inputName.getText().toString().trim());
                    email.setText(inputEmail.getText().toString().trim());
                    phone.setText(inputPhone.getText().toString().trim());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
