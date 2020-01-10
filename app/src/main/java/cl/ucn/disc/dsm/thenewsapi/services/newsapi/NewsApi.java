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

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsApi {

  /**
   * URL API
   */
  String BASE_URL = "https://newsapi.org/v2/";

  /**
   * API Key
   */
  String API_KEY = "ab97f667d6dd4d73a7388bcdc269170c";

  /**
   * Endpoints - https://newsapi.org/docs/endpoints/top-headlines
   *
   * @param category - Used as filter
   * @param pageSize - Number of result to get
   * @return - The call of {@link NewsApiResult}
   */
  // TODO: Add news by different countries - Inner interface ??
  @Headers({"X-Api-key: " + API_KEY, "X-No-Cache: true"})
  @GET("top-headlines")
  Call<NewsApiResult> getTopHeadLines(@Query("category") final String category, @Query("pageSize") final int pageSize);

  /**
   * Endpoints - https://newsapi.org/docs/endpoints/everything
   *
   * @param pageSize - Number of result to get
   * @return - The call of {@link NewsApiResult}
   */
  @Headers({"X-Api-key: " + API_KEY, "X-No-Cache: true"})
  @GET("everything?sources=ars-technica,wired,hacker-news,recode")
  Call<NewsApiResult> getEverything(@Query("pageSize") final int pageSize);
}
