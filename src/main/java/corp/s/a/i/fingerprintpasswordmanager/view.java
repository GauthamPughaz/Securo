package corp.s.a.i.fingerprintpasswordmanager;

/**
 * Created by Gautham Leo on 15-08-2016.
*/

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.app.ActionBar;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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


public class view extends AppCompatActivity {
    static public String TAG = "Symmetric AES";
    ListView simpleList;
    private Cipher cipher=null;
    private SecretKeySpec key=null;
    private AlgorithmParameterSpec spec;
    String encryptedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view1);
        final TextView t=(TextView)findViewById(R.id.textView12);
        final TextView t1=(TextView)findViewById(R.id.textView15);
        final EditText cat=(EditText)findViewById(R.id.editText4);
        ImageButton search=(ImageButton)findViewById(R.id.button2);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/segoeui.ttf");
        t.setTypeface(typeface);
        t1.setTypeface(typeface);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=cat.getText().toString();
                if(s=="")
                {
                    Toast.makeText(getApplicationContext(),"Enter a valid category",Toast.LENGTH_LONG).show();
                }
                else
                {

                    s=s.toUpperCase();
                    SQLiteDatabase s1=openOrCreateDatabase("pass8",MODE_PRIVATE,null);
                    Cursor c=s1.rawQuery("select * from password8 where category='"+s+"';",null);
                    c.moveToFirst();

                    String[] Cat=new String[c.getCount()];
                    String[] pass=new String[c.getCount()];
                    int[] img=new int[c.getCount()];
                    try {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        digest.update("HelloWorld".getBytes("UTF-8"));
                        byte[] keyBytes = new byte[32];
                        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

                        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                        key = new SecretKeySpec(keyBytes, "AES");
                        spec = getIV();
                    }
                    catch (Exception e)
                    {
                        System.out.print("Error :"+e);
                    }
                    for(int i=0;i<c.getCount();i++){
                        Cat[i]=c.getString(0);
                        System.out.println(c.getString(0));
                        String g=c.getString(1);

                           try {
                               pass[i] = decrypt(g);
                           }
                           catch(Exception e) {

                               System.out.print(e);
                           }



                        System.out.println(R.drawable.gmail);


                        if(Cat[i]=="GMAIL")
                            img[i]=R.drawable.gmail;
                        else if(Cat[i]=="FACEBOOK")
                            img[i]=R.drawable.arrow;
                        else if(Cat[i]=="LINKEDIN")
                            img[i]=R.drawable.arrow;
                        else if(Cat[i]=="OUTLOOK")
                            img[i]=R.drawable.arrow;
                        else if(Cat[i]=="YAHOO")
                            img[i]=R.drawable.arrow;
                        else
                            img[i]=R.drawable.arrow;


                        c.moveToNext();
                    }

                    c.close();
                    simpleList = (ListView) findViewById(R.id.listView1);
                    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), pass,Cat);
                   // Toast.makeText(view.this,pass[0], Toast.LENGTH_LONG).show();
                    simpleList.setAdapter(customAdapter);



                }
            }
        });

    }
    public AlgorithmParameterSpec getIV()
    {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }
    public String decrypt(String cryptedText) throws Exception
    {
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(bytes);
        String decryptedText = new String(decrypted, "UTF-8");

        return decryptedText;
    }
}