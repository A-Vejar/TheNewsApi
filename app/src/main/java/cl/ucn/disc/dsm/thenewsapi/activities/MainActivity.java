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

package cl.ucn.disc.dsm.thenewsapi.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import cl.ucn.disc.dsm.thenewsapi.activities.adapters.NewsAdapter;
import cl.ucn.disc.dsm.thenewsapi.databinding.ActivityMainBinding;
import cl.ucn.disc.dsm.thenewsapi.model.News;
import cl.ucn.disc.dsm.thenewsapi.services.NewsService;
import cl.ucn.disc.dsm.thenewsapi.services.newsapi.NewsApiService;
import es.dmoral.toasty.Toasty;
import java.util.List;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends AppCompatActivity {

  /**
   * Logger
   */
  private static final Logger log = LoggerFactory.getLogger(MainActivity.class);

  /**
   * NewsAdapter
   */
  private NewsAdapter adapter;

  /**
   * NewsService
   */
  private NewsService service;

  /**
   * @param savedInstanceState to use.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Inflate the layout
    ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

    // Assign to the main view.
    setContentView(binding.getRoot());

    // Set the toolbar
    {
      this.setSupportActionBar(binding.toolbar);
    }

    // Adapter + RecyclerView
    {
      // Adapter
      this.adapter = new NewsAdapter();
      binding.rvNews.setAdapter(this.adapter);

      // Layout (ListView)
      binding.rvNews.setLayoutManager(new LinearLayoutManager(this));

      // Separator (line)
      binding.rvNews.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    // NewsService
    this.service = new NewsApiService();

    // The refresh
    {
      binding.swlRefresh.setOnRefreshListener(() -> {
        log.debug("Refreshing ..");

        // Execute in background ..
        AsyncTask.execute(() -> {

          // How much time do we need?
          final StopWatch stopWatch = StopWatch.createStarted();

          try {

            // 1. Get the List from NewsApi (in background)
            //final List<News> news = this.service.getEverything(50);
            final List<News> news = this.service.getTopHeadLines(50);

            // (in UI)
            this.runOnUiThread(() -> {

              // 2. Set in the adapter (
              this.adapter.setNews(news);

              // 3. Show a Toast!
              Toasty.success(this, "Done: " + stopWatch, Toast.LENGTH_SHORT, true).show();
            });

          }catch(Exception e) {
            log.error("Error", e);

            // (in UI)
            this.runOnUiThread(() -> {

              // Build the message
              final StringBuffer sb = new StringBuffer("Error: ");
              sb.append(e.getMessage());

              if(e.getCause() != null) {
                sb.append(", ");
                sb.append(e.getCause().getMessage());
              }

              // 3. Show the Toast!
              Toasty.error(this, sb.toString(), Toast.LENGTH_LONG, true).show();
            });

          }finally {
            // 4. Hide the spinning circle
            binding.swlRefresh.setRefreshing(false);
          }
        });
      });
    }
  }
}
