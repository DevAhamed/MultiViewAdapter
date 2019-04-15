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

package mva2.adapter;

import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SpanSizeLookup;
import java.util.ArrayList;
import java.util.List;
import mva2.adapter.testconfig.AdapterDataObserver;
import mva2.adapter.testconfig.Comment;
import mva2.adapter.testconfig.CommentBinder;
import mva2.adapter.testconfig.DecorationCapture;
import mva2.adapter.testconfig.HashMapCache;
import mva2.adapter.testconfig.Header;
import mva2.adapter.testconfig.HeaderBinder;
import mva2.adapter.testconfig.TestItem;
import mva2.adapter.testconfig.TestItemBinder;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

//@formatter:off
/*

         ADAPTER STRUCTURE - GRID LAYOUT MANAGER


         [              0             ]            ---  ItemSection1
         [1]  [2]  [3]  [4]   [5]   [6]            |
         [7]  [8]  [9]  [10]  [11] [12]            |--  ListSection1
         [13] [14] [15] [16]  [17] [18]            |

         [             19             ]             ---  ItemSection2    |
         [  20  ]   [  21  ]   [  22  ]             |                    |-- HeaderSection1
         [  23  ]   [  24  ]   [  25  ]             |--  ListSection2    |
         [  26  ]   [  27  ]   [  28  ]             |                    |

         [             29             ]             ---  ItemSection3                       |
         [  30  ]   [  31  ]   [  32  ]             |                                       |
         [  33  ]   [  34  ]   [  35  ]             |--  ListSection3                       |
         [  36  ]   [  37  ]   [  38  ]             |                                       |
                                                                                            |-- NestedSection1
         [             39             ]             ---  ItemSection4    |                  |
         [  40  ]   [  41  ]   [  42  ]             |                    |-- HeaderSection2 |
         [  43  ]   [  44  ]   [  45  ]             |--  ListSection4    |                  |
         [  46  ]   [  47  ]   [  48  ]             |                    |                  |

         [             49             ]             |---  TreeSection1
          [             50            ]             |---  TreeSection2
          [             51            ]             |---  TreeSection3
           [             52           ]             |---  TreeSection4
          [             53            ]             |---  TreeSection5
           [             54           ]             |---  TreeSection6
           [             55           ]             |---  TreeSection7
           [             56           ]             |---  TreeSection8


  Items which are full-width are using HeaderBinder. Ex: 0,19,29.,
  Items which are not full-width are using ItemBinder. Ex: 1,11,21, 35.,
  Child items of TreeSection is using TreeItemBinder Ex: 50, 53, 57

*/
// @formatter:on
@SuppressWarnings({ "WeakerAccess", "unused" }) public class BaseTest {

  @Mock protected AdapterDataObserver adapterDataObserver;
  @Mock protected DecorationCapture testItemDecoratorCapture;
  @Mock protected DecorationCapture headerDecoratorCapture;
  @Mock protected DecorationCapture sectionDecoratorCapture;
  @Mock protected RecyclerView recyclerView;
  @Mock protected RecyclerView.State state;
  @Mock protected Canvas canvas;
  @Mock protected View child;
  @Mock protected ViewGroup viewGroup;

  @Spy protected TestItemBinder testItemBinder = new TestItemBinder();
  @Spy protected HeaderBinder headerBinder = new HeaderBinder();
  @Spy protected CommentBinder commentBinder = new CommentBinder();
  @Mock protected TestItemBinder.ViewHolder testItemViewHolder;
  @Mock protected HeaderBinder.ViewHolder headerViewHolder;
  @Mock protected CommentBinder.ViewHolder commentViewHolder;

  protected ItemSection<Header> itemSection1;
  protected ListSection<TestItem> listSection1;
  protected HeaderSection<Header, TestItem> headerSection1;
  protected ItemSection<Header> itemSection3;
  protected ListSection<TestItem> listSection3;
  protected HeaderSection<Header, TestItem> headerSection2;
  protected NestedSection nestedSection1;
  protected TreeSection<Comment> treeSection1;
  protected TreeSection<Comment> treeSection2;
  protected TreeSection<Comment> treeSection3;
  protected TreeSection<Comment> treeSection4;
  protected TreeSection<Comment> treeSection5;
  protected TreeSection<Comment> treeSection6;
  protected TreeSection<Comment> treeSection7;
  protected TreeSection<Comment> treeSection8;
  protected TestAdapter adapter;
  protected SpanSizeLookupForTest spanSizeLookup = new SpanSizeLookupForTest();

