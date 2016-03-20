package com.bzh.dytt.ui.adapter.film;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bzh.data.film.entity.FilmEntity;
import com.bzh.dytt.R;
import com.bzh.dytt.ui.activity.base.BaseActivity;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-20<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class NewestFilmAdapter extends RecyclerView.Adapter<NewestFilmAdapter.NewestFilmViewHolder> {

    private ArrayList<FilmEntity> filmEntities;

    private BaseActivity baseActivity;

    private OnRecyclerItemClickListener listener;

    public NewestFilmAdapter(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        filmEntities = new ArrayList<>();
    }

    public void setFilmEntities(ArrayList<FilmEntity> filmEntities) {
        this.filmEntities.clear();
        this.filmEntities.addAll(filmEntities);
        this.notifyDataSetChanged();
    }

    @Override
    public NewestFilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(baseActivity).inflate(R.layout.item_newestfilm, parent, false);
        return new NewestFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewestFilmViewHolder holder, final int position) {
        final FilmEntity filmEntity = filmEntities.get(position);
        holder.tv_film_name.setText(filmEntity.getName());
        holder.tv_film_leading_players.setText(filmEntity.getUrl());
        holder.setOnRippleClickListener(new NewestFilmViewHolder.OnRippleClick() {
            @Override
            public void onRippleClick(View view) {
                if (listener != null) {
                    listener.onRecyclerItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmEntities.size();
    }

    public void setOnRecyclerItemClick(OnRecyclerItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(View view, int position);
    }

    public static class NewestFilmViewHolder extends RecyclerView.ViewHolder {

        TextView tv_film_name;

        TextView tv_film_leading_players;

        private OnRippleClick onRippleClick;

        public NewestFilmViewHolder(final View itemView) {
            super(itemView);
            tv_film_name = (TextView) itemView.findViewById(R.id.tv_film_name);
            tv_film_leading_players = (TextView) itemView.findViewById(R.id.tv_film_leading_players);
            RxView
                    .clicks(itemView)
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            onRippleClick.onRippleClick(itemView);
                        }
                    });
        }

        public void setOnRippleClickListener(OnRippleClick onRippleClick) {
            this.onRippleClick = onRippleClick;
        }

        public interface OnRippleClick {
            void onRippleClick(View view);
        }
    }
}
