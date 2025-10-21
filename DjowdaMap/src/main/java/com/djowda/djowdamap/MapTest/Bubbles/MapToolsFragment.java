package com.djowda.djowdamap.MapTest.Bubbles;

import android.animation.ValueAnimator;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


public class MapToolsFragment extends Fragment {

//    private ImageView toggleButton;
//    private LinearLayout contentLayout;
//    private LinearLayout Linear_selectLayer;
//    private ImageView collapsedIcon;
//    private boolean isExpanded = false;
//    private static final int ANIMATION_DURATION = 300;
//
//    private LayerSelectionDialog layerDialog;
//    private LayerViewModel layerViewModel;
//
//    public MapToolsFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Initialize ViewModel
//
//        layerViewModel = new ViewModelProvider(requireActivity()).get(LayerViewModel.class);
//
//        // Observe layer changes
//        observeLayerChanges();
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_map_tools, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        initViews(view);
//        setupClickListeners();
//    }
//
//    private void initViews(View view) {
//        toggleButton = view.findViewById(R.id.btn_toggle_map_tools);
//        contentLayout = view.findViewById(R.id.map_tools_content_layout);
//        collapsedIcon = view.findViewById(R.id.map_tools_collapsed_icon);
//
//        Linear_selectLayer = view.findViewById(R.id.Linear_selectLayer);
//
//        // Initially collapsed
//        contentLayout.setVisibility(View.GONE);
//        collapsedIcon.setVisibility(View.VISIBLE);
//    }
//
//    private void setupClickListeners() {
//        View.OnClickListener toggleListener = v -> toggleContent();
//        toggleButton.setOnClickListener(toggleListener);
//        collapsedIcon.setOnClickListener(toggleListener);
//
//        Linear_selectLayer.setOnClickListener(view -> {
//            showLayerSelectionDialog();
//        });
//    }
//
//
//
//
//    private void toggleContent() {
//        if (isExpanded) {
//            collapseContent();
//        } else {
//            expandContent();
//        }
//        isExpanded = !isExpanded;
//    }
//
//    private void expandContent() {
//        collapsedIcon.setVisibility(View.GONE);
//        contentLayout.setVisibility(View.VISIBLE);
//
//        // Measure the content width
//        contentLayout.measure(
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(((View) contentLayout.getParent()).getHeight(), View.MeasureSpec.AT_MOST)
//        );
//        final int targetWidth = contentLayout.getMeasuredWidth();
//
//        // Start from 0 width
//        contentLayout.getLayoutParams().width = 0;
//        contentLayout.requestLayout();
//
//        // Animate to target width
//        ValueAnimator animator = ValueAnimator.ofInt(0, targetWidth);
//        animator.setDuration(ANIMATION_DURATION);
//        animator.addUpdateListener(animation -> {
//            contentLayout.getLayoutParams().width = (int) animation.getAnimatedValue();
//            contentLayout.requestLayout();
//        });
//
//        // Rotate arrow
//        toggleButton.animate().rotation(180f).setDuration(ANIMATION_DURATION).start();
//
//        animator.start();
//    }
//
//    private void collapseContent() {
//        final int initialWidth = contentLayout.getWidth();
//
//        ValueAnimator animator = ValueAnimator.ofInt(initialWidth, 0);
//        animator.setDuration(ANIMATION_DURATION);
//        animator.addUpdateListener(animation -> {
//            int value = (int) animation.getAnimatedValue();
//            if (value == 0) {
//                contentLayout.setVisibility(View.GONE);
//                collapsedIcon.setVisibility(View.VISIBLE);
//            } else {
//                contentLayout.getLayoutParams().width = value;
//                contentLayout.requestLayout();
//            }
//        });
//
//        // Rotate arrow back
//        toggleButton.animate().rotation(0f).setDuration(ANIMATION_DURATION).start();
//
//        animator.start();
//    }
//
//
//    /**
//     * Observe layer changes from ViewModel and update the map accordingly
//     */
//    private void observeLayerChanges() {
//        layerViewModel.getCurrentLayer().observe(this, layer -> {
//            if (layer != null) {
//                // Update map based on the new layer
//                updateMapForLayer(layer);
//            }
//        });
//    }
//
//    /**
//     * Shows the Layer Selection Dialog with ViewModel integration
//     */
//    private void showLayerSelectionDialog() {
//        // Pass the ViewModel to the dialog
//        layerDialog = new LayerSelectionDialog(requireContext(), layerViewModel);
//
//        // Show the dialog
//        layerDialog.show();
//
//    }
//
//
//
//    // ════════════════════════════════════════════════════════════════════════════
//    // MAP UPDATE LOGIC BASED ON LAYER
//    // ════════════════════════════════════════════════════════════════════════════
//
//    /**
//     * Updates the map visualization based on the selected layer
//     * @param layer The current MapLayer from ViewModel
//     */
//    private void updateMapForLayer(MapLayer layer) {
//        String layerId = layer.getLayerId();
//
//        // Clear existing map content
//        clearMap();
//
//        // Render the new layer
//        switch (layerId) {
//            case "support_needed":
//                renderSupportNeededLayer();
//                break;
//
//            case "support_offered":
//                renderSupportOfferedLayer();
//                break;
//
//            case "product":
//                renderProductLayer();
//                break;
//
//            case "user":
//                renderUserLayer();
//                break;
//
//            case "store":
//                renderStoreLayer();
//                break;
//
//            case "farm":
//                renderFarmLayer();
//                break;
//
//            case "restaurant":
//                renderRestaurantLayer();
//                break;
//
//            case "factory":
//                renderFactoryLayer();
//                break;
//
//            case "delivery":
//                renderDeliveryLayer();
//                break;
//
//            case "seed":
//                renderSeedLayer();
//                break;
//
//            case "wholesaler":
//                renderWholesellerLayer();
//                break;
//
//            case "transport":
//                renderTransportLayer();
//                break;
//
//            case "admin":
//                renderAdminLayer();
//                break;
//
//            case "default":
//            default:
//                renderDefaultLayer();
//                break;
//        }
//
//        // Use the layer's renderer directly
//        // layer.render(mapView, dataProvider);
//    }
//
//    // ════════════════════════════════════════════════════════════════════════════
//    // LAYER RENDERING METHODS (PLACEHOLDERS)
//    // ════════════════════════════════════════════════════════════════════════════
//
//    /**
//     * Clears all map content
//     */
//    private void clearMap() {
//        // TODO: Clear all markers, overlays, and layers
//        // mapView.clear();
//        System.out.println("Map cleared");
//    }
//
//    /**
//     * Renders Support Needed layer
//     */
//    private void renderSupportNeededLayer() {
//        // TODO: Show markers for users/components needing support
//        // Example:
//        // List<SupportRequest> requests = dataProvider.getSupportNeeded();
//        // for (SupportRequest request : requests) {
//        //     addMarker(request.getLocation(), R.drawable.ic_support_needed, request.getDescription());
//        // }
//        System.out.println("Rendering Support Needed Layer");
//    }
//
//    /**
//     * Renders Support Offered layer
//     */
//    private void renderSupportOfferedLayer() {
//        // TODO: Show markers for users/components offering support
//        // Example:
//        // List<SupportOffer> offers = dataProvider.getSupportOffered();
//        // for (SupportOffer offer : offers) {
//        //     addMarker(offer.getLocation(), R.drawable.ic_support_offered, offer.getDescription());
//        // }
//        System.out.println("Rendering Support Offered Layer");
//    }
//
//    /**
//     * Renders Product layer
//     */
//    private void renderProductLayer() {
//        // TODO: Show product availability across cells
//        // Example:
//        // String selectedProduct = getSelectedProduct();
//        // List<ProductAvailability> availability = dataProvider.getProductAvailability(selectedProduct);
//        // for (ProductAvailability data : availability) {
//        //     colorMapCell(data.getCellId(), data.getAvailabilityColor());
//        //     addMarker(data.getLocation(), R.drawable.ic_product, data.getQuantityText());
//        // }
//        System.out.println("Rendering Product Layer");
//    }
//
//    /**
//     * Renders User layer
//     */
//    private void renderUserLayer() {
//        // TODO: Show all user locations
//        // Example:
//        // List<User> users = dataProvider.getUsers();
//        // for (User user : users) {
//        //     addMarker(user.getLocation(), R.drawable.ic_user, user.getName());
//        // }
//        System.out.println("Rendering User Layer");
//    }
//
//    /**
//     * Renders Store layer
//     */
//    private void renderStoreLayer() {
//        // TODO: Show all store locations
//        // Example:
//        // List<Store> stores = dataProvider.getStores();
//        // for (Store store : stores) {
//        //     addMarker(store.getLocation(), R.drawable.ic_store, store.getName());
//        // }
//        System.out.println("Rendering Store Layer");
//    }
//
//    /**
//     * Renders Farm layer
//     */
//    private void renderFarmLayer() {
//        // TODO: Show all farm locations
//        // Example:
//        // List<Farm> farms = dataProvider.getFarms();
//        // for (Farm farm : farms) {
//        //     addMarker(farm.getLocation(), R.drawable.ic_farm, farm.getName());
//        // }
//        System.out.println("Rendering Farm Layer");
//    }
//
//    /**
//     * Renders Restaurant layer
//     */
//    private void renderRestaurantLayer() {
//        // TODO: Show all restaurant locations
//        System.out.println("Rendering Restaurant Layer");
//    }
//
//    /**
//     * Renders Factory layer
//     */
//    private void renderFactoryLayer() {
//        // TODO: Show all factory locations
//        System.out.println("Rendering Factory Layer");
//    }
//
//    /**
//     * Renders Delivery layer
//     */
//    private void renderDeliveryLayer() {
//        // TODO: Show all delivery personnel/vehicles
//        System.out.println("Rendering Delivery Layer");
//    }
//
//    /**
//     * Renders Seed layer
//     */
//    private void renderSeedLayer() {
//        // TODO: Show all seed suppliers
//        System.out.println("Rendering Seed Layer");
//    }
//
//    /**
//     * Renders Wholesaler layer
//     */
//    private void renderWholesellerLayer() {
//        // TODO: Show all wholesaler locations
//        System.out.println("Rendering Wholesaler Layer");
//    }
//
//    /**
//     * Renders Transport layer
//     */
//    private void renderTransportLayer() {
//        // TODO: Show all transport providers
//        System.out.println("Rendering Transport Layer");
//    }
//
//    /**
//     * Renders Admin layer
//     */
//    private void renderAdminLayer() {
//        // TODO: Show admin-specific information
//        System.out.println("Rendering Admin Layer");
//    }
//
//    /**
//     * Renders Default layer
//     */
//    private void renderDefaultLayer() {
//        // TODO: Show default map view
//        System.out.println("Rendering Default Layer");
//    }
//
//    // ════════════════════════════════════════════════════════════════════════════
//    // HELPER METHODS (PLACEHOLDERS)
//    // ════════════════════════════════════════════════════════════════════════════
//
//    /**
//     * Adds a marker to the map
//     */
//    private void addMarker(Location location, int iconResId, String title) {
//        // TODO: Implement based on your map library
//        // Example for Google Maps:
//        // MarkerOptions options = new MarkerOptions()
//        //     .position(new LatLng(location.latitude, location.longitude))
//        //     .title(title)
//        //     .icon(BitmapDescriptorFactory.fromResource(iconResId));
//        // mapView.addMarker(options);
//    }
//
//    /**
//     * Colors a map cell
//     */
//    private void colorMapCell(String cellId, int color) {
//        // TODO: Implement grid cell coloring
//        // Example: Use polygons or overlay tiles
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // Dismiss dialog if it's showing
//        if (layerDialog != null && layerDialog.isShowing()) {
//            layerDialog.dismiss();
//        }
//    }

}