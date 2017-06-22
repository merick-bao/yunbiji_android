package bbh.fzu.com.ybg.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bbh.fzu.com.ybg.Activity.Note;
import bbh.fzu.com.ybg.R;

/**
 * Created by pc on 2017/5/24.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{

    private static final String TAG = "NoteAdapter";

    private Context mContext;

    private List<Note> mNoteList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView note_title;
        TextView note_content_abs;
        TextView note_time;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView;
            note_title = (TextView) itemView.findViewById(R.id.note_item_title);
            note_content_abs = (TextView) itemView.findViewById(R.id.note_item_abs);
            note_time = (TextView) itemView.findViewById(R.id.note_item_time);
        }
    }

    public NoteAdapter(List<Note> noteList){//构造方法
        mNoteList = noteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.note_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mNoteList.get(position);
        holder.note_title.setText(note.getNote_title());
        holder.note_content_abs.setText(note.getNote_content_abs());
        holder.note_time.setText(note.getNote_time());

    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
