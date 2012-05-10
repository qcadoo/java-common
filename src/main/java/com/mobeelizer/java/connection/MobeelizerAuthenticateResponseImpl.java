// 
// MobeelizerAuthenticateResponseImpl.java
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

public class MobeelizerAuthenticateResponseImpl implements MobeelizerAuthenticateResponse {

    private final String role;

    private final String instanceGuid;

    public MobeelizerAuthenticateResponseImpl(final String role, final String instanceGuid) {
        this.role = role;
        this.instanceGuid = instanceGuid;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getInstanceGuid() {
        return instanceGuid;
    }

    @Override
    public String toString() {
        return "MobeelizerAuthenticateResponse [role=" + role + ", instanceGuid=" + instanceGuid + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((instanceGuid == null) ? 0 : instanceGuid.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MobeelizerAuthenticateResponseImpl)) {
            return false;
        }
        MobeelizerAuthenticateResponseImpl other = (MobeelizerAuthenticateResponseImpl) obj;
        if (instanceGuid == null) {
            if (other.instanceGuid != null) {
                return false;
            }
        } else if (!instanceGuid.equals(other.instanceGuid)) {
            return false;
        }
        if (role == null) {
            if (other.role != null) {
                return false;
            }
        } else if (!role.equals(other.role)) {
            return false;
        }
        return true;
    }

}
