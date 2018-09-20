package uk.co.enwl.purl;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the MealPlan button */
    public void onCalculate(View view) {
        // Do something in response to button

        boolean[] PURLT1 = new boolean[9];
        boolean[] PURLT2 = new boolean[9];
        boolean[] PURLT3 = new boolean[9];

        ToggleButton btnPURL;
        Resources res = getResources();
        int id;

        for (int i=1;i<=9;i++)
        {
            id = res.getIdentifier("PURLT1-" + i, "id", this.getPackageName());
            btnPURL = findViewById(id);
            PURLT1[i-1]=btnPURL.isChecked();

            id = res.getIdentifier("PURLT2-" + i, "id", this.getPackageName());
            btnPURL = findViewById(id);
            PURLT2[i-1]=btnPURL.isChecked();

            id = res.getIdentifier("PURLT3-" + i, "id", this.getPackageName());
            btnPURL = findViewById(id);
            PURLT3[i-1]=btnPURL.isChecked();
        }


        Bundle b=new Bundle();
        b.putBooleanArray("PURL1",PURLT1);
        b.putBooleanArray("PURL2",PURLT2);
        b.putBooleanArray("PURL3",PURLT3);


        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtras(b);
        startActivity(intent);

    }


}
