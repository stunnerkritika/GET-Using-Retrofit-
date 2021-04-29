package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewresult =findViewById(R.id.text_view_result);
        Retrofit retrofit =new  Retrofit .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call =jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>()

        {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
         if (!response.isSuccessful()){
             textViewresult.setText("code: " +response.code());
             return;
                }
         List<Post> Posts =response.body();
         for (Post post : Posts ){
             String Content = "";
             Content += "ID:" + post.getId() +"\n";
             Content += "UserId"+post.getUserId() + "\n";
             Content += "Title" +post.getTitle() + "\n";
             Content += "Text" + post.getText() +"\n\n";
             textViewresult.append(Content);



         }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewresult.setText(t.getMessage());
            }
        });

        }



    }
