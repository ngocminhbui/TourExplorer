package com.cntn14.ngocminhbui.tourexplorer.Direction;

import java.util.List;


public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
