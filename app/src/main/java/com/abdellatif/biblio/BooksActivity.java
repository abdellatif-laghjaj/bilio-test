package com.abdellatif.biblio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
    private String randomCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        category_id = getIntent().getStringExtra("category_id");

        books_list = findViewById(R.id.books_list);
    }


    private void readBooks() {
        initBooks();
        dbRef.child("books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                books = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Book book = dataSnapshot.getValue(Book.class);
                    books.add(book);
                }
                bookAdapter = new BookAdapter(books, BooksActivity.this);
                books_list.setAdapter(bookAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    //init data
    private void initBooks() {
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String id = dbRef.push().getKey();
            Book book = new Book(id, "Author #" + i, "Book title #" + 1, randomCategory);
        }
    }

    //get random category from database
    private String getRandomCategory() {
        dbRef.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Category> categories = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category);
                }
                int random = (int) (Math.random() * categories.size());
                randomCategory = categories.get(random).getId();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return randomCategory;
    }
}