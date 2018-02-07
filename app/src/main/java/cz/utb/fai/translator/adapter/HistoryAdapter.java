package cz.utb.fai.translator.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.utb.fai.translator.R;

/**
 * Created by student on 09.12.2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private List<String> mDataset;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextRow.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    //reference to row template
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextRow;

        public ViewHolder(View view){
            super(view);
            mTextRow = (TextView) view.findViewById(R.id.textView);
        }
    }
    public HistoryAdapter(List<String>myDataset){
        mDataset=myDataset;

    }
}
