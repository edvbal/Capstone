package com.telesoftas.edvinas.onboardingedvblk.settings;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.telesoftas.edvinas.onboardingedvblk.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SourcesSpinnerAdapter extends ArrayAdapter<Source> {
    public SourcesSpinnerAdapter(
            @NonNull Context context,
            @LayoutRes int resource,
            @NonNull List<Source> sources
    ) {
        super(context, resource, sources);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    @NonNull
    private View getItemView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_spinner_sources, parent, false);
            ViewHolder viewHolder = new ViewHolder(row);
            row.setTag(viewHolder);
        }
        ViewHolder convertViewHolder = (ViewHolder) row.getTag();
        convertViewHolder.bind(getItem(position));
        return row;
    }

    static class ViewHolder {
        @BindView(R.id.spinnerItemText) TextView spinnerItemText;

        ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }

        public void bind(Source source) {
            spinnerItemText.setText(source.getName());
        }
    }
}
