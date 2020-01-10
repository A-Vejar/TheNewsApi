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

import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * Domain class
 *
 * @author Ariel-Vejar
 */
public final class News {

  /**
   * The Zone
   */
  public static final ZoneId ZONE_ID = ZoneId.of("-3");

  /**
   * The Id
   */
  public final Long id;

  /**
   * The Title
   */
  public final String title;

  /**
   * The Source
   */
  public final String source;

  /**
   * The Author
   */
  public final String author;

  /**
   * The URL
   */
  public final String url;

  /**
   * The URL Picture
   */
  public final String urlPic;

  /**
   * The Description
   */
  public final String description;

  /**
   * The Content
   */
  public final String content;

  /**
   * The Date
   */
  public final ZonedDateTime date;

  /**
   * The Constructor.
   *
   * @param id
   * @param title
   * @param source
   * @param author
   * @param url
   * @param urlPic
   * @param description
   * @param content
   * @param date
   */
  public News(Long id, String title, String source, String author, String url, String urlPic,
      String description, String content, ZonedDateTime date) {
    this.id = id;
    this.title = title;
    this.source = source;
    this.author = author;
    this.url = url;
    this.urlPic = urlPic;
    this.description = description;
    this.content = content;
    this.date = date;
  }

  /**
   * @return The id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * @return The title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * @return The source
   */
  public String getSource() {
    return this.source;
  }

  /**
   * @return The author
   */
  public String getAuthor() {
    return this.author;
  }

  /**
   * @return The url
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * @return The urlPic
   */
  public String getUrlPic() {
    return this.urlPic;
  }

  /**
   * @return The description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * @return The content
   */
  public String getContent() {
    return this.content;
  }

  /**
   * @return The date
   */
  public ZonedDateTime getDate() {
    return this.date;
  }
}
