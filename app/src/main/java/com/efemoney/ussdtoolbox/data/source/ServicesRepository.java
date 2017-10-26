package com.efemoney.ussdtoolbox.data.source;

import com.efemoney.ussdtoolbox.data.model.Action;
import com.efemoney.ussdtoolbox.data.model.Service;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Efe on 22/02/2017.
 */

public interface ServicesRepository {

    boolean isFromCache();

    void favoriteService(String key);

    void clickService(String key);

    Observable<Service> getService(String key);

    Observable<List<Service>> getServices();

    Observable<List<Action>> getActionsForService(String key);
}
