package com.devbrackets.android.recyclerextdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devbrackets.android.recyclerext.adapter.RecyclerHeaderAdapter;
import com.devbrackets.android.recyclerext.decoration.StickyHeaderDecoration;
import com.devbrackets.android.recyclerextdemo.database.DBHelper;
import com.devbrackets.android.recyclerextdemo.database.ItemDAO;
import com.devbrackets.android.recyclerextdemo.viewholder.SimpleTextViewHolder;

import java.util.List;


/**
 * An example of the {@link com.devbrackets.android.recyclerextdemo.HeaderListFragment.HeaderAdapter}
 * and using the {@link StickyHeaderDecoration} to keep the header at the top of the screen when reached.
 */
public class HeaderListFragment extends Fragment {
    private DBHelper dbHelper;
    private RecyclerView recyclerView;

    public static HeaderListFragment newInstance() {
        return new HeaderListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerext_fragment_recycler);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DBHelper(getActivity());
        setupRecyclerExt();
    }

    private void setupRecyclerExt() {
        HeaderAdapter adapter = new HeaderAdapter(getActivity(), ItemDAO.findAll(dbHelper.getWritableDatabase()));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new StickyHeaderDecoration(recyclerView));
    }


















    private class HeaderAdapter extends RecyclerHeaderAdapter<SimpleTextViewHolder, SimpleTextViewHolder> {
        private LayoutInflater inflater;
        private List<ItemDAO> items;

        public HeaderAdapter(Context context, List<ItemDAO> items) {
            this.items = items;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public SimpleTextViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = inflater.inflate(R.layout.simple_text_item, null);
            return new SimpleTextViewHolder(view);
        }

        @Override
        public SimpleTextViewHolder onCreateChildViewHolder(ViewGroup parent) {
            View view = inflater.inflate(R.layout.simple_text_item, null);
            return new SimpleTextViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(SimpleTextViewHolder holder, int childPosition) {
            holder.setText(getHeaderId(childPosition) + "0s");
            holder.setBackgroundColor(0xFFCCCCCC);
        }

        @Override
        public void onBindChildViewHolder(SimpleTextViewHolder holder, int childPosition) {
            holder.setText(items.get(childPosition).getText());
        }

        @Override
        public int getChildCount() {
            return items.size();
        }

        @Override
        public long getHeaderId(int childPosition) {
            return items.get(childPosition).getOrder() / 10;
        }
    }
}
