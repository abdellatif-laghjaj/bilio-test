package com.abdellatif.biblio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {
    private String category_id;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private RecyclerView books_list;
    private ArrayList<Book> books;
    private BookAdapter bookAdapter;
    private Button search_btn;
    private TextInputEditText search_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        category_id = getIntent().getStringExtra("category_id");

        Log.d("category_id", category_id);

        books_list = findViewById(R.id.books_list);
        search_btn = findViewById(R.id.search_btn);
        search_input = findViewById(R.id.search_input);

        ArrayList<Book> fileteredBooks = new ArrayList<>();

        search_btn.setOnClickListener(v -> {
            String search = search_input.getText().toString();
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(search.toLowerCase())) {
                    fileteredBooks.add(book);
                }
                if (search.isEmpty()) {
                    readBooks();
                }
            }
            bookAdapter.setBooks(fileteredBooks);
        });

        readBooks();
    }


    private void readBooks() {
        dbRef.child("books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                books = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Book book = dataSnapshot.getValue(Book.class);
                    if (book.getCategoryId().equals(category_id)) {
                        books.add(book);
                    }
                }
                bookAdapter = new BookAdapter(books, BooksActivity.this);
                books_list.setAdapter(bookAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BooksActivity.this);
                books_list.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}