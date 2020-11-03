package com.ouledahmed.simplynote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

public class DeleteDialog extends AppCompatDialogFragment {

    private String id;

    public DeleteDialog(String id){
        this.id = id;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Note")
                .setMessage("Do you want to delete this note")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                        dbHelper.deleteOneRow(id);
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.notesFragment);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
