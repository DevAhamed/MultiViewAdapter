package mva2.adapter;

import mva2.adapter.util.Mode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class) public class SectionExpansionTest extends BaseTest {

  @Test public void sectionExpansionModeTest_Single() {
    adapter.removeAllSections();
    adapter.addSection(itemSection1);
    adapter.addSection(listSection1);
    adapter.addSection(headerSection1);
    adapter.addSection(nestedSection1);

    adapter.setSectionExpansionMode(Mode.SINGLE);

    adapter.collapseAllSections();

    adapter.onSectionExpansionToggled(19);
    assertEquals(adapter.getItemCount(), 40);

    adapter.onSectionExpansionToggled(19);
    assertEquals(adapter.getItemCount(), 31);

    adapter.onSectionExpansionToggled(30);
    assertEquals(adapter.getItemCount(), 40);

    adapter.onSectionExpansionToggled(19);
    assertEquals(adapter.getItemCount(), 40);
  }

  @Test public void sectionExpansionModeTest_Multiple() {
    adapter.removeAllSections();
    adapter.addSection(itemSection1);
    adapter.addSection(listSection1);
    adapter.addSection(headerSection1);
    adapter.addSection(nestedSection1);

    adapter.setSectionExpansionMode(Mode.MULTIPLE);

    adapter.collapseAllSections();

    adapter.onSectionExpansionToggled(19);
    assertEquals(adapter.getItemCount(), 40);

    adapter.onSectionExpansionToggled(19);
    assertEquals(adapter.getItemCount(), 31);

    adapter.onSectionExpansionToggled(30);
    assertEquals(adapter.getItemCount(), 40);

    adapter.onSectionExpansionToggled(19);
    assertEquals(adapter.getItemCount(), 49);
  }
}
