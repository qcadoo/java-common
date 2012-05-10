// 
// MobeelizerIntegerFieldOptionTypeHelper.java
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

import java.util.regex.Pattern;

public class MobeelizerIntegerFieldOptionTypeHelper implements MobeelizerModelFieldOptionTypeHelper {

    private static final Pattern integerPattern = Pattern.compile("-?[0-9]+");

    @Override
    public Object getValue(final String value) {
        return Integer.valueOf(value.trim());
    }

    @Override
    public boolean validate(final String value) {
        return integerPattern.matcher(value.trim()).matches();
    }
}
