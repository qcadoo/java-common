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
 * @since 1.0
 */
public class MobeelizerUser {

    private String login;

    private String password;
    
    private String sha256EncodedPassword;

    private String mail;

    private String group;

    private boolean admin;

    /**
     * Gets the login.
     * 
     * @return login
     * @since 1.0
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login.
     * 
     * @param login
     *            login
     * @since 1.0
     */
    public void setLogin(final String login) {
        this.login = login;
    }

    /**
     * Gets the password. Note that this field is not retrieve from the Cloud.
     * 
     * @return password
     * @since 1.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password
     *            password
     * @since 1.0
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets the SHA-256 hashed password. Note that this field is not retrieve from the Cloud.
     *
     * @return password
     * @since 1.0
     */

	public String getSha256EncodedPassword() {
		return sha256EncodedPassword;
	}

    /**
     * Sets HA-256 hashed password.
     *
     * @since 1.0
     */
	public void setSha256EncodedPassword(String sha256EncodedPassword) {
		this.sha256EncodedPassword = sha256EncodedPassword;
	}

    /**
     * Gets the email address.
     * 
     * @return mail
     * @since 1.0
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets the email address.
     * 
     * @param mail
     *            mail
     * @since 1.0
     */
    public void setMail(final String mail) {
        this.mail = mail;
    }

    /**
     * Gets the group that user belongs to.
     * 
     * @return group
     * @since 1.0
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the group that user belongs to.
     * 
     * @param group
     *            group
     * @since 1.0
     */
    public void setGroup(final String group) {
        this.group = group;
    }

    /**
     * Check if the user has administation privileges.
     * 
     * @return true if user has administation privileges
     * @since 1.0
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets whether the user has administation privileges.
     * 
     * @param admin
     *            admin
     */
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
