package com.ouledahmed.simplynote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadFragment extends Fragment {

    private String noteId;

    private FloatingActionButton floatingActionButton;
    private TextView oldTitle;
    private TextView oldDescription;
    private TextView oldContent;
    private ImageView isImportant;
    private ImageView isUrgent;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReadFragment() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment readFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadFragment newInstance(String param1, String param2) {
        ReadFragment fragment = new ReadFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        floatingActionButton = getActivity().findViewById(R.id.floatingOldActionButton);
        ReadFragmentArgs args = ReadFragmentArgs.fromBundle(getArguments());
        ((MainActivity)getActivity()).setToolbarTitle(args.getTitleArg());

    }

    @Override
    public void onStart() {
        super.onStart();
        oldTitle = getActivity().findViewById(R.id.textOldTitle);
        oldDescription = getActivity().findViewById(R.id.textOldDescription);
        oldContent = getActivity().findViewById(R.id.textOldContent);
        isImportant = getActivity().findViewById(R.id.imageViewImportant);
        isUrgent = getActivity().findViewById(R.id.imageViewUrgent);
        ReadFragmentArgs args = ReadFragmentArgs.fromBundle(getArguments());
        final int receivedId = args.getIdArg();
        noteId = String.valueOf(receivedId);
        final String receivedTitle = args.getTitleArg();
        final String receivedDescription = args.getDescriptionArg();
        final String receivedContent = args.getContentArg();
        final int receivedIsImportant = args.getIsImportantArg();
        final int receivedIsUrgent = args.getIsUrgentArg();

        oldTitle.setText(receivedTitle);
        oldDescription.setText(receivedDescription);
        oldContent.setText(receivedContent);

        if (receivedIsImportant == 0){
            isImportant.setVisibility(View.INVISIBLE);
        } else if(receivedIsImportant == 1){
            isImportant.setVisibility(View.VISIBLE);
        }

        if (receivedIsUrgent == 0){
            isUrgent.setVisibility(View.INVISIBLE);
        } else if(receivedIsUrgent == 1){
            isUrgent.setVisibility(View.VISIBLE);
        }

        //edit floating button

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               ReadFragmentDirections.ActionReadFragmentToCreateEditFragment actionReadFragmentToCreateEditFragment =
                       ReadFragmentDirections.actionReadFragmentToCreateEditFragment();

                actionReadFragmentToCreateEditFragment.setIdArg(Integer.valueOf(receivedId));
                actionReadFragmentToCreateEditFragment.setTitleArg(String.valueOf(receivedTitle));
                actionReadFragmentToCreateEditFragment.setDescriptionArg(String.valueOf(receivedDescription));
                actionReadFragmentToCreateEditFragment.setContentArg(String.valueOf(receivedContent));
                actionReadFragmentToCreateEditFragment.setIsImportantArg(Integer.valueOf(receivedIsImportant));
                actionReadFragmentToCreateEditFragment.setIsUrgentArg(Integer.valueOf(receivedIsUrgent));
                Navigation.findNavController(view).navigate(actionReadFragmentToCreateEditFragment);
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.notes_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteItem){
            openDialog(noteId);
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog(String id){
        DeleteDialog deleteDialog = new DeleteDialog(id);
        deleteDialog.show(getActivity().getSupportFragmentManager(),"delete dialog");
    }

}