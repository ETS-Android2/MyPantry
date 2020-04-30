/*
 * Copyright (c) 2020
 * Mateusz Hermanowicz - All rights reserved.
 * My Pantry
 * https://www.mypantry.eu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hermanowicz.pantry.interfaces;

public interface AppSettingsView {
    void setSelectedTheme(int selectedTheme);

    void setScanCamera (int selectedCamera);

    void setDaysBeforeExpirationDate(int daysBeforeExpirationDate);

    void setCheckbox_pushNotification(boolean isPushNotificationsAllowed);

    void setCheckbox_emailNotification(boolean isEmailNotificationsAllowed);

    void setEmailAddress(String emailAddress);

    void setHourOfNotifications(int hourOfNotifications);

    void recreateNotifications();

    void enableEmailCheckbox(boolean isValidEmail);

    void onSettingsSaved();

    void onDatabaseClear();

    void showCodeVersion(String version);

    void navigateToMainActivity();
}