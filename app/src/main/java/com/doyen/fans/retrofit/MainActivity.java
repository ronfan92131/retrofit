package com.doyen.fans.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private TextView textViewResult;
    private String baseUrl = "https://raw.githubusercontent.com/ronfan92131/json/master/";
    private String url     = "https://raw.githubusercontent.com/ronfan92131/json/master/sample.json";

    //private String baseUrl = "https://raw.githubusercontent.com/granularag/granular_mobile_mock_response/master/";
    //private String url     = "https://raw.githubusercontent.com/granularag/granular_mobile_mock_response/master/list.json";

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getComments();
        getUsers();
      //  getGranular();
    }

    private void getGranular(){
        Log.d(TAG, "getGranular");

        Call<List<Granular>> call = jsonPlaceHolderApi.getGranular(url);

        call.enqueue(new Callback<List<Granular>>() {
            @Override
            public void onResponse(Call<List<Granular>> call, Response<List<Granular>> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "response not successful");
                    return;
                }


                /*
                [
                    {
                        "name" : "One",
                        "url" : "Icons/1.png"
                    },  */
                List<Granular> granulars = response.body();
                for (Granular granular : granulars){
                    String content = "";
                    content += "name: " + granular.getName() +"\n";
                    content += "url: " + granular.getUrl() + "\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Granular>> call, Throwable t) {
                Log.d(TAG, "onFailuer");
                textViewResult.setText(t.getMessage());
            }
        });

    }


    private void getUsers(){
        Log.d(TAG, "getUsers");

        Call<List<User>> call = jsonPlaceHolderApi.getUsers(url);

        call.enqueue(new Callback<List<User>>() {
                         @Override
                         public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                             if (!response.isSuccessful()){
                                 Log.d(TAG, "response not successful");
                                 return;
                             }

                             List<User> users = response.body();

                             /* display all users in textView
                               {
                                    "userId": 1,
                                    "id": 1,
                                    "title": "sunt aut facere repellat providri optio reprehenderit",
                                    "body": "quia et suscipit\nsuscipit otam\nnorem rchitecto"
                                }, */
                             for (User user : users){
                                 String content = "";
                                 content += "userID: " + user.getUserId() +"\n";
                                 content += "id: " + user.getId() + "\n";
                                 content += "title: " + user.getTitle() + "\n";
                                 content += "body: " + user.getBody() + "\n\n";

                                 textViewResult.append(content);
                             }

                         }

                         @Override
                         public void onFailure(Call<List<User>> call, Throwable t) {
                             Log.d(TAG, "onFailuer");
                             textViewResult.setText(t.getMessage());
                         }
                     }
        );
    }


    private void getComments() {
        Log.d(TAG, "getComments");

        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(url);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "response not successful");
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Comment> comments = response.body();
                updateUI(comments);
            }

            private void updateUI(List<Comment> comments) {
                for (Comment comment : comments){
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}
