package org.evaluation.pal.palcrewevaluation;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvaluationActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);  // clear all scroll flags

        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(10);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();


                List<RadioGroup> radioGroups = new ArrayList<>();
                List<String> formAnswers = new ArrayList<>();
                // 0 = B
                // 1 = O
                //-1 = N/A

                radioGroups.add((RadioGroup)findViewById(R.id.radioGroup));
                radioGroups.add((RadioGroup)findViewById(R.id.radioGroup1));
                radioGroups.add((RadioGroup)findViewById(R.id.radioGroup2));
                radioGroups.add((RadioGroup)findViewById(R.id.radioGroup3));


                for (RadioGroup rg : radioGroups){
                    RadioButton checkedRadioButton = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                    if(checkedRadioButton == null){
                        formAnswers.add("-1");
                    }
                    else if(checkedRadioButton.getText().equals("O")){
                        formAnswers.add("1");
                    }
                    else{
                        formAnswers.add("0");
                    }

                }
                Log.d("Debug", formAnswers.toString());
            }
        });

        //Dynamically Create UI


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static List<View> layoutList;
        private static List<String> dimensions;
        private static List<Integer> pages;



        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        private void initPages(){
            pages.add(R.array.evaluation_page_1);
            pages.add(R.array.evaluation_page_2);
            pages.add(R.array.evaluation_page_3);
            pages.add(R.array.evaluation_page_4);
            pages.add(R.array.evaluation_page_5);
            pages.add(R.array.evaluation_page_6);
            pages.add(R.array.evaluation_page_7);
            pages.add(R.array.evaluation_page_8);
            pages.add(R.array.evaluation_page_9);
            pages.add(R.array.evaluation_page_10);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            dimensions = Arrays.asList(getResources().getStringArray(R.array.dimensions));

            layoutList = new ArrayList<>();
            for(int i = 0; i < 10; i++){
                List<String> pageRows = Arrays.asList(getResources().getStringArray(R.array.evaluation_page_2));
                layoutList.add(generateView(pageRows, getActivity(), dimensions.get(i)));
            }



            int mFragmentIndex = 0;
            if(getArguments() != null){
                mFragmentIndex = getArguments().getInt(ARG_SECTION_NUMBER);
            }

            View rootView = null;

            switch(mFragmentIndex){
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_evaluation_1, container, false);

                    Spinner empStatusSpinner = (Spinner) rootView.findViewById(R.id.empStatusSpinner);
                    ArrayAdapter<String> empStatusSpinnerAdapter;
                    List<String> empStatusSpinnerList = Arrays.asList(getResources().getStringArray(R.array.employee_status));
                    empStatusSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, empStatusSpinnerList);
                    empStatusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    empStatusSpinner.setAdapter(empStatusSpinnerAdapter);

                    Spinner aircraftSpinner = (Spinner) rootView.findViewById(R.id.aircraftSpinner);
                    ArrayAdapter<String> aircraftSpinnerAdapter;
                    List<String> aircraftSpinnerList = Arrays.asList(getResources().getStringArray(R.array.aircraft_codes));
                    aircraftSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, aircraftSpinnerList);
                    aircraftSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    aircraftSpinner.setAdapter(aircraftSpinnerAdapter);

                    break;
                case 2:
                    rootView = layoutList.get(0);
                    break;
                case 3:
                    rootView = layoutList.get(1);
                    break;
                case 5:
                    rootView = layoutList.get(2);
                    break;
                case 6:
                    rootView = layoutList.get(3);
                    break;
                case 7:
                    rootView = layoutList.get(4);
                    break;
                case 8:
                    rootView = layoutList.get(5);
                    break;
            }





            return rootView;
        }

    }

    private static View generateView(List<String> pageElements, FragmentActivity context, String sectionTitle){
        LinearLayout container = new LinearLayout(context);
        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(16, 16, 16, 16);

        TextView sectionTextView = new TextView(context);
        sectionTextView.setText(sectionTitle);
        sectionTextView.setTextSize(20.0f);
        container.addView(sectionTextView);

        for (String s : pageElements){
            LinearLayout rowLayout = new LinearLayout(context);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rowLayout.setLayoutParams(params);

            TextView rowLabelTextView = new TextView(context);
            rowLabelTextView.setText(s);
            rowLabelTextView.setLeft(0);


            params.weight = 0.80f;
            params.gravity = Gravity.LEFT;
            rowLabelTextView.setLayoutParams(params);

            RadioGroup rowRadioGroup = new RadioGroup(context);
            rowRadioGroup.setOrientation(RadioGroup.HORIZONTAL);
            rowRadioGroup.setRight(0);

            params.weight = 1f;
            params.gravity = Gravity.RIGHT;
            //rowRadioGroup.setLayoutParams(params);

            RadioButton oButton = new RadioButton(context);
            oButton.setText("O");

            RadioButton bButton = new RadioButton(context);
            bButton.setText("B");

            RadioButton naButton = new RadioButton(context);
            naButton.setText("N/A");

            rowRadioGroup.addView(oButton);
            rowRadioGroup.addView(bButton);
            rowRadioGroup.addView(naButton);

            rowLayout.addView(rowLabelTextView);
            rowLayout.addView(rowRadioGroup);

            container.addView(rowLayout);
        }

        return container;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
