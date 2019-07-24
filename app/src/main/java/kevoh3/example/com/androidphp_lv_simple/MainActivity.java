package kevoh3.example.com.androidphp_lv_simple;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //VARIABLES
    ListView lv;
    ArrayAdapter<String> adapter;
    String address="http://10.0.2.2/android/soccer.php";
    InputStream is=null;
    String line=null;
    String result=null;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZE VARIABLES
        lv=(ListView)findViewById(R.id.ListView1);

        //ALLOW NETWORK IN MAIN THREAD
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        //RETRIEVE DATA
        getData();

        //ADAPTER
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);

    }

    //LETS CONNECT OUR DB BY READING THE JSON
    private void getData(){

        try {
            URL url = new URL(address);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            is=new BufferedInputStream(con.getInputStream());


        }catch(Exception e){
            e.printStackTrace();
        }

        //READ INPUT STREAM CONTENT INTO A STRING

        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();

            while ((line=br.readLine()) !=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();

        }catch(Exception e){
            e.printStackTrace();
        }

        //PARSE JSON DATA
        try{
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;
            data=new String[ja.length()];

            for (int i=0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                data[i]=jo.getString("Name");
            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
