package lyc.foreignexchangerate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    //紀錄spinner最後選取的index
    int selectedIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitialComponent();

        txt1.setText(R.string.basecurrency);

        StrictMode.ThreadPolicy l_policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(l_policy);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource (this, R.array.currency_array,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedIndex = parent.getSelectedItemPosition();
        getCurrencyArray(selectedIndex);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getCurrencyArray(int index){

        List<Currencydata> cuList = new ArrayList<>();
        String[] currencyarray = getResources().getStringArray(R.array.currency_array);
        String htmlStr = "";

        try {
            URL url = new URL("https://tw.rter.info/capi.php");
            URLConnection conn = url.openConnection();
            InputStream streamIn = conn.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));
            StringBuilder html = new StringBuilder();
            String line;

            while ((line = r.readLine()) != null) {
                html.append(line);
            }

            htmlStr = html.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jo1 = new JSONObject(htmlStr);

            cuList.add(new Currencydata(currencyarray[0],1));
            cuList.add(new Currencydata(currencyarray[1],(new JSONObject(jo1.getString("USDTWD"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[2],(new JSONObject(jo1.getString("USDHKD"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[3],(new JSONObject(jo1.getString("USDGBP"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[4],(new JSONObject(jo1.getString("USDAUD"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[5],(new JSONObject(jo1.getString("USDCAD"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[6],(new JSONObject(jo1.getString("USDSGD"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[7],(new JSONObject(jo1.getString("USDCHF"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[8],(new JSONObject(jo1.getString("USDJPY"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[9],(new JSONObject(jo1.getString("USDZAR"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[10],(new JSONObject(jo1.getString("USDSEK"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[11],(new JSONObject(jo1.getString("USDNZD"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[12],(new JSONObject(jo1.getString("USDTHB"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[13],(new JSONObject(jo1.getString("USDPHP"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[14],(new JSONObject(jo1.getString("USDIDR"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[15],(new JSONObject(jo1.getString("USDEUR"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[16],(new JSONObject(jo1.getString("USDKRW"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[17],(new JSONObject(jo1.getString("USDVND"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[18],(new JSONObject(jo1.getString("USDMYR"))).getDouble("Exrate")));
            cuList.add(new Currencydata(currencyarray[19],(new JSONObject(jo1.getString("USDCNY"))).getDouble("Exrate")));

            double BaseCurrent = cuList.get(index).getValue();
            for(int i = 0;i<cuList.size();i++){
                cuList.get(i).setValue(cuList.get(i).getValue()/BaseCurrent);
            }

        }

        catch (JSONException e){
            e.printStackTrace();
        }

        CurrencyAdapter currencyAdapteradapter = new CurrencyAdapter(cuList);
        recyclerView.setAdapter(currencyAdapteradapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        txttime.setText("更新時間：" +  df.format(Calendar.getInstance().getTime()));

    }

    View.OnClickListener btnrefresh_click = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            getCurrencyArray(selectedIndex);
            Toast.makeText(MainActivity.this, "已重新整理", Toast.LENGTH_LONG).show();
        }
    };

    public void InitialComponent(){
        spinner = (Spinner)findViewById(R.id.spinnerCurrency);
        btn = (Button) findViewById(R.id.btnRefresh);
        btn.setOnClickListener(btnrefresh_click);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        txttime = (TextView)findViewById(R.id.txtTime);
        txt1 = (TextView)findViewById(R.id.txt1);
    }

    Spinner spinner;
    Button btn;
    RecyclerView recyclerView;
    TextView txt1,txttime;
}
