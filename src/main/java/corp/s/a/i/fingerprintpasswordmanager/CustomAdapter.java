package corp.s.a.i.fingerprintpasswordmanager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gautham Leo on 08-09-2016.
 */
public class CustomAdapter extends BaseAdapter {
    Context context;
    String password[];
    String cat[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] pass, String[] cat) {
        this.context = applicationContext;
        this.password = pass;
        this.cat = cat;

    }

    @Override
    public int getCount() {
        return password.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.content,null);

        TextView pass = (TextView) view.findViewById(R.id.textView19);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView4);
        icon.setImageResource(R.drawable.arrow);
        pass.setText(this.password[i]);
        //Toast.makeText(context,this.password[i] +"2", Toast.LENGTH_LONG).show();

        if(cat[i]=="GOOGLE") {

            Drawable drawable = context.getResources().getDrawable(R.drawable.gmail);
            icon.setImageDrawable(drawable);

        }else
        if(cat[i]=="FACEBOOK") {
            Drawable drawable = context.getResources().getDrawable(R.drawable.facebook);
            icon.setImageDrawable(drawable);
        }else
        if(cat[i]=="LINKEDIN") {
            Drawable drawable = context.getResources().getDrawable(R.drawable.linkedin);
            icon.setImageDrawable(drawable);
        }else
        if(cat[i]=="OUTLOOK") {
            Drawable drawable = context.getResources().getDrawable(R.drawable.outlook);
            icon.setImageDrawable(drawable);
        }else
        if(cat[i]=="APPLE") {
            Drawable drawable = context.getResources().getDrawable(R.drawable.mac);
            icon.setImageDrawable(drawable);
        }else{
            Drawable drawable = context.getResources().getDrawable(R.drawable.q);
            icon.setImageDrawable(drawable);
        }



        return view;
    }
}
