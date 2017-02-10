    package org.evaluation.pal.palcrewevaluation;
    import android.content.Context;
    import android.graphics.Color;
    import android.support.design.widget.AppBarLayout;
    import android.support.v4.app.FragmentActivity;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.app.FragmentPagerAdapter;
    import android.support.v4.view.ViewPager;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.Gravity;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.EditText;
    import android.widget.LinearLayout;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.RelativeLayout;
    import android.widget.ScrollView;
    import android.widget.Spinner;
    import android.widget.TextClock;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.github.gcacace.signaturepad.views.SignaturePad;

    import org.w3c.dom.Text;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

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
        private static List<View> layoutList;
        private static Map<String, Integer> scoreList;
        private static Map<String, String> scoreCategoryMapping;
        private static int safetyScore, serviceScore;
        private static TextView safetyScoreText, serviceScoreText, raterNameLabel, empNameLabel;
        private static SignaturePad empSignaturePad, raterSignaturePad;

        /*Employee Details*/
        private static EditText employeeName, idNumber, date, acRegistry, flightNum, sector, sla, raterName;
        private static RadioGroup checkType;

        private static boolean debug = false;

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
            mViewPager.setOffscreenPageLimit(15);
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

            private static List<String> dimensions;
            private static List<Integer> pages;
            //private static TextView serviceScoreText, safetyScoreText;

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
                pages = new ArrayList<>();
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
                pages.add(R.array.evaluation_page_11);
                pages.add(R.array.evaluation_page_12);

            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {

                initPages();
                dimensions = Arrays.asList(getResources().getStringArray(R.array.dimensions));

                layoutList = new ArrayList<>();
                scoreList = new HashMap<>();
                scoreCategoryMapping = new HashMap<>();

                for(int i = 0; i < 12; i++){
                    List<String> pageRows = Arrays.asList(getResources().getStringArray(pages.get(i)));
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

                        employeeName = (EditText) rootView.findViewById(R.id.fullNameText);
                        idNumber = (EditText) rootView.findViewById(R.id.idNumberText);
                        date = (EditText) rootView.findViewById(R.id.dateText);
                        acRegistry = (EditText) rootView.findViewById(R.id.acRegistryText);
                        flightNum = (EditText) rootView.findViewById(R.id.flightNumText);
                        sector = (EditText) rootView.findViewById(R.id.sectorText);
                        sla = (EditText) rootView.findViewById(R.id.slaText);
                        raterName = (EditText) rootView.findViewById(R.id.raterText);
                        checkType = (RadioGroup) rootView.findViewById(R.id.checkTypeRadioGroup);

                        employeeName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                empNameLabel.setText(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        raterName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                raterNameLabel.setText(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        break;
                    case 2:
                        rootView = layoutList.get(0);
                        break;
                    case 3:
                        rootView = layoutList.get(1);
                        break;
                    case 4:
                        rootView = layoutList.get(2);
                        break;
                    case 5:
                        rootView = layoutList.get(3);
                        break;
                    case 6:
                        rootView = layoutList.get(4);
                        break;
                    case 7:
                        rootView = layoutList.get(5);
                        break;
                    case 8:
                        rootView = layoutList.get(6);
                        break;
                    case 9:
                        rootView = layoutList.get(7);
                        break;
                    case 10:
                        rootView = layoutList.get(8);
                        break;
                    case 11:
                        rootView = layoutList.get(9);
                        break;
                    case 12:
                        rootView = layoutList.get(10);
                        break;
                    case 13:
                        rootView = layoutList.get(11);
                        break;
                    case 14:
                        rootView = inflater.inflate(R.layout.fragment_summary, container, false);
                        serviceScoreText = (TextView) rootView.findViewById(R.id.serviceScore);
                        safetyScoreText = (TextView) rootView.findViewById(R.id.safetyScore);
                        empNameLabel = (TextView) rootView.findViewById(R.id.employeeNameLabel);
                        raterNameLabel = (TextView) rootView.findViewById(R.id.raterNameLabel);
                        empSignaturePad = (SignaturePad) rootView.findViewById(R.id.signature_pad_1);
                        raterSignaturePad = (SignaturePad) rootView.findViewById(R.id.signature_pad_2);

                        serviceScoreText.setText("0");
                        safetyScoreText.setText("0");

                        break;
                }
                return rootView;
            }
        }

        private static View generateView(List<String> pageElements, FragmentActivity context, String sectionTitle){
            ScrollView scroller = new ScrollView(context);
            LinearLayout container = new LinearLayout(context);
            container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            container.setOrientation(LinearLayout.VERTICAL);
            container.setPadding(16, 16, 16, 16);

            scroller.addView(container);

            TextView sectionTextView = new TextView(context);
            sectionTextView.setText(sectionTitle);
            sectionTextView.setTextSize(20.0f);
            container.addView(sectionTextView);

            for (String s : pageElements){
                //Extract values in s
                String[] temp = s.split("\\|");
                String rowLabel, rowType, radioType;
                rowLabel = temp[0];
                rowType = temp[1];
                radioType = "";
                if(temp.length > 2)
                    radioType = temp[2];

                LinearLayout rowLayout = new LinearLayout(context);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setPadding(8, 16, 8, 16);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                rowLayout.setLayoutParams(params);

                TextView rowLabelTextView = new TextView(context);
                RadioGroup rowRadioGroup = new RadioGroup(context);

                if(!rowType.equalsIgnoreCase("Text")) {
                    rowLabelTextView.setText(rowLabel);
                    rowLabelTextView.setLeft(0);
                    rowLayout.addView(rowLabelTextView);
                }
                else{
                    EditText rowEditText = new EditText(context);
                    rowEditText.setLayoutParams(params);
                    rowLayout.addView(rowEditText);
                }

                if (rowType.equals("Header")) {
                    rowLabelTextView.setTextSize(18);
                    rowLabelTextView.setTextColor(Color.BLACK);
                } else {
                    params.weight = 0.80f;
                    params.gravity = Gravity.LEFT;
                    rowLabelTextView.setLayoutParams(params);

                    rowRadioGroup = generateRadioGroup(context, radioType, rowLabel);
                    //params.weight = 1f;
                    //params.gravity = Gravity.RIGHT;
                    //rowRadioGroup.setLayoutParams(params);

                    scoreCategoryMapping.put(rowLabel, rowType);
                }

                rowLayout.addView(rowRadioGroup);

                /*else{
                    if(!rowLabel.trim().equals("")){
                        TextView rowLabelTextView = new TextView(context);
                        rowLabelTextView.setText(rowLabel);
                        rowLabelTextView.setLeft(0);



                    }
                }*/

                container.addView(rowLayout);
            }

            return scroller;
        }

        public static class ScoreClickListener implements View.OnClickListener{
            String rowName;
            int score;
            Context context;
            public ScoreClickListener(String rowName, int score, Context context){
                this.rowName = rowName;
                this.score = score;
                this.context = context;
            }

            @Override
            public void onClick(View v) {
                scoreList.put(rowName, score);
                updateServiceScore(context);
                updateSafetyScore(context);
            }
        }

        private static RadioGroup generateRadioGroup(final Context context, String type, String rowName){
            RadioGroup rowRadioGroup = new RadioGroup(context);

            rowRadioGroup.setOrientation(RadioGroup.HORIZONTAL);
            rowRadioGroup.setRight(0);

            if(type.equals("01")){
                RadioButton zeroButton = new RadioButton(context);
                zeroButton.setText("0");
                zeroButton.setOnClickListener(new ScoreClickListener(rowName, 0, context));

                RadioButton oneButton = new RadioButton(context);
                oneButton.setText("1");
                oneButton.setOnClickListener(new ScoreClickListener(rowName, 1, context));

                rowRadioGroup.addView(zeroButton);
                rowRadioGroup.addView(oneButton);
            }
            else if(type.equals("0123")){
                RadioButton zeroButton = new RadioButton(context);
                zeroButton.setText("0");
                zeroButton.setOnClickListener(new ScoreClickListener(rowName, 0, context));

                RadioButton oneButton = new RadioButton(context);
                oneButton.setText("1");
                oneButton.setOnClickListener(new ScoreClickListener(rowName, 1, context));

                RadioButton twoButton = new RadioButton(context);
                twoButton.setText("2");
                twoButton.setOnClickListener(new ScoreClickListener(rowName, 2, context));

                RadioButton threeButton = new RadioButton(context);
                threeButton.setText("3");
                threeButton.setOnClickListener(new ScoreClickListener(rowName, 3, context));

                rowRadioGroup.addView(zeroButton);
                rowRadioGroup.addView(oneButton);
                rowRadioGroup.addView(twoButton);
                rowRadioGroup.addView(threeButton);
            }
            else{
                RadioButton oButton = new RadioButton(context);
                oButton.setText("O");
                oButton.setOnClickListener(new ScoreClickListener(rowName, 1, context));

                RadioButton bButton = new RadioButton(context);
                bButton.setText("B");
                bButton.setOnClickListener(new ScoreClickListener(rowName, 0, context));

                RadioButton naButton = new RadioButton(context);
                naButton.setText("N/A");
                naButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        naScore();
                    }
                });

                rowRadioGroup.addView(oButton);
                rowRadioGroup.addView(bButton);
                rowRadioGroup.addView(naButton);
            }

            return rowRadioGroup;
        }

        private static void naScore(){

        }

        private static void updateServiceScore(Context context){
            int score = 0;
            for(Map.Entry<String, Integer> entry :  scoreList.entrySet()){
                String category = scoreCategoryMapping.get(entry.getKey());
                if(category.trim().equalsIgnoreCase("Service") || category.trim().equalsIgnoreCase("Text"))
                    score += entry.getValue();
            }
            serviceScore = score;
            serviceScoreText.setText(String.valueOf(serviceScore));


            if(debug)
                Toast.makeText(context, String.valueOf(score), Toast.LENGTH_SHORT).show();
        }

        private static void updateSafetyScore (Context context){
            int score = 0;
            for(Map.Entry<String, Integer> entry :  scoreList.entrySet()){
                String category = scoreCategoryMapping.get(entry.getKey());
                if(category.trim().equalsIgnoreCase("Safety"))
                    score += entry.getValue();
            }

            safetyScore = score;
            safetyScoreText.setText(String.valueOf(safetyScore));

            if(debug)
                Toast.makeText(context, String.valueOf(score), Toast.LENGTH_LONG).show();
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
                return 14;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Page " + position;
            }
        }
    }
