package corp.s.a.i.fingerprintpasswordmanager;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Gautham Leo on 30-07-2016.
 */
public class display extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        TextView t=(TextView)findViewById(R.id.textView5);
        TextView t1=(TextView)findViewById(R.id.textView7);
        TextView t2=(TextView)findViewById(R.id.textView8);
        TextView t3=(TextView)findViewById(R.id.textView9);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        t.setTypeface(typeface);
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
        t3.setTypeface(typeface);

        ImageButton add=(ImageButton)findViewById(R.id.imageButton2);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(display.this,Add.class);
                startActivity(i1);
            }
        });
        ImageButton view=(ImageButton)findViewById(R.id.imageButton3);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(display.this,view.class);
                startActivity(in);
            }
        });

        ImageButton delete=(ImageButton)findViewById(R.id.imageButton4);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(display.this,delete.class);
                startActivity(in);
            }
        });

    }

}
