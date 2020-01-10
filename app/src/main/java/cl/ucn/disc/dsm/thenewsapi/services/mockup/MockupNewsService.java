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

package cl.ucn.disc.dsm.thenewsapi.services.mockup;

import cl.ucn.disc.dsm.thenewsapi.model.News;
import cl.ucn.disc.dsm.thenewsapi.services.NewsService;
import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.ZonedDateTime;

public final class MockupNewsService implements NewsService {

  // final ZonedDateTime date = ZonedDateTime.now(News.ZONE_ID);

  /**
   * Constructor.
   */
  public MockupNewsService() {
  }

  /**
   * Get the News from the backend.
   *
   * @param pageSize how many.
   * @return the {@link List} of {@link News}.
   */
  @Override
  public List<News> getNews(int pageSize) {

    final List<News> news = new ArrayList<>();

    news.add(new News(
        1L,
        "First Title",
        "First Source",
        "First Author",
        "http://first.cl",
        "http://first.cl/first.jpg",
        "First Description",
        "First Content",
        ZonedDateTime.now(News.ZONE_ID))
    );

    news.add(new News(
        2L,
        "Second Title",
        "Second Source",
        "Second Author",
        "http://second.cl",
        "http://second.cl/second.jpg",
        "Second Description",
        "Second Content",
        ZonedDateTime.now(News.ZONE_ID))
    );

    return news;
  }
}
