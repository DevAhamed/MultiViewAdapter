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

package mva2.adapter.util;

import mva2.adapter.MultiViewAdapter;

/**
 * Enum represents different types of mode which can be set as selection or expansion modes.
 *
 * @see MultiViewAdapter#setSelectionMode(Mode)
 * @see MultiViewAdapter#setExpansionMode(Mode) (Mode)
 */
public enum Mode {

  /**
   * Removes the selection or expansion feature
   */
  NONE,

  /**
   * Applies to a single item ie., only one item can be selected or expanded at a time
   */
  SINGLE,

  /**
   * Applies to a multiple items ie., multiple items can be selected or expanded at a time
   */
  MULTIPLE,

  /**
   * Inherits the mode from its parent
   */
  INHERIT
}
