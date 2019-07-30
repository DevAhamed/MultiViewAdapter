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

package dev.ahamed.mva.sample.data;

import android.graphics.Color;
import dev.ahamed.mva.sample.R;
import dev.ahamed.mva.sample.data.model.ColoredItem;
import dev.ahamed.mva.sample.data.model.Comment;
import dev.ahamed.mva.sample.data.model.FaqItem;
import dev.ahamed.mva.sample.data.model.NewsItem;
import dev.ahamed.mva.sample.data.model.NumberItem;
import dev.ahamed.mva.sample.data.model.Person;
import dev.ahamed.mva.sample.data.model.SelectableItem;
import dev.ahamed.mva.sample.data.model.TextItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataManager {

  private static final int[] newsColorsList = new int[] {
      R.color.sample_color_one, R.color.sample_color_two, R.color.sample_color_three,
      R.color.sample_color_four, R.color.sample_color_five
  };
  private static final int[] newsThumbnails = new int[] {
      R.drawable.ic_circle_variant, R.drawable.ic_heart, R.drawable.ic_star
  };
  private static String[] newsDummyTitles = {
      "Introducing new Android Excellence apps and games on Google Play ",
      "Life can be tough; here are a few SDK improvements to make it a little easier",
      "You can't rush perfection, but now you can file bugs against it ",
      "Announcing Apps for Android and Google developer podcast",
      "Congrats to the new Android Excellence apps and games on Google Play "
  };
  private static String[] languages = {
      "Arabic", "English", "French", "Hindi", "Russian", "Spanish"
  };
  private static String[] topics = {
      "Local", "World", "Sports", "Economy", "Technology", "Entertainment"
  };
  private static String[] newsPapers = {
      "The New York Times", "China Daily", "Times of India", "The Guardian",
      "The Sydney Morning Herald"
  };
  private static String[] televisions = {
      "BBC", "Fox News", "CNN", "NDTV", "Al-Jazeera", "Euronews"
  };
  private static String[] websites = {
      "Reddit", "Google News", "Yahoo News", "Huffington Post", "Reuters"
  };
  private static String[] cast = {
      "Tony Stark", "Hans Olo", "Alice", "Petey Cruiser"
  };
  private static String[] castOverline = {
      "as ROBIN BANKS", "as LUKE WARM", "as MARIA HILL", "as PETE SARIYA"
  };
  private static String[] crew = {
      "John Doe", "Jane Doe", "Mario Speedwagon", "Rick O'Shea"
  };
  private static String[] crewOverline = {
      "DIRECTOR", "DIRECTOR", "WRITER", "WRITER"
  };
  private static String[] producer = {
      "Monty Carlo", "Jimmy Changa", "Barry Wine", "Buster Hyman"
  };
  private static String[] producerOverline = {
      "EXECUTIVE PRODUCER", "PRODUCER", "PRODUCER", "ASSOCIATE PRODUCER"
  };
  private static String[] authors = {
      "Concurag", "CrazyBilki", "emakerOf", "patA1", "trollicon", "iAmThere", "silento",
      "silentreader"
  };
  private static String[] comments = {
      "You are so inspiring! Such illustration, many avatar, so sublime",
      "Vastly sick notification :-) Just fab, friend.",
      "Excellent =) I want to make love to the use of button and background image!",
      "Sleek work you have here. Let me take a nap... great shot, anyway.",
      "Nice use of violet in this shot :-) Mission accomplished. It's strong :-)",
      "These are graceful and engaging mate", "I want to learn this kind of shot! Teach me. ",
      "This shot has navigated right into my heart.",
      "Immensely thought out! Excellent shot! Ahhhhhhh...",
      "Nice use of light in this style dude, and Those icons blew my mind.",
      "Button, gradient, boldness, texture – excellent =)",
      "Sky blue. I wonder what would have happened if I made this",
      "This work has navigated right into my heart.",
      "Engaging. It keeps your mind occupied while you wait.",
      "Background, hero, experience, colour – incredible :-)",
      "Very thought out! I wonder what would have happened if I made this",
      "Strong mate I approve the use of layout and lines!", "This is magical work :)",
      "Classic animation! Just delightful!", "Nice use of magenta in this shapes!!"
  };
  private static String[] postedTime = {
      "Yesterday", "12 hours ago", "5 hours ago", "2 hours ago", "1 hour ago", "5 minutes ago",
      "2 minutes ago", "1 minute ago", "Just now"
  };
  private final Random random = new Random();

  public List<FaqItem> getAccountFaq() {
    List<FaqItem> items = new ArrayList<>();
    items.add(new FaqItem("How do I activate my account?"));
    items.add(new FaqItem("How can I change my shipping address?"));
    items.add(new FaqItem("What do you mean by loyalty points? How do I earn it?"));
    items.add(new FaqItem("How can I track my orders & payment?"));
    items.add(new FaqItem("Ho do I change my contact details?"));
    return items;
  }

  public List<Person> getCast() {
    List<Person> items = new ArrayList<>(cast.length);
    int i = 0;
    for (String name : cast) {
      items.add(new Person(name, name.substring(0, 1), castOverline[i++]));
    }
    return items;
  }

  public List<ColoredItem> getColoredItems(int count) {
    List<ColoredItem> coloredItems = new ArrayList<>(count);
    for (int i = 1; i <= count; i++) {
      coloredItems.add(new ColoredItem(i, newsColorsList[random.nextInt(5)]));
    }
    return coloredItems;
  }

  public List<Comment> getComments() {
    int totalComments = random.nextInt(10) + 2;
    return generateComment(totalComments, 0);
  }

  public List<Person> getCrew() {
    List<Person> items = new ArrayList<>(crew.length);
    int i = 0;
    for (String name : crew) {
      items.add(new Person(name, name.substring(0, 1), crewOverline[i++]));
    }
    return items;
  }

  public List<SelectableItem> getLanguages() {
    List<SelectableItem> items = new ArrayList<>(languages.length);
    for (String language : languages) {
      items.add(new SelectableItem(R.drawable.ic_language, R.color.sample_color_one, language));
    }
    return items;
  }

  public List<FaqItem> getMiscFaq() {
    List<FaqItem> items = new ArrayList<>();
    items.add(new FaqItem("What is life?"));
    items.add(new FaqItem("What is love?"));
    items.add(new FaqItem("Is the meaning of life the same for animals and humans?"));
    items.add(new FaqItem("Tab? Or Spaces?"));
    return items;
  }

  public List<NewsItem> getNewsList(boolean isOffline) {
    List<NewsItem> numberItems = new ArrayList<>(5);
    for (int i = 0; i < 5; i++) {
      int newsSource = random.nextInt(2);
      numberItems.add(new NewsItem(i, newsSource == 0 ? "Umbrella News" : "Fake News",
          newsSource == 0 ? R.drawable.ic_umbrella : R.drawable.ic_fake_news,
          Color.parseColor(newsSource == 0 ? "#01CC9D" : "#FE1743"),
          newsThumbnails[random.nextInt(3)], newsColorsList[random.nextInt(4)], newsDummyTitles[i],
          "14-July-2018", isOffline));
    }
    return numberItems;
  }

  public List<SelectableItem> getNewsPapers() {
    List<SelectableItem> items = new ArrayList<>(newsPapers.length);
    for (String newsPaper : newsPapers) {
      items.add(new SelectableItem(R.drawable.ic_newspaper, R.color.sample_color_three, newsPaper));
    }
    return items;
  }

  public List<NumberItem> getNumberItems(int count) {
    return getNumberItems(1, count);
  }

  public List<NumberItem> getNumberItems(int start, int count) {
    List<NumberItem> numberItems = new ArrayList<>(count);
    for (int i = start; i < count + start; i++) {
      numberItems.add(new NumberItem(i));
    }
    return numberItems;
  }

  public List<FaqItem> getPaymentFaq() {
    List<FaqItem> items = new ArrayList<>();
    items.add(new FaqItem("Why is there a checkout limit? / What are all the checkout limits?"));
    items.add(new FaqItem("How long will it take for my order to arrive after I make payment?"));
    items.add(new FaqItem("How much is the handling fee?"));
    items.add(new FaqItem("What are the payment methods available?"));
    items.add(new FaqItem("How can I use my remaining Account Credits?"));
    items.add(new FaqItem("What happens if there's been a delivery mishap to my order?"));
    return items;
  }

  public List<Person> getProducers() {
    List<Person> items = new ArrayList<>(producer.length);
    int i = 0;
    for (String name : producer) {
      items.add(new Person(name, name.substring(0, 1), producerOverline[i++]));
    }
    return items;
  }

  public List<SelectableItem> getTelevisionChannels() {
    List<SelectableItem> items = new ArrayList<>(televisions.length);
    for (String channel : televisions) {
      items.add(new SelectableItem(R.drawable.ic_tv, R.color.sample_color_three, channel));
    }
    return items;
  }

  public List<TextItem> getTextItems(int count) {
    List<TextItem> textItems = new ArrayList<>(count);
    for (int i = 1; i <= count; i++) {
      textItems.add(new TextItem("Text Item " + i));
    }
    return textItems;
  }

  public List<SelectableItem> getTopics() {
    List<SelectableItem> items = new ArrayList<>(topics.length);
    for (String topic : topics) {
      items.add(new SelectableItem(R.drawable.ic_topic, R.color.sample_color_two, topic));
    }
    return items;
  }

  public List<SelectableItem> getWebsites() {
    List<SelectableItem> items = new ArrayList<>(websites.length);
    for (String website : websites) {
      items.add(new SelectableItem(R.drawable.ic_web, R.color.sample_color_three, website));
    }
    return items;
  }

  private List<Comment> generateComment(int count, int level) {
    if (level > 7) {
      return new ArrayList<>();
    }
    List<Comment> comments = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      comments.add(generateComment(level));
    }
    return comments;
  }

  private Comment generateComment(int level) {
    return new Comment(authors[random.nextInt(8)], comments[random.nextInt(20)],
        postedTime[random.nextInt(2) + level], generateComment(random.nextInt(5), level + 1));
  }
}
