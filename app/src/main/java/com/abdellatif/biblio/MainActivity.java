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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        category_list = findViewById(R.id.category_list);

        initCategories();

        readCategories();
    }

    private void readCategories()
    {
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
        }

        for (Category category : categories) {
            dbRef.child("categories").child(category.getId()).setValue(category);
        }
    }
}