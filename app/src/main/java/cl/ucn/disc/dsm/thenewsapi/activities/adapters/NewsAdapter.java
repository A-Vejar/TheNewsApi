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

package cl.ucn.disc.dsm.thenewsapi.activities.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cl.ucn.disc.dsm.thenewsapi.R;
import cl.ucn.disc.dsm.thenewsapi.databinding.PopupImageBinding;
import cl.ucn.disc.dsm.thenewsapi.databinding.RowNewsBinding;
import cl.ucn.disc.dsm.thenewsapi.model.News;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ViewHolder Pattern.
 *
 * @author Ariel-Vejar.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

  /**
   * Logger.
   */
  private static final Logger log = LoggerFactory.getLogger(NewsAdapter.class);

  /**
   * News list.
   */
  private List<News> news;

  /**
   * Constructor.
   */
  public NewsAdapter() {

    // Empty list of news.
    this.news = new ArrayList<>();

    // Each News has an unique ID.
    this.setHasStableIds(true);
  }

  /**
   * Change the current List of News.
   *
   * @param news - To use.
   */
  public void setNews(final List<News> news) {

    // Update the news.
    this.news = news;

    // Notify to re-layout.
    this.notifyDataSetChanged();
  }

  /**
   * Show an image popup with the url.
   *
   * @param news - To be showed.
   * @param inflater - Inflates the popup.
   * @param context - Build the dialog.
   */
  private void showImagePopup(final News news, final LayoutInflater inflater, final Context context ) {

    // The popup-image.
    final PopupImageBinding popupImageBinding = PopupImageBinding.inflate(inflater);

    // The URL of the photo.
    popupImageBinding.pdvPic.setPhotoUri(Uri.parse(news.getUrlPic()));

    // The Dialog.
    final Dialog dialog = new Dialog(context, R.style.PopupDialog);
    dialog.setContentView(popupImageBinding.getRoot());
    dialog.show();
  }

  /**
   * Called when RecyclerView needs a newViewHolder of the given type to represent an item.
   */
  @NotNull
  @Override
  public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    // The inflater.
    final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

    // The row of news.
    final RowNewsBinding rowNewsBinding = RowNewsBinding.inflate(
        layoutInflater,
        parent,
        false
    );

    // The NewsViewHolder.
    final NewsViewHolder newsViewHolder = new NewsViewHolder(rowNewsBinding);

    /**
     * Click Listener ...
     * Popup the image on the list.
     */
    rowNewsBinding.sdvPicture.setOnClickListener(view -> {

      // The position.
      final int position = newsViewHolder.getAdapterPosition();

      // The id.
      final long id = newsViewHolder.getItemId();
      log.debug("Click! position: {}, id: {}.", position, Long.toHexString(id));

      // News to show.
      final News news = this.news.get(position);

      // Nothing to do.
      if (news.getUrlPic() == null) {
        return;
      }

      // Popup the image.
      this.showImagePopup(news, layoutInflater, parent.getContext());
    });

    /**
     * Click Listener ...
     * Click on a news in the row to open the news link.
     */
    rowNewsBinding.getRoot().setOnClickListener(view -> {

      // The position.
      final int position = newsViewHolder.getAdapterPosition();

      // The id.
      final long id = newsViewHolder.getItemId();
      log.debug("Click! position: {}, id: {}.", position, Long.toHexString(id));

      // News to show.
      final News news = this.news.get(position);

      log.debug("Link: {}.", news.getUrl());
      if (news.getUrl() != null) {

        // Open the browser.
        parent.getContext().startActivity(
            new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(news.getUrl())
            )
        );
      }
    });

    return newsViewHolder;
  }

  /**
   * Called by RecyclerView to display the data at the specified position. This method should update the contents of the ...
   * ... ViewHolder to reflect the item at the given position.
   */
  @Override
  public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
    holder.bind(this.news.get(position));
  }

  /**
   * Returns the total number of items in the data set held by the adapter.
   */
  @Override
  public int getItemCount() {
    return this.news.size();
  }

  /**
   * Return the stable ID for the item at position.
   */
  @Override
  public long getItemId(int position) {
    return this.news.get(position).id;
  }
}
