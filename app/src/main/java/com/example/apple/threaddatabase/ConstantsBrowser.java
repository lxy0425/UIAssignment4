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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ConstantsBrowser extends Activity{
    Button populate;
    Button search;
    ArrayList<String> name1 = new ArrayList<>();
  @Override
public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.start);
    populate = (Button)findViewById(R.id.populate);
    search = (Button)findViewById(R.id.search);
    View.OnClickListener l = new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (getFragmentManager().findFragmentById(android.R.id.content)==null) {
                  getFragmentManager().beginTransaction()
                          .replace(android.R.id.content,
                                  new ConstantsFragment()).commit();
              }

          }
          };
      populate.setOnClickListener(l);
      }
    public ArrayList<String> getName1(){
        return name1;
    }
    public void setName1(ArrayList<String> name){
        name1.clear();
        name1 = name;
    }
  }
