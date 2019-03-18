package newsdaily.com.newsdaily.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import newsdaily.com.newsdaily.R;
import newsdaily.com.newsdaily.models.Article;
import newsdaily.com.newsdaily.models.News;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<Article> data;
    private Context context;

    public HomeAdapter(Context context, List<Article> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
        this.context = context;
    }


    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        holder.click(data.get(position), listener);
        holder.card_view_image_title.setText(data.get(position).getTitle());

        String images = data.get(position).getUrlToImage();

        Glide.with(context)
                .load(images)
                .into(holder.card_view_image);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnItemClickListener {
        void onClick(Article Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView card_view_image_title;
        ImageView card_view_image;

        public ViewHolder(View itemView) {
            super(itemView);

            card_view_image_title = itemView.findViewById(R.id.card_view_image_title);
            card_view_image = itemView.findViewById(R.id.card_view_image);

        }


        public void click(final Article articleListData, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(articleListData);
                }
            });
        }
    }


}
