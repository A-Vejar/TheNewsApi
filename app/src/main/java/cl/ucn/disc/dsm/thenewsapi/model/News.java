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
   * Zone
   */
  public static final ZoneId ZONE_ID = ZoneId.of("-3");

  /**
   * News ID
   */
  public final Long id;

  /**
   * News Title
   */
  public final String title;

  /**
   * News Source
   */
  public final String source;

  /**
   * News Author
   */
  public final String author;

  /**
   * News URL
   */
  public final String url;

  /**
   * News URL-Picture
   */
  public final String urlPic;

  /**
   * News Description
   */
  public final String description;

  /**
   * News Content
   */
  public final String content;

  /**
   * News Date
   */
  public final ZonedDateTime date;

  /**
   * Constructor
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
   * @return - ID
   */
  public Long getId() {
    return this.id;
  }

  /**
   * @return - Title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * @return - Source
   */
  public String getSource() {
    return this.source;
  }

  /**
   * @return - Author
   */
  public String getAuthor() {
    return this.author;
  }

  /**
   * @return - Url
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * @return - UrlPic
   */
  public String getUrlPic() {
    return this.urlPic;
  }

  /**
   * @return - Description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * @return - Content
   */
  public String getContent() {
    return this.content;
  }

  /**
   * @return - Date
   */
  public ZonedDateTime getDate() {
    return this.date;
  }
}
