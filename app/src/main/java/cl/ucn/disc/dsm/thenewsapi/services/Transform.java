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
import cl.ucn.disc.dsm.thenewsapi.services.newsapi.Article;
import cl.ucn.disc.dsm.thenewsapi.services.newsapi.NewsApiService;
import cl.ucn.disc.dsm.thenewsapi.services.newsapi.Source;
import java.net.URI;
import java.net.URISyntaxException;
import net.openhft.hashing.LongHashFunction;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeParseException;

public class Transform {

  /**
   * Logger.
   */
  private static final Logger log = LoggerFactory.getLogger(Transform.class);

  /**
   * Article to News.
   *
   * @param article - To transform.
   * @return - The News.
   */
  public static News transform(final Article article) {

    throwingExceptions(article);

    // The date.
    final ZonedDateTime publishedAt = parseZonedDateTime(article.publishedAt)
        .withZoneSameInstant(News.ZONE_ID);
    log.debug("PUBLISHED AT = {}", publishedAt);

    final ZonedDateTime published = ZonedDateTime.parse(article.publishedAt);
    log.debug("PUBLISH AT = {}", published);

    // The unique id (computed from hash).
    final Long theId = LongHashFunction.xx()
        .hashChars(article.title + article.source.name);
    log.debug("ID = {}", theId);

    // The News.
    return new News(
        theId,
        article.title,
        article.source.name,
        article.author,
        article.url,
        article.urlToImage,
        article.description,
        article.content,
        publishedAt
    );
  }

  /**
   * Exceptions.
   *
   * @param article - The articles.
   */
  private static void throwingExceptions(final Article article) {

    // If article is null.
    if (article == null) {
      throw new NewsApiService.NewsApiException("Article was null");
    }

    // Host.
    final String host = getHost(article.url);
    log.debug("HOST = {}", host);

    // If title is null.
    if (article.title == null) {

      // ... and the content/description is null as well, throws an exception.
      if (article.description == null) {
        throw new NewsApiService
            .NewsApiException("Article without title and description");
      }

      if (host != null) {
        article.title = host;

      } else {
        article.title = "No Title*";
        log.warn("Article without title: {}", toString(article));
      }
    }

    // If source is null.
    if (article.source == null) {
      article.source = new Source();

      if (host != null) {
        article.source.name = host;

      } else {
        article.source.name = "No Source*";
        log.warn("Article without source: {}", toString(article));
      }
    }

    // If author is null.
    if (article.author == null) {

      if (host != null) {
        article.author = host;

      } else {
        article.author = "No Author*";
        log.warn("Article without author: {}", toString(article));
      }
    }
  }

  /**
   * Get the host part of one url.
   *
   * @param url - To use.
   * @return - The host part (without the 'www').
   */
  private static String getHost(final String url) {

    try {
      final URI uri = new URI(url);
      final String hostname = uri.getHost();
      log.debug("HOSTNAME = {}", hostname);

      /*
       * To provide 'faultproof' result, check if
       * not null then return only hostname, without www.
       */
      if (hostname != null) {
        return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
      }
      return null;

    } catch (final URISyntaxException | NullPointerException e) {
      return null;
    }
  }

  /**
   * Parse a date of {@link String} to {@link ZonedDateTime}.
   *
   * @param date - Date to parse.
   * @return - The date.
   *
   * @throws - cl.ucn.disc.dsm.thenewsapi.services.newsapi.NewsApiService.NewsApiException
   *  --> In case of no achieve the parse of the date.
   */
  private static ZonedDateTime parseZonedDateTime(final String date) {

    // Null date.
    if (date == null) {
      throw new NewsApiService.NewsApiException("Can't parse null date");
    }

    try {
      // Try to parse the date ...
      return ZonedDateTime.parse(date);

    } catch (DateTimeParseException e) {

      // Debug message.
      log.error("Can't parse date: ->{}<-. Error: ", date, e);

      // Add a DateTimeParseException into a NewsTransformerException.
      throw new NewsApiService.NewsApiException("Can't parse date: " + date, e);
    }
  }

  /**
   * Transform into String an object t showing its attributes.
   *
   * @param t - To convert.
   * @param <T> - Type of t.
   * @return - The object in String format.
   */
  public static <T> String toString(final T t) {
    return ReflectionToStringBuilder
        .toString(t, ToStringStyle.MULTI_LINE_STYLE);
  }
}