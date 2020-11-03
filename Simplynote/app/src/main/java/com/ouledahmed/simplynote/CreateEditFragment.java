package com.ouledahmed.simplynote;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEditFragment extends Fragment {

    private FloatingActionButton addFloatingActionButton;
    private TextView newTitle ;
    private TextView newDescription ;
    private TextView newContent ;
    private CheckBox newIsImportant;
    private CheckBox newIsUrgent;
    private static final int IS_CHECKED = 1 ;
    private static final int IS_NOT_CHECKED = 0 ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment createEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEditFragment newInstance(String param1, String param2) {
        CreateEditFragment fragment = new CreateEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newTitle = getActivity().findViewById(R.id.newTitlePlainText);
        newDescription = getActivity().findViewById(R.id.newDescriptionPlainText);
        newContent = getActivity().findViewById(R.id.newContentPLainText);
        newIsImportant = getActivity().findViewById(R.id.newIsImportantChekBox);


        newIsImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newIsImportant.isChecked()){
                    newIsImportant.setTextColor(getResources().getColor(R.color.important));
                } else {
                    newIsImportant.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        newIsUrgent = getActivity().findViewById(R.id.newIsUrgentCheckBox);

        newIsUrgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newIsUrgent.isChecked()){
                    newIsUrgent.setTextColor (getResources().getColor(R.color.urgent));
                } else {
                    newIsUrgent.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });


        CreateEditFragmentArgs args = CreateEditFragmentArgs.fromBundle(getArguments());
        final int editId = args.getIdArg();
        final String editTitle = args.getTitleArg();
        final String editDescription = args.getDescriptionArg();
        final String editContent = args.getContentArg();
        final int editIsImportant = args.getIsImportantArg();
        final int editIsUrgent = args.getIsUrgentArg();


        if (editId==0){
            Toast.makeText(getContext(),"new note",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(),String.valueOf(editId),Toast.LENGTH_LONG).show();
        }


        if (editId==0) {
            newTitle.setHint(" ya jdid ya behi");
        } else {
            newTitle.setText(editTitle);
        }

        if (editId==0) {
            newDescription.setHint(editDescription);
        } else {
            newDescription.setText(editDescription);
        }

        if (editId==0){
            newContent.setHint(editContent);
        } else {
            newContent.setText(editContent);
        }

        if (editIsImportant == 0){
            newIsImportant.setChecked(false);
        } else if (editIsImportant == 1){
            newIsImportant.setChecked(true);
            newIsImportant.setTextColor(getResources().getColor(R.color.important));
        }

        if (editIsUrgent == 0){
            newIsUrgent.setChecked(false);
        } else if (editIsUrgent == 1){
            newIsUrgent.setChecked(true);
            newIsUrgent.setTextColor(getResources().getColor(R.color.urgent));
        }

        addFloatingActionButton = getActivity().findViewById(R.id.addFloatingActionButton);
        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editId == 0){
                    int isImportant = 0;
                    int isUrgent = 0;
                    if (newIsImportant.isChecked()){
                        isImportant=1;
                    }
                    if (newIsUrgent.isChecked()){
                        isUrgent = 1;
                    }
                    DatabaseHelper myDataBase = new DatabaseHelper(getContext());
                    myDataBase.addNote(newTitle.getText().toString(),newDescription.getText().toString(),newContent.getText().toString(),
                            Integer.valueOf(isImportant),Integer.valueOf(isUrgent));
                } else {
                    DatabaseHelper myDataBase = new DatabaseHelper(getContext());
                    String editedTitle = newTitle.getText().toString();
                    String editedDescription = newDescription.getText().toString();
                    String editedContent= newContent.getText().toString();

                    int editedIsImportant = 0;
                    if (newIsImportant.isChecked()){
                        editedIsImportant = 1;
                    }

                    int editedIsUrgent = 0 ;
                    if (newIsUrgent.isChecked()){
                        editedIsUrgent = 1;
                    }

                    myDataBase.updateData(String.valueOf(editId),editedTitle,editedDescription,editedContent,editedIsImportant,editedIsUrgent);
                }
            }
        });

        if (editId != 0) {
            ((MainActivity) getActivity()).setToolbarTitle(editTitle);
            Toast.makeText(getContext(),"changing title",Toast.LENGTH_LONG).show();
        }

    }
}