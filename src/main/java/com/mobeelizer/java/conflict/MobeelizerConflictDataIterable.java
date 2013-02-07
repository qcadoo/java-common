package com.mobeelizer.java.conflict;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.json.JSONException;
import com.mobeelizer.java.sync.MobeelizerData;

public class MobeelizerConflictDataIterable implements Iterable<MobeelizerConflictVersionJson> {

	private MobeelizerData data;
	
	public MobeelizerConflictDataIterable(MobeelizerData data){
		this.data = data;
	}
	
	@Override
	public Iterator<MobeelizerConflictVersionJson> iterator() {
		return new ConflictDataIterator(data.getDataInputStream());
	}

	class ConflictDataIterator implements Iterator<MobeelizerConflictVersionJson>{
        
		private final BufferedReader reader;

        private String currentLine;

        public ConflictDataIterator(final InputStream inputData) {
            try {
                reader = new BufferedReader(new InputStreamReader(inputData, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e);
            }
        }
        
		@Override
		public boolean hasNext() {
            return readLine() != null;
		}

		@Override
		public MobeelizerConflictVersionJson next() {
			try {
                return new MobeelizerConflictVersionJson(getCurrentLine());
            } catch (JSONException e) {
                throw new IllegalStateException(e);
            }
		}

		@Override
		public void remove() {
            throw new UnsupportedOperationException();
		}

        private String readLine() {
            try {
                if (currentLine == null) {
                    currentLine = reader.readLine();
                }
                return currentLine;
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        
        private String getCurrentLine() {
            String line = readLine();
            if (line == null) {
                throw new IllegalStateException("Unexpected end of file");
            }
            currentLine = null;
            return line;
        }	
	}
}
