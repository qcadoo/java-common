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

import com.mobeelizer.java.api.MobeelizerOperationError;
import com.mobeelizer.java.api.user.MobeelizerUser;
import com.mobeelizer.java.errors.MobeelizerOperationStatus;

public interface MobeelizerConnectionService {

    MobeelizerAuthenticateResponse authenticate(final String login, final String password);

    @Deprecated
    MobeelizerAuthenticateResponse authenticate(final String user, final String password, final String token);

    MobeelizerAuthenticateResponse authenticate(final String user, final String password, final String deviceType,
            final String deviceToken);

    MobeelizerOperationStatus<List<String>> getGroups();

    MobeelizerOperationStatus<List<MobeelizerUser>> getUsers();

    MobeelizerOperationStatus<MobeelizerUser> getUser(final String login);

    MobeelizerOperationError createUser(final MobeelizerUser user);

    MobeelizerOperationError updateUser(final MobeelizerUser user);

    MobeelizerOperationError deleteUser(final String login);

    MobeelizerOperationStatus<String> sendSyncAllRequest();

    MobeelizerOperationStatus<String> sendSyncDiffRequest(final File outputFile);

    MobeelizerOperationError waitUntilSyncRequestComplete(final String ticket);

    File getSyncData(final String ticket) throws IOException;

    MobeelizerOperationError confirmTask(final String ticket);

    MobeelizerOperationError registerForRemoteNotifications(final String token);

    MobeelizerOperationError unregisterForRemoteNotifications(final String token);

    MobeelizerOperationError sendRemoteNotification(final String device, final String group, final List<String> users,
            final Map<String, String> notification);
    
    File getConflictHistory(final String model, final String guid) throws IOException;
}