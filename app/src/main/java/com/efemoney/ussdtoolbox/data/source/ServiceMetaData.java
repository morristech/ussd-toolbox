package com.efemoney.ussdtoolbox.data.source;

/**
 * Created by Efe on 01/03/2017.
 */

public interface ServiceMetaData {

    boolean getFavorite(String serviceKey);

    int getClicks(String serviceKey);

    void toggleFavorite(String serviceKey);

    void incrementClick(String serviceKey);
}
