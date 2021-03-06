package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.util.JsonReader;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatablePathValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.CircleShape;

import java.io.IOException;

public class CircleShapeParser {

  private CircleShapeParser() {}

  public static CircleShape parse(
      JsonReader reader, LottieComposition composition) throws IOException {
    String name = null;
    AnimatableValue<PointF, PointF> position = null;
    AnimatablePointValue size = null;
    boolean reversed = false;

    while (reader.hasNext()) {
      switch (reader.nextName()) {
        case "nm":
          name = reader.nextString();
          break;
        case "p":
          position = AnimatablePathValue
              .createAnimatablePathOrSplitDimensionPath(reader, composition);
          break;
        case "s":
          size = AnimatableValueParser.parsePoint(reader, composition);
          break;
        case "d":
          // "d" is 2 for normal and 3 for reversed.
          reversed = reader.nextInt() == 3;
          break;
        default:
          reader.skipValue();
      }
    }

    return new CircleShape(name, position, size, reversed);
  }
}
