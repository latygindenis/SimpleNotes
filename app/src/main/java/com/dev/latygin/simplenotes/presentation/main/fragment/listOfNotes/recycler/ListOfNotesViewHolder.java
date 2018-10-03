package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.recycler;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dev.latygin.simplenotes.R;

class ListOfNotesViewHolder extends RecyclerView.ViewHolder {

    CardView cardNote;
    TextView titleNote;
    TextView contentNote;

    ListOfNotesViewHolder(View itemView) {
        super(itemView);
        cardNote = itemView.findViewById(R.id.noteCard);
        titleNote = itemView.findViewById(R.id.noteTitle);
        contentNote = itemView.findViewById(R.id.noteContent);
    }

}
