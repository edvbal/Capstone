package com.telesoftas.edvinas.onboardingedvblk.utils.network.response;

import com.telesoftas.edvinas.onboardingedvblk.settings.Source;

import java.util.List;

public class SourcesResponse {
    private final List<Source> sources;

    public SourcesResponse(List<Source> sources) {
        this.sources = sources;
    }

    public List<Source> getSources() {
        return sources;
    }
}
