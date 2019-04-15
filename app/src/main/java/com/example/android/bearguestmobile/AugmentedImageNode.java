package com.example.android.bearguestmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;

import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"AndroidApiChecker"})
public class AugmentedImageNode extends AnchorNode {

    private static final String TAG = "AugmentedImageNode";

    private AugmentedImage image;
    private Context context;
    private String imageName;
    private static CompletableFuture<ModelRenderable> pin;

    public AugmentedImageNode(Context context) {
        if (pin == null) {
            pin =
                    ModelRenderable.builder()
                            .setSource(context, Uri.parse("models/Pin.sfb"))
                            .build();
        }
        this.context = context;
    }

    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    public void setImage(AugmentedImage image) {
        this.image = image;
        this.imageName = image.getName();

        // If any of the models are not loaded, then recurse when all are loaded.
        if (!pin.isDone()) {
            CompletableFuture.allOf(pin)
                    .thenAccept((Void aVoid) -> setImage(image))
                    .exceptionally(
                            throwable -> {
                                Log.e(TAG, "Exception loading", throwable);
                                return null;
                            });
        }

        // Set the anchor based on the center of the image.
        setAnchor(image.createAnchor(image.getCenterPose()));
        Vector3 localPosition = new Vector3();
        Node pinNode;
/*
Toast toast = Toast.makeText(context,
          imageName,
          Toast.LENGTH_SHORT);
toast.show();
*/

        // split imageName from underscore
        // index[0] of restaurantNameNum has the restaurant name, index[1] as page number
        String[] restaurantNameNum = imageName.split("[_\\.]");

        // test sendPost
        /*
        Toast toast = Toast.makeText(context,
                sendPost(restaurantNameNum[0]),
                Toast.LENGTH_SHORT);
        toast.show();
        */

        // use restaurantNameNum[0] to make API call for specific restaurant
        //Gson gson = new Gson();
        //MenuItem[] menuItems = gson.fromJson("[{\"x\":-0.01,\"z\":-0.175,\"substitution\":\"Substitute double white rice for naan and coconut rice.\",\"pageNum\":2},{\"x\":-0.2,\"z\":-0.05,\"substitution\":\"Ask for double tofu instead of chicken.\",\"pageNum\":2}," +
        //        "{\"x\":-0.45,\"z\":0.05,\"substitution\":\"Replace egg noodles with rice noodles. Ask for no lotus root.\",\"pageNum\":1},{\"x\":-0.45,\"z\":0.25,\"pageNum\":1}]", MenuItem[].class);


        // Uncomment this to make it work
        // TEST: Create MenuItem java objects with desired fields
        ArrayList<MenuItem> menuItemObjs = new ArrayList<>();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setX((float)-0.01);
        menuItem1.setZ((float)-0.175);
        menuItem1.setSubstitution("Substitute double white rice for naan and coconut rice.");
        menuItem1.setPageNum(2);

        MenuItem menuItem2 = new MenuItem();
        menuItem2.setX((float)-0.2);
        menuItem2.setZ((float)-0.05);
        menuItem2.setSubstitution("Ask for double tofu instead of chicken.");
        menuItem2.setPageNum(2);
        
        MenuItem menuItem3 = new MenuItem();
        menuItem3.setX((float)-0.45);
        menuItem3.setZ((float)0.05);
        menuItem3.setSubstitution("Replace egg noodles with rice noodles. Ask for no lotus root.");
        menuItem3.setPageNum(1);

        MenuItem menuItem4 = new MenuItem();
        menuItem4.setX((float)-0.45);
        menuItem4.setZ((float)0.25);
        //menuItem4.setSubstitution("Replace egg noodles with rice noodles. Ask for no lotus root.");
        menuItem4.setPageNum(1);

        menuItemObjs.add(menuItem1);
        menuItemObjs.add(menuItem2);
        menuItemObjs.add(menuItem3);
        menuItemObjs.add(menuItem4);

        /*
        // TEST use repo and view model (currently does not work)
        // Create Restaurant java object with all fields null except restaurantName
        MutableLiveData<Restaurant> thisRestaurantName = new MutableLiveData<>();
        thisRestaurantName.setValue(new Restaurant(restaurantNameNum[0]));
        Log.v("AR", "restaurante name = "+ restaurantNameNum[0]);

        // Call view model method to query from database
        DashboardViewModel dashboardViewModel = ViewModelProviders.of((MainActivity)context).get(DashboardViewModel.class);
        List<MenuItem> menuItemObjs = (dashboardViewModel.getMenuItemListForARByRestaurantName(thisRestaurantName)).getValue();
        */

        //Log.v("AR", "menuItemObjs size=" + menuItemObjs.size());

        //for (MenuItem item : menuItems) {
        for(MenuItem item: menuItemObjs) {
            Log.v("AR", "cycling thru item="+item.getItemName() +"parsedInt=" + Integer.parseInt(restaurantNameNum[1]));

            if (item.pageNum == Integer.parseInt(restaurantNameNum[1])) {

                Log.v("AR", "entered if statement for item="+item.getItemName() +"parsedInt=" + Integer.parseInt(restaurantNameNum[1]));

                localPosition.set(item.x * image.getExtentX(), -0.01f, item.z * image.getExtentZ());
                pinNode = new Node();
                pinNode.setParent(this);
                pinNode.setLocalPosition(localPosition);
                pinNode.setRenderable(pin.getNow(null));
                pinNode.setOnTapListener(new OnTapListener() {
                    @Override
                    public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                        if (item.substitution == null)
                            item.substitution = "No substitutions necessary.";
                        new AlertDialog.Builder(context)
                                //.setTitle("Delete entry")
                                .setMessage(item.substitution)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // close window
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                //.setNegativeButton(android.R.string.no, null)
                                //.setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                });
            }
        }
    }


    public AugmentedImage getImage() {
        return image;
    }
/*
    public String sendPost(String restaurantName) {
        try {
            String response;
            URL url = new URL("http://be-ar-guest.herokuapp.com/restaurant/getAllItemsByRestaurantName");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("restaurantName", restaurantName);

            //Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            //Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            //Log.i("MSG" , conn.getResponseMessage());

            response = conn.getResponseMessage();

            conn.disconnect();

            return response;
        } catch (Exception e) // gotta catch 'em all
        {
            return null;
        }
    }
    */
}
