package bbh.fzu.com.ybg.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

import bbh.fzu.com.ybg.Activity.Note;
import bbh.fzu.com.ybg.R;

/**
 * Created by pc on 2017/5/26.
 */

public class MyRecyclerViewAdapter extends RecyclerSwipeAdapter<MyRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Note> list;

    public MyRecyclerViewAdapter(Context context,List<Note> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        Note note = list.get(position);
        viewHolder.note_title.setText(note.getNote_title());
        viewHolder.note_content_abs.setText(note.getNote_content_abs());
        viewHolder.note_time.setText(note.getNote_time());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView note_title;
        TextView note_content_abs;
        TextView note_time;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView;
            note_title = (TextView) itemView.findViewById(R.id.note_item_title);
            note_content_abs = (TextView) itemView.findViewById(R.id.note_item_abs);
            note_time = (TextView) itemView.findViewById(R.id.note_item_time);
        }
    }
}