package com.example.cookshop.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.cookshop.R;
import com.example.cookshop.items.Item;

import java.util.ArrayList;

;

public class ListItemWithDeleteButtonAdapter<T extends Item> extends BaseAdapter implements ListAdapter
{
    private final String                      TAG = this.getClass().getSimpleName();
    private       ArrayList<T>             list;
    private       Context                     context;
    private       OnDeleteButtonClickListener onDeleteButtonClickListener;

  public ArrayList<T> getList() {
    return list;
  }

  public ListItemWithDeleteButtonAdapter(ArrayList<T> list, Context context)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int pos)
    {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_with_delete_button, parent, false);
        convertView = BindView(position, convertView, this.onDeleteButtonClickListener);
        return convertView;
    }

    /**
     * Method to fill each {@link View} with the corresponding parameters of the {@link Article}
     * and bind them to the {@code convertView}
     *
     * @param position
     *         the position of the {@link Article} in the {@code  articleList}
     * @param convertView
     * @return View convertView
     */
    private View BindView(final int position, View convertView, final OnDeleteButtonClickListener onDeleteButtonClickListener)
    {
        Item item = list.get(position); //get the Article

        //Setup the name TextView
            TextView name = convertView.findViewById(R.id.name_textview);
            name.setText(item.getName());

        Button deleteButton = convertView.findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onDeleteButtonClickListener.onItemClick(position);
            }
        });

        return convertView;
    }


    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener)
    {
        this.onDeleteButtonClickListener = listener;
    }

    public Context getContext()
    {
        return this.context;
    }

    public interface OnDeleteButtonClickListener extends RecipeRecyclerViewAdapter.OnItemClickListener
    {

    }
}

