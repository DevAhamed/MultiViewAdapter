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

package mva2.adapter.testconfig;

import android.annotation.SuppressLint;
import java.util.HashMap;
import mva2.adapter.internal.Cache;

public class HashMapCache implements Cache {

  @SuppressLint("UseSparseArrays") private final HashMap<Integer, Integer> cache = new HashMap<>();

  @Override public void append(int key, int value) {
    cache.put(key, value);
  }

  @Override public int get(int key, int valueIfKeyNotFound) {
    Integer value = cache.get(key);
    return value == null ? valueIfKeyNotFound : value;
  }

  @Override public void clear() {
    cache.clear();
  }
}
