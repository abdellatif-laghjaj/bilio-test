package com.abdellatif.biblio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
        initData();
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
    private void initData() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("1", "Romance"));
        categories.add(new Category("2", "Horror"));
        categories.add(new Category("3", "Science Fiction"));
        categories.add(new Category("4", "Fantasy"));
        categories.add(new Category("5", "Mystery"));
        categories.add(new Category("6", "Thriller"));
        categories.add(new Category("7", "Drama"));
        categories.add(new Category("8", "Action"));
        categories.add(new Category("9", "Adventure"));
        categories.add(new Category("10", "Historical Fiction"));

        for (Category category : categories) {
            dbRef.child("categories").child(category.getId()).setValue(category);
        }

        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            randomCategory = categories.get((int) (Math.random() * categories.size())).getId();
            books.add(new Book(String.valueOf(i), "Book " + i, "Author " + i, randomCategory));
            dbRef.child("books").child(String.valueOf(i)).setValue(books.get(i));
        }
    }
}