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

package cl.ucn.disc.dsm.thenewsapi;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.facebook.drawee.backends.pipeline.Fresco;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApplication extends Application {

  /**
   * Logger
   */
  private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

  /**
   * Called when the application is starting, before any activity, service, or receiver objects (excluding content
   * providers) have been created.
   */
  @Override
  public void onCreate() {
    super.onCreate();

    log.debug("Initializing ..");

    /**
     * Day and Night support
     *
     * MODE_NIGHT_NO - Day mode
     * MODE_NIGHT_YES - Night mode
     * MODE_NIGHT_AUTO_BATTERY - Night mode if the save battery is activate
     * MODE_NIGHT_FOLLOW_SYSTEM - Default mode by the device
     */
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);

    // Facebook fresco
    Fresco.initialize(this);

    log.debug("Initializing: Done.");
  }
}
