package com.android.android.farhan_1202152159_modul4;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListMahasiswa extends AppCompatActivity {

    //Deklarasi Komponen yang akan digunakan
    //Button
    private Button btnLoad;
    private ListView listMhs;
    private ProgressDialog progDailog;

    //Atribut yang akan digunakan sebagai penunjuk
    private static int dataIsLoaded=0;
    private ArrayAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmahasiswa);
        setTitle("Students Name List");

        //Inisialisasi Komponen View
        btnLoad=(Button)findViewById(R.id.btnLoad);
        listMhs=(ListView)findViewById(R.id.listMhs);

        //Aksi saat Tombol di Klik
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Memanggil Asynctask dari class listNameTask() pada sub class ini
                new listNameTask().execute(listNama());
            }
        });

        //Cek sesuatu didalam savedInstansState
        if(savedInstanceState!=null){
            //Cek apakah kunci penunjuk bukan 0 atau jika 1 maka data sudah di melalui proses load sebelumhya
            if(savedInstanceState.getInt("DATA_LIST_LOADED")==1) {
                //Set Text pada Button
                btnLoad.setText("Data Sudah Dimuat, Muat Ulang?");
                //Eksekusi Asynctask yang diisikan dengan datanya (@param listName())
                new listNameTask().execute(listNama());
            }
        }
    }

    /*
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Simpan Sesuatu dengan Key
        outState.putInt("DATA_LIST_LOADED",dataIsLoaded);
    }

    /*
    * Method yang mengembalikan array bentuk string, berguna untuk mempopulasikan data yang nantinya akan digunakn pada ListView
    */
    public String[] listNama(){
        return new String[]{
                "Jono","Joni","John","Anto","Budi","Cecep","Doni","Ema","Fred","Gideon","Henri","Ian","Jack","Kevin","Long","Mark","Nemanja","Obrian","Peter","Roy"
        };
    }


    /*
    * Class Asynctask dengan paramater deklarasi:
    *   Input: String[] -> Array
    *   Progress: Integer -> Process
    *   Result: String[] -> Array
    */
    private class listNameTask extends AsyncTask<String[], Integer, String[]>{

        /*
        * Method yang dilakukan sebelum aksi dilakukan
        *   Pada aplikasi, tahap ini digunakan untuk memunculkan progress bar beserta propertynya
        */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(ListMahasiswa.this);
            progDailog.setMessage("Waiting...");
            progDailog.setMax(100);
            progDailog.show();
        }

        /*
        * Method yang digunakan saat eksekusi berlangsung 1x
        *   Tahap ini digunakan untuk mengembalikan data input agar menjadi data output (String[])
        */
        @Override
        protected String[] doInBackground(String[]... strings) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("doInBackground","n: "+strings[0].length);
            return strings[0];
        }

        /*
        * Method yang dilakukan saat proses berlangsung sesuai dengan besarnya request yang dijalankan
        */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try {
                Thread.sleep(1500);
                progDailog.setMessage("Populating...");
                progDailog.incrementProgressBy(values[0]);
                progDailog.setProgress(values[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
        * Method yang dilakukan setalah proses berhasil di eksekusi
        *   Tahap ini digunakan untuk mengisikan data input ke dalam ListView beserta dengan pembuatan dan set pada Adapter
        *       ..digunakan juga untuk memberi petunjuk agar direspon oleh onSaveInstanceState()
        */
        @Override
        protected void onPostExecute(String[] res) {
            super.onPostExecute(res);
            Log.d("OnPostExecute: "," n:"+res.length+" x:"+res);
            //Pembuatan dan Mengisi data ArrayAdapter dengan menggunakan layout Android List Default 1
            dataAdapter = new ArrayAdapter(ListMahasiswa.this,android.R.layout.simple_list_item_1, res);
            //Set DataAdapter
            listMhs.setAdapter(dataAdapter);
            //Set dataIsLoaded untk memberitahukan onSaveInstanceSaved bahwa data sudah di load
            dataIsLoaded=1;
            //Menghilangkan Progress Bar (Loading)
            progDailog.dismiss();
        }
    }
}
