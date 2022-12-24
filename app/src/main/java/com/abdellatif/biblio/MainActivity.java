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

public class MainActivity extends AppCompatActivity {
    private RecyclerView category_list;
    private CategoryAdapter categoryAdapter;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private ArrayList<Category> categories;
    private String randomCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        category_list = findViewById(R.id.category_list);


        readCategories();
    }

    private void readCategories() {
        initCategories();
        initBooks();
        dbRef.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                categories = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    categories.add(category);
                }
                categoryAdapter = new CategoryAdapter(categories, MainActivity.this);
                category_list.setAdapter(categoryAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                category_list.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    //init data
    private void initCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String id = dbRef.push().getKey();
            Category category = new Category(id, "Category #" + i);
            categories.add(category);
            dbRef.child("categories").child(id).setValue(category);
        }
    }

    private void initBooks() {
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String id = dbRef.push().getKey();
            randomCategory = getRandomCategory();
            Book book = new Book(id, "Author #" + i, "Book title #" + 1, randomCategory);
            books.add(book);
            dbRef.child("books").child(id).setValue(book);
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
                if (categories.size() > 0) {
                    randomCategory = categories.get(random).getId();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        return randomCategory;
    }
}