package com.marshalchen.ultimaterecyclerview.quickAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter;

import java.util.List;

/**
 * This is the normal adapter for implementation on the regular basis
 * Created by hesk on 4/8/15.
 */
public abstract class easyRegularAdapter<T, BINDHOLDER extends UltimateRecyclerviewViewHolder> extends UltimateViewAdapter {
    private List<T> source;


    /**
     * dynamic object to start
     *
     * @param list the list source
     */
    public easyRegularAdapter(List<T> list) {
        source = list;
    }

    /**
     * the layout id for the normal data
     *
     * @return the ID
     */
    protected abstract int getNormalLayoutResId();

    /**
     * this is the Normal View Holder initiation
     *
     * @param view view
     * @return holder
     */
    protected abstract BINDHOLDER newViewHolder(View view);

    @Override
    public UltimateRecyclerviewViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    /**
     * this MUST BE USING THE NORMAL VIEW
     *
     * @param parent view group parent
     * @return THE HOLDER
     */
    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getNormalLayoutResId(), parent, false);
        return newViewHolder(v);
    }

    @Override
    public int getAdapterItemCount() {
        return source.size();
    }

    protected T getItem(final int pos) {
        return source.get(pos);
    }

    @Override
    public long generateHeaderId(int i) {
        //    if (position < stringList.size())
        // return stringList.get(position);
        return -1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //    if (position >= getAdapterItemCount()) return;
        if (getItemViewType(position) == VIEW_TYPES.ADVIEW) {
            onBindAdViewHolder(holder, position);
        } else if (getItemViewType(position) == VIEW_TYPES.CUSTOMVIEW) {
            onBindCustomViewHolder(holder, position);
        } else if (getItemViewType(position) == VIEW_TYPES.HEADER) {
            onBindHeaderViewHolder(holder, position);
        } else if (getItemViewType(position) == VIEW_TYPES.FOOTER) {
            onBindFooterViewHolder(holder, position);
        } else if (getItemViewType(position) == VIEW_TYPES.NORMAL) {
            // if (position >= getAdapterItemCount()) return;
            withBindHolder((BINDHOLDER) holder, source.get(getItemDataPosFromInternalPos(position)), position);
        }
    }


    protected int getItemDataPosFromInternalPos(final int touch_position) {
        int shift = 0;
        if (hasHeaderView()) shift--;
        int prefinal = touch_position + shift;
        if (prefinal >= getAdapterItemCount()) {
            return 0;
        } else if (prefinal < 0) {
            return 0;
        }
        return prefinal;
    }


    /**
     * binding normal view holder
     *
     * @param holder   holder class
     * @param data     data
     * @param position position
     */
    protected abstract void withBindHolder(final BINDHOLDER holder, final T data, final int position);

    /**
     * this is the implementation from sticky viewholder interface
     *
     * @param viewHolder ViewHolder
     * @param pos        position
     */
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, final int pos) {

    }

    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, final int pos) {

    }

    protected void onBindAdViewHolder(RecyclerView.ViewHolder holder, final int pos) {

    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new UltimateRecyclerviewViewHolder(parent);
    }


    public void insert(List<T> new_data) {
        insertInternal(new_data, source);
    }

    public void removeAll() {
        clearInternal(source);
    }

    public void insertFirst(T item) {
        insertFirstInternal(source, item);
    }

    public void insertLast(T item) {
        insertLastInternal(source, item);
    }


    public final void removeAt(int pos) {
        removeInternal(source, pos);
    }

    public void removeLast() {
        removeLastInternal(source);
    }

    public void removeFirst() {
        removeFirstInternal(source);
    }

    public final void swapPositions(int from, int to) {
        swapPositions(source, from, to);
    }

    public void setStableId(boolean b) {
        if (!hasObservers()) {
            setHasStableIds(b);
        }
    }
}