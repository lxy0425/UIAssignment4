/***
  Copyright (c) 2008-2014 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain	a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
	
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
 */

package com.example.apple.threaddatabase;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.util.ArrayList;


public class ConstantsFragment extends Fragment implements View.OnClickListener{
private DataBaseConnector db=null;
ArrayList<String>name = new ArrayList<>();
ArrayList<String>nametemp = new ArrayList<>();
Cursor current = null;
ProgressBar pb;
Button search;
LoadCursorTask task;
EditText editText;
static String url;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle savedInstanceState) {
      super.onCreateView(inflater, viewGroup, savedInstanceState);
      return inflater.inflate(R.layout.constants, viewGroup, false);
  }

  public void onActivityCreated(Bundle savedInstanceState){
      super.onActivityCreated(savedInstanceState);
      editText = (EditText)getView().findViewById(R.id.editTextc);
      editText.setText(((ConstantsBrowser)getActivity()).geturl());
      db = new DataBaseConnector(getActivity().getApplicationContext());
      if (current==null) {
          db = new DataBaseConnector(getActivity().getApplicationContext());
          task = new LoadCursorTask();
          task.execute();
    }

      pb = (ProgressBar)getView().findViewById(R.id.bar);
      search = (Button)getView().findViewById(R.id.search);
      search.setOnClickListener(this);
  }

    public void onClick(View view){
//        getFragmentManager().beginTransaction().replace(android.R.id.content,new ElementFragment()).commit();
        Intent intent = new Intent();
        intent.putExtra("information",nametemp);
        intent.setClass(getActivity(), Dossier_Activity.class);
        getActivity().startActivity(intent);
    }

  @Override
  public void onDestroy() {
    super.onDestroy();
    db.close();
  }

  abstract private class BaseTask<T> extends AsyncTask<T, Void, Cursor> {
    @Override
    public void onPostExecute(Cursor result) {

    }
    protected Cursor doQuery() {
      try{
          db.open();
      }
      catch (Exception e){

      }
      Cursor result = db.getNode();
      result.getCount();
        nametemp.clear();
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext())
        {
            int nameColumn = result.getColumnIndex(db.TITLE);
            nametemp.add(result.getString(nameColumn));
            int bioColumn = result.getColumnIndex(db.BIO);
            nametemp.add(result.getString(bioColumn));
            int picColumn = result.getColumnIndex(db.PICTURE);
            nametemp.add(result.getString(picColumn));
        }
        ((ConstantsBrowser)getActivity()).setName1(nametemp);
 //       db.DeleteNote();
        db.close();
      return(result);
    }
  }

  private class LoadCursorTask extends BaseTask<Void> {
    @Override
    protected Cursor doInBackground(Void... params) {
        Document doc = null;
            try {
                  url = ((ConstantsBrowser)getActivity()).geturl();
                  doc = Jsoup.connect(url).get();
//                String html = "<pre>Elon Musk<br/>"+
//                "Elon Reeve Musk (born June 28, 1971) is a South Africa-born, Canadian-American entrepreneur, engineer, inventor and investor. He is the CEO and CTO of SpaceX, CEO and chief product architect of Tesla Motors, and chairman of SolarCity. He is the founder of SpaceX and a cofounder of PayPal, Tesla Motors, and Zip2. He has also envisioned a conceptual high-speed transportation system known as the Hyperloop.<br/>"+
//                "pics/elon.jpg<br/>"+
//                "Yang Wang<br/>"+
//                "Yang Wang (born February 12, 1948) is an American author, computer scientist, inventor, futurist, and is a director of engineering at Google. Aside from futurology, he is involved in fields such as optical character recognition (OCR), text-to-speech synthesis, speech recognition technology, and electronic keyboard instruments. He has written books on health, artificial intelligence (AI), transhumanism, the technological singularity, and futurism. Kurzweil is a public advocate for the futurist and transhumanist movements, as has been displayed in his vast collection of public talks, wherein he has shared his primarily optimistic outlooks on life extension technologies and the future of nanotechnology, robotics, and biotechnology.<br/>"+
//                "pics/kurzweil.jpg<br/>"+
//                "Steve Jobs"+
//                "Steven Paul 'Steve' Jobs (February 24, 1955 â€“ October 5, 2011) was an American entrepreneur, marketer, and inventor, who was the cofounder, chairman, and CEO of Apple Inc. Through Apple, he is widely recognized as a charismatic and design-driven pioneer of the personal computer revolution and for his influential career in the computer and consumer electronics fields, transforming 'one industry after another, from computers and smartphones to music and movies.'<br/>"+
//                "pics/jobs.jpg<br/>"+
//                "Yang Wang<br/>"+
//                "Yang Wang (born February 12, 1948) is an American author, computer scientist, inventor, futurist, and is a director of engineering at Google. Aside from futurology, he is involved in fields such as optical character recognition (OCR), text-to-speech synthesis, speech recognition technology, and electronic keyboard instruments. He has written books on health, artificial intelligence (AI), transhumanism, the technological singularity, and futurism. Kurzweil is a public advocate for the futurist and transhumanist movements, as has been displayed in his vast collection of public talks, wherein he has shared his primarily optimistic outlooks on life extension technologies and the future of nanotechnology, robotics, and biotechnology.<br/>"+
//                "pics/kurzweil.jpg<br/></pre>";
//                doc = Jsoup.parse();
                Elements divs = doc.select("body");
 //               Element divs = doc.select("body").first();
                String s = divs.text();
                System.out.print(s);
                String[] ss = s.split(" ");
                ArrayList<Integer> indexRecord = new ArrayList<>();
                indexRecord.add(-1);
                for(int i = 0 ; i < ss.length; i++){
                    if(ss[i].contains("/")){
                        indexRecord.add(i);
                    }
                }
                name.clear();
                for(int i = 0 ; i < indexRecord.size(); i++){
                    int start = indexRecord.get(i)+1;
                    name.add(ss[start]+" "+ss[start+1]);
                    if(i < indexRecord.size()-1) {
                        int end = indexRecord.get(i + 1);
                        String bio = "";
                        for (int j = start + 2; j < end; j++){
                            bio += ss[j]+" ";
                        }
                        name.add(bio);
                        name.add(ss[end]);
                    }
                }
            }
            catch(Exception e){
                doc = null;
            }
        for(int i = 0 ; i < name.size()-2; i+=3) {
            db.InsertNote(name.get(i),name.get(i+1),name.get(i+2));
        }
        return(doQuery());
    }

      @Override
      public void onPostExecute(Cursor result) {
          super.onPostExecute(result);
          pb.setVisibility(View.INVISIBLE);
          search.setEnabled(true);
          Toast.makeText(getActivity(),"The data has been loaded",Toast.LENGTH_SHORT).show();
          task = null;
      }
  }
}
