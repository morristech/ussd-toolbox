/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.efemoney.ussdtoolbox.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Adapted from github.com/romannurik/muzei/
 * <p/>
 * Also see https://code.google.com/p/android/issues/detail?id=9904
 * <p>
 * modified by efeturi.money
 */
public class FontUtil {

    private static final Map<String, Typeface> sTypefaceCache = new HashMap<>();

    private static final String REGULAR = "Cabin-Regular";
    private static final String BOLD = "Cabin-Bold";
    private static final String ITALIC = "Cabin-Italic";
    private static final String BOLD_ITALIC = "Cabin-BoldItalic";
    private static final String MEDIUM = "Cabin-Medium";
    private static final String MEDIUM_ITALIC = "Cabin-MediumItalic";
    private static final String SEMI_BOLD = "Cabin-SemiBold";
    private static final String SEMI_BOLD_ITALIC = "Cabin-SemiBoldItalic";

    static final String DEFAULT = REGULAR;

    private FontUtil() {
        // Prevent instantiation
    }

    public static Typeface get(Context context, String font) {

        return get(context, font, Typeface.NORMAL);
    }

    public static Typeface get(Context context, int style) {

        return get(context, REGULAR, style);
    }

    public static Typeface get(Context context, String font, int style) {

        font = format(font);

        if (REGULAR.equals(font) && style == Typeface.BOLD) font = BOLD;
        if (REGULAR.equals(font) && style == Typeface.ITALIC) font = ITALIC;
        if (REGULAR.equals(font) && style == Typeface.BOLD_ITALIC) font = BOLD_ITALIC;

        if (SEMI_BOLD.equals(font) && (style == Typeface.ITALIC || style == Typeface.BOLD_ITALIC))
            font = SEMI_BOLD_ITALIC;

        if (MEDIUM.equals(font) && (style == Typeface.ITALIC || style == Typeface.BOLD_ITALIC))
            font = MEDIUM_ITALIC;

        if (BOLD.equals(font) && ((style == Typeface.ITALIC) || style == Typeface.BOLD_ITALIC))
            font = BOLD_ITALIC;

        return getTypeface(context, path(font));
    }


    private static String path(String font) {

        return "fonts/" + font + (font.contains(".") ? "" : ".ttf");
    }

    private static String format(String font) {

        // Makes using regular, bold etc in XML easier
        if ("regular".equalsIgnoreCase(font.trim())) font = REGULAR;
        if ("semibold".equalsIgnoreCase(font.trim())) font = SEMI_BOLD;
        if ("medium".equalsIgnoreCase(font.trim())) font = MEDIUM;
        if ("bold".equalsIgnoreCase(font.trim())) font = BOLD;

        return font;
    }

    private static Typeface getTypeface(Context context, String path) {

        synchronized (sTypefaceCache) {

            if (!sTypefaceCache.containsKey(path)) {
                AssetManager assetManager = context.getApplicationContext().getAssets();
                Typeface tf = Typeface.createFromAsset(assetManager, path);
                sTypefaceCache.put(path, tf);
            }

            return sTypefaceCache.get(path);
        }
    }
}