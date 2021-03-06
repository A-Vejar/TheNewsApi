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
import java.util.List;

/**
 * Service class.
 */
public interface NewsService {

  /**
   * Get the News from the backend.
   *
   * @param pageSize - How many News.
   * @return - The {@link List} of {@link News}.
   */
  List<News> getEverything(int pageSize);

  /**
   * Get the News from the backend.
   *
   * @param pageSize - How many News.
   * @return - The {@link List} of {@link News}.
   */
  List<News> getTopHeadLines(int pageSize);
}
