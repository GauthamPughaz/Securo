package corp.s.a.i.fingerprintpasswordmanager;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.jar.Attributes;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        final TextView t=(TextView)findViewById(R.id.textView2);
        final TextView t1=(TextView)findViewById(R.id.textView3);
        final TextView t2=(TextView)findViewById(R.id.textView);
        final EditText name=(EditText)findViewById(R.id.editText);
        final EditText lname=(EditText)findViewById(R.id.editText5);
        ImageButton next=(ImageButton)findViewById(R.id.button);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        t.setTypeface(typeface);
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final Intent i=new Intent(Login.this,fingerprint.class);
                 String nam=name.getText().toString();
                String lnam=lname.getText().toString();
                if(nam==""&& lnam==""||nam==""||lnam=="")
                {
                    Toast.makeText(Login.this,"Enter all the fields",Toast.LENGTH_LONG).show();
                }
                else {
                        String fullname=nam+lnam;

                    i.putExtra("NAME", fullname);
                    startActivity(i);
                }
            }
        });
    }
}

