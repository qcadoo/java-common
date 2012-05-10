// 
// MobeelizerTextFieldOptions.java
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

package com.mobeelizer.java.definition.type.options;

public class MobeelizerTextFieldOptions implements MobeelizerFieldOptions {

    private static final long serialVersionUID = 8632797810027283427L;

    private int maxLength = 4096;

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + maxLength;
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
        if (!(obj instanceof MobeelizerTextFieldOptions)) {
            return false;
        }
        MobeelizerTextFieldOptions other = (MobeelizerTextFieldOptions) obj;
        if (maxLength != other.maxLength) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TextModelFieldOptions [maxLength=" + maxLength + "]";
    }

}
