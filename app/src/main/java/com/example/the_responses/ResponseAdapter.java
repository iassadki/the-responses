package com.example.the_responses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder> {

    private List<ResponseItem> responseList;
    private Context context;

    public ResponseAdapter(Context context, List<ResponseItem> responseList) {
        this.context = context;
        this.responseList = responseList;
    }

    @NonNull
    @Override
    public ResponseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.response_item, parent, false);
        return new ResponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseViewHolder holder, int position) {
        ResponseItem responseItem = responseList.get(position);
        holder.tvResponseText.setText(responseItem.getResponseText());
        holder.cbSpam.setChecked(responseItem.isSpam());
        holder.cbAutoResponse.setChecked(responseItem.isAutoResponse());

        holder.cbSpam.setOnCheckedChangeListener(null);
        holder.cbAutoResponse.setOnCheckedChangeListener(null);

        holder.cbSpam.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                deselectAllExcept(position, true);
                responseItem.setSpam(true);
                responseItem.setAutoResponse(false);
                notifyDataSetChanged();
            } else {
                responseItem.setSpam(false);
            }
        });

        holder.cbAutoResponse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                deselectAllExcept(position, false);
                responseItem.setAutoResponse(true);
                responseItem.setSpam(false);
                notifyDataSetChanged();
            } else {
                responseItem.setAutoResponse(false);
            }
        });
    }

    private void deselectAllExcept(int position, boolean isSpam) {
        for (int i = 0; i < responseList.size(); i++) {
            if (i != position) {
                ResponseItem item = responseList.get(i);
                if (isSpam) {
                    item.setSpam(false);
                } else {
                    item.setAutoResponse(false);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public static class ResponseViewHolder extends RecyclerView.ViewHolder {
        TextView tvResponseText;
        CheckBox cbSpam;
        CheckBox cbAutoResponse;

        public ResponseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvResponseText = itemView.findViewById(R.id.tvResponseText);
            cbSpam = itemView.findViewById(R.id.cbSpam);
            cbAutoResponse = itemView.findViewById(R.id.cbAutoResponse);
        }
    }
}
