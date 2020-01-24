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

public class Article {

  /**
   * Article Source.
   */
  public Source source;

  /**
   * Article Author.
   */
  public String author;

  /**
   * Article Title.
   */
  public String title;

  /**
   * Article Description.
   */
  public String description;

  /**
   * Article URL.
   */
  public String url;

  /**
   * Article URL-Image.
   */
  public String urlToImage;

  /**
   * Article Date.
   */
  public String publishedAt;

  /**
   * Article Content.
   */
  public String content;
}
