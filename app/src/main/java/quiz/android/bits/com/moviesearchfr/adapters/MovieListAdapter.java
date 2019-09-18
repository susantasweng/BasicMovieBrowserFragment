package quiz.android.bits.com.moviesearchfr.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import quiz.android.bits.com.moviesearchfr.R;

public class MovieListAdapter extends ArrayAdapter {

    private Activity context;
    private String[] names;

    public MovieListAdapter (Activity context, String[] names ) {
        super(context, R.layout.list_row_layout, names);
        this.context = context;
        this.names = names;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if(convertView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.list_row_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.labelView = (TextView) view.findViewById(R.id.list_item_textview);
            //viewHolder.imageView = (ImageView) view.findViewById(R.id.list_item_imageview);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.labelView.setText(names[position]);
        //viewHolder.imageView.setImageResource(R.drawable.arrow);

        return view;
    }

    private class ViewHolder {
        public TextView labelView;
        //public ImageView imageView;
    }
}
