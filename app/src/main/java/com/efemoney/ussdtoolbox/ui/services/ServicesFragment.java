package com.efemoney.ussdtoolbox.ui.services;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.efemoney.ussdtoolbox.R;
import com.efemoney.ussdtoolbox.base.BaseFragment;
import com.efemoney.ussdtoolbox.data.model.Service;
import com.efemoney.ussdtoolbox.databinding.FragmentServicesBinding;
import com.efemoney.ussdtoolbox.databinding.ItemServiceBinding;
import com.efemoney.ussdtoolbox.menu.MenuWrap;
import com.efemoney.ussdtoolbox.ui.selectaction.ServiceActionActivity;
import com.efemoney.ussdtoolbox.util.DiffCb;
import com.efemoney.ussdtoolbox.util.Preconditions;
import com.efemoney.ussdtoolbox.widget.decoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Efe on 08/03/2017.
 */
public class ServicesFragment extends BaseFragment implements ServicesMvp.View {

    private FragmentServicesBinding binding;

    private ServicesMvp.Presenter presenter;
    private ServicesAdapter adapter;
    private ServiceListener listener = new ServiceListener() {
        @Override
        public void onServiceClick(Service service) {
            presenter.onServiceClick(service);
        }

        @Override
        public void onServiceFave(Service service) {
            presenter.onServiceFaveClicked(service);
        }
    };

