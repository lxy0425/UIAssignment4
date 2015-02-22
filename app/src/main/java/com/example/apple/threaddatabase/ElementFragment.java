package com.example.apple.threaddatabase;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ElementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ElementFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    ArrayList<String> temp = new ArrayList<String>();
    String[] item = new String[3];
    TextView textView1;
    TextView textView2;
    ImageView imageView;
    Button button1;
    Button button2;
    DownLoadImage task;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ElementFragment newInstance(int sectionNumber) {
        ElementFragment fragment = new ElementFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ElementFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_dossier_, container, false);
        return rootView;
    }
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        Bundle bundle1 = this.getArguments();
        if(bundle1 != null) {
            int i = bundle1.getInt(ARG_SECTION_NUMBER) - 1;
            temp = ((Dossier_Activity) getActivity()).getInformation();
            for (int j = 0; j < 3; j++)
                item[j] = temp.get(i * 3 + j);
            textView1 = (TextView) getView().findViewById(R.id.name);
            textView1.setText(item[0]);
            textView2 = (TextView) getView().findViewById(R.id.bio);
            textView2.setText(item[1]);
            int line = textView2.getLineCount();
            while(line++ < 18)
                textView2.append("\n");
            textView2.setMovementMethod(ScrollingMovementMethod.getInstance());
            textView2.setScrollbarFadingEnabled(false);
            imageView = (ImageView) getView().findViewById(R.id.picture);
            button1 = (Button) getView().findViewById(R.id.search);
            button2 = (Button) getView().findViewById(R.id.back);
            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("name", item[0]);
                    intent.setClass(getActivity(), WebActivity.class);
                    startActivity(intent);
                }
            };
            View.OnClickListener m = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ConstantsBrowser.class));
                    getFragmentManager().beginTransaction().addToBackStack(null).commit();
                }
            };
            button1.setOnClickListener(l);
            button2.setOnClickListener(m);
            StringBuffer url = new StringBuffer(new ConstantsFragment().url);
            int end = url.lastIndexOf("/");
            String s = url.substring(0,end) + "/"+item[2];
            task = new DownLoadImage(imageView);
            task.execute(s);
        }
    }
    private class DownLoadImage extends AsyncTask<String, Integer, Bitmap> {
        public DownLoadImage(ImageView is) {
            imageView = is;
        }
    protected Bitmap doInBackground(String... urls) {
        System.out.println("start");
        String url =urls[0];
        System.out.println(url);
        Bitmap tmpBitmap = null;
        try {
            InputStream is = new java.net.URL(url).openStream();
            tmpBitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Download", e.getMessage());
        }
        return tmpBitmap;

    }



    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        System.out.println("Progress："+values);
    }
    protected void onPostExecute(Bitmap result) {
        //TODO:
        Resources res=getResources();
        Drawable bd=new BitmapDrawable(res,result);
        imageView.setImageDrawable(bd);
        System.out.println("finished！");
        task =  null;
    }
}

}
