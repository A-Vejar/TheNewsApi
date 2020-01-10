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

package cl.ucn.disc.dsm.thenewsapi.services.newsapi;

import cl.ucn.disc.dsm.thenewsapi.Transform;
import cl.ucn.disc.dsm.thenewsapi.model.News;
import cl.ucn.disc.dsm.thenewsapi.services.NewsService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApiService implements NewsService {

  /**
   * NewsAPI
   */
  private final NewsApi newsApi;

  /**
   * The Constructor.
   */
  public NewsApiService() {

    // https://futurestud.io/tutorials/retrofit-getting-started-and-android-client
    this.newsApi = new Retrofit.Builder()
        // Main URL
        .baseUrl(NewsApi.BASE_URL)
        // JSON to POJO
        .addConverterFactory(GsonConverterFactory.create())
        // Validates the interface
        .validateEagerly(true)
        // Build the Retrofit and get the NewsApi
        .build()
        .create(NewsApi.class);
  }

  /**
   * Get the News from the Call
   *
   * @param call - To use
   * @return - The {@link List} of {@link News}
   */
  private static List<News> getNewsFromCall(final Call<NewsApiResult> call) {

    try {
      // Get the result from the call
      final Response<NewsApiResult> response = call.execute();

      // Not successful
      if(!response.isSuccessful()) {

        // Error
        throw new NewsApiException(
            "Can't get the NewsResult, code: " + response.code()
            , new HttpException(response));
      }

      final NewsApiResult result = response.body();

      // No body
      if(result == null) {
        throw new NewsApiException("NewsResult was null");
      }

      // No articles
      if(result.articles == null) {
        throw new NewsApiException("Articles in NewsResult was null");
      }

      // Article to News (transformer) thought Stream
      return result.articles.stream()
          .map(Transform::transform)
          .collect(Collectors.toList());

    }catch(final IOException e) {
      throw new NewsApiException("Can't get the NewsResult", e);
    }
  }

  /**
   * Inner class - Exception
   */
  public static final class NewsApiException extends RuntimeException {

    public NewsApiException(final String message) {
      super(message);
    }

    public NewsApiException(final String message, final Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * Get the News from the Call
   *
   * @param pageSize how many.
   * @return - The {@link List} of {@link News}
   */
  @Override
  public List<News> getNews(int pageSize) {

    // Call
    final Call<NewsApiResult> call = this.newsApi.getEverything(pageSize);

    // Process the Call
    return getNewsFromCall(call);
  }
}
