package com.efemoney.ussdtoolbox.ui.services;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Property;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.efemoney.ussdtoolbox.R;
import com.efemoney.ussdtoolbox.base.BaseActivity;
import com.efemoney.ussdtoolbox.data.source.ServicesRepository;
import com.efemoney.ussdtoolbox.databinding.ActivityServicesBinding;
import com.efemoney.ussdtoolbox.ui.services.search.SearchListFragment;
import com.efemoney.ussdtoolbox.ui.services.search.SearchPresenter;
import com.efemoney.ussdtoolbox.util.AnimUtils;
import com.efemoney.ussdtoolbox.util.AnimUtils.IntProp;
import com.efemoney.ussdtoolbox.util.ThemeUtils;
import com.efemoney.ussdtoolbox.util.Utils;

import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;

public class ServicesActivity extends BaseActivity {

    private static final String TAG_SEARCH_VIEW = "search-fragment";

    private ActivityServicesBinding binding;

    private AnimatedVectorDrawableCompat searchToBackDrawable;
    private AnimatedVectorDrawableCompat backToSearchDrawable;

    private boolean showToolbarMenu = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_services);

        setToolbar(binding.toolbar, 0, 0, false);

        setupSearchView();

        FragmentManager fm = getSupportFragmentManager();
        ServicesFragment fragment = (ServicesFragment) fm.findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = ServicesFragment.newInstance();
            fm.beginTransaction().add(R.id.container, fragment).commitNow();
        }

        // Instantiate the presenter and bind to view
        new ServicesPresenter(repository(), fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // Unfortunately returning false still shows the overflow menu
        // So take matters in our hands and hide 'em all
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setVisible(showToolbarMenu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_about:
                break;

            case R.id.action_settings:
                break;

            case R.id.action_debug_dialog:
                showDebugDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private ServicesRepository repository() {

        return getApp().getAppComponent().repository();
    }

    private void showDebugDialog() {

        BottomSheetDialog d = new BottomSheetDialog(this);
        d.setCanceledOnTouchOutside(true);
        d.setCancelable(true);
        d.setContentView(R.layout.dialog_debug_options);
        d.setOnShowListener(dialog -> {
            View view = d.findViewById(R.id.design_bottom_sheet);
            if (view != null) BottomSheetBehavior.from(view).setState(STATE_EXPANDED);
        });
        d.show();
    }

    private void setupSearchView() {

        // Update search edittext typeface
        TextView searchSrc = (TextView) binding.search.findViewById(R.id.search_src_text);
        searchSrc.setTypeface(ResourcesCompat.getFont(this, R.font.regular));

        searchToBackDrawable = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_search_back);
        backToSearchDrawable = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_back_search);

        binding.toolbar.setNavigationIcon(searchToBackDrawable);
        binding.toolbar.setNavigationOnClickListener(v -> {

            if (searchSrc.isFocused())
                binding.search.clearFocus();
            else
                binding.search.requestFocus();
        });

        // Not sure where this goes in this whole MVP thing
        // I'm just gonna stick it here anyways 😭
        binding.search.setOnQueryTextFocusChangeListener((v, hasFocus) -> {

            showToolbarMenu = !hasFocus;
            supportInvalidateOptionsMenu();

            setupToolbarNavIcon(hasFocus);
            animateSearchBar(hasFocus);

            addOrRemoveSearchView(hasFocus);
        });
    }

    private void addOrRemoveSearchView(boolean add) {

        FragmentManager fm = getSupportFragmentManager();

        SearchListFragment f =  (SearchListFragment) fm.findFragmentByTag(TAG_SEARCH_VIEW);
        if (f == null) f = SearchListFragment.newInstance();

        if (add) {
            fm.beginTransaction()
                    .add(R.id.container, f, TAG_SEARCH_VIEW)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

            // Create a presenter
            new SearchPresenter(repository(), f);

        } else {
            fm.beginTransaction()
                    .remove(f)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    private void setupToolbarNavIcon(boolean hasFocus) {

        AnimatedVectorDrawableCompat dwb = hasFocus ? searchToBackDrawable : backToSearchDrawable;
        dwb.setState(hasFocus ? ThemeUtils.FOCUSED_STATE_SET : ThemeUtils.EMPTY_STATE_SET);
        dwb.start();

        binding.toolbar.setNavigationIcon(dwb);
    }

    private void animateSearchBar(boolean expand) {

        AnimatorSet set = new AnimatorSet();

        Animator cardAnim = getCardAnim(expand);
        Animator toolbarAnim = getToolbarAnim(expand);

        set.playTogether(cardAnim, toolbarAnim);
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.setDuration(300L);
        set.start();
    }

    private Animator getCardAnim(boolean expand) {

        int twoDp = Utils.dpToPxInt(this, 2);
        int eightDp = twoDp * 4;

        int startMargin = expand ? eightDp : 0;
        int endMargin = expand ? 0 : eightDp;

        int startRadius = expand ? twoDp : 0;
        int endRadius = expand ? 0 : twoDp;

        PropertyValuesHolder left = PropertyValuesHolder.ofInt(LEFT_MARGIN, startMargin, endMargin);
        PropertyValuesHolder top = PropertyValuesHolder.ofInt(TOP_MARGIN, startMargin, endMargin);
        PropertyValuesHolder right= PropertyValuesHolder.ofInt(RIGHT_MARGIN, startMargin, endMargin);
        PropertyValuesHolder bottom = PropertyValuesHolder.ofInt(BOTTOM_MARGIN, startMargin, endMargin);

        PropertyValuesHolder radius = PropertyValuesHolder.ofFloat("radius", startRadius, endRadius);

        return ObjectAnimator.ofPropertyValuesHolder(binding.card, left, top, right, bottom, radius);
    }

    private Animator getToolbarAnim(boolean expand) {

        int forty8Dp = Utils.dpToPxInt(this, 48);
        int abHeight = getResources().getDimensionPixelSize(R.dimen.action_bar_size);

        int startHeight = expand ? forty8Dp : abHeight;
        int endHeight = expand ? abHeight : forty8Dp;

        PropertyValuesHolder height = PropertyValuesHolder.ofInt(HEIGHT, startHeight, endHeight);
        PropertyValuesHolder minHeight = PropertyValuesHolder.ofInt("minimumHeight", startHeight, endHeight);

        return ObjectAnimator.ofPropertyValuesHolder(binding.toolbar, minHeight, height);
    }


    public static final Property<View, Integer> LEFT_MARGIN
            = AnimUtils.createIntProperty(new IntProp<View>("leftMargin") {

        @Override
        public void set(View object, int value) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) object.getLayoutParams();
            p.leftMargin = value;

            object.setLayoutParams(p);
        }

        @Override
        public int get(View object) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) object.getLayoutParams();

            return p.leftMargin;
        }
    });

    public static final Property<View, Integer> TOP_MARGIN
            = AnimUtils.createIntProperty(new IntProp<View>("topMargin") {

        @Override
        public void set(View object, int value) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) object.getLayoutParams();
            p.topMargin = value;

            object.setLayoutParams(p);
        }

        @Override
        public int get(View object) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) object.getLayoutParams();

            return p.topMargin;
        }
    });

    public static final Property<View, Integer> RIGHT_MARGIN
            = AnimUtils.createIntProperty(new IntProp<View>("rightMargin") {

        @Override
        public void set(View object, int value) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) object.getLayoutParams();
            p.rightMargin = value;

            object.setLayoutParams(p);
        }

        @Override
        public int get(View object) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) object.getLayoutParams();

            return p.rightMargin;
        }
    });

    public static final Property<View, Integer> BOTTOM_MARGIN
            = AnimUtils.createIntProperty(new IntProp<View>("bottomMargin") {

        @Override
        public void set(View object, int value) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) object.getLayoutParams();
            p.bottomMargin = value;

            object.setLayoutParams(p);
        }

        @Override
        public int get(View object) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) object.getLayoutParams();

            return p.bottomMargin;
        }
    });

    public static final Property<View, Integer> HEIGHT
            = AnimUtils.createIntProperty(new IntProp<View>("height") {
        @Override
        public void set(View object, int value) {
            ViewGroup.LayoutParams p = object.getLayoutParams();
            p.height = value;

            object.setLayoutParams(p);
        }

        @Override
        public int get(View object) {
            ViewGroup.LayoutParams p = object.getLayoutParams();

            return p.height;
        }
    });
}