package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dclassics.R;
import java.util.List;
import model.Store;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<Store> storeList;
    private Context context;

    public StoreAdapter(Context context, List<Store> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_horizontal, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        // We use the modulo operator to loop through the real items.
        // This makes position 4 become 0, 5 become 1, etc.
        Store store = storeList.get(position % storeList.size());
        holder.storeImage.setImageResource(store.getImageResId());
    }

    @Override
    public int getItemCount() {
        // Return a very large number to simulate infinity.
        return Integer.MAX_VALUE;
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        ImageView storeImage;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            storeImage = itemView.findViewById(R.id.store_image);
        }
    }
}