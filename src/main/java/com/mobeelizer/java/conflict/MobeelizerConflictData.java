package com.mobeelizer.java.conflict;

import java.io.File;
import java.io.InputStream;
import com.mobeelizer.java.sync.MobeelizerData;

public class MobeelizerConflictData extends MobeelizerData {

    public MobeelizerConflictData(final InputStream inputStream, final File tmpFile) {
    	super(inputStream, tmpFile);
    }
    
    public Iterable<MobeelizerConflictVersionJson> getVersionsIterator(){
    	return new MobeelizerConflictDataIterable(this);
    }
}
