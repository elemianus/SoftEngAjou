package sweng.ajou.kr.heatingcontrolclient;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Peter on 21-11-2016.
 */

public class Util {
    public static int doesJSONDataContainError(String inputData){
        int statusCode = 0;
        if(inputData.length()<2){
            statusCode = 1;
            return statusCode;
        }
        if(inputData.contains("{\"status\": \"error\", \"data\": null, \"message\": \"Unsupported handle was provided\"}")){
            statusCode = 2;
            return statusCode;
        }
        if(inputData.contains("{\"status\": \"error\", \"data\": null, \"message\": \"No such user\"}")){
            statusCode=4;
            return statusCode;
        }
        try{
            JSONArray testArray = new JSONArray(inputData);
        }catch (JSONException e){
            e.printStackTrace();
            statusCode = 3;
            return statusCode;
        }

        return statusCode;
    }

    public static String getDescriptionForErrorCode(int input){
        switch (input){
            case 0:
                return "Ingen fejl";
            case 1:
                return "Intet data retuneret, prøv at opdateret";
            case 2:
                return "Ikke understøttet handle fra serveren, prøv at hente data igen";
            case 3:
                return "Data er ikke formateret korrekt, prøv at opdatere igen";
            case 4:
                return "Fejl i authentificeringen, prøv at hente data igen.";
            case 5:
                return "Problem med at hente data, tjek om du har adgang til internettet";
            case 6:
                return "Problem med internet forbindelsen, prøv igen";
            default:
                return "Ikke kendt fejl";
        }
    }

    public static String contentBetwenStrings(String data, String startString, String endString) {
        try {
            int start = data.indexOf(startString);
            int endStop = data.indexOf(endString, start);
            start += startString.length();
            String result = (String) data.subSequence(start, endStop);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static boolean getInternetState(Context context) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (connectionManager.getActiveNetworkInfo() != null && connectionManager.getActiveNetworkInfo().isAvailable()
                && connectionManager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else
            return false;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

}
