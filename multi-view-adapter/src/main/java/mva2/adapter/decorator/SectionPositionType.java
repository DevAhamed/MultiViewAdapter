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

package mva2.adapter.decorator;

/**
 * SectionPositionType refers to position of section inside the adapter
 *
 * <br>
 * <br>
 *
 * SectionPositionType is an enum value, which has only possible three values.
 * <li>1. FIRST - Denotes that Section is first inside adapter</li>
 * <li>2. MIDDLE - Denotes that Section is neither first nor last inside adapter</li>
 * <li>3. LAST - Denotes that Section is last inside adapter</li>
 */
public enum SectionPositionType {
  FIRST, MIDDLE, LAST
}
