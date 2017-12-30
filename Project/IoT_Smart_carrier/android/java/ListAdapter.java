package com.example.skcho.smartcarrier;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.sql.SQLException;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    AlertDialog.Builder alertDialogBuilder;
    List<Memo> datas;
    DetailInterface detailInterface;

    public ListAdapter(Context context, List<Memo> datas) {
        detailInterface = (DetailInterface) context;
        this.context = context;
        this.datas = datas;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("메모 삭제")
                .setMessage("메모를 지우시겠습니까?")
                .setCancelable(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Memo memo = datas.get(position);
        holder.textView.setText(memo.getMemo());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
            textView.setOnClickListener(this);
            textView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            alertDialogBuilder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete(getAdapterPosition());
                        }
                    })
                    .setNegativeButton("아니요",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog dialog = alertDialogBuilder.create();
            dialog.show();
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }

        public void delete(int position) {
            try {
                detailInterface.deleteFromList(datas.get(position));
                datas.remove(position);
                notifyItemRemoved(position);
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}