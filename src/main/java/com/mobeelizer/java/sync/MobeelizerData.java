package com.mobeelizer.java.sync;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class MobeelizerData {
	
	    static final String DATA_ENTRY_NAME = "data";

	    static final String DELETED_FILES_ENTRY_NAME = "deletedFiles";

	    protected ZipFile zipFile;

	    protected File tmpFile;

	    public MobeelizerData(final InputStream inputStream, final File tmpFile) {
	        try {
	            this.tmpFile = tmpFile;
	            copy(inputStream, tmpFile);
	            zipFile = new ZipFile(tmpFile, ZipFile.OPEN_READ);
	        } catch (IOException e) {
	            throw new IllegalStateException(e.getMessage(), e);
	        }
	    }

	    public InputStream getFile(final String guid) throws IOException {
	        ZipEntry fileEntry = zipFile.getEntry(guid);
	        if (fileEntry == null) {
	            throw new FileNotFoundException("File '" + guid + "' not foud.");
	        }
	        return zipFile.getInputStream(fileEntry);
	    }

	    public InputStream getDataInputStream() {
	        ZipEntry entry = zipFile.getEntry(DATA_ENTRY_NAME);

	        if (entry == null) {
	            throw new IllegalStateException("Zip entry " + DATA_ENTRY_NAME + " hasn't been found");
	        }

	        try {
	            return zipFile.getInputStream(entry);
	        } catch (IOException e) {
	            throw new IllegalStateException(e.getMessage(), e);
	        }
	    }

	    public void close() {
	        try {
	            if (tmpFile != null && tmpFile.exists() && !tmpFile.delete()) {
	                // TODO Log.w(TAG, "File '" + tmpFile.getAbsolutePath() + "' cannot be deleted");
	            }
	            zipFile.close();
	        } catch (IOException e) {
	            throw new IllegalStateException(e.getMessage(), e);
	        }
	    }

	    private void copy(final InputStream is, final File file) throws IOException {
	        FileOutputStream os = null;

	        try {
	            os = new FileOutputStream(file);
	            byte[] buffer = new byte[4096];
	            int n = 0;
	            while (-1 != (n = is.read(buffer))) {
	                os.write(buffer, 0, n);
	            }
	        } finally {
	            if (os != null) {
	                os.close();
	            }
	        }
	    }
}
