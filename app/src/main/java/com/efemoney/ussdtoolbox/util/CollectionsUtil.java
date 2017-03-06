package com.efemoney.ussdtoolbox.util;

import java.util.Collection;

/**
 * Created by Efe on 23/02/2017.
 */

public class CollectionsUtil {

    public static boolean isEmpty(Collection<?> collection) {

        return collection == null || collection.isEmpty();
    }
}
