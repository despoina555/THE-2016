package gr.uoa.di.google.maps;

import java.util.List;

/**
 * Created by Despina on 10/9/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}