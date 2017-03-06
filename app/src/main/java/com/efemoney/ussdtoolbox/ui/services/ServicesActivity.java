package com.efemoney.ussdtoolbox.ui.services;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.efemoney.ussdtoolbox.R;
import com.efemoney.ussdtoolbox.base.BaseActivity;
import com.efemoney.ussdtoolbox.data.model.Service;
import com.efemoney.ussdtoolbox.data.source.ServicesRepository;
import com.efemoney.ussdtoolbox.databinding.ActivityServicesBinding;
import com.efemoney.ussdtoolbox.databinding.ItemServiceBinding;
import com.efemoney.ussdtoolbox.presenter.PresenterLoadCallback;
import com.efemoney.ussdtoolbox.presenter.PresenterLoader;
import com.efemoney.ussdtoolbox.util.DiffCb;
import com.efemoney.ussdtoolbox.widget.decoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicesActivity extends BaseActivity implements
        PresenterLoadCallback<ServicesMvp.Presenter>,
        ServicesMvp.View {

    private static final int LOADER_ID = 1;

    private ActivityServicesBinding binding;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_services);

        setToolbar(binding.appbar.toolbar, R.string.title_services, 0, true);

        // Load presenter using LoaderManager. The operation is synchronous.
        // See https://medium.com/@czyrux/presenter-surviving-orientation-changes-with-loaders-6da6d86ffbbf#.b17tav2ud
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        adapter = new ServicesAdapter(this, listener, new ArrayList<>(0));
        adapter.setHasStableIds(true); // It really doesn't but whatever

        int spanCount = getResources().getInteger(R.integer.grid_span_count);
        int spacingPx = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        SpaceItemDecoration gridSpacing = new SpaceItemDecoration(spanCount, spacingPx, true);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setSupportsChangeAnimations(false);

        binding.grid.addItemDecoration(gridSpacing);
        binding.grid.setItemAnimator(animator);
        binding.grid.setAdapter(adapter);
    }

    @Override
    public Loader<ServicesMvp.Presenter> onCreateLoader(int id, Bundle args) {

        return new PresenterLoader<>(this, () -> {
            ServicesRepository repository = getApp().getAppComponent().repository();
            return new ServicesPresenter(repository);
        });
    }

    @Override
    public void onLoadFinished(Loader<ServicesMvp.Presenter> loader, ServicesMvp.Presenter data) {

        // Presenter has been loaded time to bind the view
        presenter = data;
        presenter.bindView(this);
    }

    @Override
    public void onLoaderReset(Loader<ServicesMvp.Presenter> loader) {

        presenter.unbindView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(ServicesMvp.Presenter presenter) {

        this.presenter = presenter;
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
        Toast.makeText(this, "Show actions for " + serviceKey, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateFave(String key) {

        adapter.updateFave(key);
    }


    private void showErrorViews(CharSequence text, @DrawableRes int image) {

        hide(binding.grid, binding.progress);
        show(binding.errorView);

        // To load VectorDrawables with correct tint
        Drawable d = AppCompatResources.getDrawable(this, image);

        binding.text.setText(text);
        binding.image.setImageDrawable(d);
    }

    private void show(View... views) {

        for (View view : views) view.setVisibility(View.VISIBLE);
    }

    private void hide(View... views) {

        for (View view : views) view.setVisibility(View.GONE);
    }


    /**
     * Adapter for list of banks
     */
    private static class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder> {

        private static final String PAYLOAD_FAVE = "FAVE";

        // Provides constant time lookup by service key
        private HashMap<String, Service> serviceMap = new HashMap<>();
        private List<Service> services;
        private ServiceListener listener;

        private final ColorHelper helper;
        private final LayoutInflater inflater;

        public ServicesAdapter(Context context, ServiceListener listener, List<Service> services) {
            this.inflater = LayoutInflater.from(context);
            this.helper = ColorHelper.from(context);
            this.listener = listener;
            this.services = services;
            updateMap();
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

        private Service getData(int position) {

            return services.get(position);
        }

        public void updateFave(String key) {
            Service service = serviceMap.get(key);
            int index = services.indexOf(service);

            notifyItemChanged(index);
        }

        public void replaceData(List<Service> services) {

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
                handler.post(() -> ServicesAdapter.this.applyDiffResult(diffResult, newServices));

            }).start();
        }

        private void applyDiffResult(DiffUtil.DiffResult result, List<Service> services) {
            this.services = services;
            updateMap();
            result.dispatchUpdatesTo(this);
        }

        private void updateMap() {

            for (Service service : services) serviceMap.put(service.getKey(), service);
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

            public void bind(Service data) {
                this.data = data;

                int[] colors = helper.getColors(data.getColor(), data.getAccentColor());

                binding.background.setBackgroundColor(colors[ColorHelper.BG]);
                binding.name.setText(data.getName());

                binding.fave.setFavorite(data.isFavorite());
                if (data.getAccentColor() != 0) {
                    Drawable d = binding.fave.getDrawable().mutate();
                    d.setColorFilter(data.getAccentColor(), PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }

    private static class ColorHelper {

        private final Context context;

        static int BG = 0;
        static int TEXT = 1;
        static int ICON = 2;

        private ColorHelper(Context context) {

            this.context = context;
        }

        int[] getColors(int color, int accent) {

            int[] res = new int[3];

            boolean hasColor = color != 0;
            boolean hascAccent = accent != 0;

            int background = hasColor
                    ? color
                    : ContextCompat.getColor(context, R.color.black_54);

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

        public static ColorHelper from(Context context) {

            return new ColorHelper(context);
        }
    }

    private interface ServiceListener {

        void onServiceClick(Service service);
        void onServiceFave(Service service);

    }
}
