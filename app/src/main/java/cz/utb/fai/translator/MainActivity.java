package cz.utb.fai.translator;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.utb.fai.translator.adapter.HistoryAdapter;
import cz.utb.fai.translator.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private List<String> mHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /*@Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d("deubg", "--------onsaveinstancestate--------");
        outState.putInt(CURRENT_STEP_TAG, currentStep);
        super.onSaveInstanceState(outState, outPersistentState);
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        final EditText textBox = (EditText) findViewById(R.id.etTranslate);
        CharSequence userText = textBox.getText();
        outState.putCharSequence("savedText", userText);

        ArrayList<String> pom= new ArrayList<String>();
        for(String s:mHistory){
            pom.add(s);
        }
        outState.putStringArrayList("MyString", pom);  // save your instance
        //final List<String> listt = (List<String>) findViewById(R.id.etTranslate);
        //http://www.techotopia.com/index.php/Saving_and_Restoring_the_User_Interface_State_of_an_Android_Activity
        //final TextView mTextRow = (TextView) findViewById(R.id.textView);
        //List<String> listt = mTextRow. .getText();
        //outState.putCharSequence("savedText", userText);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        final EditText textBox =(EditText) findViewById(R.id.etTranslate);
        CharSequence userText =savedState.getCharSequence("savedText");
        textBox.setText(userText);


        super.onRestoreInstanceState(savedState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.


        ArrayList<String> gg=savedState.getStringArrayList("MyString"); //get it

        for(String s:gg){
            mHistory.add(s);
        }
        /*outState.putStringArrayList("MyString", pom);


        TextView myTextView = (TextView) findViewById(R.id.textView);
        myTextView.clearComposingText();
        //myTextView.setText("");
        int i=0;
        for(String list : mHistory){
            //myTextView.setText(mHistory.get(i));
            System.out.println(mHistory.get(i));
            i++;
        }*/

        RecyclerView.Adapter mAdapter=this.returnAdapter();
        mAdapter.notifyDataSetChanged();
    }

    private void init(){

        //seznam polozek prekladu
        mHistory = new ArrayList<String>();

        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new ViewPagerAdapter(this));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout = (TabLayout)findViewById(R.id.tableLayout);
        // adds view pager into tab layout
        mTabLayout.setupWithViewPager(mViewPager);

        new Page_home(this);

        //set Recycler view
        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter=new HistoryAdapter(mHistory);
        mRecyclerView.setAdapter(mAdapter);



       //mSave=new SaveOpen(mAdapter);

        //mRecyclerView.setLayoutManager();
    }

    public List<String> getHistoryList(){
        return this.mHistory;
    }
    public RecyclerView.Adapter returnAdapter(){
        return mAdapter;
    }
}