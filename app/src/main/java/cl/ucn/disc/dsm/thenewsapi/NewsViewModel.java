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

package cl.ucn.disc.dsm.thenewsapi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cl.ucn.disc.dsm.thenewsapi.model.News;
import cl.ucn.disc.dsm.thenewsapi.services.NewsService;
import cl.ucn.disc.dsm.thenewsapi.services.newsapi.NewsApiService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NewsViewModel extends ViewModel {

  /**
   * Logger.
   */
  private static final Logger log = LoggerFactory.getLogger(NewsViewModel.class);

  /**
   * Size of news.
   */
  private static final int PAGE_SIZE = 50;

  /**
   * The {@link List} of {@link News} to provide.
   */
  private final MutableLiveData<List<News>> news = new MutableLiveData<>();

  /**
   * The Exception in case of error.
   */
  private final MutableLiveData<Exception> exception = new MutableLiveData<>();

  /**
   * The provider of {@link News}.
   */
  private NewsService service = new NewsApiService();

  /**
   * LiveData of News to use in the view.
   *
   * @return - The List of News inside a LiveData.
   */
  public LiveData<List<News>> getTopHeadLines() {
    return news;
  }

  /**
   * LiveData of Exception to use in the view.
   *
   * @return - The Exception in case of error.
   */
  public LiveData<Exception> getException() {
    return exception;
  }

  /**
   * Update the internal list of News.
   *
   * <p>NOTE: Need to run in background.</p>.
   *
   * @return - The number of news loaded.
   */
  public int refresh() {

    try {

      // 1. Get the list of news from NewsApi.
      final List<News> aNews = service.getTopHeadLines(PAGE_SIZE);

      // 2. Set the values (NEED to be in background).
      news.postValue(aNews);

      // 3. All ok.
      return aNews.size();

    }catch(final Exception e) {
      log.error("Error", e);

      // 2. Set the exception.
      exception.postValue(e);

      // 3. All error.
      return -1;
    }
  }
}
