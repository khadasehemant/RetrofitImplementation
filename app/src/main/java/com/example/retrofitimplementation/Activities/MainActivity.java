package com.example.retrofitimplementation.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.retrofitimplementation.Adapters.CustomBaseAdapter;
import com.example.retrofitimplementation.Clients.ApiCallback;
import com.example.retrofitimplementation.Clients.ApiClient;
import com.example.retrofitimplementation.Models.RowData;
import com.example.retrofitimplementation.R;
import com.example.retrofitimplementation.Utils.Common;
import com.example.retrofitimplementation.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.example.retrofitimplementation.Utils.Constants.API_books;

public class MainActivity extends AppCompatActivity implements ApiCallback {

    private ListView list_common;
    private List<RowData> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Common.isNetworkAvailable(this)) getBooksData();
        else Toast.makeText(this, Constants.INTERNET_NOT_AVAILABLE, Toast.LENGTH_SHORT).show();

        list_common = (ListView)findViewById(R.id.list_common);
        rowItems = new ArrayList<RowData>();
    }

    private void getBooksData() {
        Common.progressDialog(this,Constants.pregressText);
        new ApiClient().apiRequest(ApiClient.getClient().get_books_data(), MainActivity.this, API_books, this);
    }

    @Override
    public void onResponse(Response<ResponseBody> res, int number, Activity activity) {
        if(activity == this) {
            switch (number){
                case API_books:{
                    //Stop loader
                    Common.dismisDialog();
                    // call Method with response
                    methodAPI(res);
                }
                break;
            }
        }else{
            //Stop loader
            Common.dismisDialog();
        }
    }

    private void methodAPI(Response<ResponseBody> res) {
        try {
            String response = res.body().string();
            JSONObject main_jsonobj = new JSONObject(response);
            String bookdata = main_jsonobj.getString("BookData");
            JSONObject bookdata_jsonobj = new JSONObject(bookdata);
            String book = bookdata_jsonobj.getString("book");
            JSONArray book_jsonarray = new JSONArray(book);
            for (int i=0;i<book_jsonarray.length();i++) {
                JSONObject book_jsonobject = book_jsonarray.getJSONObject(i);
                String title = book_jsonobject.getString("Title");
                String fname = book_jsonobject.getString("AutorFName1");
                String lname = book_jsonobject.getString("AutorLName1");
                String img_url = book_jsonobject.getString("ThumbURL");
                RowData rowData = new RowData();
                rowData.setTitle(title);
                rowData.setName(fname+" "+lname);
                rowData.setImage(img_url);
                rowItems.add(rowData);
            }
            CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems);
            list_common.setAdapter(adapter);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Response<ResponseBody> res, int number, Activity activity) {
        Toast.makeText(MainActivity.this," "+res.raw(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t, int number, Activity activity) {
        Common.dismisDialog();
        if(activity == this) {
            switch (number){
                case API_books:{
                    Log.e("throw", " "+t.getMessage());
                }
                break;
            }
        }
        Log.e("err", t.toString());
    }
}
