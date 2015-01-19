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


import android.app.Fragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;


public class ConstantsFragment extends Fragment implements View.OnClickListener{
private DataBaseConnector db=null;
ArrayList<String>name = new ArrayList<>();
ArrayList<String>nametemp = new ArrayList<>();
Cursor current = null;
ProgressBar pb;
Button search;
LoadCursorTask task;
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
        getFragmentManager().beginTransaction().replace(android.R.id.content,new FragmentWeb()).commit();
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
        }
        ((ConstantsBrowser)getActivity()).setName1(nametemp);
        db.DeleteNote();
        db.close();
      return(result);
    }
  }

  private class LoadCursorTask extends BaseTask<Void> {
    @Override
    protected Cursor doInBackground(Void... params) {
        Document doc = null;
            try {
                doc = Jsoup.connect("http://www.eecg.utoronto.ca/~jayar/PeopleList").get();
                Elements divs = doc.select("body");
                String s = divs.text();
                String[] ss = s.split(" ");
                name.clear();
                for(int i = 0 ; i < ss.length-1; i+=2){
                    name.add(ss[i]+" "+ss[i+1]);
                }
            }
            catch(Exception e){
                doc = null;
            }
        for(int i = 0 ; i < name.size() ; i++)
            db.InsertNote(name.get(i));
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
