
package com.adsoft.girls_tatoos_gallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class HorizontalLayoutFragment extends Activity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnTouchListener {
    private View mViewRoot;

    private Toast mToast;
    private ImageView saveBtn, sharebtn, wallpaperBtn, moreapp, gridBtn, prev, next, playimage, musicBtn;
    TextView sharetxt, wallpapertxt, downtxt, gridtxt, moreapptxt, disclamerTxt, reviewTxt;
    private AdView adView;
    private GridView gridView;
    String Myprefrence;
    private SliderLayout sliderLayout;
    private LinearLayout reviewLayout, disclaimerLayout,download_text_layout,wall_text_layout,wall_image_layout,download_image_layout,text_layout,image_layout;
    RelativeLayout llayout;
    LayoutAdapter adapter;
    int time = 0;
    static int h = 0, w = 0;
    CustomAdapter customAdapter;
    private static int tag = 0;
    long TIME =3000;

    private int[] imageList = {R.drawable.a0, R.drawable.a1, R.drawable.a2, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9,
            R.drawable.a10, R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18,
            R.drawable.a19, R.drawable.a20, R.drawable.a21, R.drawable.a22, R.drawable.a23, R.drawable.a24, R.drawable.a25, R.drawable.a26, R.drawable.a27,
            R.drawable.a28, R.drawable.a29, R.drawable.a30, R.drawable.a31, R.drawable.a32, R.drawable.a33, R.drawable.a34, R.drawable.a35, R.drawable.a36,
            R.drawable.a37, R.drawable.a38, R.drawable.a39, R.drawable.a40, R.drawable.a41, R.drawable.a42, R.drawable.a43, R.drawable.a45,
            R.drawable.a46, R.drawable.a47, R.drawable.a48, R.drawable.a49, R.drawable.a50, R.drawable.a51, R.drawable.a52, R.drawable.a53, R.drawable.a54,
            R.drawable.a55, R.drawable.a56, R.drawable.a57, R.drawable.a58, R.drawable.a59, R.drawable.a60, R.drawable.a61, R.drawable.a62, R.drawable.a63,
            R.drawable.a64, R.drawable.a65, R.drawable.a66, R.drawable.a67, R.drawable.a68, R.drawable.a69, R.drawable.a70, R.drawable.a71, R.drawable.a72, R.drawable.a73, R.drawable.a74, R.drawable.a75, R.drawable.a76, R.drawable.a77, R.drawable.a78, R.drawable.a79, R.drawable.a80, R.drawable.a81,
            R.drawable.a82, R.drawable.a83, R.drawable.a84, R.drawable.a85, R.drawable.a86, R.drawable.a87, R.drawable.a88};
    private InterstitialAd mInterstitialAd;

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_horizontal);
        mToast = Toast.makeText(HorizontalLayoutFragment.this, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(Util.InterstitialAdId);
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        init();
        addSlider();


        if (PreferenceConnector.readString(HorizontalLayoutFragment.this, PreferenceConnector.FEEDBACK_POPUP, "").equals("1")) {
            final Dialog dialog = new Dialog(HorizontalLayoutFragment.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.popup_for_gallry);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView popup_text = (TextView) dialog.findViewById(R.id.popup_text);
            ImageView free_btn = (ImageView) dialog.findViewById(R.id.no);
            ImageView paid_btn = (ImageView) dialog.findViewById(R.id.yes);
            dialog.show();

            paid_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    review();
                    PreferenceConnector.writeString(HorizontalLayoutFragment.this, PreferenceConnector.FEEDBACK_POPUP, "0");

                }
            });
            free_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    PreferenceConnector.writeString(HorizontalLayoutFragment.this, PreferenceConnector.FEEDBACK_POPUP, "0");
                }
            });
        }

        if (PreferenceConnector.readString(HorizontalLayoutFragment.this, PreferenceConnector.FEEDBACK_POPUP, "").equals("")) {
            PreferenceConnector.writeString(HorizontalLayoutFragment.this, PreferenceConnector.FEEDBACK_POPUP, "1");
        }

        wallpapertxt.setSelected(true);
        downtxt.setSelected(true);
        moreapptxt.setSelected(true);
        gridtxt.setSelected(true);
        sharetxt.setSelected(true);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                sliderLayout.movePrevPosition();
                sliderLayout.stopAutoCycle();
                tag = 0;

                playimage.setImageResource(R.drawable.play);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                tag = 0;
                sliderLayout.moveNextPosition();
                sliderLayout.stopAutoCycle();
                playimage.setImageResource(R.drawable.play);
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);

                sliderLayout.stopAutoCycle();
                playimage.setImageResource(R.drawable.play);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HorizontalLayoutFragment.this);
                alertDialogBuilder.setMessage("Are you sure, you want to Save this picture?  ");
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        int pos = sliderLayout.getCurrentPosition();
                        downlodeImage("" + imageList[pos], imageList[pos]);

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);

                sliderLayout.stopAutoCycle();
                int pos = sliderLayout.getCurrentPosition();
                shareImage(imageList[pos]);

            }
        });
        disclaimerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HorizontalLayoutFragment.this);
                alertDialogBuilder.setTitle("About Disclaimer");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setMessage(Util.DisclaimerText);


                alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        wallpaperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);

                sliderLayout.stopAutoCycle();
                playimage.setImageResource(R.drawable.play);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HorizontalLayoutFragment.this);
                alertDialogBuilder.setMessage("Are you sure, you want to set this picture as Wallpaper? ");
                alertDialogBuilder.setCancelable(false);


                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        setWallPaper();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        moreapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);

                moreApp();
            }
        });

        gridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);


                if (sliderLayout.getVisibility() == View.GONE) {
                    download_text_layout.setVisibility(View.VISIBLE);
                    image_layout.setWeightSum(2.5f);
                    text_layout.setWeightSum(2.5f);
                    wall_text_layout.setVisibility(View.VISIBLE);
                    wall_image_layout.setVisibility(View.VISIBLE);
                    download_image_layout.setVisibility(View.VISIBLE);
                    sliderLayout.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                    playimage.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);

                    gridBtn.setImageResource(R.drawable.grid);
                    gridtxt.setText(getResources().getString(R.string.grid));
                    tag = 0;
                    playimage.setImageResource(R.drawable.play);
                    popUpShow();
                } else {
                    sliderLayout.setVisibility(View.GONE);
                    image_layout.setWeightSum(1.5f);
                    text_layout.setWeightSum(1.5f);
                    download_text_layout.setVisibility(View.GONE);
                    wall_text_layout.setVisibility(View.GONE);
                    wall_image_layout.setVisibility(View.GONE);
                    download_image_layout.setVisibility(View.GONE);

                    gridBtn.setImageResource(R.drawable.slideshow);
                    gridtxt.setText(getResources().getString(R.string.slide_show));
                    next.setVisibility(View.GONE);
                    prev.setVisibility(View.GONE);
                    playimage.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                    sliderLayout.stopAutoCycle();
                    CustomAdapter cadapter = new CustomAdapter(HorizontalLayoutFragment.this, imageList);
                    gridView.setAdapter(cadapter);
                    tag = 0;
                    playimage.setImageResource(R.drawable.play);
                }
            }
        });

        sharetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);

                sliderLayout.stopAutoCycle();
                int pos = sliderLayout.getCurrentPosition();
                shareImage(imageList[pos]);

            }
        });
        wallpapertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);


                sliderLayout.stopAutoCycle();
                playimage.setImageResource(R.drawable.play);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HorizontalLayoutFragment.this);
                alertDialogBuilder.setMessage("Are you sure, you want to set this picture as Wallpaper? ");
                alertDialogBuilder.setCancelable(false);


                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        setWallPaper();

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        downtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);

                sliderLayout.stopAutoCycle();
                playimage.setImageResource(R.drawable.play);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HorizontalLayoutFragment.this);
                alertDialogBuilder.setMessage("Are you sure, you want to Save this picture?  ");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        int pos = sliderLayout.getCurrentPosition();
                        downlodeImage("" + imageList[pos], imageList[pos]);

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        gridtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);


                if (sliderLayout.getVisibility() == View.GONE) {
                    download_text_layout.setVisibility(View.VISIBLE);
                    image_layout.setWeightSum(2.5f);
                    text_layout.setWeightSum(2.5f);
                    wall_text_layout.setVisibility(View.VISIBLE);
                    wall_image_layout.setVisibility(View.VISIBLE);
                    download_image_layout.setVisibility(View.VISIBLE);
                    sliderLayout.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                    playimage.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);

                    gridBtn.setImageResource(R.drawable.grid);
                    gridtxt.setText(getResources().getString(R.string.grid));
                    popUpShow();
                    tag = 0;
                    playimage.setImageResource(R.drawable.play);
                } else {
                    sliderLayout.setVisibility(View.GONE);
                    image_layout.setWeightSum(1.5f);
                    text_layout.setWeightSum(1.5f);
                    download_text_layout.setVisibility(View.GONE);
                    wall_text_layout.setVisibility(View.GONE);
                    wall_image_layout.setVisibility(View.GONE);
                    download_image_layout.setVisibility(View.GONE);
                    gridBtn.setImageResource(R.drawable.slideshow);

                    gridtxt.setText(getResources().getString(R.string.slide_show));
                    next.setVisibility(View.GONE);
                    prev.setVisibility(View.GONE);
                    playimage.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                    sliderLayout.stopAutoCycle();
                    tag = 0;
                    playimage.setImageResource(R.drawable.play);

                    CustomAdapter cadapter = new CustomAdapter(HorizontalLayoutFragment.this, imageList);
                    gridView.setAdapter(cadapter);
                }

            }
        });

        moreapptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                }, TIME);
                moreApp();
            }
        });

        playimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popUpShow();
            }
        });
        reviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review();
            }
        });
      /*   SpannableString content = new SpannableString("Disclaimer");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        SpannableString content1 = new SpannableString("Review");
        content1.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        disclamerTxt.setText(content);
        reviewTxt.setText(content1);*/


    }

    void popUpShow() {

        if (tag == 0) {


            final Dialog dialog = new Dialog(HorizontalLayoutFragment.this);
            // Include dialog.xml file
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.pop_up_layout);


            final TextView twosec = (TextView) dialog.findViewById(R.id.twosec);
            final TextView foursec = (TextView) dialog.findViewById(R.id.foursec);
            final TextView sixsec = (TextView) dialog.findViewById(R.id.sixsec);
            final TextView eightsec = (TextView) dialog.findViewById(R.id.eightsec);
            final TextView tensec = (TextView) dialog.findViewById(R.id.tensec);
            dialog.show();

            ImageView declineButton = (ImageView) dialog.findViewById(R.id.cancelslad);
            // if decline button is clicked, close the custom dialog
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    dialog.dismiss();

                }
            });
            twosec.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              tag = 1;
                                              time = 2000;
                                              dialog.dismiss();
                                              sliderLayout.setDuration(time);
                                              playimage.setImageResource(R.drawable.pause);
                                              sliderLayout.startAutoCycle();
                                              twosec.setTextColor(0x00ff6f00);
                                              foursec.setTextColor(0x00000000);
                                              sixsec.setTextColor(0x00000000);
                                              eightsec.setTextColor(0x00000000);
                                              tensec.setTextColor(0x00000000);
                                          }
                                      }
            );
            foursec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tag = 1;
                    time = 4000;
                    dialog.dismiss();
                    sliderLayout.setDuration(time);
                    playimage.setImageResource(R.drawable.pause);
                    sliderLayout.startAutoCycle();
                    twosec.setTextColor(0x00000000);
                    foursec.setTextColor(0x00ff6f00);
                    sixsec.setTextColor(0x00000000);
                    eightsec.setTextColor(0x00000000);
                    tensec.setTextColor(0x00000000);
                }
            });
            sixsec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tag = 1;
                    time = 6000;
                    dialog.dismiss();
                    sliderLayout.setDuration(time);
                    playimage.setImageResource(R.drawable.pause);
                    sliderLayout.startAutoCycle();
                    twosec.setTextColor(0x00000000);
                    foursec.setTextColor(0x00000000);
                    sixsec.setTextColor(0x00ff6f00);
                    eightsec.setTextColor(0x00000000);
                    tensec.setTextColor(0x00000000);

                }
            });
            eightsec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    time = 8000;
                    tag = 1;
                    dialog.dismiss();
                    sliderLayout.setDuration(time);
                    playimage.setImageResource(R.drawable.pause);
                    sliderLayout.startAutoCycle();
                    twosec.setTextColor(0x00000000);
                    foursec.setTextColor(0x00000000);
                    sixsec.setTextColor(0x00000000);
                    eightsec.setTextColor(0x00ff6f00);
                    tensec.setTextColor(0x00000000);

                }
            });
            tensec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    time = 10000;
                    tag = 1;
                    dialog.dismiss();
                    sliderLayout.setDuration(time);
                    playimage.setImageResource(R.drawable.pause);
                    sliderLayout.startAutoCycle();
                    twosec.setTextColor(0x00000000);
                    foursec.setTextColor(0x00000000);
                    sixsec.setTextColor(0x00000000);
                    eightsec.setTextColor(0x00000000);
                    tensec.setTextColor(0x00ff6f00);

                }
            });

        } else {
            tag = 0;
            sliderLayout.stopAutoCycle();
            playimage.setImageResource(R.drawable.play);
        }

    }

    void review() {
        sliderLayout.stopAutoCycle();
        tag = 0;
        playimage.setImageResource(R.drawable.play);
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Util.Review_Application)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Util.Review_Url)));
        }
        PreferenceConnector.writeString(HorizontalLayoutFragment.this, PreferenceConnector.FEEDBACK_POPUP, "0");
    }

    void moreApp() {
        sliderLayout.stopAutoCycle();
        tag = 0;
        playimage.setImageResource(R.drawable.play);
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Util.MoreApp_Application)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Util.MoreApp_Url)));
        }
    }

    void addSlider() {
        for (int i = 0; i < imageList.length; i++) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(HorizontalLayoutFragment.this);
            // initialize a SliderLayout
            defaultSliderView
                    .image(imageList[i])
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener(HorizontalLayoutFragment.this);


            //add your extra information
            defaultSliderView.bundle(new Bundle());
            defaultSliderView.getBundle()
                    .putInt("extra", imageList[i]);
            sliderLayout.addSlider(defaultSliderView);
        }
        sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(time);
        sliderLayout.stopAutoCycle();
        sliderLayout.addOnPageChangeListener(HorizontalLayoutFragment.this);
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        sliderLayout.stopAutoCycle();
        tag = 0;
        playimage.setImageResource(R.drawable.play);
        if (Myapplication.mediaPlayer != null && Myapplication.mediaPlayer.isPlaying() && Myapplication.tag == 0) {
            Myapplication.mediaPlayer.pause();
        }
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        sliderLayout.stopAutoCycle();
        tag = 0;
        playimage.setImageResource(R.drawable.play);
        int s = sliderLayout.getCurrentPosition();
        Myapplication.tag = 1;
        Intent intent = new Intent(HorizontalLayoutFragment.this, ViewImageActivity.class);
        intent.putExtra("image", imageList[s]);
        startActivity(intent);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("Slider Demo", "Page Changed: " + position);

    }

    @Override
    public void onPageSelected(int position)

    {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d("Slider Demo", "Page Changed: " + state);

    }


    private void shareImage(int res) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), res);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, Util.ShareImageName);
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {

        }
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        share.putExtra(Intent.EXTRA_SUBJECT, Util.Sahare_subject);
        share.putExtra(Intent.EXTRA_TEXT, Util.ShareText);
        startActivity(Intent.createChooser(share, "Share Image"));
    }

    private void downlodeImage(String filename, int res) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), res);
        File f = new File("Download");
        File outFile = null;
        if (f.exists()) {
            outFile = new File(Environment.getExternalStorageDirectory() + "/Download", filename);
        } else {
            File f2 = new File(Environment.getExternalStorageDirectory() + "/downloads");
            if (f2.exists()) {
                outFile = new File(Environment.getExternalStorageDirectory() + "/downloads", filename);
            } else {
                File f3 = new File(Environment.getExternalStorageDirectory() + "/Download");
                f3.mkdir();
                outFile = new File(Environment.getExternalStorageDirectory() + "/Download", filename);
            }
        }
        try {
            FileOutputStream outStream = new FileOutputStream(outFile);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {

        }


    }

    void setWallPaper() {
        int pos = sliderLayout.getCurrentPosition();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageList[pos]);
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(getApplicationContext());
        try {
            myWallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (Myapplication.mediaPlayer != null && Myapplication.mediaPlayer.isPlaying()) {
            Myapplication.isPlaying = 1;
            playimage.setImageResource(R.drawable.play);
        }
        if (Myapplication.mediaPlayer != null && !Myapplication.mediaPlayer.isPlaying() && Myapplication.isPlaying == 1) {
            Myapplication.mediaPlayer.start();
            Myapplication.isPlaying = 1;
        }
        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Myapplication.mediaPlayer != null) {
                    if (Myapplication.mediaPlayer.isPlaying()) {
                        musicBtn.setImageResource(R.drawable.mute);
                        Myapplication.mediaPlayer.pause();
                        Myapplication.isPlaying = 0;
                    } else {
                        Myapplication.mediaPlayer.start();
                        Myapplication.isPlaying = 1;
                        musicBtn.setImageResource(R.drawable.volume);
                    }
                }
            }
        });
        if (PreferenceConnector.readString(HorizontalLayoutFragment.this, PreferenceConnector.SHOWADD, "0").equals("0")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                PreferenceConnector.writeString(HorizontalLayoutFragment.this, PreferenceConnector.SHOWADD, "1");
            }

        }

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                if (Myapplication.mediaPlayer != null && !Myapplication.mediaPlayer.isPlaying() && Myapplication.isPlaying == 1) {
                    Myapplication.mediaPlayer.start();
                    Myapplication.isPlaying = 1;
                    playimage.setImageResource(R.drawable.play);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
       if (event.getPointerCount() > 1) {
           Log.e("Multitouch detected!", "   dsfksjfsf");
            return true;
        } else
            return super.onTouchEvent(event);
    }

    private class CustomAdapter extends BaseAdapter {

        private Context mContext;
        LayoutInflater mInflater;
        GridHolder holder;
        int[] image;
        RelativeLayout.LayoutParams layoutParams;

        public class GridHolder {
            ImageView gridimage;

        }

        public CustomAdapter(Context context, int[] btmpList) {
            this.mContext = context;
            this.image = btmpList;
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (w / 2) - 10);
            mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public Object getItem(int i) {
            return image.length;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {

            if (convertView == null) { // if it's not recycled, initialize some attributes
                convertView = mInflater.inflate(R.layout.grid_item, null);
                holder = new GridHolder();
                holder.gridimage = (ImageView) convertView.findViewById(R.id.gridimage);
                holder.gridimage.setLayoutParams(layoutParams);
                holder.gridimage.setScaleType(ImageView.ScaleType.FIT_XY);
                convertView.setTag(holder);
            } else {
                holder = (GridHolder) convertView.getTag();
            }

            loadBitmap(image[i], holder.gridimage);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Myapplication.tag = 1;
                    Intent intent = new Intent(mContext, ViewImageActivity.class);
                    intent.putExtra("image", image[i]);
                    mContext.startActivity(intent);
                }
            });

            return convertView;
        }


        public void loadBitmap(int resId, ImageView imageView) {
            if (cancelPotentialWork(resId, imageView)) {
                final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
                Bitmap mPlaceHolderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.close);

                final AsyncDrawable asyncDrawable = new AsyncDrawable(getResources(), null, task);
                imageView.setImageDrawable(asyncDrawable);
                task.execute(resId);
            }
        }


    }

    public float convertDipToPixel(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    private Bitmap getRoundedCornerBitmap(Context context, Bitmap bitmap) {
        try {

            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = convertDipToPixel(context, (float) 13);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// draw round
            // 4Corner

            if (!true) {
                Rect rectTL = new Rect(0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                canvas.drawRect(rectTL, paint);
            }
            if (!true) {
                Rect rectTR = new Rect(bitmap.getWidth() / 2, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
                canvas.drawRect(rectTR, paint);
            }
            if (!true) {
                Rect rectBR = new Rect(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth(), bitmap.getHeight());
                canvas.drawRect(rectBR, paint);
            }
            if (!true) {
                Rect rectBL = new Rect(0, bitmap.getHeight() / 2, bitmap.getWidth() / 2, bitmap.getHeight());
                canvas.drawRect(rectBL, paint);
            }

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        } catch (Exception ignored) {
        }
        return bitmap;
    }


    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(getRoundedCornerBitmap(HorizontalLayoutFragment.this, bitmap));
                }
            }
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    void init() {
        llayout = (RelativeLayout) findViewById(R.id.ll);
        saveBtn = (ImageView) findViewById(R.id.saveBtn);
        sharebtn = (ImageView) findViewById(R.id.shareBtn);
        musicBtn = (ImageView) findViewById(R.id.musicBtn);
        wallpaperBtn = (ImageView) findViewById(R.id.wallBtn);
        moreapp = (ImageView) findViewById(R.id.moreapp);
        gridBtn = (ImageView) findViewById(R.id.gridBtn);
        prev = (ImageView) findViewById(R.id.prev);
        next = (ImageView) findViewById(R.id.next);
        sharetxt = (TextView) findViewById(R.id.sharetxt);
        wallpapertxt = (TextView) findViewById(R.id.wallpapertxt);
        downtxt = (TextView) findViewById(R.id.downtxt);
        gridtxt = (TextView) findViewById(R.id.gridtxt);
        moreapptxt = (TextView) findViewById(R.id.moreapptxt);
        playimage = (ImageView) findViewById(R.id.playimage);
        disclamerTxt = (TextView) findViewById(R.id.disclamerTxt);
        reviewTxt = (TextView) findViewById(R.id.reviewTxt);

        LinearLayout addlayout = (LinearLayout) findViewById(R.id.imgBanner1);
        adView = new AdView(this);
        adView.setAdUnitId(Util.BannerAdId);
        adView.setAdSize(AdSize.BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
        addlayout.addView(adView);
        adView.loadAd(adRequest);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        gridView = (GridView) findViewById(R.id.gridview);

        disclaimerLayout = (LinearLayout) findViewById(R.id.disclamerLayout);
        reviewLayout = (LinearLayout) findViewById(R.id.reviewLayout);
        download_text_layout = (LinearLayout) findViewById(R.id.download_text_layout);
        wall_text_layout = (LinearLayout) findViewById(R.id.wall_text_layout);
        wall_image_layout = (LinearLayout) findViewById(R.id.wall_image_layout);
        download_image_layout = (LinearLayout) findViewById(R.id.download_image_layout);
        text_layout = (LinearLayout) findViewById(R.id.text_layout);
        image_layout = (LinearLayout) findViewById(R.id.image_layout);
        Display display = getWindowManager().getDefaultDisplay();
        w = display.getWidth();
        h = display.getHeight();
        if (Myapplication.mediaPlayer != null && Myapplication.mediaPlayer.isPlaying()) {
            musicBtn.setImageResource(R.drawable.volume);
        } else {
            musicBtn.setImageResource(R.drawable.mute);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Myapplication.mediaPlayer != null && Myapplication.mediaPlayer.isPlaying()) {
            Myapplication.mediaPlayer.pause();
        }
        PreferenceConnector.writeString(HorizontalLayoutFragment.this, PreferenceConnector.SHOWADD, "0");
    }
}
