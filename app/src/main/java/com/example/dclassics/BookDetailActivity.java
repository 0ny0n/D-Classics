package com.example.dclassics; // Your main package

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import model.Book; // Corrected path to your model package

public class BookDetailActivity extends AppCompatActivity {

    private ImageView bookCoverImage;
    private TextView bookTitleText, bookAuthorText, bookPriceText, bookSynopsisText;
    private TextInputEditText addressEditText, phoneEditText;
    private Button backButton, buyButton;
    private Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookCoverImage = findViewById(R.id.detail_book_cover);
        bookTitleText = findViewById(R.id.detail_book_title);
        bookAuthorText = findViewById(R.id.detail_book_author);
        bookPriceText = findViewById(R.id.detail_book_price);
        bookSynopsisText = findViewById(R.id.detail_book_synopsis);
        addressEditText = findViewById(R.id.address_edit_text);
        phoneEditText = findViewById(R.id.phone_number_edit_text);
        backButton = findViewById(R.id.back_button);
        buyButton = findViewById(R.id.buy_button);

        currentBook = getIntent().getParcelableExtra("BOOK_EXTRA");

        if (currentBook != null) {
            bookCoverImage.setImageResource(currentBook.getCoverResourceId());
            bookTitleText.setText(currentBook.getTitle());
            bookAuthorText.setText(currentBook.getAuthor());
            bookPriceText.setText(currentBook.getPrice());
            bookSynopsisText.setText(currentBook.getSynopsis());
        } else {
            Toast.makeText(this, "Error loading book details.", Toast.LENGTH_SHORT).show();
            finish();
        }

        backButton.setOnClickListener(v -> finish());

        buyButton.setOnClickListener(v -> {
            String address = addressEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (TextUtils.isEmpty(address) || TextUtils.isEmpty(phone)) {
                Toast.makeText(BookDetailActivity.this, "Please enter address and phone number.", Toast.LENGTH_SHORT).show();
            } else {
                showConfirmationDialog();
            }
        });
    }

    private void showConfirmationDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCancelable(false);
        Button okButton = dialog.findViewById(R.id.ok_button);
        okButton.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(BookDetailActivity.this, "Order placed (simulated)!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(BookDetailActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}