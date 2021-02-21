package com.zikozee.architectureexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

//    private List<Note> notes =new ArrayList<>();//TODO 21.1 delete me
    private OnItemClickListener listener; //TODO 20.2

    //TODO 21 change extension above from RecyclerView.Adapter to ListAdapter(make sure its the One for RecyclerView)
    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK =new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {//TODO 21.2 we only want to ensure the ids are the same
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {//TODO 21.3 wa want to ensure the Item not just id are the same
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        //here we do the matching for each object in recyclerview
        Note currentNote = getItem(position);//TODO 20.4 change notes.get to getItem
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

    //TODO 20.5 no longer needed as extended ListAdapter will take care of this
//    @Override
//    public int getItemCount() {
//        return notes.size();
//    }

    //TODO 20.6 we will use listAdapter's own method
//    public void setNotes(List<Note> notes){
//        this.notes = notes;
//        notifyDataSetChanged();
//    }

    //TODO STEP 19.1
    //TODO 20.7 change notes.get to getItem
    public Note getNoteAt(int position){
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            //TODO 20.3
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));//TODO 20.8 change notes.get to getItem
                }
            });
        }
    }

    //TODO 20
    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    //TODO 20.1
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
