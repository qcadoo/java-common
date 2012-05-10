// 
// MobeelizerDecimalFieldOptions.java
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

import java.math.BigDecimal;

public class MobeelizerDecimalFieldOptions implements MobeelizerFieldOptions {

    private static final long serialVersionUID = -1760773543926883197L;

    private BigDecimal minValue = BigDecimal.valueOf(-Double.MAX_VALUE);

    private BigDecimal maxValue = BigDecimal.valueOf(Double.MAX_VALUE);

    private boolean includeMinValue = true;

    private boolean includeMaxValue = true;

    private int scale = 3;

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(final BigDecimal minValue) {
        this.minValue = minValue;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(final BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isIncludeMinValue() {
        return includeMinValue;
    }

    public void setIncludeMinValue(final boolean includeMinValue) {
        this.includeMinValue = includeMinValue;
    }

    public boolean isIncludeMaxValue() {
        return includeMaxValue;
    }

    public void setIncludeMaxValue(final boolean includeMaxValue) {
        this.includeMaxValue = includeMaxValue;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(final int scale) {
        this.scale = scale;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (includeMaxValue ? 1231 : 1237);
        result = prime * result + (includeMinValue ? 1231 : 1237);
        result = prime * result + ((maxValue == null) ? 0 : maxValue.hashCode());
        result = prime * result + ((minValue == null) ? 0 : minValue.hashCode());
        result = prime * result + scale;
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
        if (!(obj instanceof MobeelizerDecimalFieldOptions)) {
            return false;
        }
        MobeelizerDecimalFieldOptions other = (MobeelizerDecimalFieldOptions) obj;
        if (includeMaxValue != other.includeMaxValue) {
            return false;
        }
        if (includeMinValue != other.includeMinValue) {
            return false;
        }
        if (maxValue == null) {
            if (other.maxValue != null) {
                return false;
            }
        } else if (!maxValue.equals(other.maxValue)) {
            return false;
        }
        if (minValue == null) {
            if (other.minValue != null) {
                return false;
            }
        } else if (!minValue.equals(other.minValue)) {
            return false;
        }
        if (scale != other.scale) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DecimalModelFieldOptions [minValue=" + minValue + ", maxValue=" + maxValue + ", includeMinValue="
                + includeMinValue + ", includeMaxValue=" + includeMaxValue + ", scale=" + scale + "]";
    }

}
