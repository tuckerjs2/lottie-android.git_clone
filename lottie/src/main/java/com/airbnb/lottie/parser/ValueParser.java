package com.airbnb.lottie.parser;

import android.util.JsonReader;

import java.io.IOException;

public interface ValueParser<V> {
  V parse(JsonReader reader, float scale) throws IOException;
}
