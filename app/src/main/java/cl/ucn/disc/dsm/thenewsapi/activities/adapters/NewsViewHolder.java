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
import java.util.Date;
import org.ocpsoft.prettytime.PrettyTime;
import org.threeten.bp.DateTimeUtils;

/**
 * ViewHolder Pattern.
 *
 * @author Ariel-Vejar.
 */
public final class NewsViewHolder extends RecyclerView.ViewHolder {

  /**
   * The Date formatter.
   */
  private static final PrettyTime PRETTY_TIME = new PrettyTime();

  /**
   * Bindings.
   */
  private final RowNewsBinding binding;

  /**
   * Constructor.
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
   * @param news - To bind.
   */
  public void bind(final News news) {

    // If exist the url ...
    if(news.getUrlPic() != null) {
      // ... Set the uri.
      this.binding.sdvPicture.setImageURI(news.getUrlPic());

    }else {
      // .. set a default image.
      this.binding.sdvPicture.setImageResource(R.drawable.ic_launcher_background);
    }

    this.binding.tvTitle.setText(news.getTitle());
    this.binding.tvDescription.setText(news.getDescription());
    this.binding.tvAuthor.setText(news.getAuthor());
    this.binding.tvSource.setText(news.getSource());

    // ZonedDateTime to Date.
    final Date date = DateTimeUtils.toDate(news.getDate().toInstant());
    this.binding.tvDate.setText(PRETTY_TIME.format(date));
  }
}
