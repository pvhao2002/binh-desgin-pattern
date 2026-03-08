package com.db.service.template;

import java.io.InputStream;
import java.util.List;

public abstract class AbstractDataImportTemplate {

    public final int importData(InputStream input) {
        List<String> raw = readRaw(input);
        if (raw.isEmpty()) return 0;
        validate(raw);
        var entities = mapToEntities(raw);
        return save(entities);
    }

    protected abstract List<String> readRaw(InputStream input);
    protected abstract void validate(List<String> raw);
    protected abstract List<?> mapToEntities(List<String> raw);
    protected abstract int save(List<?> entities);
}
