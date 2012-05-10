// 
// MobeelizerIntegerFieldOptions.java
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

public class MobeelizerIntegerFieldOptions implements MobeelizerFieldOptions {

    private static final long serialVersionUID = -6871567997813790161L;

    private int minValue = Integer.MIN_VALUE;

    private int maxValue = Integer.MAX_VALUE;

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(final int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(final int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return "IntegerModelFieldOptions [minValue=" + minValue + ", maxValue=" + maxValue + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + maxValue;
        result = prime * result + minValue;
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
        if (!(obj instanceof MobeelizerIntegerFieldOptions)) {
            return false;
        }
        MobeelizerIntegerFieldOptions other = (MobeelizerIntegerFieldOptions) obj;
        if (maxValue != other.maxValue) {
            return false;
        }
        if (minValue != other.minValue) {
            return false;
        }
        return true;
    }

}
