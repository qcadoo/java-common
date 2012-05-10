// 
// MobeelizerModelFieldOption.java
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

package com.mobeelizer.java.definition.type.options.type;

public class MobeelizerModelFieldOption {

    private String name;

    private MobeelizerModelFieldOptionTypeHelper type;

    private boolean required;

    public MobeelizerModelFieldOption(final String name, final MobeelizerModelFieldOptionTypeHelper type, final boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public MobeelizerModelFieldOptionTypeHelper getType() {
        return type;
    }

    public void setType(final MobeelizerModelFieldOptionTypeHelper type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (required ? 1231 : 1237);
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        if (!(obj instanceof MobeelizerModelFieldOption)) {
            return false;
        }
        MobeelizerModelFieldOption other = (MobeelizerModelFieldOption) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (required != other.required) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModelFieldOption [name=" + name + ", type=" + type + ", required=" + required + "]";
    }

}
