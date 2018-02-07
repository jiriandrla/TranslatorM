package cz.utb.fai.translator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;

import cz.utb.fai.translator.api.ApiClient;
import cz.utb.fai.translator.api.ApiInterface;
import cz.utb.fai.translator.api.pojo.ResponseData;
import cz.utb.fai.translator.api.pojo.ResponseTranslator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cz.utb.fai.translator.R.id.btnOpen;
import static cz.utb.fai.translator.R.id.btnSave;


public class Page_home implements View.OnClickListener{
    private MainActivity mActivity;
    private TextView mResult;
    private Button mTranslate;
    private Button mSave;
    private Button mOpen;
    private Button mDelete;
    private Spinner mInputSpinner;
    private Spinner mOutputSpinner;
    private String[] mAbbrs;
    private EditText mEditText;



    private List<String> mHistory;

    public Page_home(MainActivity activity){
        mActivity = activity;
        mTranslate = (Button)activity.findViewById(R.id.btnTranslate);
        mTranslate.setOnClickListener(this);

        mSave = (Button)activity.findViewById(R.id.btnSave);
        mOpen = (Button)activity.findViewById(R.id.btnOpen);
        mDelete = (Button)activity.findViewById(R.id.btnDelete);

        mSave.setOnClickListener(this);
        mOpen.setOnClickListener(this);
        mDelete.setOnClickListener(this);


        mInputSpinner = (Spinner)activity.findViewById(R.id.inputSpinner);
        // set default value - Czech
        mInputSpinner.setSelection(0);

        mOutputSpinner = (Spinner)activity.findViewById(R.id.outputSpinner);
        // set default value - English
        mOutputSpinner.setSelection(1);

        mAbbrs = activity.getResources().getStringArray(R.array.languages_abbr);

        mResult = (TextView)activity.findViewById(R.id.tvResult);

        mEditText = (EditText) activity.findViewById(R.id.etTranslate);


    }



    @Override
    public void onClick(View view) {//povine prepsano
        int inputPos = mInputSpinner.getSelectedItemPosition();
        int outPos = mOutputSpinner.getSelectedItemPosition();
        switch (view.getId()) {
            case R.id.btnTranslate:


                String translatedText = mEditText.getText().toString();
                if (translatedText.isEmpty()) {
                    // empty text

                } else {
                    System.out.println("uvnitr save");
                    // we have translated text from user input
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseTranslator> call = apiService.getTranslation(translatedText, mAbbrs[inputPos] + '|' + mAbbrs[outPos]);
                    call.enqueue(new MyCall(translatedText, mAbbrs[inputPos], mAbbrs[outPos]));

                    Log.v("transApp", translatedText);
                }
                break;
            case R.id.btnSave:
                System.out.println("uvnitr hhhsave");

                //public static int writeLines(Activity activity, String filename, ArrayList<String> lines) throws IOException {
                int linesWritten = 0;
                Context context = mActivity.getApplicationContext();
                try {
                    FileOutputStream fos = context.openFileOutput("a.txt", Context.MODE_PRIVATE);
                    if(mHistory!=null) {
                        for (String line : mHistory) {
                            // add terminal character so that it doesn't get written as one line
                            fos.write((line + "\n").getBytes());
                            ++linesWritten;
                        }
                        fos.close();
                    }
                } catch (java.io.IOException fnfe) {
                    fnfe.printStackTrace();
                }
              //  return linesWritten;
            //}

                //RecyclerView.Adapter mAdapter=mActivity.returnAdapter();
                //mAdapter.notifyDataSetChanged();
                break;
            case R.id.btnOpen:
                System.out.println("uvnitr openn");
                mHistory=mActivity.getHistoryList();

                mHistory.clear();
                int linesRead = 0;
                Context contextt = mActivity.getApplicationContext();
                try {
                    FileInputStream fos = contextt.openFileInput("a.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fos));
                    String line = reader.readLine();;
                    while (line!=null) {
                        // add terminal character so that it doesn't get written as one line
                        //if(line!= null) {
                            System.out.println(line);
                            mHistory.add(line);
                        //}
                        ++linesRead;
                        //if(reader.)
                        line = reader.readLine();
                    }
                    fos.close();
                } catch (java.io.IOException fnfe) {
                    fnfe.printStackTrace();
                }

                break;
            case R.id.btnDelete:
                System.out.println("uvnitr openn");
                if(mHistory!=null) {
                    mHistory.clear();
                }
                mHistory=mActivity.getHistoryList();
                break;
        }
        RecyclerView.Adapter mAdapter=mActivity.returnAdapter();
        mAdapter.notifyDataSetChanged();
    }

    private class MyCall implements Callback<ResponseTranslator>
    {
        private String mTranslatedText;
        private String i, o;
        private MyCall(String translatedText, String in, String out) {
            mTranslatedText = translatedText;
            i=in;
            o=out;
        }
        @Override
        public void onResponse(Call<ResponseTranslator> call,
                               Response<ResponseTranslator> response) {
            System.out.println("onResponse");
            if (mResult != null){
                ResponseData data = response.body().getResponseData();
                mResult.setText("Překlad: " + data.getTranslatedText());
                mResult.setVisibility(View.VISIBLE);

                mHistory=mActivity.getHistoryList();
                mHistory.add(mTranslatedText+" >> "+data.getTranslatedText()+"\t("+i+"->"+o+")");

                RecyclerView.Adapter mAdapter=mActivity.returnAdapter();
                mAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onFailure(Call<ResponseTranslator> call, Throwable t) {
            System.out.println("onFailure");
            t.printStackTrace();
        }
    }
}
