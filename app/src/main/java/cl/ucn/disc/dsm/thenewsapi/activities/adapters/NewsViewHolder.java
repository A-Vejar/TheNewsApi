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

import androidx.recyclerview.widget.RecyclerView;
import cl.ucn.disc.dsm.thenewsapi.R;
import cl.ucn.disc.dsm.thenewsapi.databinding.RowNewsBinding;
import cl.ucn.disc.dsm.thenewsapi.model.News;

/**
 * ViewHolder Pattern
 *
 * @author Ariel-Vejar
 */
public final class NewsViewHolder extends RecyclerView.ViewHolder {

  /**
   * Bindings
   */
  private final RowNewsBinding binding;

  /**
   * Constructor
   *
   * @param rowNewsBinding to use.
   */
  public NewsViewHolder(RowNewsBinding rowNewsBinding) {
    super(rowNewsBinding.getRoot());
    this.binding = rowNewsBinding;
  }

  /**
   * Bind the News to the ViewHolder.
   *
   * @param news - To bind
   */
  public void bind(final News news) {

    this.binding.tvTitle.setText(news.getTitulo());
    this.binding.tvDescription.setText(news.getDescription());
    this.binding.tvAuthor.setText(news.getAuthor());
    this.binding.tvSource.setText(news.getSource());

    // FIXME: The format of the date.
    this.binding.tvDate.setText(news.getDate().toString());

    // If exist the url ...
    if(news.getUrlPic() != null) {
      // ... Set the uri
      this.binding.sdvPicture.setImageURI(news.getUrlPic());

    }else {
      // .. set a default image
      this.binding.sdvPicture.setImageResource(R.drawable.ic_launcher_background);
    }
  }
}
