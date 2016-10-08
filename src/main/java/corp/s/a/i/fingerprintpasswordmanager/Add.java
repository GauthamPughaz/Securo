package corp.s.a.i.fingerprintpasswordmanager;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class Add extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static  public String TAG="Symmetric AES";
    private Cipher cipher=null;
    private SecretKeySpec key=null;
    private AlgorithmParameterSpec spec;
    String encryptedText;

    Context appContext;
    String cate;
    public Add()
    {

    }

    public Add(Context context)
    {
        appContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");


        final EditText cat=(EditText)findViewById(R.id.editText3);
        final EditText pass=(EditText)findViewById(R.id.editText2);
        final EditText cpass=(EditText)findViewById(R.id.editText4);
        final ImageButton add=(ImageButton)findViewById(R.id.button3);
        final TextView t4=(TextView)findViewById(R.id.textView17);
        final TextView t15=(TextView)findViewById(R.id.textView11);
        final TextView t16=(TextView)findViewById(R.id.textView12);
        final TextView t17=(TextView)findViewById(R.id.textView13);
        final TextView t18=(TextView)findViewById(R.id.textView14);
        final ImageView i=(ImageView)findViewById(R.id.imageView3);
        Spinner staticSpinner = (Spinner) findViewById(R.id.spinner);
        staticSpinner.setOnItemSelectedListener(this);
        String[] category=new String[]{"Google","Facebook","LinkedIn","Outlook","Apple","Custom"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,category);
        staticSpinner.setAdapter(arrayAdapter);


        t4.setTypeface(typeface);
        t15.setTypeface(typeface);
        t16.setTypeface(typeface);
        t17.setTypeface(typeface);
        t18.setTypeface(typeface);
        i.setVisibility(View.INVISIBLE);
        t4.setVisibility(View.INVISIBLE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = cat.getText().toString();
                String categor=category.toUpperCase();
                String password = pass.getText().toString();
                String cpassword = cpass.getText().toString();
                if (category.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
                    Toast.makeText(Add.this, "Enter all the details", Toast.LENGTH_LONG).show();
                } else if (!password.equals(cpassword)) {
                    Toast.makeText(Add.this, "Passwords does not match", Toast.LENGTH_LONG).show();

                } else {
                    try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        digest.update("HelloWorld".getBytes("UTF-8"));
                        byte[] keyBytes = new byte[32];
                        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

                        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                        key = new SecretKeySpec(keyBytes, "AES");
                        spec = getIV();
                        encryptedText = encrypt(password);

                        SQLiteDatabase s=openOrCreateDatabase("pass7",MODE_PRIVATE,null);
                        s.execSQL("create table if not exists password3(category varchar(30),encryptedpass varchar(256));");
                        s.execSQL("insert into password3 values('"+categor+"','"+encryptedText+"');");
                        Cursor c=s.rawQuery("select * from password3 where category='"+categor+"';",null);
                        c.moveToFirst();
                        System.out.println(c.getString(1));


                        c.close();



                    }
                    catch (Exception e)
                    {
                        System.out.print("Error :"+e);
                    }



                    cat.setVisibility(View.INVISIBLE);
                    pass.setVisibility(View.INVISIBLE);
                    cpass.setVisibility(View.INVISIBLE);
                    TextView t=(TextView)findViewById(R.id.textView11);
                    TextView t1=(TextView)findViewById(R.id.textView12);
                    TextView t2=(TextView)findViewById(R.id.textView13);
                    TextView t3=(TextView)findViewById(R.id.textView14);
                    TextView t5=(TextView)findViewById(R.id.textView15);
                    t.setVisibility(View.INVISIBLE);
                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t5.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);


                    t4.setVisibility(View.VISIBLE);
                    i.setVisibility(View.VISIBLE);

t18.setText("Status : Success");
                    t4.setText("Your password has been successfully added and\n encrpted with AES-128 bit encryption");


                    add.setVisibility(View.INVISIBLE);

                    // Decode the encoded data with AES







                }
            }


        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final EditText cat=(EditText)findViewById(R.id.editText3);
        final EditText pass=(EditText)findViewById(R.id.editText2);
        final EditText cpass=(EditText)findViewById(R.id.editText4);
        final ImageButton add=(ImageButton)findViewById(R.id.button3);
        final TextView t4=(TextView)findViewById(R.id.textView17);
        final Spinner staticSpinner = (Spinner) findViewById(R.id.spinner);
        final TextView t18=(TextView)findViewById(R.id.textView14);
        final ImageView i=(ImageView)findViewById(R.id.imageView3);

        // On selecting a spinner item
        final String cate = parent.getItemAtPosition(position).toString().toUpperCase();
        if(cate=="CUSTOM")
        {

            System.out.println("I'm here");

            staticSpinner.setVisibility(View.INVISIBLE);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String category = cat.getText().toString();
                    String categor=category.toUpperCase();
                    String password = pass.getText().toString();
                    String cpassword = cpass.getText().toString();
                    if (category.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
                        Toast.makeText(Add.this, "Enter all the details", Toast.LENGTH_LONG).show();
                    } else if (!password.equals(cpassword)) {
                        Toast.makeText(Add.this, "Passwords does not match", Toast.LENGTH_LONG).show();

                    } else {
                        try {
                            MessageDigest digest = MessageDigest.getInstance("SHA-256");
                            digest.update("HelloWorld".getBytes("UTF-8"));
                            byte[] keyBytes = new byte[32];
                            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

                            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                            key = new SecretKeySpec(keyBytes, "AES");
                            spec = getIV();
                            encryptedText = encrypt(password);

                            SQLiteDatabase s=openOrCreateDatabase("pass8",MODE_PRIVATE,null);
                            s.execSQL("create table if not exists password8(category varchar(30),encryptedpass varchar(256));");
                            s.execSQL("insert into password8 values('"+categor+"','"+encryptedText+"');");
                            Cursor c=s.rawQuery("select * from password8 where category='"+categor+"';",null);
                            c.moveToFirst();
                            System.out.println(c.getString(1));


                            c.close();



                        }
                        catch (Exception e)
                        {
                            System.out.print("Error :"+e);
                        }



                        cat.setVisibility(View.INVISIBLE);
                        pass.setVisibility(View.INVISIBLE);
                        cpass.setVisibility(View.INVISIBLE);
                        TextView t=(TextView)findViewById(R.id.textView11);
                        TextView t1=(TextView)findViewById(R.id.textView12);
                        TextView t2=(TextView)findViewById(R.id.textView13);
                        TextView t3=(TextView)findViewById(R.id.textView14);
                        TextView t5=(TextView)findViewById(R.id.textView15);
                        t.setVisibility(View.INVISIBLE);
                        t1.setVisibility(View.INVISIBLE);
                        t2.setVisibility(View.INVISIBLE);
                        t5.setVisibility(View.INVISIBLE);
                        t3.setVisibility(View.INVISIBLE);


                        t4.setVisibility(View.VISIBLE);
                        i.setVisibility(View.VISIBLE);
                        staticSpinner.setVisibility(View.INVISIBLE);
                        t18.setText("Status : Success");
                        t4.setText("Your password has been successfully added and\n encrpted with AES-128 bit encryption");


                        add.setVisibility(View.INVISIBLE);

                        // Decode the encoded data with AES







                    }
                }


            });

        }
        else{
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String categor=cate;
                    String password = pass.getText().toString();


                    String cpassword = cpass.getText().toString();
                    if ( password=="" || cpassword=="") {
                        Toast.makeText(Add.this, "Enter all the details", Toast.LENGTH_LONG).show();
                    } else if (!password.equals(cpassword)) {
                        Toast.makeText(Add.this, "Passwords does not match", Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(Add.this, cate, Toast.LENGTH_LONG).show();
                        try {
                            MessageDigest digest = MessageDigest.getInstance("SHA-256");
                            digest.update("HelloWorld".getBytes("UTF-8"));
                            byte[] keyBytes = new byte[32];
                            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

                            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                            key = new SecretKeySpec(keyBytes, "AES");
                            spec = getIV();
                            encryptedText = encrypt(password);
                            Toast.makeText(Add.this, cate + encryptedText, Toast.LENGTH_LONG).show();

                            SQLiteDatabase s=openOrCreateDatabase("pass8",MODE_PRIVATE,null);
                            s.execSQL("create table if not exists password8(category varchar(30),encryptedpass varchar(256));");
                            s.execSQL("insert into password8 values('"+categor+"','"+encryptedText+"');");
                            Toast.makeText(Add.this,categor+encryptedText, Toast.LENGTH_LONG).show();
                            Cursor c=s.rawQuery("select * from password8 where category='"+categor+"';",null);

                            c.moveToFirst();
                            System.out.println(c.getString(1)+c.getString(1));


                            c.close();



                        }
                        catch (Exception e)
                        {
                            System.out.print("Error :"+e);
                        }



                        cat.setVisibility(View.INVISIBLE);
                        pass.setVisibility(View.INVISIBLE);
                        cpass.setVisibility(View.INVISIBLE);
                        TextView t=(TextView)findViewById(R.id.textView11);
                        TextView t1=(TextView)findViewById(R.id.textView12);
                        TextView t2=(TextView)findViewById(R.id.textView13);
                        TextView t3=(TextView)findViewById(R.id.textView14);
                        TextView t5=(TextView)findViewById(R.id.textView15);
                        t.setVisibility(View.INVISIBLE);
                        t1.setVisibility(View.INVISIBLE);
                        t2.setVisibility(View.INVISIBLE);
                        t5.setVisibility(View.INVISIBLE);
                        t3.setVisibility(View.INVISIBLE);


                        t4.setVisibility(View.VISIBLE);
                        i.setVisibility(View.VISIBLE);
                        staticSpinner.setVisibility(View.INVISIBLE);

                        t18.setText("Status : Success");
                        t4.setText("Your password has been successfully added and\n encrpted with AES-128 bit encryption");


                        add.setVisibility(View.INVISIBLE);

                        // Decode the encoded data with AES







                    }
                }


            });

        }
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: "+cate, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public AlgorithmParameterSpec getIV()
    {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }

    public String encrypt(String password) throws Exception
    {
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(password.getBytes("UTF-8"));
        String encryptedText = new String(Base64.encode(encrypted, Base64.DEFAULT), "UTF-8");

        return encryptedText;
    }





}
