// 
// MobeelizerInputData.java
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

package com.mobeelizer.java.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;

public class MobeelizerInputData extends MobeelizerData{

    public MobeelizerInputData(final InputStream inputStream, final File tmpFile) {
        super(inputStream, tmpFile);
    }

    public List<String> getFiles() {
        List<String> result = new LinkedList<String>();
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            String entryName = entries.nextElement().getName();
            if (!entryName.equals(DATA_ENTRY_NAME) && !entryName.equals(DELETED_FILES_ENTRY_NAME)) {
                result.add(entryName);
            }
        }
        return result;
    }

    public Iterable<MobeelizerJsonEntity> getInputData() {
        return new MobeelizerInputDataIterable(this);
    }

    public List<String> getDeletedFiles() {
        ZipEntry entry = zipFile.getEntry(DELETED_FILES_ENTRY_NAME);

        if (entry == null) {
            throw new IllegalStateException("Zip entry " + DELETED_FILES_ENTRY_NAME + " hasn't been found");
        }

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
            String line;
            List<String> lines = new LinkedList<String>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Log.d(TAG, e.getMessage(), e);
                }
            }
        }
    }
}
