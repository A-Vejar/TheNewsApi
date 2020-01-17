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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import cl.ucn.disc.dsm.thenewsapi.NewsViewModel;
import cl.ucn.disc.dsm.thenewsapi.activities.adapters.NewsAdapter;
import cl.ucn.disc.dsm.thenewsapi.databinding.ActivityMainBinding;
import es.dmoral.toasty.Toasty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends AppCompatActivity {

  /**
   * Logger
   */
  private static final Logger log = LoggerFactory.getLogger(MainActivity.class);

  /**
   * Bindings
   */
  private ActivityMainBinding binding;

  /**
   * NewsAdapter
   */
  private NewsAdapter adapter;

  /**
   * ViewModel of News
   */
  private NewsViewModel newsViewModel;

  /**
   * @param savedInstanceState to use.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Inflate the layout
    binding = ActivityMainBinding.inflate(getLayoutInflater());

    // Assign to the main view.
    setContentView(binding.getRoot());

    // Set the toolbar
    {
      this.setSupportActionBar(binding.toolbar);
    }

    // Adapter + RecyclerView
    {
      // Adapter
      adapter = new NewsAdapter();
      binding.rvNews.setAdapter(adapter);

      // Layout (ListView)
      binding.rvNews.setLayoutManager(new LinearLayoutManager(this));

      // Separator (line)
      binding.rvNews.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    // The ViewModel
    {
      // Build the NewsViewModel
      newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

      // Observe the list of news
      newsViewModel.getTopHeadLines().observe(this, news -> adapter.setNews(news));

      // Observe the exception
      newsViewModel.getException().observe(this, this::showException);
    }

    // The refresh
    {
      binding.swlRefresh.setOnRefreshListener(() -> {
        log.debug("Refreshing ..");

        // Run in background
        AsyncTask.execute(() -> {

          // All ok
          final int size = newsViewModel.refresh();
          if (size != -1) {

            // In the UI
            runOnUiThread(() -> {

              // Hide the loading
              binding.swlRefresh.setRefreshing(false);

              // Show a message.
              Toasty.success(this, "News fetched: " + size, Toast.LENGTH_SHORT, true).show();
            });
          }
        });
      });
    }
  }

  /**
   * Show the exception
   *
   * @param - Exception to use
   */
  private void showException(final Exception exception) {

    // Hide the loading
    binding.swlRefresh.setRefreshing(false);

    // Build the message
    final StringBuilder sb = new StringBuilder("Error: ");
    sb.append(exception.getMessage());

    if(exception.getCause() != null) {
      sb.append(", ");
      sb.append(exception.getCause().getMessage());
    }

    Toasty.error(this, sb.toString(), Toast.LENGTH_LONG, true).show();
  }
}
