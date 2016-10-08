package corp.s.a.i.fingerprintpasswordmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.AlgorithmParameters;

/**
 * Created by Gautham Leo on 15-08-2016.
 */
public class delete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        final TextView disp=(TextView)findViewById(R.id.delete) ;
        disp.setVisibility(View.INVISIBLE);
        final TextView disp2=(TextView)findViewById(R.id.message) ;
        final TextView disp3=(TextView)findViewById(R.id.textView12) ;
        final TextView disp4=(TextView)findViewById(R.id.textView16) ;
         disp2.setVisibility(View.INVISIBLE);
        final EditText get=(EditText)findViewById(R.id.editText3);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");

        disp.setTypeface(typeface);
        disp2.setTypeface(typeface);
        disp3.setTypeface(typeface);
        disp4.setTypeface(typeface);
        final ImageButton search=(ImageButton)findViewById(R.id.imageButton2);
        final ImageButton del=(ImageButton)findViewById(R.id.del);
        final ImageButton no=(ImageButton)findViewById(R.id.yes);
        final ImageView delete= (ImageView)findViewById(R.id.imageView2);
        delete.setVisibility(View.INVISIBLE);
        del.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cat=get.getText().toString();
                if(cat=="")
                {
                    Toast.makeText(getApplicationContext(),"Enter a valid category",Toast.LENGTH_LONG).show();
                }
                else{
                    delete.setVisibility(View.VISIBLE);
                    del.setVisibility(View.VISIBLE);
                    no.setVisibility(View.VISIBLE);
                    disp.setVisibility(View.VISIBLE);
                    disp2.setVisibility(View.VISIBLE);
                    disp.setText("Selected "+cat+" account\n passwords");
                    disp2.setText("Are you sure?");
                    del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String[] upper=new String[1];
                            upper[0]=cat.toUpperCase();

                            SQLiteDatabase s=openOrCreateDatabase("pass8",MODE_PRIVATE,null);
                            //Cursor c=s.rawQuery("delete from password3 where category='"+upper+"';",null);

                            s.delete("password8","category=?",upper);
                            //c.close();
                            del.setVisibility(View.INVISIBLE);
                            no.setVisibility(View.INVISIBLE);
                            delete.setVisibility(View.INVISIBLE);
                            disp.setVisibility(View.VISIBLE);
                            disp2.setVisibility(View.VISIBLE);

                            disp.setText("");
                            disp2.setText("Deletion Successful");

                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            del.setVisibility(View.INVISIBLE);
                            no.setVisibility(View.INVISIBLE);
                            delete.setVisibility(View.INVISIBLE);
                            disp.setVisibility(View.VISIBLE);
                            disp2.setVisibility(View.VISIBLE);

                            disp.setText("");
                            disp2.setText("Operation Cancelled");
                        }
                    });



                }
            }
        });


    }
}
