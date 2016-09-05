package gr.uoa.di.google.maps;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;


import com.google.maps.DirectionsApi;
import com.google.maps.model.DirectionsResult;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Despina on 4/9/2016.
 */
 class DirectionsFetcher extends AsyncTask<URL, Integer, String> {

    private List<LatLng> latLngs = new ArrayList<LatLng>();
    Context context;

    public DirectionsFetcher(Context context){
        this.context=context;
    }


    @Override
    protected String doInBackground(URL... params) {
        try {
//            HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
//                @Override
//                public void initialize(HttpRequest request) {
//                    request.setParser(new JsonObjectParser(JSON_FACTORY));
//                }
//            });
//
//            GenericUrl url = new GenericUrl("http://maps.googleapis.com/maps/api/directions/json");
//            url.put("origin", "Chicago,IL");
//            url.put("destination", "Los Angeles,CA");
//            url.put("sensor",false);

/*         DirectionsResult directionsResult = DirectionsApi.getDirections(context,"Chicago,IL","Los Angeles,CA");

//            HttpRequest request = requestFactory.buildGetRequest(url);
//            HttpResponse httpResponse = request.execute();
//            DirectionsResult directionsResult = httpResponse.parseAs(DirectionsResult.class);
            String encodedPoints = directionsResult.routes.get(0).overviewPolyLine.points;
            latLngs = PolyUtil.decode(encodedPoints);*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
