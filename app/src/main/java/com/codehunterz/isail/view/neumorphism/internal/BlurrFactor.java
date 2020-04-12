package com.codehunterz.isail.view.neumorphism.internal;

import android.graphics.Color;

public class BlurrFactor {
    static final int DEFAULT_RADIUS = 25;
    static final int DEFAULT_SAMPLING = 1;

    public int width;
    public int height;
    public int radius = DEFAULT_RADIUS;
    public int sampling = DEFAULT_SAMPLING;
    public int color = Color.TRANSPARENT;
}
