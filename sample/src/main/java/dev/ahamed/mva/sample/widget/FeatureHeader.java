/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.ahamed.mva.sample.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.util.TileDrawable;

public class FeatureHeader extends FrameLayout {

  private ImageView featureImage;
  private TextView featureText;

  public FeatureHeader(@NonNull Context context) {
    super(context);
    initViews(null);
  }

  public FeatureHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initViews(attrs);
  }

  public FeatureHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initViews(attrs);
  }

  public void setIcon(@DrawableRes int resourceId) {
    featureImage.setImageResource(resourceId);
  }

  public void setTitle(String text) {
    featureText.setText(text);
  }

  private void initViews(AttributeSet attrs) {
    inflate(getContext(), R.layout.widget_feature_header, this);

    featureImage = findViewById(R.id.iv_feature);
    featureText = findViewById(R.id.tv_feature);

    if (attrs == null) {
      return;
    }

    TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.FeatureHeader);
    int drawableRes = ta.getResourceId(R.styleable.FeatureHeader_icon, R.drawable.ic_thumb_up);
    featureImage.setImageResource(drawableRes);

    featureText.setText(ta.getText(R.styleable.FeatureHeader_title));

    Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_patter);
    setBackground(new TileDrawable(drawable, Shader.TileMode.REPEAT));

    ta.recycle();
  }
}
