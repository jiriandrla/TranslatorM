package cz.utb.fai.translator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cz.utb.fai.translator.adapter.HistoryAdapter;
import cz.utb.fai.translator.api.ApiClient;
import cz.utb.fai.translator.api.ApiInterface;
import cz.utb.fai.translator.api.pojo.ResponseTranslator;
import retrofit2.Call;

/**
 * Created by Jirka on 23.1.2018.
 */

public class SaveOpen extends Activity {

    HistoryAdapter Tadapter;

    /*public SaveOpen(HistoryAdapter Ta) {
        Tadapter=Ta;
    }*/
   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveopen);

        Button mSave = (Button) findViewById(R.id.btnSave);
        Button mOave = (Button) findViewById(R.id.btnOpen);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        init();
    }*/

    /*public void onClick(View view) {//povine prepsano
        switch (view.getId()) {
          case R.id.btnSave:
                System.out.println("uvnitr hhhsave");
                Tadapter.  .add("fr+ + + +");
                RecyclerView.Adapter mAdapter=mActivity.returnAdapter();
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    };*/
}
