// 
// MobeelizerUser.java
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

package com.mobeelizer.java.api.user;

/**
 * Represents the instance's user.
 * 
 * @sinse 1.0
 */
public class MobeelizerUser {

    /**
     * The login.
     */
    private String login;

    /**
     * The password. Note that this field is not retrieve from the Cloud.
     */
    private String password;

    /**
     * The email address.
     */
    private String mail;

    /**
     * The group that user belongs to.
     */
    private String group;

    /**
     * True if the user has administation privileges.
     */
    private boolean admin;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(final boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "MobeelizerUser [login=" + login + ", mail=" + mail + ", group=" + group + ", admin=" + admin + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (admin ? 1231 : 1237);
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((mail == null) ? 0 : mail.hashCode());
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
        if (!(obj instanceof MobeelizerUser)) {
            return false;
        }
        MobeelizerUser other = (MobeelizerUser) obj;
        if (admin != other.admin) {
            return false;
        }
        if (group == null) {
            if (other.group != null) {
                return false;
            }
        } else if (!group.equals(other.group)) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        if (mail == null) {
            if (other.mail != null) {
                return false;
            }
        } else if (!mail.equals(other.mail)) {
            return false;
        }
        return true;
    }

}
