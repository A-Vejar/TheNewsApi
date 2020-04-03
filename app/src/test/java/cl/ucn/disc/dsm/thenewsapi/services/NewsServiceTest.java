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

package cl.ucn.disc.dsm.thenewsapi.services;

import cl.ucn.disc.dsm.thenewsapi.model.News;
import cl.ucn.disc.dsm.thenewsapi.services.mockup.MockupNewsService;
import cl.ucn.disc.dsm.thenewsapi.services.newsapi.NewsApiService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The test of NewsService.
 *
 * @author Ariel-Vejar.
 */
public final class NewsServiceTest {

  /**
   * The Logger.
   */
  private static final Logger log = LoggerFactory.getLogger(NewsServiceTest.class);

  /**
   * Test {@link NewsService#getEverything(int)}.
   */
  @Test
  public void testGetNewsMockup() {

    log.debug("Testing the NewsService ...");

    // The news service.
    final NewsService service = new MockupNewsService();

    // The List of News.
    final List<News> news = service.getEverything(2);

    Assertions.assertNotNull(news);
    Assertions.assertEquals(news.size(), 2, "Size error ...");

    for (final News aNews : news) {
      log.debug("News: {}.", aNews);
    }

    log.debug("Done.");
  }

  /**
   * Test {@link NewsService#getEverything(int)} with NewsAPI.org.
   */
  @Test
  public void testGetNewsApi() {

    final int size = 20;
    log.debug("Testing the NewsApi ... requesting {} News.", size);

    // The news service.
    final NewsService service = new NewsApiService();

    // The List of News.
    final List<News> news = service.getTopHeadLines(size);

    Assertions.assertNotNull(news);
    Assertions.assertEquals(news.size(), size, "Size error ...");

    for(final News aNews : news) {
      log.debug("News: {}.", aNews);
    }

    /*
    for(int i = 0; i < news.size(); i++) {
      log.debug("News: {}.", news.get(i).getUrl());
    }
     */

    log.debug("Done.");
  }
}
