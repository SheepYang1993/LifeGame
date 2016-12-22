package me.sheepyang.lifegame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import me.sheepyang.lifegame.R;

/**
 * Created by SheepYang on 2016/11/24 21:11.
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mDatas;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public SettingAdapter(Context context, List<String> datas) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_setting, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String data = mDatas.get(position);
//        holder.tvText.setText(data.getText());
//        holder.tvDesc.setText(data.getDesc());
//        if (!TextUtils.isEmpty(data.getIntentClass())) {
//            holder.ivArrow.setVisibility(View.VISIBLE);
//        } else {
//            holder.ivArrow.setVisibility(View.INVISIBLE);
//        }

        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void updata(List<String> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_text)
//        TextView tvText;
//        @BindView(R.id.tv_desc)
//        TextView tvDesc;
//        @BindView(R.id.iv_arrow)
//        ImageView ivArrow;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}