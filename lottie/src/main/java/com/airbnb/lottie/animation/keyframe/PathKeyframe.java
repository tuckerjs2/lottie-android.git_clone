package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.util.JsonToken;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.animation.Keyframe;
import com.airbnb.lottie.parser.KeyframeParser;
import com.airbnb.lottie.parser.ValueParser;
import com.airbnb.lottie.utils.Utils;

import java.io.IOException;

public class PathKeyframe extends Keyframe<PointF> {
  @Nullable private Path path;

  private PathKeyframe(LottieComposition composition, Keyframe<PointF> keyframe) {
    super(composition, keyframe.startValue, keyframe.endValue, keyframe.interpolator,
        keyframe.startFrame, keyframe.endFrame);

    // This must use equals(float, float) because PointF didn't have an equals(PathF) method
    // until KitKat...
    boolean equals = endValue != null && startValue != null &&
        startValue.equals(endValue.x, endValue.y);
    //noinspection ConstantConditions
    if (endValue != null && !equals) {
      path = Utils.createPath(startValue, endValue, keyframe.pathCp1, keyframe.pathCp2);
    }
  }

  public static class Factory {
    private Factory() {
    }

    public static PathKeyframe newInstance(JsonReader reader, LottieComposition composition,
        ValueParser<PointF> valueParser) throws IOException {
      boolean animated = reader.peek() == JsonToken.BEGIN_OBJECT;
      Keyframe<PointF> keyframe = KeyframeParser.parse(
          reader, composition, Utils.dpScale(), valueParser, animated);

      return new PathKeyframe(composition, keyframe);
    }
  }

  /** This will be null if the startValue and endValue are the same. */
  @Nullable Path getPath() {
    return path;
  }
}