    public static ServicesFragment newInstance() {

        Bundle args = new Bundle();

        ServicesFragment fragment = new ServicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuWrap wrap = new MenuWrap(getContext(), menu);

        inflater.inflate(R.menu.services, wrap);

        super.onCreateOptionsMenu(wrap, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_update:
                presenter.onActionUpdate();
                break;

            case R.id.action_clear_frequents:
                presenter.onActionClearFrequents();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        binding = FragmentServicesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        adapter = new ServicesAdapter(getContext(), listener, new ArrayList<>(0));
        adapter.setHasStableIds(true); // It really doesn't but whatever

        int spanCount = getResources().getInteger(R.integer.grid_span_count);
        int spacingPx = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        SpaceItemDecoration gridSpacing = new SpaceItemDecoration(spanCount, spacingPx, true);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setSupportsChangeAnimations(false); // Disable change animations

        binding.grid.addItemDecoration(gridSpacing);
        binding.grid.setItemAnimator(animator);
        binding.grid.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(ServicesMvp.Presenter presenter) {

        this.presenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

        binding.progress.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showGenericError(String errorMessage) {

        showErrorViews(
                errorMessage,
                R.drawable.ic_error
        );
    }

    @Override
    public void showLoadingServicesError() {
        showErrorViews(
                getString(R.string.could_not_load_services),
                R.drawable.ic_error
        );
    }

    @Override
    public void showNoNetwork() {

        showErrorViews(
                getString(R.string.no_network),
                R.drawable.ic_no_network
        );
    }

    @Override
    public void showNoServices() {

        String text = getString(R.string.no_services);

        URLSpan span = new URLSpan("http://www.google.com");

        int i = text.length();

        SpannableString string = new SpannableString(text);
        string.setSpan(span, i - 5, i - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        showErrorViews(string, R.drawable.ic_error);
    }

    @Override
    public void showServices(List<Service> services) {

        adapter.replaceData(services);

        show(binding.grid);
        hide(binding.errorView);
    }

    @Override
    public void showActionsScreen(String serviceKey) {

        // ToDo animation
        startActivity(ServiceActionActivity.createLaunchIntent(getContext(), serviceKey));
    }


    private void showErrorViews(CharSequence text, @DrawableRes int image) {

        hide(binding.grid, binding.progress);
        show(binding.errorView);

        // To load VectorDrawables with correct tint
        Drawable d = AppCompatResources.getDrawable(getContext(), image);

        binding.text.setText(text);
        binding.image.setImageDrawable(d);
    }

    private void show(View... views) {

        for (View view : views) view.setVisibility(View.VISIBLE);
    }

    private void hide(View... views) {

        for (View view : views) view.setVisibility(View.GONE);
    }


    private static class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder> {

        private List<Service> services;
        private ServiceListener listener;

        private final LayoutInflater inflater;
        private final ServiceColorHelper helper;

        ServicesAdapter(Context context, ServiceListener listener, List<Service> services) {
            this.helper = ServiceColorHelper.from(context);
            this.inflater = LayoutInflater.from(context);
            this.listener = listener;
            this.services = services;
        }

        private Service getData(int position) {

            return services.get(position);
        }


        @Override
        public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ItemServiceBinding binding = ItemServiceBinding.inflate(inflater, parent, false);
            return new ServiceViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ServiceViewHolder holder, int position) {

            Service data = getData(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount() {

            return services.size();
        }

        @Override
        public long getItemId(int position) {

            // Key is guaranteed to be unique
            return getData(position).getKey().hashCode();
        }


        void replaceData(List<Service> services) {

            replaceInternal(services);
        }

        private void replaceInternal(List<Service> newServices) {

            // Make a defensive copy of the service list
            List<Service> oldServices = new ArrayList<>(services);

            // Get Main thread handler
            Handler handler = new Handler(Looper.getMainLooper());

            // Calculate diff in new thread and post to the main thread
            new Thread(() -> {

                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCb<>(oldServices, newServices));
                handler.post(() -> applyDiffResult(diffResult, newServices));

            }).start();
        }

        private void applyDiffResult(DiffUtil.DiffResult result, List<Service> services) {
            this.services = new ArrayList<>(services);
            result.dispatchUpdatesTo(this);
        }


        class ServiceViewHolder extends RecyclerView.ViewHolder {

            ItemServiceBinding binding;
            Service data;

            ServiceViewHolder(ItemServiceBinding binding) {
                super(binding.getRoot());

                this.binding = binding;

                binding.getRoot().setOnClickListener(v -> {
                    if (listener != null) listener.onServiceClick(data);
                });

                binding.fave.setOnClickListener(v -> {
                    if (listener != null) listener.onServiceFave(data);
                });
            }

            void bind(Service data) {
                this.data = data;

                int[] colors = helper.getColors(data.getColor(), data.getAccentColor());

                binding.background.setBackgroundColor(colors[ServiceColorHelper.BG]);

                binding.name.setTextColor(colors[ServiceColorHelper.TEXT]);
                binding.name.setText(data.getName());

                Drawable d = binding.fave.getDrawable().mutate();
                d.setColorFilter(colors[ServiceColorHelper.ICON], PorterDuff.Mode.SRC_IN);

                binding.fave.setFavorite(data.isFavorite());
            }
        }
    }

    private static class ServiceColorHelper {

        private final Context context;

        private static final int BG = 0;
        private static final int TEXT = 1;
        private static final int ICON = 2;

        private ServiceColorHelper(Context context) {

            this.context = context;
        }

        /**
         * @param color The service main/brand color. Used as service tile background
         * @param accent The service accent color. Used in styling button and text.
         * @return An array of three colors.
         */
        int[] getColors(int color, int accent) {

            int[] res = new int[3];

            boolean hasColor = color != 0;
            boolean hascAccent = accent != 0;

            int background = hasColor
                    ? color
                    : ContextCompat.getColor(context, R.color.black_54); // Fallback bg color

            int text = ColorUtils.calculateLuminance(background) <= 0.5
                    ? ContextCompat.getColor(context, R.color.white)
                    : ContextCompat.getColor(context, R.color.black_87);

            // 5.0 contrast ratio is a balance between AA(4.5:1) and AAA(7:1) standards for body of texts
            int minAlpha = ColorUtils.calculateMinimumAlpha(text, background, 5.0f);
            if (minAlpha != -1) ColorUtils.setAlphaComponent(text, minAlpha);

            int icon = hascAccent ? accent : text;

            res[BG] = background;
            res[TEXT] = text;
            res[ICON] = icon;

            return res;
        }

        static ServiceColorHelper from(Context context) {

            return new ServiceColorHelper(context);
        }
    }

    private interface ServiceListener {
        void onServiceClick(Service service);
        void onServiceFave(Service service);
    }
}
