package org.allison.choicemaker21.util.transferable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Allison on 5/2/2015.
 */
public class JacksonTransfer<T> implements Transferable<T> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Class<T> clazz;

    public JacksonTransfer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize() {
        try {
            return mapper.writeValueAsBytes(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
            return mapper.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
