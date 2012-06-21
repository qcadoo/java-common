// 
// MobeelizerConnectionService.java
// 
// Copyright (C) 2012 Mobeelizer Ltd. All Rights Reserved.
//
// Mobeelizer SDK is free software; you can redistribute it and/or modify it 
// under the terms of the GNU Affero General Public License as published by 
// the Free Software Foundation; either version 3 of the License, or (at your
// option) any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
// for more details.
//
// You should have received a copy of the GNU Affero General Public License 
// along with this program; if not, write to the Free Software Foundation, Inc., 
// 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
//

package com.mobeelizer.java.connection;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.mobeelizer.java.api.user.MobeelizerUser;

public interface MobeelizerConnectionService {

    MobeelizerAuthenticateResponse authenticate(final String login, final String password) throws IOException;

    MobeelizerAuthenticateResponse authenticate(final String user, final String password, final String token) throws IOException;

    List<String> getGroups() throws IOException;

    List<MobeelizerUser> getUsers() throws IOException;

    MobeelizerUser getUser(final String login) throws IOException;

    void createUser(final MobeelizerUser user) throws IOException;

    void updateUser(final MobeelizerUser user) throws IOException;

    boolean deleteUser(final String login) throws IOException;

    String sendSyncAllRequest() throws IOException;

    String sendSyncDiffRequest(final File outputFile) throws IOException;

    MobeelizerConnectionResult waitUntilSyncRequestComplete(final String ticket) throws IOException;

    File getSyncData(final String ticket) throws IOException;

    void confirmTask(final String ticket) throws IOException;

    void registerForRemoteNotifications(final String token) throws IOException;

    void unregisterForRemoteNotifications(final String token) throws IOException;

    void sendRemoteNotification(final String device, final String group, final List<String> users,
            final Map<String, String> notification) throws IOException;

}