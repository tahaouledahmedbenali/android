package com.ouledahmed.simplynote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchNotesAdapter extends RecyclerView.Adapter<SearchNotesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList note_id, note_title, note_description, note_content, note_is_important, note_is_urgent;

    int position;

    public SearchNotesAdapter(Context context, ArrayList note_id, ArrayList note_title, ArrayList note_description,
                        ArrayList note_content, ArrayList note_is_important, ArrayList note_is_urgent) {
        this.context = context;
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_description = note_description;
        this.note_content = note_content;
        this.note_is_important = note_is_important;
        this.note_is_urgent = note_is_urgent;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.note_row,parent,false);
        return new SearchNotesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        this.position = position;
        holder.noteTitle.setText(String.valueOf(note_title.get(position)));
        holder.noteDescription.setText(String.valueOf(note_description.get(position)));
        holder.noteContent.setText(String.valueOf(note_content.get(position)));

        if (String.valueOf(note_is_important.get(position)).equals("0")){
            holder.noteIsImportant.setVisibility(View.INVISIBLE);
        } else if (String.valueOf(note_is_important.get(position)).equals("1")){
            holder.noteIsImportant.setVisibility(View.VISIBLE);
        }

        if (String.valueOf(note_is_urgent.get(position)).equals("0")){
            holder.noteIsUrgent.setVisibility(View.INVISIBLE);
        } else if (String.valueOf(note_is_urgent.get(position)).equals("1")){
            holder.noteIsUrgent.setVisibility(View.VISIBLE);
        }



        //passing data to show note residing in the card clicked
        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                SearchFragmentDirections.ActionSearchFragment2ToReadFragment actionSearchFragment2ToReadFragment =
                        SearchFragmentDirections.actionSearchFragment2ToReadFragment();

                String id = String.valueOf(note_id.get(position));
                actionSearchFragment2ToReadFragment.setIdArg(Integer.valueOf(id));

                actionSearchFragment2ToReadFragment.setTitleArg(String.valueOf(note_title.get(position)));
                actionSearchFragment2ToReadFragment.setDescriptionArg(String.valueOf(note_description.get(position)));
                actionSearchFragment2ToReadFragment.setContentArg(String.valueOf(note_content.get(position)));

                if (String.valueOf(note_is_important.get(position)).equals("0")){
                    actionSearchFragment2ToReadFragment.setIsImportantArg(0);
                } else if (String.valueOf(note_is_important.get(position)).equals("1")){
                    actionSearchFragment2ToReadFragment.setIsImportantArg(1);
                }

                if (String.valueOf(note_is_urgent.get(position)).equals("0")){
                    actionSearchFragment2ToReadFragment.setIsUrgentArg(0);
                } else if (String.valueOf(note_is_urgent.get(position)).equals("1")){
                    actionSearchFragment2ToReadFragment.setIsUrgentArg(1);
                }

                Navigation.findNavController(view).navigate(actionSearchFragment2ToReadFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return note_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle, noteDescription, noteContent;
        ImageView noteIsImportant,noteIsUrgent;
        LinearLayout cardLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_row_title);
            noteDescription = itemView.findViewById(R.id.note_row_description);
            noteContent = itemView.findViewById(R.id.note_row_content);
            noteIsImportant = itemView.findViewById(R.id.note_row_is_important);
            noteIsUrgent = itemView.findViewById(R.id.note_row_is_urgent);
            cardLayout = itemView.findViewById(R.id.rootCard);
        }
    }
}
