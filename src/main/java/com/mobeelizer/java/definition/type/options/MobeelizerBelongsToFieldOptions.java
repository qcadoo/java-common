// 
// MobeelizerBelongsToFieldOptions.java
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

public class MobeelizerBelongsToFieldOptions implements MobeelizerFieldOptions {

    private static final long serialVersionUID = -6871567997813790161L;

    private boolean analyzeConflict;

    private String model;

    public boolean isAnalyzeConflict() {
        return analyzeConflict;
    }

    public void setAnalyzeConflict(final boolean analyzeConflict) {
        this.analyzeConflict = analyzeConflict;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (analyzeConflict ? 1231 : 1237);
        result = prime * result + ((model == null) ? 0 : model.hashCode());
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
        if (!(obj instanceof MobeelizerBelongsToFieldOptions)) {
            return false;
        }
        MobeelizerBelongsToFieldOptions other = (MobeelizerBelongsToFieldOptions) obj;
        if (analyzeConflict != other.analyzeConflict) {
            return false;
        }
        if (model == null) {
            if (other.model != null) {
                return false;
            }
        } else if (!model.equals(other.model)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BelongsToModelFieldOptions [analyzeConflict=" + analyzeConflict + ", model=" + model + "]";
    }

}
