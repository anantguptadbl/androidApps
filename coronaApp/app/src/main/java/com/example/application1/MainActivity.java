package com.example.application1;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application1.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //private Button getBtn;
    private TextView newCases;
    private TextView newDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newCases = (TextView) findViewById(R.id.newCases);
        newDeaths = (TextView) findViewById(R.id.newDeaths);
        Timer timer = new Timer();
        timer.schedule(new getWebsitePeriodically(), 0, 5000);
        /*
        getBtn = (Button) findViewById(R.id.button);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebsite();
            }
        });
        */

    }

    class getWebsitePeriodically extends TimerTask {
        public void run() {
            String oldNewCasesValue=((TextView) findViewById(R.id.newCases)).getText().toString().trim().replaceAll("\\P{Print}","");
            String oldNewDeathsValue=((TextView) findViewById(R.id.newDeaths)).getText().toString().trim().replaceAll("\\P{Print}","");
            getWebsite();
            // The following will be executed only when there is a change in the value
            String NewCasesValue=((TextView) findViewById(R.id.newCases)).getText().toString().trim().replaceAll("\\P{Print}","");;
            String NewDeathsValue=((TextView) findViewById(R.id.newDeaths)).getText().toString().trim().replaceAll("\\P{Print}","");;
            final StringBuilder flag=new StringBuilder();
            int triggerDeaths=0;
            int triggerCases=0;
            flag.append("0");
            //System.out.println("Triggers " + triggerCases + "," + triggerDeaths+ " " + oldNewDeathsValue + " : "+ NewDeathsValue + " " + oldNewCasesValue + " : "+ NewCasesValue);
            if(oldNewDeathsValue.equals(NewDeathsValue))
            {
                triggerDeaths=0;
            }
            else
            {
                triggerDeaths=1;
            }

            if(oldNewCasesValue.equals(NewCasesValue))
            {
                triggerCases=0;
            }
            else
            {
                triggerCases=1;
            }
            //System.out.println("Triggers " + triggerCases + "," + triggerDeaths+ " " + oldNewDeathsValue + " : "+ NewDeathsValue + " " + oldNewCasesValue + " : "+ NewCasesValue);
            if(triggerDeaths==1)
            {
                flag.replace(0,5,"0");
                System.out.println("The old deaths are not the same");
                TextToSpeech ttobj=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status)
                    {
                        if (status == TextToSpeech.SUCCESS) {
                            flag.replace(0, 5, "1");
                            //System.out.println("We have successfully done the initialization of TTS");
                            //System.out.println("The flag value is now " + flag.toString() + "Comparison is " + flag.toString().equals("0"));
                            // Nothing to do during on Init
                        }
                    }
                });
                while(flag.toString().equals("0"))
                {
                    //System.out.println("Waiting for the init. The current flag is " + flag.toString());
                }
                ttobj.setLanguage(Locale.UK);
                String toSpeakString="New deaths are " + NewDeathsValue;
                ttobj.speak(toSpeakString, TextToSpeech.QUEUE_FLUSH, null);
                try {
                    TimeUnit.SECONDS.sleep((long)5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ttobj.stop();
                ttobj.shutdown();
                flag.replace(0,5,"0");

            }

            if(triggerCases==1)
            {
                flag.replace(0,5,"0");
                //System.out.println("The old cases are not the same");
                TextToSpeech ttobj=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status)
                    {
                        if (status == TextToSpeech.SUCCESS)
                        {
                            //System.out.println("We have successfully done the initialization of TTS");
                            flag.replace(0,5,"1");
                        }
                    }
                });
                while(flag.toString().equals("0"))
                {
                    //System.out.println("Waiting for the init. The current flag is " + flag.toString());
                }
                ttobj.setLanguage(Locale.UK);
                String toSpeakString="New cases are " + NewCasesValue;
                ttobj.speak(toSpeakString, TextToSpeech.QUEUE_FLUSH, null);
                try {
                    TimeUnit.SECONDS.sleep((long)5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ttobj.stop();
                ttobj.shutdown();
            }


        }
    }

    private void getWebsite(){
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                //final StringBuilder builder = new StringBuilder();
                final StringBuilder newCasesVal=new StringBuilder();
                final StringBuilder newDeathsVal=new StringBuilder();

                try {
                    Document doc = Jsoup.connect("https://www.worldometers.info/coronavirus/#countries").get();
                    String title = doc.title();
                    Elements links = doc.select("tr.total_row > td");
                    newCasesVal.replace(0,100,links.get(2).text());
                    newDeathsVal.replace(0,100,links.get(4).text());
                    //System.out.println("New Cases : " + newCasesVal.toString());


                } catch (IOException e) {
                    // We will not do anything
                   //builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newCases.setText(newCasesVal.toString());
                        newDeaths.setText(newDeathsVal.toString());
                    }
                });
            }
        });
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}