/*
 * Copyright 2019-2020 Ariel Vejar Martinez <ariel.vejar@live.cl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cl.ucn.disc.dsm.thenewsapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZonedDateTime;

/**
 * Test of {@link cl.ucn.disc.dsm.thenewsapi.model.News}.
 *
 * @author Ariel-Vejar.
 */
public class NewsTest {

  /**
   * The Logger.
   */
  private static final Logger log = LoggerFactory.getLogger(NewsTest.class);

  /**
   * The Test of Constructor.
   */
  @Test
  public void testConstructor() {

    log.debug("Testing the Constructor ..");

    // The values.
    final Long id = 1L;
    final String title = "The Title";
    final String source = "The Source";
    final String author = "The Author";
    final String url = "The URL";
    final String urlPic = "The URL-Picture";
    final String description = "The Description";
    final String content = "The Content";
    final ZonedDateTime date = ZonedDateTime.now(News.ZONE_ID);

    // The Constructor.
    final News news = new News(id, title, source, author, url, urlPic, description, content, date);

    // Testing.
    Assertions.assertEquals(id, news.getId());
    Assertions.assertEquals(title, news.getTitle());
    Assertions.assertEquals(source, news.getSource());
    Assertions.assertEquals(author, news.getAuthor());
    Assertions.assertEquals(url, news.getUrl());
    Assertions.assertEquals(urlPic, news.getUrlPic());
    Assertions.assertEquals(description, news.getDescription());
    Assertions.assertEquals(content, news.getContent());
    Assertions.assertEquals(date, news.getDate());

    log.debug("Done.");
  }
}
