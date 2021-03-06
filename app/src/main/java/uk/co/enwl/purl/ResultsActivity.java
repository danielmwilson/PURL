package uk.co.enwl.purl;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private PURLCalc myPURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Bundle b=this.getIntent().getExtras();
        boolean[] PURLT1=b.getBooleanArray("PURL1");
        boolean[] PURLT2=b.getBooleanArray("PURL2");
        boolean[] PURLT3=b.getBooleanArray("PURL3");

        myPURL = new PURLCalc();

        int[][] GridArray = myPURL.CalcGrid(PURLT1,PURLT2,PURLT3);

        double ResidStrength = myPURL.calcResidualStrength(GridArray);

        TextView txtStrength = findViewById(R.id.txtResidualStrength);

        txtStrength.setText(String.format("%.0f%%",ResidStrength));

        int[] bPoleImage = new int[1600];

        int k = -1;

        for (int i=0;i<40;i++)
        {
            for (int j = 0; j < 40; j++) {
                k++;
                if (GridArray[i][j] == 0) {
                    bPoleImage[k] = getResources().getColor(R.color.colorPoleBackground);
                } else if (GridArray[i][j] == 2) {
                    bPoleImage[k] = getResources().getColor(R.color.colorPoleRim);
                } else if (GridArray[i][j] == -1) {
                    bPoleImage[k] = getResources().getColor(R.color.colorPoleRotWood);
                } else if (GridArray[i][j] == 1) {
                    bPoleImage[k] = getResources().getColor(R.color.colorPoleGoodWood);
                }
            }
        }


        Bitmap bmpPole= Bitmap.createBitmap(bPoleImage,40,40,Bitmap.Config.ARGB_8888);



        ImageView imgPole = findViewById(R.id.imageView);

        int intImgSize=200;

        imgPole.setImageBitmap(Bitmap.createScaledBitmap(bmpPole,intImgSize,intImgSize,false));

    }

    public void onCopy(View view)
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", String.format("%.1f",myPURL.ResidStrength));


        clipboard.setPrimaryClip(clip);
    }
}
