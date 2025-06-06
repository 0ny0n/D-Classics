package com.example.dclassics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu; // Import for the dropdown menu
import android.widget.Scroller;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import adapter.BookAdapter;
import adapter.StoreAdapter;
import model.Book;
import model.Store;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 viewPagerStores;
    private RecyclerView featuredBooksRecyclerView;
    private StoreAdapter storeAdapter;
    private BookAdapter featuredBookAdapter;
    private List<Store> popularStoresList = new ArrayList<>();
    private List<Book> featuredBooksList = new ArrayList<>();

    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // --- Find all views by their ID ---
        viewPagerStores = findViewById(R.id.view_pager_stores);
        featuredBooksRecyclerView = findViewById(R.id.rv_featured_books);
        ImageButton btnPrev = findViewById(R.id.btn_prev);
        ImageButton btnNext = findViewById(R.id.btn_next);
        ImageView ivMenu = findViewById(R.id.iv_menu);
        TextView greetingText = findViewById(R.id.greetingText);

        // --- Set up dynamic greeting ---
        String username = getIntent().getStringExtra("USERNAME_EXTRA");
        if (username == null || username.isEmpty()) {
            username = "User";
        }
        greetingText.setText(getString(R.string.hello_user, username));


        // --- Setup Store Carousel ---
        loadPopularStores();
        storeAdapter = new StoreAdapter(this, popularStoresList);
        viewPagerStores.setAdapter(storeAdapter);

        // --- Visual settings for ViewPager2 ---
        viewPagerStores.setClipToPadding(false);
        viewPagerStores.setClipChildren(false);
        viewPagerStores.setOffscreenPageLimit(3);
        viewPagerStores.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        int middlePosition = Integer.MAX_VALUE / 2;
        viewPagerStores.setCurrentItem(middlePosition - (middlePosition % popularStoresList.size()), false);


        // --- Setup Featured Books RecyclerView ---
        loadFeaturedBooks();
        featuredBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        featuredBookAdapter = new BookAdapter(this, featuredBooksList, BookAdapter.TYPE_FEATURED);
        featuredBooksRecyclerView.setAdapter(featuredBookAdapter);

        // --- Button clicks ---
        btnPrev.setOnClickListener(v -> viewPagerStores.setCurrentItem(viewPagerStores.getCurrentItem() - 1, true));
        btnNext.setOnClickListener(v -> viewPagerStores.setCurrentItem(viewPagerStores.getCurrentItem() + 1, true));

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });


        // --- Auto-Scroll logic ---
        sliderRunnable = () -> viewPagerStores.setCurrentItem(viewPagerStores.getCurrentItem() + 1, true);

        viewPagerStores.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        // --- Scroll speed control ---
        try {
            Field scrollerField = ViewPager2.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPagerStores.getContext());
            scrollerField.set(viewPagerStores, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.home_popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_all_books) {
                    startActivity(new Intent(HomeActivity.this, AllBooksActivity.class));
                    return true;
                } else if (itemId == R.id.menu_our_store) {
                    startActivity(new Intent(HomeActivity.this, StoresActivity.class));
                    return true;
                } else if (itemId == R.id.menu_log_out) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }


    private void loadPopularStores() {
        popularStoresList.clear();
        popularStoresList.add(new Store("s1", "DAUNT BOOKS", R.drawable.dauntbooks, "83", "Popular"));
        popularStoresList.add(new Store("s2", "City Lights", R.drawable.store, "75", "Popular"));
        popularStoresList.add(new Store("s3", "Shakespeare & Co", R.drawable.balfourbooks, "90", "Popular"));
        popularStoresList.add(new Store("s4", "Barnes Bookshop", R.drawable.barnesbookshop, "90", "Popular"));
    }

    private void loadFeaturedBooks() {
        featuredBooksList.clear();
        featuredBooksList.add(new Book("b1", "To Kill A Mockingbird", "Harper Lee", R.drawable.mocking_bird, "To Kill a Mockingbird is a powerful novel...", "100$", "Fiction"));
        featuredBooksList.add(new Book("b2", "The Lord of The Rings", "J.R.R. Tolkien", R.drawable.lord_of_rings, "The Lord of the Rings is an epic fantasy tale...", "150$", "Fiction"));
        featuredBooksList.add(new Book("b3", "The Great Gatsby", "F. Scott Fitzgerald", R.drawable.great_gatsby, "Set in the Roaring Twenties...", "75$", "Fiction"));
        featuredBooksList.add(new Book("b4", "1984", "George Orwell", R.drawable.one_ninety, "In a dystopian future ruled by a totalitarian regime...", "50$", "Fiction"));
        featuredBooksList.add(new Book("b5", "The Little Prince", "Antoine de Saint", R.drawable.the_little_prince, "The Little Prince is a poetic tale...", "125$", "Fiction"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    // --- Helper class for controlling scroll speed ---
    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 800;

        public FixedSpeedScroller(Context context) {
            super(context);
        }


        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }
}