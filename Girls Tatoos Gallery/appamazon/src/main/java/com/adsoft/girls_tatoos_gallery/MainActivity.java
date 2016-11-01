/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adsoft.girls_tatoos_gallery;

import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity {
    Fragment fragment;
    ProgressDialog progressDialog;
    private List<String> assetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


      /*  if ((Myapplication.assetList2 != null&&Myapplication.assetList2.size()!=0) &&(Myapplication.btmpList != null&&Myapplication.btmpList.size()!=0)) {
            Intent intent = new Intent(MainActivity.this, HorizontalLayoutFragment.class);
            startActivity(intent);
            finish();

        } else {
            new Task().execute();
        }*/

    }


    private List<String> getImage() throws IOException {
        AssetManager assetManager = getAssets();
        String[] files = assetManager.list("images");
        List<String> it = Arrays.asList(files);
        return it;
    }

    private Bitmap getBitmapFromAsset(String strName) {
        AssetManager assetManager = getResources().getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open("images/" + strName);
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = 2;
            bitmap = BitmapFactory.decodeStream(istr, new Rect(), o2);
            istr.close();
            istr = null;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Exeption", Toast.LENGTH_SHORT).show();
        }



        return bitmap;
    }

   /* class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

            Intent intent = new Intent(MainActivity.this, HorizontalLayoutFragment.class);
            startActivity(intent);
            finish();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                assetList = getImage();
                int size = assetList.size();
                for (int j = 0; j < size; j++) {
                    if (assetList.get(j).contains("_xyz")) {
                        Myapplication.assetList2.add(assetList.get(j));
                    }
                }
                int size2 = Myapplication.assetList2.size();

                for (int i = 0; i < size2; i++) {


                    Myapplication. btmpList.add(getBitmapFromAsset(Myapplication.assetList2.get(i)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }*/
}