  @Before public void setUpTest() {
    MockitoAnnotations.initMocks(this);

    adapter = new TestAdapter(adapterDataObserver, spanSizeLookup);
    adapter.setSpanCount(6);

    itemSection1 = new ItemSection<>();
    listSection1 = new ListSection<>();
    headerSection1 = new HeaderSection<>(new Header("ItemSection2"));
    itemSection3 = new ItemSection<>();
    listSection3 = new ListSection<>();
    headerSection2 = new HeaderSection<>();
    nestedSection1 = new NestedSection();

    headerSection1.setSpanCount(3);
    nestedSection1.setSpanCount(3);

    nestedSection1.addSection(itemSection3);
    nestedSection1.addSection(listSection3);
    nestedSection1.addSection(headerSection2);

    adapter.addSection(itemSection1);
    adapter.addSection(listSection1);
    adapter.addSection(headerSection1);
    adapter.addSection(nestedSection1);

    // ITEM SECTION 1
    itemSection1.setItem(new Header("ItemSection1"));

    // LIST SECTION 1
    List<TestItem> testItemList = new ArrayList<>();
    for (int i = 1; i <= 18; i++) {
      testItemList.add(new TestItem(i, "Test Item  " + i));
    }
    listSection1.set(testItemList);

    // HEADER SECTION 1
    List<TestItem> testItemList2 = new ArrayList<>();
    for (int i = 20; i <= 28; i++) {
      testItemList2.add(new TestItem(i, "Test Item  " + i));
    }
    headerSection1.getListSection().set(testItemList2);

    // NESTED SECTION 1
    itemSection3.setItem(new Header("ItemSection1"));
    List<TestItem> testItemList3 = new ArrayList<>();
    for (int i = 30; i <= 38; i++) {
      testItemList3.add(new TestItem(i, "Test Item  " + i));
    }
    listSection3.set(testItemList3);

    headerSection2.setHeader((new Header("ItemSection2")));
    List<TestItem> testItemList4 = new ArrayList<>();
    for (int i = 40; i <= 48; i++) {
      testItemList4.add(new TestItem(i, "Test Item  " + i));
    }
    headerSection2.getListSection().set(testItemList4);

    // TREE SECTION 1
    treeSection1 = new TreeSection<>(new Comment(49, "Author 49", "Comment 49"));
    treeSection2 = new TreeSection<>(new Comment(50, "Author 50", "Comment 50"));
    treeSection3 = new TreeSection<>(new Comment(51, "Author 51", "Comment 51"));
    treeSection4 = new TreeSection<>(new Comment(52, "Author 52", "Comment 52"));
    treeSection5 = new TreeSection<>(new Comment(53, "Author 53", "Comment 53"));
    treeSection6 = new TreeSection<>(new Comment(54, "Author 54", "Comment 54"));
    treeSection7 = new TreeSection<>(new Comment(55, "Author 55", "Comment 55"));
    treeSection8 = new TreeSection<>(new Comment(56, "Author 56", "Comment 56"));

    treeSection5.addSection(treeSection6);
    treeSection5.addSection(treeSection7);
    treeSection5.addSection(treeSection8);

    treeSection3.addSection(treeSection4);

    treeSection1.addSection(treeSection2);
    treeSection1.addSection(treeSection3);
    treeSection1.addSection(treeSection5);

    adapter.addSection(treeSection1);
  }

  public static class SpanSizeLookupForTest extends SpanSizeLookup {

    /**
     * Hard coded span index data.
     */
    private final int[] spanIndex = {
        0,                                                               // ItemSection1
        0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5,            // ListSection1
        0, 0, 2, 4, 0, 2, 4, 0, 2, 4,                                    // HeaderSection1
        0, 0, 2, 4, 0, 2, 4, 0, 2, 4, 0, 0, 2, 4, 0, 2, 4, 0, 2, 4,      // NestedSection1
        0, 0, 0, 0, 0, 0, 0, 0                                           // TreeSection1
    };

    public SpanSizeLookupForTest() {
      super(new HashMapCache());
    }

    @Override public int getCachedSpanIndex(int position, int spanCount) {
      return spanIndex[position];
    }
  }
}
