package com.ouledahmed.simplynote;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private String searchtext;
    private TextView searchTextView;
    private RecyclerView recyclerView;
    private DatabaseHelper dbHelper;
    private ArrayList<String> note_id,note_title,note_description,note_content;
    private ArrayList<Integer> note_is_important,note_is_urgent;
    private ArrayList<String> note_id_filtered,note_title_filtered,note_description_filtered,note_content_filtered;
    private ArrayList<Integer> note_is_important_filtered,note_is_urgent_filtered;
    private SearchNotesAdapter notesAdapter;
    NavController navController;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchTextView = getView().findViewById(R.id.searchText);
        dbHelper = new DatabaseHelper(getContext());
        note_id = new ArrayList<>();
        note_title = new ArrayList<>();
        note_description = new ArrayList<>();
        note_content = new ArrayList<>();
        note_is_important = new ArrayList<>();
        note_is_urgent = new ArrayList<>();
        note_id_filtered = new ArrayList<>();
        note_title_filtered = new ArrayList<>();
        note_description_filtered = new ArrayList<>();
        note_content_filtered = new ArrayList<>();
        note_is_important_filtered = new ArrayList<>();
        note_is_urgent_filtered = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.searchedRecycler);
        storeDataInArrays();
        ImageButton searchButton =  getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
                notesAdapter = new SearchNotesAdapter(getContext(),note_id_filtered,note_title_filtered,note_description_filtered
                        ,note_content_filtered,note_is_important_filtered,note_is_urgent_filtered);
                recyclerView.setAdapter(notesAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

    private void search(){
        if (note_id_filtered != null && note_id_filtered.size() !=0) {
            clearFilteredData();
        }
        searchtext = searchTextView.getText().toString().toLowerCase().trim();
        int i = 0;
        String test;
        while (i<note_title.size()){
            test = note_title.get(i);
            if (test.toLowerCase().contains(searchtext)){
                note_id_filtered.add(note_id.get(i));
                note_title_filtered.add(note_title.get(i));
                note_description_filtered.add(note_description.get(i));
                note_content_filtered.add(note_content.get(i));
                note_is_important_filtered.add(note_is_important.get(i));
                note_is_urgent_filtered.add(note_is_urgent.get(i));
            }
            i++;
        }
    }

    void clearFilteredData(){
        note_id_filtered.clear();
        note_title_filtered.clear();
        note_description_filtered.clear();
        note_content_filtered.clear();
        note_is_important_filtered.clear();
        note_is_urgent_filtered.clear();
    }



}