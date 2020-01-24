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

import cl.ucn.disc.dsm.thenewsapi.services.Transform;
import cl.ucn.disc.dsm.thenewsapi.model.News;
import cl.ucn.disc.dsm.thenewsapi.services.NewsService;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApiService implements NewsService {

  /**
   * Logger.
   */
  private static final Logger log = LoggerFactory.getLogger(NewsApiService.class);

  /**
   * NewsAPI.
   */
  private final NewsApi newsApi;

  /**
   * Constructor.
   */
  public NewsApiService() {

    // Logging with slf4j.
    final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::debug)
        .setLevel(Level.BODY);

    // Web Client.
    final OkHttpClient httpClient = new Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .callTimeout(10, TimeUnit.SECONDS)
        .addNetworkInterceptor(loggingInterceptor)
        .build();

    // https://futurestud.io/tutorials/retrofit-getting-started-and-android-client.
    this.newsApi = new Retrofit.Builder()
        // Main URL.
        .baseUrl(NewsApi.BASE_URL)
        // JSON to POJO.
        .addConverterFactory(GsonConverterFactory.create())
        // Validates the interface.
        .validateEagerly(true)
        // Build the Retrofit and get the NewsApi.
        .build()
        .create(NewsApi.class);
  }

  /**
   * Get the News from the Call.
   *
   * @param call - To use.
   * @return - The {@link List} of {@link News}.
   */
  private static List<News> getNewsFromCall(final Call<NewsApiResult> call) {

    try {
      // Get the result from the call.
      final Response<NewsApiResult> response = call.execute();

      // Not successful.
      if(!response.isSuccessful()) {

        // Error.
        throw new NewsApiException("Can't get the NewsResult, code: " + response.code()
            , new HttpException(response));
      }

      // Result.
      final NewsApiResult result = response.body();

      // No body.
      if(result == null) {
        throw new NewsApiException("NewsResult was null");
      }

      // No articles.
      if(result.articles == null) {
        throw new NewsApiException("Articles in NewsResult was null");
      }

      // Article to News (transformer) thought Stream.
      return result.articles.stream()
          .map(Transform::transform)
          .collect(Collectors.toList());

    }catch(final IOException e) {
      throw new NewsApiException("Can't get the NewsResult", e);
    }
  }

  /**
   * Get the News from the Call.
   *
   * @param pageSize - How many News.
   * @return - The {@link List} of {@link News}.
   */
  @Override
  public List<News> getEverything(int pageSize) {

    // Call.
    final Call<NewsApiResult> call = this.newsApi.getEverything(pageSize);

    // Process the Call.
    return getNewsFromCall(call);
  }

  /**
   * Get the News from the Call.
   *
   * @param pageSize - How many News.
   * @return - The {@link List} of {@link News}.
   */
  @Override
  public List<News> getTopHeadLines(final int pageSize) {

    String country = Country.mx.toString();
    String category = Category.science.toString();

    // Call.
    final Call<NewsApiResult> call = this.newsApi.getTopHeadLines(country, category, pageSize);

    // Process the Call.
    return getNewsFromCall(call);
  }

  /**
   * Inner class - Exception.
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
   * Enum class - Category.
   */
  public enum Category {
    business,
    entertainment,
    general,
    health,
    science,
    sports,
    technology
  }

  /**
   * Enum class - Country.
   */
  public enum Country {
    ar, // Argentina
    co, // Colombia
    cu, // Cuba
    de, // Germany
    jp, // Japan
    mx, // Mexico
    ru,  // Russia
    us, // United States
    ve // Venezuela
  }
}
