package com.batura.stas.booksfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HOME on 28.04.2018.
 */

public class BooksAdapter extends ArrayAdapter<Book> {

    public BooksAdapter (Context context, List<Book> books) {
        super(context,0,books);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.books_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView authorTextView = (TextView)listItemView.findViewById(R.id.autor_text);
        authorTextView.setText(currentBook.getAuthor());

        TextView titleTextView = (TextView)listItemView.findViewById(R.id.title_text);
        titleTextView.setText(currentBook.getTitle());

        TextView descrTextView = (TextView)listItemView.findViewById(R.id.description_text);
        descrTextView.setText(currentBook.getDescription());

        return listItemView;
    }
}
