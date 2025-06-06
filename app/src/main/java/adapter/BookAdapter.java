package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassics.BookDetailActivity;
import com.example.dclassics.R;
import java.util.List;
import model.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private Context context;
    private int layoutId;

    public static final int TYPE_FEATURED = R.layout.item_book_featured;
    public static final int TYPE_LIST = R.layout.item_book_list;

    public BookAdapter(Context context, List<Book> bookList, int layoutId) {
        this.context = context;
        this.bookList = bookList;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        // This will now work for BOTH layouts because the IDs are the same
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookCoverImage.setImageResource(book.getCoverResourceId());

        // This block only runs for the list layout, which is correct
        if (layoutId == TYPE_LIST) {
            holder.bookSynopsisShort.setText(book.getSynopsis().substring(0, Math.min(book.getSynopsis().length(), 100)) + "...");
            holder.addToCartButton.setOnClickListener(v -> {
                Toast.makeText(context, book.getTitle() + " added to cart.", Toast.LENGTH_SHORT).show();
            });
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra("BOOK_EXTRA", book);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // This ViewHolder is now MUCH simpler and safer
    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookCoverImage;
        TextView bookTitle;
        TextView bookAuthor;
        // These views may or may not exist, depending on the layout
        TextView bookSynopsisShort;
        Button addToCartButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            // We find ALL possible views. If a view doesn't exist in the layout,
            // findViewById will just return null, which is fine.
            bookCoverImage = itemView.findViewById(R.id.book_cover_image);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookSynopsisShort = itemView.findViewById(R.id.list_book_synopsis_short); // Still use the unique ID for this one
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
        }
    }
}