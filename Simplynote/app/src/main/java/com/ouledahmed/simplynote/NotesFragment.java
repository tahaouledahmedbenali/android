package com.ouledahmed.simplynote;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private SharedNotesReadViewModel sharedNotesReadViewModel;

    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;
    private ArrayList<String> note_id,note_title,note_description,note_content;
    private ArrayList<Integer> note_is_important,note_is_urgent;
    private NotesAdapter notesAdapter;
    NavController navController;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
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
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        floatingActionButton = getActivity().findViewById(R.id.floatingHome);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_notesFragment_to_createEditFragment);
            }
        });

        dbHelper = new DatabaseHelper(getContext());
        note_id = new ArrayList<>();
        note_title = new ArrayList<>();
        note_description = new ArrayList<>();
        note_content = new ArrayList<>();
        note_is_important = new ArrayList<>();
        note_is_urgent = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.recyclerNotes);

        storeDataInArrays();
        notesAdapter = new NotesAdapter(getContext(),note_id,note_title,note_description,note_content,note_is_important,note_is_urgent);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        sharedNotesReadViewModel = new ViewModelProvider(requireActivity()).get(SharedNotesReadViewModel.class);
        sharedNotesReadViewModel.getRequested().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer.equals(0)){
                    showAllData();
                } else if (integer.equals(1)){
                    showImportant();
                } else if (integer.equals(2)){
                    showUrgent();
                }
            }
        });


    }

    private void storeDataInArrays() {
        Cursor cursor = dbHelper.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(),"NO DATA",Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_title.add(cursor.getString(1));
                note_description.add(cursor.getString(2));
                note_content.add(cursor.getString(3));
                note_is_important.add(cursor.getInt(4));
                note_is_urgent.add(cursor.getInt(5));
            }
        }
    }

    void clearData(){
        note_id.clear();
        note_title.clear();
        note_description.clear();
        note_content.clear();
        note_is_important.clear();
        note_is_urgent.clear();
    }

    void storeImportantData (){
        Cursor cursor = dbHelper.readImporatantData();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(),"NO IMPORTANT NOTES",Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_title.add(cursor.getString(1));
                note_description.add(cursor.getString(2));
                note_content.add(cursor.getString(3));
                note_is_important.add(cursor.getInt(4));
                note_is_urgent.add(cursor.getInt(5));
            }
        }
    }

    void storeUrgentData (){
        Cursor cursor = dbHelper.readUrgentData();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(),"NO IMPORTANT NOTES",Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_title.add(cursor.getString(1));
                note_description.add(cursor.getString(2));
                note_content.add(cursor.getString(3));
                note_is_important.add(cursor.getInt(4));
                note_is_urgent.add(cursor.getInt(5));
            }
        }
    }

    public void showImportant(){
        clearData();
        storeImportantData();
        notesAdapter.notifyDataSetChanged();
    }

    public void showUrgent(){
        clearData();
        storeUrgentData();
        notesAdapter.notifyDataSetChanged();
    }

    public void showAllData(){
        clearData();
        storeDataInArrays();
        notesAdapter.notifyDataSetChanged();
    }

}